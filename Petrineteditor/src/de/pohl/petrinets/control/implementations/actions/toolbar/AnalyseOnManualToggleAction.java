package de.pohl.petrinets.control.implementations.actions.toolbar;

import java.awt.event.ActionEvent;
import java.net.URL;

import javax.swing.*;

import de.pohl.petrinets.control.PetrinetEditorViewListener;

/**
 * Eine Aktion für das Umschalten der automatischen Analyse eines Petrinetzes
 * beim Aufbau.
 */
public class AnalyseOnManualToggleAction extends AbstractAction {
    private static final String ACTION_ICON = "/images/8666656_check_circle_icon.png";
    private static final String ACTION_NAME = "";
    private static final String ACTION_DESCRIPTION = "Automatische Erkennung von Unbeschränktheit beim Aufbau des pEG aktivieren/deaktivieren.";
    private PetrinetEditorViewListener petrinetEditorViewListener;
    private JToggleButton tglPermanentAnalysis;

    /**
     * Erstellt eine {@link AnalyseOnManualToggleAction}.
     *
     * @param petrinetEditorViewListener ein {@link PetrinetEditorViewListener}.
     * @param tglPermanentAnalysis       ein {@link JToggleButton}.
     */
    public AnalyseOnManualToggleAction(PetrinetEditorViewListener petrinetEditorViewListener,
            JToggleButton tglPermanentAnalysis) {
        super(ACTION_NAME);
        this.petrinetEditorViewListener = petrinetEditorViewListener;
        this.tglPermanentAnalysis = tglPermanentAnalysis;
        URL iconURL = getClass().getResource(ACTION_ICON);
        putValue(SMALL_ICON, new ImageIcon(iconURL));
        putValue(SHORT_DESCRIPTION, ACTION_DESCRIPTION);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        petrinetEditorViewListener.togglePermanentAnalysis(tglPermanentAnalysis.isSelected());
    }
}
