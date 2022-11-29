import java.util.HashSet;

public class Tile implements Cloneable{
    private boolean mine;
    private boolean revealed = false;
    private boolean flag = false;
    private HashSet<Tile> neighbours = new HashSet<>();
    private Table table;
    private Integer neighbouringMines = 0;
    private TileView view;

    Tile(Table table){
        this(table, false);
    }

    Tile(Table table, boolean mine){
        this.mine = mine;
        this.table = table;
        neighbouringMines = 0;
    }

    public void setView(TileView view) {
        this.view = view;
    }

    public void reveal() {
        if (revealed) {
            return;
        }

        revealed = true;
        view.onTileRevealed();

        if(neighbouringMines == 0) {
            for (Tile neighbour : neighbours) {
                neighbour.reveal();
            }
        }
    }

    public boolean isMine() {
        return mine;
    }

    public void toggleFlag() {
        flag = !flag;
    }

    public void addNeighbour(Tile tile){
        neighbours.add(tile);
        if(tile.isMine()){
            neighbouringMines++;
        }
    }

    public boolean isRevealed() {
        return revealed;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public Integer getNeighbouringMines() {
        return neighbouringMines;
    }
}
