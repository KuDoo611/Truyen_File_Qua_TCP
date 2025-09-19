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
        <img src="docs/fitdnu_logo.png" alt="FIT DNU Logo" width="180"/>
        <img src="docs/dnu_logo.png" alt="DaiNam University Logo" width="200"/>
    </p>

[![AIoTLab](https://img.shields.io/badge/AIoTLab-green?style=for-the-badge)](https://www.facebook.com/DNUAIoTLab)
[![Faculty of Information Technology](https://img.shields.io/badge/Faculty%20of%20Information%20Technology-blue?style=for-the-badge)](https://dainam.edu.vn/vi/khoa-cong-nghe-thong-tin)
[![DaiNam University](https://img.shields.io/badge/DaiNam%20University-orange?style=for-the-badge)](https://dainam.edu.vn)

</div>

---

## 📖 1. Giới thiệu

Hệ thống **Truyền file qua TCP** được xây dựng nhằm mô phỏng quá trình truyền file giữa các máy tính trong mạng thông qua giao thức TCP.  

**Mục tiêu chính:**
- Hiểu rõ cách hoạt động của giao thức TCP trong việc truyền dữ liệu.  
- Nắm vững cơ chế kết nối Client – Server.  
- Thực hành xử lý dữ liệu file (upload/download).  
- Xây dựng giao diện người dùng thân thiện với Java Swing.  

**Chức năng chính:**

**1.1 Client**  
- Giao diện Đăng nhập, Đăng ký.  
- Gửi file: Cho phép người dùng chọn file và gửi đến người nhận khác.  
- Quản lý lịch sử: Xem lịch sử file đã gửi và file đã nhận.  

**1.2 Server**  
- Lắng nghe kết nối: Chấp nhận kết nối từ client trên cổng `12345`.  
- Quản lý truyền file: Nhận và lưu trữ file từ client.  
- Hiển thị thông tin: Thông báo kết nối và lịch sử truyền file.  

**1.3 Database**  
- Quản lý người dùng: Đăng ký, đăng nhập, kiểm tra user.  
- Lưu trữ lịch sử: Ghi nhận các lần gửi/nhận file với trạng thái.  
- Kết nối MySQL qua JDBC.  

---

## 🔧 2. Công nghệ sử dụng

- **Ngôn ngữ lập trình:** Java (JDK 8+)  
- **Giao thức mạng:** TCP Socket (`java.net.Socket`, `java.net.ServerSocket`)  
- **Xử lý đa luồng:** `Thread` (`java.lang.Thread`)  
- **Giao diện:** Java Swing (`JFrame`, `JButton`, `JTable`, `JProgressBar`, `JFileChooser`)  
- **Truyền dữ liệu:** `DataInputStream` và `DataOutputStream`  

📚 **Thư viện sử dụng**  
- `java.net` – Socket communication  
- `java.io` – File I/O operations  
- `javax.swing` – GUI components  
- `java.awt` – Layout managers & Color  
- `java.sql` – Database connectivity (JDBC)  
- `java.text` – Date formatting  
- `java.util` – Collections & utilities  

🗄️ **Cơ sở dữ liệu**  
- Hệ quản trị: MySQL  
- JDBC Driver: `mysql-connector-java`  
- Schema: `truyen_fileTCP`  
- Tables:  
  - `users` – Quản lý người dùng  
  - `history` – Lịch sử truyền file  

---

## ✨ Tính năng chính

- Gửi/nhận file giữa các client.  
- Xác thực người dùng qua database.  
- Theo dõi tiến trình truyền file real-time.  
- Lưu trữ lịch sử truyền file với trạng thái.  
- Giao diện quản lý trực quan.  

---

## 🖥️ Công cụ & Môi trường phát triển

- Công cụ phát triển: **Eclipse IDE / IntelliJ IDEA**  
- Phiên bản JDK: **Java SE 8+ (khuyến nghị Java 11+)**  
- Database: **MySQL 5.7+ hoặc 8.0+**  
- Hệ điều hành: **Windows 10/11** (đa nền tảng: Linux, macOS)  

---

## 🚀 3. Một số hình ảnh hệ thống

- **Giao diện Đăng nhập**  
<p align="center">
  <img width="430" height="385" alt="Hình 1" src="docs/Hình1.png"/>
</p>
<p align="center"><i>Hình 1. Giao diện đăng nhập</i></p>

- **Giao diện Chọn file**  
<p align="center">
  <img width="430" height="385" alt="Hình 2" src="docs/image2.png"/>
</p>
<p align="center"><i>Hình 2. Giao diện chọn file</i></p>

- **Giao diện Thông báo và hiển thị thông tin file**  
<p align="center">
  <img width="430" height="385" alt="Hình 3" src="docs/image3.png"/>
</p>
<p align="center"><i>Hình 3. Thông báo & hiển thị file</i></p>

- **Giao diện Tìm kiếm file**  
<p align="center">
  <img width="430" height="385" alt="Hình 4" src="docs/image4.png"/>
</p>
<p align="center"><i>Hình 4. Giao diện tìm kiếm</i></p>

---

## 📝 4. Các bước cài đặt  

*(Phần này bạn có thể bổ sung hướng dẫn chi tiết: clone project, cấu hình database, chạy server & client...)*  

---

## 📌 5. Liên hệ  

- **Sinh viên thực hiện:** **Nguyễn Xuân Thuận**  
- 🌐 Website: [FIT DNU](https://dainam.edu.vn/vi/khoa-cong-nghe-thong-tin)  
- 📧 Email: [xuanthuan611@gmail.com](mailto:xuanthuan611@gmail.com)  
- 📱 Fanpage: [AIoTLab - FIT DNU](https://www.facebook.com/DNUAIoTLab)  
