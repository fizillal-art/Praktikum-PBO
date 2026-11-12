package TugasP6;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Program procedural sederhana untuk mengelola data nilai siswa.
 * Alur: input console -> tulis ke file -> baca kembali file -> tampilkan ke console.
 */
public class LaporanNilaiSiswa {

    static final String NAMA_FILE = "laporan_nilai.txt";
    static final double BATAS_LULUS = 70.0;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("=== PENGELOLAAN NILAI SISWA ===");
        int jumlahSiswa = bacaJumlahSiswa(input);

        // Langkah 1: input data dan tulis ke file
        try {
            tulisLaporan(input, jumlahSiswa);
            System.out.println("\nData berhasil ditulis ke file " + NAMA_FILE);
        } catch (IOException e) {
            System.out.println("Gagal menulis file: " + e.getMessage());
            input.close();
            return;
        }

        // Langkah 2: baca kembali file dan tampilkan
        try {
            bacaLaporan();
        } catch (IOException e) {
            System.out.println("Gagal membaca file: " + e.getMessage());
        }

        input.close();
    }

    /** Meminta jumlah siswa, diulang sampai input valid (bilangan bulat > 0). */
    static int bacaJumlahSiswa(Scanner input) {
        int jumlah = 0;
        while (jumlah <= 0) {
            System.out.print("Masukkan jumlah siswa: ");
            String baris = input.nextLine().trim();
            try {
                jumlah = Integer.parseInt(baris);
                if (jumlah <= 0) {
                    System.out.println("Jumlah harus lebih dari 0.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Input tidak valid, masukkan angka bulat.");
            }
        }
        return jumlah;
    }

    /** Meminta nilai (double) dengan validasi rentang 0-100. */
    static double bacaNilai(Scanner input, int nomor) {
        while (true) {
            System.out.print("  Nilai siswa ke-" + nomor + " (0-100): ");
            String baris = input.nextLine().trim().replace(',', '.');
            try {
                double nilai = Double.parseDouble(baris);
                if (nilai >= 0 && nilai <= 100) {
                    return nilai;
                }
                System.out.println("  Nilai harus antara 0 dan 100.");
            } catch (NumberFormatException e) {
                System.out.println("  Input tidak valid, masukkan angka.");
            }
        }
    }

    /** Menentukan status kelulusan berdasarkan nilai. */
    static String tentukanStatus(double nilai) {
        return nilai >= BATAS_LULUS ? "Lulus" : "Tidak Lulus";
    }

    /** Membaca data tiap siswa dari console dan menuliskannya ke file beserta ringkasan. */
    static void tulisLaporan(Scanner input, int jumlahSiswa) throws IOException {
        double totalNilai = 0;
        int jumlahLulus = 0;
        int jumlahTidakLulus = 0;

        // try-with-resources: writer otomatis ditutup
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(NAMA_FILE))) {
            writer.write("=== LAPORAN NILAI SISWA ===");
            writer.newLine();
            writer.write(String.format("%-4s %-25s %-8s %-12s", "No", "Nama", "Nilai", "Status"));
            writer.newLine();
            writer.write("-------------------------------------------------------");
            writer.newLine();

            for (int i = 1; i <= jumlahSiswa; i++) {
                System.out.println("\nData siswa ke-" + i);
                System.out.print("  Nama siswa: ");
                String nama = input.nextLine().trim();
                double nilai = bacaNilai(input, i);

                String status = tentukanStatus(nilai);
                if (nilai >= BATAS_LULUS) {
                    jumlahLulus++;
                } else {
                    jumlahTidakLulus++;
                }
                totalNilai += nilai;

                writer.write(String.format("%-4d %-25s %-8.2f %-12s", i, nama, nilai, status));
                writer.newLine();
            }

            double rataRata = totalNilai / jumlahSiswa;

            writer.write("-------------------------------------------------------");
            writer.newLine();
            writer.write("RINGKASAN");
            writer.newLine();
            writer.write(String.format("Rata-rata nilai kelas : %.2f", rataRata));
            writer.newLine();
            writer.write("Jumlah siswa lulus    : " + jumlahLulus);
            writer.newLine();
            writer.write("Jumlah tidak lulus    : " + jumlahTidakLulus);
            writer.newLine();
        }
    }

    /** Membaca file baris demi baris sampai null lalu menampilkannya ke console. */
    static void bacaLaporan() throws IOException {
        System.out.println("\n=== ISI FILE " + NAMA_FILE + " ===");

        // try-with-resources: reader otomatis ditutup
        try (BufferedReader reader = new BufferedReader(new FileReader(NAMA_FILE))) {
            String baris;
            while ((baris = reader.readLine()) != null) {
                System.out.println(baris);
            }
        }
    }
}