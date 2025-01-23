package de.pohl.petrinets.model.reachabilitygraph;

import java.util.ArrayList;

import de.pohl.petrinets.control.PetrinetEditorGraphProperties;
import de.pohl.petrinets.model.AbstractNode;
import de.pohl.petrinets.model.petrinet.AbstractPetrinet;
import de.pohl.petrinets.model.petrinet.Transition;

/**
 * Entitätsklasse eines Knotens eines {@link AbstractReachabilitygraph}.
 */
public class RGraphNode extends AbstractNode {
    private boolean elementOfLastTransition;
    private boolean elementOfUnboundedcause;
    private boolean initalmarking;
    private ArrayList<Integer> marking;
    private ArrayList<String> remainingActiveTransitionIDs;
    private boolean sourcemarkingOfUnboundedcause;
    private boolean targetmarkingOfUnboundedcause;

    /**
     * Ein Copy-Konstruktor zum Erstellen der Kopie einer {@link RGraphNode}.
     *
     * @param rGraphNode die {@link RGraphNode}, die kopiert werden soll.
     */
    public RGraphNode(RGraphNode rGraphNode) {
        super(rGraphNode);
        this.elementOfLastTransition = rGraphNode.elementOfLastTransition;
        this.elementOfUnboundedcause = rGraphNode.elementOfUnboundedcause;
        this.initalmarking = rGraphNode.isInitalmarking();
        this.remainingActiveTransitionIDs = new ArrayList<>();
        this.marking = new ArrayList<>();
        if (rGraphNode.marking != null) {
            this.marking.addAll(rGraphNode.marking);
        }
        if (rGraphNode.remainingActiveTransitionIDs != null) {
            this.remainingActiveTransitionIDs.addAll(rGraphNode.remainingActiveTransitionIDs);
        }
        this.sourcemarkingOfUnboundedcause = rGraphNode.sourcemarkingOfUnboundedcause;
        this.targetmarkingOfUnboundedcause = rGraphNode.sourcemarkingOfUnboundedcause;
    }

    /**
     * Erstellt einen neuen {@link RGraphNode}.
     *
     * @param nodeID die ID des {@link RGraphNode} als {@link String}.
     */
    public RGraphNode(String nodeID) {
        super(nodeID);
        remainingActiveTransitionIDs = new ArrayList<>();
        marking = new ArrayList<>();
    }

    /**
     * Fügt dem Knoten eine aktive {@link Transition} hinzu.
     *
     * @param activeTransitionID die ID der aktiven {@link Transition} als
     *                           {@link String}.
     */
    public void addRemainingActiveTransitionID(String activeTransitionID) {
        ArrayList<String> oldValue = new ArrayList<>(remainingActiveTransitionIDs);
        if (!remainingActiveTransitionIDs.contains(activeTransitionID)) {
            remainingActiveTransitionIDs.add(activeTransitionID);
        }
        ArrayList<String> newValue = new ArrayList<>(remainingActiveTransitionIDs);
        firePropertyChange(PetrinetEditorGraphProperties.NODE_ACTIVE_TRANSITION_IDS, oldValue, newValue);
    }

    /**
     * Erzeugt und liefert das Label des {@link RGraphNode} zurück.
     * <p>
     * Format: {@code (Marken Stelle 0|Marken Stelle 1|...|Marken Stelle n)}
     */
    @Override
    public String getLabel() {
        StringBuilder sb = new StringBuilder();
        for (Integer token : marking) {
            sb.append(sb.isEmpty() ? token : "|" + token);
        }
        return String.format("(%1$s)", sb.toString());
    }

    /**
     * Liefert die Markierung des {@link AbstractPetrinet} zurück, die der
     * {@link RGraphNode} repräsentiert.
     *
     * @return die Markierung des {@link AbstractPetrinet} als {@link ArrayList} mit
     *         {@link Integer}.
     */
    public ArrayList<Integer> getMarking() {
        return new ArrayList<>(marking);
    }

    /**
     * Liefert die auf dem Knoten bzw. auf der Markierung, die der Knoten
     * repräsentiert, aktiven Transitionen zurück.
     *
     * @return the remainingTransitions
     */
    public ArrayList<String> getRemainingActiveTransitionIDs() {
        return new ArrayList<>(remainingActiveTransitionIDs);
    }

    /**
     * Gibt an, ob der {@link RGraphNode} ein Element des letzten Schaltvorganges
     * einer {@link Transition} ist.
     *
     * @return <code>true</code>, wenn Element des letzten Schaltvorganges.
     */
    public boolean isElementOfLastTransition() {
        return elementOfLastTransition;
    }

    /**
     * Gibt an, ob dieser {@link RGraphNode} Element eines Unbeschränktheitspfades
     * ist.
     *
     * @return <code>true</code>, wenn Element eines Unbeschränktheitspfades.
     */
    public boolean isElementOfUnboundedcause() {
        return elementOfUnboundedcause;
    }

    /**
     * Gibt an, ob dieser {@link RGraphNode} die initiale Markierung (Wurzelknoten)
     * des {@link Reachabilitygraph} enthält.
     *
     * @return Wenn <code>true</code> ist der {@link RGraphNode} die initiale
     *         Markierung enthält.
     */
    public boolean isInitalmarking() {
        return initalmarking;
    }

    /**
     * Gibt an, ob dieser {@link RGraphNode} Ursprungsknoten (Knoten m) einer
     * Unbeschränktheit ist.
     *
     * @return Wenn <code>true</code> ist der {@link RGraphNode} Ursprungsknoten
     *         einer Unbeschränktheit.
     */
    public boolean isSourcemarkingOfUnboundedcause() {
        return sourcemarkingOfUnboundedcause;
    }

    /**
     * Gibt an, ob dieser {@link RGraphNode} Zielknoten (Knoten m') einer
     * Unbeschränktheit ist.
     *
     * @return Wenn <code>true</code> ist der {@link RGraphNode} Zielknoten einer
     *         Unbeschränktheit.
     */
    public boolean isTargetmarkingOfUnboundedcause() {
        return targetmarkingOfUnboundedcause;
    }

    /**
     * Entfernt die aktive {@link Transition} aus dem {@link RGraphNode}.
     *
     * @param activeTransitionID die ID der aktiven {@link Transition}.
     */
    public void removeRemainingActiveTransitionID(String activeTransitionID) {
        ArrayList<String> oldValue = new ArrayList<>(remainingActiveTransitionIDs);
        remainingActiveTransitionIDs.remove(activeTransitionID);
        ArrayList<String> newValue = new ArrayList<>(remainingActiveTransitionIDs);
        firePropertyChange(PetrinetEditorGraphProperties.NODE_ACTIVE_TRANSITION_IDS, oldValue, newValue);
    }

    /**
     * Ändert die Eigenschaft, die den {@link RGraphNode} als Teil des letzten
     * Schaltvorganges einer {@link Transition} kennzeichnet.
     *
     * @param isElement wenn <code>true</code>, wird der {@link RGraphNode} als Teil
     *                  des letzten Schaltvorganges gekennzeichnet.
     */
    public void setElementOfLastTransition(boolean isElement) {
        boolean oldValue = elementOfLastTransition;
        elementOfLastTransition = isElement;
        boolean newValue = elementOfLastTransition;
        firePropertyChange(PetrinetEditorGraphProperties.NODE_ELEMENT_OF_LAST_TRANSITION, oldValue, newValue);
    }

    /**
     * Ändert die Eigenschaft, die den {@link RGraphNode} als ein Element eines
     * Unbeschränktheitspfades kennzeichnet.
     *
     * @param isElement wenn <code>true</code>, wird der {@link RGraphNode} als ein
     *                  Element eines Unbeschränktheitspfades kennzeichnet.
     */
    public void setElementOfUnboundedcause(boolean isElement) {
        boolean oldValue = elementOfUnboundedcause;
        elementOfUnboundedcause = isElement;
        boolean newValue = elementOfUnboundedcause;
        firePropertyChange(PetrinetEditorGraphProperties.NODE_ELEMENT_OF_UNBOUNDEDCAUSE, oldValue, newValue);
    }

    /**
     * Ändert den Wert der Eigenschaft die kennzeichnet, dass dieser
     * {@link RGraphNode} die initiale Markierung enhält.
     *
     * @param isInitalmarking wenn <code>true</code>, wird der Knoten
     *                        gekennzeichnet.
     */
    public void setInitalmarking(boolean isInitalmarking) {
        boolean oldValue = initalmarking;
        initalmarking = isInitalmarking;
        boolean newValue = initalmarking;
        firePropertyChange(PetrinetEditorGraphProperties.NODE_INITIALMARKING, oldValue, newValue);
    }

    /**
     * Ändert die Markierung des {@link AbstractPetrinet}, die der
     * {@link RGraphNode} repräsentiert.
     *
     * @param marking die Markierung des {@link AbstractPetrinet} als
     *                {@link ArrayList} mit {@link Integer}.
     */
    public void setMarking(ArrayList<Integer> marking) {
        this.marking = marking;
    }

    /**
     * Ändert die Kennzeichnung die Angibt, dass der {@link RGraphNode}
     * Ursprungsknoten (Knoten m) einer Unbeschränktheit ist.
     *
     * @param isSourcemarking wenn <code>true</code> ist der Knoten ein
     *                        Ursprungsknoten.
     */
    public void setSourcemarkingOfUnboundedcause(boolean isSourcemarking) {
        boolean oldValue = this.sourcemarkingOfUnboundedcause;
        this.sourcemarkingOfUnboundedcause = isSourcemarking;
        boolean newValue = this.sourcemarkingOfUnboundedcause;
        firePropertyChange(PetrinetEditorGraphProperties.NODE_SOURCEMARKING_OF_UNBOUNDEDCAUSE, oldValue, newValue);
    }

    /**
     * Ändert die Kennzeichnung die Angibt, dass der {@link RGraphNode} Zielknoten
     * (Knoten m') einer Unbeschränktheit ist.
     *
     * @param isTargetmarking wenn <code>true</code> ist der Knoten ein Zielknoten.
     */
    public void setTargetmarkingOfUnboundedcause(boolean isTargetmarking) {
        boolean oldValue = this.targetmarkingOfUnboundedcause;
        this.targetmarkingOfUnboundedcause = isTargetmarking;
        boolean newValue = this.targetmarkingOfUnboundedcause;
        firePropertyChange(PetrinetEditorGraphProperties.NODE_TARGETMARKIGN_OF_UNBOUNDEDCAUSE, oldValue, newValue);
    }
}
