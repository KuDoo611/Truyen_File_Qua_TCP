package client;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.DefaultTableCellRenderer;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.net.Socket;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class FileClient extends JFrame {
    private String username;
    private JTable table;
    private JProgressBar progressBar;
    private File selectedFile;
    private JTextField txtReceiver;
    private JButton btnReceivedFiles;
    private JButton btnSendHistory;
    private JButton btnChoose;
    private JButton btnSend;
    
    // Thông tin kết nối database
    private static final String DB_URL = "jdbc:mysql://localhost:3306/truyen_fileTCP?useSSL=false&serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "1";
    
    // Tên server để kiểm tra
    private static final String SERVER_NAME = "server";

    public FileClient(String username) {
        this.username = username;
        setTitle("Client - Gửi file (" + username + ")");
        setSize(750, 550);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(245, 247, 250));

        // Tạo panel chính với padding
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(new Color(245, 247, 250));

        // Panel header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(52, 152, 219));
        headerPanel.setBorder(new EmptyBorder(12, 20, 12, 20));
        headerPanel.setPreferredSize(new Dimension(getWidth(), 60));
        
        JLabel lblTitle = new JLabel("CLIENT GỬI FILE - " + username.toUpperCase());
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        headerPanel.add(lblTitle, BorderLayout.WEST);
        
        JLabel lblStatus = new JLabel("Đã kết nối đến server: localhost:12345");
        lblStatus.setForeground(new Color(230, 230, 230));
        lblStatus.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        headerPanel.add(lblStatus, BorderLayout.EAST);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Panel trung tâm
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBackground(new Color(245, 247, 250));

        // Panel chọn file và người nhận
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(Color.WHITE);
        inputPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)), 
                "Thông tin gửi file", 
                TitledBorder.LEFT, 
                TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 13),
                new Color(52, 152, 219)
            ),
            new EmptyBorder(15, 15, 15, 15)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        JLabel lblReceiver = new JLabel("Người nhận:");
        lblReceiver.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        inputPanel.add(lblReceiver, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.7;
        txtReceiver = new JTextField();
        txtReceiver.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtReceiver.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        inputPanel.add(txtReceiver, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.3;
        JLabel lblFile = new JLabel("File:");
        lblFile.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        inputPanel.add(lblFile, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0.5;
        JPanel filePanel = new JPanel(new BorderLayout(8, 0));
        filePanel.setBackground(Color.WHITE);
        JTextField txtFileName = new JTextField("Chưa chọn file");
        txtFileName.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtFileName.setEditable(false);
        txtFileName.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        filePanel.add(txtFileName, BorderLayout.CENTER);
        
        btnChoose = createModernButton("Chọn file", new Color(52, 152, 219), new Color(41, 128, 185), 120);
        filePanel.add(btnChoose, BorderLayout.EAST);
        inputPanel.add(filePanel, gbc);
        
        centerPanel.add(inputPanel, BorderLayout.NORTH);

        // Bảng hiển thị file
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)), 
                "File đã chọn", 
                TitledBorder.LEFT, 
                TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 13),
                new Color(52, 152, 219)
            ),
            new EmptyBorder(8, 8, 8, 8)
        ));
        
        String[] colNames = {"Tên file", "Kích thước (KB)"};
        Object[][] data = {};
        table = new JTable(new DefaultTableModel(data, colNames));
        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setShowGrid(false);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        
        centerPanel.add(tablePanel, BorderLayout.CENTER);

        // Panel nút
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        btnPanel.setBackground(new Color(245, 247, 250));
        
        btnReceivedFiles = createModernButton("File đã nhận", new Color(46, 204, 113), new Color(39, 174, 96), 150);
        btnSendHistory = createModernButton("Lịch sử gửi file", new Color(155, 89, 182), new Color(142, 68, 173), 150);
        btnSend = createModernButton("Gửi file", new Color(231, 76, 60), new Color(192, 57, 43), 150);
        
        btnPanel.add(btnReceivedFiles);
        btnPanel.add(btnSendHistory);
        btnPanel.add(btnSend);
        
        centerPanel.add(btnPanel, BorderLayout.SOUTH);
        
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Progress bar
        JPanel progressPanel = new JPanel(new BorderLayout());
        progressPanel.setBorder(new EmptyBorder(10, 10, 5, 10));
        progressPanel.setBackground(new Color(245, 247, 250));
        
        JLabel progressLabel = new JLabel("Tiến trình gửi file:");
        progressLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        progressPanel.add(progressLabel, BorderLayout.NORTH);
        
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        progressBar.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        progressBar.setForeground(new Color(52, 152, 219));
        progressBar.setBackground(new Color(220, 220, 220));
        progressBar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(2, 2, 2, 2)
        ));
        progressPanel.add(progressBar, BorderLayout.CENTER);
        
        mainPanel.add(progressPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Xử lý sự kiện
        btnChoose.addActionListener(e -> {
            chooseFile();
            if (selectedFile != null) {
                txtFileName.setText(selectedFile.getName());
            }
        });
        
        btnSend.addActionListener(e -> confirmAndSendFile());
        
        btnReceivedFiles.addActionListener(e -> showReceivedFiles());
        
        btnSendHistory.addActionListener(e -> showSendHistory());
    }

    private JButton createModernButton(String text, Color color, Color hoverColor, int width) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (getModel().isPressed()) {
                    g2.setColor(hoverColor.darker());
                } else if (getModel().isRollover()) {
                    g2.setColor(hoverColor);
                } else {
                    g2.setColor(color);
                }
                
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.dispose();

                super.paintComponent(g);
            }
        };
        
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(width, 40));
        
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setForeground(Color.WHITE);
            }
        });
        
        return button;
    }

    private void chooseFile() {
        JFileChooser chooser = new JFileChooser();
        int result = chooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = chooser.getSelectedFile();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0); // clear
            model.addRow(new Object[]{selectedFile.getName(), selectedFile.length() / 1024});
        }
    }

    private void confirmAndSendFile() {
        if (selectedFile == null) {
            JOptionPane.showMessageDialog(this, "Chưa chọn file!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String receiver = txtReceiver.getText().trim();
        if (receiver.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên người nhận!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // KIỂM TRA: Không cho phép gửi file đến server
        if (receiver.equalsIgnoreCase(SERVER_NAME)) {
            JOptionPane.showMessageDialog(this, 
                "Không thể gửi file trực tiếp đến server!\n" +
                "Chỉ có thể gửi file giữa các client với nhau.",
                "Lỗi gửi file", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
            this, 
            "Bạn có chắc chắn muốn gửi file '" + selectedFile.getName() + 
            "' đến '" + receiver + "' không?",
            "Xác nhận gửi file",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        
        if (confirm == JOptionPane.YES_OPTION) {
            sendFile();
        }
    }

    private void sendFile() {
        String receiver = txtReceiver.getText().trim();
        
        // KIỂM TRA LẦN NỮA: Không cho phép gửi file đến server
        if (receiver.equalsIgnoreCase(SERVER_NAME)) {
            JOptionPane.showMessageDialog(this, 
                "Không thể gửi file trực tiếp đến server!\n" +
                "Chỉ có thể gửi file giữa các client với nhau.",
                "Lỗi gửi file", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Socket socket = new Socket("localhost", 12345);
             DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
             FileInputStream fis = new FileInputStream(selectedFile)) {

            // Kiểm tra kết nối đến server
            if (!socket.isConnected()) {
                throw new IOException("Không thể kết nối đến server");
            }
            
            dos.writeUTF(username);
            dos.writeUTF(receiver);
            dos.writeUTF(selectedFile.getName());
            dos.writeLong(selectedFile.length());

            byte[] buffer = new byte[4096];
            long totalRead = 0;
            int read;
            while ((read = fis.read(buffer)) > 0) {
                dos.write(buffer, 0, read);
                totalRead += read;
                int percent = (int) ((totalRead * 100) / selectedFile.length());
                progressBar.setValue(percent);
            }

            // Lưu thông tin file vào database
            saveFileHistory(selectedFile.getName(), selectedFile.length(), receiver, "Thành công");
            
            JOptionPane.showMessageDialog(this, 
                "Đã gửi thành công file cho " + receiver + "!",
                "Thành công", 
                JOptionPane.INFORMATION_MESSAGE);

            // Reset sau khi gửi
            selectedFile = null;
            txtReceiver.setText("");
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);
            progressBar.setValue(0);

        } catch (IOException ex) {
            ex.printStackTrace();
            // Lưu thông tin file vào database với trạng thái thất bại
            saveFileHistory(selectedFile.getName(), selectedFile.length(), receiver, "Thất bại");
            
            JOptionPane.showMessageDialog(this, 
                "Không thể gửi file đến server!\n\n" +
                "Nguyên nhân: " + ex.getMessage() + "\n" +
                "Hãy kiểm tra:\n" +
                "1. Server có đang chạy không?\n" +
                "2. Kết nối mạng có ổn định không?",
                "Lỗi gửi file", 
                JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi gửi file: " + ex.getMessage(), 
                "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void saveFileHistory(String fileName, long fileSize, String receiver, String status) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            
            String sql = "INSERT INTO history (sender_name, receiver_name, file_name, file_size, status, transfer_time) VALUES (?, ?, ?, ?, ?, NOW())";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, receiver);
            pstmt.setString(3, fileName);
            pstmt.setLong(4, fileSize);
            pstmt.setString(5, status);
            
            pstmt.executeUpdate();
            
            System.out.println("Đã lưu lịch sử file vào database: " + fileName + " - Trạng thái: " + status);
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi lưu lịch sử: " + ex.getMessage(), 
                "Lỗi database", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void showReceivedFiles() {
        try {
            Object[][] data = getReceivedFilesFromDB();
            String[] columns = {"Tên file", "Kích thước (KB)", "Ngày nhận", "Người gửi", "Thao tác"};
            
            if (data.length == 0) {
                JOptionPane.showMessageDialog(this, "Không có file nào đã nhận!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            JDialog dialog = createReceivedFilesDialog("File đã nhận", data, columns);
            dialog.setVisible(true);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi kết nối database: " + ex.getMessage(), 
                "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private Object[][] getReceivedFilesFromDB() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            
            String sql = "SELECT file_name, file_size, transfer_time, sender_name FROM history WHERE receiver_name = ? AND status = 'Thành công' ORDER BY transfer_time DESC";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            rs = pstmt.executeQuery();
            
            List<Object[]> dataList = new ArrayList<>();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            
            while (rs.next()) {
                Object[] row = new Object[5];
                row[0] = rs.getString("file_name");
                row[1] = rs.getLong("file_size") / 1024;
                row[2] = dateFormat.format(rs.getTimestamp("transfer_time"));
                row[3] = rs.getString("sender_name");
                row[4] = "download";
                dataList.add(row);
            }
            
            Object[][] data = new Object[dataList.size()][5];
            for (int i = 0; i < dataList.size(); i++) {
                data[i] = dataList.get(i);
            }
            
            System.out.println("Đã lấy được " + data.length + " file đã nhận từ database");
            
            return data;
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi truy vấn database: " + ex.getMessage(), 
                "Lỗi", JOptionPane.ERROR_MESSAGE);
            return new Object[0][0];
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void showSendHistory() {
        try {
            Object[][] data = getSendHistoryFromDB();
            String[] columns = {"Tên file", "Kích thước (KB)", "Ngày gửi", "Người nhận", "Trạng thái"};
            
            if (data.length == 0) {
                JOptionPane.showMessageDialog(this, "Không có lịch sử gửi file!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            JDialog dialog = createHistoryDialog("Lịch sử gửi file", data, columns);
            dialog.setVisible(true);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi kết nối database: " + ex.getMessage(), 
                "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private Object[][] getSendHistoryFromDB() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            
            String sql = "SELECT file_name, file_size, transfer_time, receiver_name, status FROM history WHERE sender_name = ? ORDER BY transfer_time DESC";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            rs = pstmt.executeQuery();
            
            List<Object[]> dataList = new ArrayList<>();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            
            while (rs.next()) {
                Object[] row = new Object[5];
                row[0] = rs.getString("file_name");
                row[1] = rs.getLong("file_size") / 1024;
                row[2] = dateFormat.format(rs.getTimestamp("transfer_time"));
                row[3] = rs.getString("receiver_name");
                row[4] = rs.getString("status");
                dataList.add(row);
            }
            
            Object[][] data = new Object[dataList.size()][5];
            for (int i = 0; i < dataList.size(); i++) {
                data[i] = dataList.get(i);
            }
            
            System.out.println("Đã lấy được " + data.length + " file đã gửi từ database");
            
            return data;
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi truy vấn database: " + ex.getMessage(), 
                "Lỗi", JOptionPane.ERROR_MESSAGE);
            return new Object[0][0];
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private JDialog createHistoryDialog(String title, Object[][] data, String[] columns) {
        JDialog dialog = new JDialog(this, title, true);
        dialog.setSize(700, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout(10, 10));
        dialog.getContentPane().setBackground(new Color(245, 247, 250));
        
        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        contentPanel.setBackground(Color.WHITE);
        
        JTable historyTable = new JTable(data, columns);
        historyTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        historyTable.setRowHeight(28);
        historyTable.setEnabled(false);
        
        // Thiết lập renderer cho cột trạng thái
        historyTable.getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (value != null) {
                    String status = value.toString();
                    if ("Thành công".equals(status)) {
                        c.setForeground(new Color(46, 204, 113)); // Màu xanh lá
                    } else if ("Thất bại".equals(status)) {
                        c.setForeground(new Color(231, 76, 60)); // Màu đỏ
                    } else {
                        c.setForeground(Color.BLACK);
                    }
                }
                return c;
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(historyTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)), 
            title,
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 13),
            new Color(52, 152, 219)
        ));
        
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        
        JButton closeButton = createModernButton("Đóng", new Color(120, 120, 120), new Color(100, 100, 100), 100);
        closeButton.addActionListener(e -> dialog.dispose());
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(closeButton);
        
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        dialog.add(contentPanel);
        
        return dialog;
    }

    private JDialog createReceivedFilesDialog(String title, Object[][] data, String[] columns) {
        JDialog dialog = new JDialog(this, title, true);
        dialog.setSize(700, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout(10, 10));
        dialog.getContentPane().setBackground(new Color(245, 247, 250));
        
        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        contentPanel.setBackground(Color.WHITE);
        
        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public Class<?> getColumnClass(int column) {
                if (column == 4) {
                    return JButton.class;
                }
                return Object.class;
            }
            
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4;
            }
        };
        
        JTable historyTable = new JTable(model);
        historyTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        historyTable.setRowHeight(35);
        
        // Thiết lập renderer và editor cho cột thao tác
        historyTable.getColumn("Thao tác").setCellRenderer(new ButtonRenderer());
        historyTable.getColumn("Thao tác").setCellEditor(new ButtonEditor(new JCheckBox(), historyTable));
        
        JScrollPane scrollPane = new JScrollPane(historyTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)), 
            title,
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 13),
            new Color(52, 152, 219)
        ));
        
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        
        JButton closeButton = createModernButton("Đóng", new Color(120, 120, 120), new Color(100, 100, 100), 100);
        closeButton.addActionListener(e -> dialog.dispose());
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(closeButton);
        
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        dialog.add(contentPanel);
        
        return dialog;
    }

    // Lớp renderer cho nút download với thiết kế mới
    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
            setFont(new Font("Segoe UI", Font.BOLD, 12));
            setForeground(Color.WHITE);
            setBackground(new Color(52, 152, 219)); // Màu xanh dương thay vì xanh lá
            setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(41, 128, 185)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
            ));
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            setText("⬇️ Tải xuống"); // Thêm biểu tượng và văn bản
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            if (isSelected) {
                setBackground(new Color(41, 128, 185)); // Màu xanh dương đậm hơn khi được chọn
            } else {
                setBackground(new Color(52, 152, 219)); // Màu xanh dương mặc định
            }
            return this;
        }
    }

    // Lớp editor cho nút download với thiết kế mới
    class ButtonEditor extends DefaultCellEditor {
        protected JButton button;
        private String label;
        private boolean isPushed;
        private JTable table;

        public ButtonEditor(JCheckBox checkBox, JTable table) {
            super(checkBox);
            this.table = table;
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(e -> fireEditingStopped());
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            if (isSelected) {
                button.setForeground(table.getSelectionForeground());
                button.setBackground(new Color(41, 128, 185)); // Màu khi được chọn
            } else {
                button.setForeground(Color.WHITE);
                button.setBackground(new Color(52, 152, 219)); // Màu mặc định
            }

            button.setText("⬇️ Tải xuống"); // Thêm biểu tượng và văn bản
            button.setFont(new Font("Segoe UI", Font.BOLD, 12));
            button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(41, 128, 185)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
            ));
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            
            isPushed = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (isPushed) {
                int row = table.getSelectedRow();
                String fileName = (String) table.getValueAt(row, 0);
                String sender = (String) table.getValueAt(row, 3);
                
                downloadFile(fileName, sender);
            }
            isPushed = false;
            return label;
        }

        @Override
        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }

        @Override
        protected void fireEditingStopped() {
            super.fireEditingStopped();
        }
    }

    // Phương thức download file
    private void downloadFile(String fileName, String sender) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file");
        fileChooser.setSelectedFile(new File(fileName));
        
        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            
            // Hiển thị thông báo mô phỏng việc tải xuống
            JOptionPane.showMessageDialog(this, 
                "Đang tải xuống file: " + fileName + "\n" +
                "Từ người gửi: " + sender + "\n" +
                "Lưu tại: " + fileToSave.getAbsolutePath() + "\n\n" +
                "Lưu ý: Đây là tính năng mô phỏng. File thực tế không được tải về\n" +
                "do server không hỗ trợ tính năng tải file từ lịch sử.",
                "Thông tin tải xuống",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        boolean driverFound = false;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            driverFound = true;
            System.out.println("MySQL JDBC Driver loaded successfully!");
        } catch (ClassNotFoundException e) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                driverFound = true;
                System.out.println("MySQL JDBC Driver (older version) loaded successfully!");
            } catch (ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(null, 
                    "Không tìm thấy MySQL JDBC Driver!\n\n" +
                    "Hãy chắc chắn đã thêm thư viện MySQL Connector/J vào project.\n" +
                    "Tải về từ: https://dev.mysql.com/downloads/connector/j/",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
        
        if (!driverFound) {
            return;
        }
        
        // Test database connection
        try (Connection testConn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            System.out.println("Database connection test successful!");
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, 
                "Không thể kết nối đến database!\n" +
                "URL: " + DB_URL + "\n" +
                "Nguyên nhán: " + e.getMessage() + "\n\n" +
                "Hãy kiểm tra:\n" +
                "1. MySQL Server có đang chạy không?\n" +
                "2. Database 'truyen_fileTCP' có tồn tại không?\n" +
                "3. Username và password có đúng không?", 
                "Lỗi kết nối database", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return;
        }
        
        SwingUtilities.invokeLater(() -> {
            String inputUsername = JOptionPane.showInputDialog(null, "Nhập tên người dùng:", "Đăng nhập", JOptionPane.QUESTION_MESSAGE);
            if (inputUsername == null || inputUsername.trim().isEmpty()) {
                inputUsername = "user123";
            }
            new FileClient(inputUsername.trim()).setVisible(true);
        });
    }
}