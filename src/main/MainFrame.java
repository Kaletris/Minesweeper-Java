package main;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;

public class MainFrame extends JFrame {
    private final Table table;
    private final ArrayList<TableButton> tableButtons;
    private Counter counter;
    private JLabel timeLabel;
    Difficulty difficulty;
    private final JLabel minesLeftLabel;
    private ArrayList<Win> wins;


    public MainFrame(int row, int column, int numberOfMines, Difficulty difficulty){
        this(new Table(row, column, numberOfMines), difficulty);
    }
    public MainFrame(Table table, Difficulty difficulty){
        super("Minesweeper");
        this.table = table;
        table.setMainFrame(this);
        this.difficulty = difficulty;

        timeLabel = new JLabel();
        counter = new Counter(() -> timeLabel.setText("Time: " + String.format("%02d", counter.getCounter() / 60) + ":" + String.format("%02d", counter.getCounter() % 60)));
        int remainingMines = table.getNumberOfTiles() - table.getTilesToReveal();
        tableButtons = new ArrayList<>();
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        JPanel menuPanel = new JPanel();
        JPanel tablePanel = new JPanel();
        JButton rankingsButton = new JButton("Rankings");
        rankingsButton.addActionListener(e -> createRankingsTable());
        timeLabel = new JLabel();
        GridLayout gridLayout = new GridLayout(table.getRows(), table.getColumns(), 0, 0);
        tablePanel.setLayout(gridLayout);

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu(difficulty.toString());
        JMenuItem easyDifficulty = new JMenuItem("EASY");
        easyDifficulty.addActionListener(e -> {
                setVisible(false);
                new MainFrame(9,9,10,Difficulty.EASY);
            }
        );

        JMenuItem mediumDifficulty = new JMenuItem("MEDIUM");
        mediumDifficulty.addActionListener(e -> {
                setVisible(false);
                new MainFrame(16,16,40,Difficulty.MEDIUM);
            }
        );

        JMenuItem hardDifficulty = new JMenuItem("HARD");
        hardDifficulty.addActionListener(e -> {
                setVisible(false);
                new MainFrame(16,30,99,Difficulty.HARD);
            }
        );

        minesLeftLabel = new JLabel("Remaining mines: " + remainingMines);
        JLabel difficultyLabel = new JLabel("Difficulty:");

        add(mainPanel);
        mainPanel.add(menuPanel, BorderLayout.NORTH);
        menuPanel.add(timeLabel);
        menuPanel.add(difficultyLabel);
        menu.add(easyDifficulty);
        menu.add(mediumDifficulty);
        menu.add(hardDifficulty);
        menuBar.add(menu);
        menuPanel.add(menuBar);
        menuPanel.add(minesLeftLabel);
        menuPanel.add(rankingsButton);

        mainPanel.add(tablePanel, BorderLayout.CENTER);

        for (int i = 0; i < table.getRows(); i++){
            for (int j = 0; j < table.getColumns(); j++){
                Tile tile = table.getTile(i, j);
                TableButton tableButton = new TableButton(i, j, tile);
                tableButton.setPreferredSize(new Dimension(50,50));
                tableButton.setModel(new TableButtonModel());
                tile.setTileView(tableButton);
                tableButtons.add(tableButton);
                tablePanel.add(tableButton);
                tableButton.setFocusable(false);
                tile.setMineCounterView(flags -> minesLeftLabel.setText("Remaining mines: " + flags));
            }
        }

        counter.start();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(table.getColumns()*50, table.getRows()*50+20));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void lostGame(){
        counter.end();
        table.revealMines();
        for (TableButton tableButton :
                tableButtons) {
            tableButton.setEnabled(false);
        }
        JOptionPane.showMessageDialog(getParent(), "You lost");
    }
    public void wonGame(){
        int time = counter.getCounter();
        counter.end();
        for (TableButton tableButton :
                tableButtons) {
            tableButton.setEnabled(false);
        }
        String name;
        name = JOptionPane.showInputDialog(getParent(), "You are winner, you may save your success", "Anonymus");
        if(name == null){
            return;
        }
        Win win = new Win(difficulty, name, time);

        deSerializeWins();
        wins.add(win);
        wins.sort(Comparator.comparingInt(o -> o.time));
        wins.sort(Comparator.comparing(o -> o.difficulty));
        serializeWins();
    }

    private void serializeWins(){
        FileOutputStream fos;
        ObjectOutputStream oos = null;
        try{
            fos = new FileOutputStream("Rankings.txt");
            oos = new ObjectOutputStream(fos);
            oos.writeObject(wins);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(oos != null){
                    oos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void deSerializeWins(){
        FileInputStream fin;
        ObjectInputStream ois = null;
        try{
            fin = new FileInputStream("Rankings.txt");
            ois = new ObjectInputStream(fin);
            wins = (ArrayList<Win>) ois.readObject();
        } catch (FileNotFoundException e){
            wins = new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if(ois != null) {
                    ois.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void createRankingsTable(){
        JFrame frame = new JFrame("Rankings.txt");
        frame.setLayout(new BorderLayout());
        frame.setPreferredSize(new Dimension(250, 250));
        deSerializeWins();
        int rowCount = wins.size();
        String[][] data = new String[rowCount][3];
        int i = 0;
        for (Win win :
                wins) {
            data[i][0] = win.difficulty.toString();
            data[i][1] = win.name;
            data[i][2] = String.format("%02d", win.time / 60) + ":" + String.format("%02d", win.time % 60);
            i++;
        }
        String[] column = {"Difficulty", "Name", "Time"};
        JTable rankingsTable = new JTable(data, column);
        JScrollPane scrollPane = new JScrollPane(rankingsTable);

        frame.setContentPane(scrollPane);

        frame.setDefaultCloseOperation(HIDE_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
