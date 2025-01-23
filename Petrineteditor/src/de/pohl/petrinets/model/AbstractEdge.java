package de.pohl.petrinets.model;

/**
 * Abstrakte Modellklasse für die Kante eines Graphen.
 */
public abstract class AbstractEdge extends AbstractGraphElement {
    private String sourceNodeID;
    private String targetNodeID;

    /**
     * Ein Copy-Konstruktor zum Erstellen der Kopie einer {@link AbstractEdge}.
     *
     * @param abstractEdge die {@link AbstractEdge}, die kopiert werden soll.
     */
    public AbstractEdge(AbstractEdge abstractEdge) {
        super(abstractEdge);
        this.sourceNodeID = abstractEdge.sourceNodeID;
        this.targetNodeID = abstractEdge.targetNodeID;
    }

    /**
     * Erstellt eine neue {@link AbstractEdge}.
     *
     * @param id           Idendität der {@link AbstractEdge} als {@link String}.
     * @param sourceNodeID die ID des Quellknostens als {@link String}.
     * @param targetNodeID die ID des Zielknotens als {@link String}.
     */
    public AbstractEdge(String id, String sourceNodeID, String targetNodeID) {
        super(id);
        this.sourceNodeID = sourceNodeID;
        this.targetNodeID = targetNodeID;
    }

    /**
     * Liefert die ID des Quellknotens der {@link AbstractEdge}.
     *
     * @return Die ID des Quellknotens als {@link String}.
     */
    public String getSourceNodeID() {
        return sourceNodeID;
    }

    /**
     * Liefert die ID des Zielknotens der {@link AbstractEdge}.
     *
     * @return Die ID des Zielknotens als {@link String}.
     */
    public String getTargetNodeID() {
        return targetNodeID;
    }
}
