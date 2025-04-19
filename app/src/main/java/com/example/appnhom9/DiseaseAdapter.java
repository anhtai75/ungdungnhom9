package com.example.appnhom9;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DiseaseAdapter extends RecyclerView.Adapter<DiseaseAdapter.DiseaseViewHolder> {

    private List<Disease> diseaseList;
    private Context context;

    public DiseaseAdapter(List<Disease> diseaseList, Context context) {
        this.diseaseList = diseaseList;
        this.context = context;
    }

    @Override
    public DiseaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate layout benh.xml để sử dụng cho từng item trong RecyclerView
        View view = LayoutInflater.from(context).inflate(R.layout.benh, parent, false);
        return new DiseaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DiseaseViewHolder holder, int position) {
        Disease disease = diseaseList.get(position);
        holder.diseaseNameTextView.setText(disease.getTenBenh());
        holder.detailsButton.setOnClickListener(v -> {
            if (context instanceof SecondActivity) {
                // Khi nhấn vào nút "Chi tiết", sẽ mở thêm chi tiết về bệnh
                ((SecondActivity) context).onDiseaseItemClick(disease);
            }
        });
    }

    @Override
    public int getItemCount() {
        return diseaseList.size();
    }

    public static class DiseaseViewHolder extends RecyclerView.ViewHolder {
        TextView diseaseNameTextView;
        Button detailsButton;

        public DiseaseViewHolder(View itemView) {
            super(itemView);
            diseaseNameTextView = itemView.findViewById(R.id.ten_benh_text);
            detailsButton = itemView.findViewById(R.id.huong_dan_button);
        }
    }
}