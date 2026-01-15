package Tampilan;

import database.Koneksi;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class FormPelanggan extends JFrame {

    // Komponen GUI
    JTextField txtId, txtNama, txtHp, txtAlamat;
    JButton btnSimpan, btnUbah, btnHapus, btnReset;
    JButton btnLanjut; // Tombol Navigasi
    
    JTable tabelPelanggan;
    DefaultTableModel model;

    // Palet Warna (Sesuai Tema Dashboard)
    Color colorPrimary = Color.decode("#2C3E50");
    Color colorBackground = Color.WHITE;
    Color colorSuccess = Color.decode("#27AE60");
    Color colorWarning = Color.decode("#F39C12");
    Color colorDanger = Color.decode("#E74C3C");
    Color colorNeutral = Color.decode("#7F8C8D");
    Color colorInfo = Color.decode("#3498DB"); 

    public FormPelanggan() {
        setTitle("Aplikasi Bengkel - Master Pelanggan");
        setSize(900, 650);
        setMinimumSize(new Dimension(700, 500));
        setLocationRelativeTo(null);
        
        // PERBAIKAN PENTING: Pakai DISPOSE agar Menu Utama tidak ikut tertutup
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        
        setLayout(new BorderLayout());

        // --- 1. PANEL ATAS (FORM INPUT) ---
        JPanel panelAtas = new JPanel();
        panelAtas.setLayout(null); 
        panelAtas.setPreferredSize(new Dimension(800, 260)); 
        panelAtas.setBackground(colorBackground); 
        
        // Header Biru
        JPanel header = new JPanel();
        header.setBounds(0, 0, 3000, 70); 
        header.setBackground(colorPrimary);
        header.setLayout(null);
        
        JLabel judul = new JLabel("DATA PELANGGAN");
        judul.setFont(new Font("Segoe UI", Font.BOLD, 24));
        judul.setForeground(Color.WHITE);
        judul.setBounds(30, 20, 400, 30);
        header.add(judul);
        panelAtas.add(header);

        // Label & Input
        buatLabel(panelAtas, "Nama Lengkap", 50, 100);
        buatLabel(panelAtas, "Nomor HP", 50, 150);
        buatLabel(panelAtas, "Alamat", 50, 200);

        txtId = new JTextField(); txtId.setBounds(0, 0, 0, 0); panelAtas.add(txtId); // ID Tersembunyi
        txtNama = buatTextField(180, 100); panelAtas.add(txtNama);
        txtHp = buatTextField(180, 150); panelAtas.add(txtHp);
        txtAlamat = buatTextField(180, 200); panelAtas.add(txtAlamat);

        add(panelAtas, BorderLayout.NORTH);

        // --- 2. PANEL BAWAH (TOMBOL) ---
        JPanel panelBawah = new JPanel();
        panelBawah.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20)); 
        panelBawah.setBackground(colorBackground);
        
        btnSimpan = buatTombol("Simpan", colorSuccess);
        btnUbah = buatTombol("Ubah", colorWarning);
        btnHapus = buatTombol("Hapus", colorDanger);
        btnReset = buatTombol("Reset", colorNeutral);
        
        // Tombol Lanjut ke Kendaraan
        btnLanjut = buatTombol("Lanjut ke Kendaraan >>", colorInfo);
        btnLanjut.setPreferredSize(new Dimension(200, 45)); 
        btnLanjut.setEnabled(false); 
        
        panelBawah.add(btnSimpan);
        panelBawah.add(btnUbah);
        panelBawah.add(btnHapus);
        panelBawah.add(btnReset);
        panelBawah.add(btnLanjut); 

        add(panelBawah, BorderLayout.SOUTH);

        // --- 3. PANEL TENGAH (TABEL) ---
        String[] judulTabel = {"ID", "Nama Pelanggan", "No HP", "Alamat"};
        model = new DefaultTableModel(judulTabel, 0);
        tabelPelanggan = new JTable(model);
        
        // Styling Header Tabel
        tabelPelanggan.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel l = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                l.setBackground(colorPrimary);
                l.setForeground(Color.WHITE);
                l.setFont(new Font("Segoe UI", Font.BOLD, 14));
                l.setHorizontalAlignment(JLabel.CENTER);
                l.setOpaque(true);
                l.setBorder(new LineBorder(Color.WHITE, 1)); 
                return l;
            }
        });

        tabelPelanggan.setRowHeight(30);
        tabelPelanggan.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tabelPelanggan.setShowVerticalLines(false);
        tabelPelanggan.setGridColor(new Color(230, 230, 230));
        tabelPelanggan.setSelectionBackground(new Color(52, 152, 219));
        tabelPelanggan.setSelectionForeground(Color.WHITE);
        
        JScrollPane scrollPane = new JScrollPane(tabelPelanggan);
        scrollPane.setBorder(new EmptyBorder(0, 30, 0, 30)); 
        scrollPane.setBackground(colorBackground);
        scrollPane.getViewport().setBackground(colorBackground);
        
        add(scrollPane, BorderLayout.CENTER);

        // --- 4. LOGIC EVENT LISTENER ---
        tabelPelanggan.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int baris = tabelPelanggan.getSelectedRow();
                txtId.setText(model.getValueAt(baris, 0).toString());
                txtNama.setText(model.getValueAt(baris, 1).toString());
                txtHp.setText(model.getValueAt(baris, 2).toString());
                txtAlamat.setText(model.getValueAt(baris, 3).toString());
                
                aturTombol(false); // Mode Edit Aktif
                btnLanjut.setEnabled(true); 
            }
        });

        btnSimpan.addActionListener(e -> simpanData());
        btnUbah.addActionListener(e -> ubahData());
        btnHapus.addActionListener(e -> hapusData()); // Ini yang diperbaiki
        btnReset.addActionListener(e -> kosongkanForm());
        
        // Logic Tombol Lanjut
        btnLanjut.addActionListener(e -> {
            String id = txtId.getText();
            String nama = txtNama.getText();
            FormKendaraan formK = new FormKendaraan();
            formK.setVisible(true);
            formK.pilihPelangganOtomatis(id, nama);
            // dispose(); // Opsional: tutup form ini jika mau
        });

        tampilkanData();
        kosongkanForm();
        setVisible(true);
    }

    // --- HELPER METHODS UI ---
    private void buatLabel(JPanel panel, String text, int x, int y) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, 150, 35);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(new Color(50, 50, 50)); 
        panel.add(label);
    }
    
    private JTextField buatTextField(int x, int y) {
        JTextField field = new JTextField();
        field.setBounds(x, y, 500, 35);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(new LineBorder(new Color(180, 180, 180), 1)); 
        field.setMargin(new Insets(0, 10, 0, 0)); 
        return field;
    }
    
    private JButton buatTombol(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setPreferredSize(new Dimension(130, 45));
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false);
        UIManager.put("Button.disabledText", new Color(150, 150, 150));
        return btn;
    }
    
    private void aturTombol(boolean modeSimpan) {
        btnSimpan.setEnabled(modeSimpan);
        btnUbah.setEnabled(!modeSimpan);
        btnHapus.setEnabled(!modeSimpan);
    }

    private void kosongkanForm() {
        txtId.setText(""); txtNama.setText(""); txtHp.setText(""); txtAlamat.setText("");
        txtNama.requestFocus();
        aturTombol(true);
        btnLanjut.setEnabled(false); 
        tabelPelanggan.clearSelection();
    }

    // --- LOGIC DATABASE (CRUD) ---

    private void tampilkanData() {
        int row = model.getRowCount();
        for (int i = 0; i < row; i++) { model.removeRow(0); }
        try {
            Connection conn = Koneksi.getKoneksi();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM pelanggan");
            while (rs.next()) {
                model.addRow(new Object[]{ rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4) });
            }
        } catch (Exception e) { System.err.println("Error: " + e.getMessage()); }
    }

    private void simpanData() {
        try {
            String sql = "INSERT INTO pelanggan (nama, no_hp, alamat) VALUES (?, ?, ?)";
            Connection conn = Koneksi.getKoneksi();
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, txtNama.getText()); stat.setString(2, txtHp.getText()); stat.setString(3, txtAlamat.getText());
            stat.executeUpdate(); JOptionPane.showMessageDialog(null, "Data Tersimpan!");
            tampilkanData(); kosongkanForm();
        } catch (Exception e) { JOptionPane.showMessageDialog(null, "Gagal: " + e.getMessage()); }
    }

    private void ubahData() {
        try {
            String sql = "UPDATE pelanggan SET nama=?, no_hp=?, alamat=? WHERE id_pelanggan=?";
            Connection conn = Koneksi.getKoneksi();
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, txtNama.getText()); stat.setString(2, txtHp.getText()); stat.setString(3, txtAlamat.getText()); stat.setString(4, txtId.getText());
            stat.executeUpdate(); JOptionPane.showMessageDialog(null, "Data Diupdate!");
            tampilkanData(); kosongkanForm();
        } catch (Exception e) { JOptionPane.showMessageDialog(null, "Gagal: " + e.getMessage()); }
    }

    // --- FITUR HAPUS PAKSA (MANUAL CASCADE) ---
    private void hapusData() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Pilih data dulu!"); return;
        }

        // Peringatan Keras
        int confirm = JOptionPane.showConfirmDialog(null, 
                "PERINGATAN: Pelanggan ini mungkin memiliki Data Kendaraan & Service.\n" +
                "Jika dihapus, SEMUA data mobil & riwayat service-nya juga akan HILANG.\n\n" +
                "Yakin tetap hapus?", 
                "Hapus Paksa", 
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            Connection conn = null;
            try {
                conn = Koneksi.getKoneksi();
                // 1. Matikan Auto Commit (Biar aman)
                conn.setAutoCommit(false); 

                // 2. HAPUS KENDARAANNYA DULU (Otomatis service yg nyantol di kendaraan jg harusnya kena, 
                // tapi kalau database masih strict, kita harusnya hapus service detail dulu.
                // UNTUK KASUS INI, KITA COBA HAPUS KENDARAAN DULU).
                
                String sqlKendaraan = "DELETE FROM kendaraan WHERE id_pelanggan=?";
                PreparedStatement psKendaraan = conn.prepareStatement(sqlKendaraan);
                psKendaraan.setString(1, txtId.getText());
                psKendaraan.executeUpdate();

                // 3. BARU HAPUS ORANGNYA
                String sqlPelanggan = "DELETE FROM pelanggan WHERE id_pelanggan=?";
                PreparedStatement psPelanggan = conn.prepareStatement(sqlPelanggan);
                psPelanggan.setString(1, txtId.getText());
                psPelanggan.executeUpdate();

                // 4. Simpan Permanen
                conn.commit(); 
                
                JOptionPane.showMessageDialog(null, "Data Pelanggan & Mobilnya BERHASIL Dihapus!");
                tampilkanData();
                kosongkanForm();
                
            } catch (Exception e) {
                // Kalau error, batalkan semua
                try { if(conn != null) conn.rollback(); } catch(Exception ex){}
                JOptionPane.showMessageDialog(null, "Gagal Hapus (Mungkin ada data Service yang mengunci):\n" + e.getMessage());
            } finally {
                try { if(conn != null) conn.setAutoCommit(true); } catch(Exception ex){}
            }
        }
    }

    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) { UIManager.setLookAndFeel(info.getClassName()); break; }
            }
        } catch (Exception e) {}
        new FormPelanggan();
    }
}