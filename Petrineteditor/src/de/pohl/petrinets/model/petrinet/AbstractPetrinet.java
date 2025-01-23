package de.pohl.petrinets.model.petrinet;

import java.io.File;
import java.util.ArrayList;

import de.pohl.petrinets.model.AbstractGraph;
import de.pohl.petrinets.model.Originator;

/**
 * Abstrakte Modellklasse eines Petrinetzes.
 * <p>
 * Die Klasse implementiert einen {@link Originator} für ein
 * {@link PetrinetMemento}. Die Implementation der Methoden erfolg in den von
 * {@link AbstractPetrinet} abgeleiteten Klassen.
 */
public abstract class AbstractPetrinet extends AbstractGraph implements Originator<PetrinetMemento> {
    /**
     * Fügt einen {@link Arc} zum {@link AbstractPetrinet} hinzu
     *
     * @param arcID        die ID des neuen {@link Arc} als {@link String}.
     * @param sourceNodeID die ID des Quellknotens als {@link String}.
     * @param targetNodeID die ID des Zielknotens als {@link String}.
     * @throws IllegalArgumentException Wenn entweder Quell und Zielknoten oder
     *                                  einer von beiden nicht vorhanden sind.
     */
    public abstract void addArc(String arcID, String sourceNodeID, String targetNodeID) throws IllegalArgumentException;

    /**
     * Erstellt und fügt dem {@link AbstractPetrinet} eine {@link Place} hinzu.
     *
     * @param placeID die ID der Stelle als {@link String}.
     */
    public abstract void addPlace(String placeID);

    /**
     * Erstellt und fügt dem {@link AbstractPetrinet} eine {@link Transition} hinzu.
     *
     * @param transitionID die ID der Transition als {@link String}.
     */
    public abstract void addTransition(String transitionID);

    /**
     * Überprüft alle {@link Transition} des {@link AbstractPetrinet}, ob diese
     * unter der aktuellen Markierung aktiv sind.
     */
    public abstract void checkAllTransitionstate();

    /**
     * Verringert die Anzahl der Marken einer fokussierten {@link Place} um eine
     * Marke.
     */
    public abstract void decFocusedPlaceTokens();

    /**
     * Erzeugt eine sortierte Liste mit den IDs aller unter der aktuellen Markierung
     * aktiven {@link Transition} des {@link AbstractPetrinet}.
     *
     * @return Eine sortierte {@link ArrayList} mit den IDs als {@link String}.
     */
    public abstract ArrayList<String> getActiveTransitionIDs();

    /**
     * Liefert die aktuelle Markierung des {@link AbstractPetrinet} zurück.
     *
     * @return Eine {@link ArrayList} mit {@link Integer}-Werten als Repräsentation
     *         der aktuellen Markierung.
     */
    public abstract ArrayList<Integer> getActualMarking();

    /**
     * Liefert die initiale Markierung des {@link AbstractPetrinet} zurück.
     *
     * @return Eine {@link ArrayList} mit {@link Integer}-Werten als Repräsentation
     *         der initialen Markierung.
     */
    public abstract ArrayList<Integer> getInitialMarking();

    /**
     * Liefert den Dateipfad der zum {@link AbstractPetrinet} gehörigen PNML-Datei
     * zurück.
     *
     * @return Den Dateipfad als {@link File}.
     */
    public abstract File getPNMLFile();

    /**
     * Liefert den Dateinamern der PNML-Datei des {@link AbstractPetrinet} zurück.
     *
     * @return Der Dateiname als {@link String}.
     */
    public abstract String getPNMLFileName();

    /**
     * Liefert den Namen einer {@link Transition} zurück.
     *
     * @param transitionID die ID der {@link Transition}, deren Name gesucht wird
     *                     als {@link String}.
     * @return Der Name der {@link Transition} als {@link String}.
     */
    public abstract String getTransitionName(String transitionID);

    /**
     * Erhöht die Anzahl der Marken einer fokussierten {@link Place} um eine Marke.
     */
    public abstract void incFocusedPlaceTokens();

    /**
     * Initiert die Anazhl der Tokens eines {@link Place}. Dies betrifft sowohl die
     * initiale Anzahl als auch die aktuelle Anazhl der Tokens eines
     * {@link Place}.<br>
     * Die Methode kann zum Beispiel beim Importieren einer PNML-Datei verwendet
     * werden.
     *
     * @param placeID die ID des {@link Place}, deren Tokens geändert werden sollen
     *                als {@link String}.
     * @param tokens  die neue Anzahl der Tokens als {@link Integer}.
     */
    public abstract void initPlaceTokens(String placeID, int tokens);

    /**
     * Liefert den Modifikationsstatus des {@link Petrinet} zurück.
     *
     * @return <code>true</code>, wenn sich die initiale Markierung seit dem Laden
     *         der PNML-Datei verändert hat.
     */
    public abstract boolean isModified();

    /**
     * Prüft, ob es sich bei dem {@link AbstractPetrinetNode} um ein {@link Place}
     * handelt.
     *
     * @param petrinetNodeID die ID des {@link AbstractPetrinetNode} als
     *                       {@link String}.
     * @return <code>true</code>, wenn der {@link AbstractPetrinetNode} ein
     *         {@link Place} ist.
     */
    public abstract boolean isPlace(String petrinetNodeID);

    /**
     * Prüft, ob es sich bei dem {@link AbstractPetrinetNode} um eine
     * {@link Transition} handelt.
     *
     * @param petrinetnodeID die ID des {@link AbstractPetrinetNode} als
     *                       {@link String}.
     * @return <code>true</code>, wenn der {@link AbstractPetrinetNode} eine
     *         {@link Transition} ist.
     */
    public abstract boolean isTransition(String petrinetnodeID);

    /**
     * Setzt die Markierung des {@link AbstractPetrinet} auf die Anfangsmarkierung
     * zurück.<br>
     * Diese entspricht nicht zwangsweise der Anfangsmarkierung aus der PNML-Datei
     * des {@link AbstractPetrinet}.
     */
    public abstract void resetToInitialMarking();

    /**
     * Ändert die aktuelle Markierung des {@link AbstractPetrinet}.
     *
     * @param newMarking die neue Markierung als {@link ArrayList} mit
     *                   {@link Integer}-Werten.
     */
    public abstract void setActualMarking(ArrayList<Integer> newMarking);

    /**
     * Ändert den Namen einer {@link AbstractPetrinetNode} des
     * {@link AbstractPetrinet} auf den gewünschten Wert.
     *
     * @param petrinetNodeID die ID des {@link AbstractPetrinetNode} als
     *                       {@link String}.
     * @param newName        der neue Name als {@link String}.
     */
    public abstract void setPetrinetNodeName(String petrinetNodeID, String newName);

    /**
     * Ändert die Position eines {@link AbstractPetrinetNode} des
     * {@link AbstractPetrinet} auf den gewünschten Wert.
     *
     * @param petrinetNodeID die ID der {@link AbstractPetrinetNode} als
     *                       {@link String}.
     * @param xPos           die neue horizontale Position als {@link Integer}.
     * @param yPos           die neue vertikale Position als {@link Integer}.
     */
    public abstract void setPetrinetNodePosition(String petrinetNodeID, int xPos, int yPos);

    /**
     * Schaltet den Editier-Fokus einer {@link Place} um.
     *
     * @param placeID die ID der {@link Place} als {@link String}.
     */
    public abstract void togglePlaceEditFocus(String placeID);

    /**
     * Schaltet eine {@link Transition} gemäß der Schaltfunktion.<br>
     * Löst eine Übeprüfung der Transitionszustände (aktiviert/nicht aktiviert) aus.
     *
     * @param transitionID die ID der {@link Transition} als {@link String}.
     * @return Eine {@link ArrayList} mit {@link Integer}-Werten als Repräsentation
     *         der Markierung des {@link AbstractPetrinet} nach dem Schalten der
     *         Transition.<br>
     *         Ist <code>null</code>, wenn die {@link Transition} unter der
     *         aktuellen Markierung nicht aktiviert ist.
     */
    public abstract ArrayList<Integer> toggleTransition(String transitionID);
}