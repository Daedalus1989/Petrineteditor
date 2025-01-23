package de.pohl.petrinets.control.implementations.actions.toolbar;

import java.awt.event.ActionEvent;
import java.net.URL;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import de.pohl.petrinets.control.PetrinetEditorViewListener;

/**
 * Eine Aktion für das Erhöhen der Marken einer selektierten Stelle.
 */
public class IncreaseTokenAction extends AbstractAction {
    private static final String ACTION_ICON = "/images/8666749_plus_add_icon.png";
    private static final String ACTION_NAME = "";
    private static final String ACTION_DESCRIPTION = "Markierung der Stelle um 1 erhöhen und Anfangsmarkierung ersetzen.";
    private PetrinetEditorViewListener petrinetEditorViewListener;

    /**
     * Erstellt eine {@link IncreaseTokenAction}.
     *
     * @param petrinetEditorViewListener ein {@link PetrinetEditorViewListener}.
     */
    public IncreaseTokenAction(PetrinetEditorViewListener petrinetEditorViewListener) {
        super(ACTION_NAME);
        this.petrinetEditorViewListener = petrinetEditorViewListener;
        URL iconURL = getClass().getResource(ACTION_ICON);
        this.putValue(SMALL_ICON, new ImageIcon(iconURL));
        this.putValue(SHORT_DESCRIPTION, ACTION_DESCRIPTION);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        petrinetEditorViewListener.onIncTokenClick();
    }
}
