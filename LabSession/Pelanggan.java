package LabSession;
/**
 * Class pelanggan warnet. Saldo hanya bisa berubah lewat topUp() dan bayar().
 * Overloading constructor: Pelanggan(nama) dan Pelanggan(nama, member).
 */
public class Pelanggan {

    private String nama;
    private double saldo;
    private boolean member;

    public Pelanggan(String nama) {
        this(nama, false);
    }

    public Pelanggan(String nama, boolean member) {
        this.nama = nama;
        this.member = member;
        this.saldo = 0;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public double getSaldo() {
        return saldo;
    }

    public boolean isMember() {
        return member;
    }

    public void setMember(boolean member) {
        this.member = member;
    }

    // Diskon yang berhak didapat pelanggan (member 10%)
    public double getDiskonPersen() {
        return member ? 10.0 : 0.0;
    }

    public void topUp(double jumlah) {
        if (jumlah <= 0) {
            throw new IllegalArgumentException("Jumlah top up harus lebih dari 0");
        }
        saldo += jumlah;
    }

    // Mengembalikan false jika saldo tidak cukup
    public boolean bayar(double jumlah) {
        if (jumlah > saldo) {
            return false;
        }
        saldo -= jumlah;
        return true;
    }

    @Override
    public String toString() {
        return nama + (member ? " (Member)" : " (Non-member)");
    }
}
