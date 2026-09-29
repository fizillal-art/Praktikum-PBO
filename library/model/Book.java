package library.model;

public class Book {
    // Tipe data primitive & reference
    private String judul;
    private String penulis;
    private int tahunTerbit;
    private String kategori;
    private boolean statusKetersediaan;
    private int kaliDipinjam; // Untuk analisis

    // Constructor
    public Book(String judul, String penulis, int tahunTerbit, String kategori) {
        this.judul = judul;
        this.penulis = penulis;
        this.tahunTerbit = tahunTerbit;
        
        // Manipulasi Character & String: Memastikan huruf pertama kategori selalu kapital
        if (kategori != null && !kategori.isEmpty()) {
            char hurufPertama = Character.toUpperCase(kategori.charAt(0));
            this.kategori = hurufPertama + kategori.substring(1).toLowerCase();
        } else {
            this.kategori = "Umum";
        }
        
        this.statusKetersediaan = true; // Default saat ditambahkan selalu tersedia
        this.kaliDipinjam = 0;
    }

    // Getter dan Setter
    public String getJudul() { return judul; }
    public String getKategori() { return kategori; }
    public boolean isStatusKetersediaan() { return statusKetersediaan; }
    public void setStatusKetersediaan(boolean statusKetersediaan) { this.statusKetersediaan = statusKetersediaan; }
    public int getKaliDipinjam() { return kaliDipinjam; }
    
    public void tambahKaliDipinjam() { this.kaliDipinjam++; }

    public void infoBuku() {
        String status = statusKetersediaan ? "Tersedia" : "Dipinjam";
        System.out.println("- " + judul + " | " + penulis + " (" + tahunTerbit + ") | Kategori: " + kategori + " | Status: " + status);
    }
}