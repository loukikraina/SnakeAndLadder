package domain;

import java.io.Serializable;

public class PlayerState implements Comparable, Serializable {
    private int position;
    private String playerName;

    public PlayerState(String name){
        this.playerName = name;
        this.position = 1;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public String getPlayerName() {
        return playerName;
    }


    @Override
    public int compareTo(Object o) {
        PlayerState sortedPlayers = (PlayerState) o;
        if(position > sortedPlayers.getPosition()){
            return -1;
        }
        else if(position < sortedPlayers.position){
            return 1;
        }
        else {
            if (playerName.compareToIgnoreCase(sortedPlayers.getPlayerName())>0){
                return 1;
            }
            else{
                return -1;
            }
        }
    }
}
