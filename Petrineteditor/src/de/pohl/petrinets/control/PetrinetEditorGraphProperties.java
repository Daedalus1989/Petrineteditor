package de.pohl.petrinets.control;

/**
 * Aufzählungsklasse mit den Petrinetz- und Erreichbarkeitsgrapheigenschaften.
 */
public enum PetrinetEditorGraphProperties {
    /**
     * Kante als Eigenschaft eines Erreichbarkeitsgraphen.
     */
    EDGE,
    /**
     * Die Eigenschaft, die eine Kante eines Erreichbarkeitsgraphen als Element der
     * zuletzt geschalteten Transition kennzeichnet.
     */
    EDGE_ELEMENT_OF_LAST_TRANSITION,
    /**
     * Die Eigenschaft, die eine Kante eines Erreichbarkeitsgraphen als Element
     * eines Unbeschränktheitspfades kennzeichnet.
     */
    EDGE_ELEMENT_OF_UNBOUNDEDCAUSE,
    /**
     * Knoten als Eigenschaft eines Erreichbarkeitsgraphen.
     */
    NODE,
    /**
     * Die Eigenschaft, die die noch nicht geschalteten aktiven Transitionen
     * beinhaltet, die auf der durch den Knoten repräsentierten Markierung
     * existieren.
     */
    NODE_ACTIVE_TRANSITION_IDS,
    /**
     * Die Eigenschaft, die einen Knoten eines Erreichbarkeitsgraphen als Element
     * der zuletzt geschalteten Transition kennzeichnet.
     */
    NODE_ELEMENT_OF_LAST_TRANSITION,
    /**
     * Die Eigenschaft, die einen Knoten eines Erreichbarkeitsgraphen als Element
     * eines Unbeschränktheitspfades kennzeichnet.
     */
    NODE_ELEMENT_OF_UNBOUNDEDCAUSE,
    /**
     * Die Eigenschaft, die einen Knoten eines Erreichbarkeitsgraphen als Knoten mit
     * der initialen Markierung eines Petrinetzes kennzeichnet
     */
    NODE_INITIALMARKING,
    /**
     * Die Eigenschaft, die einen Knoten eines Erreichbarkeitsgraphen als
     * Ursprungsknoten (Knoten m) einer Unbeschränktheit kennzeichnet.
     */
    NODE_SOURCEMARKING_OF_UNBOUNDEDCAUSE,
    /**
     * Die Eigenschaft, die einen Knoten eines Erreichbarkeitsgraphen als Zielknoten
     * (Knoten m') einer Unbeschränktheit kennzeichnet.
     */
    NODE_TARGETMARKIGN_OF_UNBOUNDEDCAUSE,
    /**
     * Bogen als Eigenschaft eines Petrinetzes.
     */
    ARC,
    /**
     * Der Name eines Petrinetzknotens.
     */
    PETRINETNODE_NAME,
    /**
     * Die Position eines Petrinetzknotens.
     */
    PETRINETNODE_POSITION,
    /**
     * Die Anzahl der aktuellen Marken einer Stelle.
     */
    PLACE_ACTUALTOKENS,
    /**
     * Die Eigenschaft, die eine Stelle kennzeichnet, die den Editier-Fokus besitzt.
     */
    PLACE_EDITFOCUS,
    /**
     * Die Anzahl der initialen Marken einer Stelle.
     */
    PLACE_INITIALTOKENS,
    /**
     * Stelle als Eigenschaft eines Petrinetzes.
     */
    PLACE,
    /**
     * Die Eigenschaft, die eine Transition kennzeichnet, die unter der aktuellen
     * Markierung eines Petrinetzes aktiviert ist.
     */
    TRANSITION_ACTIVATION,
    /**
     * Transition als Eigenschaft eines Petrinetzes.
     */
    TRANSITION,
    /**
     * Modifikationsstatus als Eigenschaft eines Petrinetzes.
     */
    MODIFIED
}
