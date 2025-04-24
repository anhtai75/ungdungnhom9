package com.example.appnhom9;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

public class AddRecipeActivity extends BaseActivity {

    private EditText editTextName, editTextDescription, editTextIngredients, editTextInstructions;
    private Spinner spinnerImage;
    private Button buttonSave;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());

        editTextName = findViewById(R.id.editTextName);
        editTextDescription = findViewById(R.id.editTextDescription);
        editTextIngredients = findViewById(R.id.editTextIngredients);
        editTextInstructions = findViewById(R.id.editTextInstructions);
        spinnerImage = findViewById(R.id.spinnerImage);
        buttonSave = findViewById(R.id.buttonSave);

        // Setup Spinner with drawable names
        String[] imageOptions = {"pho_bo", "bun_cha", "goi_cuon", "ca_kho_to", "canh_chua", "cha_gio"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, imageOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerImage.setAdapter(adapter);

        dbHelper = new DatabaseHelper(this);

        buttonSave.setOnClickListener(v -> {
            String name = editTextName.getText().toString().trim();
            String description = editTextDescription.getText().toString().trim();
            String ingredients = editTextIngredients.getText().toString().trim();
            String instructions = editTextInstructions.getText().toString().trim();
            String image = spinnerImage.getSelectedItem().toString();

            if (name.isEmpty() || description.isEmpty() || ingredients.isEmpty() || instructions.isEmpty()) {
                Toast.makeText(this, "Vui lòng điền đầy đủ các trường bắt buộc", Toast.LENGTH_SHORT).show();
                return;
            }

            Recipe recipe = new Recipe(name, description, ingredients, instructions);
            recipe.setImage(image);
            dbHelper.addRecipe(recipe);

            Intent resultIntent = new Intent();
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }
}