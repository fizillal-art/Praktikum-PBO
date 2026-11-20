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