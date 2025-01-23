package de.pohl.petrinets.control.implementations.usecases;

import java.util.ArrayList;

import de.pohl.petrinets.model.petrinet.Transition;
import de.pohl.petrinets.model.reachabilitygraph.AbstractReachabilitygraph;
import de.pohl.petrinets.model.reachabilitygraph.RGraphNode;

/**
 * Eine Anwendungsfallklasse für die Beschränktheitsanalyse eines
 * Petrinetzes.<br>
 * Die Beschränktheitsanalyse wird auf einem dem Petrinetz zugeordneten
 * Erreichbarkeitsgraphen durchgeführt.
 *
 * Die Analyse nutzt für die Feststellung der Unbeschränktheit das folgende
 * Kriterium:
 *
 * <blockquote> Es gibt von einer Markierung m aus eine erreichbare Markierung
 * m' für die gilt:
 * <ol>
 * <li>m' weist jeder Stelle mindestens so viele Marken zu wie m.</li>
 * <li>m' weist mindestens einer Stelle mehr Marken zu als m.</li>
 * </ol>
 * </blockquote>
 */
public class BoundednessAnalyser {
    private ArrayList<String> edgePath = new ArrayList<>();
    private ArrayList<Integer> m2Marking;
    private ArrayList<String> visitedNodes = new ArrayList<>();
    private AbstractReachabilitygraph rGraphModel;

    /**
     * Erzeugt einen neuen {@link BoundednessAnalyser}.
     *
     * @param rGraphModel ein {@link AbstractReachabilitygraph}, auf dem die
     *                    Beschränktheitsanalyse ausgeführt werden soll.
     */
    public BoundednessAnalyser(AbstractReachabilitygraph rGraphModel) {
        this.rGraphModel = rGraphModel;
    }

    /**
     * Startet die Beschränktheitsanalyse ab einem Zielknoten m' eines
     * Erreichbarkeitsgraphen. Dieser repräsentiert die neue Markierung des
     * Petrinetzes, die untersucht werden soll.
     * <p>
     * Verwendet einen DFS-Algorithmus.
     *
     * @param m2 die ID eines {@link RGraphNode} m' als {@link String}.
     * @return Eine {@link ArrayList} mit {@link String}-Werten als IDs der
     *         {@link Transition}, die die Kanten auf dem Pfad von m nach m' in
     *         einem {@link AbstractReachabilitygraph} repräsenteiren, wenn der
     *         {@link AbstractReachabilitygraph} unbeschränkt ist.<br>
     *         Ansonsten <code>null</code>.
     */
    public ArrayList<String> run(String m2) {
        System.out.println("---------------");
        System.out.println("Initiiere Unbeschränktheitsanalyse.");
        System.out.println("---------------");
        m2Marking = rGraphModel.getNodeMarking(m2);
        if (analyseRecursion(m2)) {
            System.out.println("---------------");
            System.out.println("Beende Unbeschränktheitsanalyse.");
            System.out.println("---------------");
            return edgePath;
        }
        System.out.println("Unbeschränktheit nicht festgestellt.");
        System.out.println("---------------");
        System.out.println("Beende Unbeschränktheitsanalyse.");
        System.out.println("---------------");
        return null;
    }

    /**
     * Rekursiver DFS-Algorithmus zur bestimmung, ob der Graph unbeschränkt ist.
     *
     * @param m1 die ID eines {@link RGraphNode} m als {@link String}.
     * @return <code>true</code>, wenn die Rekursion die Unbeschränktheit des
     *         {@link AbstractReachabilitygraph} feststellt.<br>
     *         false, wenn die Rekursion keine Unbeschränktheit des
     *         {@link AbstractReachabilitygraph} feststellen konnte.
     */
    private boolean analyseRecursion(String m1) {
        ArrayList<Integer> m1Marking = rGraphModel.getNodeMarking(m1);
        System.out.println("Rekursionsaufruf auf m = " + m1Marking);
        visitedNodes.add(m1);
        if (!m1Marking.equals(m2Marking)) {
            if (fulfilsUnboundednessCriterion(m1Marking, m2Marking)) {
                // Rekursion abbrechen und Pfad erstellen
                System.out.println("Abbruch der Rekursion bei: " + m1Marking.toString()
                        + ". Unbeschränktheit wurde festgestellt.");
                return true;
            }
        }
        // Eingehende Nachbarschaft des aktuellen Knotens bestimmen
        ArrayList<String> inboundEdgeIDs = rGraphModel.getNodeInboundEdgeIDs(m1);
        // Auf jedem Knoten der Nachbarschaft, der noch nicht besucht worden ist, dfs
        // ausführen.
        for (String inboundEdgeID : inboundEdgeIDs) {
            String nextNodeID = rGraphModel.getEdgeSourceID(inboundEdgeID);
            if (!visitedNodes.contains(nextNodeID)) {
                if (analyseRecursion(nextNodeID)) {
                    edgePath.add(inboundEdgeID);
                    return true;
                }
            }
        }
        System.out.println("Keine weiteren eingehenden Kanten bei " + m1Marking.toString());
        return false;
    }

    /**
     * Prüft, ob das Unbeschränktheitskriterium erfüllt wird:
     * <p>
     * Ein Petrinetz ist genau dann unbeschränkt, wenn folgendes Kriterium erfüllt
     * ist:
     *
     * <blockquote> Es gibt von einer Markierung m aus eine erreichbare Markierung
     * m' für die gilt:
     * <ol>
     * <li>m' weist jeder Stelle mindestens so viele Marken zu wie m.</li>
     * <li>m' weist mindestens einer Stelle mehr Marken zu als m.</li>
     * </ol>
     * </blockquote>
     *
     * @param m1Marking eine {@link ArrayList} mit {@link Integer}-Werten als
     *                  Markierung m.
     * @param m2Marking eine {@link ArrayList} mit {@link Integer}-Werten als
     *                  Markierung m'.
     * @return <code>true</code>, wenn Unbeschränktheitskriterium erfüllt ist.<br>
     *         <code>false</code>, wenn Unbeschränktheitskriterium nicht erfüllt
     *         ist.
     */
    @SuppressWarnings("hiding")
    private boolean fulfilsUnboundednessCriterion(ArrayList<Integer> m1Marking, ArrayList<Integer> m2Marking) {
        // Es wird davon ausgegangen, dass m' jeder Stelle mindestens so viele Marken
        // zuweist wie m. Ist dem nicht so, erfolgt ein Abbruch der Methode innerhalb
        // der For-Schleife.
        boolean isGreater = false;
        System.out.printf("Unbeschränktheitskriterium für m = %1$s und m' = %2$s wird geprüft.\n", m1Marking,
                m2Marking);
        for (int i = 0; i < m1Marking.size(); i++) {
            int a = m1Marking.get(i);
            int b = m2Marking.get(i);
            // Wenn auch nur eine Stelle von m größer ist als die jeweilige Stelle in m',
            // so weist m' nicht jeder Stelle mindestens so viele Marken zu wie m.
            if (a > b) {
                System.out.println(
                        "Kriterium nicht erfüllt: m' weist nicht jeder Stelle mindestens so viele Marken zu wie m.");
                return false;
            }
            // Liefert true, wenn mindestens einer Stelle in m' sogar mehr Marken als in m
            // zugewiesen worden sind.
            isGreater = isGreater || (a < b);
        }
        System.out.printf("Kriterium %1$s\n", (isGreater ? "erfüllt." : "nicht erfüllt."));
        return isGreater;
    }
}
