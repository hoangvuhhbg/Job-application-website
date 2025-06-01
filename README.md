# Job-application-website
Để chạy được web site:
- Download folder này
- Máy được cài sẵn maven
- Dùng MySQL làm database

- Sau khi cài được maven, chạy vscode open folder và chọn folder job-web.
- Trong folder Controller trong backend, config lại file Conn.java dòng 18 (c = DriverManager.getConnection("jdbc:mysql://localhost:3306/job_application", "root", "nghvpbm28122004"); với job_application là tên database và nghvpbm28122004 là mật khẩu root
- Thầy tạo database tên job_application hoặc tên khác cũng được chỉ cần đổi tên database trong file trùng với database trong máy, và chỉnh lại mật khẩu trong file theo mật khẩu root máy của thầy
- Sau khi tạo database, set database thành default schema và chạy 6 file trong folder scripts theo thứ tự User -> Company -> job -> cv_upload -> cv_job -> savedjob do có cài đặt khóa ngoại. Đó là cấu trúc bảng và dữ liệu cũ của em.

Bắt đầu trang web bằng cách truy cập http://localhost:8080/User/Home trên google chrome

Hoạt động login:
- User, Admin vào bằng trang của ứng viên chạy vào trang tiếp tùy thuộc vào quyền
- Company vào bằng trang của NTD
- Sửa quyền trực tiếp của user -> admin hoặc ngược lại trực tiếp trong MySQL.
- Quyền là cột Position với giá trị admin cho admin, user cho user, company cho company
- Tài khoản bị khóa sẽ không thể đăng nhập được và quay lại trang ban đầu.
- Em xin hết ạ.

Nếu chạy vẫn lỗi do kết nối sql thì thầy cài thêm thư viên jdbc để kết nối với sql ạ.
