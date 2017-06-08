package sr.unasat.bewakingsbedrijf.ui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by mitchel on 5/30/17.
 */
public class MainFrame extends JFrame {
    public MainFrame() throws HeadlessException {
        super("SwingApp");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //create and set menu bar
        MenuBar menuBar = new MenuBar();
        // put the menubar on the frame
        setJMenuBar(menuBar.getMenuBar());

        JTabbedPane tabbedPane=new JTabbedPane();
        tabbedPane.setBounds(50,50,200,200);

        //  Place Student
        tabbedPane.add("Gebruiker", new GebruikerUI());
        tabbedPane.add("Posten", new PostUI());

        //  Place Rooster
//        tabbedPane.add("Rooster", new RoosterUI());

        tabbedPane.setOpaque(true);    //	content panes must be op..
        setContentPane(tabbedPane);

        setLocationRelativeTo(null);
        pack();
        setVisible(true);
    }
}
