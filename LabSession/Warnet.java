package LabSession;

import java.util.ArrayList;
import java.util.List;

/**
 * Class pengelola warnet.
 * Daftar komputer bertipe Komputer (superclass), tetapi isinya objek subclass
 * -> contoh RUNTIME POLYMORPHISM.
 */
public class Warnet {

    private String nama;
    private double totalPendapatan;
    private List<Komputer> daftarKomputer = new ArrayList<>();

    public Warnet(String nama) {
        this.nama = nama;
    }

    public String getNama() {
        return nama;
    }

    public double getTotalPendapatan() {
        return totalPendapatan;
    }

    public void tambahKomputer(Komputer komputer) {
        daftarKomputer.add(komputer);
    }

    public void tampilkanDaftarKomputer() {
        System.out.println("Daftar komputer di " + nama + ":");
        for (Komputer k : daftarKomputer) {
            k.tampilkanInfo(true); // dispatch ke versi subclass saat runtime
        }
    }

    public void mulaiSesi(Komputer komputer, Pelanggan pelanggan) {
        if (!komputer.getStatus().equals(Komputer.TERSEDIA)) {
            System.out.println("  [GAGAL] " + komputer + " sedang " + komputer.getStatus());
            return;
        }
        komputer.setStatus(Komputer.DIPAKAI);
        System.out.println("  [MULAI] " + pelanggan + " memakai " + komputer);
    }

    public void selesaiSesi(Komputer komputer, Pelanggan pelanggan, int durasiMenit) {
        if (!komputer.getStatus().equals(Komputer.DIPAKAI)) {
            System.out.println("  [GAGAL] " + komputer + " tidak sedang dipakai");
            return;
        }

        // Memanggil overload hitungBiaya(int, double) -> memakai diskon member
        double biaya = komputer.hitungBiaya(durasiMenit, pelanggan.getDiskonPersen());

        System.out.println("  [SELESAI] " + pelanggan.getNama() + " | " + komputer
                + " | " + durasiMenit + " menit | Tagihan: " + Komputer.rupiah(biaya));

        if (pelanggan.bayar(biaya)) {
            totalPendapatan += biaya;
            System.out.println("            Pembayaran berhasil. Sisa saldo: "
                    + Komputer.rupiah(pelanggan.getSaldo()));
            komputer.setStatus(Komputer.TERSEDIA);
        } else {
            System.out.println("            Saldo tidak cukup (saldo "
                    + Komputer.rupiah(pelanggan.getSaldo()) + "). Silakan top up dulu.");
            // komputer tetap DIPAKAI sampai pembayaran lunas
        }
    }
}
