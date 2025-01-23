package de.pohl.petrinets.view;

/**
 * Ein Interface das Methoden definiert, die eine
 * {@link AnalysisResultDialogView} zur Anzeige des Ergebnisdialoges einer
 * Beschräntkheitsanalyse bereitstellen muss.
 */
public interface AnalysisResultDialogView {
    /**
     * Zeigt ein Informationsdialogfenster mit dem Ergebnis der
     * Beschränktheitsanalyse an.
     *
     * @param dialogMessage das Ergebnis der Beschränktheitsanalyse.<br>
     *                      Erwartet {@code true}, wenn das Petrinetz unbeschränkt
     *                      ist.
     */
    void showUnboundAnalysisResultDialog(String dialogMessage);
}