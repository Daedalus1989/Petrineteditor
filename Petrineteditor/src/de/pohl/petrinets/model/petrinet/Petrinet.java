package de.pohl.petrinets.model.petrinet;

import java.awt.Point;
import java.io.File;
import java.util.*;

import de.pohl.petrinets.control.PetrinetEditorGraphProperties;

/**
 * Die Implementierung eines {@link AbstractPetrinet}
 */
public class Petrinet extends AbstractPetrinet {
    private ArrayList<Arc> arcs;
    private String editFocusPlaceID;
    private final File pnmlFile;
    private ArrayList<Place> places;
    private ArrayList<Transition> transitions;
    private boolean modified;

    /**
     * Erstellt ein neues {@link Petrinet}.
     *
     * @param pnmlFile der Dateipfad zur PNML-Datei als {@link File}.
     */
    public Petrinet(File pnmlFile) {
        this.arcs = new ArrayList<>();
        this.places = new ArrayList<>();
        this.transitions = new ArrayList<>();
        this.pnmlFile = new File(pnmlFile.getPath());
    }

    @Override
    public void addArc(String arcID, String sourceID, String targetID) {
        AbstractPetrinetNode sourceNode = this.getPetrinetNode(sourceID);
        AbstractPetrinetNode targetNode = this.getPetrinetNode(targetID);
        if (sourceNode != null && targetNode != null) {
            Arc newArc = new Arc(arcID, sourceID, targetID);
            forewardGraphPCSToGraphElement(newArc);
            this.arcs.add(newArc);
            sourceNode.addOutbound(newArc.getID());
            targetNode.addInbound(newArc.getID());
            this.firePropertyChange(PetrinetEditorGraphProperties.ARC, null, newArc);
        } else {
            if (sourceNode == null && targetNode == null) {
                throw new IllegalArgumentException("Kein Knoten der Kante " + arcID
                        + " befindet sich nicht im Petrinetz. Kante wurde nicht hinzugefügtg.");
            } else if (sourceNode == null) {
                throw new IllegalArgumentException("Der Quellknoten der Kante " + arcID
                        + " befindet sich nicht im Petrinetz. Kante wurde nicht hinzugefügtg.");
            } else {
                throw new IllegalArgumentException("Der Zielknoten der Kante " + arcID
                        + " befindet sich nicht im Petrinetz. Kante wurde nicht hinzugefügtg.");
            }
        }
    }

    @Override
    public void addPlace(String placeID) {
        if (getPlace(placeID) != null) {
            throw new IllegalArgumentException("Es exisitert bereits eine Stelle mit der ID: " + placeID + ".");
        } else {
            Place newPlace = new Place(placeID);
            forewardGraphPCSToGraphElement(newPlace);
            this.places.add(newPlace);
            places.sort(new Comparator<Place>() {
                @Override
                public int compare(Place place1, Place place2) {
                    return place1.getID().compareToIgnoreCase(place2.toString());
                }
            });
            this.firePropertyChange(PetrinetEditorGraphProperties.PLACE, null, placeID);
        }
    }

    @Override
    public void addTransition(String transitionID) {
        if (getTransition(transitionID) != null) {
            throw new IllegalArgumentException("Es exisitert bereits eine Stelle mit der ID: " + transitionID + ".");
        } else {
            Transition newTransition = new Transition(transitionID);
            forewardGraphPCSToGraphElement(newTransition);
            this.transitions.add(newTransition);
            this.firePropertyChange(PetrinetEditorGraphProperties.TRANSITION, null, transitionID);
        }
    }

    /**
     * Überprüft alle Transitionen des Graphen, ob diese unter der aktuellen
     * Markierung aktiv sind.
     */
    @Override
    public void checkAllTransitionstate() {
        for (Transition transition : transitions) {
            this.checkTransitionstate(transition.getID());
        }
    }

    /**
     * Verringert die Anzahl der Marken einer Stelle um eine Marke.
     */
    @Override
    public void decFocusedPlaceTokens() {
        Place place = getPlace(editFocusPlaceID);
        if (place != null) {
            place.decActualTokens();
            setAsNewInitialMarking();
            checkAllTransitionstate();
        }
    }

    @Override
    public ArrayList<String> getActiveTransitionIDs() {
        ArrayList<String> transitionIDs = new ArrayList<>();
        for (Transition transition : transitions) {
            if (transition.isActivated()) {
                transitionIDs.add(transition.getID());
            }
        }
        transitionIDs.sort(null);
        return transitionIDs;
    }

    @Override
    public ArrayList<Integer> getActualMarking() {
        return this.calcMarking(false);
    }

    @Override
    public ArrayList<Integer> getInitialMarking() {
        return this.calcMarking(true);
    }

    @Override
    public File getPNMLFile() {
        return new File(pnmlFile.getPath());
    }

    /**
     * Liefert den Dateinamern der PNML-Datei des {@link AbstractPetrinet}.
     *
     * @return Der Dateiname als {@link String}.
     */
    @Override
    public String getPNMLFileName() {
        return pnmlFile.getName();
    }

    /**
     * Liefert den Namen einer Transition zurück.
     *
     * @param transitionID die ID der Transition, deren Name gesucht wird.
     * @return Der Name der Transition.
     */
    @Override
    public String getTransitionName(String transitionID) {
        return getTransition(transitionID).getName();
    }

    /**
     * Erhöht die Anzahl der Marken einer Stelle um eine Marke.
     */
    @Override
    public void incFocusedPlaceTokens() {
        Place place = getPlace(editFocusPlaceID);
        if (place != null) {
            place.incActualTokens();
            setAsNewInitialMarking();
            checkAllTransitionstate();
        }
    }

    @Override
    public void initPlaceTokens(String placeID, int tokens) {
        Place place = getPlace(placeID);
        if (place == null) {
            throw new NoSuchElementException("Die Stelle mit der ID " + placeID + " konnte nicht gefunden werden.");
        } else {
            if (tokens < 0) {
                throw new IllegalArgumentException("Die Anzahl der Marken einer Stelle darf nicht kleiner 0 sein.");
            } else {
                place.setActualTokens(tokens);
                place.setInitialTokens(tokens);
            }
        }
    }

    /**
     * Liefert den Modifikationsstatus des {@link Petrinet} zurück.
     *
     * @return <code>true</code>, wenn sich die initiale Markierung seit dem Laden
     *         der PNML-Datei verändert hat.
     */
    @Override
    public boolean isModified() {
        return modified;
    }

    @Override
    public boolean isPlace(String petrinetnodeID) {
        return getPlace(petrinetnodeID) == null ? false : true;
    }

    @Override
    public boolean isTransition(String petrinetnodeID) {
        return getTransition(petrinetnodeID) == null ? false : true;
    }

    @Override
    public void resetToInitialMarking() {
        for (Place place : places) {
            int initialTokens = place.getInitialTokens();
            place.setActualTokens(initialTokens);
        }
        this.checkAllTransitionstate();
    }

    @Override
    public void restoreState(PetrinetMemento saveState) {
        ArrayList<Integer> savedActualMarking = saveState.getActualMarking();
        ArrayList<Integer> savedInitialMarking = saveState.getInitialMarking();
        setInitialMarking(savedInitialMarking);
        setActualMarking(savedActualMarking);
        if (saveState.isModified()) {
            setModified();
        } else {
            setUnmodified();
        }
    }

    @Override
    public PetrinetMemento saveState() {
        return new PetrinetMemento(getActualMarking(), getInitialMarking(), this.modified);
    }

    /**
     * Ändert die Markierung des Petrinetzes auf die neue Markierung.
     *
     * @param newActualMarking die neuer Markierung.
     */
    @Override
    public void setActualMarking(ArrayList<Integer> newActualMarking) {
        if (newActualMarking.size() == places.size()) {
            for (int i = 0; i < newActualMarking.size(); i++) {
                Place place = places.get(i);
                int newActualTokens = newActualMarking.get(i);
                place.setActualTokens(newActualTokens);
            }
            System.out.println("Petrinetz auf Zustand " + newActualMarking.toString() + " gesetzt.");
            this.checkAllTransitionstate();
        }
    }

    @Override
    public void setPetrinetNodeName(String nodeID, String newName) {
        AbstractPetrinetNode node = getPetrinetNode(nodeID);
        if (node != null) {
            node.setName(newName);
        } else {
            throw new NoSuchElementException("Der Knoten mit der ID " + nodeID + " konnte nicht gefunden werden.");
        }
    }

    @Override
    public void setPetrinetNodePosition(String nodeID, int xPos, int yPos) {
        AbstractPetrinetNode node = getPetrinetNode(nodeID);
        if (node != null) {
            Point newPos = new Point(xPos, -1 * yPos);
            node.setPosition(newPos);
        } else {
            throw new NoSuchElementException("Der Knoten mit der ID " + nodeID + " konnte nicht gefunden werden.");
        }
    }

    /**
     * Schaltet den Editier-Fokus einer Stelle um.
     *
     * @param placeID die ID der Stelle.
     */
    @Override
    public void togglePlaceEditFocus(String placeID) {
        if (editFocusPlaceID != null) {
            Place oldPlaceEditFocus = getPlace(editFocusPlaceID);
            oldPlaceEditFocus.deactivateEditFocus();
        }
        if (!placeID.equals(editFocusPlaceID)) {
            Place newPlaceEditFocus = getPlace(placeID);
            newPlaceEditFocus.activateEditFocus();
            editFocusPlaceID = placeID;
        } else {
            editFocusPlaceID = null;
        }
    }

    @Override
    public ArrayList<Integer> toggleTransition(String transitionID) {
        Transition transition = getTransition(transitionID);
        if (transition.isActivated()) {
            System.out.println("Die Transition " + transitionID + " ist unter der aktuellen Markierung aktiviert.");
            ArrayList<Integer> oldActualMarking = this.getActualMarking();
            ArrayList<Place> preSet = generatePreSet(transitionID);
            ArrayList<Place> postSet = generatePostSet(transitionID);
            for (Place place : preSet) {
                place.decActualTokens();
            }
            for (Place place : postSet) {
                place.incActualTokens();
            }
            checkAllTransitionstate();
            ArrayList<Integer> newActualMarking = this.getActualMarking();
            System.out.println("Petrinetz wurde vom Zustand " + oldActualMarking.toString() + " in den Zustand "
                    + newActualMarking.toString() + " überführt.");
            return new ArrayList<>(newActualMarking);
        }
        System.out.println("Die Transition " + transitionID + " ist unter der aktuellen Markierung nicht aktiviert.");
        return null;
    }

    /**
     * Ermittelt die Markierung des {@link Petrinet}.
     *
     * @param calcInitialMarking Wenn <code>false</code> wird die aktuelle
     *                           Markierung ermittelt. Andernfalls die initiale
     *                           Markierung.
     *
     * @return Eine {@link ArrayList} mit {@link Integer}-Werten als Repräsentation
     *         der Markierung.
     */
    private ArrayList<Integer> calcMarking(boolean calcInitialMarking) {
        ArrayList<Integer> marking = new ArrayList<>();
        if (calcInitialMarking) {
            for (Place place : places) {
                marking.add(place.getInitialTokens());
            }
        } else {
            for (Place place : places) {
                marking.add(place.getActualTokens());
            }
        }
        return marking;
    }

    /**
     * Überprüft, ob die {@link Transition} des {@link Petrinet} unter der aktuellen
     * Markierung aktiv ist.
     *
     * @param transitionID die ID der {@link Transition} als {@link String}.
     */
    private void checkTransitionstate(String transitionID) {
        Transition transition = this.getTransition(transitionID);
        // Wir gehen davon aus, dass der Vorbereich der Transition die Kriterien
        // erfüllt. Also das alle Stellen im Vorbereich min. eine Marke tragen.
        // Andernfalls erkennt die folgende Schleife dies und deaktiviert die
        // Transition.
        transition.activate();
        for (Arc arc : arcs) {
            AbstractPetrinetNode targetNode = getPetrinetNode(arc.getTargetNodeID());
            if (targetNode.getClass().equals(Transition.class)) {
                String targetID = targetNode.getID();
                Place sourceNode = getPlace(arc.getSourceNodeID());
                int tokens = sourceNode.getActualTokens();
                if (targetID.equalsIgnoreCase(transitionID) && tokens == 0) {
                    transition.deactivate();
                    // Es ist keine weitere Iteration notwendig, da das Kriterium ab hier niemals
                    // erfüllt werden wird.
                    break;
                }
            }
        }
    }

    /**
     * Ermittelt den Nachbereich einer {@link Transition}.
     *
     * @param transitionID die ID der {@link Petrinet} als {@link String}.
     * @return Eine {@link ArrayList} mit {@link Place}-Instanzen.
     */
    private ArrayList<Place> generatePostSet(String transitionID) {
        ArrayList<Place> postSet = new ArrayList<>();
        for (Arc arc : arcs) {
            if (arc.getSourceNodeID().equalsIgnoreCase(transitionID)) {
                Place place = getPlace(arc.getTargetNodeID());
                postSet.add(place);
            }
        }
        return postSet;
    }

    /**
     * Ermittelt den Vorbereich einer {@link Transition}.
     *
     * @param transitionID die ID der {@link Petrinet} als {@link String}.
     * @return Eine {@link ArrayList} mit {@link Place}-Instanzen.
     */
    private ArrayList<Place> generatePreSet(String transitionID) {
        ArrayList<Place> preSet = new ArrayList<>();
        for (Arc arc : arcs) {
            if (arc.getTargetNodeID().equalsIgnoreCase(transitionID)) {
                Place place = getPlace(arc.getSourceNodeID());
                preSet.add(place);
            }
        }
        return preSet;
    }

    /**
     * Durchsucht alle {@link AbstractPetrinetNode} des {@link Petrinet} nach dem
     * {@link AbstractPetrinetNode} mit der angegebenen ID.
     *
     * @param petrinetnodeID die ID des gesuchten {@link AbstractPetrinetNode} als
     *                       {@link String}.
     * @return Den gesuchten {@link AbstractPetrinetNode} oder <code>null</code>,
     *         wenn der gesuchte {@link AbstractPetrinetNode} nicht existiert.
     */
    private AbstractPetrinetNode getPetrinetNode(String petrinetnodeID) {
        ArrayList<AbstractPetrinetNode> petrinetnodes = new ArrayList<>();
        petrinetnodes.addAll(transitions);
        petrinetnodes.addAll(places);
        for (AbstractPetrinetNode petrinetnode : petrinetnodes) {
            if (petrinetnode.getID().equals(petrinetnodeID)) return petrinetnode;
        }
        return null;
    }

    /**
     * Durchsucht alle {@link Place} des {@link Petrinet} nach der {@link Place} mit
     * der angegebenen ID.
     *
     * @param placeID die ID der gesuchten {@link Place} als {@link String}.
     * @return Die gesuchte {@link Place} oder <code>null</code>, wenn der gesuchte
     *         {@link Place} nicht existiert.
     */
    private Place getPlace(String placeID) {
        for (Place place : places) {
            if (place.getID().equals(placeID)) return place;
        }
        return null;
    }

    /**
     * Durchsucht alle {@link Transition} des {@link Petrinet} nach der
     * {@link Transition} mit der angegebenen ID.
     *
     * @param transitionID Die ID der gesuchten {@link Transition} als
     *                     {@link String}.
     * @return Die gesuchte {@link Transition} oder <code>null</code>, wenn der
     *         gesuchte {@link Transition} nicht existiert.
     */
    private Transition getTransition(String transitionID) {
        for (Transition transition : transitions) {
            if (transition.getID().equals(transitionID)) return transition;
        }
        return null;
    }

    /**
     * Setzt die aktuelle Markierung als neue Anfangsmarkierung.
     */
    private void setAsNewInitialMarking() {
        for (Place place : places) {
            int actualTokens = place.getActualTokens();
            place.setInitialTokens(actualTokens);
        }
        checkAllTransitionstate();
        setModified();
    }

    /**
     * Setzt die Markierung als neue Anfangsmarkierung.
     *
     * @param newInitialMarking die neue Anfangsmarkierung als {@link ArrayList} mit
     *                          {@link Integer}-Werten.
     */
    private void setInitialMarking(ArrayList<Integer> newInitialMarking) {
        if (newInitialMarking.size() == places.size()) {
            for (int i = 0; i < newInitialMarking.size(); i++) {
                Place place = places.get(i);
                int newInitialTokens = newInitialMarking.get(i);
                place.setInitialTokens(newInitialTokens);
            }
            System.out.println("Petrinetz auf initialen Zustand " + newInitialMarking.toString() + " gesetzt.");
        }
    }

    /**
     * Ändert den Modifikationsstatus des {@link Petrinet} zu modifiziert.
     */
    private void setModified() {
        boolean oldValue = modified;
        modified = true;
        boolean newValue = modified;
        firePropertyChange(PetrinetEditorGraphProperties.MODIFIED, oldValue, newValue);
    }

    /**
     * Ändert den Modifikationsstatus des {@link Petrinet} zu unmodifiziert.
     */
    private void setUnmodified() {
        boolean oldValue = modified;
        modified = false;
        boolean newValue = modified;
        firePropertyChange(PetrinetEditorGraphProperties.MODIFIED, oldValue, newValue);
    }
}
