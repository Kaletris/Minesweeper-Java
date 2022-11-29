import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;

public class MainFrame extends JFrame {
    Table table;
    JPanel mainPanel;
    JPanel menuPanel;
    JPanel tablePanel;
    ArrayList<JToggleButton> tableButtons;
    JButton gameButton;

    MainFrame(){
        this(new Table());
    }
    MainFrame(Table table){
        super("Aknakereső");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.table = table;
        tableButtons = new ArrayList<>();
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        menuPanel = new JPanel();
        tablePanel = new JPanel();
        gameButton = new JButton("Options");
        GridLayout gridLayout = new GridLayout(table.getRows(), table.getColumns(), 0, 0);
        tablePanel.setLayout(gridLayout);

        add(mainPanel);
        mainPanel.add(menuPanel, BorderLayout.NORTH);
        menuPanel.add(gameButton);
        mainPanel.add(tablePanel, BorderLayout.CENTER);
        for(int i = 0; i < table.getColumns(); i++){
            for (int j = 0; j < table.getRows(); j++){
                Tile tile = table.getTile(i,j);
                TableButton tableButton = new TableButton(i, j, tile);
                tableButton.setPreferredSize(new Dimension(50,50));
                tableButton.setModel(new TableButtonModel());
                tile.setView(tableButton);
                tableButtons.add(tableButton);
                tablePanel.add(tableButton);
            }
        }

        pack();
        setVisible(true);
    }
}
