package com.example.appnhom9;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class NotesListActivity extends AppCompatActivity {
    private RecyclerView noteRecyclerView;
    private NoteAdapter adapter;
    private List<Note> noteList = new ArrayList<>();  // Sử dụng lớp Note của bạn

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);

        // Ánh xạ RecyclerView
        noteRecyclerView = findViewById(R.id.noteRecyclerView);
        adapter = new NoteAdapter(this, noteList);
        noteRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        noteRecyclerView.setAdapter(adapter);

        // Ánh xạ nút quay lại và xử lý sự kiện nhấn nút quay lại
        ImageView backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> onBackPressed());  // Quay lại màn hình trước đó khi nhấn

        loadAllNotes();
    }

    private void loadAllNotes() {
        String[] files = fileList();  // Lấy danh sách các tệp tin trong bộ nhớ
        for (String file : files) {
            try {
                FileInputStream fis = openFileInput(file);  // Mở tệp tin
                BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
                StringBuilder content = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");  // Đọc nội dung tệp
                }
                fis.close();

                // Sử dụng lớp Note của bạn để tạo ghi chú
                String title = file;  // Lấy tên tệp làm tiêu đề
                String noteContent = content.toString();  // Lấy nội dung từ tệp

                // Thêm ghi chú vào danh sách
                noteList.add(new Note(title, noteContent));  // Sử dụng lớp Note đã tạo
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        adapter.notifyDataSetChanged();  // Cập nhật Adapter sau khi thêm ghi chú
    }
}