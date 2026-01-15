package Tampilan;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

public class MenuUtama extends JFrame {

    Color colorPrimary = Color.decode("#2C3E50");
    Color colorSecondary = Color.decode("#34495E");
    Color colorAccent = Color.decode("#3498DB");
    Color colorExit = Color.decode("#E74C3C");

    public MenuUtama() {
        setTitle("Sistem Informasi Bengkel Maju Jaya - Dashboard");
        setExtendedState(JFrame.MAXIMIZED_BOTH); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // --- HEADER ---
        JPanel panelHeader = new JPanel(new BorderLayout());
        panelHeader.setBackground(colorPrimary);
        panelHeader.setPreferredSize(new Dimension(100, 80));

        JLabel lblJudul = new JLabel("SISTEM MANAJEMEN BENGKEL", SwingConstants.CENTER);
        lblJudul.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblJudul.setForeground(Color.WHITE);
        panelHeader.add(lblJudul, BorderLayout.CENTER);
        add(panelHeader, BorderLayout.NORTH);

        // --- PANEL MENU ---
        JPanel panelMenu = new JPanel();
        // UBAH GRID JADI 3 BARIS, 3 KOLOM BIAR MUAT BANYAK
        panelMenu.setLayout(new GridLayout(3, 3, 20, 20)); 
        panelMenu.setBackground(Color.WHITE);
        panelMenu.setBorder(BorderFactory.createEmptyBorder(30, 100, 30, 100));

        // 1. Pelanggan
        JButton btnPelanggan = buatTombolMenu("MASTER PELANGGAN", "Data Customer");
        btnPelanggan.addActionListener(e -> new FormPelanggan().setVisible(true));
        panelMenu.add(btnPelanggan);
        
        // 2. Kendaraan
        JButton btnKendaraan = buatTombolMenu("MASTER KENDARAAN", "Data Mobil/Motor");
        btnKendaraan.addActionListener(e -> new FormKendaraan().setVisible(true));
        panelMenu.add(btnKendaraan);
        
        // 3. Mekanik
        JButton btnMekanik = buatTombolMenu("MASTER MEKANIK", "Data Montir");
        btnMekanik.addActionListener(e -> new FormMekanik().setVisible(true));
        panelMenu.add(btnMekanik);
        
        // 4. Sparepart
        JButton btnPart = buatTombolMenu("MASTER SPAREPART", "Stok Barang");
        btnPart.addActionListener(e -> new FormSparepart().setVisible(true));
        panelMenu.add(btnPart);
        
        // 5. Jasa
        JButton btnJasa = buatTombolMenu("MASTER JASA", "Ongkos Service");
        btnJasa.addActionListener(e -> new FormJasa().setVisible(true));
        panelMenu.add(btnJasa);
        
        // 6. SUPPLIER (BARU)
        JButton btnSupplier = buatTombolMenu("MASTER SUPPLIER", "Data Pemasok");
        btnSupplier.addActionListener(e -> new FormSupplier().setVisible(true));
        panelMenu.add(btnSupplier);
        
        // 7. Transaksi
        JButton btnTransaksi = buatTombolMenu("TRANSAKSI SERVICE", "Kasir");
        btnTransaksi.setBackground(colorAccent); 
        btnTransaksi.addActionListener(e -> new FormTransaksi().setVisible(true));
        panelMenu.add(btnTransaksi);

        // 8 & 9. Tombol Kosong (Placeholder biar rapi) atau Tombol Laporan nanti
        // Kita biarkan kosong atau isi dengan label kosong
        panelMenu.add(new JLabel("")); 

        // Tombol Keluar
        JButton btnKeluar = buatTombolMenu("KELUAR", "Tutup Aplikasi");
        btnKeluar.setBackground(colorExit);
        btnKeluar.addActionListener(e -> {
             if (JOptionPane.showConfirmDialog(null, "Keluar?", "Konfirmasi", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) System.exit(0);
        });
        panelMenu.add(btnKeluar);

        add(panelMenu, BorderLayout.CENTER);
        setVisible(true);
    }

    private JButton buatTombolMenu(String judul, String sub) {
        JButton btn = new JButton("<html><center><span style='font-size:16px; font-weight:bold;'>" + judul + "</span><br/><span style='font-size:11px;'>" + sub + "</span></center></html>");
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.setBackground(colorSecondary);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createRaisedBevelBorder());
        return btn;
    }

    public static void main(String[] args) {
        try { for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) if ("Nimbus".equals(info.getName())) UIManager.setLookAndFeel(info.getClassName()); } catch (Exception e) {}
        new MenuUtama();
    }
}