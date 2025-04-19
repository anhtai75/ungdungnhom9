package com.example.appnhom9;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class EmergencyActivity extends BaseActivity {

    private LinearLayout customContactContainer;
    private static final int REQUEST_CALL_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_emergency);

        // Tìm các card view
        CardView mapCard = findViewById(R.id.mapCard);
        CardView contactCard = findViewById(R.id.contactCard);

        // Khởi tạo container cho liên hệ tùy chỉnh
        customContactContainer = findViewById(R.id.customContactContainer);
        Button addContactButton = findViewById(R.id.addContactButton);

        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(EmergencyActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            finish(); // Quay lại màn hình trước (trang chủ)
        });


        if (addContactButton != null) {
            addContactButton.setOnClickListener(v -> showAddContactDialog());
        }

        // Kiểm tra quyền gọi điện thoại
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL_PERMISSION);
        }

        // Thiết lập các số gọi khẩn cấp mặc định
        setupDefaultEmergencyCalls();

        // Thiết lập sự kiện click cho nút bản đồ
        mapCard.setOnClickListener(v -> {
            // Mở màn hình bản đồ bệnh viện
            Intent intent = new Intent(EmergencyActivity.this, HospitalActivity.class);
            startActivity(intent);
        });

        // Thiết lập sự kiện click cho nút liên lạc
        contactCard.setOnClickListener(v -> {
            // Mở màn hình liên lạc khẩn cấp
            Intent intent = new Intent(EmergencyActivity.this, CallActivity.class);
            startActivity(intent);
        });
    }

    private void setupDefaultEmergencyCalls() {
        String[] numbers = {"113", "114", "115"};

        LinearLayout layout = findViewById(R.id.contactListLayout);
        if (layout != null) {
            int count = layout.getChildCount();

            for (int i = 0; i < count; i++) {
                View child = layout.getChildAt(i);
                if (i < numbers.length) {
                    final String phone = numbers[i];
                    child.setOnClickListener(v -> {
                        makePhoneCall(phone);
                    });
                }
            }
        }
    }

    private void makePhoneCall(String phoneNumber) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + phoneNumber));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                == PackageManager.PERMISSION_GRANTED) {
            startActivity(callIntent);
        } else {
            Toast.makeText(this, "Chưa được cấp quyền gọi!", Toast.LENGTH_SHORT).show();
        }
    }

    private void showAddContactDialog() {
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(60, 20, 40, 20);

        final EditText nameInput = new EditText(this);
        nameInput.setHint("Tên liên hệ");
        nameInput.setBackgroundResource(R.drawable.nav_background);
        layout.addView(nameInput);

        final EditText phoneInput = new EditText(this);
        phoneInput.setHint("Số điện thoại");
        phoneInput.setInputType(InputType.TYPE_CLASS_PHONE);
        phoneInput.setBackgroundResource(R.drawable.nav_background);
        layout.addView(phoneInput);

        new AlertDialog.Builder(this)
                .setTitle("Thêm liên hệ mới")
                .setView(layout)
                .setIcon(getDrawable(R.drawable.popup_background))
                .setPositiveButton("Thêm", (dialog, which) -> {
                    String name = nameInput.getText().toString().trim();
                    String phone = phoneInput.getText().toString().trim();
                    if (!name.isEmpty() && !phone.isEmpty()) {
                        LinearLayout newContact = createContactItem("📞", name, phone);
                        customContactContainer.addView(newContact);
                    } else {
                        Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    private LinearLayout createContactItem(String iconText, String labelText, final String phoneNumber) {
        LinearLayout contactItem = new LinearLayout(this);
        contactItem.setOrientation(LinearLayout.VERTICAL);
        contactItem.setGravity(Gravity.CENTER);
        contactItem.setBackgroundResource(R.drawable.rounded_background); // Bo góc
        contactItem.setPadding(24, 24, 24, 24); // Padding bên trong
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 8, 0, 8);
        contactItem.setLayoutParams(params);

        TextView icon = new TextView(this);
        icon.setText(iconText);
        icon.setTextSize(40); // To hơn
        icon.setGravity(Gravity.CENTER);
        icon.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        contactItem.addView(icon);

        TextView label = new TextView(this);
        label.setText(labelText);
        label.setTextColor(getResources().getColor(android.R.color.black));
        label.setTextSize(30); // To hơn
        label.setGravity(Gravity.CENTER);
        label.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        contactItem.addView(label);

        contactItem.setOnClickListener(v -> makePhoneCall(phoneNumber));

        contactItem.setOnLongClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Tùy chọn")
                    .setItems(new CharSequence[]{"Chỉnh sửa", "Xóa"}, (dialog, which) -> {
                        if (which == 0) {
                            showEditContactDialog(contactItem, iconText, labelText, phoneNumber);
                        } else if (which == 1) {
                            customContactContainer.removeView(contactItem);
                        }
                    })
                    .show();
            return true;
        });

        return contactItem;
    }

    private void showEditContactDialog(LinearLayout contactItem, String iconText, String oldName, String oldPhone) {
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(60, 20, 40, 20);

        final EditText nameInput = new EditText(this);
        nameInput.setText(oldName);
        nameInput.setBackgroundResource(R.drawable.nav_background);
        layout.addView(nameInput);

        final EditText phoneInput = new EditText(this);
        phoneInput.setText(oldPhone);
        phoneInput.setInputType(InputType.TYPE_CLASS_PHONE);
        phoneInput.setBackgroundResource(R.drawable.nav_background);
        layout.addView(phoneInput);

        new AlertDialog.Builder(this)
                .setTitle("Chỉnh sửa liên hệ")
                .setView(layout)
                .setPositiveButton("Lưu", (dialog, which) -> {
                    String newName = nameInput.getText().toString().trim();
                    String newPhone = phoneInput.getText().toString().trim();
                    if (!newName.isEmpty() && !newPhone.isEmpty()) {
                        int index = customContactContainer.indexOfChild(contactItem);
                        customContactContainer.removeView(contactItem);
                        LinearLayout updatedContact = createContactItem(iconText, newName, newPhone);
                        customContactContainer.addView(updatedContact, index);
                    } else {
                        Toast.makeText(this, "Không được để trống!", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CALL_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Đã được cấp quyền
                Toast.makeText(this, "Đã được cấp quyền gọi điện", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Cần cấp quyền gọi điện để sử dụng tính năng này", Toast.LENGTH_SHORT).show();
            }
        }
    }
}