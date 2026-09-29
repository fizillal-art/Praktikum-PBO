package library.main;

import library.model.Book;
import library.service.LibraryService;
import library.exception.BookNotFoundException;
import library.exception.BorrowLimitExceededException;

import java.util.Scanner;

public class MainApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LibraryService perpustakaan = new LibraryService();
        boolean jalan = true;

        System.out.println("Selamat Datang di Sistem Manajemen Perpustakaan Mini!");
        System.out.println("(Gunakan ID Anggota: M01 atau M02 untuk mencoba fitur peminjaman)\n");

        while (jalan) {
            System.out.println("\n=== MENU PERPUSTAKAAN ===");
            System.out.println("1. Tambah Buku");
            System.out.println("2. Daftar Buku");
            System.out.println("3. Cari Buku");
            System.out.println("4. Pinjam Buku");
            System.out.println("5. Kembalikan Buku");
            System.out.println("6. Laporan Perpustakaan");
            System.out.println("7. Keluar");
            System.out.print("Pilih menu (1-7): ");
            
            int pilihan = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (pilihan) {
                case 1:
                    System.out.print("Masukkan Judul: ");
                    String judul = scanner.nextLine();
                    System.out.print("Masukkan Penulis: ");
                    String penulis = scanner.nextLine();
                    System.out.print("Masukkan Tahun Terbit: ");
                    int tahun = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Masukkan Kategori (Fiksi/Sains/Sejarah/dll): ");
                    String kategori = scanner.nextLine();
                    perpustakaan.tambahBuku(new Book(judul, penulis, tahun, kategori));
                    break;
                case 2:
                    perpustakaan.daftarSemuaBuku();
                    break;
                case 3:
                    System.out.print("Masukkan judul atau kategori buku: ");
                    String keyword = scanner.nextLine();
                    perpustakaan.cariBuku(keyword);
                    break;
                case 4:
                    System.out.print("Masukkan ID Anggota (misal: M01): ");
                    String idPinjam = scanner.nextLine();
                    System.out.print("Masukkan Judul Buku: ");
                    String judulPinjam = scanner.nextLine();
                    
                    // Penerapan Exception Handling
                    try {
                        perpustakaan.pinjamBuku(idPinjam, judulPinjam);
                    } catch (BookNotFoundException | BorrowLimitExceededException e) {
                        System.out.println("GAGAL: " + e.getMessage());
                    } catch (AssertionError e) {
                        System.out.println("KESALAHAN SISTEM: " + e.getMessage());
                    }
                    break;
                case 5:
                    System.out.print("Masukkan ID Anggota: ");
                    String idKembali = scanner.nextLine();
                    System.out.print("Masukkan Judul Buku yang dikembalikan: ");
                    String judulKembali = scanner.nextLine();
                    perpustakaan.kembalikanBuku(idKembali, judulKembali);
                    break;
                case 6:
                    perpustakaan.buatLaporan();
                    break;
                case 7:
                    jalan = false;
                    System.out.println("Terima kasih telah menggunakan sistem perpustakaan!");
                    break;
                default:
                    System.out.println("Pilihan tidak valid, silakan coba lagi.");
            }
        }
        scanner.close();
    }
}
