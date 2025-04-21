package com.example.appnhom9;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.textfield.TextInputEditText;
import java.util.Locale;

public class AddRecipeActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextInputEditText editTextRecipeName, editTextDescription, editTextIngredients, editTextInstructions;
    private ImageView imageViewRecipe;
    private Button buttonChooseImage, buttonSave;
    private ProgressBar progressBar;
    private DatabaseHelper dbHelper;
    private Uri imageUri;
    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setLocale(getCurrentLanguage());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        toolbar = findViewById(R.id.toolbar);
        editTextRecipeName = findViewById(R.id.editTextRecipeName);
        editTextDescription = findViewById(R.id.editTextDescription);
        editTextIngredients = findViewById(R.id.editTextIngredients);
        editTextInstructions = findViewById(R.id.editTextInstructions);
        imageViewRecipe = findViewById(R.id.imageViewRecipe);
        buttonChooseImage = findViewById(R.id.buttonChooseImage);
        buttonSave = findViewById(R.id.buttonSave);
        progressBar = findViewById(R.id.progressBar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());

        dbHelper = new DatabaseHelper(this);

        buttonChooseImage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        });

        buttonSave.setOnClickListener(v -> saveRecipe());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            imageViewRecipe.setImageURI(imageUri);
        }
    }

    private void saveRecipe() {
        String recipeName = editTextRecipeName.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();
        String ingredients = editTextIngredients.getText().toString().trim();
        String instructions = editTextInstructions.getText().toString().trim();
        String imagePath = imageUri != null ? imageUri.toString() : "";

        if (recipeName.isEmpty()) {
            editTextRecipeName.setError(getString(R.string.error_recipe_name));
            return;
        }

        Recipe recipe = new Recipe(recipeName, description, ingredients, instructions);
        recipe.setImage(imagePath);

        dbHelper.addRecipe(recipe);

        progressBar.setVisibility(View.VISIBLE);
        buttonSave.setEnabled(false);
        new Handler().postDelayed(() -> {
            progressBar.setVisibility(View.GONE);
            buttonSave.setEnabled(true);
            setResult(RESULT_OK);
            finish();
        }, 1000);
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