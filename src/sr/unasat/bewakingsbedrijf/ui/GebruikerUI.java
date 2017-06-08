package sr.unasat.bewakingsbedrijf.ui;

import sr.unasat.bewakingsbedrijf.entities.Gebruiker;
import sr.unasat.bewakingsbedrijf.entities.Post;
import sr.unasat.bewakingsbedrijf.repositories.GebruikerRepository;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

/**
 * Created by Jonathan on 6/7/2017.
 */
public class GebruikerUI extends JPanel{
    private JList outputList;
    private GebruikerRepository gebruikerRepo;
    private DefaultListModel listModel;
    private DefaultTableModel listTableModel;
    private JTable outputTable;
    private JButton selectAllButton, insertButton, updateButton, deleteButton;
    private JPanel buttonPanel;

    public GebruikerUI(){
        gebruikerRepo = new GebruikerRepository();

        //Buttons initializen
        selectAllButton = new JButton("Select All");
        insertButton = new JButton("Insert");
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");

        buttonPanel = new JPanel(new GridLayout(4,1,5,4));
        buttonPanel.add(selectAllButton);
        buttonPanel.add(insertButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        JPanel west = new JPanel(new GridBagLayout());
        west.add(buttonPanel);
        add(west, BorderLayout.WEST);

        //	Creeer outputPanel en voeg toe
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
        String[] colnames = {"id", "naam", "leeftijd", "adres", "studierichting", "cijfergemiddelde"};
        Vector colnamesV = new Vector(Arrays.asList(colnames));
        outputTable = new JTable(null, colnamesV);
        JScrollPane tablePanel = new JScrollPane(outputTable);
        tablePanel.setPreferredSize(new Dimension(800, 150));
        outputPanel.add(tablePanel);



        // Select listener

        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame insert = new JFrame("Insert");
                insert.setLayout(new BorderLayout());
                insert.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                insert.setSize(500, 500);
                insert.setVisible(true);

                JTextField nameField = new JTextField();
                JLabel nameLabel = new JLabel("Name");
                JPanel labelPanel = new JPanel();
                JPanel textPanel = new JPanel();
                nameField.setEditable(true);
                nameField.setBounds(50, 50, 10, 200);
                labelPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
                textPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
                labelPanel.add(nameLabel);
                textPanel.add(nameField);
                add(textPanel, BorderLayout.CENTER);
                add(labelPanel, BorderLayout.EAST);
                insert.add(textPanel);
                insert.add(labelPanel);

            }
        });

        // Select listener
        selectAllButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg) {
                // Voer SelectAll functie uit.

                // sr.unasat.beroeps.product.entities.Rooster database instantie is alleen 1 keer nodig
                if (gebruikerRepo == null) {
                    gebruikerRepo = new GebruikerRepository();
                }

                // Initialiseer de roosterRepo alleen als het moet
                if (!gebruikerRepo.isInitialised()) {
                    gebruikerRepo.initialize();
                }

                if (outputTable.getSelectedRow() > 0) {
                    Post post = (Post) listTableModel.getDataVector().elementAt(outputTable.getSelectedRow());
                    System.out.println(post);
                }

                // Alleen als de rooster database is geinitialiseerd dan verder
                if (gebruikerRepo.isInitialised()) {
                    List<Gebruiker> outputList = gebruikerRepo.selectAll();

                    // Maak de lijst leeg als er elementen inzitten
                    listModel.removeAllElements();

                    // Parse de lijst
                    for (Gebruiker gebruiker : outputList) {
                        Gebruiker record = gebruiker;
                        listModel.addElement(record.toString());
                    }

                    listTableModel = (DefaultTableModel) outputTable.getModel();
                    listTableModel.setRowCount(0);
                    // Parse de table
                    for (Gebruiker gebruiker : outputList) {
                        Gebruiker record = gebruiker;
                        String[] colData = new String[2];
                        colData[0] = Integer.valueOf(record.getId()).toString();
                        //colData[1] = record.getLocatie();
                        listTableModel.addRow(colData);
                    }
                    outputTable.setModel(listTableModel);
                }
            }
        });
    }
}
