package de.pohl.petrinets.model;

import java.beans.*;

import javax.swing.event.SwingPropertyChangeSupport;

import de.pohl.petrinets.control.PetrinetEditorGraphProperties;

/**
 * Abstrakte Modellklasse eines Graphen.
 */
public abstract class AbstractGraph {
    private SwingPropertyChangeSupport pcs;

    /**
     * Erstellt einen {@link AbstractGraph}.
     */
    public AbstractGraph() {
        pcs = new SwingPropertyChangeSupport(this);
    }

    /**
     * Fügt dem {@link AbstractGraph} einen {@link PropertyChangeListener} hinzu
     *
     * @param listener der {@link PropertyChangeListener}, der hinzugefügt werden
     *                 soll.
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.addPropertyChangeListener(listener);
    }

    /**
     * Entfernt einen {@link PropertyChangeListener} aus dem {@link AbstractGraph}.
     *
     * @param listener der {@link PropertyChangeListener}, der entfernt werden soll.
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.removePropertyChangeListener(listener);
    }

    /**
     * Benachrichtigt alle angemeldten {@link PropertyChangeListener} über eine
     * Änderung.
     * <p>
     * Die Benachrichtigung erfolgt nicht, wenn der alte und neue Wert nicht
     * <code>null</code> sind und übereinstimmen.
     *
     * @param propertyName ein {@link PetrinetEditorGraphProperties}.
     * @param oldValue     der alte Wert der Eigenschaft.
     * @param newValue     der neue Wert der Eigenschaft.
     */
    protected void firePropertyChange(PetrinetEditorGraphProperties propertyName, Object oldValue, Object newValue) {
        this.pcs.firePropertyChange(propertyName.toString(), oldValue, newValue);
    }

    /**
     * Leitet einem {@link AbstractGraphElement} den {@link PropertyChangeSupport}
     * des {@link AbstractGraph} weiter, sodass das {@link AbstractGraph} an die im
     * Graph registierten {@link PropertyChangeListener} auch
     * {@link PropertyChangeEvent} senden kann.
     *
     * @param graphElement ein {@link AbstractGraphElement}.
     */
    final protected void forewardGraphPCSToGraphElement(AbstractGraphElement graphElement) {
        graphElement.setPcs(pcs);
    }
}
