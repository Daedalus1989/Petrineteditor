package de.pohl.petrinets.model.petrinet;

import de.pohl.petrinets.control.PetrinetEditorGraphProperties;

/**
 * Die Modellklasse einer Transition eines Petrinetzes
 */
public class Transition extends AbstractPetrinetNode {
    private boolean isActivated;

    /**
     * Erstellt eine neue {@link Transition}.
     *
     * @param id die Identität der {@link Transition} als {@link String}.
     */
    public Transition(String id) {
        super(id);
    }

    /**
     * Ein Copy-Konstruktor zum Erstellen der Kopie einer {@link Transition}.
     *
     * @param transition die {@link Transition}, die kopiert werden soll.
     */
    public Transition(Transition transition) {
        super(transition);
        this.isActivated = transition.isActivated;
    }

    /**
     * Aktiviert die {@link Transition}.
     */
    public void activate() {
        boolean oldValue = isActivated;
        isActivated = true;
        boolean newValue = isActivated;
        firePropertyChange(PetrinetEditorGraphProperties.TRANSITION_ACTIVATION, oldValue, newValue);
    }

    /**
     * Deaktiviert die {@link Transition}.
     */
    public void deactivate() {
        boolean oldValue = isActivated;
        isActivated = false;
        boolean newValue = isActivated;
        firePropertyChange(PetrinetEditorGraphProperties.TRANSITION_ACTIVATION, oldValue, newValue);
    }

    /**
     * Erzeugt und liefert das Label der {@link Transition} zurück.
     * <p>
     * Format: {@code [id] name}
     */
    @Override
    public String getLabel() {
        return String.format("[%1$s] %2$s", getID(), getName());
    }

    /**
     * Gibt an, ob die {@link Transition} unter der aktuellen Markierung aktiviert
     * ist.
     *
     * @return <code>false</code>, wenn Transition nicht aktiv ist.
     */
    public boolean isActivated() {
        return isActivated;
    }
}
