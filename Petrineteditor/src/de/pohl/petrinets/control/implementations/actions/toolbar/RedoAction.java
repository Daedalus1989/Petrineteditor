package de.pohl.petrinets.control.implementations.actions.toolbar;

import java.awt.event.ActionEvent;
import java.net.URL;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import de.pohl.petrinets.control.PetrinetEditorViewListener;

/**
 * Eine Aktion für das Rückgängigmachen einer Änderung an den Graphmodellen.
 */
public class RedoAction extends AbstractAction {
    private static final String ACTION_ICON = "/images/8666729_rotate_cw_icon.png";
    private static final String ACTION_NAME = "";
    private static final String ACTION_DESCRIPTION = "Die letzte Aktion wiederherstellen.";
    private PetrinetEditorViewListener petrinetEditorViewListener;

    /**
     * Erstellt eine {@link RedoAction}.
     *
     * @param petrinetEditorViewListener ein {@link PetrinetEditorViewListener}.
     */
    public RedoAction(PetrinetEditorViewListener petrinetEditorViewListener) {
        super(ACTION_NAME);
        this.petrinetEditorViewListener = petrinetEditorViewListener;
        URL iconURL = getClass().getResource(ACTION_ICON);
        this.putValue(SMALL_ICON, new ImageIcon(iconURL));
        this.putValue(SHORT_DESCRIPTION, ACTION_DESCRIPTION);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        petrinetEditorViewListener.onRedoClick();
    }
}
