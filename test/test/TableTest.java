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
        Assert.assertEquals(table.getTilesToReveal(), 9*9-10);
        Assert.assertEquals(table.getColumns(), 9);
        Assert.assertEquals(table.getRows(), 9);
        Assert.assertEquals(table.getFlags(), 0);
        Assert.assertEquals(table.getRevealedTiles(), 0);
        Assert.assertEquals(table.getNumberOfTiles(), 9*9);
        Assert.assertEquals(table.getNumberOfMines(), 10);
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
        Assert.assertEquals(revealedMines, 10);
    }
}
