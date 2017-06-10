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
    private JButton selectAllButton, insertButton, updateButton, deleteButton;
    private JPanel buttonPanel;
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

//        if (outputTable.getSelectedRow() > 0) {
//            Rooster rooster = (Rooster) listTableModel.getDataVector().elementAt(outputTable.getSelectedRow());
//        }

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
                String[] colData = new String[4];
                colData[0] = Integer.valueOf(record.getId()).toString();
                if (record.getGebruiker() != null) {
                    colData[1] = record.getGebruiker().getVoornaam();
                }
                if (record.getPost() != null) {
                    colData[2] = record.getPost().getLocatie();
                }
                colData[3] = record.getDatum();
                listTableModel.addRow(colData);
            }
            outputTable.setModel(listTableModel);
        }
    }
    public RoosterUI(){
        roosterRepo = new RoosterRepository();

        //Buttons initializen
        selectAllButton = new JButton("Select All");
        selectAllButton.setBackground(Color.green);
        insertButton = new JButton("Insert");
        insertButton.setBackground(Color.lightGray);
        updateButton = new JButton("Update");
        updateButton.setBackground(Color.lightGray);
        deleteButton = new JButton("Delete");
        deleteButton.setBackground(Color.red);

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

        listModel = new DefaultListModel();
        outputList = new JList(listModel);
        outputList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        outputList.setSelectedIndex(0);
        outputList.setVisibleRowCount(5);
        JScrollPane listPanel = new JScrollPane(outputList);
        listPanel.setPreferredSize(new Dimension(800, 150));
        //outputPanel.add(listPanel);

        listTableModel = new DefaultTableModel();
        String[] colnames = {"id", "voornaam", "post", "locatie"};
        Vector colnamesV = new Vector(Arrays.asList(colnames));
        outputTable = new JTable(null, colnamesV);
        JScrollPane tablePanel = new JScrollPane(outputTable);
        tablePanel.setPreferredSize(new Dimension(800, 150));
        outputPanel.add(tablePanel);

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
                UpdateRooster updateRooster = new UpdateRooster(id);
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                // get selected value id
                int id = Integer.parseInt(outputTable.getModel().getValueAt(outputTable.getSelectedRow(), 0).toString());

                RoosterRepository roosterRepository = new RoosterRepository();
                roosterRepository.deleteRecord(id);

                // refresh the table
                getData();
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
