package sr.unasat.bewakingsbedrijf.ui;

import sr.unasat.bewakingsbedrijf.entities.Gebruiker;
import sr.unasat.bewakingsbedrijf.entities.Post;
import sr.unasat.bewakingsbedrijf.entities.Rol;
import sr.unasat.bewakingsbedrijf.repositories.GebruikerRepository;
import sr.unasat.bewakingsbedrijf.repositories.RolRepository;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.List;

/**
 * Created by Jonathan on 6/7/2017.
 */
public class GebruikerUI extends JPanel {
    private Connection connect;
    private JList outputList;
    private GebruikerRepository gebruikerRepo;
    private DefaultListModel listModel;
    private DefaultTableModel listTableModel;
    private JTable outputTable;
    private JButton selectAllButton, insertButton, updateButton, deleteButton;
    private JPanel buttonPanel;
    private JFrame insert;
    private Gebruiker gebruiker;

    public GebruikerUI() {
        gebruikerRepo = new GebruikerRepository();

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
        String[] colnames = {"id", "achternaam", "voornaam", "adres", "woonplaats", "idnummer", "geslacht"
                , "geboortedatum", "rol_id"};
        Vector colnamesV = new Vector(Arrays.asList(colnames));
        outputTable = new JTable(null, colnamesV);
        JScrollPane tablePanel = new JScrollPane(outputTable);
        tablePanel.setPreferredSize(new Dimension(800, 150));
        outputPanel.add(tablePanel);

        // Select listener
        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insert = new JFrame("Insert");

                JLabel achternaamLabel = new JLabel("Achternaam");
                JLabel voornaamLabel = new JLabel("Voornaam");
                JLabel adresLabel = new JLabel("Adres");
                JLabel woonplaatsLabel = new JLabel("Woonplaats");
                JLabel idnummerLabel = new JLabel("ID Nummer");
                JLabel geboortedatumLabel = new JLabel("Geboortedatum");
                JLabel geslachtLabel = new JLabel("Geslacht");
                JLabel rolLabel = new JLabel("Rol");

                JTextField achternaamField = new JTextField();
                JTextField voornaamField = new JTextField();
                JTextField adresField = new JTextField();
                JTextField woonplaatsField = new JTextField();
                JTextField idnummerField = new JTextField();
                JTextField geboortedatumField = new JTextField();
                JTextField geslachtField = new JTextField();
                JButton submitButton = new JButton("Submit");
                JComboBox rolComboBox = new JComboBox();
                RolRepository rolRepository = new RolRepository();
                List<Rol> rolList = rolRepository.selectAll();
                for (Rol rol : rolList) {
                    Rol record = rol;
                    rolComboBox.addItem(record.getNaam().toString());
                }

                achternaamField.setBounds(150, 100, 200, 20);
                voornaamField.setBounds(150, 130, 200, 20);
                adresField.setBounds(150, 160, 200, 20);
                woonplaatsField.setBounds(150, 190, 200, 20);
                idnummerField.setBounds(150, 220, 200, 20);
                geboortedatumField.setBounds(150, 250, 200, 20);
                geslachtField.setBounds(150, 280, 200, 20);
                rolComboBox.setBounds(150, 70, 200, 20);

                achternaamLabel.setBounds(60, 100, 200, 20);
                voornaamLabel.setBounds(60, 130, 200, 20);
                adresLabel.setBounds(60, 160, 200, 20);
                woonplaatsLabel.setBounds(60, 190, 200, 20);
                idnummerLabel.setBounds(60, 220, 200, 20);
                geboortedatumLabel.setBounds(60, 250, 200, 20);
                geslachtLabel.setBounds(60, 280, 200, 20);
                rolLabel.setBounds(60, 70, 200, 20);
                submitButton.setBounds(150, 340, 80, 20);

                insert.add(achternaamField);
                insert.add(voornaamField);
                insert.add(adresField);
                insert.add(woonplaatsField);
                insert.add(idnummerField);
                insert.add(geboortedatumField);
                insert.add(geslachtField);
                insert.add(rolComboBox);

                insert.add(achternaamLabel);
                insert.add(voornaamLabel);
                insert.add(adresLabel);
                insert.add(woonplaatsLabel);
                insert.add(idnummerLabel);
                insert.add(geboortedatumLabel);
                insert.add(geslachtLabel);
                insert.add(rolLabel);
                insert.add(submitButton);

                insert.setLayout(new BorderLayout());
                insert.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                insert.setSize(500, 500);
                insert.setVisible(true);

                submitButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        gebruiker = new Gebruiker();
                        PreparedStatement preparedStatement = null;
                        int result = 0;
                        try {
                            preparedStatement = connect.prepareStatement("insert into gebruikers values (NULL, ?,?,?,?,?,?,?,?)");

                            String rolComboBoxValue = rolComboBox.getSelectedItem().toString();
                            String[] explodedRolId = rolComboBoxValue.split(" - ");
                            RolRepository rolRepository1 = new RolRepository();
                            Rol rol = rolRepository1.selectRecord(Integer.parseInt(explodedRolId[0]));
                            preparedStatement.setString(8, rolComboBox.getSelectedObjects().toString());
                            preparedStatement.setString(1, achternaamField.getText());
                            preparedStatement.setString(2, voornaamField.getText());
                            preparedStatement.setString(3, adresField.getText());
                            preparedStatement.setString(4, woonplaatsField.getText());
                            preparedStatement.setString(5, idnummerField.getText());
                            preparedStatement.setString(6, geslachtField.getText());
                            preparedStatement.setString(7, geboortedatumField.getText());

                            // Voer de statement uit en haal het resultaat op
                            result = preparedStatement.executeUpdate();
                            JOptionPane.showMessageDialog(null, "Submitted!");
                        } catch (Exception e1) {
                            System.out.println("Er is een SQL fout tijdens het inserten van een nieuwe record!");
                            e1.printStackTrace();
                        }
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
                            Gebruiker gebruiker = (Gebruiker) listTableModel.getDataVector().elementAt(outputTable.getSelectedRow());
                            System.out.println(gebruiker);
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
                                String[] colData = new String[6];
                                colData[0] = Integer.valueOf(record.getId()).toString();
                                colData[1] = record.getAchternaam();
                                colData[2] = record.getVoornaam();
                                colData[3] = record.getAdres();
                                colData[4] = record.getWoonplaats();
                                colData[5] = record.getIdnummer();
                                colData[6] = record.getGeslacht();
                                colData[7] = record.getGeboortedatum();
                                if (record.getRol() != null) {
                                    colData[8] = record.getRol().getNaam();
                                }

                                listTableModel.addRow(colData);
                            }
                            outputTable.setModel(listTableModel);
                        }
                    }
                });
            }
        });
    }
}
