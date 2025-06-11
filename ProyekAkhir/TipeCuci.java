package tugas.ProyekAkhir;


public enum TipeCuci {
    CUKI_KILAT("Cuci Kilat", new int[]{10000, 25000}, "CK"),
    CUKI_SALJU("Cuci Salju", new int[]{15000, 35000}, "CS"),
    CUKI_BERSIH("Cuci Bersih", new int[]{20000, 40000}, "CB");

    private final String nama;
    private final int[] harga; // [motor, mobil]
    private final String kode;

    TipeCuci(String nama, int[] harga, String kode) {
        this.nama = nama;
        this.harga = harga;
        this.kode = kode;
    }

    public String getNama() {
        return nama;
    }

    public int getHarga(String jenis) {
        return jenis.equalsIgnoreCase("motor") ? harga[0] : harga[1];
    }

    public String getKode() {
        return kode;
    }

    @Override
    public String toString() {
        return nama;
    }
}
