package com.example.appnhom9;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

public class TrackNutritionActivity extends BaseActivity {

    private Toolbar toolbar;
    private TextView textViewNutritionInfo;
    private PieChart pieChartNutrition;
    private RecyclerView recyclerViewRecipes;
    private FloatingActionButton buttonAddDiet;
    private ArrayList<Food> foodList;
    private ArrayList<Recipe> recipeList;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_nutrition);

        toolbar = findViewById(R.id.toolbar);
        textViewNutritionInfo = findViewById(R.id.textViewNutritionInfo);
        pieChartNutrition = findViewById(R.id.pieChartNutrition);
        recyclerViewRecipes = findViewById(R.id.recyclerViewRecipes);
        buttonAddDiet = findViewById(R.id.buttonAddDiet);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());

        dbHelper = new DatabaseHelper(this);
        foodList = dbHelper.getAllFoods();

        float totalCalories = 0, totalCarbs = 0, totalProtein = 0, totalFat = 0;
        if (!foodList.isEmpty()) {
            for (Food food : foodList) {
                totalCalories += food.getCalories();
                totalCarbs += food.getCarbs();
                totalProtein += food.getProtein();
                totalFat += food.getFat();
            }
        }

        String nutritionInfo = getString(R.string.calories) + ": " + totalCalories + " kcal\n" +
                getString(R.string.carbs) + ": " + totalCarbs + "g\n" +
                getString(R.string.protein) + ": " + totalProtein + "g\n" +
                getString(R.string.fat) + ": " + totalFat + "g";
        textViewNutritionInfo.setText(nutritionInfo);

        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(totalCarbs, getString(R.string.carbs)));
        entries.add(new PieEntry(totalProtein, getString(R.string.protein)));
        entries.add(new PieEntry(totalFat, getString(R.string.fat)));

        PieDataSet dataSet = new PieDataSet(entries, getString(R.string.nutrition_analysis));
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        dataSet.setValueTextSize(12f);
        dataSet.setValueTextColor(android.R.color.black);

        PieData pieData = new PieData(dataSet);
        pieChartNutrition.setData(pieData);
        pieChartNutrition.setUsePercentValues(true);
        pieChartNutrition.getDescription().setEnabled(false);
        pieChartNutrition.setCenterText(getString(R.string.nutrition));
        pieChartNutrition.setCenterTextSize(14f);
        pieChartNutrition.setHoleRadius(40f);
        pieChartNutrition.setTransparentCircleRadius(45f);
        pieChartNutrition.invalidate();

        recipeList = dbHelper.getAllRecipes();
        RecipeAdapter adapter = new RecipeAdapter(this, recipeList);
        recyclerViewRecipes.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewRecipes.setAdapter(adapter);

        buttonAddDiet.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddDietActivity.class);
            startActivityForResult(intent, 100);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            String dietName = data.getStringExtra("dietName");
            Toast.makeText(this, getString(R.string.added_diet, dietName), Toast.LENGTH_SHORT).show();
        }
    }
}