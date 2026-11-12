package model;

/**
 * Kelas data untuk satu mahasiswa: nama, NIM, dan nilai.
 */
public class Mahasiswa {

    // Konstanta (final) standar kelulusan dan rentang nilai
    public static final double FINAL_PASSING_GRADE = 70.0;
    public static final double NILAI_MIN = 0.0;
    public static final double NILAI_MAX = 100.0;

    private final String nama;   // reference type
    private final String nim;    // reference type
    private final double nilai;  // primitive type

    /**
     * Constructor: menginisialisasi atribut objek.
     * Nama dan NIM dirapikan dengan trim(), nama diubah ke huruf besar.
     *
     * @throws NilaiInvalidException jika nilai < 0 atau > 100
     * @throws IllegalArgumentException jika NIM mengandung karakter selain angka
     */
    public Mahasiswa(String nama, String nim, double nilai) throws NilaiInvalidException {
        if (Double.isNaN(nilai) || nilai < NILAI_MIN || nilai > NILAI_MAX) {
            throw new NilaiInvalidException(nilai);
        }

        this.nama = nama.trim().toUpperCase();
        this.nim = nim.trim();

        // Assertion: NIM tidak boleh kosong (aktif jika program dijalankan dengan -ea)
        assert !this.nim.isEmpty() : "NIM tidak boleh kosong!";

        if (!isNimValid(this.nim)) {
            throw new IllegalArgumentException("NIM hanya boleh berisi angka: " + this.nim);
        }

        this.nilai = nilai;
    }

    /** Memeriksa NIM: tidak kosong dan seluruh karakternya angka (Character.isDigit). */
    public static boolean isNimValid(String nim) {
        if (nim == null || nim.trim().isEmpty()) {
            return false;
        }
        for (char c : nim.trim().toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    /** Memetakan nilai ke grade huruf dengan If-Else. */
    public String getGrade() {
        if (nilai >= 85) {
            return "A";
        } else if (nilai >= 75) {
            return "B";
        } else if (nilai >= FINAL_PASSING_GRADE) {
            return "C";
        } else {
            return "D";
        }
    }

    public boolean isLulus() {
        return nilai >= FINAL_PASSING_GRADE;
    }

    public String getStatus() {
        return isLulus() ? "Lulus" : "Tidak Lulus";
    }

    public String getNama() {
        return nama;
    }

    public String getNim() {
        return nim;
    }

    public double getNilai() {
        return nilai;
    }
}