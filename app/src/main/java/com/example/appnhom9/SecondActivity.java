package com.example.appnhom9;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
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

public class SecondActivity extends BaseActivity {

    private Button backButton;
    private RecyclerView recyclerView;
    private DiseaseAdapter diseaseAdapter;
    private List<Disease> diseaseList = new ArrayList<>();

    // Phần lời nhắc sức khoẻ
    private TextView summaryTitle;
    private final String[] healthTips = {
            "📅 Khám sức khỏe định kỳ 6 tháng/lần để phát hiện bệnh sớm.",
            "🦠 Rửa tay thường xuyên để phòng tránh lây nhiễm bệnh.",
            "🚭 Nói không với thuốc lá – sức khỏe lá phổi là trên hết.",
            "🍺 Uống rượu có chừng mực hoặc tránh hoàn toàn nếu có thể.",
            "💡 Giữ tinh thần lạc quan – tâm trạng tốt giúp miễn dịch khỏe.",
            "🌿 Hít thở không khí trong lành – tránh môi trường ô nhiễm.",
            "🎧 Nghe nhạc thư giãn để cải thiện tâm trạng và giảm stress.",
            "📱 Giới hạn thời gian dùng điện thoại để bảo vệ mắt và não bộ.",
            "🤝 Kết nối xã hội tích cực giúp bạn sống lâu và hạnh phúc hơn.",
            "🧴 Dưỡng ẩm da sau khi tắm để tránh khô nứt.",
            "🍽️ Không bỏ bữa – đặc biệt là bữa sáng.",
            "👟 Mang giày phù hợp để bảo vệ khớp và cột sống.",
            "🪞 Nhìn vào gương và mỉm cười mỗi sáng để bắt đầu ngày mới tích cực.",
            "🧃 Ưu tiên ăn trái cây hơn là uống nước ép.",
            "🫀 Kiểm tra cholesterol định kỳ nếu trên 30 tuổi.",
            "🍱 Hộp cơm tự nấu là lựa chọn an toàn và tiết kiệm.",
            "🧘‍♂️ Thở sâu 3 lần trước khi phản ứng khi tức giận.",
            "📆 Viết nhật ký sức khỏe để theo dõi cảm xúc và thay đổi cơ thể.",
            "🧑‍⚕️ Tham khảo ý kiến chuyên gia trước khi dùng thực phẩm chức năng.",
            "🥤 Uống nước từng ngụm nhỏ thay vì uống ừng ực.",
            "🚶‍♂️ 10.000 bước mỗi ngày – mục tiêu đơn giản cho trái tim khỏe.",
            "🎨 Tạo không gian sống lành mạnh và sáng tạo giúp tinh thần thoải mái.",
            "🕒 Đi ngủ trước 23h giúp nội tạng hồi phục tốt hơn.",
            "🧠 Đừng quên chăm sóc sức khỏe tinh thần – cũng quan trọng như thể chất.",
            "🛀 Tắm nước ấm + tinh dầu giúp thư giãn thần kinh.",
            "🌬️ Học cách hít thở đúng giúp bạn tăng năng lượng mỗi ngày.",
            "🎉 Khen ngợi bản thân mỗi khi duy trì được thói quen lành mạnh.",
            "👨‍⚕️ Chủ động khám bệnh sớm hơn là đợi có triệu chứng nặng."
    };
    private int currentTipIndex = 0;
    private final Handler tipHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // Ánh xạ view
        backButton = findViewById(R.id.backButton);
        recyclerView = findViewById(R.id.recyclerView);
        summaryTitle = findViewById(R.id.summaryTitle); // thêm TextView trong layout

        // Thiết lập RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        diseaseAdapter = new DiseaseAdapter(diseaseList, this);
        recyclerView.setAdapter(diseaseAdapter);

        // Load dữ liệu từ file JSON
        loadDiseaseDataFromJSON();

        // Nút quay lại
        backButton.setOnClickListener(v -> onBackPressed());

        // Thiết lập lời nhắc sức khoẻ
        summaryTitle.setText(healthTips[currentTipIndex]);
        startHealthTipRotation();
    }

    private void loadDiseaseDataFromJSON() {
        try {
            InputStream is = getAssets().open("data.json");
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
        Dialog dialog = new Dialog(SecondActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.thongtinbenh);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView trieuChungTextView = dialog.findViewById(R.id.trieu_chung_text);
        TextView viecNenLamTextView = dialog.findViewById(R.id.viec_nen_lam_text);
        TextView viecKhongNenLamTextView = dialog.findViewById(R.id.viec_khong_nen_lam_text);
        TextView cachXuLyTextView = dialog.findViewById(R.id.cach_xu_ly_text);
        Button closeButton = dialog.findViewById(R.id.close_button);

        trieuChungTextView.setText(disease.getTrieuChung());
        viecNenLamTextView.setText(disease.getViecNenLam());
        viecKhongNenLamTextView.setText(disease.getViecKhongNenLam());
        cachXuLyTextView.setText(disease.getCachXuLy());

        closeButton.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    private void startHealthTipRotation() {
        tipHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                currentTipIndex = (currentTipIndex + 1) % healthTips.length;
                summaryTitle.animate()
                        .alpha(0f)
                        .translationY(-30)
                        .setDuration(500)
                        .withEndAction(() -> {
                            summaryTitle.setText(healthTips[currentTipIndex]);
                            summaryTitle.setAlpha(0f);
                            summaryTitle.setTranslationY(30);
                            summaryTitle.animate()
                                    .alpha(1f)
                                    .translationY(0)
                                    .setDuration(500)
                                    .start();
                        }).start();
                tipHandler.postDelayed(this, 10000);
            }
        }, 10000);
    }
}