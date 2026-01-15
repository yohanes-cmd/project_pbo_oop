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
import javax.swing.JComboBox;
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

public class FormKendaraan extends JFrame {

    // Komponen
    JTextField txtNopol, txtMerk, txtModel, txtWarna, txtTahun;
    JComboBox<String> cmbPemilik;
    JButton btnSimpan, btnUbah, btnHapus, btnReset;
    JTable tabelKendaraan;
    DefaultTableModel model;

    // Warna
    Color colorPrimary = Color.decode("#2C3E50");
    Color colorBackground = Color.WHITE;
    Color colorSuccess = Color.decode("#27AE60");
    Color colorWarning = Color.decode("#F39C12");
    Color colorDanger = Color.decode("#E74C3C");
    Color colorNeutral = Color.decode("#7F8C8D");

    public FormKendaraan() {
        setTitle("Aplikasi Bengkel - Master Kendaraan");
        setSize(950, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // PENTING: Ganti jadi DISPOSE biar form Pelanggan gak ikut mati
        setLayout(new BorderLayout());

        // --- PANEL ATAS ---
        JPanel panelAtas = new JPanel();
        panelAtas.setLayout(null); 
        panelAtas.setPreferredSize(new Dimension(800, 320)); 
        panelAtas.setBackground(colorBackground); 
        
        JPanel header = new JPanel();
        header.setBounds(0, 0, 3000, 70); 
        header.setBackground(colorPrimary);
        header.setLayout(null);
        
        JLabel judul = new JLabel("DATA KENDARAAN");
        judul.setFont(new Font("Segoe UI", Font.BOLD, 24));
        judul.setForeground(Color.WHITE);
        judul.setBounds(30, 20, 400, 30);
        header.add(judul);
        panelAtas.add(header);

        buatLabel(panelAtas, "No Polisi (PK)", 50, 90);
        buatLabel(panelAtas, "Merk Mobil", 50, 130);
        buatLabel(panelAtas, "Model/Tipe", 50, 170);
        buatLabel(panelAtas, "Warna", 450, 130);
        buatLabel(panelAtas, "Tahun", 450, 170);
        buatLabel(panelAtas, "Pemilik", 50, 220);

        txtNopol = buatTextField(180, 90, 200); panelAtas.add(txtNopol);
        txtMerk = buatTextField(180, 130, 200); panelAtas.add(txtMerk);
        txtModel = buatTextField(180, 170, 200); panelAtas.add(txtModel);
        txtWarna = buatTextField(550, 130, 200); panelAtas.add(txtWarna);
        txtTahun = buatTextField(550, 170, 200); panelAtas.add(txtTahun);

        cmbPemilik = new JComboBox<>();
        cmbPemilik.setBounds(180, 220, 400, 35);
        cmbPemilik.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panelAtas.add(cmbPemilik);

        add(panelAtas, BorderLayout.NORTH);

        // --- PANEL BAWAH ---
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

        // --- PANEL TENGAH (TABEL) ---
        String[] judulTabel = {"No Polisi", "Merk", "Model", "Warna", "Tahun", "Pemilik", "ID Pelanggan"};
        model = new DefaultTableModel(judulTabel, 0);
        tabelKendaraan = new JTable(model);
        
        tabelKendaraan.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer() {
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
        tabelKendaraan.setRowHeight(30);
        tabelKendaraan.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tabelKendaraan.setShowVerticalLines(false);
        tabelKendaraan.setGridColor(new Color(230, 230, 230));
        tabelKendaraan.setSelectionBackground(new Color(52, 152, 219));
        tabelKendaraan.setSelectionForeground(Color.WHITE);
        
        JScrollPane scrollPane = new JScrollPane(tabelKendaraan);
        scrollPane.setBorder(new EmptyBorder(0, 30, 0, 30)); 
        scrollPane.getViewport().setBackground(colorBackground);
        
        add(scrollPane, BorderLayout.CENTER);

        // --- LOGIC ---
        tabelKendaraan.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int baris = tabelKendaraan.getSelectedRow();
                txtNopol.setText(model.getValueAt(baris, 0).toString());
                txtMerk.setText(model.getValueAt(baris, 1).toString());
                txtModel.setText(model.getValueAt(baris, 2).toString());
                txtWarna.setText(model.getValueAt(baris, 3).toString());
                txtTahun.setText(model.getValueAt(baris, 4).toString());
                
                String namaPemilik = model.getValueAt(baris, 5).toString();
                String idPemilik = model.getValueAt(baris, 6).toString();
                cmbPemilik.setSelectedItem(idPemilik + " - " + namaPemilik);
                
                txtNopol.setEditable(false);
                aturTombol(false);
            }
        });

        btnSimpan.addActionListener(e -> simpanData());
        btnUbah.addActionListener(e -> ubahData());
        btnHapus.addActionListener(e -> hapusData());
        btnReset.addActionListener(e -> kosongkanForm());

        isiComboPelanggan();
        tampilkanData();
        kosongkanForm();
        setVisible(true);
    }
    
    // --- FITUR BARU: METHOD UNTUK MENERIMA LEMPARAN DARI FORM PELANGGAN ---
    public void pilihPelangganOtomatis(String id, String nama) {
        String dataYangDicari = id + " - " + nama;
        cmbPemilik.setSelectedItem(dataYangDicari);
        // Opsional: Kunci combo box biar gak bisa ganti orang
        // cmbPemilik.setEnabled(false); 
        System.out.println("Otomatis memilih: " + dataYangDicari);
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

    private void isiComboPelanggan() {
        cmbPemilik.removeAllItems();
        try {
            Connection conn = Koneksi.getKoneksi();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT id_pelanggan, nama FROM pelanggan");
            while (rs.next()) {
                String item = rs.getString("id_pelanggan") + " - " + rs.getString("nama");
                cmbPemilik.addItem(item);
            }
        } catch (Exception e) {
            System.err.println("Gagal isi combo: " + e.getMessage());
        }
    }
    
    private String getSelectedIdPelanggan() {
        String selected = (String) cmbPemilik.getSelectedItem();
        if (selected != null) {
            String[] parts = selected.split(" - ");
            return parts[0]; 
        }
        return "0";
    }

    private void kosongkanForm() {
        txtNopol.setText(""); txtNopol.setEditable(true);
        txtMerk.setText(""); txtModel.setText(""); txtWarna.setText(""); txtTahun.setText("");
        if(cmbPemilik.getItemCount() > 0) cmbPemilik.setSelectedIndex(0);
        aturTombol(true);
        tabelKendaraan.clearSelection();
    }

    private void tampilkanData() {
        int row = model.getRowCount();
        for (int i = 0; i < row; i++) { model.removeRow(0); }
        try {
            Connection conn = Koneksi.getKoneksi();
            Statement stm = conn.createStatement();
            String sql = "SELECT k.*, p.nama FROM kendaraan k JOIN pelanggan p ON k.id_pelanggan = p.id_pelanggan";
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                model.addRow(new Object[]{ 
                    rs.getString("no_polisi"), rs.getString("merk"), rs.getString("model"), rs.getString("warna"), rs.getString("tahun"), rs.getString("nama"), rs.getString("id_pelanggan")
                });
            }
        } catch (Exception e) { System.err.println("Error: " + e.getMessage()); }
    }

    private void simpanData() {
        try {
            String sql = "INSERT INTO kendaraan (no_polisi, merk, model, warna, tahun, id_pelanggan) VALUES (?, ?, ?, ?, ?, ?)";
            Connection conn = Koneksi.getKoneksi();
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, txtNopol.getText()); stat.setString(2, txtMerk.getText()); stat.setString(3, txtModel.getText()); stat.setString(4, txtWarna.getText()); stat.setString(5, txtTahun.getText()); stat.setString(6, getSelectedIdPelanggan());
            stat.executeUpdate(); JOptionPane.showMessageDialog(null, "Data Tersimpan!");
            tampilkanData(); kosongkanForm();
        } catch (Exception e) { JOptionPane.showMessageDialog(null, "Gagal: " + e.getMessage()); }
    }

    private void ubahData() {
        try {
            String sql = "UPDATE kendaraan SET merk=?, model=?, warna=?, tahun=?, id_pelanggan=? WHERE no_polisi=?";
            Connection conn = Koneksi.getKoneksi();
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, txtMerk.getText()); stat.setString(2, txtModel.getText()); stat.setString(3, txtWarna.getText()); stat.setString(4, txtTahun.getText()); stat.setString(5, getSelectedIdPelanggan()); stat.setString(6, txtNopol.getText());
            stat.executeUpdate(); JOptionPane.showMessageDialog(null, "Data Diupdate!");
            tampilkanData(); kosongkanForm();
        } catch (Exception e) { JOptionPane.showMessageDialog(null, "Gagal: " + e.getMessage()); }
    }

    private void hapusData() {
        int confirm = JOptionPane.showConfirmDialog(null, "Hapus kendaraan ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                String sql = "DELETE FROM kendaraan WHERE no_polisi=?";
                Connection conn = Koneksi.getKoneksi();
                PreparedStatement stat = conn.prepareStatement(sql);
                stat.setString(1, txtNopol.getText()); stat.executeUpdate(); 
                JOptionPane.showMessageDialog(null, "Data Terhapus!");
                tampilkanData(); kosongkanForm();
            } catch (Exception e) { JOptionPane.showMessageDialog(null, "Gagal Hapus: " + e.getMessage()); }
        }
    }

    public static void main(String[] args) {
        new FormKendaraan();
    }
}