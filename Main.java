package TugasP2;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Masukkan jumlah siswa: ");
        int jumlahSiswa = scanner.nextInt();
        scanner.nextLine();

        Student[] students = new Student[jumlahSiswa];

        for (int i = 0; i < jumlahSiswa; i++) {
            System.out.println("\nData siswa ke-" + (i + 1) + ":");
            
            System.out.print("Masukkan nama: ");
            String name = scanner.nextLine();
            
            System.out.print("Masukkan nilai: ");
            double score = scanner.nextDouble();
            scanner.nextLine();

            students[i] = new Student(name, score);
            students[i].checkPassed();

            String status = students[i].passed ? "Lulus" : "Tidak Lulus";
            System.out.println("Siswa: " + students[i].name + " - Nilai: " + students[i].score + " - " + status);
        }

        double totalScore = 0;
        int jumlahLulus = 0;
        int jumlahTidakLulus = 0;

        for (int i = 0; i < jumlahSiswa; i++) {
            totalScore += students[i].score;
            
            if (students[i].passed) {
                jumlahLulus++;
            } else {
                jumlahTidakLulus++;
            }
        }

        double average = totalScore / jumlahSiswa;

        System.out.println("\n==================================");
        System.out.printf("Rata-rata nilai kelas: %.2f\n", average);
        System.out.println("Jumlah siswa lulus: " + jumlahLulus);
        System.out.println("Jumlah siswa tidak lulus: " + jumlahTidakLulus);

        scanner.close();
    }
}
