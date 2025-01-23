package de.pohl.petrinets.control;

import org.graphstream.ui.view.ViewerListener;

/**
 * Eine Interfaceklasse mit Defaultimplementantionen der Methoden in
 * {@link ViewerListener}.
 * <p>
 * So befinden sich in der implementierenden Klasse nur die Methoden, die diese
 * auch implementieren soll.
 */
public interface ViewerAdapter extends ViewerListener {
    /**
     * Wird aufgerufen wenn ein Knoten in einem Graphen angeklickt wird.
     */
    @Override
    default void buttonPushed(String id) {
    }

    @Override
    default void buttonReleased(String id) {
        // Wird nicht verwendet.
    }

    @Override
    default void mouseLeft(String id) {
        // Wird nicht verwendet.
    }

    @Override
    default void mouseOver(String id) {
        // Wird nicht verwendet.
    }

    @Override
    default void viewClosed(String viewName) {
        // Wird nicht verwendet.
    }
}
