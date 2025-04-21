package com.example.appnhom9;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.Locale;

public class RecipesActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerViewRecipes;
    private FloatingActionButton buttonAddRecipe;
    private ArrayList<Recipe> recipeList;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setLocale(getCurrentLanguage());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        toolbar = findViewById(R.id.toolbar);
        recyclerViewRecipes = findViewById(R.id.recyclerViewRecipes);
        buttonAddRecipe = findViewById(R.id.buttonAddRecipe);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());

        dbHelper = new DatabaseHelper(this);
        recipeList = dbHelper.getAllRecipes();

        RecipeAdapter adapter = new RecipeAdapter(this, recipeList);
        recyclerViewRecipes.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewRecipes.setAdapter(adapter);

        buttonAddRecipe.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddRecipeActivity.class);
            startActivityForResult(intent, 200);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == RESULT_OK) {
            recipeList = dbHelper.getAllRecipes();
            RecipeAdapter adapter = new RecipeAdapter(this, recipeList);
            recyclerViewRecipes.setAdapter(adapter);
            Toast.makeText(this, getString(R.string.added_recipe), Toast.LENGTH_SHORT).show();
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