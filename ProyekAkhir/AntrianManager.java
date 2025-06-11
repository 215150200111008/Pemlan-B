package tugas.ProyekAkhir;

import java.util.HashMap;
import java.io.*;

public class AntrianManager {
    private static final HashMap<String, Integer> counterMap = new HashMap<>();
    private static final String COUNTER_FILE = "counter.txt";

    static {
        loadCounter();
    }

    public static String getKodeAntrian(String jenis, TipeCuci tipe) {
        String key = jenis.toLowerCase();
        int nomor = counterMap.getOrDefault(key, 0) + 1;
        counterMap.put(key, nomor);
        saveCounter();
        return tipe.getKode() + String.format("%03d", nomor);
    }

    private static void saveCounter() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(COUNTER_FILE))) {
            for (String key : counterMap.keySet()) {
                pw.println(key + "=" + counterMap.get(key));
            }
        } catch (IOException e) {
            System.err.println("Gagal menyimpan counter: " + e.getMessage());
        }
    }

    private static void loadCounter() {
        File file = new File(COUNTER_FILE);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("=");
                if (parts.length == 2) {
                    counterMap.put(parts[0], Integer.parseInt(parts[1]));
                }
            }
        } catch (IOException e) {
            System.err.println("Gagal memuat counter: " + e.getMessage());
        }
    }
}
