package lab.kasir;

import javax.swing.SwingUtilities;

public class MainKasir {

    public static void main(String[] args) {
        // ===== TAHAP 1: Uji class Pesanan =====
        System.out.println("=== TAHAP 1: Uji Pesanan ===");
        Pesanan pesanan = new Pesanan();

        pesanan.tambahItem(15000, 2); // Nasi Goreng
        pesanan.tambahItem(5000, 3);  // Es Teh
        System.out.println("Subtotal : " + pesanan.getSubtotal());
        System.out.println("Total    : " + pesanan.hitungTotal() + " (tanpa diskon)");

        pesanan.tambahItem(25000, 1); // Ayam Bakar
        System.out.println("Subtotal : " + pesanan.getSubtotal());
        System.out.println("Total    : " + pesanan.hitungTotal() + " (diskon 10%)");

        pesanan.reset();
        System.out.println("Setelah reset, subtotal: " + pesanan.getSubtotal());

        // ===== TAHAP 2: Dua kasir sebagai thread =====
        System.out.println();
        System.out.println("=== TAHAP 2: Dua Kasir (Thread) ===");
        Kasir dina = new Kasir("Dina");
        Kasir eko = new Kasir("Eko");

        dina.start();
        eko.start();

        try {
            dina.join();
            eko.join();
        } catch (InterruptedException e) {
            System.out.println("Main terhenti.");
        }
        System.out.println("Semua pelanggan selesai dilayani");

        // ===== TAHAP 3 & 4: Tampilkan GUI =====
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new KasirFrame();
            }
        });
    }
}