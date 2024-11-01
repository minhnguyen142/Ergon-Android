package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.adapter.ButtonAdapter;
import com.example.project.fragment.HomePageFragment;

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

        buttonItemList = new ArrayList<>();
        buttonItemList.add(new ButtonItem(R.drawable.baseline_query_stats_24, "Kinh doanh & Đầu tư"));
        buttonItemList.add(new ButtonItem(R.drawable.baseline_psychology_24, "Tâm lý học"));
        buttonItemList.add(new ButtonItem(R.drawable.baseline_menu_book_24, "Triết học"));
        buttonItemList.add(new ButtonItem(R.drawable.baseline_temple_buddhist_24, "Tôn giáo & Tâm linh"));
        buttonItemList.add(new ButtonItem(R.drawable.baseline_health_and_safety_24, "Sức khỏe & Phong cách sống"));
        buttonItemList.add(new ButtonItem(R.drawable.baseline_history_24, "Lịch sử & Văn hóa"));
        buttonItemList.add(new ButtonItem(R.drawable.baseline_smartphone_24, "Công nghệ"));

        buttonAdapter = new ButtonAdapter(buttonItemList, position -> {
            ButtonItem item = buttonItemList.get(position);

            if (item.isSelected()) {
                item.setSelected(false);
                selectedCount--;
            } else if (selectedCount < maxSelection) {
                item.setSelected(true);
                selectedCount++;
            }

            progressBar.setProgress(selectedCount);
            buttonAdapter.notifyItemChanged(position);
            validateForm();
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(buttonAdapter);

        progressBar.setMax(maxSelection);

        validateForm();

        // Xử lý sự kiện khi nhấn nút "Tiếp tục"
        btnContinueStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Start.this, HomePageFragment.class);
                startActivity(intent);
            }
        });
    }

    private void validateForm() {
        if (selectedCount == maxSelection) {
            btnContinueStart.setEnabled(true);
            btnContinueStart.setBackgroundTintList(getResources().getColorStateList(R.color.button_enabled_color));
        } else {
            btnContinueStart.setEnabled(false);
            btnContinueStart.setBackgroundTintList(getResources().getColorStateList(R.color.button_disabled_color));
        }
    }

}
