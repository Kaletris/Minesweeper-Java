package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Table {
    //tiles: [row][column]
    private final ArrayList<ArrayList<Tile>> tiles;
    private final int numberOfTiles;
    private final int numberOfMines;
    private final int rows;
    private final int columns;
    private final int tilesToReveal;
    private int revealedTiles = 0;
    private int flags = 0;
    private MainFrame mainFrame;

    public Table() {
        this(9, 9, 10);
    }

    public Table(int rows, int columns, int numberOfMines) {
        tiles = new ArrayList<>(rows);
        for (int i = 0; i < rows; i++) {
            tiles.add(new ArrayList<>(columns));
        }
        this.rows = rows;
        this.columns = columns;
        this.numberOfMines = numberOfMines;
        numberOfTiles = rows * columns;
        tilesToReveal = numberOfTiles - numberOfMines;
        generateTiles();
        setNeighbours();
    }

    private void generateTiles() {
        ArrayList<Tile> tileDeck = new ArrayList<>(rows * columns);

        for (int i = 0; i < rows * columns - numberOfMines; i++) {
            tileDeck.add(new Tile(this));
        }

        for (int i = 0; i < numberOfMines; i++) {
            tileDeck.add(new Tile(this, true));
        }

        for (int i = 0; i < numberOfMines; i++) {
            Random rand = new Random();
            int randInt = rand.nextInt(numberOfTiles - i);
            int current = numberOfTiles - 1 - i;
            Collections.swap(tileDeck, current, randInt);
        }


        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                tiles.get(i).add(tileDeck.get(i * columns + j));
            }
        }
    }
    public Tile getTile ( int row, int column){
            return tiles.get(row).get(column);
        }

    private void setNeighbours(){
        int lastrow = rows - 1;
        int lastcolumn = columns - 1;

        for(int i = 0; i < rows ; i++){
            for (int j = 0; j < columns; j++){
                Tile middle = tiles.get(i).get(j);
                if (j != 0) {
                    middle.addNeighbour(tiles.get(i).get(j - 1));
                    if (i != 0) {
                        middle.addNeighbour(tiles.get(i - 1).get(j - 1));
                    }

                    if (i != lastrow) {
                        middle.addNeighbour(tiles.get(i + 1).get(j - 1));
                    }
                }
                if(j != lastcolumn){
                    middle.addNeighbour(tiles.get(i).get(j + 1));
                    if(i != 0){
                        middle.addNeighbour(tiles.get(i - 1).get(j + 1));
                    }

                    if(i != lastrow){
                        middle.addNeighbour(tiles.get(i + 1).get(j + 1));
                    }
                }

                if(i != 0) {
                    middle.addNeighbour(tiles.get(i - 1).get(j));
                }
                if(i != lastrow) {
                    middle.addNeighbour(tiles.get(i + 1).get(j));
                }
            }
        }
    }

    public void revealMines(){
        for (ArrayList<Tile> tileArrayList :
                tiles) {
            for (Tile tile :
                    tileArrayList) {
                if (tile.isMine()) {
                    tile.forceReveal();
                }
            }
        }
    }

    public void lostGame(){
        mainFrame.lostGame();
    }

    public void wonGame(){
        mainFrame.wonGame();
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public int getNumberOfTiles() {
        return numberOfTiles;
    }

    public int getTilesToReveal() {
        return tilesToReveal;
    }

    public int getFlags() {
        return flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    public int getRevealedTiles() {
        return revealedTiles;
    }

    public void setRevealedTiles(int revealedTiles) {
        this.revealedTiles = revealedTiles;
    }

    public void setMainFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public int getNumberOfMines() {
        return numberOfMines;
    }
}
