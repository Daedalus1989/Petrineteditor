package de.pohl.petrinets.view.gui;

import java.awt.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import de.pohl.petrinets.control.PetrinetEditorViewListener;
import de.pohl.petrinets.view.PetrinetEditorView;
import de.pohl.petrinets.view.gui.components.*;
import de.pohl.petrinets.view.gui.components.MenuBar;
import de.pohl.petrinets.view.gui.dialog.PNMLFileChooser;

/**
 * Klasse für das Hauptfenster des Programms.
 */
public class PetrinetEditorFrame extends JFrame implements PetrinetEditorView {
    private PetrinetEditorViewListener listener;
    private MenuBar menuBar;
    private StatusBar statusBar;
    private ToolBar toolBar;
    private JTabbedPane tabbedPane;

    /**
     * Erstellt ein {@link PetrinetEditorFrame}.
     *
     * @param title    der Titel des {@link PetrinetEditorFrame}.
     * @param listener ein {@link PetrinetEditorViewListener}, der auf Aktionen der
     *                 Komponenten der {@link PetrinetEditorFrame} reagiert.
     */
    public PetrinetEditorFrame(String title, PetrinetEditorViewListener listener) {
        super(title);
        this.listener = listener;
        this.init();
    }

    @Override
    public void addTab(PetrinetViewArea petrinetViewArea, String label) {
        tabbedPane.add(label, petrinetViewArea);
        tabbedPane.setSelectedComponent(petrinetViewArea);
    }

    @Override
    public void removeTab(int index) {
        tabbedPane.remove(index);
    }

    @Override
    public void renameTab(String label) {
        tabbedPane.setTitleAt(tabbedPane.getSelectedIndex(), label);
    }

    @Override
    public void setAnalyseSingleActivationState(boolean state) {
        toolBar.setAnalyseSingleActivationState(state);
    }

    @Override
    public void setCloseTabActionState(boolean state) {
        menuBar.setCloseTabActionState(state);
    }

    @Override
    public void setDecActivationState(boolean state) {
        toolBar.setDecActivationState(state);
    }

    @Override
    public void setIncActivationState(boolean state) {
        toolBar.setIncActivationState(state);
    }

    @Override
    public void setModifiedLabel(String text) {
        statusBar.setModifiedLabel(text);
    }

    @Override
    public void setNextActivationState(boolean state) {
        toolBar.setNextActivationState(state);
    }

    @Override
    public void setPermanentAnalysisActivationState(boolean state) {
        toolBar.setPermanentAnalysisActivationState(state);
    }

    @Override
    public void setPrevActivationState(boolean state) {
        toolBar.setPrevActivationState(state);
    }

    @Override
    public void setRedoActivationState(boolean state) {
        toolBar.setRedoActivationState(state);
    }

    @Override
    public void setReloadActivationState(boolean state) {
        menuBar.setReloadActivationState(state);
    }

    @Override
    public void setRemEGActivationState(boolean state) {
        toolBar.setRemEGActivationState(state);
    }

    @Override
    public void setResetActivationState(boolean state) {
        toolBar.setResetActivationState(state);
    }

    @Override
    public void setStatusbarFilename(String fileName) {
        statusBar.setFilename(fileName);
    }

    @Override
    public void setUndoActivationState(boolean state) {
        toolBar.setUndoActivationState(state);
    }

    @Override
    public void showInfoDialog(String javaVersion, String workingDirectory) {
        StringBuilder sb = new StringBuilder();
        sb.append("Java-Version:\n");
        sb.append("          " + javaVersion + "\n");
        sb.append("Arbeitsverzeichnis:\n");
        sb.append("          " + workingDirectory);
        JOptionPane.showMessageDialog(this, sb.toString(), "Information", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public int showOpenDialog(PNMLFileChooser pnmlFileChooser) {
        return pnmlFileChooser.showOpenDialog(this);
    }

    @Override
    public void showUnboundAnalysisResultDialog(String dialogMessage) {
        JOptionPane.showMessageDialog(this, dialogMessage, "Ergebnis der Beschränktheitsanalyse",
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Fügt die Hauptkomponenten dem Hauptfenster hinzu.
     */
    private void addComponents() {
        this.setLayout(new BorderLayout());
        this.setJMenuBar(menuBar);
        this.add(toolBar, BorderLayout.NORTH);
        this.add(tabbedPane, BorderLayout.CENTER);
        this.add(statusBar, BorderLayout.SOUTH);
        this.pack();
    }

    /**
     * Initialisiert das Anwendungshauptfenster.
     */
    private void init() {
        initBars();
        initTabbedPane();
        // Add Components
        addComponents();
        // Finalize Window
        this.setInitialWindowSize();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    /**
     * Initialisiert die Leisten des Petrinetzeditors
     */
    private void initBars() {
        this.menuBar = new MenuBar(listener);
        this.toolBar = new ToolBar(listener);
        this.statusBar = new StatusBar();
    }

    /**
     * Initialisiert das JTabbedPane.
     */
    private void initTabbedPane() {
        tabbedPane = new JTabbedPane();
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                listener.onActiveTabChanged(tabbedPane.getSelectedIndex());
            }
        });
    }

    /**
     * Bestimmt eine geeignete initiale Fenstergröße und setzt diese fest.
     */
    private void setInitialWindowSize() {
        // bestimme eine geeignete Fenstergröße in Abhängigkeit von der
        // Bildschirmauflösung
        double heightPerc = 0.6; // relative Höhe des Fensters bzgl. der der Bildschirmhöhe (1.0), hier also 60 %
        double aspectRatio = 16.0 / 10.0; // Seitenverhältnis des Fensters
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int h = (int) (screenSize.height * heightPerc);
        int w = (int) (h * aspectRatio);
        this.setBounds((screenSize.width - w) / 2, (screenSize.height - h) / 2, w, h);
    }
}
