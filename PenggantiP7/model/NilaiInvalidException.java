package model;

/**
 * Exception khusus yang dilempar (throw) ketika nilai mahasiswa
 * berada di luar rentang 0 sampai 100.
 */
public class NilaiInvalidException extends Exception {

    private static final long serialVersionUID = 1L;

    private final double nilai;

    public NilaiInvalidException(double nilai) {
        super("Nilai tidak valid: " + nilai + " (harus antara 0 dan 100)");
        this.nilai = nilai;
    }

    public double getNilai() {
        return nilai;
    }
}