package com.example.appnhom9;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;

public class DietAdapter extends RecyclerView.Adapter<DietAdapter.DietViewHolder> {

    private ArrayList<Diet> dietList;
    private Context context;

    public DietAdapter(Context context, ArrayList<Diet> dietList) {
        this.context = context;
        this.dietList = dietList;
    }

    @NonNull
    @Override
    public DietViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_diet, parent, false);
        return new DietViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DietViewHolder holder, int position) {
        Diet diet = dietList.get(position);
        holder.textViewDietName.setText(diet.getName());
        holder.textViewDietDescription.setText(diet.getDescription());

        // Log để debug
        Log.d("DietAdapter", "Loading image: " + diet.getImage());

        if (diet.getImage() != null && !diet.getImage().isEmpty()) {
            try {
                // Lấy resource ID từ tên tài nguyên
                int resourceId = context.getResources().getIdentifier(diet.getImage(), "drawable", context.getPackageName());
                if (resourceId != 0) {
                    Glide.with(context)
                            .load(resourceId)
                            .placeholder(R.drawable.placeholder_image)
                            .error(R.drawable.error_image)
                            .into(holder.imageViewDiet);
                } else {
                    Log.e("DietAdapter", "Resource not found: " + diet.getImage());
                    holder.imageViewDiet.setImageResource(R.drawable.error_image);
                }
            } catch (Exception e) {
                Log.e("DietAdapter", "Error loading image: " + e.getMessage());
                holder.imageViewDiet.setImageResource(R.drawable.error_image);
            }
        } else {
            Log.w("DietAdapter", "Image is null or empty for diet: " + diet.getName());
            holder.imageViewDiet.setImageResource(R.drawable.placeholder_image);
        }

        holder.itemView.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(diet.getName());
            builder.setMessage(diet.getDescription());
            builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
            builder.show();
        });
    }

    @Override
    public int getItemCount() {
        return dietList.size();
    }

    static class DietViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewDiet;
        TextView textViewDietName;
        TextView textViewDietDescription;

        public DietViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewDiet = itemView.findViewById(R.id.imageViewDiet);
            textViewDietName = itemView.findViewById(R.id.textViewDietName);
            textViewDietDescription = itemView.findViewById(R.id.textViewDietDescription);
        }
    }
}