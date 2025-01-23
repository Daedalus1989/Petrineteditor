package de.pohl.petrinets.control.implementations.actions.menu;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

import de.pohl.petrinets.control.PetrinetEditorViewListener;

/**
 * Eine Aktion das Neuladen der geladenen PNML-Datei.
 */
public class ReloadPNMLFileAction extends AbstractAction {
    private static final String ACTION_NAME = "PNML-Datei neu Laden";
    private static final String ACTION_DESCRIPTION = "Die aktuell geladene PNML-Datei neu laden.";
    private PetrinetEditorViewListener petrinetEditorViewListener;

    /**
     * Erstellt eine {@link ReloadPNMLFileAction}.
     *
     * @param petrinetEditorViewListener ein {@link PetrinetEditorViewListener}.
     */
    public ReloadPNMLFileAction(PetrinetEditorViewListener petrinetEditorViewListener) {
        super(ACTION_NAME);
        this.petrinetEditorViewListener = petrinetEditorViewListener;
        putValue(SHORT_DESCRIPTION, ACTION_DESCRIPTION);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
        putValue(DISPLAYED_MNEMONIC_INDEX_KEY, KeyEvent.VK_N);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        petrinetEditorViewListener.onReloadClick();
    }
}
