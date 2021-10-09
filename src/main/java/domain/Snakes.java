package domain;

import java.io.Serializable;
import java.lang.Math;

public class Snakes implements Serializable {
    private final int startBox;
    private final int endBox;

    public Snakes(){
        this.startBox = 20+(int)(Math.random()*80);
        this.endBox = this.startBox-((int)(Math.random()*10)+10);
    }
    public int getStartBox() {
        return startBox;
    }

    public int getEndBox() {
        return endBox;
    }
}
