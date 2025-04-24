package com.example.appnhom9;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.widget.Toolbar;

public class dinhduongActivity extends BaseActivity {

    private Toolbar toolbar;
    private Button buttonTrackNutrition;
    private Button buttonDiets;
    private Button buttonRecipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dinhduong);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());

        buttonTrackNutrition = findViewById(R.id.buttonTrackNutrition);
        buttonTrackNutrition.setOnClickListener(v -> {
            Intent intent = new Intent(this, TrackNutritionActivity.class);
            startActivity(intent);
        });

        buttonDiets = findViewById(R.id.buttonDiets);
        buttonDiets.setOnClickListener(v -> {
            Intent intent = new Intent(this, DietsActivity.class);
            startActivity(intent);
        });

        buttonRecipes = findViewById(R.id.buttonRecipes);
        buttonRecipes.setOnClickListener(v -> {
            Intent intent = new Intent(this, RecipesActivity.class);
            startActivity(intent);
        });
    }
}