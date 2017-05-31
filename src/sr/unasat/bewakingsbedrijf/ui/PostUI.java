package sr.unasat.bewakingsbedrijf.ui;

import sr.unasat.bewakingsbedrijf.entities.Post;
import sr.unasat.bewakingsbedrijf.entities.Rooster;
import sr.unasat.bewakingsbedrijf.repositories.PostRepository;
import sr.unasat.bewakingsbedrijf.repositories.RoosterRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

/**
 * Created by mitchel on 5/30/17.
 */
public class PostUI extends JPanel {
    private JButton selectAllButton, removeButton;
    private JList outputList;
    private PostRepository postRepo;

    private DefaultListModel listModel;

    private DefaultTableModel listTableModel;
    private JTable outputTable;

    public PostUI(){
        postRepo = new PostRepository();

        // Initialisatie selectAllButton.
        selectAllButton = new JButton("Select All");
        selectAllButton.setBackground(Color.green);
        selectAllButton.setPreferredSize(new Dimension(100, 40));

        //	Creeer buttonPanel met buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());
        // Voeg selectAllButton aan inputPanel
        buttonPanel.add(selectAllButton);
        add(buttonPanel, BorderLayout.LINE_START);

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
                if (postRepo == null) {
                    postRepo = new PostRepository();
                }

                // Initialiseer de roosterRepo alleen als het moet
                if (!postRepo.isInitialised()) {
                    postRepo.initialize();
                }

                if (outputTable.getSelectedRow() > 0) {
                    Post post = (Post) listTableModel.getDataVector().elementAt(outputTable.getSelectedRow());
                    System.out.println(post);
                }

                // Alleen als de rooster database is geinitialiseerd dan verder
                if (postRepo.isInitialised()) {
                    java.util.List<Post> outputList = postRepo.selectAll();

                    // Maak de lijst leeg als er elementen inzitten
                    listModel.removeAllElements();

                    // Parse de lijst
                    for (Post post : outputList) {
                        Post record = post;
                        listModel.addElement(record.toString());
                    }

                    listTableModel = (DefaultTableModel) outputTable.getModel();
                    listTableModel.setRowCount(0);
                    // Parse de table
                    for (Post post : outputList) {
                        Post record = post;
                        String[] colData = new String[2];
                        colData[0] = Integer.valueOf(record.getId()).toString();
                        colData[1] = record.getLocatie();
                        listTableModel.addRow(colData);
                    }
                    outputTable.setModel(listTableModel);
                }
            }
        });
    }
}
