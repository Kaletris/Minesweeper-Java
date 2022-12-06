package test;

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
    public void setUp(){
        table = new Table(9,9,10);
    }


}
