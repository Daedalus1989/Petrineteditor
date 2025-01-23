package de.pohl.petrinets.model.reachabilitygraph;

import java.util.ArrayList;

import de.pohl.petrinets.control.Caretaker;
import de.pohl.petrinets.control.CaretakerObserver;
import de.pohl.petrinets.model.Originator;
import de.pohl.petrinets.model.petrinet.AbstractPetrinet;
import de.pohl.petrinets.model.petrinet.PetrinetMemento;

/**
 * Eine Memento-Klasse für ein {@link AbstractReachabilitygraph} welches den
 * Zustand der des {@link AbstractReachabilitygraph} vor einer Änderung der
 * Markierung des dazugehörigen {@link AbstractPetrinet} erhält.
 *
 * @see <a href="https://www.baeldung.com/java-memento-design-pattern">Memento
 *      Design Pattern (URL im Internet)</a>
 * @see Caretaker
 * @see CaretakerObserver
 * @see Originator
 * @see PetrinetMemento
 */
public class RGraphMemento {
    private ArrayList<RGraphEdge> edges;
    private ArrayList<RGraphNode> nodes;

    /**
     * Erstellt eine neue {@link RGraphMemento}.
     *
     * @param edges die {@link RGraphEdge} des {@link AbstractReachabilitygraph} als
     *              {@link ArrayList} mit {@link RGraphEdge}-Instanzen.
     * @param nodes die {@link RGraphNode} des {@link AbstractReachabilitygraph} als
     *              {@link ArrayList} mit {@link RGraphNode}-Instanzen.
     */
    public RGraphMemento(ArrayList<RGraphEdge> edges, ArrayList<RGraphNode> nodes) {
        this.edges = new ArrayList<>();
        for (RGraphEdge rGraphEdge : edges) {
            this.edges.add(new RGraphEdge(rGraphEdge));
        }
        this.nodes = new ArrayList<>();
        for (RGraphNode rGraphNode : nodes) {
            this.nodes.add(new RGraphNode(rGraphNode));
        }
    }

    /**
     * Liefert die im Memento gespeicherten {@link RGraphEdge} des
     * {@link AbstractReachabilitygraph} zurück.
     *
     * @return Die im Memento gespeicherten {@link RGraphEdge} des
     *         {@link AbstractReachabilitygraph} als {@link ArrayList} mit
     *         {@link RGraphEdge}-Instanzen.
     */
    public ArrayList<RGraphEdge> getEdges() {
        ArrayList<RGraphEdge> newEdges = new ArrayList<>();
        for (RGraphEdge rGraphEdge : edges) {
            newEdges.add(new RGraphEdge(rGraphEdge));
        }
        return newEdges;
    }

    /**
     * Liefert die im Memento gespeicherten {@link RGraphNode} des
     * {@link AbstractReachabilitygraph} zurück.
     *
     * @return Die im Memento gespeicherten {@link RGraphNode} des
     *         {@link AbstractReachabilitygraph} als {@link ArrayList} mit
     *         {@link RGraphNode}-Instanzen.
     */
    public ArrayList<RGraphNode> getNodes() {
        ArrayList<RGraphNode> newNodes = new ArrayList<>();
        for (RGraphNode rGraphNode : nodes) {
            newNodes.add(new RGraphNode(rGraphNode));
        }
        return newNodes;
    }
}
