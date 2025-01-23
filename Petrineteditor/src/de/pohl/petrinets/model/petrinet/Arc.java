package de.pohl.petrinets.model.petrinet;

import de.pohl.petrinets.model.AbstractEdge;

/**
 * Die Modellklasse eines Bogens in einem Petrinetz.
 */
public class Arc extends AbstractEdge {
    /**
     * Ein Copy-Konstruktor zum Erstellen der Kopie eines {@link Arc}.
     *
     * @param arc die {@link Arc}, die kopiert werden soll.
     */
    public Arc(Arc arc) {
        super(arc);
    }

    /**
     * Erstellt eine neue {@link Arc}.
     *
     * @param id           die ID als {@link String}.
     * @param soruceNodeID der Ursprungsknoten als {@link AbstractPetrinetNode}.
     * @param targetNodeID der Zielknoten als {@link AbstractPetrinetNode}.
     */
    public Arc(String id, String soruceNodeID, String targetNodeID) {
        super(id, soruceNodeID, targetNodeID);
    }

    /**
     * Erzeugt und liefert das Label des {@link Arc} zurück.
     * <p>
     * Format: {@code [id]}
     */
    @Override
    public String getLabel() {
        return String.format("[%1$s]", getID());
    }
}
