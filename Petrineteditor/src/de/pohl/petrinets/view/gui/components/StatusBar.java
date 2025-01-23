package de.pohl.petrinets.view.gui.components;

import java.awt.Dimension;

import javax.swing.*;
import javax.swing.border.BevelBorder;

/**
 * Eine Klasse für die Statusleiste des Petrinetzeditors.
 */
public class StatusBar extends JPanel {
    private JLabel filenameLabel;
    private JLabel modifiedLabel;

    /**
     * Erstellt eine neue {@link StatusBar}.
     */
    public StatusBar() {
        initLabel();
        configPanel();
        addComponents();
    }

    /**
     * Legt den anzuzeigenden Dateinamen fest.
     *
     * @param pnmlFileName der anzuzeigende Dateiname als {@link String}.
     */
    public void setFilename(String pnmlFileName) {
        this.filenameLabel.setText(pnmlFileName);
    }

    /**
     * Ändert den Text in dem Bereich, der den Modifikationssatus einer PNML-Datei
     * anzeigen soll.
     *
     * @param text der Text, der angegeben werden soll als {@link String}.
     */
    public void setModifiedLabel(String text) {
        this.modifiedLabel.setText(text);
    }

    /**
     * Fügt die Komponenten hinzu.
     */
    private void addComponents() {
        this.add(Box.createRigidArea(new Dimension(10, 0)));
        this.add(filenameLabel);
        this.add(Box.createRigidArea(new Dimension(10, 0)));
        this.add(createSeparator());
        this.add(Box.createHorizontalGlue());
        this.add(createSeparator());
        this.add(Box.createRigidArea(new Dimension(10, 0)));
        this.add(modifiedLabel);
        this.add(Box.createRigidArea(new Dimension(10, 0)));
    }

    /**
     * Konfiguriert das Panel
     */
    private void configPanel() {
        this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        this.setBorder(new BevelBorder(BevelBorder.LOWERED));
        this.setPreferredSize(new Dimension(this.getMinimumSize().width, 20));
    }

    /**
     * Erzeugt einen Separator.
     *
     * @return der Separator
     */
    private JSeparator createSeparator() {
        JSeparator separator = new JSeparator(SwingConstants.VERTICAL);
        int width = separator.getPreferredSize().width;
        int height = separator.getMaximumSize().height;
        separator.setMaximumSize(new Dimension(width, height));
        return separator;
    }

    /**
     * Initialisiert die Label
     */
    private void initLabel() {
        this.filenameLabel = new JLabel("");
        this.modifiedLabel = new JLabel("");
        modifiedLabel.setPreferredSize(new Dimension(100, modifiedLabel.getPreferredSize().height));
    }
}
