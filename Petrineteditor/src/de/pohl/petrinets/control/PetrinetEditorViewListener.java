package de.pohl.petrinets.control;

import de.pohl.petrinets.view.PetrinetEditorView;

/**
 * Eine Schnittstellenklasse die Methoden definiert, die eine Klasse
 * implementieren muss, die auf Ereignisse der {@link PetrinetEditorView}
 * reagiert.
 */
public interface PetrinetEditorViewListener {
    /**
     * Wird aufgerufen, wenn ein ein Wechsel des aktiven Tabs im Tabbed Document
     * Interface (TDI) stattgefunden hat.
     *
     * @param index der Index des neuen aktiven Tabs als {@link Integer}.
     */
    void onActiveTabChanged(int index);

    /**
     * Wird aufgerufen, wenn die Beschränktheitsanalyse für eine Menge von Petrinetz
     * Dateien durchgeführt werden soll.
     */
    void onAnalyseMultiplePetrinetClick();

    /**
     * Wird aufgerufen, wenn die Beschränktheitsanalyse für ein Petrinetz
     * durchgeführt werden soll.
     */
    void onAnaylseSingleClick();

    /**
     * Wird aufgerufen, wenn ein Tab des Tabbed Document Interface (TDI) geschlossen
     * werden soll.
     */
    void onCloseTabClick();

    /**
     * Wird aufgerufen, wenn die Marken der aktuell fukossierten Stelle verringert
     * werden sollen.
     */
    void onDecTokenClick();

    /**
     * Wird aufgerufen, wenn das Programm beendet werden soll.
     */
    void onExitClick();

    /**
     * Wird aufgerufen, wenn die Marken der aktuell fukossierten Stelle erhöht
     * werden sollen.
     */
    void onIncTokenClick();

    /**
     * Wird aufgerufen, wenn der Info-Dialog angezeigt werden soll.
     */
    void onInfoClick();

    /**
     * Wird aufgerufen, wenn die nachfolgende PNML-Datei im Verzeichnis der aktuell
     * geöffneten PNML-Datei geöffnet werden soll.
     */
    void onNextPNMLFileClick();

    /**
     * Wird aufgerufen, wenn eine einzelne PNML-Datei geöffnet werden soll.
     */
    void onOpenPNMLFileClick();

    /**
     * Wird aufgerufen, wenn die vorangehende PNML-Datei im Verzeichnis der aktuell
     * geöffneten PNML-Datei geöffnet werden soll.
     */
    void onPrevGraphClick();

    /**
     * Wird aufgerufen, wenn eine Änderung der Markierung des geladenen Petrinetzes
     * Wiederhergestellt werden soll.
     */
    void onRedoClick();

    /**
     * Wird aufgerufen, wenn die aktuell geöffnete PNML-Datei neu geladen werden
     * soll.
     */
    void onReloadClick();

    /**
     * Wird aufgerufen, wenn der (partielle) Erreichbarkeitsgraph gelöscht werden
     * soll.
     */
    void onRemEGClick();

    /**
     * Wird aufgerufen, wenn die Markierung des aktuell geladenen Petrinetzes
     * zurückgesetzt werden soll.
     * <p>
     * Dabei wird das aktuell geladene Petrinetz auf die Anfangsmarkierung
     * zurückgesetzt. Diese entspricht nicht zwangsweise der Anfangsmarkierung der
     * entsprechenden PNML-Datei.
     */
    void onResetClick();

    /**
     * Wird aufgerufen, wenn eine Änderung der Markierung des geladenen Petrinetzes
     * rückgängig gemacht werden soll.
     */
    void onUndoClick();

    /**
     * Wird aufgerufen, wenn während des manuellen Aufbaus des (partiellen)
     * Erreichbarkeitsgraphen eine Beschräntkheitsanalyse durchgeführt werden soll.
     *
     * @param status wenn <code>true</code> wird die Unbeschränktheit schon beim
     *               Aufbau erkannt.
     */
    void togglePermanentAnalysis(boolean status);
}
