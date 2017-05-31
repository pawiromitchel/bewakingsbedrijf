package sr.unasat.bewakingsbedrijf.ui;

import javax.swing.*;

/**
 * Created by mitchel on 5/30/17.
 */
public class MenuBar {
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenu editMenu;
    private JMenuItem openMenuItem;
    private JMenuItem cutMenuItem;
    private JMenuItem copyMenuItem;
    private JMenuItem pasteMenuItem;

    public MenuBar() {
        // build the File menu
        fileMenu = new JMenu("File");
        openMenuItem = new JMenuItem("Open");
        // openMenuItem.addActionListener(this);
        fileMenu.add(openMenuItem);

        // build the Edit menu
        editMenu = new JMenu("Edit");
        cutMenuItem = new JMenuItem("Cut");
        copyMenuItem = new JMenuItem("Copy");
        pasteMenuItem = new JMenuItem("Paste");
        editMenu.add(cutMenuItem);
        editMenu.add(copyMenuItem);
        editMenu.add(pasteMenuItem);


        // add menus to menubar
        menuBar = new JMenuBar();
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
    }

    public JMenuBar getMenuBar() {
        return menuBar;
    }
}
