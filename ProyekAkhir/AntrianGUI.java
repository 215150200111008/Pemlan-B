package tugas.ProyekAkhir;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class AntrianGUI extends JFrame {
    private JTextField platField;
    private JComboBox<String> jenisBox;
    private JComboBox<TipeCuci> tipeBox;

    public AntrianGUI() {
        setTitle("Sistem Antrian Cuci Kendaraan");
        setSize(450, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(230, 240, 255));

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(230, 240, 255));

        panel.add(new JLabel("Plat Nomor:"));
        platField = new JTextField();
        panel.add(platField);

        panel.add(new JLabel("Jenis Kendaraan:"));
        jenisBox = new JComboBox<>(new String[]{"motor", "mobil"});
        panel.add(jenisBox);

        panel.add(new JLabel("Tipe Cuci:"));
        tipeBox = new JComboBox<>(TipeCuci.values());
        panel.add(tipeBox);

        JButton cetakBtn = new JButton("Cetak Tiket");
        cetakBtn.setBackground(new Color(100, 149, 237));
        cetakBtn.setForeground(Color.white);
        cetakBtn.setFocusPainted(false);
        panel.add(cetakBtn);

        JButton riwayatBtn = new JButton("Lihat Riwayat Cuci");
        riwayatBtn.setBackground(new Color(60, 179, 113));
        riwayatBtn.setForeground(Color.white);
        riwayatBtn.setFocusPainted(false);
        panel.add(riwayatBtn);

        JButton rekapBtn = new JButton("Rekap Hari Ini");
        rekapBtn.setBackground(new Color(34, 139, 34));
        rekapBtn.setForeground(Color.white);

        JButton clearBtn = new JButton("Clear Riwayat");
        clearBtn.setBackground(new Color(220, 20, 60));
        clearBtn.setForeground(Color.white);

        panel.add(rekapBtn);
        panel.add(clearBtn);


        add(panel);

        cetakBtn.addActionListener(e -> cetakTiket());
        riwayatBtn.addActionListener(e -> tampilkanRiwayat());
        rekapBtn.addActionListener(e -> tampilkanRekapHariIni());
        clearBtn.addActionListener(e -> hapusRiwayatCuci());


    }

private void cetakTiket() {
    String plat = platField.getText().trim();
    String jenis = (String) jenisBox.getSelectedItem();
    TipeCuci tipe = (TipeCuci) tipeBox.getSelectedItem();

    if (plat.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Plat nomor tidak boleh kosong!");
        return;
    }

    String kode = AntrianManager.getKodeAntrian(jenis, tipe);
    Kendaraan k = new Kendaraan(plat, jenis, tipe, kode);

    // Tampilkan struk dengan 3 baris pertama rata tengah
    JTextPane pane = new JTextPane();
    pane.setEditable(false);
    pane.setFont(new Font("Monospaced", Font.PLAIN, 14));

    StyledDocument doc = pane.getStyledDocument();

    // Buat dua gaya: tengah dan kiri
    SimpleAttributeSet center = new SimpleAttributeSet();
    StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);

    SimpleAttributeSet left = new SimpleAttributeSet();
    StyleConstants.setAlignment(left, StyleConstants.ALIGN_LEFT);

try {
    // Tambahkan 3 baris pertama (rata tengah)
    String[] headerLines = {
        "Cuci Motor & Mobil",
        "Setia Hati",
        "Jl. Margo Basuki, 08 Klojen Malang"
    };

    for (String line : headerLines) {
        int start = doc.getLength();
        doc.insertString(start, line + "\n", null);
        doc.setParagraphAttributes(start, line.length(), center, false);
    }

    // Sisanya (rata kiri)
    String[] lines = k.getStruk().split("\n");
    for (int i = 3; i < lines.length; i++) {
        int start = doc.getLength();
        doc.insertString(start, lines[i] + "\n", null);
        doc.setParagraphAttributes(start, lines[i].length(), left, false);
    }

} catch (BadLocationException ex) {
    ex.printStackTrace();
}


    JScrollPane scroll = new JScrollPane(pane);
    scroll.setPreferredSize(new Dimension(350, 300));
    JOptionPane.showMessageDialog(this, scroll, "Tiket Antrian", JOptionPane.INFORMATION_MESSAGE);

    // Simpan ke file
    try (PrintWriter pw = new PrintWriter(new FileWriter("riwayatcuci.txt", true))) {
        pw.println(k.getRiwayatFormat());
    } catch (IOException ex) {
        JOptionPane.showMessageDialog(this, "Gagal menyimpan riwayat!", "Error", JOptionPane.ERROR_MESSAGE);
    }

    platField.setText("");
}
private void tampilkanRiwayat() {
    File file = new File("riwayatcuci.txt");
    if (!file.exists()) {
        JOptionPane.showMessageDialog(this, "Belum ada riwayat cuci tersimpan.");
        return;
    }

    StringBuilder sb = new StringBuilder();
    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line).append("\n");
        }
    } catch (IOException ex) {
        JOptionPane.showMessageDialog(this, "Gagal membaca riwayat!", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    JTextArea area = new JTextArea(sb.toString());
    area.setEditable(false);
    area.setFont(new Font("Monospaced", Font.PLAIN, 12));
    JScrollPane scroll = new JScrollPane(area);
    scroll.setPreferredSize(new Dimension(400, 300));

    JOptionPane.showMessageDialog(this, scroll, "Riwayat Cuci", JOptionPane.INFORMATION_MESSAGE);
}

private void tampilkanRekapHariIni() {
    int total = 0;
    String hariIni = java.time.LocalDate.now().format(java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy"));

    try (BufferedReader reader = new BufferedReader(new FileReader("riwayatcuci.txt"))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] data = line.split(";");
            if (data.length >= 6 && data[3].equals(hariIni)) {
                total += Integer.parseInt(data[5]);
            }
        }
        JOptionPane.showMessageDialog(this,
                "Total Penghasilan Hari Ini (" + hariIni + "): Rp " + total + ",00",
                "Rekap Penghasilan", JOptionPane.INFORMATION_MESSAGE);
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Gagal membaca riwayat!", "Error", JOptionPane.ERROR_MESSAGE);
    }
}

private void hapusRiwayatCuci() {
    int konfirmasi = JOptionPane.showConfirmDialog(this, "Yakin ingin menghapus semua riwayat?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
    if (konfirmasi == JOptionPane.YES_OPTION) {
        try (PrintWriter pw = new PrintWriter("riwayatcuci.txt")) {
            // Kosongkan isi file
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        JOptionPane.showMessageDialog(this, "Riwayat berhasil dihapus.");
    } else {
        JOptionPane.showMessageDialog(this, "Penghapusan dibatalkan.");
    }
}



}
