package de.pohl.petrinets.control.implementations.usecases;

import de.pohl.petrinets.control.implementations.SinglePetrinetController;
import de.pohl.petrinets.model.petrinet.AbstractPetrinet;
import de.pohl.petrinets.model.reachabilitygraph.AbstractReachabilitygraph;

/**
 * Eine Anwendungsfallklasse zum Zurücksetzen eines {@link AbstractPetrinet} auf
 * die aktuell definierte Anfangsmarkierung.
 * <p>
 * Optional kann auch der zum {@link AbstractPetrinet} gehörige
 * {@link AbstractReachabilitygraph} reinitialisiert werden.
 *
 * @see RGraphReinitializer
 */
public class PetrinetResetter {
    private boolean reinitializeRGraph;
    private SinglePetrinetController petrinetController;

    /**
     * Erstellt einen neuen {@link PetrinetResetter}.
     *
     * @param petrinetController der {@link SinglePetrinetController} dessen
     *                           {@link AbstractPetrinet} auf die aktuell definierte
     *                           Anfangsmarkierung zurückgesetzt werden soll.
     * @param reinitializeRGraph wenn <code>true</code> wird auch der
     *                           {@link AbstractReachabilitygraph} zurückgesetzt.
     */
    public PetrinetResetter(SinglePetrinetController petrinetController, boolean reinitializeRGraph) {
        this.reinitializeRGraph = reinitializeRGraph;
        this.petrinetController = petrinetController;
    }

    /**
     * Startet das Zurücksetzen des {@link AbstractPetrinet} auf die aktuell
     * definierte Anfangsmarkierung.
     */
    public void run() {
        petrinetController.resetPetrinetToInitialMarking();
        if (reinitializeRGraph) {
            RGraphReinitializer rGraphReinitializer = new RGraphReinitializer(petrinetController);
            rGraphReinitializer.run();
        }
    }
}
