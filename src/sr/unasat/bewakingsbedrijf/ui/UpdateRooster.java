package sr.unasat.bewakingsbedrijf.ui;

import sr.unasat.bewakingsbedrijf.entities.Gebruiker;
import sr.unasat.bewakingsbedrijf.entities.Post;
import sr.unasat.bewakingsbedrijf.entities.Rooster;
import sr.unasat.bewakingsbedrijf.entities.Shift;
import sr.unasat.bewakingsbedrijf.repositories.GebruikerRepository;
import sr.unasat.bewakingsbedrijf.repositories.PostRepository;
import sr.unasat.bewakingsbedrijf.repositories.RoosterRepository;
import sr.unasat.bewakingsbedrijf.repositories.ShiftRepository;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.List;

/**
 * Created by mitchel on 6/10/17.
 */
public class UpdateRooster extends JFrame{
    public UpdateRooster(int id){

        // get specific rooster
        RoosterRepository roosterRepository = new RoosterRepository();
        Rooster rooster = roosterRepository.selectRecord(id);

        // init frame
        JFrame roosterUpdate = new JFrame("Rooster bewerken");

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

        // shift component
        JLabel shiftLabel = new JLabel("Shift");
        JComboBox shiftComboBox = new JComboBox();
        ShiftRepository shiftRepository = new ShiftRepository();
        List<Shift> shiftList = shiftRepository.selectAll();
        for (Shift shift : shiftList) {
            Shift record = shift;
            shiftComboBox.addItem(record.getId() + " - " + record.getBegintijd() + " tot " + record.getEindtijd());
        }

        // datum component
        JLabel datumLabel = new JLabel("Datum");
        JTextField datumInput = new JTextField(rooster.getDatum());

        // button
        JButton submitButton = new JButton("Inroosteren");

        // position components (setBounds(x, y, width, height))
        gebruikerLabel.setBounds(100, 80, 120, 20);
        gebruikerComboBox.setBounds(180, 80, 120, 20);
        postLabel.setBounds(100, 100, 120, 20);
        postComboBox.setBounds(180, 100, 120, 20);
        shiftLabel.setBounds(100, 120, 120, 20);
        shiftComboBox.setBounds(180, 120, 120, 20);
        datumLabel.setBounds(100, 140, 120, 20);
        datumInput.setBounds(180, 140, 120, 20);
        submitButton.setBounds(140, 165, 150, 20);

        // add components
        roosterUpdate.getContentPane().add(gebruikerLabel);
        roosterUpdate.getContentPane().add(gebruikerComboBox);
        roosterUpdate.getContentPane().add(postLabel);
        roosterUpdate.getContentPane().add(postComboBox);
        roosterUpdate.getContentPane().add(shiftLabel);
        roosterUpdate.getContentPane().add(shiftComboBox);
        roosterUpdate.getContentPane().add(datumLabel);
        roosterUpdate.getContentPane().add(datumInput);
        roosterUpdate.getContentPane().add(submitButton);

        // set visible
        roosterUpdate.setBounds(100, 100, 450, 300);
        roosterUpdate.getContentPane().setLayout(null);
        roosterUpdate.setVisible(true);
        roosterUpdate.setTitle("Ingeroosterde bewaker aanpassen");

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // value van de gebruiker
                String gebruikerComboValue = gebruikerComboBox.getSelectedItem().toString();
                String[] explodedGebruikerId = gebruikerComboValue.split(" - ");

                // value van de post
                String postComboValue = postComboBox.getSelectedItem().toString();
                String[] explodedPostId = postComboValue.split(" - ");

                // value van de shift
                String shiftComboValue = shiftComboBox.getSelectedItem().toString();
                String[] explodedShiftId = shiftComboValue.split(" - ");

                // prepare gebruiker
                GebruikerRepository gebruikerRepository = new GebruikerRepository();
                Gebruiker gebruiker = gebruikerRepository.selectRecord(Integer.parseInt(explodedGebruikerId[0]));

                // prepare post
                PostRepository postRepository = new PostRepository();
                Post post = postRepository.selectRecord(Integer.parseInt(explodedPostId[0]));


                // prepare shift
                ShiftRepository shiftRepository = new ShiftRepository();
                Shift shift = shiftRepository.selectRecord(Integer.parseInt(explodedShiftId[0]));

                // update rooster
                Rooster newRooster = new Rooster(id, gebruiker, post, shift, datumInput.getText());
                RoosterRepository roosterRepository = new RoosterRepository();
                roosterRepository.updateRecord(newRooster);

                WindowEvent winClosingEvent = new WindowEvent(roosterUpdate, WindowEvent.WINDOW_CLOSING);
                Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(winClosingEvent);
            }
        });
    }
}
