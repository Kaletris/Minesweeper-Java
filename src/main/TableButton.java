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
                setText(Integer.toString(tile.getNeighbouringMines()));
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
}
