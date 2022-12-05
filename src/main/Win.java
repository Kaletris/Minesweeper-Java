package main;

import java.io.Serializable;

public class Win implements Serializable {
    String name;
    int time;
    Difficulty difficulty;
    
    public Win(Difficulty difficulty, String name, int time){
        this.difficulty = difficulty;
        this.name = name;
        this.time = time;
    }
}
