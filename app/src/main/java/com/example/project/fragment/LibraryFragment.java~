package com.example.project.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.ui.BookDetailActivity;
import com.example.project.R;
import com.example.project.adapter.BookAdapter;
import com.example.project.model.Book;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LibraryFragment extends Fragment {

    private BookAdapter bookAdapter;
    private List<Book> bookList;
    private DatabaseReference databaseReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_library, container, false);

        RecyclerView rvBookList = view.findViewById(R.id.recyclerView);
        rvBookList.setLayoutManager(new LinearLayoutManager(getContext()));
        bookList = new ArrayList<>();
        bookAdapter = new BookAdapter(getContext(), bookList);
        rvBookList.setAdapter(bookAdapter);

        // Firebase reference to the library
        databaseReference = FirebaseDatabase.getInstance().getReference("users").child("library");

        // Fetch data from Firebase
        loadBooksFromFirebase();

        return view;
    }

    private void loadBooksFromFirebase() {
        // Lấy userId từ SharedPreferences
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("user_id", null);

        if (userId != null) {
            DatabaseReference historyRef = FirebaseDatabase.getInstance().getReference("users").child(userId).child("library");

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
                        Toast.makeText(getContext(), "Không có lịch sử đọc nào", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getContext(), "Failed to load reading history: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(getContext(), "Người dùng không hợp lệ. Vui lòng đăng nhập lại.", Toast.LENGTH_SHORT).show();
            getActivity().finish();
        }
    }

    private void loadBookDetails(String bookId) {
        DatabaseReference bookRef = FirebaseDatabase.getInstance().getReference("books").child(bookId); // Tham chiếu đến sách
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
                Toast.makeText(LibraryFragment.this.getContext(), "Failed to load book details: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show(); // Sửa thành Library.this
            }
        });
    }

    private void openBookDetail(String bookId) {
        Intent intent = new Intent(getContext(), BookDetailActivity.class);
        intent.putExtra("book_id", bookId);
        startActivity(intent);
    }
}
