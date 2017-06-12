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
public class InsertRol extends JFrame {
    public InsertRol(){
        // init frame
        JFrame insertRol = new JFrame("Rol toevoegen");

        // rol component
        JLabel rolLabel = new JLabel("Rol");
        JTextField rolInput = new JTextField();

        // button
        JButton submitButton = new JButton("Submit");

        //position components
        rolLabel.setBounds(100, 120, 120, 20);
        rolInput.setBounds(180, 120, 120, 20);
        submitButton.setBounds(140, 145, 150, 20);

        //add components
        insertRol.getContentPane().add(rolLabel);
        insertRol.getContentPane().add(rolInput);
        insertRol.getContentPane().add(submitButton);

        //set visible
        insertRol.setBounds(100, 100, 450, 300);
        insertRol.getContentPane().setLayout(null);
        insertRol.setVisible(true);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // insert new rooster
                Rol newRol = new Rol(1, rolInput.getText());
                RolRepository rolRepository = new RolRepository();
                rolRepository.insertRecord(newRol);

                JOptionPane.showMessageDialog(null, "Submitted!");

                WindowEvent winClosingEvent = new WindowEvent(insertRol, WindowEvent.WINDOW_CLOSING);
                Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(winClosingEvent);
            }
        });
    }
}
