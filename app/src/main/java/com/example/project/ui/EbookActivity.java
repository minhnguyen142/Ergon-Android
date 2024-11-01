package com.example.project.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.R;
import com.example.project.adapter.EbookAdapter;
import com.example.project.model.Book;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EbookActivity extends AppCompatActivity {

    private RecyclerView recyclerDx, recyclerTrend, recyclerVh;
    private List<Book> bookListDx, bookListTrend, bookListVh;
    private EbookAdapter adapterDx, adapterTrend, adapterVh;
    private ImageButton btnBack;
    private ImageView btnSearch;
    private DatabaseReference booksRef;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ebook);

        initializeViews();
        initializeFirebase();
        loadBooksFromFirebase();
        setBackButtonListener();
        setBtnSearchListener();
    }

    private void initializeViews() {
        btnBack = findViewById(R.id.arrowleft);
        recyclerDx = findViewById(R.id.ryclerDx);
        recyclerTrend = findViewById(R.id.ryclerTrend);
        recyclerVh = findViewById(R.id.ryclerVh);
        btnSearch = findViewById(R.id.iv_search);
        bookListDx = new ArrayList<>();
        bookListTrend = new ArrayList<>();
        bookListVh = new ArrayList<>();

        adapterDx = new EbookAdapter(bookListDx, this);
        adapterTrend = new EbookAdapter(bookListTrend, this);
        adapterVh = new EbookAdapter(bookListVh, this);

        setUpRecyclerView(recyclerDx, adapterDx);
        setUpRecyclerView(recyclerTrend, adapterTrend);
        setUpRecyclerView(recyclerVh, adapterVh);
    }

    private void initializeFirebase() {
        booksRef = FirebaseDatabase.getInstance().getReference("books");
    }

    private void setUpRecyclerView(RecyclerView recyclerView, EbookAdapter adapter) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
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
                        categorizeBook(book);
                    }
                }

                adapterDx.notifyDataSetChanged();
                adapterTrend.notifyDataSetChanged();
                adapterVh.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(EbookActivity.this, "Failed to load books: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void categorizeBook(Book book) {
        if (book.getTrend() != null && book.getTrend()) {
            bookListTrend.add(book);
        } else if ("Kỹ năng".equals(book.getGenre())) {
            bookListDx.add(book);
        } else if ("Văn học".equals(book.getGenre())) {
            bookListVh.add(book);
        }
    }

    private void setBackButtonListener() {
        btnBack.setOnClickListener(v -> finish());
    }
    private void setBtnSearchListener() {
        btnSearch.setOnClickListener(v -> {
            Intent intent = new Intent(EbookActivity.this, SearchActivity.class);
            startActivity(intent);
        });
    }
}
