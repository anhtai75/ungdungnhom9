package com.example.appnhom9;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Locale;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        SharedPreferences prefs = newBase.getSharedPreferences("AppSettings", MODE_PRIVATE);
        int fontSize = prefs.getInt("fontSize", 14); // mặc định là 14sp
        float scale = fontSize / 14f; // scale theo font mặc định

        Configuration overrideConfiguration = newBase.getResources().getConfiguration();
        overrideConfiguration.fontScale = scale;

        Context context = newBase.createConfigurationContext(overrideConfiguration);
        super.attachBaseContext(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}