package de.pohl.petrinets.control.implementations.actions.menu;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;

import de.pohl.petrinets.control.PetrinetEditorViewListener;

/**
 * Eine Aktion für das Schließen von Tabs des Tabbed Document Interface (TDI).
 */
public class CloseTabAction extends AbstractAction {
    private static final String ACTION_NAME = "Tab schließen";
    private static final String ACTION_DESCRIPTION = "Schließt das den aktuellen Tab.";
    private PetrinetEditorViewListener petrinetEditorViewListener;

    /**
     * Erstellt eine {@link CloseTabAction}.
     *
     * @param petrinetEditorViewListener ein {@link PetrinetEditorViewListener}.
     */
    public CloseTabAction(PetrinetEditorViewListener petrinetEditorViewListener) {
        super(ACTION_NAME);
        this.petrinetEditorViewListener = petrinetEditorViewListener;
        putValue(SHORT_DESCRIPTION, ACTION_DESCRIPTION);
        putValue(DISPLAYED_MNEMONIC_INDEX_KEY, KeyEvent.VK_T);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        petrinetEditorViewListener.onCloseTabClick();
    }
}
