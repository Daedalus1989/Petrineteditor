package de.pohl.petrinets.model.reachabilitygraph;

import de.pohl.petrinets.control.PetrinetEditorGraphProperties;
import de.pohl.petrinets.model.AbstractEdge;
import de.pohl.petrinets.model.petrinet.Transition;

/**
 * Modellklasse einer Kante eines {@link AbstractReachabilitygraph}.
 */
public class RGraphEdge extends AbstractEdge {
    private boolean elementOfLastTransition;
    private boolean elementOfUnboundedcause;
    private String transitionID;
    private String transitionName;

    /**
     * Ein Copy-Konstruktor zum Erstellen der Kopie einer {@link RGraphEdge}.
     *
     * @param rGraphEdge die {@link RGraphEdge}, die kopiert werden soll.
     */
    public RGraphEdge(RGraphEdge rGraphEdge) {
        super(rGraphEdge);
        this.elementOfLastTransition = rGraphEdge.elementOfLastTransition;
        this.elementOfUnboundedcause = rGraphEdge.elementOfUnboundedcause;
        this.transitionID = rGraphEdge.transitionID;
        this.transitionName = rGraphEdge.transitionName;
    }

    /**
     * Erstellt eine {@link RGraphEdge}.
     *
     * @param id             die ID der {@link RGraphEdge} als {@link String}.
     * @param transitionID   die ID der {@link Transition}, auf die die
     *                       {@link RGraphEdge} referenziert als {@link String}.
     * @param transitionName der Name der {@link Transition}, auf die die
     *                       {@link RGraphEdge} referenziert als {@link String}.
     * @param sourceNodeID   der Ursprungsknoten der {@link RGraphEdge} als
     *                       {@link RGraphNode}.
     * @param targetNodeID   der Zielknoten der {@link RGraphEdge} als
     *                       {@link RGraphNode}.
     */
    public RGraphEdge(String id, String transitionID, String transitionName, String sourceNodeID, String targetNodeID) {
        super(id, sourceNodeID, targetNodeID);
        this.transitionID = transitionID;
        this.transitionName = transitionName;
    }

    /**
     * Erzeugt und liefert das Label der {@link RGraphEdge} zurück.
     * <p>
     * Format: {@code [id der Transition] Name der Transition}
     */
    @Override
    public String getLabel() {
        return String.format("[%1$s] %2$s", transitionID, transitionName);
    }

    /**
     * Liefert die ID der {@link Transition} zurück, auf die {@link RGraphEdge}
     * referenziert.
     *
     * @return Die ID der {@link Transition} als {@link String}.
     */
    public String getTransitionID() {
        return transitionID;
    }

    /**
     * Liefert den Namen der {@link Transition} zurück, auf die {@link RGraphEdge}
     * referenziert.
     *
     * @return Der Name der {@link Transition} als {@link String}.
     */
    public String getTransitionName() {
        return transitionName;
    }

    /**
     * Gibt an, ob die {@link RGraphEdge} ein Element des letzten Schaltvorganges
     * einer {@link Transition} ist.
     *
     * @return <code>true</code>, wenn Element des letzten Schaltvorganges.
     */
    public boolean isElementOfLastTransition() {
        return elementOfLastTransition;
    }

    /**
     * Gibt an, ob diese {@link RGraphEdge} Element eines Unbeschränktheitspfades
     * ist.
     *
     * @return <code>true</code>, wenn Element eines Unbeschränktheitspfades.
     */
    public boolean isElementOfUnboundedcause() {
        return elementOfUnboundedcause;
    }

    /**
     * Ändert die Eigenschaft, die die {@link RGraphEdge} als Teil des letzten
     * Schaltvorganges einer {@link Transition} kennzeichnet.
     *
     * @param isElement wenn <code>true</code>, wird die {@link RGraphEdge} als Teil
     *                  des letzten Schaltvorganges gekennzeichnet.
     */
    public void setElementOfLastTransition(boolean isElement) {
        boolean oldValue = elementOfLastTransition;
        elementOfLastTransition = isElement;
        boolean newValue = elementOfLastTransition;
        firePropertyChange(PetrinetEditorGraphProperties.EDGE_ELEMENT_OF_LAST_TRANSITION, oldValue, newValue);
    }

    /**
     * Ändert die Eigenschaft, die die {@link RGraphEdge} als ein Element eines
     * Unbeschränktheitspfades kennzeichnet.
     *
     * @param isElement wenn <code>true</code>, wird die {@link RGraphEdge} als ein
     *                  Element eines Unbeschränktheitspfades kennzeichnet.
     */
    public void setElementOfUnboundedcause(boolean isElement) {
        boolean oldValue = elementOfUnboundedcause;
        elementOfUnboundedcause = isElement;
        boolean newValue = elementOfUnboundedcause;
        firePropertyChange(PetrinetEditorGraphProperties.EDGE_ELEMENT_OF_UNBOUNDEDCAUSE, oldValue, newValue);
    }

    /**
     * Ändert die ID der {@link Transition}, auf die {@link RGraphEdge}
     * referenziert.
     *
     * @param transitionID die ID der {@link Transition} als {@link String}.
     */
    public void setTransitionID(String transitionID) {
        this.transitionID = transitionID;
    }

    /**
     * Ändert den Wert des Namens der {@link Transition}, auf die diese
     * {@link RGraphEdge} referenziert.
     *
     * @param transitionName den Wert des Namens der {@link Transition} als String.
     */
    public void setTransitionName(String transitionName) {
        this.transitionName = transitionName;
    }
}
