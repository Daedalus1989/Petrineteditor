package de.pohl.petrinets.control.implementations;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileFilter;
import java.util.*;

import de.pohl.petrinets.control.*;
import de.pohl.petrinets.control.implementations.usecases.BoundednessAnalyser;
import de.pohl.petrinets.model.petrinet.*;
import de.pohl.petrinets.model.reachabilitygraph.*;
import de.pohl.petrinets.presenter.SingleAnalysisResultPresenter;
import de.pohl.petrinets.view.*;
import de.pohl.petrinets.view.gui.components.PetrinetPanel;
import de.pohl.petrinets.view.gui.components.RGraphPanel;

/**
 * Eine Kontrollklasse für die Anzeige einer einzelnen PNML-Datei.
 * <p>
 * Die Klasse ermöglicht Undo-Redo-Funktionalität und nutzt hierfür zusammen mit
 * {@link AbstractPetrinet} und {@link AbstractReachabilitygraph} das
 * Memento-Designpattern. <br>
 * Dabei entsprecht der {@link SinglePetrinetController} dem Caretaker, der die
 * Zustände aufbewahrt und verwaltet.
 * <p>
 * Die Klase ermöglicht das sequenzielle Öffnen aller PNML-Dateien in dem
 * Verzeichnis, in dem sich die aktuelle PNML-Datei befindet.
 */
public class SinglePetrinetController extends AbstractPetrinetController
        implements ViewerAdapter, PropertyChangeListener, Caretaker {
    private boolean permanentAnalysis;
    private File pnmlFile;
    private ArrayList<File> pnmlFileDirectoryFiles = new ArrayList<>();
    // Stacks für Undo-Redo-Funktionalität
    private Stack<PetrinetMemento> petrinetUndoStack;
    private Stack<RGraphMemento> rGraphUndoStack;
    private Stack<PetrinetMemento> petrinetRedoStack;
    private Stack<RGraphMemento> rGraphRedoStack;
    private AnalysisResultDialogView analysisResultDialogView;
    private CaretakerObserver caretakerObserver;

    /**
     * Erstellt einen neuen {@link SinglePetrinetController}.
     *
     * @param pnmlFile                 der Pfad der PNML-Datei als {@link File}.
     * @param petrinetView             eine {@link PetrinetView}.
     * @param petrinetStatusView       ein {@link PetrinetEditorView}.
     * @param analysisResultDialogView ein {@link AnalysisResultDialogView}.
     * @param caretakerObserver        einen {@link CaretakerObserver}.
     */
    public SinglePetrinetController(File pnmlFile, PetrinetView petrinetView, PetrinetStatusView petrinetStatusView,
            AnalysisResultDialogView analysisResultDialogView, CaretakerObserver caretakerObserver) {
        super(petrinetView, petrinetStatusView);
        this.pnmlFile = pnmlFile;
        this.analysisResultDialogView = analysisResultDialogView;
        this.caretakerObserver = caretakerObserver;
        initWorkingDirectoryFileList();
        initStateStacks();
    }

    /**
     * Fügt dem {@link Petrinet} einen {@link PropertyChangeListener} hinzu
     *
     * @param listener ein {@link PropertyChangeListener}.
     * @see de.pohl.petrinets.model.AbstractGraph#addPropertyChangeListener(java.beans.PropertyChangeListener)
     */
    public void addPetrinetPropertyChangeListener(PropertyChangeListener listener) {
        petrinetModel.addPropertyChangeListener(listener);
    }

    /**
     * Fügt dem {@link Reachabilitygraph} einen {@link PropertyChangeListener} hinzu
     *
     * @param listener ein {@link PropertyChangeListener}.
     * @see de.pohl.petrinets.model.AbstractGraph#addPropertyChangeListener(java.beans.PropertyChangeListener)
     */
    public void addRGraphPropertyChangeListener(PropertyChangeListener listener) {
        rGraphModel.addPropertyChangeListener(listener);
    }

    @Override
    public void buttonPushed(String id) {
        if (rGraphModel.isReachabilitygraphNode(id)) {
            save();
            petrinetModel.setActualMarking(rGraphModel.getNodeMarking(id));
        } else {
            if (petrinetModel.isTransition(id)) {
                if (petrinetModel.getActiveTransitionIDs().contains(id)) {
                    save();
                }
                String rGraphEdgeID = toggleTransition(id, true);
                if (permanentAnalysis & rGraphEdgeID != null) {
                    analyseRGraphNode(rGraphModel.getEdgeTargetID(rGraphEdgeID));
                }
            } else if (petrinetModel.isPlace(id)) {
                petrinetModel.togglePlaceEditFocus(id);
            }
        }
    }

    /**
     * Verringert die Anzahl der Marken einer fokussierten {@link Place} um
     * eineMarke.
     *
     * @see de.pohl.petrinets.model.petrinet.AbstractPetrinet#decFocusedPlaceTokens()
     */
    public void decFocusedPlaceTokens() {
        save();
        petrinetModel.decFocusedPlaceTokens();
    }

    /**
     * Liefert den Pfad zur Nachfolgerdatei der geladenen Datei im aktuellen
     * Arbeitsverzeichnis zurück.
     *
     * @return Die Nachfolgerdatei als {@link File} oder <code>null</code>, wenn
     *         kein Nachfolger vorhanden ist.
     */
    public File getNextWorkingFile() {
        if (pnmlFile != null) {
            int i = pnmlFileDirectoryFiles.indexOf(pnmlFile);
            if (i < pnmlFileDirectoryFiles.size() - 1) {
                pnmlFile = pnmlFileDirectoryFiles.get(i + 1);
                petrinetStatusView.setStatusbarFilename(getPNMLFileName());
                initStateStacks();
                return pnmlFile;
            }
        }
        return null;
    }

    /**
     * Liefert eine Liste mit den IDs aller unter der aktuellen Markierung aktiven
     * {@link Transition}.
     *
     * @return Eine {@link ArrayList} mit {@link String}-Werten für die IDs der
     *         {@link Transition}.
     * @see de.pohl.petrinets.model.petrinet.AbstractPetrinet#getActiveTransitionIDs()
     */
    public ArrayList<String> getPetrinetActiveTransitionIDs() {
        return petrinetModel.getActiveTransitionIDs();
    }

    /**
     * Liefert die initiale Markierung des {@link Petrinet} zurück.
     *
     * @return Eine {@link ArrayList} mit {@link Integer}-Werten als initiale
     *         Markierung.
     * @see de.pohl.petrinets.model.petrinet.AbstractPetrinet#getInitialMarking()
     */
    public ArrayList<Integer> getPetrinetInitialMarking() {
        return petrinetModel.getInitialMarking();
    }

    /**
     * Liefert den Pfad der PNML-Datei zurück.
     *
     * @return Die PNML-Datei als {@link File}.
     */
    public File getPNMLFile() {
        return new File(pnmlFile.getPath());
    }

    /**
     * Liefert den Namen der PNML-Datei zurück.
     *
     * @return Der Name der PNML-Datei als {@link String}.
     */
    public String getPNMLFileName() {
        return pnmlFile.getName();
    }

    /**
     * Liefert den Pfad zur Vorgängerdatei der geladenen Datei im aktuellen
     * Arbeitsverzeichnis zurück.
     *
     * @return Die Vorgängerdatei als {@link File} oder <code>null</code>, wenn kein
     *         Nachfolger vorhanden ist.
     */
    public File getPreviousWorkingFile() {
        if (pnmlFile != null) {
            int i = pnmlFileDirectoryFiles.indexOf(pnmlFile);
            if (i > 0) {
                pnmlFile = pnmlFileDirectoryFiles.get(i - 1);
                petrinetStatusView.setStatusbarFilename(getPNMLFileName());
                initStateStacks();
                return pnmlFile;
            }
        }
        return null;
    }

    /**
     * Erhöht die Anzahl der Marken einer fokussierten Place um eine Marke.
     *
     * @see de.pohl.petrinets.model.petrinet.AbstractPetrinet#incFocusedPlaceTokens()
     */
    public void incFocusedPlaceTokens() {
        save();
        petrinetModel.incFocusedPlaceTokens();
    }

    @Override
    public void notifyCaretakerObserver() {
        boolean hasUndoStack = (!petrinetUndoStack.isEmpty() && !rGraphUndoStack.isEmpty());
        boolean hasRedoStack = (!petrinetRedoStack.isEmpty() && !rGraphRedoStack.isEmpty());
        caretakerObserver.update(hasUndoStack, hasRedoStack);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String evtPropertyName = evt.getPropertyName();
        if (evtPropertyName.equals(PetrinetEditorGraphProperties.MODIFIED.toString())) {
            if ((boolean) evt.getNewValue()) {
                petrinetStatusView.setModifiedLabel("modifiziert");
            } else {
                petrinetStatusView.setModifiedLabel("unmodifiziert");
            }
        }
    }

    /**
     * Stellt die letzte Zustandsänderung der Graphmodelle wieder her.
     */
    @Override
    public void redo() {
        // Zustände auf UndoStacks speichern
        petrinetUndoStack.push(petrinetModel.saveState());
        rGraphUndoStack.push(rGraphModel.saveState());
        // Zustände von RedoStacks holen
        PetrinetMemento petrinetMemento = petrinetRedoStack.pop();
        RGraphMemento rGraphMemento = rGraphRedoStack.pop();
        // Zustände zurücksetzen
        petrinetModel.restoreState(petrinetMemento);
        rGraphModel.restoreState(rGraphMemento);
        // Buttons aktualisieren
        notifyCaretakerObserver();
    }

    /**
     * Setzt die Markierung des {@link AbstractPetrinet} auf die
     * Anfangsmarkierungzurück. Diese entspricht nicht zwangsweise der
     * Anfangsmarkierung aus der PNML-Dateides {@link AbstractPetrinet}.
     *
     * @see de.pohl.petrinets.model.petrinet.AbstractPetrinet#resetToInitialMarking()
     */
    public void resetPetrinetToInitialMarking() {
        save();
        petrinetModel.resetToInitialMarking();
    }

    @Override
    public void run() {
        initStateStacks();
        runSimulation(analysisResultDialogView);
    }

    /**
     * Speichert den Zustand des {@link AbstractPetrinet} und des
     * {@link AbstractReachabilitygraph} nach dem Ausführen einer Aktion, die ein
     * neues UndoRedo-Kontinuum erzeugt.<br>
     * Dies ist z.B. dann der Fall, wenn nach einer Undo-Aktion der Zustand der
     * Graphmodelle durch das Schalten einer Transition geändert wird.
     */
    @Override
    public void save() {
        petrinetUndoStack.push(petrinetModel.saveState());
        rGraphUndoStack.push(rGraphModel.saveState());
        if (!petrinetRedoStack.isEmpty() || !rGraphRedoStack.isEmpty()) {
            petrinetRedoStack.removeAllElements();
            rGraphRedoStack.removeAllElements();
        }
        notifyCaretakerObserver();
    }

    @Override
    public void select() {
        if (petrinetModel.isModified()) {
            petrinetStatusView.setModifiedLabel("modifiziert");
        } else {
            petrinetStatusView.setModifiedLabel("unmodifiziert");
        }
        petrinetStatusView.setStatusbarFilename(getPNMLFileName());
        notifyCaretakerObserver();
    }

    /**
     * Legt fest, ob beim Schaltvorgang eine Analyse erfolgen soll.
     *
     * @param permanentAnalysis wenn <code>true</code> wird nach jedem Schalten eine
     *                          Unbeschränktheitsanalys durchgeführt.
     */
    public void setPermanentAnalysis(boolean permanentAnalysis) {
        this.permanentAnalysis = permanentAnalysis;
    }

    /**
     * Ändert das {@link AbstractPetrinet} des {@link SinglePetrinetController}.
     *
     * @param petrinetModel ein {@link AbstractPetrinet}.
     */
    @Override
    public void setPetrinetModel(AbstractPetrinet petrinetModel) {
        super.setPetrinetModel(petrinetModel);
    }

    /**
     * Ändert das {@link PetrinetPanel} des {@link SinglePetrinetController}.
     *
     * @param petrinetView ein {@link PetrinetPanel}.
     */
    public void setPetrinetView(PetrinetPanel petrinetView) {
        petrinetControllerView.setPetrinetView(petrinetView);
    }

    /**
     * Ändert das {@link AbstractReachabilitygraph} des
     * {@link SinglePetrinetController}.
     *
     * @param rGraphModel der {@link AbstractReachabilitygraph}.
     */
    @Override
    public void setRGraphModel(AbstractReachabilitygraph rGraphModel) {
        super.setRGraphModel(rGraphModel);
    }

    /**
     * Ändert das {@link RGraphPanel} des {@link SinglePetrinetController}.
     *
     * @param rGraphView das {@link RGraphPanel}.
     */
    public void setRGraphView(RGraphPanel rGraphView) {
        petrinetControllerView.setRGraphView(rGraphView);
    }

    /**
     * Macht die letzte Zustandsänderung an den Graphmodellen rückgängig.
     */
    @Override
    public void undo() {
        // Zustände auf RedoStacks speichern
        petrinetRedoStack.push(petrinetModel.saveState());
        rGraphRedoStack.push(rGraphModel.saveState());
        // Zustände von UndoStacks holen
        PetrinetMemento petrinetMemento = petrinetUndoStack.pop();
        RGraphMemento rGraphMemento = rGraphUndoStack.pop();
        // Zustände zurücksetzen
        petrinetModel.restoreState(petrinetMemento);
        rGraphModel.restoreState(rGraphMemento);
        // Buttons aktualisieren
        notifyCaretakerObserver();
    }

    /**
     * Startet die Unbeschränktheitsanalyse.
     *
     * @param m2 ein {@link RGraphNode} mit der Markierung m'.
     */
    private void analyseRGraphNode(String m2) {
        BoundednessAnalyser boundednessAnalyser = new BoundednessAnalyser(rGraphModel);
        ArrayList<String> edgePath = boundednessAnalyser.run(m2);
        if (edgePath != null) {
            resultPresenter = new SingleAnalysisResultPresenter(petrinetModel.getPNMLFile().getName(), rGraphModel,
                    petrinetControllerView);
            resultPresenter.setResultPetrinetIsUndbounded(edgePath, false);
            resultPresenter.printResult(null);
        }
    }

    /**
     * Initiiert die Stacks für die Undo-Redo-Funktionalität.
     */
    private void initStateStacks() {
        petrinetUndoStack = new Stack<>();
        rGraphUndoStack = new Stack<>();
        petrinetRedoStack = new Stack<>();
        rGraphRedoStack = new Stack<>();
        notifyCaretakerObserver();
    }

    /**
     * Initiert eine sortierte {@link ArrayList} mit {@link File}-Instanzen, in der
     * alle PNML-Dateien des aktuellen Arbeitsverzeichnisses enthalten sind.
     */
    private void initWorkingDirectoryFileList() {
        File[] fileArray = pnmlFile.getParentFile().listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                String name = pathname.getName();
                if (name.endsWith(".pnml")) {
                    return true;
                }
                return false;
            }
        });
        pnmlFileDirectoryFiles = new ArrayList<>(Arrays.asList(fileArray));
        pnmlFileDirectoryFiles.sort(null);
    }
}
