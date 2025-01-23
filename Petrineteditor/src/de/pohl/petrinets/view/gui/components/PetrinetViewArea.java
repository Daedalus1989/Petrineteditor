package de.pohl.petrinets.view.gui.components;

import javax.swing.JSplitPane;

import de.pohl.petrinets.view.PetrinetView;

/**
 * Eine View-Klasse für den Bereich eine Petrinetzinstanz.<br>
 * Diese View-Klasse stellt die Komponenten für die Darstellung bzw. Einbettung
 * eines {@link PetrinetPanel} und eines {@link RGraphPanel} zur Verfügung.<br>
 * Außerdem stellt die {@link PetrinetViewArea} einen Nachrichtenbereich für die
 * Ausgabe relevanter Informationen bereit.
 */
public class PetrinetViewArea extends JSplitPane implements PetrinetView {
    private JSplitPane graphspace;
    private MessagePane messagePane;

    /**
     * Erstellt eine neue {@link PetrinetViewArea}.
     */
    public PetrinetViewArea() {
        super(JSplitPane.VERTICAL_SPLIT);
        graphspace = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, null, null);
        graphspace.setResizeWeight(0.5);
        messagePane = new MessagePane();
        this.setResizeWeight(0.9);
        this.setTopComponent(graphspace);
        this.setBottomComponent(messagePane);
    }

    @Override
    public void printInMessageView(String message, boolean clearArea) {
        if (clearArea) {
            messagePane.clearText();
        }
        messagePane.append(message);
    }

    @Override
    public void setPetrinetView(PetrinetPanel petrinetPanel) {
        graphspace.setLeftComponent(petrinetPanel);
    }

    @Override
    public void setRGraphView(RGraphPanel rGraphPanel) {
        graphspace.setRightComponent(rGraphPanel);
    }
}
