package com.example.project.Fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.example.project.Class.Books;
import com.example.project.Adapter.Books_Adapter;
import com.example.project.R;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {

    private RecyclerView recyclerView;
    private Books_Adapter bookAdapter;
    private List<Books> bookList;
    private Spinner spinner;
    private ImageButton btnBack;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);


        btnBack = view.findViewById(R.id.button_back);
        recyclerView = view.findViewById(R.id.recyclerView);
        spinner = view.findViewById(R.id.spinner);

        setUpSpinner();
        initializeBookList();
        setUpRecyclerView();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (requireActivity().getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    requireActivity().getSupportFragmentManager().popBackStack();
                } else {
                    requireActivity().finish();
                }
            }
        });

        return view;
    }

    private void setUpSpinner() {
        String[] items = getResources().getStringArray(R.array.spinner_items);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void initializeBookList() {
        bookList = new ArrayList<>();
        bookList.add(new Books("Nhà Giả Kim", R.drawable.ic_launcher_background, "Paul Coelho"));
        bookList.add(new Books("Tư Duy Nhanh và Chậm", R.drawable.ic_launcher_background, "Daniel Kahneman"));
        bookList.add(new Books("Đôi Ngả Dùng Người Dài", R.drawable.ic_launcher_background, "Tuấn Anh"));
    }

    private void setUpRecyclerView() {
        bookAdapter = new Books_Adapter(bookList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(bookAdapter);
    }
}
