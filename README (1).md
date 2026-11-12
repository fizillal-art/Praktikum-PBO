# Program Pengelola Nilai Siswa

Program Java procedural sederhana untuk mengelola data nilai siswa. Program membaca data dari konsol (keyboard), menuliskannya ke file teks `laporan_nilai.txt`, lalu membaca kembali isi file tersebut dan menampilkannya di konsol.

## Fitur

- Input jumlah siswa, nama (`String`), dan nilai (`double`) lewat `Scanner`.
- Penentuan status kelulusan langsung di dalam loop:
  - Nilai >= 70 -> **Lulus**
  - Nilai < 70 -> **Tidak Lulus**
- Penulisan data ke `laporan_nilai.txt` memakai `FileWriter` dan `BufferedWriter`.
- Ringkasan di akhir file: rata-rata nilai kelas, jumlah siswa lulus, dan jumlah siswa tidak lulus.
- Pembacaan ulang file memakai `FileReader` dan `BufferedReader` (`readLine()` sampai `null`), lalu isinya ditampilkan ke konsol.
- Penanganan error dengan `try-with-resources` dan `catch (IOException)`, sehingga writer dan reader otomatis ditutup.
- Validasi input: jumlah siswa harus bilangan bulat > 0 dan nilai harus berada di rentang 0-100. Jika tidak valid, program meminta input ulang.

## Struktur Program

| Method | Fungsi |
|---|---|
| `main()` | Alur utama: input jumlah siswa, tulis file, baca file |
| `bacaJumlahSiswa()` | Membaca dan memvalidasi jumlah siswa |
| `bacaNilai()` | Membaca dan memvalidasi nilai (0-100) |
| `tentukanStatus()` | Menentukan status Lulus / Tidak Lulus |
| `tulisLaporan()` | Menulis data siswa dan ringkasan ke file |
| `bacaLaporan()` | Membaca file dan menampilkannya ke konsol |

## Cara Menjalankan

### NetBeans

1. Pastikan file bernama `LaporanNilaiSiswa.java` dan baris pertama sesuai nama package (`package TugasP6;`).
2. Klik kanan file, pilih **Run File** (Shift+F6).
3. Ketik input di jendela **Output** pada bagian bawah.

### Command Line

Dari folder yang berisi file `LaporanNilaiSiswa.java` (tanpa deklarasi package):

```
javac LaporanNilaiSiswa.java
java LaporanNilaiSiswa
```

Jika file memakai `package TugasP6;`, jalankan dari folder induk package:

```
javac -d out TugasP6/LaporanNilaiSiswa.java
java -cp out TugasP6.LaporanNilaiSiswa
```

File `laporan_nilai.txt` dibuat di direktori tempat program dijalankan (pada project NetBeans/Maven, yaitu folder root project).

## Contoh Penggunaan

Input:

```
Masukkan jumlah siswa: 3

Data siswa ke-1
  Nama siswa: Budi
  Nilai siswa ke-1 (0-100): 85

Data siswa ke-2
  Nama siswa: Siti
  Nilai siswa ke-2 (0-100): 60.5

Data siswa ke-3
  Nama siswa: Andi
  Nilai siswa ke-3 (0-100): 70
```

Isi `laporan_nilai.txt` (juga ditampilkan di konsol):

```
=== LAPORAN NILAI SISWA ===
No   Nama                      Nilai    Status
-------------------------------------------------------
1    Budi                      85.00    Lulus
2    Siti                      60.50    Tidak Lulus
3    Andi                      70.00    Lulus
-------------------------------------------------------
RINGKASAN
Rata-rata nilai kelas : 71.83
Jumlah siswa lulus    : 2
Jumlah tidak lulus    : 1
```

## Catatan

- Nama file Java harus sama persis dengan nama class publik: `LaporanNilaiSiswa.java`.
- Batas kelulusan disimpan di konstanta `BATAS_LULUS` (70.0) dan nama file output di konstanta `NAMA_FILE`, sehingga mudah diubah.
