package de.pohl.petrinets.model.petrinet;

import java.awt.Point;

import javax.swing.text.Position;

import de.pohl.petrinets.control.PetrinetEditorGraphProperties;
import de.pohl.petrinets.model.AbstractNode;

/**
 * Abstrakte Modellklasse eines Petrinetzknotens.
 */
public abstract class AbstractPetrinetNode extends AbstractNode {
    private String name;
    private Point position;

    /**
     * Ein Copy-Konstruktor zum Erstellen der Kopie einer
     * {@link AbstractPetrinetNode}.
     *
     * @param abstractPetrinetNode die {@link AbstractPetrinetNode}, die kopiert
     *                             werden soll.
     */
    public AbstractPetrinetNode(AbstractPetrinetNode abstractPetrinetNode) {
        super(abstractPetrinetNode);
        this.name = abstractPetrinetNode.name;
        this.position = new Point(abstractPetrinetNode.position);
    }

    /**
     * Erstellt einen neuen {@link AbstractPetrinetNode}.
     *
     * @param id die ID des {@link AbstractPetrinetNode} als {@link String}.
     */
    public AbstractPetrinetNode(String id) {
        super(id);
    }

    /**
     * Liefert den Namen des {@link AbstractPetrinetNode} zurück.
     *
     * @return der Name des {@link AbstractPetrinetNode} als {@link String}.
     */
    public String getName() {
        return name;
    }

    /**
     * Liefert die Postion des {@link AbstractPetrinetNode} zurück.
     *
     * @return die Position eines {@link AbstractPetrinetNode} als {@link Point}.
     */
    public Point getPosition() {
        return new Point(position);
    }

    /**
     * Ändert den Namen des {@link AbstractPetrinetNode}.
     *
     * @param newName der neue Name als {@link String}.
     */
    public void setName(String newName) {
        String oldValue = name;
        name = newName;
        String newValue = name;
        firePropertyChange(PetrinetEditorGraphProperties.PETRINETNODE_NAME, oldValue, newValue);
    }

    /**
     * Ändert die Position des {@link AbstractPetrinetNode}.
     *
     * @param newPosition die neue Position als {@link Position}.
     */
    public void setPosition(Point newPosition) {
        Point oldValue = position;
        position = new Point(newPosition);
        Point newValue = position;
        firePropertyChange(PetrinetEditorGraphProperties.PETRINETNODE_POSITION, oldValue, newValue);
    }
}