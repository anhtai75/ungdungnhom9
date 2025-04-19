package com.example.appnhom9;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;

public class DietDetailActivity extends AppCompatActivity {
    private TextView textViewName, textViewDescription, textViewAllergies, textViewRecipes, textViewMealPlan, textViewTips;
    private ImageView imageViewDiet;
    private Button buttonFavorite, buttonShare, buttonBack;
    private Diet diet;
    private boolean isFavorite = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_detail);

        // Ánh xạ view
        textViewName = findViewById(R.id.textViewName);
        textViewDescription = findViewById(R.id.textViewDescription);
        textViewAllergies = findViewById(R.id.textViewAllergies);
        textViewRecipes = findViewById(R.id.textViewRecipes);
        textViewMealPlan = findViewById(R.id.textViewMealPlan);
        textViewTips = findViewById(R.id.textViewTips);
        imageViewDiet = findViewById(R.id.imageViewDiet);
        buttonFavorite = findViewById(R.id.buttonFavorite);
        buttonShare = findViewById(R.id.buttonShare);
        buttonBack = findViewById(R.id.buttonBack);

        // Nhận dữ liệu từ Intent
        try {
            diet = (Diet) getIntent().getSerializableExtra("diet");
        } catch (Exception e) {
            e.printStackTrace();
            finish(); // nếu lỗi, thoát activity
            return;
        }

        if (diet != null) {
            textViewName.setText(diet.getName() != null ? diet.getName() : "No Name");
            textViewAllergies.setText(getString(R.string.allergies_details, diet.getAllergies() != null ? diet.getAllergies() : "None"));

            // Nếu là Keto thì hardcode nội dung đặc biệt
            if ("Keto".equalsIgnoreCase(diet.getName())) {
                textViewDescription.setText("The Keto (Ketogenic) diet is a high-fat, low-carb, and moderate-protein diet. Its main goal is to bring the body into a state of ketosis, where the body uses fat as its primary energy source instead of carbohydrates...");
                textViewRecipes.setText("- Grilled salmon with butter and asparagus\n- Avocado salad with olive oil and walnuts\n- Fried eggs with bacon and cheese\n- Pork ribs with garlic butter sauce");
                textViewMealPlan.setText("Breakfast: Fried eggs with avocado and bacon\nLunch: Salmon salad with olive oil\nDinner: Pork ribs with garlic butter sauce\nSnack: Almonds or cheese");
                textViewTips.setText("- Drink water\n- Supplement minerals\n- Track carbs\n- Use healthy fats");
            } else {
                textViewDescription.setText(diet.getDescription() != null ? diet.getDescription() : "No description");
                textViewRecipes.setText("- Vegetable salad with chicken\n- Boiled eggs and avocado\n- Grilled salmon");
                textViewMealPlan.setText("Breakfast: Fried eggs\nLunch: Chicken salad\nDinner: Grilled salmon");
                textViewTips.setText("No specific tips for this diet.");
            }

            // Gán hình ảnh tương ứng
            switch (diet.getName()) {
                case "Low-Carb":
                    imageViewDiet.setImageResource(R.drawable.ic_diet_lowcarb);
                    break;
                case "High-Protein":
                    imageViewDiet.setImageResource(R.drawable.ic_diet_highprotein);
                    break;
                case "Keto":
                    imageViewDiet.setImageResource(R.drawable.ic_diet_keto);
                    break;
                default:
                    imageViewDiet.setImageResource(R.drawable.ic_diet_keto);
            }
        }

        // Nút yêu thích
        buttonFavorite.setOnClickListener(v -> {
            isFavorite = !isFavorite;
            buttonFavorite.setText(isFavorite ? R.string.unfavorite : R.string.favorite);
        });

        // Nút chia sẻ
        buttonShare.setOnClickListener(v -> {
            if (diet != null) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, "Diet: " + diet.getName() + "\n" + diet.getDescription());
                startActivity(Intent.createChooser(shareIntent, getString(R.string.share)));
            }
        });

        // Nút quay lại
        buttonBack.setOnClickListener(v -> finish());
    }
}