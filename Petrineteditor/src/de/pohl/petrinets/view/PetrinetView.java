package de.pohl.petrinets.view;

import de.pohl.petrinets.view.gui.components.*;

/**
 * Ein Interface das Methoden definiert, die eine {@link PetrinetView}
 * bereitstellen muss. <br>
 * Die Petrinetview ist die View-Komponente, in der die Graphen und der
 * Nachrichtenbereich angezeigt werden.
 */
public interface PetrinetView extends MessageView {
    /**
     * Setzt die View für ein Petrinetz.
     *
     * @param petrinetView das {@link PetrinetPanel} des Petrinetzes, dass angezeigt
     *                     werden soll.
     */
    public abstract void setPetrinetView(PetrinetPanel petrinetView);

    /**
     * Setzt die View für einen Erreichbarkeitsgraphen.
     *
     * @param reachabilitygraphView das {@link RGraphPanel} des
     *                              Erreichbarkeitsgraphen, das angezeigt werden
     *                              soll.
     */
    public abstract void setRGraphView(RGraphPanel reachabilitygraphView);
}
