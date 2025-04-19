package com.example.appnhom9;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LikedArticlesActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private ArticleAdapter adapter;
    private List<Article> likedArticles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liked_articles);

        // Thiết lập Toolbar có nút quay lại
        Toolbar toolbar = findViewById(R.id.toolbarLiked);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true); // nút back
            getSupportActionBar().setTitle("Danh sách bài viết đã thích");
        }

        // Thiết lập RecyclerView
        recyclerView = findViewById(R.id.recyclerViewLiked);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Lấy danh sách bài viết đã thích
        likedArticles = LikedArticlesManager.getFullLikedArticles(this);

        // Gắn adapter
        adapter = new ArticleAdapter(this, likedArticles, this::onArticleClick);
        recyclerView.setAdapter(adapter);
    }

    private void onArticleClick(Article article) {
        ArticleDetailActivity.open(this, article);
    }

    // Xử lý khi bấm nút quay lại
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // quay lại màn trước
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}