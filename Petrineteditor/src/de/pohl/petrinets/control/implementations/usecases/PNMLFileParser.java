package de.pohl.petrinets.control.implementations.usecases;

import de.pohl.petrinets.model.petrinet.*;
import de.pohl.petrinets.model.reachabilitygraph.AbstractReachabilitygraph;
import propra.pnml.PNMLWopedParser;

/**
 * Eine Anwendungsfallklasse für das Parsen einer PNML-Datei.
 */
public class PNMLFileParser extends PNMLWopedParser {
    private AbstractPetrinet petrinetModel;
    private AbstractReachabilitygraph rGraphModel;

    /**
     * Erstellt einen neuen {@link PNMLFileParser}.
     *
     * @param petrinetModel ein {@link AbstractPetrinet}.
     * @param rGraphModel   ein {@link AbstractReachabilitygraph}.
     */
    public PNMLFileParser(AbstractPetrinet petrinetModel, AbstractReachabilitygraph rGraphModel) {
        super(petrinetModel.getPNMLFile());
        this.petrinetModel = petrinetModel;
        this.rGraphModel = rGraphModel;
    }

    /**
     * Erstellt einen neuen {@link Arc}.
     */
    @Override
    public void newArc(String id, String source, String target) {
        super.newArc(id, source, target);
        petrinetModel.addArc(id, source, target);
    }

    /**
     * Erstellt eine neue {@link Place}.
     */
    @Override
    public void newPlace(String id) {
        super.newPlace(id);
        petrinetModel.addPlace(id);
    }

    /**
     * Erstellt eine neue {@link Transition}.
     */
    @Override
    public void newTransition(String id) {
        super.newTransition(id);
        petrinetModel.addTransition(id);
    }

    /**
     * Startet das Parsen der angegebenen PNML-Datei und initiiert den
     * Erreichbarkeitsgraphen.
     */
    public void run() {
        this.initParser();
        this.parse();
        petrinetModel.checkAllTransitionstate();
        rGraphModel.addInitialMarking(petrinetModel.getInitialMarking(), petrinetModel.getActiveTransitionIDs());
    }

    /**
     * Ändert den Namen eines {@link AbstractPetrinetNode}.
     */
    @Override
    public void setName(String id, String name) {
        super.setName(id, name);
        petrinetModel.setPetrinetNodeName(id, name);
    }

    /**
     * Ändert die Position eines {@link AbstractPetrinetNode}.
     */
    @Override
    public void setPosition(String id, String x, String y) {
        super.setPosition(id, x, y);
        int intX = Integer.parseInt(x);
        int intY = Integer.parseInt(y);
        petrinetModel.setPetrinetNodePosition(id, intX, intY);
    }

    /**
     * Ändert die Anzahl der initialen Marken einer {@link Place}.
     */
    @Override
    public void setTokens(String id, String tokens) {
        super.setTokens(id, tokens);
        int intTokens = Integer.parseInt(tokens);
        petrinetModel.initPlaceTokens(id, intTokens);
    }
}
