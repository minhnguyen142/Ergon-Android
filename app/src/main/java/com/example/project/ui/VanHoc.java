package com.example.project.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.project.adapter.VanhocAdapter;
import com.example.project.BookDetail;
import com.example.project.adapter.ImageOnlyAdapter;


import com.example.project.R;
import com.example.project.adapter.VanhocAdapter;
import com.example.project.model.Book;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class VanHoc extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ImageButton btnBack;
    private VanhocAdapter vanhocAdapter;
    private List<Book> bookList;
    private DatabaseReference booksRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_van_hoc);

        recyclerView = findViewById(R.id.recycler2);
        btnBack = findViewById(R.id.btnVhback);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);

        bookList = new ArrayList<>();
        vanhocAdapter = new VanhocAdapter(bookList, this);
        recyclerView.setAdapter(vanhocAdapter);

        booksRef = FirebaseDatabase.getInstance().getReference("books");
        loadBooksFromFirebase();

        btnBack.setOnClickListener(v -> finish());
    }

    private void loadBooksFromFirebase() {
        Query query = booksRef.orderByChild("genre").equalTo("Văn học");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                bookList.clear();
                for (DataSnapshot bookSnapshot : dataSnapshot.getChildren()) {
                    Book book = bookSnapshot.getValue(Book.class);
                    if (book != null) {
                        bookList.add(book);
                    }
                }
                vanhocAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(VanHoc.this, "Failed to load books data: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
