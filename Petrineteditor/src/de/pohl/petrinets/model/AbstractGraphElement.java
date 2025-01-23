package de.pohl.petrinets.model;

import java.beans.*;

import javax.swing.event.SwingPropertyChangeSupport;

import de.pohl.petrinets.control.PetrinetEditorGraphProperties;

/**
 * Abstrakte Modellklasse eines Graphelementes.
 */
public abstract class AbstractGraphElement {
    private SwingPropertyChangeSupport pcs;
    private final String ID;

    /**
     * Ein Copy-Konstruktor zum Erstellen einer Kopie eines
     * {@link AbstractGraphElement}.
     *
     * @param abstractGraphElement das {@link AbstractGraphElement}, das kopiert
     *                             werden soll.
     */
    public AbstractGraphElement(AbstractGraphElement abstractGraphElement) {
        this.ID = abstractGraphElement.getID();
        this.pcs = abstractGraphElement.pcs;
    }

    /**
     * Erstellt ein neues {@link AbstractGraphElement}.
     *
     * @param id Idendität des {@link AbstractGraphElement} als {@link String}.
     */
    public AbstractGraphElement(String id) {
        this.ID = id;
    }

    /**
     * Liefert die ID des Graphelementes zurück.
     *
     * @return die ID des Graphelementes als {@link String}.
     */
    public String getID() {
        return this.ID;
    }

    /**
     * Erzeugt und Liefert das Label des Graphelementes zurück.
     *
     * @return das Label als {@link String}.
     */
    public abstract String getLabel();

    /**
     * Legt den {@link PropertyChangeSupport} fest, dessen bei ihm angemeldeten
     * {@link PropertyChangeListener} über Änderungen benachrichtigt werden sollen.
     *
     * @param pcs der {@link PropertyChangeSupport}.
     */
    public void setPcs(SwingPropertyChangeSupport pcs) {
        this.pcs = pcs;
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
        this.pcs.firePropertyChange(new PropertyChangeEvent(this, propertyName.toString(), oldValue, newValue));
    }
}
