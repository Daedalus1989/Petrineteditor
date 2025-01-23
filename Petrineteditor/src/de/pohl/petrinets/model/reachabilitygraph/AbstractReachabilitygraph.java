package de.pohl.petrinets.model.reachabilitygraph;

import java.util.ArrayList;

import de.pohl.petrinets.control.implementations.usecases.BoundednessAnalyser;
import de.pohl.petrinets.model.AbstractGraph;
import de.pohl.petrinets.model.Originator;
import de.pohl.petrinets.model.petrinet.AbstractPetrinet;
import de.pohl.petrinets.model.petrinet.Transition;

/**
 * Abstrakte Modellklasse eines Erreichbarkeitsgraphen.
 */
public abstract class AbstractReachabilitygraph extends AbstractGraph implements Originator<RGraphMemento> {
    /**
     * Erstellt und fügt einen Wurzelknoten in den {@link AbstractReachabilitygraph}
     * ein.
     *
     * @param initialMarking      die initiale Markierung eines
     *                            {@link AbstractPetrinet} als {@link ArrayList} mit
     *                            {@link Integer}-Werten.
     * @param activeTransitionIDs eine {@link ArrayList} mit {@link String}-Werten
     *                            für die IDs der auf der Markierung aktiven
     *                            {@link Transition}.
     */
    abstract public void addInitialMarking(ArrayList<Integer> initialMarking, ArrayList<String> activeTransitionIDs);

    /**
     * Fügt dem {@link AbstractReachabilitygraph} eine neue Markierung hinzu.
     *
     * @param transitionID        die ID der geschalteten {@link Transition} als
     *                            {@link String}.
     * @param transitionName      der Name der geschalteten {@link Transition} als
     *                            {@link String}.
     * @param oldActualMarking    die Ausgangsmarkierung als {@link ArrayList} mit
     *                            {@link Integer}-Werten.
     * @param newActualMarking    die Zielmarkierung als {@link ArrayList} mit
     *                            {@link Integer}-Werten.
     * @param activeTransitionIDs eine {@link ArrayList} mit {@link String}-Werten
     *                            für die IDs der auf der Markierung aktiven
     *                            {@link Transition}.
     * @param returnExisting      wenn <code>true</code>, liefert die Methode auch
     *                            dann die ID der Kante zurück, wenn diese bereits
     *                            existiert.
     * @return Die ID der neuen {@link RGraphEdge} des Erreichbarkeitsgraphen als
     *         {@link String} oder <code>null</code>, wenn diese bereits existiert.
     */
    public abstract String addMarking(String transitionID, String transitionName, ArrayList<Integer> oldActualMarking,
            ArrayList<Integer> newActualMarking, ArrayList<String> activeTransitionIDs, boolean returnExisting);

    /**
     * Liefert die Anzahl der {@link RGraphEdge} des Graphen zurück.
     *
     * @return Die Anzahl der {@link RGraphEdge} als {@link Integer}.
     */
    public abstract int countEdges();

    /**
     * Liefert die Anzahl der {@link RGraphNode} des Graphen zurück.
     *
     * @return Die Anzahl der {@link RGraphNode} als {@link Integer}.
     */
    public abstract int countNodes();

    /**
     * Liefert die ID des Quellknotens einer Kante zurück.
     *
     * @param edgeID die ID der Kante als {@link String}.
     * @return die ID des Quellknotens der Kante als {@link String}.
     */
    public abstract String getEdgeSourceID(String edgeID);

    /**
     * Liefert die ID des Zielknotens einer Kante zurück.
     *
     * @param edgeID die ID der Kante als {@link String}.
     * @return die ID des Zielknotens der Kante als {@link String}.
     */
    public abstract String getEdgeTargetID(String edgeID);

    /**
     * Liefert die ID der {@link Transition} einer Kante zurück, auf die diese
     * referenziert.
     *
     * @param edgeID die ID der Kante als {@link String}.
     * @return die ID der Transition, auf die die Kante referenziert als
     *         {@link String}.
     */
    public abstract String getEdgeTransitionID(String edgeID);

    /**
     * Liefert die ID des Knotens mit der initalen Markierung des
     * {@link AbstractReachabilitygraph} zurück.
     *
     * @return Die ID des Wurzeknotens des {@link AbstractReachabilitygraph} als
     *         {@link String}.
     */
    public abstract String getInitialNodeID();

    /**
     * Liefert die IDs aller eingehenden Kanten eines Knotens zurück.
     *
     * @param nodeID die ID des Knotens als {@link String}.
     * @return Eine {@link ArrayList} mit {@link String}-Werten als IDs der
     *         eingehenden Kanten.
     */
    public abstract ArrayList<String> getNodeInboundEdgeIDs(String nodeID);

    /**
     * Liefert das Label eines Knotens zurück.
     *
     * @param nodeID die ID des Knotens als {@link String}.
     * @return Das Label des Knotens als {@link String}.
     */
    public abstract String getNodeLabel(String nodeID);

    /**
     * Liefert die Markierung eines {@link RGraphNode} im
     * {@link AbstractReachabilitygraph} zurück.
     *
     * @param nodeID die ID des {@link RGraphNode}, dessen Markierung gesucht wird
     *               als {@link String}.
     * @return Die Markierung als {@link ArrayList} mit {@link Integer}-Werten.
     */
    public abstract ArrayList<Integer> getNodeMarking(String nodeID);

    /**
     * Liefert die IDs aller ausgehenden Kanten eines Knotens zurück.
     *
     * @param nodeID die ID des Knotens als {@link String}.
     * @return Eine {@link ArrayList} mit {@link String}-Werten als IDs der
     *         ausgehenden Kanten.
     */
    public abstract ArrayList<String> getNodeOutboundEdges(String nodeID);

    /**
     * Hebt den {@link RGraphNode} und dessen eingehende {@link RGraphEdge} hervor,
     * die den durch das Schalten einer Transition hervorgerufenen Übergang in eine
     * Folgemakrierung repräsentieren.
     *
     * @param transitionID     die ID der {@link Transition} die geschaltet wurde
     *                         als {@link String}.
     * @param oldActualMarking die Ausgangsmarkierung als {@link ArrayList} mit
     *                         {@link Integer}-Werten.
     * @param newActualMarking die Zielmarkierung als {@link ArrayList} mit
     *                         {@link Integer}-Werten.
     */
    public abstract void highlightTransition(String transitionID, ArrayList<Integer> oldActualMarking,
            ArrayList<Integer> newActualMarking);

    /**
     * Überprüft, ob es sich bei der angegebenen ID um einen {@link RGraphNode} des
     * {@link AbstractReachabilitygraph} handelt.
     *
     * @param nodeID die zu prüfende ID als {@link String}.
     * @return <code>true</code>, wenn die ID einem {@link RGraphNode} des
     *         {@link AbstractReachabilitygraph} zugeordnet werden konnte.
     */
    public abstract boolean isReachabilitygraphNode(String nodeID);

    /**
     * Markiert die {@link RGraphNode} und {@link RGraphEdge} des Pfades, den ein
     * {@link BoundednessAnalyser} ermittelt hat.
     *
     * @param unboundedCauseEdgeIDs eine {@link ArrayList} mit {@link String}-Werten
     *                              als IDs der Kanten auf dem Pfad.
     * @param m1NodeID              die ID des {@link RGraphNode}, der die
     *                              Markierung m enthält als {@link String}.
     * @param m2NodeID              die ID des {@link RGraphNode}, der die
     *                              Markierung m' enthält als {@link String}.
     */
    public abstract void setUnboundedCause(ArrayList<String> unboundedCauseEdgeIDs, String m1NodeID, String m2NodeID);
}
