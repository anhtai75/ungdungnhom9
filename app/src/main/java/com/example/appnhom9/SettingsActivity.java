package com.example.appnhom9;



import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class SettingsActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "AppSettings";

    private Switch darkModeSwitch, notificationSwitch, soundSwitch;
    private TextView fontSizeTextView;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.setLocale(newBase, LocaleHelper.getLanguage(newBase)));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        darkModeSwitch = findViewById(R.id.darkModeSwitch);
        notificationSwitch = findViewById(R.id.notificationSwitch);
        soundSwitch = findViewById(R.id.soundSwitch);
        SeekBar fontSizeSeekBar = findViewById(R.id.fontSizeSeekBar);
        fontSizeTextView = findViewById(R.id.fontSizeTextView);
        RadioGroup languageGroup = findViewById(R.id.languageGroup);
        Button backButton = findViewById(R.id.backButton);

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        // Load dữ liệu đã lưu
        darkModeSwitch.setChecked(prefs.getBoolean("darkMode", false));
        notificationSwitch.setChecked(prefs.getBoolean("notifications", true));
        soundSwitch.setChecked(prefs.getBoolean("sound", true));
        int savedFontSize = prefs.getInt("fontSize", 14);
        fontSizeSeekBar.setProgress(savedFontSize);
        fontSizeTextView.setText("Font Size: " + savedFontSize);
        int savedLanguageId = prefs.getInt("languageId", R.id.vietnameseRadioButton);
        languageGroup.check(savedLanguageId);

        // Áp dụng dark mode
        if (prefs.getBoolean("darkMode", false)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        darkModeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            editor.putBoolean("darkMode", isChecked).apply();
            AppCompatDelegate.setDefaultNightMode(isChecked ?
                    AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
        });

        notificationSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> editor.putBoolean("notifications", isChecked).apply());

        soundSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> editor.putBoolean("sound", isChecked).apply());

        fontSizeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                fontSizeTextView.setText("Font Size: " + progress);
                editor.putInt("fontSize", progress).apply();
                recreate();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        languageGroup.setOnCheckedChangeListener((group, checkedId) -> {
            String selectedLanguage = "vi"; // mặc định

            if (checkedId == R.id.englishRadioButton) {
                selectedLanguage = "en";
            } else if (checkedId == R.id.spanishRadioButton) {
                selectedLanguage = "es";
            }

            editor.putInt("languageId", checkedId).apply();
            editor.putString("language", selectedLanguage).apply();

            LocaleHelper.setLocale(this, selectedLanguage);
            Toast.makeText(this, "Ngôn ngữ đã thay đổi", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
            //Thay bằng Intent để khởi động lại trang chính
        });
        backButton.setOnClickListener(v -> finish());
    }

}
