/**
 *
 */
package de.pohl.petrinets.control;

import de.pohl.petrinets.model.Originator;
import de.pohl.petrinets.model.petrinet.PetrinetMemento;
import de.pohl.petrinets.model.reachabilitygraph.RGraphMemento;

/**
 * Ein Interface, dass die Methoden einer Klasse definiert, die ein
 * Caretaker-Beobachter in einem Memento Design Pattern implementiert.
 *
 * @see <a href="https://www.baeldung.com/java-memento-design-pattern">Memento
 *      Design Pattern (URL im Internet)</a>
 * @see Caretaker
 * @see Originator
 * @see PetrinetMemento
 * @see RGraphMemento
 */
public interface CaretakerObserver {
    /**
     * Wird aufgerufen, wenn sich der Status der Caretakers geändert hat.
     *
     * @param hasUndoStack der Status des Undo-Stacks.
     * @param hasRedoStack der Status des Redo-Stacks.
     */
    public void update(boolean hasUndoStack, boolean hasRedoStack);
}
