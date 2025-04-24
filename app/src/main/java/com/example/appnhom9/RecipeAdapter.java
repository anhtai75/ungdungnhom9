package com.example.appnhom9;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private ArrayList<Recipe> recipeList;
    private Context context;

    public RecipeAdapter(Context context, ArrayList<Recipe> recipeList) {
        this.context = context;
        this.recipeList = recipeList;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe recipe = recipeList.get(position);
        holder.textViewRecipeName.setText(recipe.getName());
        holder.textViewRecipeDescription.setText(recipe.getDescription());
        holder.textViewRecipeIngredients.setText(context.getString(R.string.ingredients) + ": " + recipe.getIngredients());
        holder.textViewRecipeInstructions.setText(context.getString(R.string.instructions) + ": " + recipe.getInstructions());

        // Load image from drawable resource
        if (recipe.getImage() != null && !recipe.getImage().isEmpty()) {
            int resourceId = context.getResources().getIdentifier(recipe.getImage(), "drawable", context.getPackageName());
            if (resourceId != 0) {
                holder.imageViewRecipe.setImageResource(resourceId);
            } else {
                holder.imageViewRecipe.setImageResource(R.drawable.placeholder_image);
            }
        } else {
            holder.imageViewRecipe.setImageResource(R.drawable.placeholder_image);
        }

        holder.itemView.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(recipe.getName());
            builder.setMessage(context.getString(R.string.description) + ": " + recipe.getDescription() + "\n" +
                    context.getString(R.string.ingredients) + ": " + recipe.getIngredients() + "\n" +
                    context.getString(R.string.instructions) + ": " + recipe.getInstructions());
            builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
            builder.show();
        });
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    static class RecipeViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewRecipe;
        TextView textViewRecipeName;
        TextView textViewRecipeDescription;
        TextView textViewRecipeIngredients;
        TextView textViewRecipeInstructions;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewRecipe = itemView.findViewById(R.id.imageViewRecipe);
            textViewRecipeName = itemView.findViewById(R.id.textViewRecipeName);
            textViewRecipeDescription = itemView.findViewById(R.id.textViewRecipeDescription);
            textViewRecipeIngredients = itemView.findViewById(R.id.textViewRecipeIngredients);
            textViewRecipeInstructions = itemView.findViewById(R.id.textViewRecipeInstructions);
        }
    }
}