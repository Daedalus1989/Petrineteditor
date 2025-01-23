package de.pohl.petrinets.control.implementations;

import java.io.File;

import de.pohl.petrinets.control.AbstractPetrinetController;
import de.pohl.petrinets.control.implementations.usecases.PNMLFileOpener;
import de.pohl.petrinets.presenter.MultiAnalysisResultPresenter;
import de.pohl.petrinets.view.PetrinetStatusView;
import de.pohl.petrinets.view.PetrinetView;

/**
 * Die Implementierung eines {@link AbstractPetrinetController} für die
 * Verarbeitung mehrerer PNML-Dateien.
 * <p>
 * Diese Kontrollklasse ermöglicht die Durchführung der Beschränktheitsanalyse
 * für eine Menge von Petrinetz Dateien.
 */
public class MultiPetrinetController extends AbstractPetrinetController {
    private File[] pnmlFiles;
    private MultiAnalysisResultPresenter multiAnalysisResultPresenter;

    /**
     * Erstellt einen neuen {@link MultiPetrinetController}.
     *
     * @param pnmlFiles              ein {@link File}-Array mit den PNML-Dateien.
     * @param petrinetControllerView eine {@link PetrinetView}.
     * @param petrinetStatusView     eine {@link PetrinetStatusView}.
     */
    public MultiPetrinetController(File[] pnmlFiles, PetrinetView petrinetControllerView,
            PetrinetStatusView petrinetStatusView) {
        super(petrinetControllerView, petrinetStatusView);
        this.pnmlFiles = pnmlFiles;
        this.multiAnalysisResultPresenter = new MultiAnalysisResultPresenter(petrinetControllerView);
    }

    /**
     * Startet die Durchführung der Beschränktheitsanalyse für eine Menge von
     * Petrinetz Dateien.
     */
    @Override
    public void run() {
        runBatchSimulation();
    }

    @Override
    public void select() {
        petrinetStatusView.setModifiedLabel("");
        petrinetStatusView.setStatusbarFilename("");
    }

    /**
     * Informiert über die nächste zu bearbeitende PNML-Datei.
     *
     * @param pnmlFilename der Dateiname der PNML-Datei.
     */
    private void printNextFile(String pnmlFilename) {
        petrinetControllerView.printInMessageView("Nächte Datei: " + pnmlFilename, false);
    }

    /**
     * Führt die Simulation und Analyse mehrerer Petrinetze aus.
     */
    private void runBatchSimulation() {
        petrinetStatusView.setStatusbarFilename("Analyse gestartet...");
        for (File pnmlFile : pnmlFiles) {
            String pnmlFilename = pnmlFile.getName();
            printNextFile(pnmlFilename);
            PNMLFileOpener fileOpener = new PNMLFileOpener(pnmlFile);
            fileOpener.openForBatchAction(this);
            runSimulation(null);
            multiAnalysisResultPresenter.addReslut(resultPresenter);
        }
        multiAnalysisResultPresenter.printResults();
        petrinetStatusView.setStatusbarFilename("Analyse abgeschlossen.");
    }
}
