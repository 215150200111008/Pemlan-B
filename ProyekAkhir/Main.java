package tugas.ProyekAkhir;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AntrianGUI().setVisible(true));
    }
}