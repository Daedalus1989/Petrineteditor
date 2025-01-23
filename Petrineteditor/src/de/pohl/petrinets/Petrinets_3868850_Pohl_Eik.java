package de.pohl.petrinets;

import java.lang.reflect.Array;

import javax.swing.SwingUtilities;

import de.pohl.petrinets.presenter.PetrinetEditorPresenter;

/**
 * Programmeinstiegspunkt.
 */
public class Petrinets_3868850_Pohl_Eik {
    /**
     * Methode zum Starten des Programms.
     *
     * @param args {@link String}-{@link Array} mit den Parametern, die beim Aufruf
     *             des Programms mitgegeben wurden.
     */
    public static void main(String[] args) {
        String autor = "Eik Pohl";
        String matrNr = "3868850";
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PetrinetEditorPresenter(autor, matrNr);
            }
        });
    }
}
