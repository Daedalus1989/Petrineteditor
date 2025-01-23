package de.pohl.petrinets.model;

import de.pohl.petrinets.control.Caretaker;
import de.pohl.petrinets.control.CaretakerObserver;
import de.pohl.petrinets.model.petrinet.PetrinetMemento;
import de.pohl.petrinets.model.reachabilitygraph.RGraphMemento;

/**
 * Ein Interface, dass die Methoden einer Klasse definiert, die ein Originator
 * (Urheber) in einem Memento Design Pattern implementiert.
 *
 * @param <T> Typparameter für die Art der Memento, die ein Originator erzeut.
 * @see <a href="https://www.baeldung.com/java-memento-design-pattern">Memento
 *      Design Pattern (URL im Internet)</a>
 * @see Caretaker
 * @see CaretakerObserver
 * @see PetrinetMemento
 * @see RGraphMemento
 */
public interface Originator<T> {
    /**
     * Wird aufgerufen, um einen früheren Zustand wiederherzustellen.
     *
     * @param saveState ein Memento mit dem Status, der Wiederhergestellt werden
     *                  soll.
     */
    void restoreState(T saveState);

    /**
     * Wird aufgerufen, um den aktuellen Zustand zu sichern
     *
     * @return Ein T mit dem für den Zustand relevanten Informationen.
     */
    T saveState();
}
