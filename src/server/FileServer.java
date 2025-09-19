package server;

import database.DBHelper;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileServer extends JFrame {
    private JTable connectionTable;
    private JTable fileTransferTable;
    private javax.swing.table.DefaultTableModel connectionModel;
    private javax.swing.table.DefaultTableModel fileTransferModel;
    private ServerSocket serverSocket;
    private boolean isServerRunning = true;
    private JLabel lblStatus;
    private JButton btnStopServer;

    public FileServer() {
        setTitle("Server - Nhận file");
        setSize(1200, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel tiêu đề và trạng thái
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lblStatus = new JLabel("Server đang lắng nghe cổng 12345...");
        lblStatus.setFont(new Font("Arial", Font.BOLD, 14));
        statusPanel.add(lblStatus);
        
        topPanel.add(statusPanel, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        // Panel chính chứa 2 bảng chia trái-phải
        JSplitPane mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        mainSplitPane.setDividerLocation(400);
        mainSplitPane.setResizeWeight(0.4);

        // Panel bên trái: Thông báo kết nối
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY), "Thông báo kết nối"));
        
        String[] connectionColNames = {"Thời gian", "Thông báo"};
        connectionModel = new javax.swing.table.DefaultTableModel(connectionColNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        connectionTable = new JTable(connectionModel);
        connectionTable.getColumnModel().getColumn(0).setPreferredWidth(150);
        connectionTable.getColumnModel().getColumn(1).setPreferredWidth(250);
        JScrollPane connectionScrollPane = new JScrollPane(connectionTable);
        connectionScrollPane.setPreferredSize(new Dimension(380, 0));
        leftPanel.add(connectionScrollPane, BorderLayout.CENTER);

        // Panel bên phải: Lịch sử truyền file
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY), "Lịch sử truyền file"));
        
        String[] fileTransferColNames = {"Người gửi", "Người nhận", "Tên file", "Dung lượng (KB)", "Trạng thái", "Thời gian"};
        fileTransferModel = new javax.swing.table.DefaultTableModel(fileTransferColNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        fileTransferTable = new JTable(fileTransferModel);
        JScrollPane fileTransferScrollPane = new JScrollPane(fileTransferTable);
        rightPanel.add(fileTransferScrollPane, BorderLayout.CENTER);

        mainSplitPane.setLeftComponent(leftPanel);
        mainSplitPane.setRightComponent(rightPanel);
        add(mainSplitPane, BorderLayout.CENTER);

        // Panel nút điều khiển
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        JButton btnHistory = new JButton("Xem lịch sử đầy đủ");
        customizeButton(btnHistory, new Color(70, 130, 180));
        
        JButton btnClear = new JButton("Xóa thông báo");
        customizeButton(btnClear, new Color(220, 20, 60));
        
        btnStopServer = new JButton("Dừng Server");
        customizeButton(btnStopServer, new Color(178, 34, 34));
        
        bottomPanel.add(btnHistory);
        bottomPanel.add(btnClear);
        bottomPanel.add(btnStopServer);
        add(bottomPanel, BorderLayout.SOUTH);

        btnHistory.addActionListener(e -> showHistory());
        btnClear.addActionListener(e -> {
            connectionModel.setRowCount(0);
            fileTransferModel.setRowCount(0);
        });
        btnStopServer.addActionListener(e -> stopServer());

        // Tạo thư mục uploads nếu chưa tồn tại
        File uploadsDir = new File("uploads");
        if (!uploadsDir.exists()) {
            uploadsDir.mkdir();
        }

        // Thêm thông báo server khởi động
        addConnectionMessage("Server khởi động và đang lắng nghe cổng 12345");

        // Thread lắng nghe client
        new Thread(() -> listen()).start();
    }

    // Phương thức để tùy chỉnh nút với màu sắc phù hợp
    private void customizeButton(JButton button, Color backgroundColor) {
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setBorderPainted(false);
        
        // Thêm hiệu ứng hover
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(backgroundColor.darker());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(backgroundColor);
            }
        });
    }

    private void addConnectionMessage(String message) {
        SwingUtilities.invokeLater(() -> {
            String timestamp = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy").format(new Date());
            connectionModel.addRow(new Object[]{timestamp, message});
            // Tự động cuộn xuống dòng mới nhất
            connectionTable.scrollRectToVisible(connectionTable.getCellRect(connectionTable.getRowCount()-1, 0, true));
        });
    }

    private void addFileTransferRecord(String sender, String receiver, String filename, long fileSize, String status) {
        SwingUtilities.invokeLater(() -> {
            String timestamp = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy").format(new Date());
            fileTransferModel.addRow(new Object[]{sender, receiver, filename, fileSize / 1024, status, timestamp});
            // Tự động cuộn xuống dòng mới nhất
            fileTransferTable.scrollRectToVisible(fileTransferTable.getCellRect(fileTransferTable.getRowCount()-1, 0, true));
        });
    }

    private void listen() {
        try {
            serverSocket = new ServerSocket(12345);
            while (isServerRunning) {
                Socket socket = serverSocket.accept();
                String clientAddress = socket.getInetAddress().getHostAddress();
                addConnectionMessage("Client kết nối từ: " + clientAddress);
                new Thread(new ClientHandler(socket, fileTransferModel, this)).start();
            }
        } catch (Exception e) {
            if (isServerRunning) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi server: " + e.getMessage());
            }
        }
    }

    private void stopServer() {
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Bạn có chắc chắn muốn dừng server?",
            "Xác nhận dừng server",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );
        
        if (confirm == JOptionPane.YES_OPTION) {
            isServerRunning = false;
            try {
                if (serverSocket != null && !serverSocket.isClosed()) {
                    serverSocket.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            
            lblStatus.setText("Server đã dừng");
            btnStopServer.setEnabled(false);
            addConnectionMessage("Server đã dừng thành công");
            JOptionPane.showMessageDialog(this, "Server đã dừng thành công!");
        }
    }

    private void showHistory() {
        HistoryDialog historyDialog = new HistoryDialog(this);
        historyDialog.setVisible(true);
    }

    // Phương thức để ClientHandler có thể thêm thông báo
    public void addMessage(String message) {
        addConnectionMessage(message);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            FileServer server = new FileServer();
            server.setVisible(true);
        });
    }

	public void decrementClientCount() {
		// TODO Auto-generated method stub
		
	}
}

// Dialog để hiển thị lịch sử chi tiết
class HistoryDialog extends JDialog {
    public HistoryDialog(JFrame parent) {
        super(parent, "Lịch sử truyền file đầy đủ", true);
        setSize(900, 500);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        String[] colNames = {"Người gửi", "Người nhận", "Tên file", "Dung lượng (KB)", "Trạng thái", "Thời gian"};
        javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel(colNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        try (var conn = DBHelper.getConnection();
             var ps = conn.prepareStatement("SELECT * FROM history ORDER BY transfer_time DESC");
             var rs = ps.executeQuery()) {
            
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("sender_name"),
                    rs.getString("receiver_name"),
                    rs.getString("file_name"),
                    rs.getLong("file_size") / 1024,
                    rs.getString("status"),
                    rs.getTimestamp("transfer_time")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi tải lịch sử: " + e.getMessage());
        }

        JTable table = new JTable(model);
        table.setAutoCreateRowSorter(true);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnClose = new JButton("Đóng");
        btnClose.addActionListener(e -> dispose());
        buttonPanel.add(btnClose);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}