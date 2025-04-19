package com.example.appnhom9;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddDietActivity extends AppCompatActivity {
    private EditText editTextDietName, editTextDescription;
    private CheckBox checkBoxGluten, checkBoxDairy, checkBoxPeanuts;
    private Button buttonSave, buttonCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_diet);

        editTextDietName = findViewById(R.id.editTextDietName);
        editTextDescription = findViewById(R.id.editTextDescription);
        checkBoxGluten = findViewById(R.id.checkBoxGluten);
        checkBoxDairy = findViewById(R.id.checkBoxDairy);
        checkBoxPeanuts = findViewById(R.id.checkBoxPeanuts);
        buttonSave = findViewById(R.id.buttonSave);
        buttonCancel = findViewById(R.id.buttonCancel);

        buttonSave.setOnClickListener(v -> {
            String dietName = editTextDietName.getText().toString();
            String description = editTextDescription.getText().toString();

            if (dietName.isEmpty()) {
                Toast.makeText(AddDietActivity.this, R.string.error_required_fields, Toast.LENGTH_SHORT).show();
                return;
            }

            StringBuilder allergies = new StringBuilder();
            if (checkBoxGluten.isChecked()) allergies.append(getString(R.string.gluten)).append(", ");
            if (checkBoxDairy.isChecked()) allergies.append(getString(R.string.dairy)).append(", ");
            if (checkBoxPeanuts.isChecked()) allergies.append(getString(R.string.peanuts)).append(", ");
            String allergiesStr = allergies.length() > 0 ? allergies.substring(0, allergies.length() - 2) : "None";

            Diet newDiet = new Diet(dietName, description, allergiesStr);
            Intent resultIntent = new Intent();
            resultIntent.putExtra("newDiet", newDiet);
            setResult(RESULT_OK, resultIntent);
            finish();
        });

        buttonCancel.setOnClickListener(v -> finish());
    }
}