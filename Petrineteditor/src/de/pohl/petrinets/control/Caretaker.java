package de.pohl.petrinets.control;

import de.pohl.petrinets.model.Originator;
import de.pohl.petrinets.model.petrinet.PetrinetMemento;
import de.pohl.petrinets.model.reachabilitygraph.RGraphMemento;

/**
 * Ein Interface, dass die Methoden einer Klasse definiert, die ein Caretaker in
 * einem Memento Design Pattern implementiert.
 *
 * @see <a href="https://www.baeldung.com/java-memento-design-pattern">Memento
 *      Design Pattern (URL im Internet)</a>
 * @see CaretakerObserver
 * @see Originator
 * @see PetrinetMemento
 * @see RGraphMemento
 */
public interface Caretaker {
    /**
     * Wird aufgerufen, um den/die Beobachter des {@link Caretaker} zu
     * benachrichtigen.
     */
    public void notifyCaretakerObserver();

    /**
     * Wird aufgerufen, wenn eine Änderung der Markierung des geladenen Petrinetzes
     * Wiederhergestellt werden soll.
     */
    public void redo();

    /**
     * Wird aufgerufen, um den aktuellen Zustand des Petrineztes und des
     * (partiellen) Erreichbarkeitsgraphen zu speichern.
     */
    public void save();

    /**
     * Wird aufgerufen, wenn eine Änderung der Markierung des geladenen Petrinetzes
     * rückgängig gemacht werden soll.
     */
    public void undo();
}
