import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TableButton extends JToggleButton implements TileView{
    int row;
    int column;
    Tile tile;
    public TableButton(int row, int column, Tile tile){
        this.row = row;
        this.column = column;
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
        if (tile.isMine()) {
            setText("*");
        } else {
            if(tile.getNeighbouringMines() == 0){
                setText(" ");
            } else {
                setText(tile.getNeighbouringMines().toString());
            }
        }
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

    public Tile getTile() {
        return tile;
    }
}
