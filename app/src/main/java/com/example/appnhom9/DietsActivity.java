package com.example.appnhom9;


import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Locale;

public class DietsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText editTextSearch;
    private ImageView iconSearch;
    private RecyclerView recyclerViewDiets;
    private Button buttonRecipes;
    private Button buttonTrackNutrition;
    private ArrayList<Diet> dietList;
    private DietAdapter adapter;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setLocale(getCurrentLanguage());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diets);

        toolbar = findViewById(R.id.toolbar);
        editTextSearch = findViewById(R.id.editTextSearch);
        iconSearch = findViewById(R.id.iconSearch);
        recyclerViewDiets = findViewById(R.id.recyclerViewDiets);
        buttonRecipes = findViewById(R.id.buttonRecipes);
        buttonTrackNutrition = findViewById(R.id.buttonTrackNutrition);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());

        dbHelper = new DatabaseHelper(this);
        dietList = dbHelper.getAllDiets();

        adapter = new DietAdapter(dietList);
        recyclerViewDiets.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewDiets.setAdapter(adapter);

        iconSearch.setOnClickListener(v -> {
            String query = editTextSearch.getText().toString().trim().toLowerCase();
            ArrayList<Diet> filteredList = new ArrayList<>();
            for (Diet diet : dietList) {
                if (diet.getName().toLowerCase().contains(query) ||
                        diet.getDescription().toLowerCase().contains(query)) {
                    filteredList.add(diet);
                }
            }
            adapter = new DietAdapter(filteredList);
            recyclerViewDiets.setAdapter(adapter);
            Toast.makeText(this, getString(R.string.searching, query), Toast.LENGTH_SHORT).show();
        });

        buttonRecipes.setOnClickListener(v -> {
            Intent intent = new Intent(this, RecipesActivity.class);
            startActivity(intent);
        });

        buttonTrackNutrition.setOnClickListener(v -> {
            Intent intent = new Intent(this, TrackNutritionActivity.class);
            startActivity(intent);
        });
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