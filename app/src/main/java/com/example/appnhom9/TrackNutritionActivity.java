package com.example.appnhom9;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TrackNutritionActivity extends AppCompatActivity {

    private TextView textViewNutritionInfo;
    private Button buttonBack, btnDietWeightLoss, btnDietMuscleGain, btnDietMaintain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_nutrition);

        textViewNutritionInfo = findViewById(R.id.textViewNutritionInfo);
        buttonBack = findViewById(R.id.buttonBack);
        btnDietWeightLoss = findViewById(R.id.btnDietWeightLoss);
        btnDietMuscleGain = findViewById(R.id.btnDietMuscleGain);
        btnDietMaintain = findViewById(R.id.btnDietMaintain);

        // Dữ liệu cho từng chế độ
        String weightLossDiet = "📋 Chế độ Giảm cân:\n\n" +
                "• Calories: 1500 kcal\n" +
                "• Carbs: 100g\n" +
                "• Protein: 100g\n" +
                "• Fat: 50g";

        String muscleGainDiet = "📋 Chế độ Tăng cơ:\n\n" +
                "• Calories: 2500 kcal\n" +
                "• Carbs: 300g\n" +
                "• Protein: 150g\n" +
                "• Fat: 80g";

        String maintainDiet = "📋 Chế độ Duy trì:\n\n" +
                "• Calories: 2000 kcal\n" +
                "• Carbs: 200g\n" +
                "• Protein: 120g\n" +
                "• Fat: 70g";

        String lowCarbDiet = "📋 Chế độ Low Carb:\n\n" +
                "• Calories: 1800 kcal\n" +
                "• Carbs: 50g\n" +
                "• Protein: 150g\n" +
                "• Fat: 100g\n" +
                "• Fiber: 25g";

        String ketoDiet = "📋 Chế độ Keto:\n\n" +
                "• Calories: 2000 kcal\n" +
                "• Carbs: 20g\n" +
                "• Protein: 150g\n" +
                "• Fat: 150g\n" +
                "• Fiber: 30g";

        // Gán dữ liệu khi nhấn nút
        btnDietWeightLoss.setOnClickListener(v -> textViewNutritionInfo.setText(weightLossDiet));
        btnDietMuscleGain.setOnClickListener(v -> textViewNutritionInfo.setText(muscleGainDiet));
        btnDietMaintain.setOnClickListener(v -> textViewNutritionInfo.setText(maintainDiet));

        buttonBack.setOnClickListener(v -> finish());
    }
}