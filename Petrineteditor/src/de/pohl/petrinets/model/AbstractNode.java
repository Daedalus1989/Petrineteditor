package de.pohl.petrinets.model;

import java.util.ArrayList;

/**
 * Abstrakte Modellklasse für den Knoten eines Graphen.
 */
public abstract class AbstractNode extends AbstractGraphElement {
    private ArrayList<String> inboundEdgeIDs;
    private ArrayList<String> outboundEdgeIDs;

    /**
     * Ein Copy-Konstruktor zum Erstellen einer Kopie eines {@link AbstractNode}.
     *
     * @param abstractNode die {@link AbstractNode}, die kopiert werden soll.
     */
    public AbstractNode(AbstractNode abstractNode) {
        super(abstractNode);
        this.inboundEdgeIDs = new ArrayList<>();
        this.outboundEdgeIDs = new ArrayList<>();
        if (abstractNode.inboundEdgeIDs != null) {
            this.inboundEdgeIDs.addAll(abstractNode.inboundEdgeIDs);
        }
        if (abstractNode.outboundEdgeIDs != null) {
            this.outboundEdgeIDs.addAll(abstractNode.outboundEdgeIDs);
        }
    }

    /**
     * Erstellt eine neue {@link AbstractNode}.
     *
     * @param id die ID des {@link AbstractNode} als {@link String}.
     */
    public AbstractNode(String id) {
        super(id);
        inboundEdgeIDs = new ArrayList<>();
        outboundEdgeIDs = new ArrayList<>();
    }

    /**
     * Fügt dem Knoten die ID einer eingehenden Kante hinzu.
     *
     * @param inboundEdge die ID der eingehenden Kante als {@link String}.
     */
    public void addInbound(String inboundEdge) {
        this.inboundEdgeIDs.add(inboundEdge);
    }

    /**
     * Fügt dem Knoten die ID einer ausgehenden Kante hinzu.
     *
     * @param outboundEdge die ID der ausgehenden Kante als {@link String}.
     */
    public void addOutbound(String outboundEdge) {
        this.outboundEdgeIDs.add(outboundEdge);
    }

    /**
     * Liefert eine Liste mit den IDs der eingehenden Kanten zurück.
     *
     * @return Eine {@link ArrayList} mit {@link String}-Werten für die IDs der
     *         eingehenden Kanten.
     */
    public ArrayList<String> getInboundEdgeIDs() {
        return new ArrayList<>(inboundEdgeIDs);
    }

    /**
     * Liefert eine Liste mit den IDs der ausgehenden Kanten zurück.
     *
     * @return Eine {@link ArrayList} mit {@link String}-Werten für die IDs der
     *         ausgehenden Kanten.
     */
    public ArrayList<String> getOutboundEdgeIDs() {
        return new ArrayList<>(outboundEdgeIDs);
    }
}
