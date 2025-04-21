package com.example.appnhom9;

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
    private static final String COLUMN_DIET_IMAGE = "image"; // Lưu đường dẫn hình ảnh

    // Bảng Recipe
    private static final String TABLE_RECIPE = "recipe";
    private static final String COLUMN_RECIPE_ID = "id";
    private static final String COLUMN_RECIPE_NAME = "name";
    private static final String COLUMN_RECIPE_DESCRIPTION = "description";
    private static final String COLUMN_RECIPE_INGREDIENTS = "ingredients";
    private static final String COLUMN_RECIPE_INSTRUCTIONS = "instructions";
    private static final String COLUMN_RECIPE_IMAGE = "image"; // Lưu đường dẫn hình ảnh

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
        // Dữ liệu mẫu cho Food
        insertFood(db, "Chicken Breast", 165f, 0f, 31f, 3.6f);
        insertFood(db, "Avocado", 160f, 15f, 2f, 9f);
        insertFood(db, "Salmon", 208f, 0f, 20f, 13f);

        // Dữ liệu mẫu cho Diet
        insertDiet(db, "Low-Carb", "Focuses on reducing carbohydrate intake.", "");
        insertDiet(db, "Keto", "High-fat, low-carb diet to induce ketosis.", "");
        insertDiet(db, "High-Protein", "Focuses on protein for muscle growth.", "");
        insertDiet(db, "Vegan", "No animal products.", "");
        insertDiet(db, "Paleo", "Based on ancestral diet: meat, veggies, fruits.", "");
        insertDiet(db, "Intermittent Fasting", "Combines fasting with eating windows.", "");
        insertDiet(db, "Mediterranean", "Focuses on Mediterranean foods: fish, olive oil.", "");

        // Dữ liệu mẫu cho Recipe
        insertRecipe(db, "Grilled Salmon", "Delicious grilled salmon with asparagus.", "Salmon, asparagus, butter", "Grill salmon for 5 minutes each side.", "");
        insertRecipe(db, "Avocado Salad", "Healthy salad with avocado and walnuts.", "Avocado, walnuts, olive oil", "Mix all ingredients and serve.", "");
        insertRecipe(db, "Fried Eggs", "Quick and easy fried eggs with bacon.", "Eggs, bacon, cheese", "Fry eggs and bacon, top with cheese.", "");
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