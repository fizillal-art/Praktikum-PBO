package LabSession;
/**
 * SUBCLASS 2: komputer gaming dengan GPU dedicated.
 * Aturan: pemakaian minimal dihitung 30 menit.
 */
public class KomputerGaming extends Komputer {

    private static final int MINIMAL_MENIT = 30;

    private String gpu;

    public KomputerGaming(String kode, String gpu) {
        super(kode, 8000);
        this.gpu = gpu;
    }

    public String getGpu() {
        return gpu;
    }

    public void setGpu(String gpu) {
        this.gpu = gpu;
    }

    @Override
    public String getTipe() {
        return "Gaming";
    }

    @Override
    public String getSpesifikasi() {
        return "Core i5, RAM 16GB, SSD 512GB, GPU " + gpu + ", monitor 144Hz";
    }

    @Override
    public double hitungBiaya(int durasiMenit) {
        validasiDurasi(durasiMenit);
        int menitDitagih = Math.max(durasiMenit, MINIMAL_MENIT);
        return getTarifPerJam() * menitDitagih / 60.0;
    }

    @Override
    public String getRincianTarif() {
        return super.getRincianTarif() + " (minimal " + MINIMAL_MENIT + " menit)";
    }
}
