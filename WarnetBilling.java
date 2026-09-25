package LabSession;

import java.util.Scanner; // Import class Scanner untuk menerima input dari keyboard

// [POIN 1] Class & Object: Class WarnetBilling
public class WarnetBilling {

    // Atribut dasar
    private String namaPelanggan;
    private int lamaMain;
    
    // [POIN 7] Character & String: Tipe data char
    private char jenisPaket; 

    // [POIN 3] Konstanta: Keyword final
    private final int HARGA_REGULER = 5000;
    private final int HARGA_VIP = 8000;

    // [POIN 8] Array: Menyimpan daftar snack dan harga masing-masing
    private String[] daftarSnack = {"Indomie Rebus", "Es Teh Manis", "Kopi Susu"};
    private int[] hargaSnack = {10000, 3000, 5000};
    
    private String snackDibeli = "Tidak ada";
    private int hargaSnackDibeli = 0;

    // [POIN 2] Constructor: Mengisi nilai awal atribut saat objek dibuat
    public WarnetBilling(String namaPelanggan, int lamaMain, char jenisPaket) {
        this.namaPelanggan = namaPelanggan;
        this.lamaMain = lamaMain;
        this.jenisPaket = jenisPaket;
    }

    // [POIN 1] Method 1: untuk memproses pembelian snack
    public void beliSnack(int nomorSnack) {
        if (nomorSnack == 0) {
            System.out.println("-> Anda memilih tidak membeli snack.");
            return;
        }

        // [POIN 6] Exception Handling: Menangani input snack yang tidak valid
        try {
            // Mengurangi 1 karena indeks array dimulai dari 0
            this.snackDibeli = daftarSnack[nomorSnack - 1];
            this.hargaSnackDibeli = hargaSnack[nomorSnack - 1];
            System.out.println("-> Berhasil membeli: " + this.snackDibeli + " (Rp " + this.hargaSnackDibeli + ")");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("-> Gagal beli snack: Menu nomor " + nomorSnack + " tidak tersedia!");
        }
    }

    // [POIN 1] Method 2: Menghitung total biaya bermain + snack
    public double hitungTotal() {
        double totalInternet = 0;

        // [POIN 4] Kondisional: Penentuan tarif berdasarkan paket
        if (jenisPaket == 'R' || jenisPaket == 'r') {
            totalInternet = lamaMain * HARGA_REGULER;
        } else if (jenisPaket == 'V' || jenisPaket == 'v') {
            totalInternet = lamaMain * HARGA_VIP;
        } else {
            System.out.println("Paket tidak valid, otomatis dihitung Reguler.");
            totalInternet = lamaMain * HARGA_REGULER;
        }
        
        return totalInternet + hargaSnackDibeli;
    }

    // [POIN 1] Method 3: Menampilkan daftar snack
    public void tampilkanMenuSnack() {
        System.out.println("\n=== MENU SNACK WARNET ===");
        // [POIN 5] Looping: Mengiterasi array snack
        for (int i = 0; i < daftarSnack.length; i++) {
            System.out.println((i + 1) + ". " + daftarSnack[i] + " - Rp " + hargaSnack[i]);
        }
        System.out.println("0. Tidak beli snack");
        System.out.println("=========================");
    }

    // Method untuk mencetak struk pembayaran
    public void cetakStruk() {
        System.out.println("\n===================================");
        System.out.println("      STRUK PEMBAYARAN WARNET      ");
        System.out.println("===================================");

        // [POIN 7] Character & String: Memakai toUpperCase() dan length()
        String namaKapital = namaPelanggan.toUpperCase();
        int panjangNama = namaPelanggan.length();

        System.out.println("Nama Pelanggan : " + namaKapital + " (" + panjangNama + " huruf)");
        System.out.println("Jenis Paket    : " + jenisPaket + " (R=Reguler, V=VIP)");
        System.out.println("Lama Bermain   : " + lamaMain + " jam");
        System.out.println("Snack Dibeli   : " + snackDibeli + " (Rp " + hargaSnackDibeli + ")");
        System.out.println("-----------------------------------");
        System.out.println("TOTAL BAYAR    : Rp " + hitungTotal());
        System.out.println("===================================\n");
    }

    // Main Method untuk Interaksi Keyboard
    public static void main(String[] args) {
        // Membuat objek Scanner untuk membaca input pengguna
        Scanner input = new Scanner(System.in);

        System.out.println("===================================");
        System.out.println("    INPUT DATA BILLING WARNET      ");
        System.out.println("===================================");

        // 1. Input Nama
        System.out.print("Masukkan nama pelanggan  : ");
        String nama = input.nextLine();

        // 2. Input Durasi Main
        System.out.print("Masukkan lama main (jam) : ");
        int durasi = input.nextInt();

        // 3. Input Jenis Paket
        System.out.print("Pilih paket (R = Reguler / V = VIP): ");
        char paket = input.next().charAt(0);

        // [POIN 9] Membuat Objek dari data yang diinput pengguna
        WarnetBilling pelanggan = new WarnetBilling(nama, durasi, paket);

        // 4. Tampilkan Menu Snack
        pelanggan.tampilkanMenuSnack();

        // 5. Input Pilihan Snack
        System.out.print("Pilih nomor snack (0 jika tidak beli): ");
        int pilihanSnack = input.nextInt();

        // 6. Memproses Pilihan Snack
        pelanggan.beliSnack(pilihanSnack);

        // 7. Cetak Struk Akhir
        pelanggan.cetakStruk();

        // Menutup objek Scanner setelah selesai digunakan
        input.close();
    }
}
