package de.pohl.petrinets.control.implementations.actions.menu;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

import de.pohl.petrinets.control.PetrinetEditorViewListener;

/**
 * Eine Aktion für das Beenden des Programmes.
 */
public class ExitAction extends AbstractAction {
    private static final String ACTION_NAME = "Beenden";
    private static final String ACTION_DESCRIPTION = "Das Programm beenden.";
    private PetrinetEditorViewListener petrinetEditorViewListener;

    /**
     * Erstellt eine {@link ExitAction}.
     *
     * @param petrinetEditorViewListener ein {@link PetrinetEditorViewListener}.
     */
    public ExitAction(PetrinetEditorViewListener petrinetEditorViewListener) {
        super(ACTION_NAME);
        this.petrinetEditorViewListener = petrinetEditorViewListener;
        putValue(SHORT_DESCRIPTION, ACTION_DESCRIPTION);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
        putValue(DISPLAYED_MNEMONIC_INDEX_KEY, KeyEvent.VK_B);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        petrinetEditorViewListener.onExitClick();
    }
}
