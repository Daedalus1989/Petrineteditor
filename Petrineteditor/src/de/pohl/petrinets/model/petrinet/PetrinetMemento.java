package de.pohl.petrinets.model.petrinet;

import java.util.ArrayList;

import de.pohl.petrinets.control.Caretaker;
import de.pohl.petrinets.control.CaretakerObserver;
import de.pohl.petrinets.model.Originator;
import de.pohl.petrinets.model.reachabilitygraph.RGraphMemento;

/**
 * Eine Memento-Klasse für ein {@link AbstractPetrinet} welches den Zustand der
 * Markierungen vor einer Änderung erhält.
 *
 * @see <a href="https://www.baeldung.com/java-memento-design-pattern">Memento
 *      Design Pattern (URL im Internet)</a>
 * @see Caretaker
 * @see CaretakerObserver
 * @see Originator
 * @see RGraphMemento
 */
public class PetrinetMemento {
    private ArrayList<Integer> actualMarking;
    private ArrayList<Integer> initialMarking;
    private boolean modified;

    /**
     * Erstellt eine neue {@link PetrinetMemento}.
     *
     * @param actualMarking  die aktuelle Markierung als {@link ArrayList} mit
     *                       {@link Integer}-Werten.
     * @param initialMarking die initiale Markierung als {@link ArrayList} mit
     *                       {@link Integer}-Werten.
     * @param modified       den Modifikationsstatus des Petrinetzes.
     */
    public PetrinetMemento(ArrayList<Integer> actualMarking, ArrayList<Integer> initialMarking, boolean modified) {
        this.actualMarking = new ArrayList<>();
        if (actualMarking != null) {
            this.actualMarking.addAll(actualMarking);
        }
        this.initialMarking = new ArrayList<>();
        if (initialMarking != null) {
            this.initialMarking.addAll(initialMarking);
        }
        this.modified = modified;
    }

    /**
     * Liefert die im {@link PetrinetMemento} gespeicherte aktuelle Markierung
     * zurück.
     *
     * @return Die im {@link PetrinetMemento} gespeicherte aktuelle Markierung als
     *         {@link ArrayList} mit {@link Integer}-Werten.
     */
    public ArrayList<Integer> getActualMarking() {
        return new ArrayList<>(actualMarking);
    }

    /**
     * Liefert die im {@link PetrinetMemento} gespeicherte initiale Markierung
     * zurück.
     *
     * @return Die im {@link PetrinetMemento} gespeicherte initiale Markierung als
     *         {@link ArrayList} mit {@link Integer}-Werten.
     */
    public ArrayList<Integer> getInitialMarking() {
        return new ArrayList<>(initialMarking);
    }

    /**
     * Liefert den im {@link PetrinetMemento} gespeicherten Modifikationszustand des
     * {@link AbstractPetrinet} zurück.
     *
     * @return Den im {@link PetrinetMemento} gespeicherten Modifikationsstatus des
     *         {@link AbstractPetrinet}.
     */
    public boolean isModified() {
        return modified;
    }
}
