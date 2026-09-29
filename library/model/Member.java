package library.model;

import java.util.ArrayList;

public class Member {
    private String id;
    private String nama;
    private ArrayList<Book> daftarPinjaman; // Collection untuk menyimpan buku yang sedang dipinjam
    private int totalPinjamanSejarah; // Untuk analisis anggota teraktif

    public Member(String id, String nama) {
        this.id = id;
        this.nama = nama;
        this.daftarPinjaman = new ArrayList<>();
        this.totalPinjamanSejarah = 0;
    }

    public String getId() { return id; }
    public String getNama() { return nama; }
    public ArrayList<Book> getDaftarPinjaman() { return daftarPinjaman; }
    public int getTotalPinjamanSejarah() { return totalPinjamanSejarah; }

    public void pinjamBuku(Book buku) {
        daftarPinjaman.add(buku);
        totalPinjamanSejarah++;
    }

    public void kembalikanBuku(Book buku) {
        daftarPinjaman.remove(buku);
    }
}