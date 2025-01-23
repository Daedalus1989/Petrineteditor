package de.pohl.petrinets.view.gui.components;

import java.awt.Font;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultCaret;

/**
 * Eine Klasse für den Nachrichtenbereich des Petrinetzeditors
 */
public class MessagePane extends JScrollPane {
    private JTextArea textArea;

    /**
     * Erstellt eine {@link MessagePane}.
     */
    public MessagePane() {
        this.textArea = new JTextArea();
        this.setViewportView(textArea);
        this.textArea.setEditable(false);
        this.textArea.setLineWrap(true);
        this.textArea.setWrapStyleWord(true);
        this.textArea.setComponentPopupMenu(new MessageAreaPopupMenu(this));
        this.textArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        this.textArea.setBorder(new EmptyBorder(10, 10, 10, 10));
        DefaultCaret test = (DefaultCaret) this.textArea.getCaret();
        test.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
    }

    /**
     * Fügt einen Text an das Ende der {@link MessagePane} in einer neuen Zeile ein.
     *
     * @param str der Text, der eingefügt werden soll als {@link String}.
     *
     */
    public void append(String str) {
        if (this.textArea.getText().isEmpty()) {
            this.textArea.append(str);
        } else {
            this.textArea.append("\n" + str);
        }
    }

    /**
     * Löscht den gesamten Text.
     */
    public void clearText() {
        this.textArea.setText("");
    }

    /**
     * Liefert den aktuellen Text der {@link MessagePane} zurück.
     *
     * @return Der aktuelle Text der {@link MessagePane} als {@link String}.
     * @see javax.swing.text.JTextComponent#getSelectedText()
     */
    public String getSelectedText() {
        return this.textArea.getSelectedText();
    }

    /**
     * Markiert den gesamten Inhalt der {@link MessagePane}.
     *
     * @see javax.swing.text.JTextComponent#selectAll()
     */
    public void selectAll() {
        this.textArea.selectAll();
    }
}
