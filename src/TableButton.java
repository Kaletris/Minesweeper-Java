import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TableButton extends JToggleButton implements TileView{
    int column;
    int row;
    Tile tile;
    public TableButton(int column, int row, Tile tile){
        this.column = column;
        this.row = row;
        this.tile = tile;
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    tile.toggleFlag();
                } else {
                    tile.reveal();
                }
            }
        });
    }

    public void onTileRevealed() {
        setText(tile.getNeighbouringMines().toString());
    }

    @Override
    public void onFlagChange(boolean flagged) {
        if (flagged) {
            setText("F");
        } else {
            setText("");
        }
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }
}
