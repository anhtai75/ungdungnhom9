package com.example.appnhom9;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class socuuActivity extends BaseActivity {

    private Button backButton;
    private RecyclerView recyclerView;
    private socuuAdapter diseaseAdapter; // Đổi từ DiseaseAdapter sang socuuAdapter
    private List<Disease> diseaseList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socuu);

        // Ánh xạ view
        backButton = findViewById(R.id.backButton);
        recyclerView = findViewById(R.id.recyclerView);

        // Thiết lập RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        diseaseAdapter = new socuuAdapter(diseaseList, this); // Sử dụng socuuAdapter
        recyclerView.setAdapter(diseaseAdapter);

        // Load dữ liệu từ file JSON
        loadDiseaseDataFromJSON();

        // Nút quay lại
        backButton.setOnClickListener(v -> onBackPressed());
    }

    private void loadDiseaseDataFromJSON() {
        try {
            InputStream is = getAssets().open("socuu.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            String json = new String(buffer, StandardCharsets.UTF_8);

            JSONArray diseasesArray = new JSONArray(json);
            for (int i = 0; i < diseasesArray.length(); i++) {
                JSONObject diseaseObject = diseasesArray.getJSONObject(i);
                String title = diseaseObject.getString("tenBenh");
                JSONObject chiTiet = diseaseObject.getJSONObject("chiTiet");

                String trieuChung = chiTiet.getString("trieuChung");
                String viecNenLam = chiTiet.getString("viecNenLam");
                String viecKhongNenLam = chiTiet.getString("viecKhongNenLam");
                String cachXuLy = chiTiet.getString("cachXuLy");

                Disease disease = new Disease(title, trieuChung, viecNenLam, viecKhongNenLam, cachXuLy);
                diseaseList.add(disease);
            }

            diseaseAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Lỗi khi đọc dữ liệu", Toast.LENGTH_SHORT).show();
        }
    }

    public void onDiseaseItemClick(Disease disease) {
        showHuongDanPopup(disease);
    }

    private void showHuongDanPopup(Disease disease) {
        Dialog dialog = new Dialog(socuuActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.thongtinbenh);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView trieuChungTextView = dialog.findViewById(R.id.trieu_chung_text);
        TextView viecNenLamTextView = dialog.findViewById(R.id.viec_nen_lam_text);
        TextView viecKhongNenLamTextView = dialog.findViewById(R.id.viec_khong_nen_lam_text);
        TextView cachXuLyTextView = dialog.findViewById(R.id.cach_xu_ly_text);
        Button closeButton = dialog.findViewById(R.id.close_button);

        // Set dữ liệu
        trieuChungTextView.setText(disease.getTrieuChung());
        viecNenLamTextView.setText(disease.getViecNenLam());
        viecKhongNenLamTextView.setText(disease.getViecKhongNenLam());
        cachXuLyTextView.setText(disease.getCachXuLy());

        // Đóng dialog
        closeButton.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }
}