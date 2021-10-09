package domain;
import domain.Ladders;
import domain.Snakes;
import java.lang.Math; //Math.random()
import java.io.Serializable;
import java.util.ArrayList;

public class BoardState implements Serializable {
    final ArrayList<Ladders> ladderList = new ArrayList<>();
    final ArrayList<Snakes> snakeList = new ArrayList<>();

    public BoardState(){
        for(int i=0;i<=(int)(Math.random()*11)+5;i++)
        {
            snakeList.add(new Snakes());
            ladderList.add(new Ladders());
        }
    }

    public ArrayList<Ladders> getLadderList() {
        return ladderList;
    }

    public ArrayList<Snakes> getSnakeList() {
        return snakeList;
    }
}
