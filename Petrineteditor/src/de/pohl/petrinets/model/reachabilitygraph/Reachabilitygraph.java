package de.pohl.petrinets.model.reachabilitygraph;

import java.util.ArrayList;

import de.pohl.petrinets.control.PetrinetEditorGraphProperties;
import de.pohl.petrinets.model.petrinet.AbstractPetrinet;
import de.pohl.petrinets.model.petrinet.Transition;

/**
 * Entitätsklasse eines Erreichbarkeitsgraphen.
 * <p>
 * Implementiert einen {@link AbstractReachabilitygraph}.
 */
public class Reachabilitygraph extends AbstractReachabilitygraph {
    private ArrayList<RGraphEdge> edges;
    private ArrayList<RGraphNode> nodes;

    /**
     * Erstellt einen {@link Reachabilitygraph}.
     */
    public Reachabilitygraph() {
        this.edges = new ArrayList<>();
        this.nodes = new ArrayList<>();
    }

    @Override
    public void addInitialMarking(ArrayList<Integer> marking, ArrayList<String> activeTransitionIDs) {
        if (nodes.isEmpty()) {
            addNode(marking, activeTransitionIDs, true);
        }
    }

    @Override
    public String addMarking(String transitionID, String transitionName, ArrayList<Integer> oldActualMarking,
            ArrayList<Integer> newActualMarking, ArrayList<String> activeTransitionIDs, boolean returnExisting) {
        RGraphNode sourceNode = getNode(oldActualMarking.toString());
        RGraphNode targetNode = getNode(newActualMarking.toString());
        if (targetNode == null) {
            // Zielknoten und Kante exisitert nicht.
            targetNode = addNode(newActualMarking, activeTransitionIDs, false);
        }
        String edgeID = generateEdgeID(transitionID, sourceNode.getID(), targetNode.getID());
        if (getEdge(edgeID) == null) {
            String edge = addEdge(edgeID, transitionID, transitionName, sourceNode, targetNode);
            sourceNode.removeRemainingActiveTransitionID(transitionID);
            return edge;
        }
        if (returnExisting) return edgeID;
        return null;
    }

    @Override
    public int countEdges() {
        return edges.size();
    }

    @Override
    public int countNodes() {
        return nodes.size();
    }

    @Override
    public String getEdgeSourceID(String edgeID) {
        return getEdge(edgeID).getSourceNodeID();
    }

    @Override
    public String getEdgeTargetID(String edgeID) {
        return getEdge(edgeID).getTargetNodeID();
    }

    @Override
    public String getEdgeTransitionID(String edgeID) {
        return getEdge(edgeID).getTransitionID();
    }

    /**
     * Liefert den Knoten mit der initalen Markierung des Erreichbarkeitsgraphen
     * zurück.
     *
     * @return der Knoten mit der initialen Markierung.
     */
    @Override
    public String getInitialNodeID() {
        return this.nodes.get(0).getID();
    }

    @Override
    public ArrayList<String> getNodeInboundEdgeIDs(String nodeID) {
        return getNode(nodeID).getInboundEdgeIDs();
    }

    @Override
    public String getNodeLabel(String nodeID) {
        return getNode(nodeID).getLabel();
    }

    /**
     * Liefert die Markierung des angegebenen Knotens im Erreichbarkeitsgraphen
     * zurück.
     *
     * @param nodeID die ID des Knotens, dessen Markierung gesucht wird.
     * @return Die Markierung.
     */
    @Override
    public ArrayList<Integer> getNodeMarking(String nodeID) {
        return getNode(nodeID).getMarking();
    }

    @Override
    public ArrayList<String> getNodeOutboundEdges(String nodeID) {
        return getNode(nodeID).getOutboundEdgeIDs();
    }

    @Override
    public void highlightTransition(String transitionID, ArrayList<Integer> oldActualMarking,
            ArrayList<Integer> newActualMarking) {
        String nodeID = getMarkingNodeID(newActualMarking);
        String edgeID = generateEdgeID(transitionID, getMarkingNodeID(oldActualMarking),
                getMarkingNodeID(newActualMarking));
        toggleHighlightNode(nodeID);
        toggleHighlightEdge(edgeID);
    }

    /**
     * Überprüft, ob es sich bei der angegebenen ID um einen Knoten des
     * Erreichbarkeitsgraphen handelt.
     *
     * @param nodeID die zu prüfende ID.
     * @return {@code true}, wenn die ID einem Knoten zugeordnet werden konnte.
     */
    @Override
    public boolean isReachabilitygraphNode(String nodeID) {
        return getNode(nodeID) != null;
    }

    @Override
    public void restoreState(RGraphMemento saveState) {
        ArrayList<RGraphEdge> saveStateEdges = saveState.getEdges();
        ArrayList<RGraphNode> saveStateNodes = saveState.getNodes();
        boolean structuralChanges = hasStructuralChanges(saveStateEdges, saveStateNodes);
        if (nodes.size() == 1) {
            // Es gibt nur den Wurzelknoten
            if (!nodes.get(0).getID().equals(saveStateNodes.get(0).getID())) {
                // Stimmen die IDs der Wurzelknoten nicht überein, hat sich die
                // Anfangsmarkierung des Petrinetzes geändert.
                // In diesem Fall kann der Wurzelknoten gelöscht werden und dieser wird im
                // nachfolgenen Prozess wiederhergestellt.
                RGraphNode oldNode = nodes.get(0);
                nodes.remove(0);
                this.firePropertyChange(PetrinetEditorGraphProperties.NODE, oldNode, null);
            }
        }
        if (structuralChanges) {
            if (nodes.size() < saveStateNodes.size()) {
                restoreDeletedNodes(saveStateNodes);
            }
            if (edges.size() < saveStateEdges.size()) {
                restoreDeletedEdges(saveStateEdges);
            }
            if (edges.size() > saveStateEdges.size()) {
                removeAddedEdges(saveStateEdges);
            }
            if (nodes.size() > saveStateNodes.size()) {
                removeAddedNodes(saveStateNodes);
            }
        }
        restoreNodeProperties(saveStateNodes);
        restoreEdgeProperties(saveStateEdges);
    }

    @Override
    public RGraphMemento saveState() {
        return new RGraphMemento(edges, nodes);
    }

    @Override
    public void setUnboundedCause(ArrayList<String> unboundedCauseEdgeIDs, String sourcemarkingNodeID,
            String targetmarkingNodeID) {
        resetUnboundedCauseFlag();
        for (String edgeID : unboundedCauseEdgeIDs) {
            RGraphEdge edge = getEdge(edgeID);
            RGraphNode sourceNode = getNode(edge.getSourceNodeID());
            sourceNode.setElementOfUnboundedcause(true);
            edge.setElementOfUnboundedcause(true);
            if (unboundedCauseEdgeIDs.indexOf(edgeID) == unboundedCauseEdgeIDs.size() - 1) {
                RGraphNode targetNode = getNode(edge.getTargetNodeID());
                targetNode.setElementOfUnboundedcause(true);
            }
        }
        RGraphNode sourcemarkingNode = getNode(sourcemarkingNodeID);
        RGraphNode targetmarkingNode = getNode(targetmarkingNodeID);
        sourcemarkingNode.setSourcemarkingOfUnboundedcause(true);
        targetmarkingNode.setTargetmarkingOfUnboundedcause(true);
    }

    /**
     * Fügt eine Kante zum {@link Reachabilitygraph} hinzu, sofern diese noch nicht
     * exisitert.<br>
     * Dies ist genau dann der Fall, wenn es keinen durch eine Transition
     * ausgelösten Übergang von einer Ursprungsmarkierung zu einer Zielmarkierung
     * gibt.
     *
     * @param edgeID         die ID der {@link RGraphEdge} als {@link String}.
     * @param transitionID   die ID der {@link Transition}, auf die die
     *                       {@link RGraphEdge} referenziert als {@link String}.
     * @param transitionName der Name der {@link Transition}, auf die die
     *                       {@link RGraphEdge} referenziert als {@link String}.
     * @param sourceNode     der Quellknoten der Kante als {@link RGraphNode}.
     * @param targetNode     der Zielknoten der Kante als {@link RGraphNode}.
     * @return Die neue {@link RGraphEdge} des {@link Reachabilitygraph} oder
     *         <code>null</code>, wenn diese bereits existiert.
     */
    private String addEdge(String edgeID, String transitionID, String transitionName, RGraphNode sourceNode,
            RGraphNode targetNode) {
        if (getEdge(edgeID) == null) {
            RGraphEdge newEdge = new RGraphEdge(edgeID, transitionID, transitionName, sourceNode.getID(),
                    targetNode.getID());
            forewardGraphPCSToGraphElement(newEdge);
            this.edges.add(newEdge);
            sourceNode.addOutbound(newEdge.getID());
            targetNode.addInbound(newEdge.getID());
            this.firePropertyChange(PetrinetEditorGraphProperties.EDGE, null, newEdge);
            return edgeID;
        }
        return null;
    }

    /**
     * Fügt einen Knoten zum {@link Reachabilitygraph} hinzu.
     *
     * @param marking             die Markierung des {@link AbstractPetrinet}, die
     *                            der {@link RGraphNode} repräsentiert als
     *                            {@link ArrayList} mit {@link Integer}.
     * @param activeTransitionIDs eine {@link ArrayList} mit {@link String}-Werten
     *                            der IDs der auf der Markierung aktiven
     *                            {@link Transition}.
     * @param initialNode         wenn <code>true</code>, wird der Knoten als
     *                            Wurzelknoten markiert.
     * @return Der neue {@link RGraphNode} des {@link Reachabilitygraph} oder
     *         <code>null</code>, wenn dieser bereits existiert.
     */
    private RGraphNode addNode(ArrayList<Integer> marking, ArrayList<String> activeTransitionIDs, boolean initialNode) {
        if (getNode(marking) == null) {
            RGraphNode newNode = new RGraphNode(marking.toString());
            forewardGraphPCSToGraphElement(newNode);
            this.nodes.add(newNode);
            newNode.setMarking(marking);
            this.firePropertyChange(PetrinetEditorGraphProperties.NODE, null, newNode);
            if (initialNode) {
                newNode.setInitalmarking(true);
            }
            for (String activeTransitionID : activeTransitionIDs) {
                newNode.addRemainingActiveTransitionID(activeTransitionID);
            }
            return newNode;
        }
        return null;
    }

    /**
     * Erzeugt die IDs für {@link RGraphEdge}. Diese setzt sich aus der ID der
     * geschalteten {@link Transition}, sowie aus den IDs des Quell- und Zielknotens
     * zusammen.<br>
     * Anders als in einem {@link AbstractPetrinet} wird eine {@link RGraphEdge} nur
     * durch diese 3 Eigenschaften eindeutig identifiziert.
     *
     * @param transitionID die ID der geschalteten {@link Transition} als
     *                     {@link String}.
     * @param sourceNodeID die ID des Quellknotens als {@link String}.
     * @param targetNodeID die ID des Zielknotens als {@link String}.
     * @return Eine eindeutige ID einer {@link RGraphEdge} als {@link String}.
     */
    private String generateEdgeID(String transitionID, String sourceNodeID, String targetNodeID) {
        return transitionID + sourceNodeID + targetNodeID;
    }

    /**
     * Liefert eine {@link RGraphEdge} des {@link Reachabilitygraph} zurück.
     *
     * @param edgeID die ID der gesuchten {@link RGraphEdge} als {@link String}.
     * @return Die gesuchte {@link RGraphEdge} oder <code>null</code>, wenn diese
     *         nicht existiert.
     */
    private RGraphEdge getEdge(String edgeID) {
        for (RGraphEdge edge : edges) {
            if (edge.getID().equals(edgeID)) {
                return edge;
            }
        }
        return null;
    }

    /**
     * Liefert die ID eines {@link RGraphNode}, der die angegebene Markierung
     * enthält.
     *
     * @param marking die Markierung des Knotens, dessen ID gesucht wird als
     *                {@link ArrayList} mit {@link Integer}.
     * @return Die ID des {@link RGraphEdge} als String oder <code>null</code>, wenn
     *         dieser nicht existiert.
     */
    private String getMarkingNodeID(ArrayList<Integer> marking) {
        return getNode(marking).getID();
    }

    /**
     * Gibt den gesuchten {@link RGraphNode} des {@link AbstractReachabilitygraph}
     * zurück.
     *
     * @param marking die Markierung als {@link ArrayList} mit {@link Integer} des
     *                gesuchten Knotens.
     * @return der gesuchte {@link RGraphNode} oder <code>null</code>, wenn
     *         gesuchter {@link RGraphNode} nicht exisitert.
     */
    private RGraphNode getNode(ArrayList<Integer> marking) {
        for (RGraphNode node : nodes) {
            if (node.getMarking().equals(marking)) {
                return node;
            }
        }
        return null;
    }

    /**
     * Liefert einen {@link RGraphNode} des {@link Reachabilitygraph} zurück.
     *
     * @param nodeID die ID des gesuchten {@link RGraphEdge} als {@link String}.
     * @return der gesuchte {@link RGraphNode} oder <code>null</code>, wenn
     *         gesuchter {@link RGraphNode} nicht exisitert.
     */
    private RGraphNode getNode(String nodeID) {
        for (RGraphNode node : nodes) {
            if (node.getID().equals(nodeID)) {
                return node;
            }
        }
        return null;
    }

    /**
     * Prüft, sich die Struktur des {@link Reachabilitygraph} im Vergleich zu einem
     * {@link RGraphMemento} geändert hat.
     *
     * @param saveStateEdges die Kanten des {@link RGraphMemento}.
     * @param saveStateNodes die Knoten des {@link RGraphMemento}.
     * @return <code>true</code>, wenn sich die Struktur geändert hat.
     */
    private boolean hasStructuralChanges(ArrayList<RGraphEdge> saveStateEdges, ArrayList<RGraphNode> saveStateNodes) {
        if ((this.edges != null) && (saveStateEdges != null)) {
            if (this.edges.size() != saveStateEdges.size()) {
                return true;
            } else {
                for (int i = 0; i < this.edges.size(); i++) {
                    RGraphEdge edge = edges.get(i);
                    RGraphEdge savedEdge = saveStateEdges.get(i);
                    if (!edge.getID().equals(savedEdge.getID())) return true;
                }
            }
        }
        if ((this.nodes != null) && (saveStateNodes != null)) {
            if (this.nodes.size() != saveStateNodes.size()) {
                return true;
            } else {
                for (int i = 0; i < this.nodes.size(); i++) {
                    RGraphNode node = nodes.get(i);
                    RGraphNode savedNode = saveStateNodes.get(i);
                    if (!node.getID().equals(savedNode.getID())) return true;
                }
            }
        }
        return false;
    }

    /**
     * Löscht {@link RGraphEdge}, die beim vorangegangenen Schalten einer Transition
     * entstanden sind.
     *
     * @param saveStateEdges eine {@link ArrayList} mit {@link RGraphEdge}-Instanzen
     *                       aus einem {@link RGraphMemento}.
     */
    private void removeAddedEdges(ArrayList<RGraphEdge> saveStateEdges) {
        // Es wurden Kanten hinzugefügt. Neue Kanten müssen entfernt werden.
        ArrayList<RGraphEdge> diffEdges = new ArrayList<>(edges);
        diffEdges.subList(0, saveStateEdges.size()).clear();
        for (RGraphEdge diffEdge : diffEdges) {
            edges.removeIf(n -> (n.getID().equals(diffEdge.getID())));
            this.firePropertyChange(PetrinetEditorGraphProperties.EDGE, diffEdge, null);
        }
    }

    /**
     * Löscht {@link RGraphNode}, die beim vorangegangenen Schalten einer Transition
     * entstanden sind.
     *
     * @param saveStateNodes eine {@link ArrayList} mit {@link RGraphNode}-Instanzen
     *                       aus einem {@link RGraphMemento}.
     */
    private void removeAddedNodes(ArrayList<RGraphNode> saveStateNodes) {
        // Es wurden Knoten hinzugefügt. Neue Knoten müssen entfernt werden.
        ArrayList<RGraphNode> diffNodes = new ArrayList<>(nodes);
        diffNodes.subList(0, saveStateNodes.size()).clear();
        for (RGraphNode diffNode : diffNodes) {
            nodes.removeIf(n -> (n.getID().equals(diffNode.getID())));
            this.firePropertyChange(PetrinetEditorGraphProperties.NODE, diffNode, null);
        }
    }

    /**
     * Setzt die Hervorhebung aller Elemente, die Teil eines Unbeschränktheitspfades
     * sind, zurück.
     */
    private void resetUnboundedCauseFlag() {
        for (RGraphEdge edge : edges) {
            RGraphNode sourceNode = getNode(edge.getSourceNodeID());
            sourceNode.setElementOfUnboundedcause(false);
            edge.setElementOfUnboundedcause(false);
            if (edges.indexOf(edge) == edges.size() - 1) {
                RGraphNode targetNode = getNode(edge.getTargetNodeID());
                targetNode.setElementOfUnboundedcause(false);
            }
        }
    }

    /**
     * Stellt zuvor entfernte {@link RGraphEdge} wieder her.
     *
     * @param saveStateEdges Eine {@link ArrayList} mit {@link RGraphEdge}-Instanzen
     *                       aus einem {@link RGraphMemento}.
     */
    private void restoreDeletedEdges(ArrayList<RGraphEdge> saveStateEdges) {
        // Es wurden Kanten entfernt. Alte Kanten müssen hinzugefügt werden.
        ArrayList<RGraphEdge> diffEdges = new ArrayList<>(saveStateEdges);
        diffEdges.subList(0, edges.size()).clear();
        for (RGraphEdge diffEdge : diffEdges) {
            RGraphNode sourceNode = getNode(diffEdge.getSourceNodeID());
            RGraphNode targetNode = getNode(diffEdge.getTargetNodeID());
            addEdge(diffEdge.getID(), diffEdge.getTransitionID(), diffEdge.getTransitionName(), sourceNode, targetNode);
        }
    }

    /**
     * Stellt zuvor entfernte {@link RGraphNode} wieder her.
     *
     * @param saveStateNodes eine {@link ArrayList} mit {@link RGraphNode}-Instanzen
     *                       aus einem {@link RGraphMemento}.
     */
    private void restoreDeletedNodes(ArrayList<RGraphNode> saveStateNodes) {
        // Es wurden Knoten entfernt. Alte Knoten müssen hinzugefügt werden.
        ArrayList<RGraphNode> diffNodes = new ArrayList<>(saveStateNodes);
        diffNodes.subList(0, nodes.size()).clear();
        for (RGraphNode diffNode : diffNodes) {
            addNode(diffNode.getMarking(), diffNode.getRemainingActiveTransitionIDs(), diffNode.isInitalmarking());
        }
    }

    /**
     * Stellt die Eigenschaften der {@link RGraphEdge}-Instanzen auf einen früheren
     * Zustand wieder her.
     *
     * @param saveStateEdges eine {@link ArrayList} mit {@link RGraphEdge}-Instanzen
     *                       aus einem {@link RGraphMemento}.
     */
    private void restoreEdgeProperties(ArrayList<RGraphEdge> saveStateEdges) {
        for (RGraphEdge savedEdge : saveStateEdges) {
            for (RGraphEdge edge : edges) {
                if (edge.getID().equals(savedEdge.getID())) {
                    edge.setElementOfLastTransition(savedEdge.isElementOfLastTransition());
                    edge.setElementOfUnboundedcause(savedEdge.isElementOfUnboundedcause());
                    edge.setTransitionName(savedEdge.getTransitionName());
                    break;
                }
            }
        }
    }

    /**
     * Stellt die Eigenschaften der {@link RGraphNode}-Instanzen des Graphen auf
     * einen früheren Zustand wieder her.
     *
     * @param saveStateNodes eine {@link ArrayList} mit {@link RGraphNode}-Instanzen
     *                       aus einem {@link RGraphMemento}.
     */
    private void restoreNodeProperties(ArrayList<RGraphNode> saveStateNodes) {
        for (RGraphNode savedNode : saveStateNodes) {
            for (RGraphNode node : nodes) {
                if (node.getID().equals(savedNode.getID())) {
                    node.setElementOfLastTransition(savedNode.isElementOfLastTransition());
                    node.setElementOfUnboundedcause(savedNode.isElementOfUnboundedcause());
                    node.setInitalmarking(savedNode.isInitalmarking());
                    node.setMarking(savedNode.getMarking());
                    node.setSourcemarkingOfUnboundedcause(savedNode.isSourcemarkingOfUnboundedcause());
                    node.setTargetmarkingOfUnboundedcause(savedNode.isTargetmarkingOfUnboundedcause());
                    break;
                }
            }
        }
    }

    /**
     * Schaltet die Hervorhebung einer {@link RGraphEdge} um.
     *
     * @param edgeID die ID der {@link RGraphEdge} als {@link String}.
     */
    private void toggleHighlightEdge(String edgeID) {
        for (RGraphEdge rGraphEdge : edges) {
            rGraphEdge.setElementOfLastTransition(rGraphEdge.getID().equals(edgeID));
        }
    }

    /**
     * Schaltet die Hervorhebung eines {@link RGraphNode} um.
     *
     * @param nodeID die ID des {@link RGraphNode} als {@link String}.
     */
    private void toggleHighlightNode(String nodeID) {
        for (RGraphNode rGraphNode : nodes) {
            rGraphNode.setElementOfLastTransition(rGraphNode.getID().equals(nodeID));
        }
    }
}
