package com.example.project.History_book;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.project.R;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {

    private RecyclerView recyclerView;
    private Books_Adapter bookAdapter;
    private List<Books> bookList;
    private Button buttonFilter;
    private ImageButton btnBack;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fracment_history, container, false);

        // Khởi tạo các view
        recyclerView = view.findViewById(R.id.recyclerView);
        buttonFilter = view.findViewById(R.id.button_filter);
        btnBack = view.findViewById(R.id.button_back);

        // Tạo danh sách sách
        bookList = new ArrayList<>();
        bookList.add(new Books("Nhà Giả Kim", R.drawable.anh1,"Paul Coelho", 50));
        bookList.add(new Books("Tư Duy Nhanh và Chậm", R.drawable.anh1,"Daniel Kahneman", 20));
        bookList.add(new Books("Đôi Ngả Dùng Người Dài", R.drawable.anh1,"Tuấn Anh", 80));

        // Thiết lập RecyclerView
        bookAdapter = new Books_Adapter(bookList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(bookAdapter);

        // Xử lý sự kiện click cho button
        buttonFilter.setOnClickListener(v -> {
            // Mở menu hoặc xử lý theo nhu cầu
        });

        // Xử lý sự kiện click cho nút quay lại
        btnBack.setOnClickListener(v -> {
            // Pop fragment khỏi stack
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        return view; // Trả về view
    }
}