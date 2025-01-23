package de.pohl.petrinets.presenter;

import java.util.ArrayList;
import java.util.Formatter;

import de.pohl.petrinets.view.PetrinetView;

/**
 * Eine Presenter-Klasse, die die Einzelergebnisse einer Durchführung der
 * Beschränktheitsanalyse für eine Menge von Petrinetz Dateien zu einem
 * Gesamtergebnis aufbereitet und zusammenfasst und anschließend über eine
 * {@link PetrinetView} ausgibt.
 *
 * @see SingleAnalysisResultPresenter
 */
public class MultiAnalysisResultPresenter {
    private ArrayList<SingleAnalysisResultPresenter> singleAnalysisResults;
    private PetrinetView petrinetControllerView;

    /**
     * Erstellt einen neuen {@link MultiAnalysisResultPresenter}.
     *
     * @param petrinetControllerView eine {@link PetrinetView} zur Ausgabe des
     *                               Gesamtergebnisses.
     */
    public MultiAnalysisResultPresenter(PetrinetView petrinetControllerView) {
        this.petrinetControllerView = petrinetControllerView;
        this.singleAnalysisResults = new ArrayList<>();
    }

    /**
     * Fügt dem Gesamtergebnis ein Einzelergebnis hinzu.
     *
     * @param resultPresenter ein {@link SingleAnalysisResultPresenter} mit dem
     *                        Einzelergebnis einer Analyse.
     */
    public void addReslut(SingleAnalysisResultPresenter resultPresenter) {
        singleAnalysisResults.add(resultPresenter);
    }

    /**
     * Läst das Gesamtergebnis der Durchführung der Beschränktheitsanalyse für eine
     * Menge von Petrinetz Dateien erzeugen und gibt diese über die im Konstruktor
     * angegebene {@link PetrinetView} aus.
     */
    public void printResults() {
        String format = " %1$-60s| %2$-12s| %3$-40s%n";
        StringBuilder resultStringBuilder = new StringBuilder();
        resultStringBuilder.append(createHeader(format));
        for (SingleAnalysisResultPresenter singleAnalysisResult : singleAnalysisResults) {
            if (singleAnalysisResult.isUnbounded()) {
                resultStringBuilder.append(createIsUnboundedResult(format, singleAnalysisResult));
            } else {
                resultStringBuilder.append(createIsBoundedResult(format, singleAnalysisResult));
            }
        }
        petrinetControllerView.printInMessageView(resultStringBuilder.toString(), false);
    }

    /**
     * Erzeugt den Tabellenkopf für die Ausgabe.
     *
     * @param format die Formatierung als {@link String}.
     * @return Einen formatierten Tabellenkopf als {@link String}.
     * @see {@link Formatter}
     */
    private String createHeader(String format) {
        StringBuilder stringBuilder = new StringBuilder();
        Formatter formatter = new Formatter(stringBuilder);
        formatter.format(format, "", "", "Knoten / Kanten bzw.");
        formatter.format(format, "Dateiname", "beschränkt", "Pfadlänge:Pfad; m, m'");
        formatter.format(" %1$-60s|%2$-12s|%3$-40s%n", "------------------------------------------------------------",
                "-------------", "--------------------------------------------------------------------------------");
        formatter.close();
        return stringBuilder.toString();
    }

    /**
     * Bereitet ein Einzelergebnis für den Fall der Beschräntkeit für das
     * Gesamtergebnis auf.
     *
     * @param format               die Formatierung als {@link String}.
     * @param singleAnalysisResult ein {@link SingleAnalysisResultPresenter} mit dem
     *                             Einzelergebnis einer Analyse.
     * @return Das aufbereitete Einzelergebnis für den Fall der Beschränktheit als
     *         {@link String}.
     * @see {@link Formatter}
     */
    private String createIsBoundedResult(String format, SingleAnalysisResultPresenter singleAnalysisResult) {
        StringBuilder stringBuilder = new StringBuilder();
        Formatter formatter = new Formatter(stringBuilder);
        String bounded = "ja";
        String nodeCount = Integer.toString(singleAnalysisResult.getNodecount());
        String edgeCount = Integer.toString(singleAnalysisResult.getEdgecount());
        String details = String.format(" %1$-2s/%2$3s", nodeCount, edgeCount);
        formatter.format(format, singleAnalysisResult.getPNMLFileName(), bounded, details);
        formatter.close();
        return stringBuilder.toString();
    }

    /**
     * Bereitet ein Einzelergebnis für den Fall der Unbeschräntkeit für das
     * Gesamtergebnis auf.
     *
     * @param format               die Formatierung als {@link String}.
     * @param singleAnalysisResult ein {@link SingleAnalysisResultPresenter} mit dem
     *                             Einzelergebnis einer Analyse.
     * @return Das aufbereitete Einzelergebnis für den Fall der Unbeschränktheit als
     *         {@link String}.
     * @see {@link Formatter}
     */
    private String createIsUnboundedResult(String format, SingleAnalysisResultPresenter singleAnalysisResult) {
        StringBuilder stringBuilder = new StringBuilder();
        Formatter formatter = new Formatter(stringBuilder);
        String bounded = "nein";
        String pathLength = Integer.toString(singleAnalysisResult.getPathlength());
        String edgePath = singleAnalysisResult.getEdgePathAsFormattedString() + ";";
        String sourceNode = singleAnalysisResult.getM1Label() + ",";
        String targetNode = singleAnalysisResult.getM2Label();
        String details = String.format("%1$2s:%2$-40s%3$-20s %4$-20s", pathLength, edgePath, sourceNode, targetNode);
        formatter.format(format, singleAnalysisResult.getPNMLFileName(), bounded, details);
        formatter.close();
        return stringBuilder.toString();
    }
}
