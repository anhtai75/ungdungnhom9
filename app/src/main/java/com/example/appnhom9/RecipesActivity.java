package com.example.appnhom9;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

public class RecipesActivity extends BaseActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerViewRecipes;
    private FloatingActionButton buttonAddRecipe;
    private ArrayList<Recipe> recipeList;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
}