package com.example.appnhom9;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.textfield.TextInputEditText;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class AddDietActivity extends BaseActivity {

    private Toolbar toolbar;
    private TextInputEditText editTextDietName, editTextDescription, editTextGoal, editTextCalories, editTextAllergies;
    private CheckBox checkBoxGluten, checkBoxDairy, checkBoxPeanuts, checkBoxSeafood, checkBoxSoy, checkBoxEggs;
    private TextView textViewStartDate, textViewEndDate;
    private ImageView imageViewDiet;
    private Button buttonChooseImage, buttonSave, buttonReset, buttonCancel;
    private ProgressBar progressBar;
    private Uri imageUri;
    private String startDate, endDate;
    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_diet);

        toolbar = findViewById(R.id.toolbar);
        editTextDietName = findViewById(R.id.editTextDietName);
        editTextDescription = findViewById(R.id.editTextDescription);
        editTextGoal = findViewById(R.id.editTextGoal);
        editTextCalories = findViewById(R.id.editTextCalories);
        editTextAllergies = findViewById(R.id.editTextAllergies);
        checkBoxGluten = findViewById(R.id.checkBoxGluten);
        checkBoxDairy = findViewById(R.id.checkBoxDairy);
        checkBoxPeanuts = findViewById(R.id.checkBoxPeanuts);
        checkBoxSeafood = findViewById(R.id.checkBoxSeafood);
        checkBoxSoy = findViewById(R.id.checkBoxSoy);
        checkBoxEggs = findViewById(R.id.checkBoxEggs);
        textViewStartDate = findViewById(R.id.textViewStartDate);
        textViewEndDate = findViewById(R.id.textViewEndDate);
        imageViewDiet = findViewById(R.id.imageViewDiet);
        buttonChooseImage = findViewById(R.id.buttonChooseImage);
        buttonSave = findViewById(R.id.buttonSave);
        buttonReset = findViewById(R.id.buttonReset);
        buttonCancel = findViewById(R.id.buttonCancel);
        progressBar = findViewById(R.id.progressBar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());

        textViewStartDate.setOnClickListener(v -> showDatePickerDialog(true));
        textViewEndDate.setOnClickListener(v -> showDatePickerDialog(false));

        buttonChooseImage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        });

        buttonSave.setOnClickListener(v -> saveDiet());
        buttonReset.setOnClickListener(v -> resetForm());
        buttonCancel.setOnClickListener(v -> finish());
    }

    private void showDatePickerDialog(boolean isStartDate) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    Calendar selectedDate = Calendar.getInstance();
                    selectedDate.set(selectedYear, selectedMonth, selectedDay);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    String formattedDate = dateFormat.format(selectedDate.getTime());

                    if (isStartDate) {
                        startDate = formattedDate;
                        textViewStartDate.setText(formattedDate);
                    } else {
                        endDate = formattedDate;
                        textViewEndDate.setText(formattedDate);
                    }
                }, year, month, day);
        datePickerDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            imageViewDiet.setImageURI(imageUri);
        }
    }

    private void saveDiet() {
        String dietName = editTextDietName.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();
        String goal = editTextGoal.getText().toString().trim();
        String caloriesText = editTextCalories.getText().toString().trim();
        String allergiesText = editTextAllergies.getText().toString().trim();

        if (dietName.isEmpty()) {
            editTextDietName.setError(getString(R.string.error_diet_name));
            return;
        }

        float calories;
        try {
            calories = Float.parseFloat(caloriesText);
        } catch (NumberFormatException e) {
            editTextCalories.setError(getString(R.string.error_calories));
            return;
        }

        ArrayList<String> allergies = new ArrayList<>();
        if (!allergiesText.isEmpty()) {
            String[] allergyArray = allergiesText.split(",");
            for (String allergy : allergyArray) {
                allergies.add(allergy.trim());
            }
        }

        if (checkBoxGluten.isChecked()) allergies.add(getString(R.string.allergy_gluten));
        if (checkBoxDairy.isChecked()) allergies.add(getString(R.string.allergy_dairy));
        if (checkBoxPeanuts.isChecked()) allergies.add(getString(R.string.allergy_peanuts));
        if (checkBoxSeafood.isChecked()) allergies.add(getString(R.string.allergy_seafood));
        if (checkBoxSoy.isChecked()) allergies.add(getString(R.string.allergy_soy));
        if (checkBoxEggs.isChecked()) allergies.add(getString(R.string.allergy_eggs));

        Intent resultIntent = new Intent();
        resultIntent.putExtra("dietName", dietName);
        resultIntent.putExtra("description", description);
        resultIntent.putExtra("calories", calories);
        resultIntent.putExtra("startDate", startDate);
        resultIntent.putExtra("endDate", endDate);
        resultIntent.putExtra("goal", goal);
        resultIntent.putStringArrayListExtra("allergies", allergies);
        setResult(RESULT_OK, resultIntent);

        progressBar.setVisibility(View.VISIBLE);
        buttonSave.setEnabled(false);
        new Handler().postDelayed(() -> {
            progressBar.setVisibility(View.GONE);
            buttonSave.setEnabled(true);
            Toast.makeText(this, getString(R.string.saved_message, dietName), Toast.LENGTH_SHORT).show();
            finish();
        }, 1000);
    }

    private void resetForm() {
        editTextDietName.setText("");
        editTextDescription.setText("");
        editTextGoal.setText("");
        editTextCalories.setText("");
        editTextAllergies.setText("");
        checkBoxGluten.setChecked(false);
        checkBoxDairy.setChecked(false);
        checkBoxPeanuts.setChecked(false);
        checkBoxSeafood.setChecked(false);
        checkBoxSoy.setChecked(false);
        checkBoxEggs.setChecked(false);
        textViewStartDate.setText(getString(R.string.start_date));
        textViewEndDate.setText(getString(R.string.end_date));
        imageViewDiet.setImageDrawable(null);
        imageUri = null;
    }
}