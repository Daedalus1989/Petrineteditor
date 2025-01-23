package de.pohl.petrinets.view.model;

import java.awt.Point;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Arrays;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.ui.spriteManager.Sprite;
import org.graphstream.ui.spriteManager.SpriteManager;

import de.pohl.petrinets.control.PetrinetEditorGraphProperties;
import de.pohl.petrinets.model.petrinet.*;

/**
 * Viewmodelklasse zur Darstellung eines {@link AbstractPetrinet}.
 * <p>
 * Kennt ein {@link MultiGraph} und verwaltet die Informationen auf diesen.
 * <p>
 * Implementiert {@link PropertyChangeListener} und reagiert so auf
 * Eigenschaftsänderungen eines {@link AbstractPetrinet}.
 */
public class PetrinetViewModel implements PropertyChangeListener {
    // Layout des Graphen
    private static String CSS_FILE = "url(" + PetrinetViewModel.class.getResource("/Petrinet.css") + ")";
    // Anzeigedatenmodell der View
    private MultiGraph graph;
    // Der SpriteManager des Graphen
    private SpriteManager spriteMan;

    /**
     * Erstellt ein neues {@link PetrinetViewModel}.
     *
     * @param pnmlFileName der Dateiname des {@link Petrinet} als {@link String}.
     */
    public PetrinetViewModel(String pnmlFileName) {
        this.graph = new MultiGraph(pnmlFileName);
        this.graph.setAttribute("ui.stylesheet", CSS_FILE);
        this.spriteMan = new SpriteManager(graph);
    }

    /**
     * Liefert den {@link MultiGraph} des {@link PetrinetViewModel} zurück. <br>
     * Es wird kein neuer, eigenständiger {@link MultiGraph} übergeben, sondern die
     * in der {@link PetrinetViewModel}-Instanz initiierte
     * {@link MultiGraph}-Instanz, da über diese Kopplung der Datenaustausch mit der
     * View erfolgen soll.
     *
     * @return Der {@link MultiGraph} des {@link PetrinetViewModel} zurück.
     */
    public MultiGraph getGraph() {
        return graph;
    }

    /**
     * Diese Methode wird aufgerufen, wenn sich eine Eigenschaft eines
     * {@link AbstractPetrinet} geändert hat.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String evtPropertyName = evt.getPropertyName();
        if (evtPropertyName.equals(PetrinetEditorGraphProperties.TRANSITION.toString())) {
            // Neue Transition wurde hinzugefügt.
            String transitionID = (String) evt.getNewValue();
            addTransition(transitionID);
        } else if (evtPropertyName.equals(PetrinetEditorGraphProperties.PLACE.toString())) {
            // Neue Stelle wurde hinzugefügt
            String placeID = (String) evt.getNewValue();
            addPlace(placeID);
        } else if (evtPropertyName.equals(PetrinetEditorGraphProperties.ARC.toString())) {
            // Neue Kante wurde hinzugefügt.
            Arc edge = (Arc) evt.getNewValue();
            String arcID = edge.getID();
            String sourceID = edge.getSourceNodeID();
            String targetID = edge.getTargetNodeID();
            String arcLabel = edge.getLabel();
            addArc(arcID, sourceID, targetID, arcLabel);
        } else if (evtPropertyName.equals(PetrinetEditorGraphProperties.PETRINETNODE_NAME.toString())) {
            // Der Name eines Knotens hat sich geändert.
            String petrinetnodeID = ((AbstractPetrinetNode) evt.getSource()).getID();
            String newLabelText = ((AbstractPetrinetNode) evt.getSource()).getLabel();
            setNodeLabel(petrinetnodeID, newLabelText);
        } else if (evtPropertyName.equals(PetrinetEditorGraphProperties.PETRINETNODE_POSITION.toString())) {
            // Die Position eines Knontes hat sich geändert
            String petrinetnodeID = ((AbstractPetrinetNode) evt.getSource()).getID();
            Point newValue = (Point) evt.getNewValue();
            setNodePosition(petrinetnodeID, newValue);
        } else if (evtPropertyName.equals(PetrinetEditorGraphProperties.PLACE_ACTUALTOKENS.toString())) {
            // Die Anazhl der aktuellen Marken einer Stelle hat sich geändert.
            String petrinetnodeID = ((Place) evt.getSource()).getID();
            String newLabelText = ((Place) evt.getSource()).getLabel();
            setNodeLabel(petrinetnodeID, newLabelText);
            updateMarkingSprite(petrinetnodeID, ((Place) evt.getSource()).getActualTokens());
        } else if (evtPropertyName.equals(PetrinetEditorGraphProperties.TRANSITION_ACTIVATION.toString())) {
            // Der Aktivierungszustand einer Transition hat sich geändert.
            String transitionID = ((Transition) evt.getSource()).getID();
            boolean newValue = (boolean) evt.getNewValue();
            setTransitionActivation(transitionID, newValue);
        } else if (evtPropertyName.equals(PetrinetEditorGraphProperties.PLACE_EDITFOCUS.toString())) {
            // Der Editierfokus einer Stelle hat sich geändert.
            String placeID = ((Place) evt.getSource()).getID();
            boolean newValue = (boolean) evt.getNewValue();
            if (newValue) {
                this.addNodeUiClassAttribute(placeID, "focus", true);
            } else {
                this.removeNodeUiClassAttribute(placeID, "focus");
            }
        }
    }

    /**
     * Fügt dem {@link PetrinetViewModel} einen Bogen ({@link Edge}) hinzu.
     *
     * @param arcID    die ID des Bogens als {@link String}.
     * @param sourceID die ID des Ursprungsknotens als {@link String}.
     * @param targetID die ID des Zielknotens als {@link String}.
     * @param arcLabel das Label des Bogens als {@link String}.
     */
    private void addArc(String arcID, String sourceID, String targetID, String arcLabel) {
        graph.addEdge(arcID, sourceID, targetID, true);
        Sprite edgeSprite = spriteMan.addSprite(arcID);
        edgeSprite.attachToEdge(arcID);
        edgeSprite.setPosition(0.5);
        edgeSprite.setAttribute("ui.label", arcLabel);
        edgeSprite.setAttribute("ui.class", "edgeLabel");
    }

    /**
     * Fügt einem {@link Node} ein Klassenattribut hinzu.
     *
     * @param nodeID    die ID des {@link Node} als {@link String}.
     * @param attribute das Attribut als {@link String}.
     * @param onTop     wenn <code>true</code>, wird das Attribut an erster Stelle
     *                  hinzugefügt.
     */
    private void addNodeUiClassAttribute(String nodeID, String attribute, boolean onTop) {
        Node node = graph.getNode(nodeID);
        String attr = (String) node.getAttribute("ui.class");
        ArrayList<String> attributes = new ArrayList<>();
        if (attr != null) {
            attributes.addAll(Arrays.asList(attr.split(", ")));
        }
        if (!attributes.contains(attribute)) {
            if (onTop) {
                attributes.add(0, attribute);
            } else {
                attributes.add(attribute);
            }
            attr = String.join(", ", attributes);
            node.setAttribute("ui.class", attr);
        }
    }

    /**
     * Fügt dem {@link PetrinetViewModel} eine Stelle ({@link Node}) hinzu.
     *
     * @param placeID die ID der Stelle als {@link String}.
     */
    private void addPlace(String placeID) {
        Node node = graph.addNode(placeID);
        node.setAttribute("ui.class", "place");
    }

    /**
     * Fügt dem {@link PetrinetViewModel} eine Transition ({@link Node}) hinzu.
     *
     * @param transitionID die ID der Transition als {@link String}.
     */
    private void addTransition(String transitionID) {
        Node node = graph.addNode(transitionID);
        node.setAttribute("ui.class", "transition");
    }

    /**
     * Entfernt ein Klassenattribut eines {@link Node}.
     *
     * @param nodeID    die ID des {@link Node} als {@link String}.
     * @param attribute das Klassenattribut als {@link String}.
     */
    private void removeNodeUiClassAttribute(String nodeID, String attribute) {
        Node node = graph.getNode(nodeID);
        String attr = (String) node.getAttribute("ui.class");
        ArrayList<String> attributes = new ArrayList<>(Arrays.asList(attr.split(", ")));
        if (attributes.contains(attribute)) {
            attributes.remove(attribute);
            attr = String.join(", ", attributes);
            node.setAttribute("ui.class", attr);
        }
    }

    /**
     * Setzt den Namen eines {@link Node} des {@link PetrinetViewModel} auf den
     * gewünschten Wert.
     *
     * @param nodeID       die ID des {@link Node} als {@link String}.
     * @param newLabeltext das neue Label als {@link String}
     */
    private void setNodeLabel(String nodeID, String newLabeltext) {
        Node node = graph.getNode(nodeID);
        node.setAttribute("ui.label", newLabeltext);
    }

    /**
     * Setzt die Position eines {@link Node} des {@link PetrinetViewModel} auf den
     * gewünschten Wert.
     *
     * @param nodeID      die ID des {@link Node} als {@link String}.
     * @param newPosition die neue Position als {@link Point}.
     */
    private void setNodePosition(String nodeID, Point newPosition) {
        int xPos = (int) newPosition.getX();
        int yPos = (int) newPosition.getY();
        Node node = graph.getNode(nodeID);
        node.setAttribute("xy", xPos, (-1) * yPos);
    }

    /**
     * Ändert die Darstellung, ob eine Transition unter der aktuellen Markierung
     * aktiviert ist.
     *
     * @param transitionID die ID der Transition.
     * @param isActivated  wenn <code>true</code>, wird der {@link Node} als
     *                     aktiviert dargestellt.
     */
    private void setTransitionActivation(String transitionID, boolean isActivated) {
        if (isActivated) {
            this.addNodeUiClassAttribute(transitionID, "activated", true);
        } else {
            this.removeNodeUiClassAttribute(transitionID, "activated");
        }
    }

    /**
     * Aktualisiert das {@link Sprite} einer Stelle.
     * <p>
     * Dieses Sprite zeigt die Anzahl der aktuellen Marken einer Stelle an. Wenn die
     * Stelle keine Marken hat, wird das {@link Sprite} entfernt.
     *
     * @param nodeID die ID eines {@link Node} als String.
     * @param tokens die Anzahl der Makren als {@link Integer}.
     */
    private void updateMarkingSprite(String nodeID, int tokens) {
        Sprite placeSprite = spriteMan.getSprite(nodeID);
        if (placeSprite == null && tokens != 0) {
            placeSprite = spriteMan.addSprite(nodeID);
            placeSprite.attachToNode(nodeID);
            placeSprite.setAttribute("ui.class", "nodeTokens");
        }
        if (tokens == 0 && placeSprite != null) {
            spriteMan.removeSprite(placeSprite.getId());
        } else if (tokens > 9) {
            placeSprite.setAttribute("ui.label", ">9");
        } else {
            placeSprite.setAttribute("ui.label", tokens);
        }
    }
}
