package sr.unasat.bewakingsbedrijf.ui;

import sr.unasat.bewakingsbedrijf.entities.Rol;
import sr.unasat.bewakingsbedrijf.repositories.RolRepository;

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
 * Created by Patrice on 6/11/2017.
 */
public class RolUI extends JPanel {
    private JButton selectAllButton, insertButton, updateButton, deleteButton, searchButton;
    private JList outputList;
    private RolRepository rolRepo;
    private JPanel buttonPanel;
    //private JTextField searchField;

    private DefaultListModel listModel;

    private DefaultTableModel listTableModel;
    private JTable outputTable;

    private JTextField searchField;
    private TableRowSorter tableRowSorter;
    private JLabel searchLabel;

    private void getData(){
        // Voer SelectAll functie uit.

        // sr.unasat.beroeps.product.entities.Rooster database instantie is alleen 1 keer nodig
        if (rolRepo == null) {
            rolRepo = new RolRepository();
        }

        // Initialiseer de roosterRepo alleen als het moet
        if (!rolRepo.isInitialised()) {
            rolRepo.initialize();
        }


        // Alleen als de rooster database is geinitialiseerd dan verder
        if (rolRepo.isInitialised()) {
            java.util.List<Rol> outputList = rolRepo.selectAll();

            // Maak de lijst leeg als er elementen inzitten
            listModel.removeAllElements();

            // Parse de lijst
            for (Rol rol : outputList) {
                Rol record = rol;
                listModel.addElement(record.toString());
            }

            listTableModel = (DefaultTableModel) outputTable.getModel();
            listTableModel.setRowCount(0);
            // Parse de table
            for (Rol rol : outputList) {
                Rol record = rol;
                String[] colData = new String[2];
                colData[0] = Integer.valueOf(record.getId()).toString();
                colData[1] = record.getNaam();
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

    public RolUI(){
        rolRepo = new RolRepository();

        //Buttons initializen
        selectAllButton = new JButton("Select All");
        selectAllButton.setBackground(Color.green);
        insertButton = new JButton("Insert");
        insertButton.setBackground(Color.lightGray);
        updateButton = new JButton("Update");
        updateButton.setBackground(Color.lightGray);
        deleteButton = new JButton("Delete");
        deleteButton.setBackground(Color.red);
        searchButton = new JButton("Search");

        updateButton.setToolTipText("Selecteer een record voor update");
        deleteButton.setToolTipText("Selecteer een record om te verwijderen");

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
        String[] colnames = {"Id", "Rol"};
        Vector colnamesV = new Vector(Arrays.asList(colnames));
        outputTable = new JTable(null, colnamesV);
        JScrollPane tablePanel = new JScrollPane(outputTable);
        tablePanel.setPreferredSize(new Dimension(800, 150));
        outputPanel.add(tablePanel);

        getData();

        // Select listener
        selectAllButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg) {
                getData();

            }
        });

        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InsertRol insertRol= new InsertRol();

            }

        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int id = Integer.parseInt(outputTable.getModel().getValueAt(outputTable.getSelectedRow(), 0).toString());
                int dialogButton = JOptionPane.YES_NO_OPTION;
                int dialogResult = JOptionPane.showConfirmDialog (null, "Would you like to delete id: " + id + " ?","Confirm Deletion",dialogButton);
                if(dialogResult == JOptionPane.YES_OPTION) {
                    RolRepository rolRepository = new RolRepository();
                    rolRepository.deleteRecord(id);

                    getData();
                }
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(outputTable.getModel().getValueAt(outputTable.getSelectedRow(), 0).toString());
                UpdateRol updateRol = new UpdateRol(id);
            }
        });


        // Search keylistener
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


       /* searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });*/

    }
}
