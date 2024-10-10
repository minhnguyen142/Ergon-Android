package com.example.project;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Start extends AppCompatActivity {

    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private ButtonAdapter buttonAdapter;
    private List<ButtonItem> buttonItemList;
    private int selectedCount = 0;
    private final int maxSelection = 4;
    private Button btnContinueStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerView);
        btnContinueStart = findViewById(R.id.btnContinueStart);

        // Tạo danh sách các chủ đề
        buttonItemList = new ArrayList<>();
        buttonItemList.add(new ButtonItem(R.drawable.baseline_query_stats_24, "Kinh doanh & Đầu tư"));
        buttonItemList.add(new ButtonItem(R.drawable.baseline_psychology_24, "Tâm lý học"));
        buttonItemList.add(new ButtonItem(R.drawable.baseline_menu_book_24, "Triết học"));
        buttonItemList.add(new ButtonItem(R.drawable.baseline_temple_buddhist_24, "Tôn giáo & Tâm linh"));
        buttonItemList.add(new ButtonItem(R.drawable.baseline_school_24, "Văn học"));
        buttonItemList.add(new ButtonItem(R.drawable.baseline_health_and_safety_24, "Sức khỏe & Phong cách sống"));
        buttonItemList.add(new ButtonItem(R.drawable.baseline_history_24, "Lịch sử & Văn hóa"));
        buttonItemList.add(new ButtonItem(R.drawable.baseline_smartphone_24, "Công nghệ"));

        buttonAdapter = new ButtonAdapter(buttonItemList, new ButtonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ButtonItem item = buttonItemList.get(position);

                if (item.isSelected()) {
                    item.setSelected(false);
                    selectedCount--;
                } else if (selectedCount < maxSelection) {
                    item.setSelected(true);
                    selectedCount++;
                }

                // Cập nhật thanh progress
                progressBar.setProgress(selectedCount);
                buttonAdapter.notifyItemChanged(position);

                // Gọi hàm kiểm tra form
                validateForm();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(buttonAdapter);

        progressBar.setMax(maxSelection); // Đặt giá trị tối đa của ProgressBar là 4

        // Khởi tạo trạng thái nút tiếp tục
        validateForm();
    }

    private void validateForm() {
        // Kiểm tra nếu số lượng chọn đạt tối đa
        if (selectedCount == maxSelection) {
            btnContinueStart.setEnabled(true);
            btnContinueStart.setBackgroundTintList(getResources().getColorStateList(R.color.button_enabled_color));
        } else {
            btnContinueStart.setEnabled(false);
            btnContinueStart.setBackgroundTintList(getResources().getColorStateList(R.color.button_disabled_color));
        }
    }
}