package LabSession;
/**
 * INTERFACE: kontrak untuk semua objek yang dapat dihitung biayanya.
 * Setiap class yang meng-implements Billable wajib menyediakan
 * kedua method di bawah ini.
 */
public interface Billable {

    // Menghitung biaya pemakaian berdasarkan durasi (menit)
    double hitungBiaya(int durasiMenit);

    // Mengembalikan teks ringkas rincian tarif
    String getRincianTarif();
}
