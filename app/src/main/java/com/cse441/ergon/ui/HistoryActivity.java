
package com.cse441.ergon.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BookAdapter bookAdapter;
    private List<Book> bookList;
    private Spinner spinner;
    private ImageButton btnBack;
    private DatabaseReference booksRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        initializeViews();
        setUpSpinner();
        setUpRecyclerView();
        loadBooksFromFirebase();
        setListeners();
    }

    private void initializeViews() {
        btnBack = findViewById(R.id.button_back);
        recyclerView = findViewById(R.id.recyclerView);
        spinner = findViewById(R.id.spinner);
        booksRef = FirebaseDatabase.getInstance().getReference("books");
    }

    private void setUpSpinner() {
        String[] items = getResources().getStringArray(R.array.spinner_items);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void setUpRecyclerView() {
        bookList = new ArrayList<>();
        bookAdapter = new BookAdapter(this, bookList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(bookAdapter);
    }

    private void loadBooksFromFirebase() {
        // Lấy userId từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("user_id", null);

        if (userId != null) {
            DatabaseReference historyRef = FirebaseDatabase.getInstance().getReference("users").child(userId).child("readingHistory");

            historyRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        bookList.clear();
                        for (DataSnapshot bookSnapshot : dataSnapshot.getChildren()) {
                            String bookId = bookSnapshot.getKey();
                            loadBookDetails(bookId); // Gọi phương thức để lấy chi tiết sách
                        }
                    } else {
                        Toast.makeText(HistoryActivity.this, "Không có lịch sử đọc nào", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(HistoryActivity.this, "Failed to load reading history: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "Người dùng không hợp lệ. Vui lòng đăng nhập lại.", Toast.LENGTH_SHORT).show();
            finish(); // Đóng activity nếu không có người dùng
        }
    }


    private void loadBookDetails(String bookId) {
        DatabaseReference bookRef = booksRef.child(bookId);
        bookRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Book book = dataSnapshot.getValue(Book.class);
                if (book != null) {
                    book.setId(bookId);
                    bookList.add(book);
                    bookAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(HistoryActivity.this, "Failed to load book details: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setListeners() {
        btnBack.setOnClickListener(v -> finish());
    }


    private void openBookDetail(String bookId) {
        Intent intent = new Intent(HistoryActivity.this, BookDetailActivity.class);
        intent.putExtra("book_id", bookId);
        startActivity(intent);
    }
}
