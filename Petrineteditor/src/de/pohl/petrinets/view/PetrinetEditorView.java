package de.pohl.petrinets.view;

import de.pohl.petrinets.view.gui.components.PetrinetViewArea;
import de.pohl.petrinets.view.gui.dialog.PNMLFileChooser;

/**
 * Ein Interface das Methoden definiert, die das Hauptfenster eines
 * Petrinetzeditors implementieren muss.
 */
public interface PetrinetEditorView extends AnalysisResultDialogView, PetrinetStatusView {
    /**
     * Zeigt ein Informationsdialogfenster mit der verwendeten Java-Version und dem
     * aktuellen Arbeitsverzeichnis an.
     *
     * @param javaVersion      die Java-Version als {@link String}.
     * @param workingDirectory das aktuelle Arbeitsverzeichnis als {@link String}.
     */
    public abstract void showInfoDialog(String javaVersion, String workingDirectory);

    /**
     * Fügt dem TDI einen Tab hinzu.
     *
     * @param petrinetViewArea eine {@link PetrinetViewArea}.
     * @param label            Die Beschriftung des Tabs als {@link String}.
     */
    void addTab(PetrinetViewArea petrinetViewArea, String label);

    /**
     * Entfernt einen Tab aus der TDI.
     *
     * @param index der Index des Tab, der entfernt werden soll als {@link Integer}.
     */
    void removeTab(int index);

    /**
     * Ändert den Titel des aktuellen Tabs der TDI.
     *
     * @param label der neute Titel als {@link String}.
     */
    void renameTab(String label);

    /**
     * Aktiviert oder Deaktiviert die Schaltfläche für das Starten der Analyse der
     * aktuell geladenen PNML-Datei.
     *
     * @param state wenn <code>true</code>, wird die Schaltlfäche aktiviert. wenn
     *              <code>false</code>, wird die Schaltfläche deaktiviert.
     */
    void setAnalyseSingleActivationState(boolean state);

    /**
     * Aktiviert oder deaktiviert den Menüeitnrag zum Schließen des Tabs der TDI.
     *
     * @param state wenn <code>true</code>, wird der Eintrag aktiviert.<br>
     *              wenn <code>false</code>, wird der Eintrag deaktiviert.
     */
    void setCloseTabActionState(boolean state);

    /**
     * Aktiviert oder Deaktiviert die Schaltfläche zum Dekrementieren der Marken
     * einer Stelle.
     *
     * @param state wenn <code>true</code>, wird die Schaltlfäche aktiviert. wenn
     *              <code>false</code>, wird die Schaltfläche deaktiviert.
     */
    void setDecActivationState(boolean state);

    /**
     * Aktiviert oder Deaktiviert die Schaltfläche zum Inkrementieren der Marken
     * einer Stelle.
     *
     * @param state wenn <code>true</code>, wird die Schaltlfäche aktiviert. wenn
     *              <code>false</code>, wird die Schaltfläche deaktiviert.
     */
    void setIncActivationState(boolean state);

    /**
     * Aktiviert oder Deaktiviert die Schaltfläche zum Laden der vorangehenden
     * PNML-Datei.
     *
     * @param state wenn <code>true</code>, wird die Schaltlfäche aktiviert. wenn
     *              <code>false</code>, wird die Schaltfläche deaktiviert.
     */
    void setNextActivationState(boolean state);

    /**
     * Aktiviert oder Deaktiviert die Schaltfläche zum Umschalten der Analyse beim
     * Aufbau des (partiellen) Erreichbarkeitsgraphen.
     *
     * @param state wenn <code>true</code>, wird die Schaltlfäche aktiviert. wenn
     *              <code>false</code>, wird die Schaltfläche deaktiviert.
     */
    void setPermanentAnalysisActivationState(boolean state);

    /**
     * Aktiviert oder Deaktiviert die Schaltfläche zum Laden der nachfolgenden
     * PNML-Datei.
     *
     * @param state wenn <code>true</code>, wird die Schaltlfäche aktiviert. wenn
     *              <code>false</code>, wird die Schaltfläche deaktiviert.
     */
    void setPrevActivationState(boolean state);

    /**
     * Aktiviert oder Deaktiviert die Schaltfläche für Redo-Operationen.
     *
     * @param state wenn <code>true</code>, wird die Schaltlfäche aktiviert. wenn
     *              <code>false</code>, wird die Schaltfläche deaktiviert.
     */
    void setRedoActivationState(boolean state);

    /**
     * Aktiviert oder deaktiviert den Menüeitnrag zum Neuladen einer PNML-Datei.
     *
     * @param state wenn <code>true</code>, wird der Eintrag aktiviert.<br>
     *              wenn <code>false</code>, wird der Eintrag deaktiviert.
     */
    void setReloadActivationState(boolean state);

    /**
     * Aktiviert oder Deaktiviert die Schaltfläche zum Löschen des (partiellen)
     * Erreichbarkeitsgraphen.
     *
     * @param state wenn <code>true</code>, wird die Schaltlfäche aktiviert. wenn
     *              <code>false</code>, wird die Schaltfläche deaktiviert.
     */
    void setRemEGActivationState(boolean state);

    /**
     * Aktiviert oder Deaktiviert die Schaltfläche zum Zurücksetzen des Petrinetzes.
     *
     * @param state wenn <code>true</code>, wird die Schaltlfäche aktiviert. wenn
     *              <code>false</code>, wird die Schaltfläche deaktiviert.
     */
    void setResetActivationState(boolean state);

    /**
     * Aktiviert oder Deaktiviert die Schaltfläche für Undo-Operationen.
     *
     * @param state wenn <code>true</code>, wird die Schaltlfäche aktiviert. wenn
     *              <code>false</code>, wird die Schaltfläche deaktiviert.
     */
    void setUndoActivationState(boolean state);

    /**
     * Zeigt ein Öffnendialogfenster zur Auswahl der PNML-Datei(en) an, die geöffnet
     * werden sollen.
     *
     * @param pnmlFileChooser der {@link PNMLFileChooser}, der angezeigt werden
     *                        soll.
     * @return den Status des Fensters nach dem Schließen.
     */
    int showOpenDialog(PNMLFileChooser pnmlFileChooser);
}
