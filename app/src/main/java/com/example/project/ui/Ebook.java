package com.example.project.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.adapter.ImageAdapter;
import com.example.project.R;
import com.example.project.SpaceItemDecoration;
import com.example.project.model.Book;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Ebook extends AppCompatActivity {

    private RecyclerView recyclerDx, recyclerTrend, recyclerVh;
    private List<Book> bookListDx, bookListTrend, bookListVh;
    private ImageAdapter adapterDx, adapterTrend, adapterVh;
    private ImageButton btnBack;
    private DatabaseReference booksRef;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ebook);
        btnBack = findViewById(R.id.arrowleft);
        recyclerDx = findViewById(R.id.ryclerDx);
        recyclerTrend = findViewById(R.id.ryclerTrend);
        recyclerVh = findViewById(R.id.ryclerVh);

        bookListDx = new ArrayList<>();
        bookListTrend = new ArrayList<>();
        bookListVh = new ArrayList<>();

        adapterDx = new ImageAdapter(bookListDx);
        adapterTrend = new ImageAdapter(bookListTrend);
        adapterVh = new ImageAdapter(bookListVh);
        setUpRecyclerView(recyclerDx, adapterDx);
        setUpRecyclerView(recyclerTrend, adapterTrend);
        setUpRecyclerView(recyclerVh, adapterVh);
        booksRef = FirebaseDatabase.getInstance().getReference("books");
        loadBooksFromFirebase();
        btnBack.setOnClickListener(v -> finish());
    }
    private void setUpRecyclerView(RecyclerView recyclerView, ImageAdapter adapter) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new SpaceItemDecoration(2));
    }

    private void loadBooksFromFirebase() {
        booksRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                bookListDx.clear();
                bookListTrend.clear();
                bookListVh.clear();
                for (DataSnapshot bookSnapshot : dataSnapshot.getChildren()) {
                    Book book = bookSnapshot.getValue(Book.class);
                    if (book != null) {
                        if (book.getTrend() != null && book.getTrend()) {
                            bookListTrend.add(book);
                        } else if ("Kỹ năng".equals(book.getGenre())) {
                            bookListDx.add(book);
                        } else if ("Văn học".equals(book.getGenre())) {
                            bookListVh.add(book);
                        }
                    }
                }

                adapterDx.notifyDataSetChanged();
                adapterTrend.notifyDataSetChanged();
                adapterVh.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Ebook.this, "Failed to load books: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
