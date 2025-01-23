package de.pohl.petrinets.view.gui.components;

import java.awt.BorderLayout;
import java.awt.event.*;

import javax.swing.JPanel;

import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.ui.swing_viewer.SwingViewer;
import org.graphstream.ui.swing_viewer.ViewPanel;
import org.graphstream.ui.view.*;

import de.pohl.petrinets.model.reachabilitygraph.AbstractReachabilitygraph;
import de.pohl.petrinets.view.model.RGraphViewModel;

/**
 * View-Klasse für einen Erreichbarkeitsgraphen.
 * <p>
 * Sie erhält bei der Initialisierung vom {@link RGraphViewModel} die eine
 * {@link MultiGraph}-Instanz, mit der ein {@link SwingViewer} initialisiert
 * wird.<br>
 * Die Kommunikation zwischen der View und dem Viewmodel erfolgt nach der
 * Initialiserung ausschließlich über diese Datenbindung.
 */
public class RGraphPanel extends JPanel {
    private ViewerPipe viewerPipe;
    // In diesem Panel wird der Graph mittels GraphStream angezeigt.
    private ViewPanel viewPanel;

    /**
     * Erstellt eine neues {@link RGraphPanel}.
     *
     * @param reachabilitygraphVM ein {@link RGraphViewModel} des
     *                            {@link AbstractReachabilitygraph}.
     * @param listener            ein {@link ViewerListener}.
     */
    public RGraphPanel(RGraphViewModel reachabilitygraphVM, ViewerListener listener) {
        super(new BorderLayout());
        this.initPanelGraph(reachabilitygraphVM.getGraph(), listener);
        this.add(viewPanel, BorderLayout.CENTER);
    }

    /**
     * Erzeuge und initialisiere ein Panel zur Anzeige des Graphen
     *
     * @param graph    einen {@link MultiGraph}, an den die View gebunden werden
     *                 soll.
     * @param listener ein {@link ViewerListener}, der auf Aktionen reagiert.
     */
    private void initPanelGraph(MultiGraph graph, ViewerListener listener) {
        // Erzeuge Viewer mit passendem Threading-Model für Zusammenspiel mit
        // Swing
        SwingViewer viewer = new SwingViewer(graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        // bessere Darstellungsqualität und Antialiasing (Kantenglättung) aktivieren
        graph.setAttribute("ui.quality");
        graph.setAttribute("ui.antialias");
        // Das Auto-Layout für den Erreichbarkeitsgraphen aktivieren, da keine
        // Koordinaten vorhanden sind.
        viewer.enableAutoLayout();
        // Eine DefaultView zum Viewer hinzufügen
        viewPanel = (ViewPanel) viewer.addDefaultView(false);
        // ViewerPipe, für Ereignisse über den Viewer
        viewerPipe = viewer.newViewerPipe();
        viewerPipe.addViewerListener(listener);
        // Neuen MouseListener beim viewPanel anmelden. Wenn im viewPanel ein
        // Maus-Button gedrückt oder losgelassen wird, dann wird die Methode
        // viewerPipe.pump() aufgerufen, um alle bei der viewerPipe angemeldeten
        // ViewerListener zu informieren (hier also konkret unseren
        // clickListener).
        viewPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                System.out.println("DemoFrame - mousePressed: " + me);
                viewerPipe.pump();
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                System.out.println("DemoFrame - mouseReleased: " + me);
                viewerPipe.pump();
            }
        });
        // Zoom per Mausrad ermöglichen
        viewPanel.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                double zoomLevel = viewPanel.getCamera().getViewPercent();
                if (e.getWheelRotation() == -1) {
                    zoomLevel -= 0.1;
                    if (zoomLevel < 0.1) {
                        zoomLevel = 0.1;
                    }
                }
                if (e.getWheelRotation() == 1) {
                    zoomLevel += 0.1;
                }
                viewPanel.getCamera().setViewPercent(zoomLevel);
            }
        });
    }
}
