package com.example.appnhom9;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class SearchResultsActivity extends AppCompatActivity {

    private TextView searchResultsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        searchResultsTextView = findViewById(R.id.searchResultsTextView);


        // Nhận dữ liệu từ Intent
        String query = getIntent().getStringExtra("query");
        List<Article> searchResults = (List<Article>) getIntent().getSerializableExtra("searchResults");

        // Hiển thị kết quả tìm kiếm
        StringBuilder resultsText = new StringBuilder("Kết quả tìm kiếm cho: " + query + "\n\n");
        if (searchResults != null && !searchResults.isEmpty()) {
            for (Article article : searchResults) {
                resultsText.append(article.getTitle()).append("\n").append(article.getDescription()).append("\n\n");
            }
        } else {
            resultsText.append("Không có kết quả tìm kiếm.");
        }

        searchResultsTextView.setText(resultsText.toString());
    }

}