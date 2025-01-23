package de.pohl.petrinets.control.implementations.usecases;

import java.beans.PropertyChangeListener;

import de.pohl.petrinets.control.implementations.SinglePetrinetController;
import de.pohl.petrinets.model.petrinet.AbstractPetrinet;
import de.pohl.petrinets.view.gui.components.PetrinetPanel;
import de.pohl.petrinets.view.model.PetrinetViewModel;

/**
 * Anwendungsfallklasse für die Initialisierung der Komponenten zur graphischen
 * Darstellung eines {@link AbstractPetrinet}.
 * <p>
 * Sie erstellt ein neues {@link PetrinetViewModel} und meldet dieses als
 * {@link PropertyChangeListener} über einen {@link SinglePetrinetController}
 * bei einem {@link AbstractPetrinet} an.
 * <p>
 * Die Klasse erstellt außerdem ein {@link PetrinetPanel} und übergibt dieses
 * als View-Komponente einem {@link SinglePetrinetController}.
 */
public class VisualPetrinetInitializer {
    private SinglePetrinetController petrinetController;

    /**
     * Erstellt einen neuen {@link VisualPetrinetInitializer}.
     *
     * @param petrinetController ein {@link SinglePetrinetController}.
     */
    public VisualPetrinetInitializer(SinglePetrinetController petrinetController) {
        this.petrinetController = petrinetController;
    }

    /**
     * Startet die Initialisierung der Komponenten zur graphischen Darstellung eines
     * {@link AbstractPetrinet}.
     */
    public void run() {
        PetrinetViewModel pVM = new PetrinetViewModel(petrinetController.getPNMLFileName());
        petrinetController.addPetrinetPropertyChangeListener(pVM);
        PetrinetPanel petrinetGraphPanel = new PetrinetPanel(pVM, petrinetController);
        petrinetController.setPetrinetView(petrinetGraphPanel);
    }
}
