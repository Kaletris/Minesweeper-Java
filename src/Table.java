import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;

public class Table {
    //tiles: [row][column]
    private ArrayList<ArrayList<Tile>> tiles;
    private final int numberOfTiles;
    private final int numberOfMines;
    private final int rows;
    private final int columns;
    private int tilesToReveal;

    Table() {
        this(8, 10, 10);
    }

    Table(int rows, int columns, int numberOfMines) {
        tiles = new ArrayList<>(rows);
        for (int i = 0; i < rows; i++) {
            tiles.add(new ArrayList<>(columns));
        }
        this.rows = rows;
        this.columns = columns;
        this.numberOfMines = numberOfMines;
        numberOfTiles = rows * columns;
        tilesToReveal = numberOfTiles - numberOfMines;
        generateTiles(numberOfMines);
        setNeighbours();
    }

    private void generateTiles(int numberOfMines) {
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
                tiles.get(i).add(tileDeck.get(i * rows + j));
            }
        }
    }
        public Tile getTile ( int row, int column){
            return tiles.get(row).get(column);
        }

    private void setNeighbours(){
        int lastrow = rows - 1;
        int lastcolumn = columns - 1;

        //4 corner
        Tile topLeft = tiles.get(0).get(0);
        topLeft.addNeighbour(tiles.get(1).get(0));
        topLeft.addNeighbour(tiles.get(1).get(1));
        topLeft.addNeighbour(tiles.get(0).get(1));

        Tile topRight = tiles.get(0).get(lastcolumn);
        topRight.addNeighbour(tiles.get(1).get(lastcolumn   ));
        topRight.addNeighbour(tiles.get(1).get(lastcolumn - 1));
        topRight.addNeighbour(tiles.get(0).get(lastcolumn - 1));

        Tile bottomLeft = tiles.get(lastrow).get(0);
        bottomLeft.addNeighbour(tiles.get(lastrow - 1).get(0));
        bottomLeft.addNeighbour(tiles.get(lastrow - 1).get(1));
        bottomLeft.addNeighbour(tiles.get(lastrow    ).get(1));

        Tile bottomRight = tiles.get(lastrow).get(lastcolumn);
        bottomRight.addNeighbour(tiles.get(lastrow - 1).get(lastcolumn    ));
        bottomRight.addNeighbour(tiles.get(lastrow - 1).get(lastcolumn - 1));
        bottomRight.addNeighbour(tiles.get(lastrow    ).get(lastcolumn - 1));

        //4 sides
        for(int i = 1; i < lastcolumn - 1; i++){
            Tile top = tiles.get(0).get(i);
            top.addNeighbour(tiles.get(0).get(i - 1));
            top.addNeighbour(tiles.get(1).get(i - 1));
            top.addNeighbour(tiles.get(1).get(i    ));
            top.addNeighbour(tiles.get(1).get(i + 1));
            top.addNeighbour(tiles.get(0).get(i + 1));
        }

        for(int i = 1; i < lastcolumn - 1; i++){
            Tile bottom = tiles.get(lastrow).get(i);
            bottom.addNeighbour(tiles.get(lastrow    ).get(i - 1));
            bottom.addNeighbour(tiles.get(lastrow - 1).get(i - 1));
            bottom.addNeighbour(tiles.get(lastrow - 1).get(i    ));
            bottom.addNeighbour(tiles.get(lastrow - 1).get(i + 1));
            bottom.addNeighbour(tiles.get(lastrow    ).get(i + 1));
        }

        for(int i = 1; i < lastrow - 1; i++){
            Tile left = tiles.get(i).get(0);
            left.addNeighbour(tiles.get(i - 1).get(0));
            left.addNeighbour(tiles.get(i - 1).get(1));
            left.addNeighbour(tiles.get(i    ).get(1));
            left.addNeighbour(tiles.get(i + 1).get(1));
            left.addNeighbour(tiles.get(i + 1).get(0));
        }

        for(int i = 1; i < lastrow - 1; i++){
            Tile right = tiles.get(lastrow).get(i);
            right.addNeighbour(tiles.get(i - 1).get(lastcolumn    ));
            right.addNeighbour(tiles.get(i - 1).get(lastcolumn - 1));
            right.addNeighbour(tiles.get(i    ).get(lastcolumn - 1));
            right.addNeighbour(tiles.get(i + 1).get(lastcolumn - 1));
            right.addNeighbour(tiles.get(i + 1).get(lastcolumn    ));
        }


        //middle
        for(int i = 1; i < lastrow - 1; i++){
            for (int j = 1; j < lastcolumn - 1; j++){
                Tile middle = tiles.get(i).get(j);
                middle.addNeighbour(tiles.get(i - 1).get(j - 1));
                middle.addNeighbour(tiles.get(i    ).get(j - 1));
                middle.addNeighbour(tiles.get(i + 1).get(j - 1));
                middle.addNeighbour(tiles.get(i - 1).get(j    ));
                middle.addNeighbour(tiles.get(i + 1).get(j    ));
                middle.addNeighbour(tiles.get(i - 1).get(j + 1));
                middle.addNeighbour(tiles.get(i    ).get(j + 1));
                middle.addNeighbour(tiles.get(i + 1).get(j + 1));
            }
        }
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
}
