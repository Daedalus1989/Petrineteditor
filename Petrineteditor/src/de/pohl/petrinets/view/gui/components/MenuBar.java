package de.pohl.petrinets.view.gui.components;

import java.awt.event.KeyEvent;

import javax.swing.*;

import de.pohl.petrinets.control.PetrinetEditorViewListener;
import de.pohl.petrinets.control.implementations.actions.menu.*;

/**
 * Eine Klasse für die Menüleiste des Petrinetzeditors.
 */
public class MenuBar extends JMenuBar {
    private PetrinetEditorViewListener listener;
    private JMenu helpMenu;
    private JMenu fileMenu;
    private JMenuItem fileAnalyseMultiple;
    private JMenuItem closeTab;
    private JMenuItem fileExit;
    private JMenuItem helpInfo;
    private JMenuItem fileOpen;
    private JMenuItem fileReload;

    /**
     * Erstellt eine {@link MenuBar}.
     *
     * @param listener ein PetrinetEditorViewListener, der auf die Aktionen der
     *                 {@link MenuBar} reagiert.
     */
    public MenuBar(PetrinetEditorViewListener listener) {
        this.listener = listener;
        this.addMenus();
    }

    /**
     * Aktiviert oder deaktiviert den Menüeitnrag zum Schließen des Tabs der TDI.
     *
     * @param state wenn <code>true</code>, wird der Eintrag aktiviert.<br>
     *              wenn <code>false</code>, wird der Eintrag deaktiviert.
     */
    public void setCloseTabActionState(boolean state) {
        closeTab.setEnabled(state);
    }

    /**
     * Aktiviert oder deaktiviert den Menüeitnrag zum Neuladen einer PNML-Datei.
     *
     * @param state wenn <code>true</code>, wird der Eintrag aktiviert.<br>
     *              wenn <code>false</code>, wird der Eintrag deaktiviert.
     */
    public void setReloadActivationState(boolean state) {
        fileReload.setEnabled(state);
    }

    /**
     * Fügt der {@link MenuBar} die {@link JMenu} hinzu.
     */
    private void addMenus() {
        this.add(createFileMenu());
        this.add(createHelpMenu());
    }

    /**
     * Erstellt das Dateimenü.
     *
     * @return Ein {@link JMenu} als Dateimenü.
     */
    private JMenu createFileMenu() {
        // create File menu
        fileMenu = new JMenu("Datei");
        fileMenu.setMnemonic(KeyEvent.VK_D);
        fileMenu.add(newOpenMenuItem());
        fileMenu.add(newReloadMenuItem());
        fileMenu.addSeparator();
        fileMenu.add(newAnalyseMultipleMenuItem());
        fileMenu.addSeparator();
        fileMenu.add(newCloseTabMenuItem());
        fileMenu.addSeparator();
        fileMenu.add(newExitMenuItem());
        return fileMenu;
    }

    /**
     * Erstellt das Hilfemenü.
     *
     * @return Ein {@link JMenu} als Hilfemenü.
     */
    private JMenu createHelpMenu() {
        helpMenu = new JMenu("Hilfe");
        helpMenu.setMnemonic(KeyEvent.VK_H);
        helpMenu.add(newInfoMenuItem());
        return helpMenu;
    }

    /**
     * Erstellt das Menü-Item zum Starten der Analyse mehrerer PNML-Dateien.
     *
     * @return Ein {@link JMenuItem} zur Analyse mehrerer PNML-Dateien.
     */
    private JMenuItem newAnalyseMultipleMenuItem() {
        fileAnalyseMultiple = new JMenuItem(new AnalyseMultiplePNMLFilesAction(listener));
        return fileAnalyseMultiple;
    }

    /**
     * Erstellt das Menü-Item zum Schließen eines Tabs der TDI.
     *
     * @return Ein {@link JMenuItem} zum Schließen eines Tabs der TDI.
     */
    private JMenuItem newCloseTabMenuItem() {
        closeTab = new JMenuItem(new CloseTabAction(listener));
        return closeTab;
    }

    /**
     * Erstellt das Menü-Item zum Beenden des Programms.
     *
     * @return Ein {@link JMenuItem} zum Beenden des Programms.
     */
    private JMenuItem newExitMenuItem() {
        fileExit = new JMenuItem(new ExitAction(listener));
        return fileExit;
    }

    /**
     * Erstellt das Menü-Item zum Öffnen eines Infodialoges.
     *
     * @return Ein {@link JMenuItem} zum Öffnen eines Infodialoges.
     */
    private JMenuItem newInfoMenuItem() {
        helpInfo = new JMenuItem(new InfoAction(listener));
        return helpInfo;
    }

    /**
     * Erstellt das Menü-Item zum Öffnen einer PNML-Datei.
     *
     * @return Ein {@link JMenuItem} zum Öffnen einer PNML-Datei.
     */
    private JMenuItem newOpenMenuItem() {
        fileOpen = new JMenuItem(new OpenPNMLFileAction(listener));
        return fileOpen;
    }

    /**
     * Erstellt das Menü-Item zum Neuladen der aktuellen PNML-Datei.
     *
     * @return Ein {@link JMenuItem} zum Neuladen der aktuellen PNML-Datei.
     */
    private JMenuItem newReloadMenuItem() {
        fileReload = new JMenuItem(new ReloadPNMLFileAction(listener));
        return fileReload;
    }
}
