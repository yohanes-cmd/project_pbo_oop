package Tampilan;

import database.Koneksi;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class FormTransaksi extends JFrame {

    // --- VARIABEL KOMPONEN GUI ---
    JComboBox<String> cmbNopol, cmbMekanik;
    JTextField txtTanggal, txtKeluhan;
    JTabbedPane tabbedPane; 
    
    // Tab 1: Sparepart
    JComboBox<String> cmbSparepart;
    JTextField txtQty;
    JButton btnAddPart, btnDelPart;
    JTable tabelPart;
    DefaultTableModel modelPart;
    
    // Tab 2: Jasa
    JComboBox<String> cmbJasa;
    JTextField txtBiayaJasa;
    JButton btnAddJasa, btnDelJasa;
    JTable tabelJasa;
    DefaultTableModel modelJasa;

    // Tab 3: Riwayat
    JTable tabelRiwayat;
    DefaultTableModel modelRiwayat;
    JButton btnRefresh, btnCetakUlang, btnHapusRiwayat;
    
    // Footer
    JLabel lblTotal;
    JButton btnSimpan;
    
    Color colorPrimary = Color.decode("#2C3E50");

    // --- CONSTRUCTOR ---
    public FormTransaksi() {
        setTitle("Sistem Transaksi Bengkel Pro (With Smart WhatsApp)");
        setSize(1100, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        setLayout(new BorderLayout());

        // =====================================================================
        // BAGIAN 1: HEADER
        // =====================================================================
        JPanel panelUtara = new JPanel(null);
        panelUtara.setPreferredSize(new Dimension(1000, 180));
        panelUtara.setBackground(Color.WHITE);
        
        JPanel headerInfo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerInfo.setBounds(0,0,2000,50); headerInfo.setBackground(colorPrimary);
        JLabel lblJudul = new JLabel("  FORM INPUT & RIWAYAT TRANSAKSI");
        lblJudul.setFont(new Font("Segoe UI", Font.BOLD, 20)); lblJudul.setForeground(Color.WHITE);
        headerInfo.add(lblJudul);
        panelUtara.add(headerInfo);

        buatLabel(panelUtara, "Tanggal:", 30, 70);
        txtTanggal = new JTextField(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        txtTanggal.setBounds(120, 70, 150, 30); txtTanggal.setEditable(false); panelUtara.add(txtTanggal);

        buatLabel(panelUtara, "Kendaraan:", 30, 110);
        cmbNopol = new JComboBox<>(); cmbNopol.setBounds(120, 110, 300, 30); panelUtara.add(cmbNopol);
        
        buatLabel(panelUtara, "Mekanik:", 450, 70);
        cmbMekanik = new JComboBox<>(); cmbMekanik.setBounds(540, 70, 250, 30); panelUtara.add(cmbMekanik);
        
        buatLabel(panelUtara, "Keluhan:", 450, 110);
        txtKeluhan = new JTextField(); txtKeluhan.setBounds(540, 110, 400, 30); panelUtara.add(txtKeluhan);
        
        add(panelUtara, BorderLayout.NORTH);

        // =====================================================================
        // BAGIAN 2: TABS
        // =====================================================================
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        // >>> TAB 1: SPAREPART
        JPanel panelPart = new JPanel(new BorderLayout());
        JPanel pInputPart = new JPanel(new FlowLayout(FlowLayout.LEFT));
        cmbSparepart = new JComboBox<>(); cmbSparepart.setPreferredSize(new Dimension(300, 30));
        txtQty = new JTextField("1"); txtQty.setPreferredSize(new Dimension(50, 30));
        btnAddPart = new JButton("Tambah Part"); btnDelPart = new JButton("Hapus");
        
        pInputPart.add(new JLabel("Pilih Part:")); pInputPart.add(cmbSparepart);
        pInputPart.add(new JLabel("Qty:")); pInputPart.add(txtQty);
        pInputPart.add(btnAddPart); pInputPart.add(btnDelPart);
        
        String[] hPart = {"Kode", "Nama Part", "Harga", "Qty", "Subtotal"};
        modelPart = new DefaultTableModel(hPart, 0);
        tabelPart = new JTable(modelPart); stylingTabel(tabelPart);
        panelPart.add(pInputPart, BorderLayout.NORTH);
        panelPart.add(new JScrollPane(tabelPart), BorderLayout.CENTER);
        tabbedPane.addTab("  1. INPUT SPAREPART  ", panelPart);
        
        // >>> TAB 2: JASA
        JPanel panelJasa = new JPanel(new BorderLayout());
        JPanel pInputJasa = new JPanel(new FlowLayout(FlowLayout.LEFT));
        cmbJasa = new JComboBox<>(); cmbJasa.setPreferredSize(new Dimension(300, 30));
        txtBiayaJasa = new JTextField(); txtBiayaJasa.setPreferredSize(new Dimension(100, 30));
        btnAddJasa = new JButton("Tambah Jasa"); btnDelJasa = new JButton("Hapus");
        
        pInputJasa.add(new JLabel("Pilih Jasa:")); pInputJasa.add(cmbJasa);
        pInputJasa.add(new JLabel("Biaya (Rp):")); pInputJasa.add(txtBiayaJasa); 
        pInputJasa.add(btnAddJasa); pInputJasa.add(btnDelJasa);
        
        String[] hJasa = {"ID", "Nama Jasa", "Biaya"};
        modelJasa = new DefaultTableModel(hJasa, 0);
        tabelJasa = new JTable(modelJasa); stylingTabel(tabelJasa);
        panelJasa.add(pInputJasa, BorderLayout.NORTH);
        panelJasa.add(new JScrollPane(tabelJasa), BorderLayout.CENTER);
        tabbedPane.addTab("  2. INPUT JASA  ", panelJasa);

        // >>> TAB 3: RIWAYAT
        JPanel panelRiwayat = new JPanel(new BorderLayout());
        JPanel pAtasRiwayat = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        btnCetakUlang = new JButton("Cetak Ulang"); 
        btnHapusRiwayat = new JButton("Hapus Transaksi");
        btnHapusRiwayat.setBackground(Color.decode("#C0392B")); btnHapusRiwayat.setForeground(Color.WHITE);
        btnRefresh = new JButton("Refresh");
        
        pAtasRiwayat.add(btnCetakUlang);
        pAtasRiwayat.add(btnHapusRiwayat);
        pAtasRiwayat.add(btnRefresh);

        String[] hRiwayat = {"No Nota", "Tanggal", "Nopol", "Mekanik", "Total Biaya"};
        modelRiwayat = new DefaultTableModel(hRiwayat, 0);
        tabelRiwayat = new JTable(modelRiwayat); stylingTabel(tabelRiwayat);
        
        panelRiwayat.add(pAtasRiwayat, BorderLayout.NORTH);
        panelRiwayat.add(new JScrollPane(tabelRiwayat), BorderLayout.CENTER);
        tabbedPane.addTab("  3. RIWAYAT TRANSAKSI  ", panelRiwayat);

        add(tabbedPane, BorderLayout.CENTER);

        // =====================================================================
        // BAGIAN 3: FOOTER
        // =====================================================================
        JPanel panelBawah = new JPanel(new BorderLayout());
        panelBawah.setPreferredSize(new Dimension(1000, 80));
        panelBawah.setBackground(Color.decode("#ECF0F1"));
        panelBawah.setBorder(new EmptyBorder(10, 20, 10, 20));
        
        btnSimpan = new JButton("SIMPAN TRANSAKSI");
        btnSimpan.setBackground(Color.decode("#27AE60")); btnSimpan.setForeground(Color.WHITE);
        btnSimpan.setFont(new Font("Segoe UI", Font.BOLD, 16));
        
        lblTotal = new JLabel("Total: Rp 0");
        lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 30));
        lblTotal.setForeground(Color.decode("#C0392B"));
        lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);
        
        panelBawah.add(btnSimpan, BorderLayout.WEST);
        panelBawah.add(lblTotal, BorderLayout.CENTER);
        add(panelBawah, BorderLayout.SOUTH);

        // =====================================================================
        // EVENTS
        // =====================================================================
        loadCombo();
        loadRiwayat(); 
        
        btnAddPart.addActionListener(e -> tambahPart());
        btnDelPart.addActionListener(e -> hapusRow(tabelPart, modelPart));
        
        cmbJasa.addActionListener(e -> { 
            if(cmbJasa.getSelectedItem() != null) {
                try { txtBiayaJasa.setText(cmbJasa.getSelectedItem().toString().split(" - ")[2]); } catch (Exception ex) {}
            }
        });
        btnAddJasa.addActionListener(e -> tambahJasa());
        btnDelJasa.addActionListener(e -> hapusRow(tabelJasa, modelJasa));
        
        btnRefresh.addActionListener(e -> loadRiwayat()); 
        btnSimpan.addActionListener(e -> simpanTransaksi());
        btnCetakUlang.addActionListener(e -> aksiCetakUlang()); 
        btnHapusRiwayat.addActionListener(e -> aksiHapusRiwayat());
        
        setVisible(true);
    }
    
    // =========================================================================
    // LOGIC METHODS
    // =========================================================================
    
    // --- 1. FITUR TAMBAH PART DENGAN VALIDASI STOK ---
    void tambahPart() {
        if (cmbSparepart.getSelectedItem() == null) return;
        
        try {
            String[] s = cmbSparepart.getSelectedItem().toString().split(" - ");
            String kodePart = s[0];
            String namaPart = s[1];
            double harga = Double.parseDouble(s[2]);
            
            if (txtQty.getText().isEmpty()) { JOptionPane.showMessageDialog(this, "Isi Qty!"); return; }
            int qtyMinta = Integer.parseInt(txtQty.getText());
            if (qtyMinta <= 0) { JOptionPane.showMessageDialog(this, "Qty harus lebih dari 0"); return; }
            
            Connection conn = Koneksi.getKoneksi();
            PreparedStatement ps = conn.prepareStatement("SELECT stok FROM sparepart WHERE kode_part = ?");
            ps.setString(1, kodePart);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                int stokTersedia = rs.getInt("stok");
                if (stokTersedia <= 0) { JOptionPane.showMessageDialog(this, "Stok " + namaPart + " HABIS!"); return; }
                
                int stokDiKeranjang = 0;
                for(int i=0; i < modelPart.getRowCount(); i++) {
                    if(modelPart.getValueAt(i, 0).toString().equals(kodePart)) {
                        stokDiKeranjang += Integer.parseInt(modelPart.getValueAt(i, 3).toString());
                    }
                }
                
                if ((qtyMinta + stokDiKeranjang) > stokTersedia) {
                    JOptionPane.showMessageDialog(this, "Stok tidak cukup!\nSisa: " + stokTersedia + "\nDi Keranjang: " + stokDiKeranjang);
                    return;
                }
            }
            
            double sub = harga * qtyMinta;
            modelPart.addRow(new Object[]{kodePart, namaPart, harga, qtyMinta, sub});
            hitungTotal();
            txtQty.setText("1"); 
            
        } catch(Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
    
    // --- 2. HAPUS RIWAYAT ---
    void aksiHapusRiwayat() {
        int row = tabelRiwayat.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Pilih transaksi!"); return; }
        
        String idStr = tabelRiwayat.getValueAt(row, 0).toString().replace("#", "");
        int idService = Integer.parseInt(idStr);
        
        int confirm = JOptionPane.showConfirmDialog(this, "Yakin hapus transaksi #" + idService + "?\nStok akan dikembalikan.", "Hapus", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            Connection conn = null;
            try {
                conn = Koneksi.getKoneksi(); conn.setAutoCommit(false);
                
                PreparedStatement psCek = conn.prepareStatement("SELECT kode_part, qty FROM service_detail_part WHERE id_service = ?");
                psCek.setInt(1, idService); ResultSet rsCek = psCek.executeQuery();
                PreparedStatement psBalik = conn.prepareStatement("UPDATE sparepart SET stok = stok + ? WHERE kode_part = ?");
                while(rsCek.next()) {
                    psBalik.setInt(1, rsCek.getInt("qty"));
                    psBalik.setString(2, rsCek.getString("kode_part"));
                    psBalik.executeUpdate();
                }
                
                conn.createStatement().executeUpdate("DELETE FROM service_detail_part WHERE id_service=" + idService);
                conn.createStatement().executeUpdate("DELETE FROM service_detail_jasa WHERE id_service=" + idService);
                conn.createStatement().executeUpdate("DELETE FROM service_header WHERE id_service=" + idService);
                
                conn.commit();
                JOptionPane.showMessageDialog(this, "Transaksi Dihapus & Stok Dikembalikan!");
                loadRiwayat(); loadCombo(); 
            } catch (Exception e) {
                try { conn.rollback(); } catch(Exception ex){}
                JOptionPane.showMessageDialog(this, "Gagal: " + e.getMessage());
            }
        }
    }
    
    // --- 3. LOGIKA WA YANG BARU (SOLUSI MASALAH SINKRONISASI) ---
    void kirimWhatsApp(int idService) {
        try {
            Connection conn = Koneksi.getKoneksi();
            
            // 1. QUERY JOIN YANG KUAT (CARI NAMA & HP)
            // Mengambil data dari relasi: Service -> Kendaraan -> Pelanggan
            String sql = "SELECT p.nama, p.no_hp FROM service_header s " +
                         "JOIN kendaraan k ON s.no_polisi = k.no_polisi " +
                         "JOIN pelanggan p ON k.id_pelanggan = p.id_pelanggan " +
                         "WHERE s.id_service = ?";
            
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idService);
            ResultSet rs = ps.executeQuery();
            
            String noHp = "";
            String namaPelanggan = "Pelanggan";
            
            if(rs.next()) {
                noHp = rs.getString("no_hp");
                namaPelanggan = rs.getString("nama");
            }
            
            // 2. FAIL-SAFE: JIKA NOMOR TIDAK KETEMU DI DB, MINTA INPUT MANUAL
            if (noHp == null || noHp.isEmpty() || noHp.equals("-")) {
                 noHp = JOptionPane.showInputDialog(this, 
                        "Nomor HP tidak terdeteksi di database!\nSilakan masukkan No WA tujuan:", 
                        "Input Manual", JOptionPane.QUESTION_MESSAGE);
                 if (noHp == null || noHp.isEmpty()) return; // Cancel
            }
            
            // 3. KONFIRMASI (Agar user tau mau kirim ke siapa)
            int confirm = JOptionPane.showConfirmDialog(this, 
                    "Kirim Nota ke: " + namaPelanggan + "\nNomor: " + noHp + "\nLanjutkan?", 
                    "Konfirmasi WA", JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                // Formatting 08 -> 628
                noHp = noHp.replaceAll("[^0-9]", ""); // Hapus karakter aneh
                if(noHp.startsWith("0")) noHp = "62" + noHp.substring(1);
                
                String isiPesan = generateInvoiceFromDB(idService);
                String url = "https://wa.me/" + noHp + "?text=" + URLEncoder.encode(isiPesan, StandardCharsets.UTF_8.toString());
                
                Desktop.getDesktop().browse(new URI(url));
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal WA: " + e.getMessage());
        }
    }

    // --- 4. CETAK & SIMPAN ---
    void aksiCetakUlang() {
        int row = tabelRiwayat.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Pilih transaksi!"); return; }
        int idService = Integer.parseInt(tabelRiwayat.getValueAt(row, 0).toString().replace("#", ""));
        Object[] opt = {"Cetak", "WA", "Tutup"};
        int n = JOptionPane.showOptionDialog(null, "Pilih Output:", "Cetak Ulang", 0, 3, null, opt, opt[2]);
        if(n==0) cetakStrukPrinter(idService); else if(n==1) kirimWhatsApp(idService);
    }
    
    void simpanTransaksi() {
        if (modelPart.getRowCount() == 0 && modelJasa.getRowCount() == 0) { JOptionPane.showMessageDialog(this, "Data kosong!"); return; }
        Connection conn = null;
        try {
            conn = Koneksi.getKoneksi(); conn.setAutoCommit(false);
            
            PreparedStatement psHead = conn.prepareStatement("INSERT INTO service_header (tgl_service, no_polisi, id_mekanik, keluhan_konsumen, total_biaya, status) VALUES (?,?,?,?,?, 'Selesai')", Statement.RETURN_GENERATED_KEYS);
            psHead.setString(1, txtTanggal.getText());
            psHead.setString(2, cmbNopol.getSelectedItem().toString().split(" - ")[0]);
            psHead.setString(3, cmbMekanik.getSelectedItem().toString().split(" - ")[0]);
            psHead.setString(4, txtKeluhan.getText());
            psHead.setDouble(5, Double.parseDouble(lblTotal.getText().replace("Total: Rp ", "")));
            psHead.executeUpdate();
            
            ResultSet rs = psHead.getGeneratedKeys(); rs.next(); int idService = rs.getInt(1);
            
            PreparedStatement psPart = conn.prepareStatement("INSERT INTO service_detail_part (id_service, kode_part, qty, subtotal_part) VALUES (?,?,?,?)");
            PreparedStatement psStok = conn.prepareStatement("UPDATE sparepart SET stok = stok - ? WHERE kode_part = ?");
            for(int i=0; i<modelPart.getRowCount(); i++) {
                psPart.setInt(1, idService); psPart.setString(2, modelPart.getValueAt(i, 0).toString());
                psPart.setInt(3, Integer.parseInt(modelPart.getValueAt(i, 3).toString()));
                psPart.setDouble(4, Double.parseDouble(modelPart.getValueAt(i, 4).toString()));
                psPart.executeUpdate();
                psStok.setInt(1, Integer.parseInt(modelPart.getValueAt(i, 3).toString()));
                psStok.setString(2, modelPart.getValueAt(i, 0).toString());
                psStok.executeUpdate();
            }
            
            PreparedStatement psJasa = conn.prepareStatement("INSERT INTO service_detail_jasa (id_service, id_jasa, harga_deal) VALUES (?,?,?)");
            for(int i=0; i<modelJasa.getRowCount(); i++) {
                psJasa.setInt(1, idService); psJasa.setInt(2, Integer.parseInt(modelJasa.getValueAt(i, 0).toString()));
                psJasa.setDouble(3, Double.parseDouble(modelJasa.getValueAt(i, 2).toString()));
                psJasa.executeUpdate();
            }
            conn.commit();
            
            Object[] opt = {"Cetak", "WA", "Tutup"};
            int n = JOptionPane.showOptionDialog(null, "Disimpan! Cetak Nota?", "Sukses", 0, 3, null, opt, opt[2]);
            if(n==0) cetakStrukPrinter(idService); else if(n==1) kirimWhatsApp(idService);
            
            txtKeluhan.setText(""); modelPart.setRowCount(0); modelJasa.setRowCount(0); lblTotal.setText("Total: Rp 0");
            loadRiwayat(); loadCombo(); tabbedPane.setSelectedIndex(2);
            
        } catch(Exception e) { try { conn.rollback(); } catch(Exception ex){} JOptionPane.showMessageDialog(null, "Gagal: " + e.getMessage()); }
    }
    
    // --- HELPER METHODS ---
    String generateInvoiceFromDB(int idService) {
        StringBuilder sb = new StringBuilder();
        try {
            Connection conn = Koneksi.getKoneksi();
            PreparedStatement psHead = conn.prepareStatement("SELECT s.*, m.nama_mekanik FROM service_header s JOIN mekanik m ON s.id_mekanik = m.id_mekanik WHERE id_service = ?");
            psHead.setInt(1, idService); ResultSet rsHead = psHead.executeQuery();
            if(rsHead.next()) {
                sb.append("===== BENGKEL MAJU JAYA =====\nWA: 0761 1234 \n=============================\n");
                sb.append("Nota: #").append(idService).append("\nTgl : ").append(rsHead.getString("tgl_service"));
                sb.append("\nNopol: ").append(rsHead.getString("no_polisi")).append("\nMekanik: ").append(rsHead.getString("nama_mekanik"));
                sb.append("\n-----------------------------\n");
            }
            PreparedStatement psPart = conn.prepareStatement("SELECT p.nama_part, d.qty, d.subtotal_part FROM service_detail_part d JOIN sparepart p ON d.kode_part = p.kode_part WHERE d.id_service = ?");
            psPart.setInt(1, idService); ResultSet rsPart = psPart.executeQuery();
            while(rsPart.next()) { sb.append(rsPart.getString("nama_part")).append(" (x").append(rsPart.getInt("qty")).append(") Rp ").append((int)rsPart.getDouble("subtotal_part")).append("\n"); }
            PreparedStatement psJasa = conn.prepareStatement("SELECT j.nama_jasa, d.harga_deal FROM service_detail_jasa d JOIN jasa_service j ON d.id_jasa = j.id_jasa WHERE d.id_service = ?");
            psJasa.setInt(1, idService); ResultSet rsJasa = psJasa.executeQuery();
            while(rsJasa.next()) { sb.append(rsJasa.getString("nama_jasa")).append(" Rp ").append((int)rsJasa.getDouble("harga_deal")).append("\n"); }
            sb.append("-----------------------------\nTOTAL: Rp ").append((int)rsHead.getDouble("total_biaya")).append("\n=============================\n");
        } catch (Exception e) { return "Error"; }
        return sb.toString();
    }
    
    void cetakStrukPrinter(int idService) { try { new JTextArea(generateInvoiceFromDB(idService)).print(); } catch (Exception e) {} }

    void tambahJasa() {
        if (cmbJasa.getSelectedItem() == null) return;
        try {
            String[] s = cmbJasa.getSelectedItem().toString().split(" - ");
            double biaya = Double.parseDouble(txtBiayaJasa.getText());
            modelJasa.addRow(new Object[]{s[0], s[1], biaya}); hitungTotal();
        } catch(Exception e) {}
    }
    
    void loadCombo() {
        try {
            Statement st = Koneksi.getKoneksi().createStatement();
            cmbNopol.removeAllItems(); ResultSet rs1 = st.executeQuery("SELECT no_polisi, merk FROM kendaraan");
            while(rs1.next()) cmbNopol.addItem(rs1.getString(1) + " - " + rs1.getString(2));
            cmbMekanik.removeAllItems(); ResultSet rs2 = st.executeQuery("SELECT id_mekanik, nama_mekanik FROM mekanik");
            while(rs2.next()) cmbMekanik.addItem(rs2.getString(1) + " - " + rs2.getString(2));
            cmbSparepart.removeAllItems(); ResultSet rs3 = st.executeQuery("SELECT kode_part, nama_part, harga_jual FROM sparepart WHERE stok > 0");
            while(rs3.next()) cmbSparepart.addItem(rs3.getString(1) + " - " + rs3.getString(2) + " - " + rs3.getString(3));
            cmbJasa.removeAllItems(); ResultSet rs4 = st.executeQuery("SELECT id_jasa, nama_jasa, harga_standar FROM jasa_service");
            while(rs4.next()) cmbJasa.addItem(rs4.getString(1) + " - " + rs4.getString(2) + " - " + rs4.getString(3));
        } catch(Exception e) {}
    }

    void loadRiwayat() {
        modelRiwayat.setRowCount(0);
        try {
            Connection conn = Koneksi.getKoneksi();
            ResultSet rs = conn.createStatement().executeQuery("SELECT s.id_service, s.tgl_service, s.no_polisi, m.nama_mekanik, s.total_biaya FROM service_header s JOIN mekanik m ON s.id_mekanik = m.id_mekanik ORDER BY s.id_service DESC");
            while(rs.next()) { modelRiwayat.addRow(new Object[]{"#" + rs.getString("id_service"), rs.getString("tgl_service"), rs.getString("no_polisi"), rs.getString("nama_mekanik"), "Rp " + rs.getString("total_biaya")}); }
        } catch (Exception e) {}
    }
    
    void hapusRow(JTable t, DefaultTableModel m) { if(t.getSelectedRow() != -1) { m.removeRow(t.getSelectedRow()); hitungTotal(); } }
    void hitungTotal() {
        double total = 0;
        for(int i=0; i<modelPart.getRowCount(); i++) total += Double.parseDouble(modelPart.getValueAt(i, 4).toString());
        for(int i=0; i<modelJasa.getRowCount(); i++) total += Double.parseDouble(modelJasa.getValueAt(i, 2).toString());
        lblTotal.setText("Total: Rp " + (int)total);
    }
    void buatLabel(JPanel p, String t, int x, int y) { JLabel l = new JLabel(t); l.setBounds(x, y, 100, 30); l.setFont(new Font("Segoe UI", Font.BOLD, 14)); p.add(l); }
    void stylingTabel(JTable t) {
        t.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel l = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                l.setBackground(colorPrimary); l.setForeground(Color.WHITE); l.setFont(new Font("Segoe UI", Font.BOLD, 14));
                l.setHorizontalAlignment(JLabel.CENTER); l.setOpaque(true); return l;
            }
        });
        t.setRowHeight(30);
    }
    public static void main(String[] args) {
        try { for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) if ("Nimbus".equals(info.getName())) UIManager.setLookAndFeel(info.getClassName()); } catch (Exception e) {}
        new FormTransaksi();
    }
}