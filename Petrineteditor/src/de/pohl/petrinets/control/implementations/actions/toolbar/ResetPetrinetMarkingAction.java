package de.pohl.petrinets.control.implementations.actions.toolbar;

import java.awt.event.ActionEvent;
import java.net.URL;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import de.pohl.petrinets.control.PetrinetEditorViewListener;

/**
 * Eine Aktion für das Zurücksetzen der Markierung des Petrinetzes auf die
 * Anfangsmarkierung.
 */
public class ResetPetrinetMarkingAction extends AbstractAction {
    private static final String ACTION_ICON = "/images/8666635_refresh_ccw_icon.png";
    private static final String ACTION_NAME = "";
    private static final String ACTION_DESCRIPTION = "Die Markierung des Petrinetzes auf aktuell definierte Anfangsmarkierung zurücksetzen.";
    private PetrinetEditorViewListener petrinetEditorViewListener;

    /**
     * Erstellt eine {@link ResetPetrinetMarkingAction}.
     *
     * @param petrinetEditorViewListener ein {@link PetrinetEditorViewListener}.
     */
    public ResetPetrinetMarkingAction(PetrinetEditorViewListener petrinetEditorViewListener) {
        super(ACTION_NAME);
        this.petrinetEditorViewListener = petrinetEditorViewListener;
        URL iconURL = getClass().getResource(ACTION_ICON);
        this.putValue(SMALL_ICON, new ImageIcon(iconURL));
        this.putValue(SHORT_DESCRIPTION, ACTION_DESCRIPTION);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        petrinetEditorViewListener.onResetClick();
    }
}
