package de.pohl.petrinets.view;

/**
 * Ein Inteface das Methoden definiert, die eine {@link PetrinetStatusView}
 * bereitstellen muss. Hierbei handelt es sich um Statuseigenschaften der
 * geöffneten PNML-Datei, oder auch den Operationsstatus einer
 * Beschränktheitsanalyse für mehrere PNML-Dateien.
 */
public interface PetrinetStatusView {
    /**
     * Zeigt an, dass die aktuell geladene Datei modifiziert worden ist.
     *
     * @param text der Text, der angezeigt werden soll als {@link String}.
     */
    void setModifiedLabel(String text);

    /**
     * Legt den Dateinamen fest, der in der Statusleiste angezeigt werden soll.<br>
     * Kann auch für alternative Informationen, wie den Status der Analyse mehrerer
     * PNML-Dateien verwendet werden, da hier kein Dateiname angezeigt wird.
     *
     * @param fileName der Dateiname als {@link String}.
     */
    void setStatusbarFilename(String fileName);
}