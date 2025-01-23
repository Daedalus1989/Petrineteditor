package de.pohl.petrinets.control.implementations.actions.toolbar;

import java.awt.event.ActionEvent;
import java.net.URL;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import de.pohl.petrinets.control.PetrinetEditorViewListener;

/**
 * Eine Aktion für das laden der vorangehenden PNML-Datei im Verzeichnis der
 * aktuell geladenen PNML-Datei.
 */
public class PreviousPNMLFileAction extends AbstractAction {
    private static final String ACTION_ICON = "/images/8666600_arrow_left_icon.png";
    private static final String ACTION_NAME = "";
    private static final String ACTION_DESCRIPTION = "Lädt die vorangehende PNML-Datei im Verzeichnis der aktuell geladenen PNML-Datei.\";";
    private PetrinetEditorViewListener petrinetEditorViewListener;

    /**
     * Erstellt eine {@link PreviousPNMLFileAction}.
     *
     * @param petrinetEditorViewListener ein {@link PetrinetEditorViewListener}.
     */
    public PreviousPNMLFileAction(PetrinetEditorViewListener petrinetEditorViewListener) {
        super(ACTION_NAME);
        this.petrinetEditorViewListener = petrinetEditorViewListener;
        URL iconURL = getClass().getResource(ACTION_ICON);
        this.putValue(SMALL_ICON, new ImageIcon(iconURL));
        this.putValue(SHORT_DESCRIPTION, ACTION_DESCRIPTION);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        petrinetEditorViewListener.onPrevGraphClick();
    }
}
