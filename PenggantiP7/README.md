# Program Pengelola Nilai Mahasiswa (OOP)

Program Java berorientasi objek untuk mengelola data nilai mahasiswa. Program membaca input dari pengguna, memvalidasi data dengan Assertion dan Custom Exception, menyimpan objek dalam `ArrayList`, mengolah `String`/`Character`, lalu menyimpan rekapitulasi dan statistik kelas ke file teks `laporan_nilai.txt`.

## Pemetaan Materi

| Materi | Implementasi |
|---|---|
| Package, Class, Constructor, `final` | Package `model` dan `app`; class `Mahasiswa` dengan constructor; konstanta `FINAL_PASSING_GRADE = 70.0` |
| Kondisional, Looping, Primitive & Reference Type | `if-else` untuk grade huruf dan status kelulusan; for-each untuk menghitung rata-rata; `double`/`boolean` (primitif) dan `Mahasiswa`/`String` (reference) |
| Exception, Assertion, Character, String | `NilaiInvalidException`; `assert !nim.isEmpty()`; `trim()`, `toUpperCase()`, `Character.isDigit()` |
| Array & Collection | `ArrayList<Mahasiswa>` dengan `add()`, `size()`, dan for-each |
| Input/Output & File | `Scanner` untuk console; `BufferedWriter` + `FileWriter` dalam try-with-resources untuk file |

## Struktur Project

```
src/main/java/
├── model/
│   ├── Mahasiswa.java
│   └── NilaiInvalidException.java
└── app/
    └── LaporanNilaiApp.java
```

| File | Peran |
|---|---|
| `model/Mahasiswa.java` | Kelas data: `nama`, `nim`, `nilai`; validasi NIM; penentuan grade dan status |
| `model/NilaiInvalidException.java` | Exception khusus, dilempar jika nilai < 0 atau > 100 |
| `app/LaporanNilaiApp.java` | Kelas utama: input console, pembuatan laporan, penulisan file |

Karena berbeda package, kelas dan method di `model` yang dipakai dari `app` dideklarasikan `public`, dan `app` meng-import `model.Mahasiswa` serta `model.NilaiInvalidException`.

## Aturan Grade dan Kelulusan

| Nilai | Grade | Status |
|---|---|---|
| >= 85 | A | Lulus |
| >= 75 | B | Lulus |
| >= 70 | C | Lulus |
| < 70 | D | Tidak Lulus |

Batas kelulusan memakai konstanta `Mahasiswa.FINAL_PASSING_GRADE` (70.0).

## Validasi dan Penanganan Error

| Kasus | Penanganan |
|---|---|
| Nilai < 0 atau > 100 | `NilaiInvalidException` dilempar oleh constructor, ditangkap, lalu nilai diminta ulang |
| Nilai bukan angka | `NumberFormatException` ditangkap, input diminta ulang |
| NIM kosong atau mengandung huruf | Ditolak oleh `isNimValid()` (memakai `Character.isDigit()`) |
| NIM kosong di constructor | `assert !nim.isEmpty() : "NIM tidak boleh kosong!"` (aktif dengan `-ea`) |
| NIM ganda | Ditolak setelah penelusuran `ArrayList` |
| Gagal menulis file | `IOException` ditangkap di `catch`; writer ditutup otomatis oleh try-with-resources |

> **Catatan Assertion:** secara default Java menonaktifkan `assert`. Agar aktif, jalankan dengan opsi `-ea` (di NetBeans: *Project Properties → Run → VM Options*).

## Cara Menjalankan

### NetBeans

1. Buat package `model` dan `app`, lalu letakkan file sesuai struktur di atas.
2. Klik kanan `LaporanNilaiApp.java`, pilih **Run File** (Shift+F6).
3. Ketik input di jendela **Output**.
4. File `laporan_nilai.txt` dibuat di folder root project (lihat lewat tab **Files**).

### Command Line

Dari folder yang berisi `model/` dan `app/`:

```
javac -d out model/*.java app/*.java
java -ea -cp out app.LaporanNilaiApp
```

## Contoh Output

Isi `laporan_nilai.txt` (juga ditampilkan di konsol):

```
=== LAPORAN NILAI MAHASISWA ===
No   NIM          Nama                      Nilai    Grade  Status
------------------------------------------------------------------------
1    0325         JEKI                      99.00    A      Lulus
2    0324         FEBRI                     80.00    B      Lulus
------------------------------------------------------------------------
STATISTIK KELAS
Jumlah mahasiswa      : 2
Rata-rata nilai kelas : 89.50
Jumlah lulus          : 2
Jumlah tidak lulus    : 0
```

Rata-rata dihitung dari (99 + 80) / 2 = 89,5.

Contoh penanganan input yang salah:

```
  NIM: L123
  NIM harus diisi dan hanya boleh berisi angka.
  Nilai (0-100): 150
  Error: Nilai tidak valid: 150.0 (harus antara 0 dan 100)
  Nilai (0-100): abc
  Input tidak valid, masukkan angka.
```

## Catatan

- Nama dirapikan otomatis: spasi di awal/akhir dihapus dan huruf diubah menjadi kapital.
- Koma desimal (misalnya `65,5`) diterima sebagai nilai.
- File `laporan_nilai.txt` ditimpa setiap program dijalankan.
