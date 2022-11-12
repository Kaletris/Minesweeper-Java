import java.util.HashSet;

public class Tile {
    private boolean mine;
    private boolean revealed;
    private boolean flag;
    private HashSet<Tile> neighbours;

    private Table table;

    Tile(Table table){
        new Tile(table, false);
    }

    Tile(Table table, boolean mine){
        this.mine = mine;
        revealed = false;
        flag = false;
        neighbours = new HashSet<>();
        this.table = table;
    }

    public boolean isMine() {
        return mine;
    }

    public void reveal() {
        if(mine){

        }
        if(revealed){
            return;
        }
        revealed = true;
        if(neighbouringMines() == 0){
            for (Tile tile :
                    neighbours) {
                tile.reveal();
            }
        }
    }

    public boolean isFlag() {
        return flag;
    }

    public void toggleFlag() {
        flag = !flag;
    }

    public void addNeighbour(Tile tile){
        neighbours.add(tile);
    }

    public Integer neighbouringMines(){
        Integer mines = 0;
        for (Tile tile:
             neighbours) {
            if (tile.isMine()) {
                mines++;
            }
        }
        return mines;
    }

    public String draw(){
        if(!revealed){
            return " ";
        }
        if(flag){
            return "F";
        }
        if(mine){
            return  "X";
        }
        if(neighbouringMines() == 0){
            return " ";
        }
        return neighbouringMines().toString();
    }
}
