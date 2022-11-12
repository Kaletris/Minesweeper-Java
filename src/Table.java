import java.util.ArrayList;
import java.util.HashSet;

public class Table {
    HashSet<Tile> tiles;
    int numberOfMines;
    int columns;
    int rows;
    int tilesToReveal;

    Table(){
        new Table(8,10,10);
    }

    Table(int columns, int rows, int numberOfMines){
        tiles = new HashSet<>();
        this.columns = columns;
        this.rows = rows;
        this.numberOfMines = numberOfMines;
        generateTiles(columns, rows, numberOfMines);
    }

    private void generateTiles(int columns, int rows, int numberOfMines){
        for(int i = 0; i < numberOfMines; i++){
            tiles.add(new Tile(this, true));
        }

        for(int i = 0; i < columns * rows - numberOfMines; i++){
            tiles.add(new Tile(this));
        }
    }
}
