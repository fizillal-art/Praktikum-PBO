package LabSession;
/**
 * SUBCLASS 1: komputer reguler untuk browsing / mengetik tugas.
 * Biaya dihitung proporsional per menit.
 */
public class KomputerReguler extends Komputer {

    public KomputerReguler(String kode) {
        super(kode, 5000); // pemanggilan constructor superclass
    }

    @Override
    public String getTipe() {
        return "Reguler";
    }

    @Override
    public String getSpesifikasi() {
        return "Core i3, RAM 8GB, SSD 256GB, monitor 19\"";
    }

    @Override
    public double hitungBiaya(int durasiMenit) {
        validasiDurasi(durasiMenit);
        return getTarifPerJam() * durasiMenit / 60.0;
    }
}
