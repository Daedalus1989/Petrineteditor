package de.pohl.petrinets.view.gui.components;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.*;

import de.pohl.petrinets.control.PetrinetEditorViewListener;
import de.pohl.petrinets.control.implementations.actions.toolbar.*;

/**
 * Eine Klasse für die ToolBar des Petrinetzeditors.
 */
public class ToolBar extends JToolBar {
    private PetrinetEditorViewListener listener;
    private JButton btnAnalyseSingle;
    private JButton btnDecToken;
    private JButton btnIncToken;
    private JButton btnNextGraph;
    private JButton btnPrevGraph;
    private JButton btnRemEG;
    private JButton btnReset;
    private JButton btnUndo;
    private JButton btnRedo;
    private JToggleButton tglPermanentAnalysis;

    /**
     * Erstellt eine neue {@link ToolBar}.
     *
     * @param listener ein {@link PetrinetEditorViewListener} der auf die Aktionen
     *                 der Buttons reagiert.
     */
    public ToolBar(PetrinetEditorViewListener listener) {
        this.listener = listener;
        this.initToolBarButtons();
        this.setSettings();
    }

    /**
     * Aktiviert oder Deaktiviert die Schaltfläche für das Starten der Analyse der
     * aktuell geladenen PNML-Datei.
     *
     * @param state wenn <code>true</code>, wird die Schaltlfäche aktiviert. wenn
     *              <code>false</code>, wird die Schaltfläche deaktiviert.
     */
    public void setAnalyseSingleActivationState(boolean state) {
        btnAnalyseSingle.setEnabled(state);
    }

    /**
     * Aktiviert oder Deaktiviert die Schaltfläche zum Dekrementieren der Marken
     * einer Stelle.
     *
     * @param state wenn <code>true</code>, wird die Schaltlfäche aktiviert. wenn
     *              <code>false</code>, wird die Schaltfläche deaktiviert.
     */
    public void setDecActivationState(boolean state) {
        btnDecToken.setEnabled(state);
    }

    /**
     * Aktiviert oder Deaktiviert die Schaltfläche zum Inkrementieren der Marken
     * einer Stelle.
     *
     * @param state wenn <code>true</code>, wird die Schaltlfäche aktiviert. wenn
     *              <code>false</code>, wird die Schaltfläche deaktiviert.
     */
    public void setIncActivationState(boolean state) {
        btnIncToken.setEnabled(state);
    }

    /**
     * Aktiviert oder Deaktiviert die Schaltfläche zum Laden der vorangehenden
     * PNML-Datei.
     *
     * @param state wenn <code>true</code>, wird die Schaltlfäche aktiviert. wenn
     *              <code>false</code>, wird die Schaltfläche deaktiviert.
     */
    public void setNextActivationState(boolean state) {
        btnNextGraph.setEnabled(state);
    }

    /**
     * Aktiviert oder Deaktiviert die Schaltfläche zum Umschalten der Analyse beim
     * Aufbau des (partiellen) Erreichbarkeitsgraphen.
     *
     * @param state wenn <code>true</code>, wird die Schaltlfäche aktiviert. wenn
     *              <code>false</code>, wird die Schaltfläche deaktiviert.
     */
    public void setPermanentAnalysisActivationState(boolean state) {
        tglPermanentAnalysis.setEnabled(state);
    }

    /**
     * Aktiviert oder Deaktiviert die Schaltfläche zum Laden der nachfolgenden
     * PNML-Datei.
     *
     * @param state wenn <code>true</code>, wird die Schaltlfäche aktiviert. wenn
     *              <code>false</code>, wird die Schaltfläche deaktiviert.
     */
    public void setPrevActivationState(boolean state) {
        btnPrevGraph.setEnabled(state);
    }

    /**
     * Aktiviert oder Deaktiviert die Schaltfläche für Redo-Operationen.
     *
     * @param state wenn <code>true</code>, wird die Schaltlfäche aktiviert. wenn
     *              <code>false</code>, wird die Schaltfläche deaktiviert.
     */
    public void setRedoActivationState(boolean state) {
        btnRedo.setEnabled(state);
    }

    /**
     * Aktiviert oder Deaktiviert die Schaltfläche zum Löschen des (partiellen)
     * Erreichbarkeitsgraphen.
     *
     * @param state wenn <code>true</code>, wird die Schaltlfäche aktiviert. wenn
     *              <code>false</code>, wird die Schaltfläche deaktiviert.
     */
    public void setRemEGActivationState(boolean state) {
        btnRemEG.setEnabled(state);
    }

    /**
     * Aktiviert oder Deaktiviert die Schaltfläche zum Zurücksetzen des Petrinetzes.
     *
     * @param state wenn <code>true</code>, wird die Schaltlfäche aktiviert. wenn
     *              <code>false</code>, wird die Schaltfläche deaktiviert.
     */
    public void setResetActivationState(boolean state) {
        btnReset.setEnabled(state);
    }

    /**
     * Aktiviert oder Deaktiviert die Schaltfläche für Undo-Operationen.
     *
     * @param state wenn <code>true</code>, wird die Schaltlfäche aktiviert. wenn
     *              <code>false</code>, wird die Schaltfläche deaktiviert.
     */
    public void setUndoActivationState(boolean state) {
        btnUndo.setEnabled(state);
    }

    /**
     * Initiiert die Schaltflächen der Toolbar.
     */
    private void initToolBarButtons() {
        this.add(newBtnPrevPNMLFile());
        this.add(newBtnNextPNMLFile());
        this.addSeparator();
        this.add(newBtnUndo());
        this.add(newBtnRedo());
        this.addSeparator();
        this.add(newBtnRemEG());
        this.addSeparator();
        this.add(newBtnIncToken());
        this.add(newBtnDecToken());
        this.add(newBtnReset());
        this.addSeparator();
        this.add(newBtnAnalyseSingle());
        this.add(newTglPermanentAnalysis());
    }

    /**
     * Erstellt einen {@link JButton} für die Analyse des aktuell angezeigten
     * Petrinetzes.
     *
     * @return Einen {@link JButton} für die Analyse des aktuell angezeigten
     *         Petrinetzes.
     */
    private JButton newBtnAnalyseSingle() {
        btnAnalyseSingle = new JButton(new AnalyseCurrentPetrinetAction(listener));
        return btnAnalyseSingle;
    }

    /**
     * Erstellt einen {@link JButton} für das Verringern der Marken einer Stelle des
     * aktuell angezeigten Petrinetzes.
     *
     * @return Einen {@link JButton} für das Verringern der Marken einer Stelle des
     *         aktuell angezeigten Petrinetzes.
     */
    private JButton newBtnDecToken() {
        DecreaseTokenAction decreaseTokenAction = new DecreaseTokenAction(listener);
        KeyStroke keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, ActionEvent.CTRL_MASK);
        String mapKey = "decreaseTokenAction";
        btnDecToken = new JButton(decreaseTokenAction);
        btnDecToken.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStroke, mapKey);
        btnDecToken.getActionMap().put(mapKey, decreaseTokenAction);
        return btnDecToken;
    }

    /**
     * Erstellt einen {@link JButton} für das Erhöhen der Marken einer Stelle des
     * aktuell angezeigten Petrinetzes.
     *
     * @return Einen {@link JButton} für das Erhöhen der Marken einer Stelle des
     *         aktuell angezeigten Petrinetzes.
     */
    private JButton newBtnIncToken() {
        IncreaseTokenAction increaseTokenAction = new IncreaseTokenAction(listener);
        KeyStroke keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_PLUS, ActionEvent.CTRL_MASK);
        String mapKey = "increaseTokenAction";
        btnIncToken = new JButton(increaseTokenAction);
        btnIncToken.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStroke, mapKey);
        btnIncToken.getActionMap().put(mapKey, increaseTokenAction);
        return btnIncToken;
    }

    /**
     * Erstellt einen {@link JButton} für das Laden des nächten Graphen im aktuellen
     * Verzeichnis.
     *
     * @return Einen {@link JButton} für das Laden des nächten Graphen im aktuellen
     *         Verzeichnis.
     */
    private JButton newBtnNextPNMLFile() {
        NextPNMLFileAction nextGraphAction = new NextPNMLFileAction(listener);
        KeyStroke keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, ActionEvent.CTRL_MASK);
        String mapKey = "nextGraphAction";
        btnNextGraph = new JButton(nextGraphAction);
        btnNextGraph.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStroke, mapKey);
        btnNextGraph.getActionMap().put(mapKey, nextGraphAction);
        return btnNextGraph;
    }

    /**
     * Erstellt einen {@link JButton} für das Laden des vorherigen Graphen im
     * aktuellen Verzeichnis.
     *
     * @return Einen {@link JButton} für das Laden des vorherigen Graphen im
     *         aktuellen Verzeichnis.
     */
    private JButton newBtnPrevPNMLFile() {
        PreviousPNMLFileAction prevGraphAction = new PreviousPNMLFileAction(listener);
        KeyStroke keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, ActionEvent.CTRL_MASK);
        String mapKey = "prevGraphAction";
        btnPrevGraph = new JButton(prevGraphAction);
        btnPrevGraph.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStroke, mapKey);
        btnPrevGraph.getActionMap().put(mapKey, prevGraphAction);
        return btnPrevGraph;
    }

    /**
     * Erstellt einen {@link JButton} für das Wiederherstellen der letzten Aktion,
     * die die Markierung eines Petrinetzes verändert hat.
     *
     * @return Einen {@link JButton} für das Wiederherstellen der letzten Aktion,
     *         die die Markierung eines Petrinetzes verändert hat.
     */
    private JButton newBtnRedo() {
        RedoAction redoAction = new RedoAction(listener);
        KeyStroke keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.CTRL_MASK);
        String mapKey = "redoAction";
        btnRedo = new JButton(redoAction);
        btnRedo.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStroke, mapKey);
        btnRedo.getActionMap().put(mapKey, redoAction);
        return btnRedo;
    }

    /**
     * Erstellt einen {@link JButton} für das Löschen des aktuell dargestellten
     * Erreichbarkeitsgraphen.
     *
     * @return Einen {@link JButton} für das Löschen des aktuell dargestellten
     *         Erreichbarkeitsgraphen.
     */
    private JButton newBtnRemEG() {
        btnRemEG = new JButton(new RemoveRGraphAction(listener));
        return btnRemEG;
    }

    /**
     * Erstellt einen {@link JButton} für das Zurücksetzen des aktuell geladenen
     * Petrinetzes.
     *
     * @return Einen {@link JButton} für das Zurücksetzen des aktuell geladenen
     *         Petrinetzes.
     */
    private JButton newBtnReset() {
        btnReset = new JButton(new ResetPetrinetMarkingAction(listener));
        return btnReset;
    }

    /**
     * Erstellt einen {@link JButton} für das Rückgängigmachen der letzten Aktion,
     * die die Markierung eines Petrinetzes verändert hat.
     *
     * @return Einen {@link JButton} für das Rückgängigmachen der letzten Aktion,
     *         die die Markierung eines Petrinetzes verändert hat.
     */
    private JButton newBtnUndo() {
        UndoAction undoAction = new UndoAction(listener);
        KeyStroke keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK);
        String mapKey = "undoAction";
        btnUndo = new JButton(undoAction);
        btnUndo.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStroke, mapKey);
        btnUndo.getActionMap().put(mapKey, undoAction);
        return btnUndo;
    }

    /**
     * Erstellt einen {@link JToggleButton} für das Umschalten der Analysefunktion
     * bei der manuellen Schaltung einer Transition.
     *
     * @return Einen {@link JToggleButton} für das Umschalten der Analysefunktion
     *         bei der manuellen Schaltung einer Transition.
     */
    private JToggleButton newTglPermanentAnalysis() {
        tglPermanentAnalysis = new JToggleButton();
        tglPermanentAnalysis.setAction(new AnalyseOnManualToggleAction(listener, tglPermanentAnalysis));
        return tglPermanentAnalysis;
    }

    /**
     * Nimmt Änderungen an den Einstellungen der {@link ToolBar} vor.
     */
    private void setSettings() {
        this.setFloatable(false);
    }
}
