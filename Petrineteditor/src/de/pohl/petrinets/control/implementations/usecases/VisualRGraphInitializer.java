package de.pohl.petrinets.control.implementations.usecases;

import java.beans.PropertyChangeListener;

import de.pohl.petrinets.control.implementations.SinglePetrinetController;
import de.pohl.petrinets.model.reachabilitygraph.AbstractReachabilitygraph;
import de.pohl.petrinets.view.gui.components.RGraphPanel;
import de.pohl.petrinets.view.model.RGraphViewModel;

/**
 * Anwendungsfallklasse für die Initialisierung der Komponenten zur graphischen
 * Darstellung eines {@link AbstractReachabilitygraph}.
 * <p>
 * Sie erstellt ein neues {@link RGraphViewModel} und meldet dieses als
 * {@link PropertyChangeListener} über einen {@link SinglePetrinetController}
 * bei einem {@link AbstractReachabilitygraph} an.
 * <p>
 * Die Klasse erstellt außerdem ein {@link RGraphPanel} und übergibt dieses als
 * View-Komponente einem {@link SinglePetrinetController}.
 */
public class VisualRGraphInitializer {
    private SinglePetrinetController petrinetController;

    /**
     * Erstellt einen neuen {@link VisualRGraphInitializer}.
     *
     * @param petrinetController ein {@link SinglePetrinetController}.
     */
    public VisualRGraphInitializer(SinglePetrinetController petrinetController) {
        this.petrinetController = petrinetController;
    }

    /**
     * Startet die Initialisierung der Komponenten zur graphischen Darstellung eines
     * {@link AbstractReachabilitygraph}.
     */
    public void run() {
        RGraphViewModel rgVM = new RGraphViewModel(petrinetController.getPNMLFileName());
        petrinetController.addRGraphPropertyChangeListener(rgVM);
        RGraphPanel rGraphPanel = new RGraphPanel(rgVM, petrinetController);
        petrinetController.setRGraphView(rGraphPanel);
    }
}
