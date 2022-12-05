package main;

import javax.swing.*;
import java.awt.event.*;
import static javax.swing.SwingUtilities.*;

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
            public void mouseReleased(MouseEvent e) {
                if(!isEnabled()){
                    return;
                }
                if (isLeftMouseButton(e)) {
                    if(!(tile.isFlag())){
                        tile.reveal();
                    }
                } else {
                    if (isRightMouseButton(e)) {
                        if(!(tile.isRevealed())){
                            tile.toggleFlag();
                        }
                    }
                }
            }
        });
    }

    public void onTileRevealed() {
        if(tile.isFlag()){
            return;
        }
        setSelected(true);
        if (tile.isMine()) {
            setText("X");
        } else {
            if(tile.getNeighbouringMines() == 0){
                setText(" ");
            } else {
                /*
                switch (tile.getNeighbouringMines()) {
                    case 1: setForeground(Color.BLUE);
                        break;
                    case 2: setForeground(Color.GREEN);
                        break;
                    case 3: setForeground(Color.RED);
                        break;
                    case 4: setForeground(Color.YELLOW);
                        break;
                    case 5: setForeground(Color.CYAN);
                        break;
                    case 6: setForeground(Color.MAGENTA);
                        break;
                    case 7: setForeground(Color.PINK);
                        break;
                    case 8: setForeground(Color.BLACK);
                        break;
                    default:
                        break;
                }

                 */
                setText(tile.getNeighbouringMines().toString());
                //setForeground(Color.BLACK);
            }
        }
    }

    @Override
    public void onFlagChange(boolean flagged) {
        if(tile.isRevealed()){
            return;
        }
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
