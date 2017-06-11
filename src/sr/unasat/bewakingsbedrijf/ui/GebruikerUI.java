package sr.unasat.bewakingsbedrijf.ui;

import sr.unasat.bewakingsbedrijf.entities.Gebruiker;
import sr.unasat.bewakingsbedrijf.entities.Rol;
import sr.unasat.bewakingsbedrijf.repositories.GebruikerRepository;
import sr.unasat.bewakingsbedrijf.repositories.RolRepository;
import sr.unasat.bewakingsbedrijf.repositories.RoosterRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.util.*;
import java.util.List;

/**
 * Created by Jonathan on 6/10/2017.
 */
public class GebruikerUI extends JPanel{
        private Connection connect;
        private JList outputList;
        private GebruikerRepository gebruikerRepo;
        private DefaultListModel listModel;
        private DefaultTableModel listTableModel;
        private JTable outputTable;
        private JButton selectAllButton, insertButton, updateButton, deleteButton, searchButton;
        private JPanel buttonPanel;

        private JTextField searchField;
        private TableRowSorter tableRowSorter;
        private JLabel searchLabel;

        void getData(){
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
                    System.out.println(record.getGeboortedatum());
                    String[] colData = new String[9];
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



        private void filterData(String query){
            DefaultTableModel tableModel = (DefaultTableModel) outputTable.getModel();

            tableRowSorter = new TableRowSorter<DefaultTableModel>(tableModel);
            outputTable.setRowSorter(tableRowSorter);

            tableRowSorter.setRowFilter(RowFilter.regexFilter(query));
        }

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

            listModel = new DefaultListModel();
            outputList = new JList(listModel);
            outputList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            outputList.setSelectedIndex(0);
            outputList.setVisibleRowCount(5);
            JScrollPane listPanel = new JScrollPane(outputList);
            listPanel.setPreferredSize(new Dimension(800, 150));
            //outputPanel.add(listPanel);

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

            listTableModel = new DefaultTableModel();
            String[] colnames = {"id", "achternaam", "voornaam", "adres", "woonplaats", "idnummer", "geslacht"
                    , "geboortedatum", "rol"};
            Vector colnamesV = new Vector(Arrays.asList(colnames));
            outputTable = new JTable(null, colnamesV);
            JScrollPane tablePanel = new JScrollPane(outputTable);
            tablePanel.setPreferredSize(new Dimension(800, 150));
            outputPanel.add(tablePanel);

            getData();

            //'Select All' button
            selectAllButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg) {
                    getData();
                }
            });

            // 'Insert' button
            insertButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFrame insert = new JFrame();

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
                    JComboBox rolComboBox = new JComboBox();
                    JButton submitButton = new JButton("Submit");
                    RolRepository rolRepository = new RolRepository();
                    List<Rol> rolList = rolRepository.selectAll();
                    for (Rol rol : rolList) {
                        Rol record = rol;
                        rolComboBox.addItem(record.getId() + " - " + record.getNaam().toString());
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
                            //if statement schrijven


                            //waarden ophalen van textvelden
                            String achternaamValue = achternaamField.getText().toString();
                            String voornaamValue = voornaamField.getText().toString();
                            String adresValue = adresField.getText().toString();
                            String woonplaatsValue = woonplaatsField.getText().toString();
                            String idnummerValue = idnummerField.getText().toString();
                            String geslachtValue = geslachtField.getText().toString();
                            String geboortedatumValue = geboortedatumField.getText().toString();
                            String rolComboBoxValue = rolComboBox.getSelectedItem().toString();

                            //prepare Rol
                            String[] explodedRolId = rolComboBoxValue.split(" - ");
                            RolRepository rolRepository = new RolRepository();
                            Rol rol = rolRepository.selectRecord(Integer.parseInt(explodedRolId[0]));

                            //insert new gebruiker
                            Gebruiker newGebruiker = new Gebruiker(0, rol, voornaamValue, achternaamValue, adresValue, woonplaatsValue,
                                    idnummerValue, geslachtValue, geboortedatumValue);
                            GebruikerRepository gebruikerRepository = new GebruikerRepository();
                            gebruikerRepository.insertRecord(newGebruiker);

                            JOptionPane.showMessageDialog(null, "Submitted!");

                            WindowEvent winClosingEvent = new WindowEvent(insert, WindowEvent.WINDOW_CLOSING);
                            Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(winClosingEvent);
                            MainFrame mainFrame = new MainFrame();
                            setVisible(true);
                        }
                    });
                }
            });

            //'Update' button
            updateButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int id = Integer.parseInt(outputTable.getModel().getValueAt(outputTable.getSelectedRow(), 0).toString());
                    UpdateGebruiker updateGebruiker = new UpdateGebruiker(id);
                }
            });

            //"'Delete' button
            deleteButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int id = Integer.parseInt(outputTable.getModel().getValueAt(outputTable.getSelectedRow(), 0).toString());
                    int dialogButton = JOptionPane.YES_NO_OPTION;
                    int dialogResult = JOptionPane.showConfirmDialog (null, "Would you like to delete id: " + id + " ?","Confirm Deletion",dialogButton);
                    if(dialogResult == JOptionPane.YES_OPTION){
                        // delete the specific id
                        GebruikerRepository gebruikerRepository = new GebruikerRepository();
                        gebruikerRepository.deleteRecord(id);

                        //refresh
                        getData();
                    }
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

        }
    }
