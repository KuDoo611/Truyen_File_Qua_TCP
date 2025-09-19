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
Chức năng chính cơ bản:br

1.1 Client:br
Giao diện Đăng nhập, Đăng ký.br
Gửi file: Cho phép người dùng chọn file và gửi đến người nhận khácbr
Quản lý lịch sử: Xem lịch sử file đã gửi và file đã nhậnbr
2. Server:br
Lắng nghe kết nối: Chấp nhận kết nối từ các client trên cổng 12345
Quản lý truyền file: Xử lý việc nhận và lưu trữ file từ client
Hiển thị thông tin: Hiển thị thông báo kết nối và lịch sử truyền file
Quản lý database: Lưu trữ thông tin về các lần truyền file
3. Database:
Quản lý người dùng: Đăng ký, đăng nhập, kiểm tra user
Lưu trữ lịch sử: Ghi nhận các lần gửi/nhận file với trạng thái
Kết nối database: Quản lý kết nối đến MySQL database
## 🔧 2. Ngôn ngữ lập trình sử dụng: [![Java](https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=java&logoColor=white)](https://www.java.com/)

## 🚀 3. Các project đã thực hiện

### [Khoá 16](./docs/projects/K16/README.md)

## 📝 4. License

© 2025 AIoTLab, Faculty of Information Technology, DaiNam University. All rights reserved.

---
