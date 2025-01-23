package de.pohl.petrinets.presenter;

import java.util.ArrayList;
import java.util.Formatter;

import de.pohl.petrinets.control.implementations.usecases.RGraphBFS;
import de.pohl.petrinets.model.petrinet.Transition;
import de.pohl.petrinets.model.reachabilitygraph.AbstractReachabilitygraph;
import de.pohl.petrinets.model.reachabilitygraph.RGraphEdge;
import de.pohl.petrinets.view.*;

/**
 * Eine Presenter-Klasse, die das Ergebnis einer Durchführung der
 * Beschränktheitsanalyse für ein Petrinetz aufbereitet und anschließend über
 * eine {@link PetrinetView} und optional auch über einen
 * {@link PetrinetEditorView} als Dialog ausgibt.
 */
public class SingleAnalysisResultPresenter {
    private ArrayList<String> edgePath;
    private boolean isUnbounded;
    private String pnmlFileName;
    private PetrinetView petrinetControllerView;
    private AbstractReachabilitygraph rGraphModel;
    private String m1;
    private String m2;

    /**
     * Erstellt einen neuen {@link SingleAnalysisResultPresenter} für die Ausgabe
     * eines Ergebnisses einer Durchführung der Beschränktheitsanalyse für ein
     * Petrinetz.
     *
     * @param pnmlFileName           der Dateiname der PNML-Datei als
     *                               {@link String}.
     * @param rGraphModel            der {@link AbstractReachabilitygraph} auf dem
     *                               die Beschränktheitsanalyse ausgeführt wurde.
     * @param petrinetControllerView eine {@link PetrinetView}.
     */
    public SingleAnalysisResultPresenter(String pnmlFileName, AbstractReachabilitygraph rGraphModel,
            PetrinetView petrinetControllerView) {
        this.pnmlFileName = pnmlFileName;
        this.rGraphModel = rGraphModel;
        this.petrinetControllerView = petrinetControllerView;
    }

    /**
     * Liefert die Anzahl der Kanten des Erreichbarkeitsgraphen.
     *
     * @return Die Anzahl der Kanten als {@link Integer}.
     */
    public Integer getEdgecount() {
        return rGraphModel.countEdges();
    }

    /**
     * Liefert den Pfad der Unbeschränktheit zurück als formatierten String zurück.
     * Der Pfad kann wahlweise vom Wurzelknoten des
     * {@link AbstractReachabilitygraph} über m nach m' verlaufen, oder nur von m
     * nach m'.
     * <p>
     * Format: {@code (transitionID, transitionID, ...)}
     *
     * @return Einen formatierten {@link String} mit den IDs der {@link Transition}
     *         auf denen die Kanten des Erreichbarkeitsgraphen referenzieren.
     * @see #setResultPetrinetIsUndbounded(ArrayList, boolean)
     */
    public String getEdgePathAsFormattedString() {
        StringBuilder sb = new StringBuilder();
        for (String edge : edgePath) {
            String transitionID = rGraphModel.getEdgeTransitionID(edge);
            sb.append(sb.isEmpty() ? transitionID : "," + transitionID);
        }
        return String.format("(%1$s)", sb.toString());
    }

    /**
     * Liefert die Beschriftung des Quellknotens m der Beschränktheitsanalyse.
     *
     * @return Ein {@link String} mit der Beschriftung von m.
     */
    public String getM1Label() {
        return rGraphModel.getNodeLabel(m1);
    }

    /**
     * Liefert die Beschriftung des Zielknotens m' der Beschränktheitsanalyse.
     *
     * @return Ein {@link String} mit der Beschriftung von m'.
     */
    public String getM2Label() {
        return rGraphModel.getNodeLabel(m2);
    }

    /**
     * Liefert die Anzahl der Knoten des Erreichbarkeitsgraphen.
     *
     * @return Die Anzahl der Knoten als {@link Integer}.
     */
    public Integer getNodecount() {
        return rGraphModel.countNodes();
    }

    /**
     * Liefert die Länge des Ergebnispfades zurück.
     *
     * @return Die Länge des Pfades als {@link Integer}.
     * @see #getEdgePathAsFormattedString()
     * @see #setResultPetrinetIsUndbounded(ArrayList, boolean)
     */
    public int getPathlength() {
        return edgePath.size();
    }

    /**
     * Liefert den Dateinamen der PNML-Datei zurück.
     *
     * @return Ein {@link String} mit dem Dateinamen.
     */
    public String getPNMLFileName() {
        return this.pnmlFileName;
    }

    /**
     * Liefert das im {@link SingleAnalysisResultPresenter} angegebene Ergebnis der
     * Beschräntkheitsanalyse zurück.
     *
     * @return <code>true</code>, wenn unbeschränkt.<br>
     *         <code>false</code>, wenn beschränkt.
     */
    public boolean isUnbounded() {
        return this.isUnbounded;
    }

    /**
     * Lässt das Ergebnis der Durchführung der Beschränktheitsanalyse für ein
     * Petrinetz aufbereiten und ausgeben.
     */
    /**
     * Lässt das Ergebnis der Durchführung der Beschränktheitsanalyse für ein
     * Petrinetz aufbereiten und ausgeben.
     *
     * @param analysisResultDialogView (Optional) <br>
     *                                 Eine {@link AnalysisResultDialogView} zur
     *                                 Ausgabe des Ergebnisses in einem
     *                                 Dialogfenster.<br>
     *                                 Wenn <code>null</code> erfolgt die ausgabe
     *                                 nur in der {@link PetrinetView}.
     */
    public void printResult(AnalysisResultDialogView analysisResultDialogView) {
        printMessageAreaResult();
        if (analysisResultDialogView != null) {
            showResultDialog(analysisResultDialogView);
        }
    }

    /**
     * Setzt das Analyseergebnis auf Beschränkt.
     */
    public void setResultPetrinetIsBounded() {
        this.isUnbounded = false;
    }

    /**
     * Setzt das Analyseergebnis auf unbeschränkt.
     *
     * @param edgePath    der Pfad zwischen der Markierung m und m' im
     *                    {@link AbstractReachabilitygraph} als {@link ArrayList}
     *                    mit {@link String}-Werten, die die IDs der
     *                    {@link RGraphEdge} des {@link AbstractReachabilitygraph}
     *                    repräsentieren.
     * @param addPathToM1 Wenn <code>true</code>, wird der kürzeste Pfad vom
     *                    Wurzelknoten des {@link AbstractReachabilitygraph} zum
     *                    Knoten mit der Markierung m ermittelt und an den Anfang
     *                    des {@code edgePath} angefügt.<br>
     *                    Wenn <code>false</code>, wird der übergebene Pfad nicht
     *                    erweitert.
     */
    public void setResultPetrinetIsUndbounded(ArrayList<String> edgePath, boolean addPathToM1) {
        this.m1 = rGraphModel.getEdgeSourceID(edgePath.get(0));
        this.m2 = rGraphModel.getEdgeTargetID(edgePath.get(edgePath.size() - 1));
        this.isUnbounded = true;
        if (addPathToM1) {
            this.edgePath = new RGraphBFS(rGraphModel).run(m1);
            this.edgePath.addAll(edgePath);
        } else {
            this.edgePath = edgePath;
        }
    }

    /**
     * Leitet das Ergebnis an den {@link AbstractReachabilitygraph} weiter, um
     * dieses zu visualisieren.
     */
    private void forewardResultToGraph() {
        rGraphModel.setUnboundedCause(edgePath, m1, m2);
    }

    /**
     * Erzeugt und präsentiert die Ausgabe für den Fall, dass das Petrinetz
     * beschränkt ist und lässt diese durch eine {@link PetrinetAnalyseResultView}
     * ausgeben.
     *
     * @see {@link Formatter}
     */
    private void printIsBoundedResult() {
        String nodeCount = Integer.toString(getNodecount());
        String edgesCount = Integer.toString(getEdgecount());
        String filename = pnmlFileName;
        String layout = "%1$-20s %2$s%n";
        StringBuilder stringBuilder = new StringBuilder();
        Formatter formatter = new Formatter(stringBuilder);
        formatter.format("Analyseergebnis: beschränkt%n");
        formatter.format("------------------------------%n");
        formatter.format(layout, "Dateiname:", filename);
        formatter.format(layout, "Anzahl der Knoten:", nodeCount);
        formatter.format(layout, "Anzahl der Kanten", edgesCount);
        formatter.format("------------------------------");
        formatter.close();
        petrinetControllerView.printInMessageView(stringBuilder.toString(), false);
    }

    /**
     * Erzeugt und präsentiert die Ausgabe für den Fall, dass das Petrinetz
     * unbeschränkt ist und lässt diese durch eine {@link PetrinetAnalyseResultView}
     * ausgeben.
     *
     * @see {@link Formatter}
     */
    private void printIsUnboundedResult() {
        String edgePathString = getEdgePathAsFormattedString();
        String filename = pnmlFileName;
        String sourceNodeLabel = getM1Label();
        String targetNodeLabel = getM2Label();
        String pathLength = Integer.toString(getPathlength());
        String layout = "%1$-20s %2$s%n";
        StringBuilder stringBuilder = new StringBuilder();
        Formatter formatter = new Formatter(stringBuilder);
        formatter.format("Analyseergebnis: unbeschränkt%n");
        formatter.format("------------------------------%n");
        formatter.format(layout, "Dateiname:", filename);
        formatter.format(layout, "m:", sourceNodeLabel);
        formatter.format(layout, "Pfad:", edgePathString);
        formatter.format(layout, "m':", targetNodeLabel);
        formatter.format(layout, "Pfadlänge:", pathLength);
        formatter.format("------------------------------");
        formatter.close();
        petrinetControllerView.printInMessageView(stringBuilder.toString(), false);
    }

    /**
     * Lässt die Ergebnisse der Beschränktheitsanalyse erzeugen und ausgeben.
     */
    private void printMessageAreaResult() {
        if (isUnbounded) {
            forewardResultToGraph();
            printIsUnboundedResult();
        } else {
            printIsBoundedResult();
        }
    }

    /**
     * Zeigt das Ergebnis in einem Dialogfenster an.
     *
     * @param analysisResultDialogView eine {@link AnalysisResultDialogView}.
     */
    private void showResultDialog(AnalysisResultDialogView analysisResultDialogView) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Das Petrinetz ist " + (isUnbounded ? "unbeschränkt." : "beschränkt."));
        analysisResultDialogView.showUnboundAnalysisResultDialog(stringBuilder.toString());
    }
}
