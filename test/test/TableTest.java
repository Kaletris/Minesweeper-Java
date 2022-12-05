package test;

import main.Difficulty;
import main.Table;
import org.junit.Before;
import org.junit.Test;

public class TableTest {
    Table table;

    @Before
    public void setUp(){
        table = new Table(9,9,10);
    }
}
