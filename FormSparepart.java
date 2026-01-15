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

public class FormSparepart extends JFrame {

    // Komponen
    JTextField txtKode, txtNama, txtHargaBeli, txtHargaJual, txtStok;
    JButton btnSimpan, btnUbah, btnHapus, btnReset;
    JTable tabelSparepart;
    DefaultTableModel model;

    // Palet Warna
    Color colorPrimary = Color.decode("#2C3E50");
    Color colorBackground = Color.WHITE;
    Color colorSuccess = Color.decode("#27AE60");
    Color colorWarning = Color.decode("#F39C12");
    Color colorDanger = Color.decode("#E74C3C");
    Color colorNeutral = Color.decode("#7F8C8D");

    public FormSparepart() {
        // --- SETTING UTAMA ---
        setTitle("Aplikasi Bengkel - Master Sparepart");
        setSize(950, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Dispose biar menu utama gak mati
        setLayout(new BorderLayout());

        // ==========================================================
        // PANEL ATAS (FORM INPUT)
        // ==========================================================
        JPanel panelAtas = new JPanel();
        panelAtas.setLayout(null); 
        panelAtas.setPreferredSize(new Dimension(800, 320)); 
        panelAtas.setBackground(colorBackground); 
        
        // Header
        JPanel header = new JPanel();
        header.setBounds(0, 0, 3000, 70); 
        header.setBackground(colorPrimary);
        header.setLayout(null);
        
        JLabel judul = new JLabel("DATA SPAREPART & STOK");
        judul.setFont(new Font("Segoe UI", Font.BOLD, 24));
        judul.setForeground(Color.WHITE);
        judul.setBounds(30, 20, 400, 30);
        header.add(judul);
        panelAtas.add(header);

        // Label
        buatLabel(panelAtas, "Kode Part (PK)", 50, 90);
        buatLabel(panelAtas, "Nama Sparepart", 50, 130);
        buatLabel(panelAtas, "Stok Saat Ini", 50, 170);
        
        buatLabel(panelAtas, "Harga Beli (Rp)", 450, 90);
        buatLabel(panelAtas, "Harga Jual (Rp)", 450, 130);

        // Input Fields
        txtKode = buatTextField(180, 90, 200); panelAtas.add(txtKode);
        txtNama = buatTextField(180, 130, 250); panelAtas.add(txtNama);
        txtStok = buatTextField(180, 170, 100); panelAtas.add(txtStok); // Stok pendek aja
        
        txtHargaBeli = buatTextField(580, 90, 200); panelAtas.add(txtHargaBeli);
        txtHargaJual = buatTextField(580, 130, 200); panelAtas.add(txtHargaJual);

        add(panelAtas, BorderLayout.NORTH);

        // ==========================================================
        // PANEL BAWAH (TOMBOL)
        // ==========================================================
        JPanel panelBawah = new JPanel();
        panelBawah.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20)); 
        panelBawah.setBackground(colorBackground);
        
        btnSimpan = buatTombol("Simpan", colorSuccess);
        btnUbah = buatTombol("Ubah", colorWarning);
        btnHapus = buatTombol("Hapus", colorDanger);
        btnReset = buatTombol("Reset", colorNeutral);
        
        panelBawah.add(btnSimpan);
        panelBawah.add(btnUbah);
        panelBawah.add(btnHapus);
        panelBawah.add(btnReset);

        add(panelBawah, BorderLayout.SOUTH);

        // ==========================================================
        // PANEL TENGAH (TABEL)
        // ==========================================================
        String[] judulTabel = {"Kode Part", "Nama Sparepart", "Stok", "Harga Beli", "Harga Jual"};
        model = new DefaultTableModel(judulTabel, 0);
        tabelSparepart = new JTable(model);
        
        // Styling Header (Biru Putih)
        tabelSparepart.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer() {
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
        
        // Styling Isi Tabel
        tabelSparepart.setRowHeight(30);
        tabelSparepart.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tabelSparepart.setShowVerticalLines(false);
        tabelSparepart.setGridColor(new Color(230, 230, 230));
        tabelSparepart.setSelectionBackground(new Color(52, 152, 219));
        tabelSparepart.setSelectionForeground(Color.WHITE);
        
        JScrollPane scrollPane = new JScrollPane(tabelSparepart);
        scrollPane.setBorder(new EmptyBorder(0, 30, 0, 30)); 
        scrollPane.getViewport().setBackground(colorBackground);
        
        add(scrollPane, BorderLayout.CENTER);

        // ==========================================================
        // LOGIC EVENTS
        // ==========================================================
        tabelSparepart.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int baris = tabelSparepart.getSelectedRow();
                txtKode.setText(model.getValueAt(baris, 0).toString());
                txtNama.setText(model.getValueAt(baris, 1).toString());
                txtStok.setText(model.getValueAt(baris, 2).toString());
                txtHargaBeli.setText(model.getValueAt(baris, 3).toString());
                txtHargaJual.setText(model.getValueAt(baris, 4).toString());
                
                txtKode.setEditable(false); // PK tidak boleh diedit
                aturTombol(false);
            }
        });

        btnSimpan.addActionListener(e -> simpanData());
        btnUbah.addActionListener(e -> ubahData());
        btnHapus.addActionListener(e -> hapusData());
        btnReset.addActionListener(e -> kosongkanForm());

        tampilkanData();
        kosongkanForm();
        setVisible(true);
    }

    // --- HELPER METHODS ---
    private void buatLabel(JPanel panel, String text, int x, int y) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, 150, 35);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(new Color(50, 50, 50));
        panel.add(label);
    }
    
    private JTextField buatTextField(int x, int y, int width) {
        JTextField field = new JTextField();
        field.setBounds(x, y, width, 35);
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
        txtKode.setText(""); txtKode.setEditable(true);
        txtNama.setText(""); txtStok.setText(""); 
        txtHargaBeli.setText(""); txtHargaJual.setText("");
        txtKode.requestFocus();
        aturTombol(true);
        tabelSparepart.clearSelection();
    }

    private void tampilkanData() {
        int row = model.getRowCount();
        for (int i = 0; i < row; i++) { model.removeRow(0); }
        try {
            Connection conn = Koneksi.getKoneksi();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM sparepart");
            while (rs.next()) {
                model.addRow(new Object[]{ 
                    rs.getString("kode_part"),
                    rs.getString("nama_part"),
                    rs.getString("stok"),
                    rs.getString("harga_beli"),
                    rs.getString("harga_jual")
                });
            }
        } catch (Exception e) { System.err.println("Error: " + e.getMessage()); }
    }

    // --- LOGIC CRUD DENGAN VALIDASI ANGKA ---
    private void simpanData() {
        try {
            // Validasi Angka: Parse input text jadi angka
            int stok = Integer.parseInt(txtStok.getText());
            double hBeli = Double.parseDouble(txtHargaBeli.getText());
            double hJual = Double.parseDouble(txtHargaJual.getText());
            
            String sql = "INSERT INTO sparepart (kode_part, nama_part, stok, harga_beli, harga_jual) VALUES (?, ?, ?, ?, ?)";
            Connection conn = Koneksi.getKoneksi();
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, txtKode.getText());
            stat.setString(2, txtNama.getText());
            stat.setInt(3, stok);
            stat.setDouble(4, hBeli);
            stat.setDouble(5, hJual);
            
            stat.executeUpdate(); JOptionPane.showMessageDialog(null, "Data Sparepart Tersimpan!");
            tampilkanData(); kosongkanForm();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error: Stok dan Harga harus berupa ANGKA!");
        } catch (Exception e) { 
            JOptionPane.showMessageDialog(null, "Gagal: " + e.getMessage()); 
        }
    }

    private void ubahData() {
        try {
            int stok = Integer.parseInt(txtStok.getText());
            double hBeli = Double.parseDouble(txtHargaBeli.getText());
            double hJual = Double.parseDouble(txtHargaJual.getText());

            String sql = "UPDATE sparepart SET nama_part=?, stok=?, harga_beli=?, harga_jual=? WHERE kode_part=?";
            Connection conn = Koneksi.getKoneksi();
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, txtNama.getText());
            stat.setInt(2, stok);
            stat.setDouble(3, hBeli);
            stat.setDouble(4, hJual);
            stat.setString(5, txtKode.getText()); // PK
            
            stat.executeUpdate(); JOptionPane.showMessageDialog(null, "Data Diupdate!");
            tampilkanData(); kosongkanForm();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error: Stok dan Harga harus berupa ANGKA!");
        } catch (Exception e) { JOptionPane.showMessageDialog(null, "Gagal: " + e.getMessage()); }
    }

    private void hapusData() {
        int confirm = JOptionPane.showConfirmDialog(null, "Hapus sparepart ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                String sql = "DELETE FROM sparepart WHERE kode_part=?";
                Connection conn = Koneksi.getKoneksi();
                PreparedStatement stat = conn.prepareStatement(sql);
                stat.setString(1, txtKode.getText()); stat.executeUpdate(); 
                JOptionPane.showMessageDialog(null, "Data Terhapus!");
                tampilkanData(); kosongkanForm();
            } catch (Exception e) { JOptionPane.showMessageDialog(null, "Gagal Hapus: " + e.getMessage()); }
        }
    }

    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) { UIManager.setLookAndFeel(info.getClassName()); break; }
            }
        } catch (Exception e) {}
        new FormSparepart();
    }
}