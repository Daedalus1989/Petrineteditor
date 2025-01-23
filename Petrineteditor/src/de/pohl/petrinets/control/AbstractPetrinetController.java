package de.pohl.petrinets.control;

import java.util.ArrayList;

import de.pohl.petrinets.control.implementations.usecases.BoundednessAnalyser;
import de.pohl.petrinets.model.petrinet.AbstractPetrinet;
import de.pohl.petrinets.model.petrinet.Transition;
import de.pohl.petrinets.model.reachabilitygraph.*;
import de.pohl.petrinets.presenter.SingleAnalysisResultPresenter;
import de.pohl.petrinets.view.*;

/**
 * Eine abstrakte Kontrollklasse für eine Petrinetinstanz. Eine Petrinetinstanz
 * besteht immer mindestens aus einem {@link AbstractPetrinet} und einem
 * dazugehörigen {@link AbstractReachabilitygraph}, der die Markierungen des
 * {@link AbstractPetrinet} repräsentiert. Sie dient als Zwischenschicht
 * zwischen den Modell- und Viewkomponenten.
 * <p>
 * Die Klasse implementiert {@link Runnable}, ohne jedoch die Methode
 * {@link Runnable#run()} zu implementieren. Diese wird in den Subklassen von
 * {@link AbstractPetrinetController} implementiert.
 * <p>
 * In dieser Klasse ist der Algorithmus für die Simulation eines Petrinetzes
 * implementiert: {@link #runSimulation(AnalysisResultDialogView)}.
 */
public abstract class AbstractPetrinetController implements Runnable {
    /**
     * Ein {@link AbstractPetrinet} als Modellkomponenten für ein Petrinetz.
     */
    protected AbstractPetrinet petrinetModel;
    /**
     * Ein {@link AbstractReachabilitygraph} als Modellkomponente für einen
     * Erreichbarkeitsgraphen.
     */
    protected AbstractReachabilitygraph rGraphModel;
    /**
     * Eine {@link PetrinetView} als View-Komponente.
     */
    protected PetrinetView petrinetControllerView;
    /**
     * Ein {@link SingleAnalysisResultPresenter} als Presenter-Komponente.
     */
    protected SingleAnalysisResultPresenter resultPresenter;
    /**
     * Eine {@link PetrinetStatusView} zur Anzeige der Statusinformationen.
     */
    protected PetrinetStatusView petrinetStatusView;

    /**
     * Erstellt einen neuen {@link AbstractPetrinetController}.
     *
     * @param petrinetControllerView eine {@link PetrinetView}.
     * @param petrinetStatusView     eine {@link PetrinetStatusView}.
     */
    public AbstractPetrinetController(PetrinetView petrinetControllerView, PetrinetStatusView petrinetStatusView) {
        this.petrinetControllerView = petrinetControllerView;
        this.petrinetStatusView = petrinetStatusView;
    }

    /**
     * Startet die Simulation eines {@link AbstractPetrinet} mit dem dazugehörigen
     * {@link AbstractReachabilitygraph}.<br>
     * Dabei wird in einem DFS-Algorithmus versucht, alle auf einer Markierung
     * aktiven {@link Transition} genau ein Mal zu schalten. Nach jedem Schalten
     * erfolgt eine Beschränktheitsanalyse. <br>
     * Ist ein {@link AbstractPetrinet} beschränkt, so gibt es irgendwann keine
     * Markierungen mehr, auf der eine unter dieser Markierung aktive
     * {@link Transition} noch nicht geschaltet wurde.<br>
     * Gibt es hingegen unendlich viele Markierungen, so erkennt die
     * Beschränktheitsanalyse dies und der Simulationsalgorithmus bricht ab.
     *
     * @param analsAnalysisResultDialogView (Optional) <br>
     *                                      Eine {@link AnalysisResultDialogView}
     *                                      zur Ausgabe des Ergebnisses in einem
     *                                      Dialogfenster.<br>
     *                                      Wenn <code>null</code> erfolgt die
     *                                      ausgabe nur in der {@link PetrinetView}.
     * @see BoundednessAnalyser
     * @see SingleAnalysisResultPresenter
     */
    public void runSimulation(AnalysisResultDialogView analsAnalysisResultDialogView) {
        System.out.println("---------------");
        System.out.println("Starte Simulation des Petrinetzes " + petrinetModel.getPNMLFileName());
        System.out.println("---------------");
        resultPresenter = new SingleAnalysisResultPresenter(petrinetModel.getPNMLFileName(), rGraphModel,
                petrinetControllerView);
        // Den Wurzelknoten es Erreichbarkeitsgraphen ermitteln der der
        // Anfangsmarkierung des Petrinetzes entspricht.
        String initialRGraphNodeID = rGraphModel.getInitialNodeID();
        boolean isUnbounded = simulationRecursion(initialRGraphNodeID);
        if (isUnbounded) {
            System.out.println("Abbruch der Simulation, da Aufruf das Petrinetz unbeschränkt ist.");
            resultPresenter.printResult(analsAnalysisResultDialogView);
            return;
        }
        System.out.println("Keine weiteren aktiven Transitionen bei " + rGraphModel.getNodeLabel(initialRGraphNodeID));
        System.out.println("---------------");
        System.out.println("Beende Simulation des Petrinetzes " + petrinetModel.getPNMLFile().getName());
        System.out.println("---------------");
        resultPresenter.setResultPetrinetIsBounded();
        resultPresenter.printResult(analsAnalysisResultDialogView);
    }

    /**
     * Behandelt das Eregnis, dass der Tab {@link AbstractPetrinetController} in der
     * {@link PetrinetView} ausgewählt wurde und die {@link PetrinetView} dieser
     * Instanz nun angezeigt wird.
     */
    public abstract void select();

    /**
     * Ändert das {@link AbstractPetrinet} des {@link AbstractPetrinetController}.
     *
     * @param petrinetModel ein {@link AbstractPetrinet}.
     */
    public void setPetrinetModel(AbstractPetrinet petrinetModel) {
        this.petrinetModel = petrinetModel;
    }

    /**
     * Ändert das {@link AbstractReachabilitygraph} des
     * {@link AbstractPetrinetController}.
     *
     * @param rGraphModel ein {@link AbstractReachabilitygraph}.
     */
    public void setRGraphModel(AbstractReachabilitygraph rGraphModel) {
        this.rGraphModel = rGraphModel;
    }

    /**
     * Rekursiver Aufruf der Schaltung von Transitionen eines Petrinetzes.
     * <p>
     * Basiert auf einem DFS-Algorithmus. Das bedeutet, dass der Graph zunächst so
     * tief wie möglich durchlaufen wird, bis keine schaltbaren Transitionen mehr
     * exisiteren. Ist dieser Punkt erreicht, kehrt der Algorithmus zu einem
     * Zeitpunkt zurück, zu dem auf dem Petrinetz noch schaltbare Transitionen
     * existierten.
     *
     * @param currentRGraphNodeID die ID des {@link RGraphNode} auf dem die
     *                            Simulationsrekursion ausgeführt werden soll.
     * @return <code>true</code>, wenn das {@link AbstractPetrinet} unbeschränkt
     *         ist.<br>
     *         <code>false</code>, wenn das {@link AbstractPetrinet} beschränkt ist.
     */
    private boolean simulationRecursion(String currentRGraphNodeID) {
        ArrayList<Integer> currentMarking = rGraphModel.getNodeMarking(currentRGraphNodeID);
        System.out.println("Rekursionsaufruf auf Zustand: " + currentMarking.toString());
        // Abbruchkriterium
        // Unbeschräktheitsanalsye durchführen. Wenn unbeschränkt, dann Simulation
        // abbrechen.
        ArrayList<String> rGraphEdgePath = new BoundednessAnalyser(rGraphModel).run(currentRGraphNodeID);
        if (rGraphEdgePath != null) {
            System.out.println("Abbruch der Rekursion bei: " + currentMarking.toString());
            resultPresenter.setResultPetrinetIsUndbounded(rGraphEdgePath, true);
            return true;
        }
        // Alle auf dieser Markierung aktiven Transitionen ermitteln
        ArrayList<String> activeTransitionIDs = petrinetModel.getActiveTransitionIDs();
        System.out.println("Aktive Transitionen: " + activeTransitionIDs.toString());
        // Jede aktive Transition schalten
        for (String currentTransitionID : activeTransitionIDs) {
            String rGraphEdge = toggleTransition(currentTransitionID, false);
            if (rGraphEdge != null) {
                // Rekursiver Aufruf der Simulation
                String nextRGraphNodeID = rGraphModel.getEdgeTargetID(rGraphEdge);
                boolean isUnbounded = simulationRecursion(nextRGraphNodeID);
                if (isUnbounded) {
                    System.out.println("Abbruch der Rekursion bei " + currentMarking.toString() + ", da Aufruf auf "
                            + rGraphModel.getNodeLabel(rGraphModel.getEdgeTargetID(rGraphEdge))
                            + " unbeschränktheit gemeldet hat.");
                    return true;
                }
            }
            // Markierung des Petrinetzes zurücksetzen
            petrinetModel.setActualMarking(currentMarking);
        }
        System.out.println("Keine weiteren aktiven Transitionen bei " + currentMarking.toString());
        return false;
    }

    /**
     * Führt das Schalten einer {@link Transition} eines {@link AbstractPetrinet}
     * auf Basis der aktuellen Markierung des {@link AbstractPetrinet} aus.<br>
     * Dabei wird die Aktuelle Markierung eines {@link AbstractPetrinet} unter
     * Einhaltung der Schaltfunktion in eine Zielmarkierung überführt und ggf. eine
     * Kante in ein {@link AbstractReachabilitygraph} eingefügt und der
     * Schaltvorgang hervorgehoben.
     *
     * @param transitionID   die ID de {@link Transition}, die geschaltet werden
     *                       soll, als {@link String}.
     * @param returnExisting wenn <code>true</code> wird auch eine im
     *                       {@link AbstractReachabilitygraph} bereits enthaltene
     *                       Kante als Repräsentation eines Schaltvorganges
     *                       zurückgegeben.<br>
     *                       Dies ist z.B. dann sinnvoll, wenn bei manuellem
     *                       Schalten auch ein bereits vorhandener Schaltvorgang im
     *                       {@link AbstractReachabilitygraph} hervorgehoben werden
     *                       soll.
     * @return Die ID der {@link RGraphEdge} im {@link AbstractReachabilitygraph},
     *         die den Schaltvorgang repräsentiert.
     */
    protected String toggleTransition(String transitionID, boolean returnExisting) {
        ArrayList<Integer> oldMarking = petrinetModel.getActualMarking();
        ArrayList<Integer> newMarking = petrinetModel.toggleTransition(transitionID);
        if (newMarking != null) {
            String transitionName = petrinetModel.getTransitionName(transitionID);
            String edgeID = rGraphModel.addMarking(transitionID, transitionName, oldMarking, newMarking,
                    petrinetModel.getActiveTransitionIDs(), returnExisting);
            rGraphModel.highlightTransition(transitionID, oldMarking, newMarking);
            return edgeID;
        }
        return null;
    }
}
