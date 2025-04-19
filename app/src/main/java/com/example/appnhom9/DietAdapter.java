package com.example.appnhom9;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DietAdapter extends RecyclerView.Adapter<DietAdapter.DietViewHolder> {
    private ArrayList<Diet> dietList;    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Diet diet);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public DietAdapter(ArrayList<Diet> dietList) {
        this.dietList = dietList;
    }

    @NonNull
    @Override
    public DietViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_diet, parent, false);
        return new DietViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DietViewHolder holder, int position) {
        Diet diet = dietList.get(position);
        holder.textViewName.setText(diet.getName());
        holder.textViewDescription.setText(diet.getDescription());

        switch (diet.getName()) {
            case "Low-Carb":
                holder.imageViewDiet.setImageResource(R.drawable.ic_diet_lowcarb);
                break;
            case "Keto":
                holder.imageViewDiet.setImageResource(R.drawable.ic_diet_keto);
                break;
            case "High-Protein":
                holder.imageViewDiet.setImageResource(R.drawable.ic_diet_highprotein);
                break;
            default:
                holder.imageViewDiet.setImageResource(R.drawable.ic_diet_keto);
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(diet);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dietList.size();
    }

    public static class DietViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewDiet;
        TextView textViewName, textViewDescription;

        public DietViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewDiet = itemView.findViewById(R.id.imageViewDiet);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
        }
    }
}