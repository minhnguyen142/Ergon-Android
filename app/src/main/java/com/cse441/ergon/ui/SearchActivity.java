package com.cse441.ergon.ui;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.cse441.ergon.R;
import com.cse441.ergon.adapter.BookAdapter;
import com.cse441.ergon.model.Book;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    private EditText etSearch;
    private ImageView ivClose;
    private RecyclerView recyclerView;
    private BookAdapter bookAdapter;
    private List<Book> booksList;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        etSearch = findViewById(R.id.et_search);
        recyclerView = findViewById(R.id.recyclerViewCategories);
        ivClose = findViewById(R.id.iv_close);
        booksList = new ArrayList<>();
        bookAdapter = new BookAdapter(this, booksList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(bookAdapter);
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String userId = sharedPreferences.getString("user_id", null);
        if (userId != null) {
            databaseReference = FirebaseDatabase.getInstance().getReference("books").child(userId);
        }

        databaseReference = FirebaseDatabase.getInstance().getReference("books");
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchBooks(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        ivClose.setOnClickListener(view -> {
            finish();
        });
    }
    private void searchBooks(String query) {
        if (query.isEmpty()) {
            booksList.clear();
            bookAdapter.notifyDataSetChanged();
            return;
        }

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                booksList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Book book = snapshot.getValue(Book.class);
                    if (book != null) {
                        String bookId = snapshot.getKey(); // Lấy bookId từ snapshot key
                        book.setId(bookId); // Đặt bookId vào đối tượng Book

                        String title = book.getTitle().toLowerCase();
                        String author = book.getAuthor().toLowerCase();
                        if (title.contains(query.toLowerCase()) || author.contains(query.toLowerCase())) {
                            booksList.add(book);
                        }
                    }
                }
                bookAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(SearchActivity.this, "Lỗi tải dữ liệu", Toast.LENGTH_SHORT).show();
            }
        });
    }

}