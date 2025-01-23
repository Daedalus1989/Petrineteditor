package de.pohl.petrinets.control.implementations.usecases;

import de.pohl.petrinets.control.implementations.SinglePetrinetController;
import de.pohl.petrinets.model.reachabilitygraph.AbstractReachabilitygraph;
import de.pohl.petrinets.model.reachabilitygraph.Reachabilitygraph;

/**
 * Anwendungsfallklasse für die Reinitialisierung eines
 * {@link AbstractReachabilitygraph}.
 * <p>
 * Sie erstellt eine neue Instanz eines {@link AbstractReachabilitygraph} und
 * startet die Initialisierung der Komponenten zur Darstellung des
 * {@link AbstractReachabilitygraph}.
 *
 * @see VisualRGraphInitializer
 */
public class RGraphReinitializer {
    private SinglePetrinetController petrinetController;

    /**
     * Erstellt einen neuen {@link RGraphReinitializer}.
     *
     * @param petrinetController ein {@link SinglePetrinetController}, dessen
     *                           {@link AbstractReachabilitygraph} reinitialisiert
     *                           werden soll.
     */
    public RGraphReinitializer(SinglePetrinetController petrinetController) {
        this.petrinetController = petrinetController;
    }

    /**
     * Startet die Reinitialiserung eines {@link AbstractReachabilitygraph} und
     * seiner visuellen Komponenten mit {@link VisualRGraphInitializer}.
     *
     * @see VisualRGraphInitializer
     */
    public void run() {
        AbstractReachabilitygraph rGraph = new Reachabilitygraph();
        petrinetController.setRGraphModel(rGraph);
        VisualRGraphInitializer visualRGraphInitializer = new VisualRGraphInitializer(petrinetController);
        visualRGraphInitializer.run();
        rGraph.addInitialMarking(petrinetController.getPetrinetInitialMarking(),
                petrinetController.getPetrinetActiveTransitionIDs());
    }
}
