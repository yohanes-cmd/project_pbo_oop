package Tampilan; // Pastikan package sesuai dengan project Anda (misal: package view;)

import database.Koneksi;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class FormJasa extends JFrame {

    JTextField txtId, txtNama, txtHarga;
    JButton btnSimpan, btnUbah, btnHapus, btnReset;
    JTable tabelJasa;
    DefaultTableModel model;

    Color colorPrimary = Color.decode("#2C3E50");
    Color colorBackground = Color.WHITE;
    Color colorSuccess = Color.decode("#27AE60");
    Color colorWarning = Color.decode("#F39C12");
    Color colorDanger = Color.decode("#E74C3C");
    Color colorNeutral = Color.decode("#7F8C8D");

    public FormJasa() {
        setTitle("Master Data Jasa Service");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // --- PANEL ATAS ---
        JPanel panelAtas = new JPanel(null);
        panelAtas.setPreferredSize(new Dimension(800, 220));
        panelAtas.setBackground(colorBackground);

        JPanel header = new JPanel(null);
        header.setBounds(0, 0, 3000, 60);
        header.setBackground(colorPrimary);
        JLabel judul = new JLabel("DAFTAR HARGA JASA / ONGKOS");
        judul.setFont(new Font("Segoe UI", Font.BOLD, 22));
        judul.setForeground(Color.WHITE);
        judul.setBounds(30, 15, 400, 30);
        header.add(judul);
        panelAtas.add(header);

        buatLabel(panelAtas, "Nama Jasa", 50, 90);
        buatLabel(panelAtas, "Biaya (Rp)", 50, 140);

        txtId = new JTextField(); txtId.setBounds(0, 0, 0, 0); panelAtas.add(txtId);
        
        // Perbaikan: buatTextField tidak perlu menambahkan ke panel di dalamnya
        txtNama = buatTextField(180, 90, 400); 
        panelAtas.add(txtNama); // Kita add manual di sini
        
        txtHarga = buatTextField(180, 140, 250); 
        panelAtas.add(txtHarga); // Kita add manual di sini

        add(panelAtas, BorderLayout.NORTH);

        // --- PANEL BAWAH ---
        JPanel panelBawah = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        panelBawah.setBackground(colorBackground);
        
        btnSimpan = buatTombol("Simpan", colorSuccess);
        btnUbah = buatTombol("Ubah", colorWarning);
        btnHapus = buatTombol("Hapus", colorDanger);
        btnReset = buatTombol("Reset", colorNeutral);
        
        panelBawah.add(btnSimpan); panelBawah.add(btnUbah); panelBawah.add(btnHapus); panelBawah.add(btnReset);
        add(panelBawah, BorderLayout.SOUTH);

        // --- TABEL ---
        String[] headerTabel = {"ID", "Nama Jasa Service", "Harga"};
        model = new DefaultTableModel(headerTabel, 0);
        tabelJasa = new JTable(model);
        
        tabelJasa.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel l = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                l.setBackground(colorPrimary); l.setForeground(Color.WHITE);
                l.setFont(new Font("Segoe UI", Font.BOLD, 14)); l.setHorizontalAlignment(JLabel.CENTER); l.setOpaque(true);
                return l;
            }
        });
        tabelJasa.setRowHeight(30);
        JScrollPane scroll = new JScrollPane(tabelJasa);
        scroll.setBorder(new EmptyBorder(0, 30, 0, 30));
        scroll.getViewport().setBackground(colorBackground);
        add(scroll, BorderLayout.CENTER);

        // --- EVENTS ---
        tabelJasa.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tabelJasa.getSelectedRow();
                txtId.setText(model.getValueAt(row, 0).toString());
                txtNama.setText(model.getValueAt(row, 1).toString());
                txtHarga.setText(model.getValueAt(row, 2).toString());
                aturTombol(false);
            }
        });

        btnSimpan.addActionListener(e -> simpan());
        btnUbah.addActionListener(e -> ubah());
        btnHapus.addActionListener(e -> hapus());
        btnReset.addActionListener(e -> reset());

        tampilData(); reset();
        setVisible(true);
    }

    // --- LOGIC CRUD ---
    void tampilData() {
        model.setRowCount(0);
        try {
            ResultSet rs = Koneksi.getKoneksi().createStatement().executeQuery("SELECT * FROM jasa_service");
            while(rs.next()) model.addRow(new Object[]{rs.getString(1), rs.getString(2), rs.getString(3)});
        } catch(Exception e) { e.printStackTrace(); }
    }

    void simpan() {
        try {
            PreparedStatement ps = Koneksi.getKoneksi().prepareStatement("INSERT INTO jasa_service (nama_jasa, harga_standar) VALUES (?, ?)");
            ps.setString(1, txtNama.getText()); ps.setDouble(2, Double.parseDouble(txtHarga.getText()));
            ps.executeUpdate(); tampilData(); reset(); JOptionPane.showMessageDialog(null, "Saved!");
        } catch(Exception e) { JOptionPane.showMessageDialog(null, "Error: " + e.getMessage()); }
    }

    void ubah() {
        try {
            PreparedStatement ps = Koneksi.getKoneksi().prepareStatement("UPDATE jasa_service SET nama_jasa=?, harga_standar=? WHERE id_jasa=?");
            ps.setString(1, txtNama.getText()); ps.setDouble(2, Double.parseDouble(txtHarga.getText())); ps.setString(3, txtId.getText());
            ps.executeUpdate(); tampilData(); reset(); JOptionPane.showMessageDialog(null, "Updated!");
        } catch(Exception e) { JOptionPane.showMessageDialog(null, "Error: " + e.getMessage()); }
    }

    void hapus() {
        try {
            PreparedStatement ps = Koneksi.getKoneksi().prepareStatement("DELETE FROM jasa_service WHERE id_jasa=?");
            ps.setString(1, txtId.getText()); ps.executeUpdate(); tampilData(); reset(); JOptionPane.showMessageDialog(null, "Deleted!");
        } catch(Exception e) { JOptionPane.showMessageDialog(null, "Error: " + e.getMessage()); }
    }

    void reset() {
        txtId.setText(""); txtNama.setText(""); txtHarga.setText("");
        aturTombol(true);
    }

    // --- HELPER ---
    void buatLabel(JPanel p, String t, int x, int y) {
        JLabel l = new JLabel(t); l.setBounds(x, y, 150, 30); l.setFont(new Font("Segoe UI", Font.BOLD, 14)); p.add(l);
    }
    
    // --- PERBAIKAN DI SINI ---
    JTextField buatTextField(int x, int y, int w) {
        JTextField f = new JTextField(); 
        f.setBounds(x, y, w, 35); 
        f.setBorder(new LineBorder(Color.GRAY, 1)); 
        // Code "p.add(f)" dihapus karena menyebabkan error
        return f;
    }
    
    JButton buatTombol(String t, Color c) {
        JButton b = new JButton(t); b.setBackground(c); b.setForeground(Color.WHITE); b.setPreferredSize(new Dimension(100, 40)); return b;
    }
    void aturTombol(boolean b) { btnSimpan.setEnabled(b); btnUbah.setEnabled(!b); btnHapus.setEnabled(!b); }
    
    public static void main(String[] args) { new FormJasa(); }
}