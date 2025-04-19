package com.example.appnhom9;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> {

    private final Context context;
    private List<Article> articleList;
    private final OnItemClickListener onItemClickListener;

    public ArticleAdapter(Context context, List<Article> articleList, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.articleList = articleList;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_article, parent, false);
        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        Article article = articleList.get(position);

        holder.titleTextView.setText(article.getTitle());
        holder.descriptionTextView.setText(article.getDescription());

        // Gán ảnh bằng resource ID
        holder.imageView.setImageResource(article.getImageResId());

        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null && article != null) {
                onItemClickListener.onItemClick(article);
            }
        });
    }

    @Override
    public int getItemCount() {
        return articleList != null ? articleList.size() : 0;
    }

    public void updateArticleList(List<Article> newList) {
        this.articleList = newList;
        notifyDataSetChanged();
    }

    public static class ArticleViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, descriptionTextView;
        ImageView imageView;

        public ArticleViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.articleTitle);
            descriptionTextView = itemView.findViewById(R.id.articleDesc);
            imageView = itemView.findViewById(R.id.articleImage);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Article article);
    }
}