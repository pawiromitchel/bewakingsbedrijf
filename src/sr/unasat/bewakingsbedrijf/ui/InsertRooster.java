package sr.unasat.bewakingsbedrijf.ui;

import sr.unasat.bewakingsbedrijf.entities.Gebruiker;
import sr.unasat.bewakingsbedrijf.entities.Post;
import sr.unasat.bewakingsbedrijf.entities.Rol;
import sr.unasat.bewakingsbedrijf.entities.Rooster;
import sr.unasat.bewakingsbedrijf.repositories.GebruikerRepository;
import sr.unasat.bewakingsbedrijf.repositories.PostRepository;
import sr.unasat.bewakingsbedrijf.repositories.RolRepository;
import sr.unasat.bewakingsbedrijf.repositories.RoosterRepository;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.List;

/**
 * Created by mitchel on 6/8/17.
 */
public class InsertRooster extends JFrame {
    public InsertRooster(){
        super("Bewaker Inroosteren");

        // init frame
        JFrame insertRooster = new JFrame();

        // gebruiker component
        JLabel gebruikerLabel = new JLabel("Gebruiker");
        JComboBox gebruikerComboBox = new JComboBox();
        GebruikerRepository gebruikerRepository = new GebruikerRepository();
        List<Gebruiker> gebruikerList = gebruikerRepository.selectAll();
        for (Gebruiker gebruiker : gebruikerList) {
            Gebruiker record = gebruiker;
            gebruikerComboBox.addItem(record.getId() + " - " + record.getVoornaam().toString());
        }

        // post component
        JLabel postLabel = new JLabel("Post");
        JComboBox postComboBox = new JComboBox();
        PostRepository postRepository = new PostRepository();
        List<Post> postList = postRepository.selectAll();
        for (Post post : postList) {
            Post record = post;
            postComboBox.addItem(record.getId() + " - " + record.getLocatie().toString());
        }

        // datum component
        JLabel datumLabel = new JLabel("Datum");
        JTextField datumInput = new JTextField();

        // button
        JButton submitButton = new JButton("Inroosteren");

        // position components (setBounds(x, y, width, height))
        gebruikerLabel.setBounds(100, 80, 120, 20);
        gebruikerComboBox.setBounds(180, 80, 120, 20);
        postLabel.setBounds(100, 100, 120, 20);
        postComboBox.setBounds(180, 100, 120, 20);
        datumLabel.setBounds(100, 120, 120, 20);
        datumInput.setBounds(180, 120, 120, 20);
        submitButton.setBounds(140, 145, 150, 20);

        // add components
        insertRooster.getContentPane().add(gebruikerLabel);
        insertRooster.getContentPane().add(gebruikerComboBox);
        insertRooster.getContentPane().add(postLabel);
        insertRooster.getContentPane().add(postComboBox);
        insertRooster.getContentPane().add(datumLabel);
        insertRooster.getContentPane().add(datumInput);
        insertRooster.getContentPane().add(submitButton);

        // set visible
        insertRooster.setBounds(100, 100, 450, 300);
        insertRooster.getContentPane().setLayout(null);
        insertRooster.setVisible(true);
        insertRooster.setTitle("Bewaker inroosteren");

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // value van de gebruiker
                String gebruikerComboValue = gebruikerComboBox.getSelectedItem().toString();
                String[] explodedGebruikerId = gebruikerComboValue.split(" - ");

                // value van de post
                String postComboValue = postComboBox.getSelectedItem().toString();
                String[] explodedPostId = postComboValue.split(" - ");

                // prepare gebruiker
                GebruikerRepository gebruikerRepository = new GebruikerRepository();
                Gebruiker gebruiker = gebruikerRepository.selectRecord(Integer.parseInt(explodedGebruikerId[0]));

                // prepare post
                PostRepository postRepository = new PostRepository();
                Post post = postRepository.selectRecord(Integer.parseInt(explodedPostId[0]));

                // insert new rooster
                Rooster newRooster = new Rooster(6, gebruiker, post, datumInput.getText());
                RoosterRepository roosterRepository = new RoosterRepository();
                roosterRepository.insertRecord(newRooster);
            }
        });
    }
}