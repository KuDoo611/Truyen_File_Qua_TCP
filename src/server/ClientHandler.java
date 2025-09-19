package server;

import database.DBHelper;

import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClientHandler implements Runnable {
    private Socket socket;
    private DefaultTableModel model;
    private FileServer fileServer;

    public ClientHandler(Socket socket, DefaultTableModel model, FileServer fileServer) {
        this.socket = socket;
        this.model = model;
        this.fileServer = fileServer;
    }

    @Override
    public void run() {
        try (DataInputStream dis = new DataInputStream(socket.getInputStream())) {
            String clientAddress = socket.getInetAddress().getHostAddress();
            fileServer.addMessage("Đang xử lý client: " + clientAddress);

            // Đọc metadata: người gửi, người nhận, tên file, kích thước
            String senderName = dis.readUTF();
            String receiverName = dis.readUTF();
            String fileName = dis.readUTF();
            long fileSize = dis.readLong();

            fileServer.addMessage("Đang nhận file: " + fileName + " từ " + senderName);

            // Lưu file vào thư mục uploads
            FileOutputStream fos = new FileOutputStream("uploads/" + fileName);
            byte[] buffer = new byte[4096];
            long totalRead = 0;
            int read;
            
            while (totalRead < fileSize && (read = dis.read(buffer, 0, (int) Math.min(buffer.length, fileSize - totalRead))) > 0) {
                fos.write(buffer, 0, read);
                totalRead += read;
            }
            fos.close();

            // Cập nhật UI
            SwingUtilities.invokeLater(() -> {
                model.addRow(new Object[]{
                    senderName, 
                    receiverName, 
                    fileName, 
                    fileSize / 1024, 
                    "Thành công",
                    new SimpleDateFormat("HH:mm:ss dd/MM/yyyy").format(new Date()) // Thêm thời gian
                });
            });

            // Lưu vào database với cả người gửi và người nhận
            DBHelper.insertHistory(senderName, receiverName, fileName, fileSize, "Thành công");

            fileServer.addMessage("Đã nhận file thành công: " + fileName + " (" + (fileSize/1024) + " KB)");

        } catch (Exception e) {
            e.printStackTrace();
            fileServer.addMessage("Lỗi khi xử lý client: " + e.getMessage());
        } finally {
            try {
                if (socket != null && !socket.isClosed()) {
                    socket.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}