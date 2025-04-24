package com.example.appnhom9;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "NutritionDB";
    private static final int DATABASE_VERSION = 1;

    // Bảng Food
    private static final String TABLE_FOOD = "food";
    private static final String COLUMN_FOOD_ID = "id";
    private static final String COLUMN_FOOD_NAME = "name";
    private static final String COLUMN_FOOD_CALORIES = "calories";
    private static final String COLUMN_FOOD_CARBS = "carbs";
    private static final String COLUMN_FOOD_PROTEIN = "protein";
    private static final String COLUMN_FOOD_FAT = "fat";

    // Bảng Diet
    private static final String TABLE_DIET = "diet";
    private static final String COLUMN_DIET_ID = "id";
    private static final String COLUMN_DIET_NAME = "name";
    private static final String COLUMN_DIET_DESCRIPTION = "description";
    private static final String COLUMN_DIET_IMAGE = "image";

    // Bảng Recipe
    private static final String TABLE_RECIPE = "recipe";
    private static final String COLUMN_RECIPE_ID = "id";
    private static final String COLUMN_RECIPE_NAME = "name";
    private static final String COLUMN_RECIPE_DESCRIPTION = "description";
    private static final String COLUMN_RECIPE_INGREDIENTS = "ingredients";
    private static final String COLUMN_RECIPE_INSTRUCTIONS = "instructions";
    private static final String COLUMN_RECIPE_IMAGE = "image";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng Food
        String createFoodTable = "CREATE TABLE " + TABLE_FOOD + " (" +
                COLUMN_FOOD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_FOOD_NAME + " TEXT, " +
                COLUMN_FOOD_CALORIES + " REAL, " +
                COLUMN_FOOD_CARBS + " REAL, " +
                COLUMN_FOOD_PROTEIN + " REAL, " +
                COLUMN_FOOD_FAT + " REAL)";
        db.execSQL(createFoodTable);

        // Tạo bảng Diet
        String createDietTable = "CREATE TABLE " + TABLE_DIET + " (" +
                COLUMN_DIET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_DIET_NAME + " TEXT, " +
                COLUMN_DIET_DESCRIPTION + " TEXT, " +
                COLUMN_DIET_IMAGE + " TEXT)";
        db.execSQL(createDietTable);

        // Tạo bảng Recipe
        String createRecipeTable = "CREATE TABLE " + TABLE_RECIPE + " (" +
                COLUMN_RECIPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_RECIPE_NAME + " TEXT, " +
                COLUMN_RECIPE_DESCRIPTION + " TEXT, " +
                COLUMN_RECIPE_INGREDIENTS + " TEXT, " +
                COLUMN_RECIPE_INSTRUCTIONS + " TEXT, " +
                COLUMN_RECIPE_IMAGE + " TEXT)";
        db.execSQL(createRecipeTable);

        // Thêm dữ liệu mẫu
        insertSampleData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOOD);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DIET);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPE);
        onCreate(db);
    }

    private void insertSampleData(SQLiteDatabase db) {
        // Dữ liệu mẫu cho Food (Vietnamese ingredients)
        insertFood(db, "Thịt Gà", 165f, 0f, 31f, 3.6f); // Chicken
        insertFood(db, "Cá Hồi", 208f, 0f, 20f, 13f); // Salmon
        insertFood(db, "Thịt Bò", 250f, 0f, 26f, 15f); // Beef
        insertFood(db, "Tôm", 99f, 0.2f, 24f, 0.3f); // Shrimp
        insertFood(db, "Gạo Lứt", 111f, 23f, 2.6f, 0.9f); // Brown Rice
        insertFood(db, "Rau Cải Xanh", 25f, 5f, 2.5f, 0.4f); // Green Leafy Vegetables
        insertFood(db, "Đậu Phụ", 76f, 1.9f, 8f, 4.8f); // Tofu
        insertFood(db, "Trứng Gà", 68f, 0.5f, 6f, 5f); // Egg

        // Dữ liệu mẫu cho Diet
        insertDiet(db, "Ít Tinh Bột", "Tập trung vào việc giảm lượng carbohydrate.", "diet_low_carb");
        insertDiet(db, "Keto", "Chế độ ăn giàu chất béo, ít carbohydrate để gây ketosis.", "diet_keto");
        insertDiet(db, "Giàu Protein", "Tập trung vào protein để phát triển cơ bắp.", "diet_protein");
        insertDiet(db, "Ăn Chay", "Không sử dụng sản phẩm từ động vật.", "diet_vegan");
        insertDiet(db, "Paleo", "Dựa trên chế độ ăn của tổ tiên: thịt, rau, trái cây.", "diet_paleo");
        insertDiet(db, "Nhịn Ăn Gián Đoạn", "Kết hợp nhịn ăn với các khoảng thời gian ăn.", "diet_fasting");
        insertDiet(db, "Địa Trung Hải", "Tập trung vào thực phẩm Địa Trung Hải: cá, dầu ô liu.", "diet_mediterranean");

        // Dữ liệu mẫu cho Recipe (Vietnamese dishes)
        insertRecipe(db, "Phở Bò", "Món phở bò thơm ngon với nước dùng đậm đà.",
                "Thịt bò, bánh phở, hành tây, gừng, quế, hồi, nước mắm",
                "Nấu nước dùng với xương bò, gừng, quế, hồi trong 6 giờ. Thêm bánh phở và thịt bò thái mỏng, ăn kèm rau thơm.",
                "pho_bo");
        insertRecipe(db, "Bún Chả", "Bún chả Hà Nội với thịt nướng thơm lừng.",
                "Thịt lợn, bún, nước mắm, tỏi, ớt, đu đủ muối",
                "Nướng thịt lợn trên than hoa, pha nước mắm chua ngọt, ăn kèm bún và rau sống.",
                "bun_cha");
        insertRecipe(db, "Gỏi Cuốn Tôm", "Gỏi cuốn tôm tươi ngon, nhẹ nhàng.",
                "Tôm, bánh tráng, bún, rau xà lách, hẹ, đậu phộng",
                "Luộc tôm, cuốn với bún, rau, hẹ trong bánh tráng, chấm nước mắm đậu phộng.",
                "goi_cuon");
        insertRecipe(db, "Cá Kho Tộ", "Cá kho tộ đậm đà, ăn với cơm nóng.",
                "Cá lóc, nước mắm, đường, tiêu, hành tím, ớt",
                "Ướp cá với nước mắm, đường, tiêu, kho trong nồi đất với hành và ớt đến khi sệt.",
                "ca_kho_to");
        insertRecipe(db, "Canh Chua Cá Lóc", "Canh chua cá lóc thơm ngon, chua nhẹ.",
                "Cá lóc, cà chua, dọc mùng, giá đỗ, me, rau mùi",
                "Nấu nước dùng với me, thêm cá lóc, cà chua, dọc mùng, giá đỗ, nêm gia vị, rắc rau mùi.",
                "canh_chua");
        insertRecipe(db, "Chả Giò", "Chả giò giòn rụm, nhân thịt và tôm.",
                "Thịt lợn, tôm, miến, cà rốt, hành tây, bánh tráng",
                "Trộn nhân, cuốn trong bánh tráng, chiên vàng giòn, chấm nước mắm.",
                "cha_gio");
    }

    // Các phương thức cho Food
    private void insertFood(SQLiteDatabase db, String name, float calories, float carbs, float protein, float fat) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_FOOD_NAME, name);
        values.put(COLUMN_FOOD_CALORIES, calories);
        values.put(COLUMN_FOOD_CARBS, carbs);
        values.put(COLUMN_FOOD_PROTEIN, protein);
        values.put(COLUMN_FOOD_FAT, fat);
        db.insert(TABLE_FOOD, null, values);
    }

    @SuppressLint("Range")
    public ArrayList<Food> getAllFoods() {
        ArrayList<Food> foodList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_FOOD, null);
        if (cursor.moveToFirst()) {
            do {
                Food food = new Food(
                        cursor.getString(cursor.getColumnIndex(COLUMN_FOOD_NAME)),
                        cursor.getFloat(cursor.getColumnIndex(COLUMN_FOOD_CALORIES)),
                        cursor.getFloat(cursor.getColumnIndex(COLUMN_FOOD_CARBS)),
                        cursor.getFloat(cursor.getColumnIndex(COLUMN_FOOD_PROTEIN)),
                        cursor.getFloat(cursor.getColumnIndex(COLUMN_FOOD_FAT))
                );
                foodList.add(food);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return foodList;
    }

    // Các phương thức cho Diet
    private void insertDiet(SQLiteDatabase db, String name, String description, String image) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_DIET_NAME, name);
        values.put(COLUMN_DIET_DESCRIPTION, description);
        values.put(COLUMN_DIET_IMAGE, image);
        db.insert(TABLE_DIET, null, values);
    }

    @SuppressLint("Range")
    public ArrayList<Diet> getAllDiets() {
        ArrayList<Diet> dietList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_DIET, null);
        if (cursor.moveToFirst()) {
            do {
                Diet diet = new Diet(
                        cursor.getString(cursor.getColumnIndex(COLUMN_DIET_NAME)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_DIET_DESCRIPTION))
                );
                diet.setImage(cursor.getString(cursor.getColumnIndex(COLUMN_DIET_IMAGE)));
                dietList.add(diet);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return dietList;
    }

    // Các phương thức cho Recipe
    private void insertRecipe(SQLiteDatabase db, String name, String description, String ingredients, String instructions, String image) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_RECIPE_NAME, name);
        values.put(COLUMN_RECIPE_DESCRIPTION, description);
        values.put(COLUMN_RECIPE_INGREDIENTS, ingredients);
        values.put(COLUMN_RECIPE_INSTRUCTIONS, instructions);
        values.put(COLUMN_RECIPE_IMAGE, image);
        db.insert(TABLE_RECIPE, null, values);
    }

    @SuppressLint("Range")
    public ArrayList<Recipe> getAllRecipes() {
        ArrayList<Recipe> recipeList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_RECIPE, null);
        if (cursor.moveToFirst()) {
            do {
                Recipe recipe = new Recipe(
                        cursor.getString(cursor.getColumnIndex(COLUMN_RECIPE_NAME)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_RECIPE_DESCRIPTION)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_RECIPE_INGREDIENTS)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_RECIPE_INSTRUCTIONS))
                );
                recipe.setImage(cursor.getString(cursor.getColumnIndex(COLUMN_RECIPE_IMAGE)));
                recipeList.add(recipe);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return recipeList;
    }

    public void addRecipe(Recipe recipe) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_RECIPE_NAME, recipe.getName());
        values.put(COLUMN_RECIPE_DESCRIPTION, recipe.getDescription());
        values.put(COLUMN_RECIPE_INGREDIENTS, recipe.getIngredients());
        values.put(COLUMN_RECIPE_INSTRUCTIONS, recipe.getInstructions());
        values.put(COLUMN_RECIPE_IMAGE, recipe.getImage());
        db.insert(TABLE_RECIPE, null, values);
        db.close();
    }
}