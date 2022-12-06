package main;

import java.util.HashSet;

public class Tile implements Cloneable{
    private  boolean mine;
    private boolean revealed = false;
    private boolean flag = false;
    private  HashSet<Tile> neighbours = new HashSet<>();
    private  Table table;
    private Integer neighbouringMines;
    private TileView tileView;
    private MineCounterView mineCounterView;

    public Tile(Table table){
        this(table, false);
    }

    public Tile(Table table, boolean mine){
        this.mine = mine;
        this.table = table;
        neighbouringMines = 0;
    }

    public void setTileView(TileView tileView){
        this.tileView = tileView;
    }
    public void forceReveal(){
        revealed = true;
        if(flag){
            toggleFlag();
        }
        tileView.onTileRevealed();
    }

    public void reveal() {
        if(mine){
            table.lostGame();
        }
        if (revealed || flag) {
            return;
        }
        revealed = true;
        if(neighbouringMines == 0) {
            for (Tile neighbour : neighbours) {
                neighbour.reveal();
            }
        }
        tileView.onTileRevealed();
        int revealedTiles = table.getRevealedTiles();
        revealedTiles++;
        table.setRevealedTiles(revealedTiles);
        if(revealedTiles == table.getTilesToReveal()){
            table.wonGame();
        }
    }

    public boolean isMine() {
        return mine;
    }

    public void toggleFlag() {
        if(revealed){
            return;
        }
        flag = !flag;
        if(flag){
            table.setFlags(table.getFlags() + 1);
        }
        else {
            table.setFlags(table.getFlags() - 1);
        }
        mineCounterView.onFlagChange(table.getNumberOfTiles() - table.getTilesToReveal() - table.getFlags());
        tileView.onFlagChange(flag);
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

    public boolean isFlag(){
        return flag;
    }

    public void setMineCounterView(MineCounterView mineCounterView) {
        this.mineCounterView = mineCounterView;
    }
}
