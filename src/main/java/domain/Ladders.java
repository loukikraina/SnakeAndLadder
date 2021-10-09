package domain;

import java.io.Serializable;
import java.lang.Math;

public class Ladders implements Serializable {
    private final int startBox;
    private final int endBox;

    public Ladders(){
        this.startBox = 5+(int)(Math.random()*75);
        this.endBox = this.startBox+((int)(Math.random()*10)+10);
    }

    public int getStartBox() {
        return startBox;
    }

    public int getEndBox() {
        return endBox;
    }
}
