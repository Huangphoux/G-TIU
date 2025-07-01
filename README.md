# 📱 G-TIU - Ứng dụng Quản Lý Chi Tiêu Cá Nhân

**G-TIU** là một ứng dụng Android giúp người dùng theo dõi và quản lý chi tiêu hằng ngày một cách dễ dàng và trực quan. Ứng dụng cho phép người dùng ghi lại các khoản thu chi, phân loại, thống kê và hiển thị biểu đồ chi tiêu theo thời gian.

---

## 🚀 Tính năng chính:

- ✅ Ghi nhận các khoản thu và chi.
- 📊 Thống kê chi tiêu theo ngày, tuần, tháng.
- 🧾 Phân loại chi tiêu theo danh mục (ăn uống, mua sắm, hoá đơn, v.v.).
- 🔍 Tìm kiếm và lọc giao dịch.
- 🎨 Giao diện thân thiện, dễ sử dụng.

---

## 🛠 Yêu cầu hệ thống:

- Android Studio **Giraffe** trở lên (khuyến khích bản mới nhất).
- JDK 17+
- Gradle 8.1 trở lên (sử dụng Gradle wrapper kèm project).
- Thiết bị Android API 26 trở lên (Android 8.0+).

---

## ⚙️ Hướng dẫn cài đặt và chạy project

### 1. Clone dự án về máy

```bash
git clone https://github.com/Huangphoux/G-TIU.git
```

### 2. Mở bằng Android Studio

- Mở Android Studio
- Chọn **"Open"** và trỏ đến thư mục `G-TIU` vừa clone
- Android Studio sẽ tự động sync Gradle và tải các dependencies

### 3. Chạy ứng dụng

- Cắm thiết bị Android thật hoặc tạo máy ảo AVD
- Nhấn nút **Run** ▶️ hoặc dùng tổ hợp phím `Shift + F10`

---

## 📁 Cấu trúc thư mục:

```
G-TIU/
├── app/                     # Mã nguồn chính của ứng dụng
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/        # Code Kotlin
│   │   │   ├── res/         # Giao diện XML
│   │   │   └── AndroidManifest.xml
│   └── build.gradle         # Cấu hình module app
├── build.gradle             # Cấu hình build toàn bộ project
├── settings.gradle          # Danh sách các module
└── gradle/                  # Gradle wrapper
```

---

## 🧩 Các thư viện sử dụng

- [AndroidX](https://developer.android.com/jetpack/androidx)
- [SQLite](https://developer.android.com/training/data-storage/sqlite)
- [LiveData & ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
- [MPAndroidChart](https://github.com/PhilJay/MPAndroidChart) – biểu đồ thống kê
- [Material Components](https://m3.material.io/)

---

## 🐞 Các lỗi thường gặp

| Lỗi | Nguyên nhân | Cách khắc phục |
|-----|-------------|----------------|
| `SDK location not found` | Chưa cài đặt Android SDK | Cài qua Android Studio > Preferences > SDK |
| `Failed to resolve dependencies` | Mất kết nối mạng hoặc proxy | Kiểm tra Internet hoặc cấu hình proxy |
| `Build failed` | Phiên bản Gradle/Plugin không tương thích | Cập nhật Android Studio và Gradle Wrapper |

---

## 🤝 Đóng góp:

Chúng tôi hoan nghênh mọi đóng góp!

1. Fork repository.
2. Tạo nhánh mới `feature/ten-tinh-nang`.
3. Commit thay đổi.
4. Tạo Pull Request để được xem xét.

---

## 📄 Giấy phép:

Dự án được phát hành dưới giấy phép [MIT License](LICENSE).

---

## 📬 Liên hệ:

Nếu bạn gặp lỗi hoặc có góp ý, vui lòng mở [Issue](https://github.com/Huangphoux/G-TIU/issues) hoặc liên hệ qua GitHub.

---

Cảm ơn bạn đã sử dụng G-TIU !