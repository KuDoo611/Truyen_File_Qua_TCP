package client;

import javax.swing.*;
import database.DBHelper;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

public class LoginClient extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;

    // Form Login
    private JTextField txtLoginUser;
    private JPasswordField txtLoginPass;

    // Form Register
    private JTextField txtName, txtRegUser;
    private JPasswordField txtRegPass;

    public LoginClient() {
        setTitle("Ứng dụng Truyền File - Đăng nhập/Đăng ký");
        setSize(450, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Kiểm tra kết nối database khi khởi động
        if (!DBHelper.testConnection()) {
            JOptionPane.showMessageDialog(this, 
                "Lỗi kết nối database! Vui lòng kiểm tra:\n" +
                "1. MySQL đang chạy\n" +
                "2. Database 'truyen_fileTCP' tồn tại\n" +
                "3. Username/password đúng", 
                "Lỗi Database", JOptionPane.ERROR_MESSAGE);
        }

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(new Color(245, 248, 250));

        mainPanel.add(createLoginPanel(), "Login");
        mainPanel.add(createRegisterPanel(), "Register");

        add(mainPanel);
        cardLayout.show(mainPanel, "Login");
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(new Color(245, 248, 250));

        JLabel lblTitle = new JLabel("ĐĂNG NHẬP", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(new Color(47, 86, 165));
        panel.add(lblTitle, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridBagLayout());
        center.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        center.setBackground(new Color(245, 248, 250));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Tài khoản
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel lblUser = new JLabel("Tài khoản:");
        lblUser.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        center.add(lblUser, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        txtLoginUser = new JTextField(15);
        txtLoginUser.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtLoginUser.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 210, 220)),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        center.add(txtLoginUser, gbc);

        // Mật khẩu
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel lblPass = new JLabel("Mật khẩu:");
        lblPass.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        center.add(lblPass, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        txtLoginPass = new JPasswordField(15);
        txtLoginPass.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtLoginPass.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 210, 220)),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        center.add(txtLoginPass, gbc);

        panel.add(center, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        bottom.setBackground(new Color(245, 248, 250));
        
        RoundedButton btnLogin = new RoundedButton("Đăng nhập");
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLogin.setBackground(new Color(65, 105, 225)); // Royal blue
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setPreferredSize(new Dimension(140, 40));
        
        JButton btnToRegister = new JButton("Đăng ký tài khoản");
        btnToRegister.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btnToRegister.setForeground(new Color(47, 86, 165));
        btnToRegister.setContentAreaFilled(false);
        btnToRegister.setBorderPainted(false);
        btnToRegister.setFocusPainted(false);
        btnToRegister.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        bottom.add(btnLogin);
        bottom.add(btnToRegister);
        panel.add(bottom, BorderLayout.SOUTH);

        // Sự kiện
        btnLogin.addActionListener(e -> {
            String user = txtLoginUser.getText();
            String pass = new String(txtLoginPass.getPassword());
            
            if (user.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            if (checkLogin(user, pass)) {
                JOptionPane.showMessageDialog(this, "Đăng nhập thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                // Sau khi login có thể mở FileClient
                SwingUtilities.invokeLater(() -> {
                    new FileClient(user).setVisible(true);
                });
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Sai tài khoản hoặc mật khẩu!", "Lỗi đăng nhập", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnToRegister.addActionListener(e -> cardLayout.show(mainPanel, "Register"));
        
        // Cho phép đăng nhập bằng phím Enter
        txtLoginPass.addActionListener(e -> btnLogin.doClick());

        return panel;
    }

    private JPanel createRegisterPanel() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(new Color(245, 248, 250));

        // Thanh trên cùng có nút Back
        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(new Color(245, 248, 250));
        
        RoundedButton btnBack = new RoundedButton("← Quay lại");
        btnBack.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btnBack.setBackground(new Color(200, 200, 200)); // Light gray
        btnBack.setForeground(Color.DARK_GRAY);
        btnBack.setPreferredSize(new Dimension(100, 30));
        top.add(btnBack, BorderLayout.WEST);

        JLabel lblTitle = new JLabel("ĐĂNG KÝ TÀI KHOẢN", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(new Color(47, 86, 165));
        top.add(lblTitle, BorderLayout.CENTER);

        panel.add(top, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridBagLayout());
        center.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        center.setBackground(new Color(245, 248, 250));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Họ và tên
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel lblName = new JLabel("Họ và tên:");
        lblName.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        center.add(lblName, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        txtName = new JTextField(15);
        txtName.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtName.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 210, 220)),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        center.add(txtName, gbc);

        // Tài khoản
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel lblUser = new JLabel("Tài khoản:");
        lblUser.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        center.add(lblUser, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        txtRegUser = new JTextField(15);
        txtRegUser.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtRegUser.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 210, 220)),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        center.add(txtRegUser, gbc);

        // Mật khẩu
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel lblPass = new JLabel("Mật khẩu:");
        lblPass.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        center.add(lblPass, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        txtRegPass = new JPasswordField(15);
        txtRegPass.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtRegPass.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 210, 220)),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        center.add(txtRegPass, gbc);

        panel.add(center, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        bottom.setBackground(new Color(245, 248, 250));
        
        RoundedButton btnRegister = new RoundedButton("Đăng ký");
        btnRegister.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnRegister.setBackground(new Color(65, 105, 225)); // Royal blue
        btnRegister.setForeground(Color.WHITE);
        btnRegister.setPreferredSize(new Dimension(140, 40));
        bottom.add(btnRegister);
        panel.add(bottom, BorderLayout.SOUTH);

        // Sự kiện
        btnBack.addActionListener(e -> cardLayout.show(mainPanel, "Login"));

        btnRegister.addActionListener(e -> {
            String name = txtName.getText();
            String user = txtRegUser.getText();
            String pass = new String(txtRegPass.getPassword());

            if (name.isEmpty() || user.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (registerUser(name, user, pass)) {
                JOptionPane.showMessageDialog(this, "Đăng ký thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                // Xóa dữ liệu sau khi đăng ký
                txtName.setText("");
                txtRegUser.setText("");
                txtRegPass.setText("");
                cardLayout.show(mainPanel, "Login");
            }
        });
        
        // Cho phép đăng ký bằng phím Enter
        txtRegPass.addActionListener(e -> btnRegister.doClick());

        return panel;
    }

    // Hàm kiểm tra login thật với database
    private boolean checkLogin(String user, String pass) {
        if (!DBHelper.testConnection()) {
            JOptionPane.showMessageDialog(this, 
                "Lỗi kết nối database! Vui lòng kiểm tra MySQL.", 
                "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return DBHelper.login(user, pass);
    }

    // Hàm đăng ký thật với database
    private boolean registerUser(String name, String user, String pass) {
        if (!DBHelper.testConnection()) {
            JOptionPane.showMessageDialog(this, 
                "Lỗi kết nối database! Vui lòng kiểm tra MySQL.", 
                "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        boolean result = DBHelper.register(name, user, pass);
        if (!result) {
            // Kiểm tra xem username đã tồn tại không
            if (DBHelper.isUsernameExists(user)) {
                JOptionPane.showMessageDialog(this, "Tài khoản đã tồn tại!", "Lỗi đăng ký", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi đăng ký! Vui lòng thử lại.", "Lỗi đăng ký", JOptionPane.ERROR_MESSAGE);
            }
        }
        return result;
    }

    // Lớp nút bo tròn
    class RoundedButton extends JButton {
        private int cornerRadius = 15;
        
        public RoundedButton(String text) {
            super(text);
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            if (getModel().isPressed()) {
                g2.setColor(getBackground().darker());
            } else if (getModel().isRollover()) {
                g2.setColor(getBackground().brighter());
            } else {
                g2.setColor(getBackground());
            }
            
            g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius));
            g2.dispose();
            
            super.paintComponent(g);
        }
    }

    public static void main(String[] args) {
        // Đặt giao diện đồ họa hệ thống để có vẻ ngoài nhất quán
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            new LoginClient().setVisible(true);
        });
    }
}