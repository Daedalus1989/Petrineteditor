package de.pohl.petrinets.control.implementations.actions.menu;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

import de.pohl.petrinets.control.PetrinetEditorViewListener;

/**
 * Eine Aktion für das Anzeigen des Informationsdialogs.
 */
public class InfoAction extends AbstractAction {
    private static final String ACTION_NAME = "Info";
    private static final String ACTION_DESCRIPTION = "Das Informationsfenster anzeigen.";
    private PetrinetEditorViewListener petrinetEditorViewListener;

    /**
     * Erstellt eine {@link InfoAction}.
     *
     * @param petrinetEditorViewListener ein {@link PetrinetEditorViewListener}.
     */
    public InfoAction(PetrinetEditorViewListener petrinetEditorViewListener) {
        super(ACTION_NAME);
        this.petrinetEditorViewListener = petrinetEditorViewListener;
        putValue(SHORT_DESCRIPTION, ACTION_DESCRIPTION);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
        putValue(DISPLAYED_MNEMONIC_INDEX_KEY, KeyEvent.VK_I);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        petrinetEditorViewListener.onInfoClick();
    }
}
