package sr.unasat.bewakingsbedrijf.ui;

import sr.unasat.bewakingsbedrijf.entities.Gebruiker;
import sr.unasat.bewakingsbedrijf.entities.Post;
import sr.unasat.bewakingsbedrijf.repositories.GebruikerRepository;
import sr.unasat.bewakingsbedrijf.repositories.PostRepository;

import javax.swing.*;
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
    private JButton selectAllButton
            , insertButton
            , updateButton
            , deleteButton;
    private JList outputList;
    private GebruikerRepository gebruikerRepo;
    private DefaultListModel listModel;
    private DefaultTableModel listTableModel;
    private JTable outputTable;

    public GebruikerUI(){
        gebruikerRepo = new GebruikerRepository();

        //Buttons initializen
        selectAllButton = new JButton("Select All");
        insertButton = new JButton("Insert");
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");

        //Bounds
        selectAllButton.setBounds(60, 100, 120, 20);
        insertButton.setBounds(60, 80, 120, 20);
        updateButton.setBounds(60, 60, 120, 20);
        deleteButton.setBounds(60, 40, 120, 20);


        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout(10,5));
        buttonPanel.setBounds(40, 110, 140, 100);
        buttonPanel.add(selectAllButton);
        buttonPanel.add(insertButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        add(buttonPanel, BorderLayout.LINE_START);


        //nog veranderen
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
        outputPanel.add(listPanel);

        listTableModel = new DefaultTableModel();
        String[] colnames = {"id", "locatie"};
        Vector colnamesV = new Vector(Arrays.asList(colnames));
        outputTable = new JTable(null, colnamesV);
        JScrollPane tablePanel = new JScrollPane(outputTable);
        tablePanel.setPreferredSize(new Dimension(800, 150));
        outputPanel.add(tablePanel);

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
