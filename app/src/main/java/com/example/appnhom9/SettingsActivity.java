package com.example.appnhom9;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;  // Thêm import này

public class SettingsActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "AppSettings";

    private Switch darkModeSwitch, notificationSwitch, soundSwitch;
    private SeekBar fontSizeSeekBar;
    private TextView fontSizeTextView;
    private RadioGroup languageGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Ánh xạ view
        darkModeSwitch = findViewById(R.id.darkModeSwitch);
        notificationSwitch = findViewById(R.id.notificationSwitch);
        soundSwitch = findViewById(R.id.soundSwitch);
        fontSizeSeekBar = findViewById(R.id.fontSizeSeekBar);
        fontSizeTextView = findViewById(R.id.fontSizeTextView);
        languageGroup = findViewById(R.id.languageGroup);
        Button backButton = findViewById(R.id.backButton);

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        // Load trạng thái đã lưu
        darkModeSwitch.setChecked(prefs.getBoolean("darkMode", false));
        notificationSwitch.setChecked(prefs.getBoolean("notifications", true));
        soundSwitch.setChecked(prefs.getBoolean("sound", true));
        int savedFontSize = prefs.getInt("fontSize", 14);
        fontSizeSeekBar.setProgress(savedFontSize);
        fontSizeTextView.setText("Font Size: " + savedFontSize);

        int savedLanguageId = prefs.getInt("languageId", R.id.englishRadioButton);
        languageGroup.check(savedLanguageId);

        // Kiểm tra và áp dụng chế độ tối khi bắt đầu
        if (prefs.getBoolean("darkMode", false)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        // Xử lý sự kiện khi thay đổi trạng thái switch
        darkModeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            editor.putBoolean("darkMode", isChecked).apply();
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        });

        notificationSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            editor.putBoolean("notifications", isChecked).apply();
        });

        soundSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            editor.putBoolean("sound", isChecked).apply();
        });

        fontSizeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                fontSizeTextView.setText("Font Size: " + progress);
                editor.putInt("fontSize", progress).apply();
                recreate(); // ⚠️ Rất quan trọng
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // Ngôn ngữ
        languageGroup.setOnCheckedChangeListener((group, checkedId) -> {
            editor.putInt("languageId", checkedId).apply();
            String lang = "";
            if (checkedId == R.id.englishRadioButton) {
                lang = "en";  // Đặt mã ngôn ngữ cho tiếng Anh
            } else if (checkedId == R.id.vietnameseRadioButton) {
                lang = "vi";  // Đặt mã ngôn ngữ cho tiếng Việt
            } else if (checkedId == R.id.spanishRadioButton) {
                lang = "es";  // Đặt mã ngôn ngữ cho tiếng Tây Ban Nha
            }

            // Lưu mã ngôn ngữ
            editor.putString("language", lang).apply();
            // Cập nhật ngôn ngữ của ứng dụng ngay lập tức
            LocaleHelper.setLocale(SettingsActivity.this, lang);
            Toast.makeText(this, "Ngôn ngữ đã thay đổi: " + lang, Toast.LENGTH_SHORT).show();
            finish();
            startActivity(getIntent());
        });

        // Nút quay lại
        backButton.setOnClickListener(v -> finish());
    }
}