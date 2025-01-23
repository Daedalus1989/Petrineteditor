package de.pohl.petrinets.view.gui.components;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 * Eine Klasse für ein Popup-Menü für eine {@link MessagePane}.
 */
public class MessageAreaPopupMenu extends JPopupMenu {
    private MessagePane messageArea;

    /**
     * Erstellt ein {@link MessageAreaPopupMenu} für einen Nachrichtenbereich.
     *
     * @param messageArea die {@link MessagePane}.
     */
    public MessageAreaPopupMenu(MessagePane messageArea) {
        this.messageArea = messageArea;
        this.add(newCopyMenuItem());
        this.add(newSelectAllMenuItem());
        this.add(newClearMenuItem());
    }

    /**
     * Diese überschriebene Methode verhindert, dass das Popupmenü überhalb des
     * unteren oder rechten Randes der Komponente, in der das Popupmenü erscheint,
     * hinaus gezeichnet wird. Dieses Verhalten würde ansonsten zu einem Fehler bei
     * der Darstellung des Programms führen.
     * <p>
     *
     * Ursprünglicher Text:<br>
     * Displays the popup menu at the position x,y in the coordinatespace of the
     * component invoker.
     *
     */
    @Override
    public void show(Component invoker, int x, int y) {
        // this.getWidth und this.getHeight liefern 0, wenn die Komponente noch nie
        // angezeigt wurde.
        // this.getPreferredSize umgeht dies und liefert auch dann bereits Werte für
        // width und height, wenn die Komponente noch nie angezeigt worden ist.
        Dimension prefSize = this.getPreferredSize();
        // Verhindert das Zeichnen über den unteren Rand hinaus.
        if ((y + prefSize.height) > invoker.getHeight()) {
            y = y - prefSize.height;
        }
        // Verhindert das Zeichnen über den rechten Rand hinaus.
        if ((x + prefSize.width) > invoker.getWidth()) {
            x = x - prefSize.width;
        }
        super.show(invoker, x, y);
    }

    /**
     * Erstellt ein {@link JMenuItem} zum Löschen des Textbereichinhaltes.
     *
     * @return Das {@link JMenuItem} zum Löschen des Textbereichinhaltes.
     */
    private JMenuItem newClearMenuItem() {
        JMenuItem menuItem = new JMenuItem("Alles Löschen");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                messageArea.clearText();
            }
        });
        return menuItem;
    }

    /**
     * Erstellt ein {@link JMenuItem} zum Kopieren des gesamten Textbereichinhaltes.
     *
     * @return Das {@link JMenuItem} zum Kopieren des gesamten Textbereichinhaltes.
     */
    private JMenuItem newCopyMenuItem() {
        JMenuItem menuItem = new JMenuItem("Kopieren");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedText = messageArea.getSelectedText();
                if (selectedText != null) {
                    StringSelection stringSelection = new StringSelection(selectedText);
                    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
                }
            }
        });
        return menuItem;
    }

    /**
     * Erstellt ein {@link JMenuItem} zum Auswählen des gesamten
     * Textbereichinhaltes.
     *
     * @return Das {@link JMenuItem} zum Auswählen des gesamten Textbereichinhaltes.
     */
    private JMenuItem newSelectAllMenuItem() {
        JMenuItem menuItem = new JMenuItem("Alles Auswählen");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                messageArea.selectAll();
            }
        });
        return menuItem;
    }
}
