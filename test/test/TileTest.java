package test;

import com.sun.source.tree.AssertTree;
import main.MineCounterView;
import main.Table;
import main.Tile;
import main.TileView;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

public class TileTest {
    Tile tile;
    Table table;

    @Before
    public void setUp() {
        table = new Table(9, 9, 0);
    }

    @Test
    public void tileInic() {
        tile = new Tile(table, false);
        Assert.assertFalse(tile.isMine());
        Assert.assertFalse(tile.isFlag());
        Assert.assertFalse(tile.isRevealed());
        Assert.assertEquals(0, tile.getNeighbouringMines());
    }

    @Test
    public void addNeighbourToTile() {
        tile = new Tile(table, false);
        Tile tile1, tile2;
        tile1 = new Tile(table, false);
        tile2 = new Tile(table, true);
        tile.addNeighbour(tile1);
        Assert.assertEquals(0, tile.getNeighbouringMines());
        tile.addNeighbour(tile2);
        Assert.assertEquals(1, tile.getNeighbouringMines());
    }

    @Test
    public void flagTile() {
        tile = new Tile(table, false);
        tile.setTileView(new TileView() {
            @Override
            public void onTileRevealed() {

            }

            @Override
            public void onFlagChange(boolean flagged) {

            }
        });
        tile.setMineCounterView(flags -> {
        });
        tile.toggleFlag();
        Assert.assertTrue(tile.isFlag());
        tile.toggleFlag();
        Assert.assertFalse(tile.isFlag());
    }

    @Test
    public void revealTile() {
        tile = new Tile(table, false);
        tile.setTileView(new TileView() {
            @Override
            public void onTileRevealed() {

            }

            @Override
            public void onFlagChange(boolean flagged) {

            }
        });
        tile.setMineCounterView(flags -> {
        });
        tile.reveal();
        Assert.assertTrue(tile.isRevealed());
        tile.reveal();
        Assert.assertTrue(tile.isRevealed());
    }

    @Test
    public void revealFlaggedTile() {
        tile = new Tile(table, false);
        tile.setTileView(new TileView() {
            @Override
            public void onTileRevealed() {

            }

            @Override
            public void onFlagChange(boolean flagged) {

            }
        });
        tile.setMineCounterView(flags -> {
        });
        tile.toggleFlag();
        tile.reveal();
        Assert.assertFalse(tile.isRevealed());
    }

    @Test
    public void revealNeighboursIfNoNeighbouringMine() {
        tile = new Tile(table, false);
        tile.setTileView(new TileView() {
            @Override
            public void onTileRevealed() {

            }

            @Override
            public void onFlagChange(boolean flagged) {

            }
        });
        tile.setMineCounterView(flags -> {
        });

        Tile tile1 = new Tile(table, false);
        tile1.setTileView(new TileView() {
            @Override
            public void onTileRevealed() {

            }

            @Override
            public void onFlagChange(boolean flagged) {

            }
        });
        tile1.setMineCounterView(flags -> {
        });

        Tile tile2 = new Tile(table, false);
        tile2.setTileView(new TileView() {
            @Override
            public void onTileRevealed() {

            }

            @Override
            public void onFlagChange(boolean flagged) {

            }
        });
        tile2.setMineCounterView(flags -> {
        });

        tile.addNeighbour(tile1);
        tile.addNeighbour(tile2);
        tile.reveal();
        Assert.assertTrue(tile1.isRevealed());
        Assert.assertTrue(tile2.isRevealed());
    }
    @Test
    public void revealNeighboursIfNeighbouringMine(){
        tile = new Tile(table, false);
        tile.setTileView(new TileView() {
            @Override
            public void onTileRevealed() {

            }

            @Override
            public void onFlagChange(boolean flagged) {

            }
        });
        tile.setMineCounterView(flags -> {});

        Tile tile1 = new Tile(table, false);
        tile1.setTileView(new TileView() {
            @Override
            public void onTileRevealed() {

            }

            @Override
            public void onFlagChange(boolean flagged) {

            }
        });
        tile1.setMineCounterView(flags -> {});

        Tile tile2 = new Tile(table, true);
        tile2.setTileView(new TileView() {
            @Override
            public void onTileRevealed() {

            }

            @Override
            public void onFlagChange(boolean flagged) {

            }
        });
        tile2.setMineCounterView(flags -> {});

        tile.addNeighbour(tile1);
        tile.addNeighbour(tile2);
        tile.reveal();
        Assert.assertFalse(tile1.isRevealed());
        Assert.assertFalse(tile2.isRevealed());
    }
}
