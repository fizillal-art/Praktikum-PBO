package LabSession;
/**
 * SUBCLASS 3: ruang VIP ber-AC dengan kursi gaming dan gratis minuman.
 * Aturan: pemakaian >= 180 menit mendapat diskon paket 10%.
 */
public class KomputerVIP extends Komputer {

    private static final int BATAS_PAKET_MENIT = 180;
    private static final double DISKON_PAKET = 0.10;

    private boolean gratisMinuman;

    public KomputerVIP(String kode, boolean gratisMinuman) {
        super(kode, 12000);
        this.gratisMinuman = gratisMinuman;
    }

    public boolean isGratisMinuman() {
        return gratisMinuman;
    }

    public void setGratisMinuman(boolean gratisMinuman) {
        this.gratisMinuman = gratisMinuman;
    }

    @Override
    public String getTipe() {
        return "VIP";
    }

    @Override
    public String getSpesifikasi() {
        return "Core i7, RAM 32GB, SSD 1TB, GPU RTX 4070, ruang privat ber-AC"
                + (gratisMinuman ? ", gratis minuman" : "");
    }

    @Override
    public double hitungBiaya(int durasiMenit) {
        validasiDurasi(durasiMenit);
        double biaya = getTarifPerJam() * durasiMenit / 60.0;
        if (durasiMenit >= BATAS_PAKET_MENIT) {
            biaya -= biaya * DISKON_PAKET;
        }
        return biaya;
    }

    @Override
    public String getRincianTarif() {
        return super.getRincianTarif() + " (diskon 10% jika >= 3 jam)";
    }
}
