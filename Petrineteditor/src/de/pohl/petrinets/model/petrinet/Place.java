package de.pohl.petrinets.model.petrinet;

import de.pohl.petrinets.control.PetrinetEditorGraphProperties;

/**
 * Die Modellklasse der Stelle eines Petrinetzes.
 */
public class Place extends AbstractPetrinetNode {
    private int actualTokens;
    private boolean editFocus;
    private int initialTokens;

    /**
     * Ein Copy-Konstruktor zum Erstellen der Kopie einer {@link Place}.
     *
     * @param place die {@link Place}, die kopiert werden soll.
     */
    public Place(Place place) {
        super(place);
        this.actualTokens = place.actualTokens;
        this.editFocus = place.editFocus;
        this.initialTokens = place.initialTokens;
    }

    /**
     * Erstellt eine neue {@link Place}.
     *
     * @param id die ID der {@link Place} als {@link String}.
     */
    public Place(String id) {
        super(id);
    }

    /**
     * Aktiviert den Editierfokus der {@link Place}.
     */
    public void activateEditFocus() {
        boolean oldValue = editFocus;
        editFocus = true;
        boolean newValue = editFocus;
        firePropertyChange(PetrinetEditorGraphProperties.PLACE_EDITFOCUS, oldValue, newValue);
    }

    /**
     * Deaktiviert den Editierfokus der {@link Place}.
     */
    public void deactivateEditFocus() {
        boolean oldValue = editFocus;
        editFocus = false;
        boolean newValue = editFocus;
        firePropertyChange(PetrinetEditorGraphProperties.PLACE_EDITFOCUS, oldValue, newValue);
    }

    /**
     * Reduziert die Anzahl der aktuellen Marken der {@link Place} um 1 Marke.
     */
    public void decActualTokens() {
        if (actualTokens != 0) {
            int oldValue = actualTokens;
            --actualTokens;
            int newValue = actualTokens;
            firePropertyChange(PetrinetEditorGraphProperties.PLACE_ACTUALTOKENS, oldValue, newValue);
        }
    }

    /**
     * Reduziert die Anzahl der initialen Marken der {@link Place} um 1 Marke.
     */
    public void decInitialTokens() {
        if (initialTokens != 0) {
            int oldValue = initialTokens;
            --initialTokens;
            int newValue = initialTokens;
            firePropertyChange(PetrinetEditorGraphProperties.PLACE_INITIALTOKENS, oldValue, newValue);
        }
    }

    /**
     * Liefert die aktuelle Anzahl der Marken der {@link Place} zurück.
     *
     * @return die aktuelle Markenanzahl als {@link Integer}.
     */
    public int getActualTokens() {
        return actualTokens;
    }

    /**
     * Liefert die initiale Anzahl der Marken der {@link Place} zurück.
     *
     * @return die initiale Markenanzahl als {@link Integer}.
     */
    public int getInitialTokens() {
        return initialTokens;
    }

    /**
     * Erzeugt und liefert das Label des {@link Place} zurück.
     * <p>
     * Format: {@code [id] name <aktuelle Markenanzahl>}
     */
    @Override
    public String getLabel() {
        return String.format("[%1$s] %2$s <%3$s>", getID(), getName(), actualTokens);
    }

    /**
     * Gibt an, ob die {@link Place} den Editierfokus besitzt.
     *
     * @return <code>true</code>, wenn {@link Place} den Editierfokus besitzt.
     */
    public boolean hasEditFocus() {
        return editFocus;
    }

    /**
     * Erhöht die Anzahl der aktuellen Marken der {@link Place} um 1 Marke.
     */
    public void incActualTokens() {
        int oldValue = actualTokens;
        ++actualTokens;
        int newValue = actualTokens;
        firePropertyChange(PetrinetEditorGraphProperties.PLACE_ACTUALTOKENS, oldValue, newValue);
    }

    /**
     * Erhöht die Anzahl der initialen Marken der {@link Place} um 1 Marke.
     */
    public void incInitialTokens() {
        int oldValue = initialTokens;
        ++initialTokens;
        int newValue = initialTokens;
        firePropertyChange(PetrinetEditorGraphProperties.PLACE_INITIALTOKENS, oldValue, newValue);
    }

    /**
     * Ändert die aktuelle Anzahl der Marken der {@link Place}.
     *
     * @param newActualTokens die neue aktuelle Markenanzahl als
     *                        {@link InternalError}.
     * @throws IllegalArgumentException Wenn {@code newActualTokens} kleiner 0.
     */
    public void setActualTokens(int newActualTokens) throws IllegalArgumentException {
        if (newActualTokens < 0) {
            throw new IllegalArgumentException("Tokens dürfen nicht kleiner 0 sein.");
        }
        int oldValue = actualTokens;
        actualTokens = newActualTokens;
        int newValue = actualTokens;
        firePropertyChange(PetrinetEditorGraphProperties.PLACE_ACTUALTOKENS, oldValue, newValue);
    }

    /**
     * Ändert die initiale Anzahl der Marken der {@link Place}.
     *
     * @param newInitialTokens die neue initiale Markenanzahl als
     *                         {@link InternalError}.
     * @throws IllegalArgumentException Wenn {@code newInitialTokens} kleiner 0.
     */
    public void setInitialTokens(int newInitialTokens) throws IllegalArgumentException {
        if (newInitialTokens < 0) {
            throw new IllegalArgumentException("Tokens dürfen nicht kleiner 0 sein.");
        }
        int oldValue = initialTokens;
        initialTokens = newInitialTokens;
        int newValue = initialTokens;
        firePropertyChange(PetrinetEditorGraphProperties.PLACE_INITIALTOKENS, oldValue, newValue);
    }
}
