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

public class FormSupplier extends JFrame {

    // Komponen
    JTextField txtId, txtNama, txtTelp, txtAlamat;
    JButton btnSimpan, btnUbah, btnHapus, btnReset;
    JTable tabelSupplier;
    DefaultTableModel model;

    // Warna Tema
    Color colorPrimary = Color.decode("#2C3E50");
    Color colorBackground = Color.WHITE;
    Color colorSuccess = Color.decode("#27AE60");
    Color colorWarning = Color.decode("#F39C12");
    Color colorDanger = Color.decode("#E74C3C");
    Color colorNeutral = Color.decode("#7F8C8D");

    public FormSupplier() {
        setTitle("Aplikasi Bengkel - Master Supplier");
        setSize(850, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Agar Menu Utama tidak tertutup
        setLayout(new BorderLayout());

        // --- 1. PANEL ATAS (INPUT) ---
        JPanel panelAtas = new JPanel();
        panelAtas.setLayout(null); 
        panelAtas.setPreferredSize(new Dimension(800, 240)); 
        panelAtas.setBackground(colorBackground); 
        
        JPanel header = new JPanel();
        header.setBounds(0, 0, 3000, 70); 
        header.setBackground(colorPrimary);
        header.setLayout(null);
        
        JLabel judul = new JLabel("DATA SUPPLIER / PEMASOK");
        judul.setFont(new Font("Segoe UI", Font.BOLD, 24));
        judul.setForeground(Color.WHITE);
        judul.setBounds(30, 20, 400, 30);
        header.add(judul);
        panelAtas.add(header);

        buatLabel(panelAtas, "Nama Supplier", 50, 90);
        buatLabel(panelAtas, "No Telepon", 50, 130);
        buatLabel(panelAtas, "Alamat Kantor", 50, 170);

        txtId = new JTextField(); txtId.setBounds(0, 0, 0, 0); panelAtas.add(txtId); // Hidden ID

        txtNama = buatTextField(180, 90, 400); panelAtas.add(txtNama);
        txtTelp = buatTextField(180, 130, 250); panelAtas.add(txtTelp);
        txtAlamat = buatTextField(180, 170, 500); panelAtas.add(txtAlamat);

        add(panelAtas, BorderLayout.NORTH);

        // --- 2. PANEL BAWAH (TOMBOL) ---
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

        // --- 3. PANEL TENGAH (TABEL) ---
        String[] judulTabel = {"ID", "Nama Supplier", "No Telp", "Alamat Kantor"};
        model = new DefaultTableModel(judulTabel, 0);
        tabelSupplier = new JTable(model);
        
        // Styling Tabel
        tabelSupplier.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer() {
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
        tabelSupplier.setRowHeight(30);
        tabelSupplier.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tabelSupplier.setShowVerticalLines(false);
        tabelSupplier.setGridColor(new Color(230, 230, 230));
        tabelSupplier.setSelectionBackground(new Color(52, 152, 219));
        tabelSupplier.setSelectionForeground(Color.WHITE);
        
        JScrollPane scrollPane = new JScrollPane(tabelSupplier);
        scrollPane.setBorder(new EmptyBorder(0, 30, 0, 30)); 
        scrollPane.getViewport().setBackground(colorBackground);
        
        add(scrollPane, BorderLayout.CENTER);

        // --- 4. LOGIC EVENTS ---
        tabelSupplier.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int baris = tabelSupplier.getSelectedRow();
                txtId.setText(model.getValueAt(baris, 0).toString());
                txtNama.setText(model.getValueAt(baris, 1).toString());
                txtTelp.setText(model.getValueAt(baris, 2).toString());
                txtAlamat.setText(model.getValueAt(baris, 3).toString());
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

    // --- HELPER UI ---
    private void buatLabel(JPanel panel, String text, int x, int y) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, 150, 35);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(new Color(50, 50, 50)); 
        panel.add(label);
    }
    
    private JTextField buatTextField(int x, int y, int w) {
        JTextField field = new JTextField();
        field.setBounds(x, y, w, 35);
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
        txtId.setText(""); txtNama.setText(""); txtTelp.setText(""); txtAlamat.setText("");
        txtNama.requestFocus();
        aturTombol(true);
        tabelSupplier.clearSelection();
    }

    // --- LOGIC DATABASE (CRUD) ---
    private void tampilkanData() {
        int row = model.getRowCount();
        for (int i = 0; i < row; i++) { model.removeRow(0); }
        try {
            Connection conn = Koneksi.getKoneksi();
            Statement stm = conn.createStatement();
            // REVISI: Menggunakan kolom 'alamat_kantor'
            ResultSet rs = stm.executeQuery("SELECT * FROM supplier");
            while (rs.next()) {
                model.addRow(new Object[]{ 
                    rs.getString("id_supplier"), 
                    rs.getString("nama_supplier"), 
                    rs.getString("no_telp"), 
                    rs.getString("alamat_kantor") // <--- DISINI PERUBAHANNYA
                });
            }
        } catch (Exception e) { System.err.println("Error: " + e.getMessage()); }
    }

    private void simpanData() {
        try {
            // REVISI: Menggunakan kolom 'alamat_kantor'
            String sql = "INSERT INTO supplier (nama_supplier, no_telp, alamat_kantor) VALUES (?, ?, ?)";
            Connection conn = Koneksi.getKoneksi();
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, txtNama.getText());
            stat.setString(2, txtTelp.getText());
            stat.setString(3, txtAlamat.getText());
            
            stat.executeUpdate(); 
            JOptionPane.showMessageDialog(null, "Data Supplier Tersimpan!");
            tampilkanData(); kosongkanForm();
        } catch (Exception e) { 
            JOptionPane.showMessageDialog(null, "Gagal: " + e.getMessage()); 
        }
    }

    private void ubahData() {
        try {
            // REVISI: Menggunakan kolom 'alamat_kantor'
            String sql = "UPDATE supplier SET nama_supplier=?, no_telp=?, alamat_kantor=? WHERE id_supplier=?";
            Connection conn = Koneksi.getKoneksi();
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, txtNama.getText());
            stat.setString(2, txtTelp.getText());
            stat.setString(3, txtAlamat.getText());
            stat.setString(4, txtId.getText());
            
            stat.executeUpdate(); 
            JOptionPane.showMessageDialog(null, "Data Diupdate!");
            tampilkanData(); kosongkanForm();
        } catch (Exception e) { 
            JOptionPane.showMessageDialog(null, "Gagal: " + e.getMessage()); 
        }
    }

    private void hapusData() {
        int confirm = JOptionPane.showConfirmDialog(null, "Yakin hapus supplier ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                String sql = "DELETE FROM supplier WHERE id_supplier=?";
                Connection conn = Koneksi.getKoneksi();
                PreparedStatement stat = conn.prepareStatement(sql);
                stat.setString(1, txtId.getText());
                stat.executeUpdate();
                
                JOptionPane.showMessageDialog(null, "Data Terhapus!");
                tampilkanData();
                kosongkanForm();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Gagal Hapus: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) { UIManager.setLookAndFeel(info.getClassName()); break; }
            }
        } catch (Exception e) {}
        new FormSupplier();
    }
}