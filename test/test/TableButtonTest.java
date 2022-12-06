package test;

import main.Table;
import main.TableButton;
import main.Tile;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TableButtonTest {
    TableButton tableButton;
    Table table;
    @Before
    public void setUp(){
        table = new Table();
        tableButton = new TableButton(0,0,new Tile(table, false));
    }

    @Test
    public void onFlagged(){
        tableButton.onFlagChange(true);
        Assert.assertEquals("F", tableButton.getText());
        tableButton.onFlagChange(false);
        Assert.assertEquals("", tableButton.getText());
    }
}
