package database;

import java.sql.*;

public class DBHelper {
    private static final String URL = "jdbc:mysql://localhost:3306/truyen_fileTCP";
    private static final String USER = "root"; 
    private static final String PASS = "1"; 

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); 
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    // Kiểm tra kết nối database
    public static boolean testConnection() {
        try (Connection conn = getConnection()) {
            return conn != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Kiểm tra username đã tồn tại chưa
    public static boolean isUsernameExists(String username) {
        String sql = "SELECT * FROM users WHERE username=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Đăng ký user
    public static boolean register(String name, String username, String password) {
        if (isUsernameExists(username)) {
            return false;
        }
        
        String sql = "INSERT INTO users(name, username, password) VALUES (?,?,?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, username);
            ps.setString(3, password);
            int result = ps.executeUpdate();
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Kiểm tra đăng nhập
    public static boolean login(String username, String password) {
        String sql = "SELECT * FROM users WHERE username=? AND password=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Lưu lịch sử với cả người gửi và người nhận
    public static void insertHistory(String sender, String receiver, String file, long size, String status) {
        String sql = "INSERT INTO history (sender_name, receiver_name, file_name, file_size, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, sender);
            ps.setString(2, receiver);
            ps.setString(3, file);
            ps.setLong(4, size);
            ps.setString(5, status);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Lấy tên đầy đủ từ username
    public static String getFullName(String username) {
        String sql = "SELECT name FROM users WHERE username=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("name");
            }
            return username; // Trả về username nếu không tìm thấy name
        } catch (Exception e) {
            e.printStackTrace();
            return username;
        }
    }
}