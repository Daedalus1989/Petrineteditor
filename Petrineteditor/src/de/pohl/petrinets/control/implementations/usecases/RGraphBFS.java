package de.pohl.petrinets.control.implementations.usecases;

import java.util.ArrayList;

import de.pohl.petrinets.model.reachabilitygraph.*;

/**
 * Anwendungsfallklasse für die Breitensuche nach der ID eines
 * {@link RGraphNode} in einem {@link AbstractReachabilitygraph}.
 */
public class RGraphBFS {
    private ArrayList<String> queue = new ArrayList<>();
    private AbstractReachabilitygraph rGraph;
    private ArrayList<String> visitedNodes = new ArrayList<>();

    /**
     * Erstellt einen neunen {@link RGraphBFS} zur Suche nach der ID eines
     * {@link RGraphNode} in einem {@link AbstractReachabilitygraph}.
     *
     * @param rGraph der {@link AbstractReachabilitygraph}.
     */
    public RGraphBFS(AbstractReachabilitygraph rGraph) {
        this.rGraph = rGraph;
    }

    /**
     * Startet die Suche nach der angegebenen ID des {@link RGraphNode}.
     *
     * @param targetNodeID die ID des gesuchten {@link RGraphNode} als
     *                     {@link String}.
     * @return Eine {@link ArrayList} mit {@link String}-Werten, die die IDs der
     *         {@link RGraphEdge} auf dem Pfad vom Wurzelknoten zum angegebenen
     *         {@link RGraphNode}.<br>
     *         Ist <code>null</code>, wenn kein Pfad exisitert oder der Startknoten
     *         und der gesuchte Knoten identsich sind.
     */
    public ArrayList<String> run(String targetNodeID) {
        return bfs(rGraph.getInitialNodeID(), targetNodeID);
    }

    /**
     * Durchläuft den Graphen in BFS auf der Suche nach der ID des Zielknoten und
     * gibt den kürzesten Pfad zu diesen Knoten zurück.
     * <p>
     * Der Algorithmus baut solange einen Spannbaum auf, bis der Knoten mit der ID
     * gefunden wurde. Die letztendliche Ermittlung des Pfades in diesem Spannbaum
     * übernimmt {@link #getEdgePath(ArrayList, String, String)}.
     *
     * @param startNodeID  die ID als {@link String} des {@link RGraphNode} als
     *                     Startknoten, von dem aus gesucht werden soll.
     * @param targetNodeID die ID als {@link String} des {@link RGraphNode} als
     *                     Zielknoten, der gesucht wird.
     * @return Eine {@link ArrayList} mit {@link String}-Werten der IDs der
     *         {@link RGraphEdge} auf dem Pfad vom Startknoten zum Zielknoten.<br>
     *         Ist <code>null</code>, wenn kein Pfad exisitert oder der Startknoten
     *         und der gesuchte Knoten identsich sind.
     */
    private ArrayList<String> bfs(String startNodeID, String targetNodeID) {
        // Liste mit Kanten eines minimalen aufspannenden Baumes
        ArrayList<String> spanningTreeEdges = new ArrayList<>();
        visitedNodes.add(startNodeID);
        queue.add(startNodeID);
        while (!queue.isEmpty()) {
            String currentNodeID = queue.remove(0);
            // Abbruchkriterium
            if (currentNodeID.equals(targetNodeID)) {
                return getEdgePath(spanningTreeEdges, startNodeID, targetNodeID);
            }
            // Nachbarschaft ermitteln
            for (String outboundEdge : rGraph.getNodeOutboundEdges(currentNodeID)) {
                String nextNode = rGraph.getEdgeTargetID(outboundEdge);
                if (!visitedNodes.contains(nextNode)) {
                    visitedNodes.add(nextNode);
                    queue.add(nextNode);
                    spanningTreeEdges.add(outboundEdge);
                }
            }
        }
        return null;
    }

    /**
     * Ermittelt den Pfad vom Startknoten zum gesuchten Knoten im übergebenen
     * Spannbaum.
     * <p>
     * Der Algorithmus durchläuft den Spannbaum rückwärts vom Zielknoten zum
     * Startknoten.
     *
     * @param spanningTreeEdges eine {@link ArrayList} mit {@link String}-Werten der
     *                          IDs der {@link RGraphEdge} des Spannbaumes.
     * @param startNodeID       die ID des Startknotens als {@link String}.
     * @param targetNodeID      die ID des Zielknotens als {@link String}.
     * @return Eine {@link ArrayList} mit {@link String}-Werten der IDs der
     *         {@link RGraphEdge} auf dem Pfad vom Startknoten zum Zielknoten.
     */
    private ArrayList<String> getEdgePath(ArrayList<String> spanningTreeEdges, String startNodeID,
            String targetNodeID) {
        ArrayList<String> edgePath = new ArrayList<>();
        String currentNode = targetNodeID;
        while (!currentNode.equals(startNodeID)) {
            for (String edge : spanningTreeEdges) {
                if (rGraph.getEdgeTargetID(edge).equals(currentNode)) {
                    edgePath.add(0, edge);
                    currentNode = rGraph.getEdgeSourceID(edge);
                    spanningTreeEdges.remove(edge);
                    break;
                }
            }
        }
        return edgePath;
    }
}
