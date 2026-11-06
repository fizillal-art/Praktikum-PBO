package LabSession;

import java.util.ArrayList;
import java.util.List;

/**
 * Class Main: demonstrasi seluruh konsep OOP pada Sistem Manajemen Warnet.
 */
public class Main {

    public static void main(String[] args) {

        System.out.println("==============================================");
        System.out.println("      SISTEM MANAJEMEN WARNET \"NET-ZONE\"");
        System.out.println("==============================================\n");

        // ---- 1. Membuat objek warnet & komputer (objek subclass) ----
        Warnet warnet = new Warnet("Net-Zone");

        Komputer pc1 = new KomputerReguler("R-01");
        Komputer pc2 = new KomputerGaming("G-01", "RTX 3060");
        Komputer pc3 = new KomputerVIP("V-01", true);

        warnet.tambahKomputer(pc1);
        warnet.tambahKomputer(pc2);
        warnet.tambahKomputer(pc3);

        // ---- 2. Membuat objek pelanggan ----
        Pelanggan budi = new Pelanggan("Budi");          // non-member
        Pelanggan sari = new Pelanggan("Sari", true);    // member
        Pelanggan andi = new Pelanggan("Andi", true);    // member

        budi.topUp(20000);
        sari.topUp(50000);
        andi.topUp(10000);

        // ---- 3. Runtime polymorphism: referensi superclass, objek subclass ----
        System.out.println("1) DAFTAR KOMPUTER (Polymorphism + Overloading tampilkanInfo)");
        warnet.tampilkanDaftarKomputer();

        System.out.println("\n2) SIMULASI BIAYA 2 JAM UNTUK SETIAP TIPE (Polymorphism)");
        List<Komputer> semua = new ArrayList<>();
        semua.add(pc1);
        semua.add(pc2);
        semua.add(pc3);
        for (Komputer k : semua) {
            System.out.println("  " + k + " -> " + Komputer.rupiah(k.hitungBiaya(120)));
        }

        System.out.println("\n3) OVERLOADING hitungBiaya (tanpa diskon vs diskon member 10%)");
        System.out.println("  Gaming 60 menit, tanpa diskon : " + Komputer.rupiah(pc2.hitungBiaya(60)));
        System.out.println("  Gaming 60 menit, diskon 10%   : " + Komputer.rupiah(pc2.hitungBiaya(60, 10)));

        // ---- 4. Sesi pemakaian ----
        System.out.println("\n4) SESI PEMAKAIAN");
        warnet.mulaiSesi(pc1, budi);
        warnet.mulaiSesi(pc2, sari);
        warnet.mulaiSesi(pc3, andi);
        warnet.mulaiSesi(pc1, sari); // gagal: R-01 masih dipakai Budi

        warnet.selesaiSesi(pc1, budi, 90);   // Reguler 90 menit, non-member
        warnet.selesaiSesi(pc2, sari, 20);   // Gaming 20 menit -> ditagih minimal 30 menit
        warnet.selesaiSesi(pc3, andi, 200);  // VIP 200 menit -> diskon paket, saldo kurang

        // ---- 5. Encapsulation: perubahan data lewat method ----
        System.out.println("\n5) ENCAPSULATION (perubahan data lewat setter/method)");
        andi.topUp(50000);
        System.out.println("  Andi top up Rp50.000 -> saldo: " + Komputer.rupiah(andi.getSaldo()));
        warnet.selesaiSesi(pc3, andi, 200);

        try {
            pc1.setTarifPerJam(-1000);
        } catch (IllegalArgumentException e) {
            System.out.println("  Setter menolak data tidak valid: " + e.getMessage());
        }
        pc1.setStatus(Komputer.PERBAIKAN);
        System.out.println("  Status R-01 diubah via setter: " + pc1.getStatus());
        warnet.mulaiSesi(pc1, budi);

        // ---- 6. Ringkasan ----
        System.out.println("\n6) RINGKASAN");
        System.out.println("  Total pendapatan " + warnet.getNama() + ": "
                + Komputer.rupiah(warnet.getTotalPendapatan()));
        System.out.println("\n--- Program selesai ---");
    }
}
