package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Koneksi {
    
    // Ini variabel untuk menyimpan koneksi
    private static Connection connect;
    
    public static Connection getKoneksi() {
        // Cek apakah koneksi sudah pernah dibuat?
        if (connect == null) {
            try {
                String url = "jdbc:mysql://localhost:3306/db_bengkel"; // Cek nama database anda
                String user = "root"; // User default XAMPP
                String password = ""; // Password default XAMPP (kosong)
                
                // 1. Register Driver
                DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
                
                // 2. Buat Koneksi
                connect = DriverManager.getConnection(url, user, password);
                
                System.out.println("Berhasil Koneksi!"); // Pesan di console bawah
                
            } catch (SQLException e) {
                System.out.println("Gagal Koneksi!");
                System.err.println(e.getMessage());
            }
        }
        return connect;
    }
    
    // Main method ini cuma buat ngetes doang.
    // Nanti kalau sudah oke, method ini boleh dihapus.
    public static void main(String[] args) {
        getKoneksi();
    }
}