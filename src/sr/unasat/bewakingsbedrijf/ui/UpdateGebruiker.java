package sr.unasat.bewakingsbedrijf.ui;

import sr.unasat.bewakingsbedrijf.entities.Gebruiker;
import sr.unasat.bewakingsbedrijf.entities.Rol;
import sr.unasat.bewakingsbedrijf.repositories.GebruikerRepository;
import sr.unasat.bewakingsbedrijf.repositories.RolRepository;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.List;

/**
 * Created by Jonathan on 6/10/2017.
 */
public class UpdateGebruiker extends JFrame {
    public UpdateGebruiker (int id){

        //get Gebruiker
        GebruikerRepository gebruikerRepository = new GebruikerRepository();
        Gebruiker gebruiker = gebruikerRepository.selectRecord(id);

        JFrame updateGebruiker = new JFrame("Gebruiker bewerken");

        //componenten
        JLabel achternaamLabel = new JLabel("Achternaam");
        JLabel voornaamLabel = new JLabel("Voornaam");
        JLabel adresLabel = new JLabel("Adres");
        JLabel woonplaatsLabel = new JLabel("Woonplaats");
        JLabel idnummerLabel = new JLabel("ID Nummer");
        JLabel geboortedatumLabel = new JLabel("Geboortedatum");
        JLabel geslachtLabel = new JLabel("Geslacht");
        JLabel rolLabel = new JLabel("Rol");

        JTextField achternaamField = new JTextField(gebruiker.getAchternaam());
        JTextField voornaamField = new JTextField(gebruiker.getVoornaam());
        JTextField adresField = new JTextField(gebruiker.getAdres());
        JTextField woonplaatsField = new JTextField(gebruiker.getWoonplaats());
        JTextField idnummerField = new JTextField(gebruiker.getIdnummer());
        JTextField geboortedatumField = new JTextField(gebruiker.getGeboortedatum());
        JTextField geslachtField = new JTextField(gebruiker.getGeslacht());

        // rol combobox
        JComboBox rolComboBox = new JComboBox();
        RolRepository rolRepository = new RolRepository();
        List<Rol> rolList = rolRepository.selectAll();
        for (Rol rol : rolList) {
            Rol record = rol;
            rolComboBox.addItem(record.getId() + " - " + record.getNaam().toString());
        }

        //button
        JButton submitButton = new JButton("Submit");

        //position
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

        //add components
        updateGebruiker.getContentPane().add(achternaamField);
        updateGebruiker.getContentPane().add(voornaamField);
        updateGebruiker.getContentPane().add(adresField);
        updateGebruiker.getContentPane().add(woonplaatsField);
        updateGebruiker.getContentPane().add(idnummerField);
        updateGebruiker.getContentPane().add(geboortedatumField);
        updateGebruiker.getContentPane().add(geslachtField);
        updateGebruiker.getContentPane().add(rolComboBox);

        updateGebruiker.getContentPane().add(achternaamLabel);
        updateGebruiker.getContentPane().add(voornaamLabel);
        updateGebruiker.getContentPane().add(adresLabel);
        updateGebruiker.getContentPane().add(woonplaatsLabel);
        updateGebruiker.getContentPane().add(idnummerLabel);
        updateGebruiker.getContentPane().add(geboortedatumLabel);
        updateGebruiker.getContentPane().add(geslachtLabel);
        updateGebruiker.getContentPane().add(rolLabel);
        updateGebruiker.getContentPane().add(submitButton);

        updateGebruiker.setBounds(100,100,450, 500);
        updateGebruiker.getContentPane().setLayout(null);
        updateGebruiker.setVisible(true);

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
        rolRepository = new RolRepository();
        Rol rol = rolRepository.selectRecord(Integer.parseInt(explodedRolId[0]));

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Gebruiker gebruikerUpdate = new Gebruiker(0, rol, voornaamValue, achternaamValue, adresValue, woonplaatsValue,
                        idnummerValue, geslachtValue, geboortedatumValue);

                GebruikerRepository gebruikerRepository = new GebruikerRepository();
                gebruikerRepository.updateRecord(gebruikerUpdate);

                WindowEvent winClosingEvent = new WindowEvent(updateGebruiker, WindowEvent.WINDOW_CLOSING);
                Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(winClosingEvent);
            }
        });
    }
}