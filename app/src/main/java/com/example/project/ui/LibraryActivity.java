package com.example.project.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.adapter.EbookAdapter;
import com.example.project.R;
import com.example.project.entity.Ebook;

import java.util.ArrayList;
import java.util.List;


public class LibraryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.library);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Sample ebook data
        List<Ebook> ebookList = new ArrayList<>();
        ebookList.add(new Ebook("Nhà Giả Kim", "Paulo Coelho", R.drawable.nhagiakim, 5));
        ebookList.add(new Ebook("Bản chất của dối trá", "Dan Ariely", R.drawable.ban_chat_cua_doi_tra, 5));
        ebookList.add(new Ebook("Atomic Habits", "James Clear", R.drawable.atomic_habit, 5));



        // Set Adapter
        EbookAdapter ebookAdapter = new EbookAdapter(ebookList);
        recyclerView.setAdapter(ebookAdapter);
    }
}