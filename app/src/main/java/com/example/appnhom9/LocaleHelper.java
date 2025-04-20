package com.example.appnhom9;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;

import java.util.Locale;

public class LocaleHelper {
    private static final String PREF_NAME = "AppSettings";
    private static final String LANGUAGE_KEY = "language";

    // Lưu ngôn ngữ vào SharedPreferences
    public static void saveLanguage(Context context, String languageCode) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(LANGUAGE_KEY, languageCode);
        editor.apply();
    }

    // Lấy ngôn ngữ từ SharedPreferences
    public static String getLanguage(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getString(LANGUAGE_KEY, "en"); // "en" là mặc định
    }

    // Áp dụng ngôn ngữ vào context và trả về context mới
    public static Context setLocale(Context context, String languageCode) {
        // Lưu lại ngôn ngữ vừa thiết lập
        saveLanguage(context, languageCode);

        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);

        Resources resources = context.getResources();
        Configuration config = new Configuration(resources.getConfiguration());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLocale(locale);
            return context.createConfigurationContext(config);
        } else {
            config.locale = locale;
            resources.updateConfiguration(config, resources.getDisplayMetrics());
            return context;
        }
    }
}

