package de.pohl.petrinets.presenter;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JFileChooser;

import de.pohl.petrinets.control.*;
import de.pohl.petrinets.control.implementations.MultiPetrinetController;
import de.pohl.petrinets.control.implementations.SinglePetrinetController;
import de.pohl.petrinets.control.implementations.usecases.*;
import de.pohl.petrinets.view.PetrinetEditorView;
import de.pohl.petrinets.view.gui.PetrinetEditorFrame;
import de.pohl.petrinets.view.gui.components.PetrinetViewArea;
import de.pohl.petrinets.view.gui.dialog.PNMLFileChooser;

/**
 * Die Presenterklasse des Petrinetzeditors.
 * <p>
 * Diese Klasse dient als Schicht zwischen dem PetrinetEditorGUI und den
 * {@link AbstractPetrinetController}-Instanzen.
 * <p>
 * Die Klasse implementiert einen {@link PetrinetEditorViewListener} und
 * reagiert somit auf die Ereginisse, die in der {@link PetrinetEditorView}
 * auftreten können. Dazu gehört z.B. das Anklicken von MenuItems.
 */
public class PetrinetEditorPresenter implements PetrinetEditorViewListener, CaretakerObserver {
    private PetrinetEditorView petrinetEditorView;
    private AbstractPetrinetController petrinetController;
    private ArrayList<AbstractPetrinetController> petrinetControllers = new ArrayList<>();
    private boolean permanentAnalysis;

    /**
     * Erstellt einen neuen {@link PetrinetEditorPresenter}.
     *
     * @param name   der Name des Autors als {@link String}.
     * @param matrNr die Matrikelnummer des Autors als {@link String}.
     */
    public PetrinetEditorPresenter(String name, String matrNr) {
        String title = "Petrinetzeditor - " + name + " - " + matrNr;
        petrinetEditorView = new PetrinetEditorFrame(title, this);
        setPetrinetActionActivationStates(false);
        petrinetEditorView.setCloseTabActionState(false);
        printSysProperties();
    }

    @Override
    public void onActiveTabChanged(int index) {
        if (index != -1) {
            petrinetController = petrinetControllers.get(index);
            if (petrinetController instanceof SinglePetrinetController) {
                ((SinglePetrinetController) petrinetController).setPermanentAnalysis(permanentAnalysis);
                setPetrinetActionActivationStates(true);
            } else {
                setPetrinetActionActivationStates(false);
            }
            petrinetController.select();
        }
    }

    @Override
    public void onAnalyseMultiplePetrinetClick() {
        PNMLFileChooser chooser = new PNMLFileChooser(null, true);
        int returnState = petrinetEditorView.showOpenDialog(chooser);
        if (returnState == JFileChooser.APPROVE_OPTION) {
            File[] pnmlFiles = chooser.getSelectedFiles();
            MultiPetrinetController multiPetrinetController = initNewMultiPetrinetController(pnmlFiles);
            Thread multiAnalyseThread = new Thread(multiPetrinetController);
            multiAnalyseThread.start();
            petrinetEditorView.setCloseTabActionState(true);
        }
    }

    @Override
    public void onAnaylseSingleClick() {
        if (petrinetController != null) {
            if (petrinetController instanceof SinglePetrinetController) {
                PetrinetResetter petrinetResetter = new PetrinetResetter(
                        ((SinglePetrinetController) petrinetController), true);
                petrinetResetter.run();
                Thread singleAnalyseThread = new Thread((petrinetController));
                singleAnalyseThread.start();
            }
        }
    }

    @Override
    public void onCloseTabClick() {
        int index = petrinetControllers.indexOf(petrinetController);
        if (index != -1) {
            petrinetController = null;
            petrinetControllers.remove(index);
            petrinetEditorView.removeTab(index);
            if (index == 0) {
                petrinetEditorView.setCloseTabActionState(false);
                setPetrinetActionActivationStates(false);
            }
        }
    }

    @Override
    public void onDecTokenClick() {
        if (petrinetController != null) {
            if (petrinetController instanceof SinglePetrinetController) {
                ((SinglePetrinetController) petrinetController).decFocusedPlaceTokens();
                RGraphReinitializer rGraphReinitializer = new RGraphReinitializer(
                        (SinglePetrinetController) petrinetController);
                rGraphReinitializer.run();
            }
        }
    }

    @Override
    public void onExitClick() {
        System.exit(0);
    }

    @Override
    public void onIncTokenClick() {
        if (petrinetController != null) {
            if (petrinetController instanceof SinglePetrinetController) {
                ((SinglePetrinetController) petrinetController).incFocusedPlaceTokens();
                RGraphReinitializer rGraphReinitializer = new RGraphReinitializer(
                        (SinglePetrinetController) petrinetController);
                rGraphReinitializer.run();
            }
        }
    }

    @Override
    public void onInfoClick() {
        petrinetEditorView.showInfoDialog(getJavaVersion(), getUserWorkingDirectory());
    }

    @Override
    public void onNextPNMLFileClick() {
        if (petrinetController != null) {
            if (petrinetController instanceof SinglePetrinetController) {
                File nextPNMLFile = ((SinglePetrinetController) petrinetController).getNextWorkingFile();
                if (nextPNMLFile != null) {
                    PNMLFileOpener pnmlFileOpener = new PNMLFileOpener(nextPNMLFile);
                    pnmlFileOpener.openForVisualRepresentation(((SinglePetrinetController) petrinetController));
                    petrinetEditorView.renameTab(nextPNMLFile.getName());
                }
            }
        }
    }

    @Override
    public void onOpenPNMLFileClick() {
        File pnmlFile = null;
        if (petrinetController != null) {
            if (petrinetController instanceof SinglePetrinetController) {
                pnmlFile = ((SinglePetrinetController) petrinetController).getPNMLFile();
            }
        }
        PNMLFileChooser chooser = new PNMLFileChooser(pnmlFile, false);
        int returnState = petrinetEditorView.showOpenDialog(chooser);
        if (returnState == JFileChooser.APPROVE_OPTION) {
            pnmlFile = chooser.getSelectedFile();
            initNewSinglePetrinetController(pnmlFile);
            petrinetEditorView.setCloseTabActionState(true);
        }
    }

    @Override
    public void onPrevGraphClick() {
        if (petrinetController != null) {
            if (petrinetController instanceof SinglePetrinetController) {
                File previousPNMLFile = ((SinglePetrinetController) petrinetController).getPreviousWorkingFile();
                if (previousPNMLFile != null) {
                    PNMLFileOpener pnmlFileOpener = new PNMLFileOpener(previousPNMLFile);
                    pnmlFileOpener.openForVisualRepresentation(((SinglePetrinetController) petrinetController));
                    petrinetEditorView.renameTab(previousPNMLFile.getName());
                }
            }
        }
    }

    @Override
    public void onRedoClick() {
        if (petrinetController != null) {
            if (petrinetController instanceof Caretaker) {
                ((Caretaker) petrinetController).redo();
            }
        }
    }

    @Override
    public void onReloadClick() {
        if (petrinetController != null) {
            if (petrinetController instanceof SinglePetrinetController) {
                PNMLFileOpener pnmlFileOpener = new PNMLFileOpener(
                        ((SinglePetrinetController) petrinetController).getPNMLFile());
                pnmlFileOpener.openForVisualRepresentation(((SinglePetrinetController) petrinetController));
            }
        }
    }

    @Override
    public void onRemEGClick() {
        if (petrinetController != null) {
            if (petrinetController instanceof SinglePetrinetController) {
                PetrinetResetter petrinetResetter = new PetrinetResetter(
                        ((SinglePetrinetController) petrinetController), true);
                petrinetResetter.run();
            }
        }
    }

    @Override
    public void onResetClick() {
        if (petrinetController != null) {
            if (petrinetController instanceof SinglePetrinetController) {
                PetrinetResetter petrinetResetter = new PetrinetResetter(
                        ((SinglePetrinetController) petrinetController), false);
                petrinetResetter.run();
            }
        }
    }

    @Override
    public void onUndoClick() {
        if (petrinetController != null) {
            if (petrinetController instanceof Caretaker) {
                ((Caretaker) petrinetController).undo();
            }
        }
    }

    @Override
    public void togglePermanentAnalysis(boolean status) {
        permanentAnalysis = status;
        if (petrinetController != null) {
            if (petrinetController instanceof SinglePetrinetController) {
                ((SinglePetrinetController) petrinetController).setPermanentAnalysis(status);
            }
        }
    }

    @Override
    public void update(boolean hasUndoStack, boolean hasRedoStack) {
        petrinetEditorView.setUndoActivationState(hasUndoStack);
        petrinetEditorView.setRedoActivationState(hasRedoStack);
    }

    /**
     * Ermittelt die aktuelle Java-Version.
     *
     * @return die Java-Version als {@link String}.
     */
    private String getJavaVersion() {
        return System.getProperty("java.version");
    }

    /**
     * Ermittelt das aktuelle Arbeitsverzeichnis des Programms.
     *
     * @return das Arbeitsverzeichnis als {@link String}.
     */
    private String getUserWorkingDirectory() {
        return System.getProperty("user.dir");
    }

    /**
     * Erzeugt und initiiert einen neuen {@link MultiPetrinetController}.
     *
     * @param pnmlFiles die PNML-Dateien, mit der der
     *                  {@link MultiPetrinetController} initiiert werden soll.
     * @return einen initierten {@link MultiPetrinetController}.
     */
    private MultiPetrinetController initNewMultiPetrinetController(File[] pnmlFiles) {
        Arrays.sort(pnmlFiles);
        PetrinetViewArea petrinetControllerView = new PetrinetViewArea();
        MultiPetrinetController multiPetrinetController = new MultiPetrinetController(pnmlFiles, petrinetControllerView,
                petrinetEditorView);
        petrinetControllers.add(multiPetrinetController);
        petrinetEditorView.addTab(petrinetControllerView, "Batchjob");
        return multiPetrinetController;
    }

    /**
     * Erzeugt und initiiert einen neuen {@link SinglePetrinetController}.
     *
     * @param pnmlFile die PNML-Datei, mit der der {@link SinglePetrinetController}
     *                 initiiert werden soll.
     */
    private void initNewSinglePetrinetController(File pnmlFile) {
        PetrinetViewArea petrinetControllerView = new PetrinetViewArea();
        petrinetController = new SinglePetrinetController(pnmlFile, petrinetControllerView, petrinetEditorView,
                petrinetEditorView, this);
        petrinetControllers.add(petrinetController);
        PNMLFileOpener pnmlFileOpener = new PNMLFileOpener(pnmlFile);
        pnmlFileOpener.openForVisualRepresentation((SinglePetrinetController) petrinetController);
        petrinetEditorView.addTab(petrinetControllerView,
                ((SinglePetrinetController) petrinetController).getPNMLFileName());
    }

    /**
     * Gibt Systemeigenschaften auf der Konsole aus.
     */
    private void printSysProperties() {
        // System Properties ausgegeben
        System.out.println("Besonders wichtige System Properties");
        System.out.println("------------------------------------");
        System.out.println("java.version = " + getJavaVersion());
        System.out.println("user.dir     = " + getUserWorkingDirectory());
        System.out.println();
    }

    /**
     * Setzt den Aktivierungszusstand aller Schaltflächen der
     * {@link PetrinetEditorView} auf einen Zustand.
     *
     * @param state
     */
    private void setPetrinetActionActivationStates(boolean state) {
        petrinetEditorView.setAnalyseSingleActivationState(state);
        petrinetEditorView.setDecActivationState(state);
        petrinetEditorView.setIncActivationState(state);
        petrinetEditorView.setNextActivationState(state);
        petrinetEditorView.setPermanentAnalysisActivationState(state);
        petrinetEditorView.setPrevActivationState(state);
        petrinetEditorView.setRedoActivationState(state);
        petrinetEditorView.setReloadActivationState(state);
        petrinetEditorView.setRemEGActivationState(state);
        petrinetEditorView.setResetActivationState(state);
        petrinetEditorView.setUndoActivationState(state);
    }
}
