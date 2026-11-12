package app;

import model.Mahasiswa;
import model.NilaiInvalidException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

/**
 * Kelas utama: membaca data mahasiswa dari console, menyimpannya di ArrayList,
 * lalu menuliskan rekapitulasi dan statistik kelas ke laporan_nilai.txt.
 */
public class LaporanNilaiApp {

    private static final String NAMA_FILE = "laporan_nilai.txt";
    private static final String GARIS = "-".repeat(72);

    public static void main(String[] args) {
        ArrayList<Mahasiswa> daftar = new ArrayList<>();

        try (Scanner input = new Scanner(System.in)) {
            System.out.println("=== PENGELOLAAN NILAI MAHASISWA ===");
            int jumlah = bacaJumlah(input);

            for (int i = 1; i <= jumlah; i++) {
                System.out.println("\nData mahasiswa ke-" + i);
                daftar.add(bacaMahasiswa(input, daftar));
            }
        }

        String laporan = buatLaporan(daftar);
        System.out.println("\n" + laporan);
        simpanKeFile(laporan);
    }

    // ------------------------------------------------------------------
    // Console I/O
    // ------------------------------------------------------------------

    private static int bacaJumlah(Scanner input) {
        int jumlah = 0;
        while (jumlah <= 0) {
            System.out.print("Masukkan jumlah mahasiswa: ");
            try {
                jumlah = Integer.parseInt(input.nextLine().trim());
                if (jumlah <= 0) {
                    System.out.println("Jumlah harus lebih dari 0.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Input tidak valid, masukkan angka bulat.");
            }
        }
        return jumlah;
    }

    private static Mahasiswa bacaMahasiswa(Scanner input, ArrayList<Mahasiswa> daftar) {
        String nama = bacaNama(input);
        String nim = bacaNim(input, daftar);

        Mahasiswa mhs = null;
        while (mhs == null) {
            double nilai = bacaDouble(input, "  Nilai (0-100): ");
            try {
                mhs = new Mahasiswa(nama, nim, nilai);
            } catch (NilaiInvalidException e) {
                System.out.println("  Error: " + e.getMessage());
            }
        }
        return mhs;
    }

    private static String bacaNama(Scanner input) {
        while (true) {
            System.out.print("  Nama: ");
            String nama = input.nextLine().trim();
            if (!nama.isEmpty()) {
                return nama;
            }
            System.out.println("  Nama tidak boleh kosong.");
        }
    }

    private static String bacaNim(Scanner input, ArrayList<Mahasiswa> daftar) {
        while (true) {
            System.out.print("  NIM: ");
            String nim = input.nextLine().trim();

            if (!Mahasiswa.isNimValid(nim)) {
                System.out.println("  NIM harus diisi dan hanya boleh berisi angka.");
            } else if (sudahTerdaftar(nim, daftar)) {
                System.out.println("  NIM sudah terdaftar, gunakan NIM lain.");
            } else {
                return nim;
            }
        }
    }

    private static boolean sudahTerdaftar(String nim, ArrayList<Mahasiswa> daftar) {
        for (Mahasiswa m : daftar) {
            if (m.getNim().equals(nim)) {
                return true;
            }
        }
        return false;
    }

    private static double bacaDouble(Scanner input, String prompt) {
        while (true) {
            System.out.print(prompt);
            String baris = input.nextLine().trim().replace(',', '.');
            try {
                return Double.parseDouble(baris);
            } catch (NumberFormatException e) {
                System.out.println("  Input tidak valid, masukkan angka.");
            }
        }
    }

    // ------------------------------------------------------------------
    // Pembuatan laporan dan File I/O
    // ------------------------------------------------------------------

    private static String buatLaporan(ArrayList<Mahasiswa> daftar) {
        String nl = System.lineSeparator();
        StringBuilder sb = new StringBuilder();

        sb.append("=== LAPORAN NILAI MAHASISWA ===").append(nl);
        sb.append(String.format(Locale.US, "%-4s %-12s %-25s %-8s %-6s %s",
                "No", "NIM", "Nama", "Nilai", "Grade", "Status")).append(nl);
        sb.append(GARIS).append(nl);

        double total = 0;
        int lulus = 0;
        int tidakLulus = 0;
        int no = 1;

        for (Mahasiswa m : daftar) {
            sb.append(String.format(Locale.US, "%-4d %-12s %-25s %-8.2f %-6s %s",
                    no++, m.getNim(), m.getNama(), m.getNilai(), m.getGrade(), m.getStatus()))
              .append(nl);

            total += m.getNilai();
            if (m.isLulus()) {
                lulus++;
            } else {
                tidakLulus++;
            }
        }

        double rataRata = daftar.isEmpty() ? 0 : total / daftar.size();

        sb.append(GARIS).append(nl);
        sb.append("STATISTIK KELAS").append(nl);
        sb.append("Jumlah mahasiswa      : ").append(daftar.size()).append(nl);
        sb.append(String.format(Locale.US, "Rata-rata nilai kelas : %.2f", rataRata)).append(nl);
        sb.append("Jumlah lulus          : ").append(lulus).append(nl);
        sb.append("Jumlah tidak lulus    : ").append(tidakLulus).append(nl);

        return sb.toString();
    }

    private static void simpanKeFile(String laporan) {
        // try-with-resources: writer otomatis ditutup, IOException ditangani di catch
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(NAMA_FILE))) {
            writer.write(laporan);
            System.out.println("Laporan berhasil disimpan ke file " + NAMA_FILE);
        } catch (IOException e) {
            System.out.println("Gagal menyimpan file: " + e.getMessage());
        }
    }
}
