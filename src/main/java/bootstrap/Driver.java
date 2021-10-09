package bootstrap;
import domain.*;


import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


public class Driver {

    public static int checkPosition(int nextPosition, ArrayList<Ladders> ladders, ArrayList<Snakes> snakes){
        for (Ladders i : ladders){
            if(nextPosition == i.getStartBox()) {
                System.out.println("Horray! You landed on a ladder, now enjoy the ride.");
                nextPosition = i.getEndBox();
            }
        }
        for (Snakes i : snakes){
            if(nextPosition == i.getStartBox()) {
                System.out.println("You dumb player, you stepped on a hungry snake. ");
                nextPosition = i.getEndBox();
            }
        }
        return nextPosition;
    }

    public static int RollDice(int currentPosition, ArrayList<Ladders> ladders, ArrayList<Snakes> snakes){
        int currentRoll = (int)(Math.random()*6)+1;
        int nextPosition = 0;
        int flag = 0;
        System.out.println("Its a "+currentRoll);
        if(currentPosition==1 && currentRoll!=6){
            currentPosition = 1;
            System.out.println("You didn't got a six! You need a six to get ahead of the starting position.");
        }
        else{
            if(currentRoll==6) {
                while (currentRoll == 6 && flag!=3){
                    Scanner sc= new Scanner(System.in);
                    System.out.println("Horray! You got a six!");
                    nextPosition = currentPosition+currentRoll;
                    currentPosition = checkPosition(nextPosition,ladders, snakes);
                    if(flag==2)
                    {
                        System.out.println("You have recieved max number of extra turns!");
                        currentRoll = 0;
                        break;
                    }
                    System.out.println("You got an extra turn, Press enter to roll dice! ");
                    String key = sc.nextLine();
                    currentRoll = (int)(Math.random()*6)+1;
                    System.out.println("Its a "+currentRoll);
                    flag++;
                }
            }
            nextPosition = currentPosition+currentRoll;
            currentPosition = checkPosition(nextPosition, ladders, snakes);
        }
        return currentPosition;
    }

    public static void checkout(int tag, ArrayList<PlayerState> finalList){
        if(tag==1){
            System.out.println("Congrats everyone on finishing the game!");
            System.out.println("Results: ");
        }
        else{
            System.out.println("The game is about to exit, saving the board stats!");
            System.out.println("Scoreboard till now: ");
        }
        Collections.sort(finalList);
        for (int i =0; i < finalList.size(); i++) {
            PlayerState player = finalList.get(i);
            System.out.println("On "+(i+1)+" position we have: "+player.getPlayerName()+", on block "+player.getPosition());
        }
        System.out.println("Thanks for playing this game, hope you enjoyed it.\n" +
                "See you all again soon. \n\n" +
                "Your friend and classmate\n" +
                "-LOUKIK RAINA");
    }


    public static void main(String[] args) throws Exception{

        Scanner sc= new Scanner(System.in);
        System.out.println("------SNAKE AND LADDER-----");
        System.out.println("Welcome to the most exciting game ever!");

        File file = new File("Player.dat");
        BoardState boardState = new BoardState();
        ArrayList<PlayerState> playerList = new ArrayList<>();
        int numberPlayers = 0;
        if(file.exists()){
            System.out.println("Saved state found!\n" +
                    "Press l to load previous game or press n to have a new game!!");
            String response = sc.nextLine();
            if(response.equals("l")){
                ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("Board.dat"));
                boardState = (BoardState) objectInputStream.readObject();

                ObjectInputStream objectInputStream1 = new ObjectInputStream(new FileInputStream("Player.dat"));
                playerList = (ArrayList<PlayerState>) objectInputStream1.readObject();

                numberPlayers = playerList.size();
                System.out.println("Loading previous data!\n" +
                        "Scorecard: ");
                for (int i =0; i < playerList.size(); i++) {
                    PlayerState player = playerList.get(i);
                    System.out.println("On "+(i+1)+" position we have: "+player.getPlayerName()+", on block "+player.getPosition());
                }

            }
            else{
                System.out.println("Enter the number of players: ");

                numberPlayers = sc.nextInt();

                for(int i=0;i<numberPlayers;i++)
                {
                    sc= new Scanner(System.in);
                    System.out.println("Enter player "+(i+1)+
                            " name: ");
                    playerList.add(new PlayerState(sc.nextLine()));
                }
            }
        }
        else{
            System.out.println("Enter the number of players: ");

            numberPlayers = sc.nextInt();

            for(int i=0;i<numberPlayers;i++)
            {
                sc= new Scanner(System.in);
                System.out.println("Enter player "+(i+1)+
                        " name: ");
                playerList.add(new PlayerState(sc.nextLine()));
            }
        }

        ArrayList<Ladders> ladders = boardState.getLadderList();
        ArrayList<Snakes> snakes  = boardState.getSnakeList();

        System.out.println("Let the game begin!");
        while(true){
            sc= new Scanner(System.in);
            for(int i=0;i<numberPlayers;i++)
            {
                System.out.println(playerList.get(i).getPlayerName()+" your current position on board is "+playerList.get(i).getPosition());
                System.out.println(playerList.get(i).getPlayerName()+
                        "'s turn. Press enter to roll dice! ");
                String key = sc.nextLine();
                if(key.equals("")) {
                    int currentPosition = playerList.get(i).getPosition();
                    int rollPosition = RollDice(currentPosition, ladders, snakes);
                    if (rollPosition > 99) {
                        checkout(1, playerList);
                        break;
                    } else {
                        System.out.println(playerList.get(i).getPlayerName()+
                                " you are at block "+rollPosition);
                        playerList.get(i).setPosition(rollPosition);
                    }
                }
                else {
                    System.out.println("You did not pressed enter" +
                            " and hence lost your turn!");
                }
                System.out.println("------------------------------------------------------------------------");
            }
            System.out.println("Press enter to continue with next round.\n" +
                    "Or Press any other key to save the state of current game and exit!");
            String key = sc.nextLine();
            if(!(key.equals(""))){
                System.out.println("Thanks for playing the game.\n" +
                        "Hope to see you again soon! :) ");
                checkout(0, playerList);

                ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("Player.dat"));
                objectOutputStream.writeObject(playerList);

                ObjectOutputStream objectOutputStream1 = new ObjectOutputStream(new FileOutputStream("Board.dat"));
                objectOutputStream1.writeObject(boardState);

                break;
            }
        }
    }
}
