# Program Pengelola Nilai Siswa

Program Java procedural sederhana untuk mengelola data nilai siswa. Program membaca data dari konsol (keyboard), menuliskannya ke file teks `laporan_nilai.txt`, lalu membaca kembali isi file tersebut dan menampilkannya di konsol.

Program ini dibuat sebagai bagian dari project Maven `PraktikumPBO` (package `TugasP6`) dan dijalankan di Apache NetBeans.

## Fitur

- Input jumlah siswa, nama (`String`), dan nilai (`double`) lewat `Scanner`.
- Penentuan status kelulusan langsung di dalam loop:
  - Nilai >= 70 -> **Lulus**
  - Nilai < 70 -> **Tidak Lulus**
- Penulisan data ke `laporan_nilai.txt` memakai `FileWriter` dan `BufferedWriter`.
- Ringkasan di akhir file: rata-rata nilai kelas, jumlah siswa lulus, dan jumlah siswa tidak lulus.
- Pembacaan ulang file memakai `FileReader` dan `BufferedReader` (`readLine()` sampai `null`), lalu isinya ditampilkan ke konsol.
- Penanganan error dengan `try-with-resources` dan `catch (IOException)`, sehingga writer dan reader otomatis ditutup.
- Validasi input: jumlah siswa harus bilangan bulat > 0 dan nilai harus berada di rentang 0-100. Jika tidak valid, program meminta input ulang. Koma desimal (misalnya `60,5`) juga diterima.

## Struktur Project

```
PraktikumPBO/
├── pom.xml
├── src/main/java/TugasP6/
│   └── LaporanNilaiSiswa.java
└── laporan_nilai.txt        (dibuat otomatis saat program dijalankan)
```

## Struktur Program

| Method | Fungsi |
|---|---|
| `main()` | Alur utama: input jumlah siswa, tulis file, baca file |
| `bacaJumlahSiswa()` | Membaca dan memvalidasi jumlah siswa |
| `bacaNilai()` | Membaca dan memvalidasi nilai (0-100) |
| `tentukanStatus()` | Menentukan status Lulus / Tidak Lulus |
| `tulisLaporan()` | Menulis data siswa dan ringkasan ke file |
| `bacaLaporan()` | Membaca file dan menampilkannya ke konsol |

Konstanta yang dipakai: `NAMA_FILE` (`laporan_nilai.txt`) dan `BATAS_LULUS` (`70.0`).

## Cara Menjalankan

### NetBeans

1. Pastikan file bernama `LaporanNilaiSiswa.java` dan baris pertamanya `package TugasP6;`.
2. Klik kanan file, pilih **Run File** (Shift+F6).
3. Ketik input di jendela **Output** pada bagian bawah.
4. Hasil file `laporan_nilai.txt` dapat dilihat lewat tab **Files** di folder root project.

### Command Line

Dari folder root project:

```
javac -d out src/main/java/TugasP6/LaporanNilaiSiswa.java
java -cp out TugasP6.LaporanNilaiSiswa
```

File `laporan_nilai.txt` dibuat di direktori tempat program dijalankan.

## Contoh Penggunaan

Input:

```
Masukkan jumlah siswa: 3

Data siswa ke-1
  Nama siswa: Jeki
  Nilai siswa ke-1 (0-100): 99

Data siswa ke-2
  Nama siswa: Febri
  Nilai siswa ke-2 (0-100): 80

Data siswa ke-3
  Nama siswa: Hida
  Nilai siswa ke-3 (0-100): 60.5
```

Isi `laporan_nilai.txt` (juga ditampilkan di konsol):

```
=== LAPORAN NILAI SISWA ===
No   Nama                      Nilai    Status
-------------------------------------------------------
1    Jeki                      99.00    Lulus
2    Febri                     80.00    Lulus
3    Hida                      60.50    Tidak Lulus
-------------------------------------------------------
RINGKASAN
Rata-rata nilai kelas : 79.83
Jumlah siswa lulus    : 2
Jumlah tidak lulus    : 1
```

Rata-rata dihitung dari (99 + 80 + 60,5) / 3 = 79,83.

## Catatan

- Nama file Java harus sama persis dengan nama class publik: `LaporanNilaiSiswa.java`.
- Nilai tepat 70 dinyatakan **Lulus** karena batasnya `>= 70`.
- Jika file `laporan_nilai.txt` sudah ada, isinya akan ditimpa setiap program dijalankan.
