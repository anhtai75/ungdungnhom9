
# Ứng Dụng Nhóm 9 - Ứng dụng Sức khỏe & Dinh dưỡng VitaCare

Đây là một ứng dụng Android được phát triển bởi Nhóm 9 trong khuôn khổ môn học lập trình di động. Dự án sử dụng ngôn ngữ Java và hệ thống build Gradle, tập trung vào quản lý sức khỏe và dinh dưỡng.

## 🚀 Tính năng

- **Quản lý dinh dưỡng** - Theo dõi chế độ ăn, công thức nấu ăn, và lượng calories
- **Kiến thức y tế** - Thông tin về các loại bệnh và cách xử lý
- **Hỗ trợ khẩn cấp** - Định vị bệnh viện gần nhất và danh bạ số khẩn cấp
- **Ghi chú sức khỏe** - Lưu trữ và quản lý các ghi chú liên quan đến sức khỏe
- **Quản lý bài viết** - Đọc và theo dõi các bài viết về sức khỏe

## 🛠️ Cấu trúc dự án

```
ungdungnhom9/
├── app/                  # Mã nguồn chính của ứng dụng Android
├── .idea/                # Cấu hình của Android Studio
├── gradle/               # Cấu hình hệ thống build Gradle
├── build.gradle.kts      # Tập tin cấu hình build chính
├── settings.gradle.kts   # Tập tin cấu hình các module
└── gradlew / gradlew.bat # Script để chạy Gradle
```

## 📱 Mô tả các màn hình
📸 Ảnh chụp màn hình
<p align="center">
  <img src="screenshots/Screenshot 2025-04-28 212929.png" width="200" alt="Màn hình chính">
  <img src="screenshots/Screenshot 2025-04-28 213016.png" width="200" alt="Màn hình dinh dưỡng">
  <img src="screenshots/Screenshot 2025-04-28 213026.png" width="200" alt="Màn hình chế độ ăn">
  <img src="screenshots/Screenshot 2025-04-28 212945.png" width="200" alt="Màn hình khẩn cấp">
  <img src="screenshots/Screenshot 2025-04-28 212936.png" width="200" alt="Màn hình kiến thức bệnh cơ bản">
  <img src="screenshots/Screenshot 2025-04-28 213007.png" width="200" alt="Màn hình cài đặt">
</p>
### Màn hình chính và Điều hướng

#### Màn hình Chính (activity_main.xml)
- Hiển thị thông báo nhắc nhở sức khỏe
- Thanh tìm kiếm bài viết
- Lịch để theo dõi và lập kế hoạch
- Khu vực ghi chú với khả năng lưu trữ
- Hiển thị danh sách bài viết
- Thanh điều hướng cố định ở dưới cùng với các biểu tượng: Trang chủ, Hồ sơ, Bệnh viện, Kiến thức dinh dưỡng, Dinh dưỡng, và Cài đặt

#### Màn hình Dinh dưỡng (activity_dinhduong.xml)
- Giao diện chính cho mục dinh dưỡng với nền gradient
- Hiển thị biểu tượng dinh dưỡng lớn
- Ba tùy chọn chính:
  - Theo dõi dinh dưỡng
  - Chế độ ăn
  - Công thức

### Màn hình Quản lý Dinh dưỡng

#### Màn hình Theo dõi Dinh dưỡng (activity_track_nutrition.xml)
- Hiển thị thông tin dinh dưỡng hiện tại
- Biểu đồ tròn trực quan về tỷ lệ dinh dưỡng
- Danh sách công thức gợi ý
- Nút thêm chế độ ăn mới

#### Màn hình Thêm Chế độ Ăn (activity_add_diet.xml)
- Form nhập liệu đầy đủ cho chế độ ăn mới với các trường:
  - Tên chế độ ăn
  - Mô tả
  - Mục tiêu dinh dưỡng
  - Lượng calories
  - Ngày bắt đầu và kết thúc
  - Dị ứng (có danh sách checkbox cho các dị ứng phổ biến)
  - Tải lên hình ảnh
  - Các nút: Lưu, Đặt lại, Hủy

#### Màn hình Chế độ Ăn (activity_diets.xml)
- Thanh tìm kiếm chế độ ăn
- Danh sách các chế độ ăn dưới dạng thẻ (card)
- Nút điều hướng đến công thức và theo dõi dinh dưỡng

#### Màn hình Công thức (activity_recipes.xml)
- Danh sách các công thức nấu ăn
- Nút thêm công thức mới

#### Màn hình Thêm Công thức (activity_add_recipe.xml)
- Form nhập liệu cho công thức mới với các trường:
  - Tên công thức
  - Mô tả
  - Nguyên liệu
  - Hướng dẫn
  - Chọn hình ảnh
  - Nút lưu

### Màn hình Sức Khỏe và Y tế

#### Màn hình Bệnh (activity_second.xml)
- Hiển thị kiến thức chung về sức khỏe
- Lời khuyên sức khỏe
- Danh sách các loại bệnh phổ biến

#### Màn hình Thông tin Bệnh (thongtinbenh.xml)
- Hiển thị thông tin chi tiết về một loại bệnh cụ thể:
  - Triệu chứng
  - Việc nên làm
  - Việc không nên làm
  - Cách xử lý
  - Nút đóng

#### Màn hình Sơ cứu (activity_socuu.xml)
- Kiến thức sơ cứu cơ bản
- Châm ngôn sống về sức khỏe
- Danh sách các tình huống cần sơ cứu

#### Màn hình Khẩn cấp (fragment_emergency.xml)
- Tiêu đề "KHẨN CẤP" nổi bật
- Ghi chú quan trọng về tình huống khẩn cấp
- Hai tùy chọn chính:
  - Tìm bệnh viện gần nhất (bản đồ)
  - Liên hệ khẩn cấp

#### Màn hình Bệnh viện (fragment_hospital.xml)
- Thanh tìm kiếm bệnh viện
- Nút tìm bệnh viện gần nhất
- Chú thích phân loại bệnh viện
- Hiển thị bản đồ các bệnh viện

#### Màn hình Gọi Khẩn cấp (fragment_call.xml)
- Danh sách số điện thoại khẩn cấp: Cảnh sát, Cứu hỏa, Cấp cứu
- Tùy chọn thêm liên hệ khẩn cấp cá nhân
- Nút quay lại

### Màn hình Bài viết và Ghi chú

#### Màn hình Chi tiết Bài viết (activity_article_detail.xml)
- Hiển thị chi tiết bài viết với:
  - Tiêu đề
  - Mô tả
  - Nội dung đầy đủ

#### Màn hình Bài viết Đã thích (activity_liked_articles.xml)
- Danh sách các bài viết người dùng đã thích

#### Màn hình Danh sách Ghi chú (activity_notes_list.xml)
- Hiển thị danh sách các ghi chú đã lưu
- Nút quay lại

#### Màn hình Kết quả Tìm kiếm (activity_search_results.xml)
- Hiển thị kết quả tìm kiếm bài viết

### Cài đặt và Tùy chọn

#### Màn hình Cài đặt (activity_settings.xml)
- Tùy chọn ngôn ngữ: Tiếng Anh, Tiếng Việt, Tiếng Tây Ban Nha
- Cài đặt hiển thị:
  - Chế độ tối
  - Thông báo
  - Âm thanh
  - Kích thước chữ (thanh trượt điều chỉnh)
- Nút quay lại

### Các Thành phần UI Chung

- **Thẻ Bài viết (item_article.xml)**: Hiển thị hình ảnh, tiêu đề và mô tả ngắn của bài viết
- **Thẻ Chế độ Ăn (item_diet.xml)**: Hiển thị hình ảnh, tên và mô tả ngắn của chế độ ăn
- **Thẻ Công thức (item_recipe.xml)**: Hiển thị hình ảnh, tên, mô tả, nguyên liệu và hướng dẫn của công thức
- **Thẻ Bệnh (item_disease.xml và benh.xml)**: Hiển thị tên và mô tả ngắn của bệnh với nút xem chi tiết
- **Thẻ Ghi chú (item_note.xml)**: Hiển thị tiêu đề và nội dung ngắn của ghi chú

## 📦 Yêu cầu hệ thống

- **Android Studio** phiên bản mới nhất
- **Java Development Kit (JDK)** phiên bản 8 trở lên
- **Gradle** (đã được tích hợp sẵn trong Android Studio)

## ▶️ Hướng dẫn cài đặt và chạy ứng dụng

1. **Clone** repository về máy:
   ```bash
   git clone https://github.com/anhtai75/ungdungnhom9.git
   ```
2. **Mở** dự án bằng Android Studio.
3. **Sync Gradle** để tải về các dependencies cần thiết.
4. **Chạy** ứng dụng trên thiết bị ảo (AVD) hoặc thiết bị thật.

## 👥 Thành viên nhóm

- Sơn
- Tài
- Quỳnh
- Quang
- Phương

## 📄 Giấy phép

Dự án này được phát hành dưới giấy phép [MIT](LICENSE).
