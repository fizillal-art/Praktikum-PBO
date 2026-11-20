# Praktikum PBO Kelas 3B: LabSession Pertemuan 11 dan 12
## Studi Kasus: Sistem Kasir Kantin UNS

| | |
|---|---|
| **Nama** | Fizillal Kamal Arsad Purwanto |
| **NIM** | L0325046 |
| **Program Studi** | Informatika PSDKU Kebumen, Universitas Sebelas Maret |
| **Mata Kuliah** | Pemrograman Berorientasi Objek (PBO) |
| **Cakupan** | Pertemuan 11 (Multithreading) dan Pertemuan 12 (GUI Programming) |
| **Package** | `lab.kasir` |

---

## Daftar Isi

1. [Deskripsi Soal](#1-deskripsi-soal)
2. [Struktur Proyek](#2-struktur-proyek)
3. [Tahap 1: Class Logika (`Pesanan`)](#3-tahap-1-class-logika-pesanan)
4. [Tahap 2: Dua Kasir sebagai Thread](#4-tahap-2-dua-kasir-sebagai-thread)
5. [Tahap 3: Tampilan GUI Kasir](#5-tahap-3-tampilan-gui-kasir)
6. [Tahap 4: Tombol Cetak Struk dengan Thread](#6-tahap-4-tombol-cetak-struk-dengan-thread)
7. [Cara Menjalankan](#7-cara-menjalankan)
8. [Kesimpulan](#8-kesimpulan)

---

## 1. Deskripsi Soal

Kantin UNS ingin aplikasi kasir sederhana berbasis jendela. Kasir memasukkan menu, harga, dan jumlah, lalu aplikasi menghitung total bayar. Selain itu dibuat simulasi dua kasir yang melayani pelanggan bersamaan, dan fitur cetak struk yang tidak membuat jendela macet.

Aplikasi dikerjakan bertahap dari Tahap 1 sampai Tahap 4.

**Ketentuan pengerjaan:**

- Semua class berada dalam satu package: `lab.kasir`.
- Atribut class dibuat `private` (enkapsulasi) dan diakses lewat method bila perlu.
- Setiap tahap dicocokkan dengan hasil yang diharapkan, lalu ditangkap layarnya untuk laporan.

**Ringkasan tahap:**

| Tahap | Topik | Class / File |
|---|---|---|
| 1 | Class logika pesanan dan diskon | `Pesanan.java`, `MainKasir.java` |
| 2 | Dua kasir sebagai thread | `Kasir.java`, `MainKasir.java` |
| 3 | Tampilan GUI dengan Java Swing | `KasirFrame.java` |
| 4 | Cetak struk dengan thread baru | `KasirFrame.java` |

---

## 2. Struktur Proyek

```
PraktikumPBO
└── src
    └── main
        └── java
            └── lab
                └── kasir
                    ├── Pesanan.java       (Tahap 1)
                    ├── Kasir.java         (Tahap 2)
                    ├── KasirFrame.java    (Tahap 3 dan 4)
                    └── MainKasir.java     (Tahap 1, 2, dan peluncur GUI)
```

---

## 3. Tahap 1: Class Logika (`Pesanan`)

### Soal

1. Buat class `Pesanan` dengan atribut private `subtotal` (double).
2. Buat method `tambahItem(double harga, int jumlah)` yang menambahkan harga x jumlah ke subtotal, dan `getSubtotal()` untuk membaca nilainya.
3. Buat method `hitungTotal()`: jika subtotal lebih dari 50.000, kembalikan subtotal dikurangi diskon 10 persen. Jika tidak, kembalikan subtotal apa adanya.
4. Buat method `reset()` yang mengembalikan subtotal ke 0.
5. Uji di class `MainKasir` dengan data berikut:

| Menu | Harga | Jumlah |
|---|---|---|
| Nasi Goreng | 15.000 | 2 |
| Es Teh | 5.000 | 3 |
| Ayam Bakar | 25.000 | 1 |

**Hasil yang diharapkan:** setelah dua menu pertama, total 45.000 (tanpa diskon). Setelah menu ketiga, subtotal 70.000 dan total 63.000.

### Kode: `Pesanan.java`

```java
package lab.kasir;

public class Pesanan {
    private double subtotal;

    public void tambahItem(double harga, int jumlah) {
        subtotal += harga * jumlah;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public double hitungTotal() {
        if (subtotal > 50000) {
            return subtotal - (subtotal * 0.10);
        }
        return subtotal;
    }

    public void reset() {
        subtotal = 0;
    }
}
```

### Pembahasan Kode

- **Enkapsulasi.** Atribut `subtotal` dideklarasikan `private`, sehingga tidak bisa diubah langsung dari luar class. Nilainya hanya bisa dibaca lewat `getSubtotal()` dan diubah lewat `tambahItem()` atau `reset()`.
- **`tambahItem(harga, jumlah)`.** Menghitung `harga * jumlah` lalu menambahkannya ke `subtotal` dengan operator `+=`. Setiap pemanggilan menambah subtotal, tidak menimpanya.
- **`hitungTotal()`.** Memakai percabangan `if`. Jika `subtotal > 50000`, hasilnya subtotal dikurangi 10 persen (`subtotal * 0.10`). Jika tidak, subtotal dikembalikan apa adanya. Method ini tidak mengubah nilai `subtotal`, hanya mengembalikan hasil perhitungan.
- **`reset()`.** Mengembalikan `subtotal` ke 0 agar pesanan baru bisa dimulai.

### Kode Pengujian (bagian Tahap 1 pada `MainKasir.java`)

```java
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
```

### Output

```
=== TAHAP 1: Uji Pesanan ===
Subtotal : 45000.0
Total    : 45000.0 (tanpa diskon)
Subtotal : 70000.0
Total    : 63000.0 (diskon 10%)
Setelah reset, subtotal: 0.0
```

![Output Tahap 1 dan 2 (Run 1)](screenshots/01-output-run1.png)

### Verifikasi dengan Hasil yang Diharapkan

| Kondisi | Diharapkan | Hasil Program | Status |
|---|---|---|---|
| Setelah Nasi Goreng dan Es Teh | Total 45.000 (tanpa diskon) | 45000.0 | Sesuai |
| Setelah Ayam Bakar | Subtotal 70.000 | 70000.0 | Sesuai |
| Setelah Ayam Bakar | Total 63.000 | 63000.0 | Sesuai |

Perhitungan: 15.000 x 2 = 30.000; 5.000 x 3 = 15.000; jumlahnya 45.000 (tidak lebih dari 50.000, jadi tanpa diskon). Ditambah 25.000 x 1 = 25.000, subtotal menjadi 70.000 (lebih dari 50.000, jadi diskon 10 persen): 70.000 - 7.000 = **63.000**.

---

## 4. Tahap 2: Dua Kasir sebagai Thread

### Soal

Lengkapi kerangka class `Kasir extends Thread`:

1. Lengkapi `run()` agar mencetak kalimat seperti `Kasir Dina melayani pelanggan ke-1`.
2. Pada `MainKasir`, buat dua kasir (Dina dan Eko) lalu jalankan keduanya dengan `start()`.
3. Jalankan program 2 kali dan catat apakah urutan output selalu sama.
4. Tambahkan `join()` untuk kedua kasir agar `main` mencetak `Semua pelanggan selesai dilayani` setelah keduanya selesai.

**Hasil yang diharapkan:** output Dina dan Eko muncul bergantian (urutannya bisa berbeda tiap dijalankan), dan kalimat `Semua pelanggan selesai dilayani` selalu muncul paling akhir.

### Kode: `Kasir.java`

```java
package lab.kasir;

public class Kasir extends Thread {
    private String nama;

    public Kasir(String nama) {
        this.nama = nama;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            System.out.println("Kasir " + nama + " melayani pelanggan ke-" + i);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println("Kasir " + nama + " terhenti.");
            }
        }
    }
}
```

### Kode Pengujian (bagian Tahap 2 pada `MainKasir.java`)

```java
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
```

### Pembahasan Kode

- **`extends Thread`.** Class `Kasir` mewarisi `Thread`, sehingga setiap objek `Kasir` dapat berjalan sebagai thread tersendiri.
- **`run()`.** Method ini berisi pekerjaan yang dijalankan thread. Perulangan `for` melayani 5 pelanggan, mencetak nama kasir dan nomor pelanggan, lalu `Thread.sleep(500)` menjeda 0,5 detik agar kedua kasir terlihat berjalan bergantian.
- **`start()` bukan `run()`.** Memanggil `start()` membuat thread baru yang menjalankan `run()` secara bersamaan dengan thread lain. Jika `run()` dipanggil langsung, kode hanya berjalan berurutan di thread `main`.
- **`join()`.** Thread `main` menunggu `dina` selesai, lalu menunggu `eko` selesai. Karena itu, kalimat `Semua pelanggan selesai dilayani` baru dicetak setelah kedua kasir selesai, sehingga selalu muncul paling akhir.
- **`try` dan `catch`.** `sleep()` dan `join()` dapat melempar `InterruptedException`, sehingga wajib ditangani.

### Output Run 1

```
=== TAHAP 2: Dua Kasir (Thread) ===
Kasir Dina melayani pelanggan ke-1
Kasir Eko melayani pelanggan ke-1
Kasir Eko melayani pelanggan ke-2
Kasir Dina melayani pelanggan ke-2
Kasir Eko melayani pelanggan ke-3
Kasir Dina melayani pelanggan ke-3
Kasir Eko melayani pelanggan ke-4
Kasir Dina melayani pelanggan ke-4
Kasir Eko melayani pelanggan ke-5
Kasir Dina melayani pelanggan ke-5
Semua pelanggan selesai dilayani
```

![Output Tahap 2 (Run 1)](screenshots/01-output-run1.png)

### Output Run 2

```
=== TAHAP 2: Dua Kasir (Thread) ===
Kasir Dina melayani pelanggan ke-1
Kasir Eko melayani pelanggan ke-1
Kasir Dina melayani pelanggan ke-2
Kasir Eko melayani pelanggan ke-2
Kasir Dina melayani pelanggan ke-3
Kasir Eko melayani pelanggan ke-3
Kasir Dina melayani pelanggan ke-4
Kasir Eko melayani pelanggan ke-4
Kasir Eko melayani pelanggan ke-5
Kasir Dina melayani pelanggan ke-5
Semua pelanggan selesai dilayani
```

![Output Tahap 2 (Run 2)](screenshots/02-output-run2.png)

### Perbandingan Dua Kali Eksekusi

| Pelanggan | Run 1 | Run 2 |
|---|---|---|
| ke-1 | Dina, Eko | Dina, Eko |
| ke-2 | Eko, Dina | Dina, Eko |
| ke-3 | Eko, Dina | Dina, Eko |
| ke-4 | Eko, Dina | Dina, Eko |
| ke-5 | Eko, Dina | Eko, Dina |

### Analisis

Setelah program dijalankan 2 kali, urutan output Kasir Dina dan Kasir Eko **tidak selalu sama**. Hal ini terjadi karena kedua thread berjalan secara bersamaan (concurrent) dan urutan eksekusinya ditentukan oleh penjadwal thread (thread scheduler) JVM, sehingga tidak dapat diprediksi.

Meskipun urutan Dina dan Eko berbeda, kalimat `Semua pelanggan selesai dilayani` **selalu muncul paling akhir** karena `join()` membuat thread `main` menunggu kedua kasir selesai terlebih dahulu.

---

## 5. Tahap 3: Tampilan GUI Kasir

### Soal

Buat jendela Kasir Kantin UNS dengan Java Swing:

1. **Komponen.** Buat semua komponen pada gambar, atur posisinya dengan `setBounds()`, tambahkan ke jendela, lalu tampilkan dengan `setVisible(true)`.
2. **Tombol Tambah.** Ambil isi field harga dan jumlah, panggil `pesanan.tambahItem(...)`, tulis satu baris ke area struk, lalu kosongkan field.
3. **Tombol Hitung Total.** Tampilkan hasil `pesanan.hitungTotal()` pada label total.
4. **Validasi sederhana.** Jika ada field kosong atau harga dan jumlah bukan angka, tampilkan pesan dengan `JOptionPane.showMessageDialog`. Gunakan `try` dan `catch` untuk menangani `NumberFormatException`.

**Hasil yang diharapkan:** dengan data uji Tahap 1, label total menampilkan **63.000** setelah ketiga menu ditambahkan.

### Komponen GUI

| Komponen | Tipe | Fungsi |
|---|---|---|
| `txtMenu` | `JTextField` | Input nama menu |
| `txtHarga` | `JTextField` | Input harga |
| `txtJumlah` | `JTextField` | Input jumlah |
| `btnTambah` | `JButton` | Menambah item ke pesanan |
| `btnHitung` | `JButton` | Menghitung total bayar |
| `areaStruk` | `JTextArea` dalam `JScrollPane` | Menampilkan baris struk |
| `lblTotal` | `JLabel` | Menampilkan total bayar |
| `btnCetak` | `JButton` | Mencetak struk (Tahap 4) |
| `lblStatus` | `JLabel` | Menampilkan status cetak (Tahap 4) |

### Kode: `KasirFrame.java`

Kode berikut sudah mencakup Tahap 3 dan Tahap 4.

```java
package lab.kasir;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import javax.swing.*;

public class KasirFrame extends JFrame {
    private JTextField txtMenu, txtHarga, txtJumlah;
    private JTextArea areaStruk;
    private JLabel lblTotal;
    private JLabel lblStatus;
    private Pesanan pesanan = new Pesanan();

    // Format angka ala Indonesia: 63.000
    private DecimalFormat formatRupiah;

    public KasirFrame() {
        setTitle("Kasir Kantin UNS");
        setSize(400, 450);
        setLayout(null);
        setLocationRelativeTo(null);

        DecimalFormatSymbols simbol = new DecimalFormatSymbols();
        simbol.setGroupingSeparator('.');
        simbol.setDecimalSeparator(',');
        formatRupiah = new DecimalFormat("#,##0", simbol);

        // --- Judul ---
        JLabel lblJudul = new JLabel("Kasir Kantin UNS");
        lblJudul.setFont(lblJudul.getFont().deriveFont(java.awt.Font.BOLD, 16f));
        lblJudul.setBounds(20, 10, 300, 25);
        add(lblJudul);

        // --- Input ---
        JLabel lblMenu = new JLabel("Nama Menu :");
        lblMenu.setBounds(20, 50, 80, 25);
        add(lblMenu);
        txtMenu = new JTextField();
        txtMenu.setBounds(110, 50, 250, 25);
        add(txtMenu);

        JLabel lblHarga = new JLabel("Harga :");
        lblHarga.setBounds(20, 85, 80, 25);
        add(lblHarga);
        txtHarga = new JTextField();
        txtHarga.setBounds(110, 85, 250, 25);
        add(txtHarga);

        JLabel lblJumlah = new JLabel("Jumlah :");
        lblJumlah.setBounds(20, 120, 80, 25);
        add(lblJumlah);
        txtJumlah = new JTextField();
        txtJumlah.setBounds(110, 120, 250, 25);
        add(txtJumlah);

        // --- Tombol ---
        JButton btnTambah = new JButton("Tambah");
        btnTambah.setBounds(40, 160, 130, 30);
        add(btnTambah);

        JButton btnHitung = new JButton("Hitung Total");
        btnHitung.setBounds(200, 160, 130, 30);
        add(btnHitung);

        // --- Area struk ---
        areaStruk = new JTextArea();
        areaStruk.setEditable(false);
        JScrollPane scroll = new JScrollPane(areaStruk);
        scroll.setBounds(20, 205, 345, 130);
        add(scroll);

        // --- Label total ---
        lblTotal = new JLabel("Total Bayar : Rp 0");
        lblTotal.setFont(lblTotal.getFont().deriveFont(java.awt.Font.BOLD, 13f));
        lblTotal.setBounds(20, 345, 345, 25);
        add(lblTotal);

        // --- Tahap 4: tombol cetak + label status ---
        JButton btnCetak = new JButton("Cetak Struk");
        btnCetak.setBounds(20, 378, 130, 30);
        add(btnCetak);

        lblStatus = new JLabel("Status : -");
        lblStatus.setBounds(165, 378, 200, 30);
        add(lblStatus);

        // ===== Aksi tombol Tambah =====
        btnTambah.addActionListener(e -> {
            String menu = txtMenu.getText().trim();
            String strHarga = txtHarga.getText().trim();
            String strJumlah = txtJumlah.getText().trim();

            if (menu.isEmpty() || strHarga.isEmpty() || strJumlah.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Nama menu, harga, dan jumlah tidak boleh kosong!",
                        "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                double harga = Double.parseDouble(strHarga);
                int jumlah = Integer.parseInt(strJumlah);

                if (harga <= 0 || jumlah <= 0) {
                    JOptionPane.showMessageDialog(this,
                            "Harga dan jumlah harus lebih dari 0!",
                            "Peringatan", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                pesanan.tambahItem(harga, jumlah);
                areaStruk.append(menu + "  " + jumlah + " x "
                        + formatRupiah.format(harga) + " = "
                        + formatRupiah.format(harga * jumlah) + "\n");

                txtMenu.setText("");
                txtHarga.setText("");
                txtJumlah.setText("");
                txtMenu.requestFocus();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Harga dan jumlah harus berupa angka!",
                        "Input Tidak Valid", JOptionPane.ERROR_MESSAGE);
            }
        });

        // ===== Aksi tombol Hitung Total =====
        btnHitung.addActionListener(e -> {
            lblTotal.setText("Total Bayar : Rp "
                    + formatRupiah.format(pesanan.hitungTotal()));
        });

        // ===== Aksi tombol Cetak Struk (thread baru) =====
        btnCetak.addActionListener(e -> {
            new Thread(() -> {
                SwingUtilities.invokeLater(() -> lblStatus.setText("Status : Mencetak struk..."));
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                SwingUtilities.invokeLater(() -> lblStatus.setText("Status : Struk selesai"));
            }).start();
        });

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
}
```

### Peluncur GUI pada `MainKasir.java`

```java
SwingUtilities.invokeLater(new Runnable() {
    @Override
    public void run() {
        new KasirFrame();
    }
});
```

### Pembahasan Kode

- **`extends JFrame` dan `setLayout(null)`.** Jendela dibuat dengan mewarisi `JFrame`. Layout `null` membuat posisi tiap komponen diatur manual dengan `setBounds(x, y, lebar, tinggi)`, sesuai perintah soal.
- **Komponen.** Tiga `JTextField` untuk input, tiga `JLabel` sebagai keterangan, dua `JButton` (Tambah dan Hitung Total), `JTextArea` yang dibungkus `JScrollPane` agar bisa di-scroll, dan `JLabel` untuk total. Area struk dibuat `setEditable(false)` supaya isinya tidak bisa diketik manual.
- **Tombol Tambah.** Mengambil teks dari field, memvalidasinya, memanggil `pesanan.tambahItem(harga, jumlah)`, menulis satu baris ke area struk, lalu mengosongkan ketiga field dan mengembalikan fokus ke `txtMenu`.
- **Tombol Hitung Total.** Memanggil `pesanan.hitungTotal()` dan menampilkan hasilnya pada `lblTotal`.
- **Format rupiah.** `DecimalFormat` dengan pemisah ribuan titik membuat angka tampil sebagai `63.000`, bukan `63000.0`.
- **Validasi.**
  - Jika ada field kosong, tampil dialog peringatan dengan `JOptionPane.showMessageDialog` dan proses dihentikan dengan `return`.
  - `Double.parseDouble()` dan `Integer.parseInt()` melempar `NumberFormatException` bila teks bukan angka. Pengecualian ini ditangkap oleh `catch` yang menampilkan dialog error, sehingga program tidak berhenti.
  - Harga dan jumlah juga dicek agar lebih dari 0.
- **`SwingUtilities.invokeLater`.** Memastikan jendela dibuat di Event Dispatch Thread, sesuai praktik yang disarankan untuk Swing.

### Tampilan Awal GUI

![Tampilan awal GUI](screenshots/03-gui-awal.png)

### Pengujian: Tiga Menu dan Hitung Total

Data uji yang dimasukkan satu per satu: Nasi Goreng (15000 x 2), Es Teh (5000 x 3), Ayam Bakar (25000 x 1), lalu klik Hitung Total.

![GUI setelah tiga menu ditambahkan dan total dihitung](screenshots/04-gui-total.png)

Label menampilkan **Total Bayar : Rp 63.000**, sesuai hasil yang diharapkan.

### Pengujian Validasi

**Field kosong:** semua field dikosongkan, lalu klik Tambah.

![Dialog peringatan field kosong](screenshots/05-validasi-kosong.png)

**Harga bukan angka:** Nama Menu `Teh`, Harga `abc`, Jumlah `2`, lalu klik Tambah.

![Dialog error input bukan angka](screenshots/06-validasi-angka.png)

### Verifikasi

| Pengujian | Diharapkan | Hasil | Status |
|---|---|---|---|
| Tiga menu ditambahkan, klik Hitung Total | Total 63.000 | Total Bayar : Rp 63.000 | Sesuai |
| Field kosong | Dialog peringatan | Dialog "tidak boleh kosong" | Sesuai |
| Harga bukan angka | Dialog pesan kesalahan | Dialog "harus berupa angka" | Sesuai |

---

## 6. Tahap 4: Tombol Cetak Struk dengan Thread

### Soal

Tambahkan tombol Cetak Struk dan sebuah label status di bawah jendela. Saat tombol ditekan, tampilkan `Mencetak struk...`, tunggu 2 detik, lalu tampilkan `Struk selesai`. Penantian 2 detik dilakukan di thread baru supaya jendela tidak macet.

Kriteria:

1. **Thread berjalan.** Tombol menjalankan thread baru dengan `start()`.
2. **Jendela tidak macet.** Selama 2 detik menunggu, tombol Tambah dan Hitung Total masih bisa ditekan.
3. **Pesan status benar.** Label berubah dari mencetak menjadi selesai.

### Kode (bagian dari `KasirFrame.java`)

```java
JButton btnCetak = new JButton("Cetak Struk");
btnCetak.setBounds(20, 378, 130, 30);
add(btnCetak);

lblStatus = new JLabel("Status : -");
lblStatus.setBounds(165, 378, 200, 30);
add(lblStatus);

btnCetak.addActionListener(e -> {
    new Thread(() -> {
        SwingUtilities.invokeLater(() -> lblStatus.setText("Status : Mencetak struk..."));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        SwingUtilities.invokeLater(() -> lblStatus.setText("Status : Struk selesai"));
    }).start();
});
```

### Pembahasan Kode

- **Mengapa memakai thread baru?** Event listener tombol berjalan di Event Dispatch Thread (EDT), yaitu thread yang mengurus tampilan dan respons klik. Jika `Thread.sleep(2000)` dipanggil langsung di listener, EDT tertahan selama 2 detik dan jendela menjadi macet (tombol tidak merespons dan tampilan tidak diperbarui). Dengan membuat `new Thread(...)` dan memanggil `start()`, penantian terjadi di thread terpisah sehingga EDT tetap bebas melayani klik.
- **`SwingUtilities.invokeLater`.** Komponen Swing tidak aman diubah dari thread selain EDT. Karena itu, perubahan teks label dari thread cetak dibungkus `invokeLater` agar dijalankan di EDT.
- **Alur status.** Label diubah menjadi "Mencetak struk...", thread tidur 2 detik, lalu label diubah menjadi "Struk selesai".
- **`InterruptedException`.** `Thread.sleep()` wajib ditangani. Pada `catch` dipanggil `Thread.currentThread().interrupt()` untuk mempertahankan status interupsi thread.

### Pengujian

**Status "Mencetak struk..."** tepat setelah tombol Cetak Struk ditekan:

![Status Mencetak struk...](screenshots/07-status-mencetak.png)

**Status "Struk selesai"** setelah 2 detik:

![Status Struk selesai](screenshots/08-status-selesai.png)

**Jendela tidak macet.** Selama status masih "Mencetak struk...", field diisi dan tombol Tambah/Hitung Total masih dapat ditekan:

![Jendela tetap responsif saat mencetak](screenshots/09-tidak-macet.png)

### Verifikasi

| Kriteria | Hasil | Status |
|---|---|---|
| Thread berjalan | Tombol menjalankan `new Thread(...).start()` | Sesuai |
| Jendela tidak macet | Tombol Tambah dan Hitung Total tetap merespons selama 2 detik | Sesuai |
| Pesan status benar | "Mencetak struk..." berubah menjadi "Struk selesai" | Sesuai |

---

## 7. Cara Menjalankan

1. Buka proyek `PraktikumPBO` di NetBeans.
2. Pastikan keempat file berada di package `lab.kasir`: `Pesanan.java`, `Kasir.java`, `KasirFrame.java`, dan `MainKasir.java`.
3. Klik kanan **`MainKasir.java`**, lalu pilih **Run File** (Shift+F6). Hanya `MainKasir` yang memiliki method `main()`.
4. Output Tahap 1 dan 2 tampil di panel Output. Setelah itu jendela GUI Kasir Kantin UNS muncul untuk Tahap 3 dan 4.
5. Tutup jendela GUI untuk mengakhiri program.

---

## 8. Kesimpulan

1. **Enkapsulasi.** Class `Pesanan` menyimpan `subtotal` sebagai atribut private yang diakses lewat method, sehingga logika diskon terpusat dan aman.
2. **Multithreading.** Dengan `extends Thread`, `start()`, dan `join()`, dua kasir dapat berjalan bersamaan. Urutan output bersifat non-deterministik karena ditentukan oleh thread scheduler, tetapi `join()` menjamin thread `main` menunggu semua kasir selesai.
3. **GUI dengan Swing.** Komponen disusun dengan `setBounds()`, dan interaksi pengguna ditangani oleh `ActionListener`. Validasi input memakai `try`-`catch` untuk `NumberFormatException` dan `JOptionPane` untuk pesan ke pengguna.
4. **Responsivitas GUI.** Pekerjaan yang memakan waktu (menunggu 2 detik) dijalankan di thread terpisah agar Event Dispatch Thread tidak terblokir, dan pembaruan komponen Swing dari thread lain dilakukan lewat `SwingUtilities.invokeLater`.
