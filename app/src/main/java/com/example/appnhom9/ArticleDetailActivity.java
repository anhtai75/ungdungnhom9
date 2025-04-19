package com.example.appnhom9;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ArticleDetailActivity extends AppCompatActivity {

    private TextView titleTextView, descTextView, contentTextView;
    private MenuItem likeItem;

    private String title = "";
    private String description = "";
    private String content = "";
    private int imageResId;  // Thay đổi: dùng int thay vì String
    private boolean isLiked = false;

    // Mở màn hình từ nơi khác
    public static void open(Context context, Article article) {
        Intent intent = new Intent(context, ArticleDetailActivity.class);
        intent.putExtra("title", article.getTitle());
        intent.putExtra("description", article.getDescription());
        intent.putExtra("content", article.getContent());
        intent.putExtra("imageResId", article.getImageResId());  // Truyền đúng kiểu
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);

        // Thiết lập Toolbar
        Toolbar toolbar = findViewById(R.id.articleToolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("");
        }

        // Ánh xạ TextView
        titleTextView = findViewById(R.id.detailTitle);
        descTextView = findViewById(R.id.detailDesc);
        contentTextView = findViewById(R.id.detailContent);

        // Nhận dữ liệu
        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        description = intent.getStringExtra("description");
        content = intent.getStringExtra("content");
        imageResId = intent.getIntExtra("imageResId", R.drawable.ic_launcher_foreground);  // Nhận int, có mặc định

        // Gán dữ liệu
        titleTextView.setText(title != null ? title : "Không có tiêu đề");
        descTextView.setText(description != null ? description : "Không có mô tả");
        contentTextView.setText(content != null ? content : "Không có nội dung");

        // Kiểm tra nếu đã thích
        isLiked = LikedArticlesManager.isArticleLiked(this, title);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.article_menu, menu);
        likeItem = menu.findItem(R.id.action_like);
        updateLikeIcon();
        return true;
    }

    private void updateLikeIcon() {
        if (likeItem != null) {
            likeItem.setIcon(isLiked ? R.drawable.ic_favorite : R.drawable.ic_favorite_border);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;

        } else if (id == R.id.action_like) {
            isLiked = !isLiked;

            if (isLiked) {
                // Truyền đúng imageResId vào Article
                LikedArticlesManager.likeArticle(this, new Article(title, description, content, imageResId));
                Toast.makeText(this, "Đã thích bài viết ❤️", Toast.LENGTH_SHORT).show();
            } else {
                LikedArticlesManager.unlikeArticle(this, title);
                Toast.makeText(this, "Đã bỏ thích bài viết", Toast.LENGTH_SHORT).show();
            }

            updateLikeIcon();
            return true;

        } else if (id == R.id.action_share) {
            String textToShare = title + "\n\n" + description + "\n\n" + content;
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, textToShare);
            startActivity(Intent.createChooser(shareIntent, "Chia sẻ bài viết qua"));
            return true;

        } else if (id == R.id.action_favorite_list) {
            startActivity(new Intent(this, LikedArticlesActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}