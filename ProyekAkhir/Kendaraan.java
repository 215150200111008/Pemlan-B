package tugas.ProyekAkhir;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Kendaraan {
    private String plat;
    private String jenis;
    private TipeCuci tipeCuci;
    private String kodeAntrian;
    private LocalDateTime waktu;
    private int harga;

    public Kendaraan(String plat, String jenis, TipeCuci tipeCuci, String kodeAntrian) {
        this.plat = plat;
        this.jenis = jenis;
        this.tipeCuci = tipeCuci;
        this.kodeAntrian = kodeAntrian;
        this.waktu = LocalDateTime.now();
        this.harga = tipeCuci.getHarga(jenis);
    }

    public String getStruk() {
        DateTimeFormatter tglFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter jamFormat = DateTimeFormatter.ofPattern("HH:mm:ss");

        return "Cuci Motor & Mobil\n" +
               "Setia Hati\n" +
               "Jl. Margo Basuki, 08 Klojen Malang\n" +
               "---------------------------------------\n" +
               "No Antrian : " + kodeAntrian + "\n" +
               "Jenis Kendaraan : " + jenis + "\n" +
               "Cuci : " + tipeCuci.getNama() + "\n" +
               "Tanggal : " + waktu.format(tglFormat) + "\n" +
               "Jam : " + waktu.format(jamFormat) + "\n" +
               "----------------------------------------\n" +
               "Harga : Rp. " + harga + ".00\n";
    }

    public String getRiwayatFormat() {
        DateTimeFormatter tglFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter jamFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
        return kodeAntrian + ";" + plat + ";" + jenis + ";" + waktu.format(tglFormat) + ";" + waktu.format(jamFormat) + ";" + harga;
    }
}
