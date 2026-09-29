package library.service;

import library.model.Book;
import library.model.Member;
import library.exception.BookNotFoundException;
import library.exception.BorrowLimitExceededException;

import java.util.ArrayList;
import java.util.HashMap;

public class LibraryService {
    private ArrayList<Book> koleksiBuku;
    private ArrayList<Member> daftarAnggota;
    private HashMap<String, Integer> popularitasKategori; // Menyimpan data kategori paling populer
    private int totalKeseluruhanPinjaman = 0;

    public LibraryService() {
        koleksiBuku = new ArrayList<>();
        daftarAnggota = new ArrayList<>();
        popularitasKategori = new HashMap<>();
        
        // Data anggota dummy untuk kemudahan pengetesan
        daftarAnggota.add(new Member("M01", "Budi"));
        daftarAnggota.add(new Member("M02", "Siti"));
    }

    public void tambahBuku(Book buku) {
        koleksiBuku.add(buku);
        System.out.println("Buku '" + buku.getJudul() + "' berhasil ditambahkan!");
    }

    public void daftarSemuaBuku() {
        if (koleksiBuku.isEmpty()) {
            System.out.println("Belum ada buku di perpustakaan.");
            return;
        }
        for (Book buku : koleksiBuku) {
            buku.infoBuku();
        }
    }

    public void cariBuku(String keyword) {
        System.out.println("Hasil pencarian untuk: " + keyword);
        boolean ditemukan = false;
        
        // Looping dan Manipulasi String toLowerCase() dan contains()
        for (Book buku : koleksiBuku) {
            if (buku.getJudul().toLowerCase().contains(keyword.toLowerCase()) || 
                buku.getKategori().toLowerCase().contains(keyword.toLowerCase())) {
                buku.infoBuku();
                ditemukan = true;
            }
        }
        if (!ditemukan) {
            System.out.println("Buku tidak ditemukan.");
        }
    }

    private Member cariAnggota(String idAnggota) {
        for (Member m : daftarAnggota) {
            if (m.getId().equalsIgnoreCase(idAnggota)) return m;
        }
        return null;
    }

    public void pinjamBuku(String idAnggota, String judulBuku) throws BookNotFoundException, BorrowLimitExceededException {
        Member anggota = cariAnggota(idAnggota);
        
        // ASSERTION: Memastikan anggota valid sebelum memproses transaksi
        assert anggota != null : "Error Kritis: Data Anggota tidak ditemukan/tidak valid!";

        if (anggota.getDaftarPinjaman().size() >= 3) {
            throw new BorrowLimitExceededException("Anggota " + anggota.getNama() + " sudah meminjam batas maksimal (3 buku).");
        }

        Book bukuDipinjam = null;
        for (Book buku : koleksiBuku) {
            if (buku.getJudul().equalsIgnoreCase(judulBuku)) {
                bukuDipinjam = buku;
                break;
            }
        }

        if (bukuDipinjam == null) {
            throw new BookNotFoundException("Buku dengan judul '" + judulBuku + "' tidak ditemukan di sistem.");
        }

        if (!bukuDipinjam.isStatusKetersediaan()) {
            System.out.println("Maaf, buku '" + judulBuku + "' sedang dipinjam orang lain.");
            return;
        }

        // Proses pinjam
        bukuDipinjam.setStatusKetersediaan(false);
        bukuDipinjam.tambahKaliDipinjam();
        anggota.pinjamBuku(bukuDipinjam);
        totalKeseluruhanPinjaman++;

        // Catat popularitas kategori
        String kategori = bukuDipinjam.getKategori();
        popularitasKategori.put(kategori, popularitasKategori.getOrDefault(kategori, 0) + 1);

        System.out.println("Berhasil! " + anggota.getNama() + " meminjam buku '" + bukuDipinjam.getJudul() + "'.");
    }

    public void kembalikanBuku(String idAnggota, String judulBuku) {
        Member anggota = cariAnggota(idAnggota);
        if (anggota == null) {
            System.out.println("Anggota tidak ditemukan.");
            return;
        }

        Book bukuDikembalikan = null;
        for (Book b : anggota.getDaftarPinjaman()) {
            if (b.getJudul().equalsIgnoreCase(judulBuku)) {
                bukuDikembalikan = b;
                break;
            }
        }

        if (bukuDikembalikan != null) {
            bukuDikembalikan.setStatusKetersediaan(true);
            anggota.kembalikanBuku(bukuDikembalikan);
            System.out.println("Buku '" + judulBuku + "' berhasil dikembalikan oleh " + anggota.getNama());
        } else {
            System.out.println("Anggota ini tidak sedang meminjam buku tersebut.");
        }
    }

    public void buatLaporan() {
        System.out.println("\n=== LAPORAN ANALISIS PERPUSTAKAAN ===");
        System.out.println("Total Transaksi Peminjaman: " + totalKeseluruhanPinjaman);

        // Analisis Buku Paling Sering Dipinjam
        Book bukuTerpopuler = null;
        for (Book b : koleksiBuku) {
            if (bukuTerpopuler == null || b.getKaliDipinjam() > bukuTerpopuler.getKaliDipinjam()) {
                bukuTerpopuler = b;
            }
        }
        if (bukuTerpopuler != null && bukuTerpopuler.getKaliDipinjam() > 0) {
            System.out.println("Buku Paling Sering Dipinjam: " + bukuTerpopuler.getJudul() + " (" + bukuTerpopuler.getKaliDipinjam() + " kali)");
        }

        // Analisis Anggota Paling Aktif
        Member anggotaTeraktif = null;
        for (Member m : daftarAnggota) {
            if (anggotaTeraktif == null || m.getTotalPinjamanSejarah() > anggotaTeraktif.getTotalPinjamanSejarah()) {
                anggotaTeraktif = m;
            }
        }
        if (anggotaTeraktif != null && anggotaTeraktif.getTotalPinjamanSejarah() > 0) {
            System.out.println("Anggota Paling Aktif: " + anggotaTeraktif.getNama() + " (" + anggotaTeraktif.getTotalPinjamanSejarah() + " buku)");
        }

        // Analisis Kategori Populer
        String kategoriPopuler = "";
        int maxKategori = 0;
        for (String kategori : popularitasKategori.keySet()) {
            if (popularitasKategori.get(kategori) > maxKategori) {
                maxKategori = popularitasKategori.get(kategori);
                kategoriPopuler = kategori;
            }
        }
        if (!kategoriPopuler.isEmpty()) {
            System.out.println("Kategori Paling Diminati: " + kategoriPopuler + " (" + maxKategori + " kali dipinjam)");
        }
        System.out.println("=====================================");
    }
}
