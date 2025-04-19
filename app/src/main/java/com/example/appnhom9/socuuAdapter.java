package com.example.appnhom9;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class socuuAdapter extends RecyclerView.Adapter<socuuAdapter.socuuViewHolder> {

    private List<Disease> diseaseList;
    private Context context;

    public socuuAdapter(List<Disease> diseaseList, Context context) {
        this.diseaseList = diseaseList;
        this.context = context;
    }

    @Override
    public socuuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.benh, parent, false);
        return new socuuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(socuuViewHolder holder, int position) {
        Disease disease = diseaseList.get(position);
        holder.diseaseNameTextView.setText(disease.getTenBenh());

        holder.detailsButton.setOnClickListener(v -> {
            if (context instanceof socuuActivity) {
                ((socuuActivity) context).onDiseaseItemClick(disease);
            }
        });
    }

    @Override
    public int getItemCount() {
        return diseaseList.size();
    }

    public static class socuuViewHolder extends RecyclerView.ViewHolder {
        TextView diseaseNameTextView;
        Button detailsButton;

        public socuuViewHolder(View itemView) {
            super(itemView);
            diseaseNameTextView = itemView.findViewById(R.id.ten_benh_text);
            detailsButton = itemView.findViewById(R.id.huong_dan_button);
        }
    }
}