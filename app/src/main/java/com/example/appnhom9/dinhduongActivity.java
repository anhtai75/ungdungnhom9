package com.example.appnhom9;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.widget.Toolbar;
import java.util.Locale;

public class dinhduongActivity extends BaseActivity {

    private Toolbar toolbar;
    private Button buttonTrackNutrition;
    private Button buttonDiets;
    private Button buttonRecipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLocale(getCurrentLanguage());
        setContentView(R.layout.activity_dinhduong);

        toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        buttonTrackNutrition = findViewById(R.id.buttonTrackNutrition);
        if (buttonTrackNutrition != null) {
            buttonTrackNutrition.setOnClickListener(v -> {
                Intent intent = new Intent(this, TrackNutritionActivity.class);
                startActivity(intent);
            });
        }

        buttonDiets = findViewById(R.id.buttonDiets);
        if (buttonDiets != null) {
            buttonDiets.setOnClickListener(v -> {
                Intent intent = new Intent(this, DietsActivity.class);
                startActivity(intent);
            });
        }

        buttonRecipes = findViewById(R.id.buttonRecipes);
        if (buttonRecipes != null) {
            buttonRecipes.setOnClickListener(v -> {
                Intent intent = new Intent(this, RecipesActivity.class);
                startActivity(intent);
            });
        }
    }

    private void setLocale(String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());

        SharedPreferences prefs = getSharedPreferences("Settings", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("language", languageCode);
        editor.apply();
    }

    private String getCurrentLanguage() {
        SharedPreferences prefs = getSharedPreferences("Settings", MODE_PRIVATE);
        return prefs.getString("language", "vi");
    }
}