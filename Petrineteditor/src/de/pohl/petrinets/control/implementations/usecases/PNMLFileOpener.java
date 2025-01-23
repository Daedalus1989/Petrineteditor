package de.pohl.petrinets.control.implementations.usecases;

import java.io.File;

import de.pohl.petrinets.control.AbstractPetrinetController;
import de.pohl.petrinets.control.implementations.MultiPetrinetController;
import de.pohl.petrinets.control.implementations.SinglePetrinetController;
import de.pohl.petrinets.model.petrinet.AbstractPetrinet;
import de.pohl.petrinets.model.petrinet.Petrinet;
import de.pohl.petrinets.model.reachabilitygraph.AbstractReachabilitygraph;
import de.pohl.petrinets.model.reachabilitygraph.Reachabilitygraph;

/**
 * Eine Anwendungsfallklasse für das Öffnen einer PNML-Datei.
 * <p>
 * Sie erstellt ein {@link AbstractPetrinet} und ein
 * {@link AbstractReachabilitygraph} und übergibt beides einem
 * {@link AbstractPetrinetController}. <br>
 * Ist der {@link AbstractPetrinetController} ein
 * {@link SinglePetrinetController}, so werden auch die Komponenten für die
 * Darstellung der Modelldaten erzeugt und übergeben.
 */
public class PNMLFileOpener {
    private AbstractPetrinet petrinetModel;
    private File pnmlFile;
    private AbstractReachabilitygraph rGraphModel;

    /**
     * Erstellt einen neuen {@link PNMLFileOpener} zum Öffnen eines Petrinetzes.<br>
     *
     * @param pnmlFile die PNML-Datei, die geöffnet werden soll als {@link File}.
     */
    public PNMLFileOpener(File pnmlFile) {
        this.pnmlFile = pnmlFile;
    }

    /**
     * Startet das Öffnen einer PNML-Datei für die Durchführung der
     * Beschränktheitsanalyse für eine Menge von Petrinetz Dateien.
     *
     * @param multiPetrinetController ein {@link MultiPetrinetController}.
     */
    public void openForBatchAction(MultiPetrinetController multiPetrinetController) {
        initModels(multiPetrinetController);
        parsePNMLFile();
    }

    /**
     * Startet das Öffnen einer PNML-Datei zur graphischen Darstellung.
     *
     * @param singlePetrinetController ein {@link SinglePetrinetController}.
     */
    public void openForVisualRepresentation(SinglePetrinetController singlePetrinetController) {
        initModels(singlePetrinetController);
        initVisualComponents(singlePetrinetController);
        parsePNMLFile();
    }

    /**
     * Erstellt eine {@link AbstractPetrinet} und eine
     * {@link AbstractReachabilitygraph} und übergibt diese an den
     * {@link AbstractPetrinetController}.
     *
     * @param abstractPetrinetController ein {@link AbstractPetrinetController}
     */
    private void initModels(AbstractPetrinetController abstractPetrinetController) {
        petrinetModel = new Petrinet(pnmlFile);
        rGraphModel = new Reachabilitygraph();
        abstractPetrinetController.setPetrinetModel(petrinetModel);
        abstractPetrinetController.setRGraphModel(rGraphModel);
    }

    /**
     * Startet die intialisierung der Komponenten für die Darstellung eines
     * {@link AbstractPetrinet} und eines {@link AbstractReachabilitygraph}.
     *
     * @param petrinetController ein {@link SinglePetrinetController}.
     */
    private void initVisualComponents(SinglePetrinetController petrinetController) {
        petrinetModel.addPropertyChangeListener(petrinetController);
        VisualPetrinetInitializer vpi = new VisualPetrinetInitializer(petrinetController);
        vpi.run();
        VisualRGraphInitializer vri = new VisualRGraphInitializer(petrinetController);
        vri.run();
    }

    /**
     * Startet das Parsen der PNML-Datei.
     */
    private void parsePNMLFile() {
        PNMLFileParser pnmlFileParser = new PNMLFileParser(petrinetModel, rGraphModel);
        pnmlFileParser.run();
    }
}
