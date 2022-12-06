package test;

import main.Table;
import main.Tile;
import main.TileView;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import java.util.ArrayList;


public class TableTest {
    Table table;

    @Before
    public void setUp(){
        table = new Table(9,9,10);
    }

    @Test
    public void tableInic(){
        Assert.assertEquals(9*9-10, table.getTilesToReveal());
        Assert.assertEquals(9, table.getColumns());
        Assert.assertEquals(9, table.getRows());
        Assert.assertEquals(0, table.getFlags());
        Assert.assertEquals(0, table.getRevealedTiles());
        Assert.assertEquals(9*9, table.getNumberOfTiles());
        Assert.assertEquals(10, table.getNumberOfMines());
    }

    @Test
    public void revealTiles(){
        for (int i = 0; i < table.getRows(); i++) {
            for (int j = 0; j < table.getColumns(); j++) {
                Tile tile = table.getTile(i, j);
                tile.setTileView(new TileView() {
                    @Override
                    public void onTileRevealed() {

                    }

                    @Override
                    public void onFlagChange(boolean flagged) {

                    }
                });
            }
        }
        table.revealMines();
        int revealedMines = 0;
        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 9; j++){
                if(table.getTile(i,j).isRevealed() && table.getTile(i,j).isMine()){
                    revealedMines++;
                }
            }
        }
        Assert.assertEquals(10, revealedMines);
    }
}
