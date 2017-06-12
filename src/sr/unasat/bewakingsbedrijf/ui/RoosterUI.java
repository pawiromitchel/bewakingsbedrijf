package sr.unasat.bewakingsbedrijf.ui;

import sr.unasat.bewakingsbedrijf.entities.Post;
import sr.unasat.bewakingsbedrijf.entities.Rooster;
import sr.unasat.bewakingsbedrijf.repositories.PostRepository;
import sr.unasat.bewakingsbedrijf.repositories.RoosterRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import java.util.Vector;

/**
 * Created by mitchel on 6/7/17.
 */
public class RoosterUI extends JPanel{
    private JList outputList;
    private RoosterRepository roosterRepo;

    private DefaultListModel listModel;

    private DefaultTableModel listTableModel;
    private JTable outputTable;
    private JButton selectAllButton, insertButton, updateButton, deleteButton, searchButton;
    private JPanel buttonPanel;
    private JTextField searchField;
    private TableRowSorter tableRowSorter;
    private JLabel searchLabel;
    //private JTextField searchBar;

    public void getData(){
        // Voer SelectAll functie uit.

        // sr.unasat.beroeps.product.entities.Rooster database instantie is alleen 1 keer nodig
        if (roosterRepo == null) {
            roosterRepo = new RoosterRepository();
        }

        // Initialiseer de roosterRepo alleen als het moet
        if (!roosterRepo.isInitialised()) {
            roosterRepo.initialize();
        }

        // Alleen als de rooster database is geinitialiseerd dan verder
        if (roosterRepo.isInitialised()) {
            java.util.List<Rooster> outputList = roosterRepo.selectAll();
            // Maak de lijst leeg als er elementen inzitten
            listModel.removeAllElements();

            // Parse de lijst
            for (Rooster rooster : outputList) {
                Rooster record = rooster;
                listModel.addElement(record.toString());
            }

            listTableModel = (DefaultTableModel) outputTable.getModel();
            listTableModel.setRowCount(0);
            // Parse de table
            for (Rooster rooster : outputList) {
                Rooster record = rooster;
                String[] colData = new String[6];
                colData[0] = Integer.valueOf(record.getId()).toString();
                if (record.getGebruiker() != null) {
                    colData[1] = record.getGebruiker().getVoornaam() + " " + record.getGebruiker().getAchternaam();
                }
                if (record.getPost() != null) {
                    colData[2] = record.getPost().getLocatie();
                }
                if (record.getShift() != null) {
                    colData[3] = record.getShift().getType();
                    colData[5] = record.getShift().getBegintijd() + " tot " + record.getShift().getEindtijd();
                }
                colData[4] = record.getDatum();
                listTableModel.addRow(colData);
            }
            outputTable.setModel(listTableModel);
        }
    }

    private void filterData(String query){
        DefaultTableModel tableModel = (DefaultTableModel) outputTable.getModel();

        tableRowSorter = new TableRowSorter<DefaultTableModel>(tableModel);
        outputTable.setRowSorter(tableRowSorter);

        tableRowSorter.setRowFilter(RowFilter.regexFilter(query));
    }

    public RoosterUI(){
        roosterRepo = new RoosterRepository();

        //Buttons initializen
        selectAllButton = new JButton("Refresh");
        selectAllButton.setBackground(Color.green);
        insertButton = new JButton("Insert");
        insertButton.setBackground(Color.lightGray);
        updateButton = new JButton("Update");
        updateButton.setBackground(Color.lightGray);
        deleteButton = new JButton("Delete");
        deleteButton.setBackground(Color.red);

        searchButton = new JButton("Search");

        buttonPanel = new JPanel(new GridLayout(4, 1, 5, 4));
        buttonPanel.add(selectAllButton);
        buttonPanel.add(insertButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        JPanel west = new JPanel(new GridBagLayout());
        west.add(buttonPanel);
        add(west, BorderLayout.WEST);

        // Creeer outputPanel en voeg toe
        JPanel outputPanel = new JPanel();
        outputPanel.setLayout(new BoxLayout(outputPanel, BoxLayout.Y_AXIS));
        add(outputPanel, BorderLayout.CENTER);

        //searchpanel
        //Initialize Searchfield and label
        searchLabel = new JLabel("Search");
        searchField = new JTextField("");
        searchField.setPreferredSize(new Dimension(200,20));
        JPanel searchPanel= new JPanel();
        searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        outputPanel.add(searchPanel);

        listModel = new DefaultListModel();
        outputList = new JList(listModel);
        outputList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        outputList.setSelectedIndex(0);
        outputList.setVisibleRowCount(5);
        JScrollPane listPanel = new JScrollPane(outputList);
        listPanel.setPreferredSize(new Dimension(800, 150));
        //outputPanel.add(listPanel);

        listTableModel = new DefaultTableModel();
        String[] colnames = {"Id", "Naam", "Post locatie", "Shift type", "Datum", "Tijd"};
        Vector colnamesV = new Vector(Arrays.asList(colnames));
        outputTable = new JTable(null, colnamesV);
        JScrollPane tablePanel = new JScrollPane(outputTable);
        tablePanel.setPreferredSize(new Dimension(800, 150));
        outputPanel.add(tablePanel);

        // run the method instantly
        getData();

        // Select listener
        selectAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg) {
                getData();
            }
        });

        // Insert listener
        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // open InsertRooster popup
                InsertRooster insertRooster = new InsertRooster();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                // get selected row id
                int id = Integer.parseInt(outputTable.getModel().getValueAt(outputTable.getSelectedRow(), 0).toString());

                // run the frame
                UpdateRooster updateRooster = new UpdateRooster(id);
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                // get selected value id
                int id = Integer.parseInt(outputTable.getModel().getValueAt(outputTable.getSelectedRow(), 0).toString());

                int dialogButton = JOptionPane.YES_NO_OPTION;
                int dialogResult = JOptionPane.showConfirmDialog (null, "Would you like to delete id: " + id + " ?","Confirm Deletion",dialogButton);
                if(dialogResult == JOptionPane.YES_OPTION){
                    // delete the specific id
                    RoosterRepository roosterRepository = new RoosterRepository();
                    roosterRepository.deleteRecord(id);

                    // refresh the table
                    getData();
                }
            }
        });

        searchField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {

            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {

            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {
                filterData(searchField.getText());

            }
        });

        /*searchBar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("jaja");
            }
        });*/
    }
}
