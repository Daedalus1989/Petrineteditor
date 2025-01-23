package de.pohl.petrinets.view.gui.dialog;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

/**
 * Eine Klasse für ein Dialogfenster zum Öffnen von PNML-Dateien.
 */
public class PNMLFileChooser extends JFileChooser {
    private static final String DESCRIPTION = "PNML-Datei (.pnml)";
    private static final String EXTENSION = ".pnml";

    /**
     * Erstellt ein {@link PNMLFileChooser}.
     *
     * @param file                eine PNML-Datei, wenn der {@link PNMLFileChooser}
     *                            in dem Verzeichnis der Datei geöffnet werden soll.
     *                            Wenn <code>null</code> wird das Fenster im
     *                            Verzeichnis {@code ../ProPra-WS23-Basis/Beispiele}
     *                            geöffnet.
     * @param allowMultiselection wenn <code>true</code> ist die Auswahl mehrerer
     *                            Dateien möglich.
     */
    public PNMLFileChooser(File file, boolean allowMultiselection) {
        if (file == null) {
            file = new File("../ProPra-WS23-Basis/Beispiele");
        }
        this.setCurrentDirectory(file);
        this.setAcceptAllFileFilterUsed(false);
        this.setMultiSelectionEnabled(allowMultiselection);
        this.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                String name = f.getName();
                if (f.isDirectory() || name.endsWith(EXTENSION)) {
                    return true;
                }
                return false;
            }

            @Override
            public String getDescription() {
                return DESCRIPTION;
            }
        });
    }
}
