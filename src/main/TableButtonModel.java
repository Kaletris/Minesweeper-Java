package main;

import javax.swing.*;

public class TableButtonModel extends JToggleButton.ToggleButtonModel {
    @Override
    public void setSelected(boolean b) {
        super.setSelected(true);
    }
    public void mySetSelected(boolean b){
        super.setSelected(b);
    }
}
