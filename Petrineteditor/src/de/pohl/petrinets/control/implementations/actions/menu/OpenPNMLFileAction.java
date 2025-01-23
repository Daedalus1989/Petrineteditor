package de.pohl.petrinets.control.implementations.actions.menu;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

import de.pohl.petrinets.control.PetrinetEditorViewListener;

/**
 * Eine Aktion das Anzeigen des Öffnendialogs.
 */
public class OpenPNMLFileAction extends AbstractAction {
    private static final String ACTION_NAME = "Öffnen...";
    private static final String ACTION_DESCRIPTION = "Eine PNML-Datei öffnen.";
    private PetrinetEditorViewListener petrinetEditorViewListener;

    /**
     * Erstellt eine {@link OpenPNMLFileAction}.
     *
     * @param petrinetEditorViewListener ein {@link PetrinetEditorViewListener}.
     */
    public OpenPNMLFileAction(PetrinetEditorViewListener petrinetEditorViewListener) {
        super(ACTION_NAME);
        this.petrinetEditorViewListener = petrinetEditorViewListener;
        putValue(SHORT_DESCRIPTION, ACTION_DESCRIPTION);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        // 0xD6 = Ö
        putValue(DISPLAYED_MNEMONIC_INDEX_KEY, 0xD6);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        petrinetEditorViewListener.onOpenPNMLFileClick();
    }
}
