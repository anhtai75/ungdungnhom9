package com.example.appnhom9;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LikedArticlesManager {

    private static final String PREF_NAME = "liked_articles";
    private static final String KEY_LIKED = "liked_articles_json";

    // Thêm bài viết vào danh sách yêu thích
    public static void likeArticle(Context context, Article article) {
        if (article == null || article.getTitle() == null) return;

        List<Article> liked = getFullLikedArticles(context);
        for (Article a : liked) {
            if (article.getTitle().equals(a.getTitle())) {
                return; // Bài viết đã tồn tại
            }
        }

        liked.add(article);
        saveLikedArticles(context, liked);
    }

    // Xóa bài viết khỏi danh sách yêu thích
    public static void unlikeArticle(Context context, String title) {
        if (title == null) return;

        List<Article> liked = getFullLikedArticles(context);
        Iterator<Article> iterator = liked.iterator();
        while (iterator.hasNext()) {
            Article a = iterator.next();
            if (title.equals(a.getTitle())) {
                iterator.remove();
                break;
            }
        }

        saveLikedArticles(context, liked);
    }

    // Kiểm tra bài viết đã được yêu thích chưa
    public static boolean isArticleLiked(Context context, String title) {
        if (title == null) return false;

        List<Article> liked = getFullLikedArticles(context);
        for (Article a : liked) {
            if (title.equals(a.getTitle())) {
                return true;
            }
        }

        return false;
    }

    // Lấy danh sách đầy đủ các bài viết đã thích
    public static List<Article> getFullLikedArticles(Context context) {
        List<Article> result = new ArrayList<>();
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String jsonString = prefs.getString(KEY_LIKED, "[]");

        try {
            JSONArray array = new JSONArray(jsonString);
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                String title = obj.optString("title", "");
                String description = obj.optString("description", "");
                String content = obj.optString("content", "");
                int imageResId = obj.optInt("imageResId", R.drawable.ic_launcher_foreground); // lấy resource ID

                result.add(new Article(title, description, content, imageResId));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }

    // Lưu danh sách bài viết yêu thích vào SharedPreferences
    private static void saveLikedArticles(Context context, List<Article> articles) {
        JSONArray array = new JSONArray();

        for (Article article : articles) {
            try {
                JSONObject obj = new JSONObject();
                obj.put("title", article.getTitle());
                obj.put("description", article.getDescription());
                obj.put("content", article.getContent());
                obj.put("imageResId", article.getImageResId()); // lưu resource ID
                array.put(obj);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().putString(KEY_LIKED, array.toString()).apply();
    }
}