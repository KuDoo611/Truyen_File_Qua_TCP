<h2 align="center">
    <a href="https://dainam.edu.vn/vi/khoa-cong-nghe-thong-tin">
    🎓 Faculty of Information Technology (DaiNam University)
    </a>
</h2>
<h2 align="center">
   TRUYỀN FILE QUA TCP
</h2>
<div align="center">
    <p align="center">
        <img src="docs/aiotlab_logo.png" alt="AIoTLab Logo" width="170"/>
        <img src="docs/fitdnu_logo.png" alt="AIoTLab Logo" width="180"/>
        <img src="docs/dnu_logo.png" alt="DaiNam University Logo" width="200"/>
    </p>

[![AIoTLab](https://img.shields.io/badge/AIoTLab-green?style=for-the-badge)](https://www.facebook.com/DNUAIoTLab)
[![Faculty of Information Technology](https://img.shields.io/badge/Faculty%20of%20Information%20Technology-blue?style=for-the-badge)](https://dainam.edu.vn/vi/khoa-cong-nghe-thong-tin)
[![DaiNam University](https://img.shields.io/badge/DaiNam%20University-orange?style=for-the-badge)](https://dainam.edu.vn)

</div>

## 📖 1. Giới thiệu
Hệ thống Truyền file qua TCP được xây dựng nhằm mô phỏng quá trình truyền file giữa các máy tính trong mạng thông qua giao thức TCP. Trong mô hình này, Server đóng vai trò trung gian, chịu trách nhiệm lắng nghe kết nối từ Client, tiếp nhận dữ liệu (file) từ Client gửi đến và lưu trữ file trên server.

Mục tiêu chính:

Hiểu rõ cách hoạt động của giao thức TCP trong việc truyền dữ liệu.

Nắm vững cơ chế kết nối Client – Server.

Thực hành xử lý dữ liệu file (upload/download).

Xây dựng giao diện người dùng thân thiện với Java Swing.

Chức năng chính cơ bản:

1.1 Client:

Giao diện Đăng nhập, Đăng ký.

Gửi file: Cho phép người dùng chọn file và gửi đến người nhận khác

Quản lý lịch sử: Xem lịch sử file đã gửi và file đã nhận

2. Server:
3. Database
Lắng nghe kết nối: Chấp nhận kết nối từ các client trên cổng 12345

Quản lý truyền file: Xử lý việc nhận và lưu trữ file từ client

Hiển thị thông tin: Hiển thị thông báo kết nối và lịch sử truyền file

Quản lý database: Lưu trữ thông tin về các lần truyền file

5. Database:

Quản lý người dùng: Đăng ký, đăng nhập, kiểm tra user

Lưu trữ lịch sử: Ghi nhận các lần gửi/nhận file với trạng thái

Kết nối database: Quản lý kết nối đến MySQL 

## 🔧 2. Công nghệ sử dụng
Ngôn ngữ lập trình: Java (JDK 8+)
Giao thức mạng: TCP Socket (java.net.Socket, java.net.ServerSocket)
Xử lý đa luồng: Thread (java.lang.Thread)
Công nghệ giao diện: Java Swing (JFrame, JButton, JTable, JProgressBar, JFileChooser)
Cơ chế truyền dữ liệu: DataInputStream và DataOutputStream để truyền file và metadata

📚 Thư viện sử dụng

java.net - Socket communication

java.io - File I/O operations

javax.swing - GUI components

java.awt - Layout managers và Color

java.sql - Database connectivity (JDBC)

java.text - Date formatting

java.util - Collections và utility classes

🗄️ Cơ sở dữ liệu

Hệ quản trị: MySQL

Kết nối: JDBC Driver (mysql-connector-java)

Schema: truyen_fileTCP

Tables: users (quản lý người dùng), history (lịch sử truyền file)

🔧 Tính năng chính

Gửi/nhận file giữa các client

Xác thực người dùng qua database

Theo dõi tiến trình truyền file real-time

Lưu trữ lịch sử truyền file với trạng thái

Giao diện quản lý file trực quan

🖥️ Công cụ & Môi trường phát triển

Công cụ phát triển: Eclipse IDE / IntelliJ IDEA

Phiên bản JDK: Java SE 8+ (khuyến nghị Java 11+)

Database: MySQL 5.7+ hoặc 8.0+

Hệ điều hành: Windows 10/11 (đa nền tảng: Linux, macOS)


## 🚀 3. Một số hình ảnh hệ thống
Giao diện đăng nhập

## 📝 4. Các bước cài đặt 

## 5. Liên hệ cá nhân
Sinh viên thực hiện: Nguyễn Xuân Thuận
Websize: https://dainam.edu.vn/vi/khoa-cong-nghe-thong-tin
Email: [xuanthuan611@gmail.com]
Fanpage: https://www.facebook.com/DNUAIoTLab
