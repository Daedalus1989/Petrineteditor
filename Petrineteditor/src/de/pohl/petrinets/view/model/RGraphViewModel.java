package de.pohl.petrinets.view.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Arrays;

import org.graphstream.graph.*;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.ui.spriteManager.Sprite;
import org.graphstream.ui.spriteManager.SpriteManager;

import de.pohl.petrinets.control.PetrinetEditorGraphProperties;
import de.pohl.petrinets.model.petrinet.Petrinet;
import de.pohl.petrinets.model.reachabilitygraph.*;

/**
 * Viewmodelklasse zur Darstellung eines {@link AbstractReachabilitygraph}.
 * <p>
 * Kennt ein {@link MultiGraph} und verwaltet die Informationen auf diesen.
 * <p>
 * Implementiert {@link PropertyChangeListener} und reagiert so auf
 * Eigenschaftsänderungen eines {@link AbstractReachabilitygraph}.
 */
public class RGraphViewModel implements PropertyChangeListener {
    // Layout des Graphen
    private static String CSS_FILE = "url(" + RGraphViewModel.class.getResource("/Reachabilitygraph.css") + ")";
    // Anzeigedatenmodell der View
    private MultiGraph graph;
    // Der SpriteManager des Graphen
    private SpriteManager spriteMan;

    /**
     * Erstellt ein neues {@link RGraphViewModel}.
     *
     * @param pnmlFileName der Dateiname des {@link Petrinet} als {@link String}.
     */
    public RGraphViewModel(String pnmlFileName) {
        this.graph = new MultiGraph(pnmlFileName);
        this.graph.setAttribute("ui.stylesheet", CSS_FILE);
        this.spriteMan = new SpriteManager(graph);
    }

    /**
     * Liefert den {@link MultiGraph} des {@link RGraphViewModel} zurück.<br>
     * Es wird kein neuer, eigenständiger {@link MultiGraph} übergeben, sondern die
     * in der {@link RGraphViewModel}-Instanz initiierte {@link MultiGraph}-Instanz,
     * da über diese Kopplung der Datenaustausch mit der View erfolgen soll.
     *
     * @return Der {@link MultiGraph} des {@link RGraphViewModel} zurück.
     */
    public MultiGraph getGraph() {
        return graph;
    }

    /**
     * Diese Methode wird aufgerufen, wenn sich eine Eigenschaft eines
     * {@link AbstractReachabilitygraph} geändert hat.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String evtPropertyName = evt.getPropertyName();
        if (evtPropertyName.equals(PetrinetEditorGraphProperties.EDGE.toString())) {
            if (evt.getNewValue() != null) {
                // Neue Kante wurde hinzugefügt.
                RGraphEdge edge = (RGraphEdge) evt.getNewValue();
                String edgeID = edge.getID();
                String edgeLabel = edge.getLabel();
                String sourceID = edge.getSourceNodeID();
                String targetID = edge.getTargetNodeID();
                addEdge(edgeID, sourceID, targetID, edgeLabel);
            } else {
                // Kante wurde entfernt.
                RGraphEdge edge = (RGraphEdge) evt.getOldValue();
                String edgeID = edge.getID();
                removeEdge(edgeID);
            }
        } else if (evtPropertyName.equals(PetrinetEditorGraphProperties.NODE.toString())) {
            if (evt.getNewValue() != null) {
                // Neuer Knoten wurde hinzugefügt.
                RGraphNode node = (RGraphNode) evt.getNewValue();
                String nodeID = node.getID();
                String nodeLabel = node.getLabel();
                addNode(nodeID, nodeLabel);
            } else {
                // Knoten wurden entfernt.
                RGraphNode node = (RGraphNode) evt.getOldValue();
                String nodeID = node.getID();
                removeNode(nodeID);
            }
        } else if (evtPropertyName.equals(PetrinetEditorGraphProperties.NODE_ELEMENT_OF_LAST_TRANSITION.toString())) {
            // Knoten ist oder war Element der letzten Transitionsschaltung.
            boolean isElementOf = (boolean) evt.getNewValue();
            String nodeID = ((RGraphNode) evt.getSource()).getID();
            if (isElementOf) {
                addElementUiClassAttribute(nodeID, "highlight", true);
            } else {
                removeElementUiClassAttribute(nodeID, "highlight");
            }
        } else if (evtPropertyName.equals(PetrinetEditorGraphProperties.EDGE_ELEMENT_OF_LAST_TRANSITION.toString())) {
            // Kante ist oder war Element der letzten Transitionsschaltung.
            boolean isElementOf = (boolean) evt.getNewValue();
            String edgeID = ((RGraphEdge) evt.getSource()).getID();
            if (isElementOf) {
                addElementUiClassAttribute(edgeID, "highlight", true);
            } else {
                removeElementUiClassAttribute(edgeID, "highlight");
            }
        } else if (evtPropertyName.equals(PetrinetEditorGraphProperties.NODE_ELEMENT_OF_UNBOUNDEDCAUSE.toString())) {
            // Knoten ist oder war Element eines Unbeschränktheitspfades.
            boolean isElementOf = (boolean) evt.getNewValue();
            String nodeID = ((RGraphNode) evt.getSource()).getID();
            if (isElementOf) {
                addElementUiClassAttribute(nodeID, "unboundedCause", true);
            } else {
                removeElementUiClassAttribute(nodeID, "unboundedCause");
            }
        } else if (evtPropertyName.equals(PetrinetEditorGraphProperties.EDGE_ELEMENT_OF_UNBOUNDEDCAUSE.toString())) {
            // Kante ist oder war Element eines Unbeschränktheitspfades.
            boolean isElementOf = (boolean) evt.getNewValue();
            String edgeID = ((RGraphEdge) evt.getSource()).getID();
            if (isElementOf) {
                addElementUiClassAttribute(edgeID, "unboundedCause", true);
            } else {
                removeElementUiClassAttribute(edgeID, "unboundedCause");
            }
        } else if (evtPropertyName
                .equals(PetrinetEditorGraphProperties.NODE_SOURCEMARKING_OF_UNBOUNDEDCAUSE.toString())) {
            // Knoten ist oder war m.
            boolean isSourcemarking = (boolean) evt.getNewValue();
            String nodeID = ((RGraphNode) evt.getSource()).getID();
            if (isSourcemarking) {
                addElementUiClassAttribute(nodeID, "unboundedCauseSourcemarking", true);
            } else {
                removeElementUiClassAttribute(nodeID, "unboundedCauseSourcemarking");
            }
        } else if (evtPropertyName
                .equals(PetrinetEditorGraphProperties.NODE_TARGETMARKIGN_OF_UNBOUNDEDCAUSE.toString())) {
            // Knoten ist oder war m'.
            boolean isTargetmarking = (boolean) evt.getNewValue();
            String nodeID = ((RGraphNode) evt.getSource()).getID();
            if (isTargetmarking) {
                addElementUiClassAttribute(nodeID, "unboundedCauseTargetmarking", true);
            } else {
                removeElementUiClassAttribute(nodeID, "unboundedCauseTargetmarking");
            }
        } else if (evtPropertyName.equals(PetrinetEditorGraphProperties.NODE_INITIALMARKING.toString())) {
            // Der Knoten ist oder war der initiale Knoten (Wurzelknoten) des
            // Erreichbarkeitsgraphen.
            boolean isInitialnode = (boolean) evt.getNewValue();
            String nodeID = ((RGraphNode) evt.getSource()).getID();
            if (isInitialnode) {
                addElementUiClassAttribute(nodeID, "initial", true);
            } else {
                removeElementUiClassAttribute(nodeID, "initial");
            }
        } else if (evtPropertyName.equals(PetrinetEditorGraphProperties.NODE_ACTIVE_TRANSITION_IDS.toString())) {
            // Meldung kann ignoriert werden. Die Instanz des Petrinetzkontens übergibt zur
            // Laufzeit eine ArrayList<String> mit den IDs der auf der Markierung
            // verbliebenen Transitionen.
            @SuppressWarnings("unchecked")
            ArrayList<String> newList = (ArrayList<String>) evt.getNewValue();
            String nodeID = ((RGraphNode) evt.getSource()).getID();
            if (newList.size() != 0) {
                showRemainingTransitionsSprite(nodeID);
            } else {
                removeRemainingTransitionsSprite(nodeID);
            }
        }
    }

    /**
     * Fügt dem {@link RGraphViewModel} eine {@link Edge} hinzu.
     *
     * @param id       die ID der {@link Edge} als {@link String}.
     * @param sourceID die ID des Ursprungsknotens als {@link String}.
     * @param targetID die ID des Zielknotens als {@link String}.
     * @param label    das Label der {@link Edge} als {@link String}
     */
    private void addEdge(String id, String sourceID, String targetID, String label) {
        graph.addEdge(id, sourceID, targetID, true);
        Sprite edgeSprite = spriteMan.addSprite(id);
        edgeSprite.attachToEdge(id);
        edgeSprite.setPosition(0.5);
        edgeSprite.setAttribute("ui.label", label);
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
    private void addElementUiClassAttribute(String elementID, String attribute, boolean onTop) {
        Element element = graph.getNode(elementID);
        if (element == null) {
            element = graph.getEdge(elementID);
        }
        String attr = (String) element.getAttribute("ui.class");
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
            element.setAttribute("ui.class", attr);
        }
    }

    /**
     * Fügt dem {@link RGraphViewModel} einen {@link Node} hinzu.
     *
     * @param nodeID    die ID des {@link Node} als {@link String}.
     * @param nodeLabel die Beschriftung des {@link Node} als {@link String}.
     */
    private void addNode(String nodeID, String nodeLabel) {
        Node node = graph.addNode(nodeID);
        node.setAttribute("ui.label", nodeLabel);
        if (graph.nodes().count() == 0) {
            graph.getReplayController().replay();
        }
    }

    private void removeEdge(String id) {
        spriteMan.removeSprite(id);
        graph.removeEdge(id);
    }

    /**
     * Entfernt ein Klassenattribut eines {@link Element}.
     *
     * @param nodeID    die ID des {@link Element} als {@link String}.
     * @param attribute das Klassenattribut als {@link String}.
     */
    private void removeElementUiClassAttribute(String elementID, String attribute) {
        Element element = graph.getNode(elementID);
        if (element == null) {
            element = graph.getEdge(elementID);
        }
        String attr = (String) element.getAttribute("ui.class");
        ArrayList<String> attributes = new ArrayList<>(Arrays.asList(attr.split(", ")));
        if (attributes.contains(attribute)) {
            attributes.remove(attribute);
            attr = String.join(", ", attributes);
            element.setAttribute("ui.class", attr);
        }
    }

    private void removeNode(String nodeID) {
        spriteMan.removeSprite(nodeID);
        graph.removeNode(nodeID);
    }

    /**
     * Entfernt ein {@link Sprite}, was einen Knoten mit einer Markierung
     * kennzeichnet, auf der bisher noch nicht verwendete aktive Transition
     * verfügbar sind.
     *
     * @param nodeID die ID des {@link Node} als {@link String}.
     */
    private void removeRemainingTransitionsSprite(String nodeID) {
        Sprite nodeSprite = spriteMan.getSprite(nodeID);
        if (nodeSprite != null) {
            spriteMan.removeSprite(nodeSprite.getId());
        }
    }

    /**
     * Zeigt ein {@link Sprite} an, was einen Knoten mit einer Markierung
     * kennzeichnet, auf der bisher noch nicht verwendete aktive Transition
     * verfügbar sind.
     *
     * @param nodeID die ID des {@link Node} als {@link String}.
     */
    private void showRemainingTransitionsSprite(String nodeID) {
        Sprite nodeSprite = spriteMan.getSprite(nodeID);
        if (nodeSprite == null) {
            nodeSprite = spriteMan.addSprite(nodeID);
            nodeSprite.attachToNode(nodeID);
            nodeSprite.setAttribute("ui.class", "remainingTransitions");
        }
    }
}
