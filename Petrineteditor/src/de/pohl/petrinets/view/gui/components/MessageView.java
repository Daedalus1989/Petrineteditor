package de.pohl.petrinets.view.gui.components;

/**
 * Ein Interface, dass Methoden definiert, die eine {@link MessageView} zur
 * Ausgabe von Nachrichten bereitstellen muss.
 */
public interface MessageView {
    /**
     * Erzeugt eine Ausgabe in dem Nachrichtenbereich.
     *
     * @param message   die Nachricht, die ausgegeben werden soll als
     *                  {@link String}.
     * @param clearArea wenn <code>true</code>, wird der Nachrichtenbereich vor der
     *                  Nachrichtenausgabe gelöscht.
     */
    void printInMessageView(String message, boolean clearArea);
}