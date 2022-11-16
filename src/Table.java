import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class Table {
    //tiles: [column][row]
    private ArrayList<ArrayList<Tile>> tiles;
    private int numberOfTiles;
    private int numberOfMines;
    private int columns;
    private int rows;
    private int tilesToReveal;

    Table(){
        new Table(8,10,10);
    }

    Table(int columns, int rows, int numberOfMines){
        tiles = new ArrayList<>(columns);
        for (int i = 0; i < columns; i++){
            tiles.add(new ArrayList<>(rows));
        }
        this.columns = columns;
        this.rows = rows;
        this.numberOfMines = numberOfMines;
        numberOfTiles = columns * rows;
        tilesToReveal = numberOfTiles - numberOfMines;
        generateTiles();
        placeMines();
        setNeighbours();
    }

    private void generateTiles(){
        for(int i = 0; i < columns; i++){
            for ( int j = 0; j < rows; j++){
                tiles.get(i).add(new Tile(this));
            }
        }
    }

    private void placeMines(){
        //creating copy of tiles
        ArrayList<ArrayList<Tile>> tilesCopy = new ArrayList<>(columns);
        for (int i = 0; i < columns; i++){
            tiles.add(new ArrayList<>(rows));
        }
        for(int i = 0; i < columns; i++){
            for ( int j = 0; j < rows; j++){
                try {
                    tiles.get(i).add((Tile)(tiles.get(i).get(j).clone()));
                } catch (CloneNotSupportedException e){
                    e.printStackTrace();
                }
            }
        }
        //shuffle last numberOfMines elements
        Random random = new Random();
        for(int i = 0; i < numberOfMines; i++){
            int currentColumn = (numberOfTiles - i) / columns;
            int currentRow = (numberOfTiles - i) % columns;
            Tile currentTile = tilesCopy.get(currentColumn).get(currentRow);
            int rand = random.nextInt(numberOfTiles - i);
            int randColumn = rand / columns;
            int randRow = rand % rows;
            Tile randTile = tilesCopy.get(randColumn).get(randRow);

            Tile tmp;
            tmp = currentTile;
            currentTile = randTile;
            randTile = tmp;
        }
        //set last numberOfMines element to have mines
        for(int i = 0; i < numberOfMines; i++){
            int currentColumn = (numberOfTiles - i) / columns;
            int currentRow = (numberOfTiles - i) % columns;
            tilesCopy.get(currentColumn).get(currentRow).setMine(true);
        }
    }

    private void setNeighbours(){
        int lastColumn = columns - 1;
        int lastRow = rows - 1;
        //4 corner
        Tile topLeft = tiles.get(0).get(0);
        topLeft.addNeighbour(tiles.get(1).get(0));
        topLeft.addNeighbour(tiles.get(1).get(1));
        topLeft.addNeighbour(tiles.get(0).get(1));

        Tile topRight = tiles.get(lastColumn).get(0);
        topRight.addNeighbour(tiles.get(lastColumn - 1).get(0));
        topRight.addNeighbour(tiles.get(lastColumn - 1).get(1));
        topRight.addNeighbour(tiles.get(lastColumn    ).get(1));

        Tile bottomLeft = tiles.get(0).get(lastRow);
        bottomLeft.addNeighbour(tiles.get(1).get(lastRow   ));
        bottomLeft.addNeighbour(tiles.get(1).get(lastRow - 1));
        bottomLeft.addNeighbour(tiles.get(0).get(lastRow - 1));

        Tile bottomRight = tiles.get(lastColumn).get(lastRow);
        bottomRight.addNeighbour(tiles.get(lastColumn - 1).get(lastColumn    ));
        bottomRight.addNeighbour(tiles.get(lastColumn - 1).get(lastColumn - 1));
        bottomRight.addNeighbour(tiles.get(lastColumn    ).get(lastColumn - 1));

        //4 sides
        for(int i = 1; i < lastColumn - 1; i++){
            Tile top = tiles.get(i).get(0);
            top.addNeighbour(tiles.get(i - 1).get(0));
            top.addNeighbour(tiles.get(i - 1).get(1));
            top.addNeighbour(tiles.get(i    ).get(1));
            top.addNeighbour(tiles.get(i + 1).get(1));
            top.addNeighbour(tiles.get(i + 1).get(0));
        }

        for(int i = 1; i < lastColumn - 1; i++){
            Tile bottom = tiles.get(i).get(lastRow);
            bottom.addNeighbour(tiles.get(i - 1).get(lastRow    ));
            bottom.addNeighbour(tiles.get(i - 1).get(lastRow - 1));
            bottom.addNeighbour(tiles.get(i    ).get(lastRow - 1));
            bottom.addNeighbour(tiles.get(i + 1).get(lastRow - 1));
            bottom.addNeighbour(tiles.get(i + 1).get(lastRow    ));
        }

        for(int i = 1; i < lastRow - 1; i++){
            Tile left = tiles.get(0).get(i);
            left.addNeighbour(tiles.get(0).get(i - 1));
            left.addNeighbour(tiles.get(1).get(i - 1));
            left.addNeighbour(tiles.get(1).get(i    ));
            left.addNeighbour(tiles.get(1).get(i + 1));
            left.addNeighbour(tiles.get(0).get(i + 1));
        }

        for(int i = 1; i < lastRow - 1; i++){
            Tile right = tiles.get(lastColumn).get(i);
            right.addNeighbour(tiles.get(lastColumn    ).get(i - 1));
            right.addNeighbour(tiles.get(lastColumn - 1).get(i - 1));
            right.addNeighbour(tiles.get(lastColumn - 1).get(i    ));
            right.addNeighbour(tiles.get(lastColumn - 1).get(i + 1));
            right.addNeighbour(tiles.get(lastColumn    ).get(i + 1));
        }

        //middle
        for(int i = 1; i < lastColumn - 1; i++){
            for (int j = 1; j < lastRow - 1; j++){
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
}
