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
  
  <img width="428" height="392" alt="image" src="https://github.com/user-attachments/assets/2c479f08-73ee-403a-83d8-b83fa760ea00" /><br/>

  <p align="center"><i>Hình 1: Giao diện Đăng nhập</i>
</p>
<br/>

- **Giao diện Đăng ký**
  
<img width="429" height="394" alt="image" src="https://github.com/user-attachments/assets/6747bac6-ac32-431c-899b-86efdf8dcc23" /><br/>

  <p align="center"><i>Hình 2: Giao diện Đăng ký</i>
</p>
<br/>

- **Giao diện người dùng**
- 
<img width="729" height="542" alt="image" src="https://github.com/user-attachments/assets/18d482a1-bad2-4f61-ad1f-42dadb26fd6a" /><br/>

  <p align="center"><i>Hình 3: Giao diện người dùng</i>
</p>
<br/>

- **Giao diện người dùng truyền file**
- 
<img width="1534" height="839" alt="image" src="https://github.com/user-attachments/assets/618c7a4d-9e31-4d0e-98eb-54b561bc3f9d" /><br/>

  <p align="center"><i>Hình 4: Giao diện người  dùng truyền file và server lưu lại thông báo</i>
</p>
<br/>

## 📝 4. Các bước cài đặt  
#### Bước 1: Chuẩn bị môi trường
1. **Kiểm tra Java**: Mở terminal/command prompt và chạy:
   ```bash
   java -version
   javac -version
   ```
   Đảm bảo cả hai lệnh đều hiển thị phiên bản Java 8 trở lên.

2. **Chuẩn bị IDE**: Khởi động Eclipse IDE và chọn workspace là thư mục vừa tạo.

#### Bước 2: Tạo project và cấu trúc
1. **Tạo Java Project**:
   - **File** → **New** → **Java Project**
   - **Project name**: `TCPFileTransfer`
   - **JRE**: Sử dụng default JRE (*Java 21*)
   - Bỏ check **"Create module-info.java file"**
   - Click **Finish**

2. **Tạo cấu trúc package**: Trong thư mục `src`, tạo các package:
   ```
   src/
   ├── server/
   ├── client/
   ├── common/
   └── utils/
   ```
   *Cách tạo: Right-click `src` → **New** → **Package** → Nhập tên package → **Finish***

3. **Tạo các file Java**:
   - `server/TCPFileServer.java` (*với main method*)
   - `server/ClientHandler.java` (*implement Runnable*)
   - `client/TCPFileClient.java`
   - `client/ClientGUI.java` (*extends JFrame, với main method*)
   - `common/FileInfo.java`
   - `utils/FileUtils.java`

#### Bước 3: Copy mã nguồn
1. **Copy source code**: Sao chép nội dung code vào từng file tương ứng đã tạo.

2. **Organize imports**: Sử dụng **Ctrl+Shift+O** để tự động import các thư viện cần thiết.

3. **Kiểm tra lỗi**: Đảm bảo không có lỗi compile trong Project Explorer.

#### Bước 4: Chạy ứng dụng

**Khởi động Server:**
1. **Right-click** file `TCPFileServer.java`
2. **Run As** → **Java Application**
3. Server sẽ khởi động trên port **12345** mặc định
4. Console hiển thị:
   ```
   Server đã khởi động trên port 12345
   Đang chờ client kết nối...
   ```

**Khởi động Client:**
1. **Right-click** file `ClientGUI.java`
2. **Run As** → **Java Application**  
3. Giao diện GUI sẽ xuất hiện
4. Click nút **"Kết Nối"** để kết nối đến Server
5. Status sẽ chuyển thành **"Đã kết nối"** (*màu xanh*)
6. Server console sẽ hiển thị: `Client đã kết nối: /127.0.0.1`

---


## 📌 5. Liên hệ - **Sinh viên thực hiện:** **Nguyễn Xuân Thuận**
 - 🌐 Website: [FIT DNU](https://dainam.edu.vn/vi/khoa-cong-nghe-thong-tin)
 - 📧 Email: [xuanthuan611@gmail.com](mailto:xuanthuan611@gmail.com)
 - 📱 Fanpage: [AIoTLab - FIT DNU](https://www.facebook.com/DNUAIoTLab)


