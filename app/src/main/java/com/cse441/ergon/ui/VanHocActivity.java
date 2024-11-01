
package com.cse441.ergon.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cse441.ergon.R;
import com.cse441.ergon.adapter.VanHocAdapter;
import com.cse441.ergon.model.Book;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class VanHocActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ImageButton btnBack;
    private VanHocAdapter vanhocAdapter;
    private List<Book> bookList;
    private DatabaseReference booksRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_van_hoc);

        recyclerView = findViewById(R.id.recyclerVanHoc);
        btnBack = findViewById(R.id.btnVhback);

        // Lấy userId từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String userId = sharedPreferences.getString("user_id", null);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);

        bookList = new ArrayList<>();
        vanhocAdapter = new VanHocAdapter(bookList, this);
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
                Toast.makeText(VanHocActivity.this, "Failed to load books data: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
