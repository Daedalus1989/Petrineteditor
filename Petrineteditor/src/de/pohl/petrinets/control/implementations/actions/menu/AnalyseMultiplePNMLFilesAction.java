package de.pohl.petrinets.control.implementations.actions.menu;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

import de.pohl.petrinets.control.PetrinetEditorViewListener;

/**
 * Eine Aktion für die Analyse mehrerer PNML-Dateien.
 */
public class AnalyseMultiplePNMLFilesAction extends AbstractAction {
    private static final String ACTION_NAME = "Analyse mehrerer PNML-Dateien...";
    private static final String ACTION_DESCRIPTION = "Die Analyser mehrerer PNML-Dateien starten.";
    private PetrinetEditorViewListener petrinetEditorViewListener;

    /**
     * Erstellt eine {@link AnalyseMultiplePNMLFilesAction}.
     *
     * @param petrinetEditorViewListener ein {@link PetrinetEditorViewListener}.
     */
    public AnalyseMultiplePNMLFilesAction(PetrinetEditorViewListener petrinetEditorViewListener) {
        super(ACTION_NAME);
        this.petrinetEditorViewListener = petrinetEditorViewListener;
        putValue(SHORT_DESCRIPTION, ACTION_DESCRIPTION);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK | ActionEvent.ALT_MASK));
        putValue(DISPLAYED_MNEMONIC_INDEX_KEY, KeyEvent.VK_A);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        petrinetEditorViewListener.onAnalyseMultiplePetrinetClick();
    }
}
