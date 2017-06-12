package sr.unasat.bewakingsbedrijf.ui;

import sr.unasat.bewakingsbedrijf.entities.Rol;
import sr.unasat.bewakingsbedrijf.repositories.RolRepository;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

/**
 * Created by Patrice on 6/11/2017.
 */
public class UpdateRol extends JFrame {
    public UpdateRol (int id){

        //get specific rol
        RolRepository rolRepository = new RolRepository();
        Rol rol = rolRepository.selectRecord(id);

        JFrame rolUpdate = new JFrame("Naam aanpassen");

        // rol component
        JLabel rolLabel = new JLabel("rol");
        JTextField rolInput = new JTextField(rol.getNaam());

        //button
        JButton submitButton = new JButton("Aanpassen");

        //position components
        rolLabel.setBounds(100, 120, 120, 20);
        rolInput.setBounds(180, 120, 120, 20);
        submitButton.setBounds(140, 145, 150, 20);

        //add components
        rolUpdate.getContentPane().add(rolLabel);
        rolUpdate.getContentPane().add(rolInput);
        rolUpdate.getContentPane().add(submitButton);

        //set visible
        rolUpdate.setBounds(100, 100, 450, 300);
        rolUpdate.getContentPane().setLayout(null);
        rolUpdate.setVisible(true);
        rolUpdate.setTitle("Rol aanpassen");

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //insert new rol
                Rol newRol = new Rol(id, rolInput.getText());
                RolRepository rolRepository = new RolRepository();
                rolRepository.updateRecord(newRol);

                WindowEvent winClosingEvent = new WindowEvent(rolUpdate, WindowEvent.WINDOW_CLOSING);
                Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(winClosingEvent);
            }
        });
    }
}
