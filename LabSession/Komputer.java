package LabSession;
import java.util.Locale;

/**
 * ABSTRACT CLASS + SUPERCLASS: dasar dari semua jenis komputer di warnet.
 * - Encapsulation : atribut private + getter/setter berisi validasi.
 * - Interface     : meng-implements Billable.
 * - Abstract      : getTipe() dan getSpesifikasi() wajib diimplementasikan subclass.
 * - Overloading   : hitungBiaya(int) vs hitungBiaya(int, double), tampilkanInfo().
 */
public abstract class Komputer implements Billable {

    public static final String TERSEDIA = "TERSEDIA";
    public static final String DIPAKAI = "DIPAKAI";
    public static final String PERBAIKAN = "PERBAIKAN";

    // ===== Encapsulation: atribut private =====
    private String kode;
    private double tarifPerJam;
    private String status;

    public Komputer(String kode, double tarifPerJam) {
        this.kode = kode;
        setTarifPerJam(tarifPerJam); // lewat setter agar tervalidasi
        this.status = TERSEDIA;
    }

    // ===== Getter & Setter =====
    public String getKode() {
        return kode;
    }

    public double getTarifPerJam() {
        return tarifPerJam;
    }

    public void setTarifPerJam(double tarifPerJam) {
        if (tarifPerJam <= 0) {
            throw new IllegalArgumentException("Tarif per jam harus lebih dari 0");
        }
        this.tarifPerJam = tarifPerJam;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        if (!status.equals(TERSEDIA) && !status.equals(DIPAKAI) && !status.equals(PERBAIKAN)) {
            throw new IllegalArgumentException("Status tidak valid: " + status);
        }
        this.status = status;
    }

    // ===== Abstract method (wajib di-override subclass) =====
    public abstract String getTipe();

    public abstract String getSpesifikasi();

    // ===== Helper untuk subclass =====
    protected void validasiDurasi(int durasiMenit) {
        if (durasiMenit <= 0) {
            throw new IllegalArgumentException("Durasi harus lebih dari 0 menit");
        }
    }

    public static String rupiah(double nilai) {
        return String.format(Locale.forLanguageTag("id-ID"), "Rp%,.0f", nilai);
    }

    // ===== Implementasi method dari interface Billable =====
    @Override
    public String getRincianTarif() {
        return getTipe() + " - " + rupiah(tarifPerJam) + "/jam";
    }

    // ===== Method Overloading #1: hitungBiaya =====
    // Versi dengan diskon (misal diskon member). hitungBiaya(int) sendiri
    // dideklarasikan di Billable dan diimplementasikan oleh tiap subclass.
    public double hitungBiaya(int durasiMenit, double diskonPersen) {
        double biayaNormal = hitungBiaya(durasiMenit);
        return biayaNormal - (biayaNormal * diskonPersen / 100.0);
    }

    // ===== Method Overloading #2: tampilkanInfo =====
    public void tampilkanInfo() {
        tampilkanInfo(false);
    }

    public void tampilkanInfo(boolean detail) {
        System.out.println("  [" + kode + "] " + getRincianTarif() + " | Status: " + status);
        if (detail) {
            System.out.println("        Spesifikasi: " + getSpesifikasi());
        }
    }

    // ===== Method Overriding (dari class Object) =====
    @Override
    public String toString() {
        return getTipe() + " " + kode;
    }
}
