package com.example.project.Fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.project.Adapter.Books_Adapter;
import com.example.project.Class.Books;
import com.example.project.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends Fragment {

    private DatabaseReference db;
    private List<Books> trendingBooks, recommendedBooks;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);

        // Initialize Firebase Database reference
        db = FirebaseDatabase.getInstance().getReference();

        // Initialize book lists
        trendingBooks = new ArrayList<>();
        recommendedBooks = new ArrayList<>();

        // Load data from Firebase
        loadBooksFromFirebase(view);

        return view;
    }

    private void loadBooksFromFirebase(View view) {
        db.child("books").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                trendingBooks.clear();
                recommendedBooks.clear();

                int count = 0;
                for (DataSnapshot bookSnapshot : dataSnapshot.getChildren()) {
                    Books book = bookSnapshot.getValue(Books.class);

                    if (book != null && book.getCoverUrl() != null) { // Kiểm tra xem book có hợp lệ không
                        if (count < 5) { // First 5 books for Trending
                            trendingBooks.add(book);
                        } else if (count < 10) { // Next 5 books for Recommended
                            recommendedBooks.add(book);
                        }
                        count++;
                    }

                    if (count >= 10) break; // Load a total of 10 books (5 trending, 5 recommended)
                }

                // Set up RecyclerView adapters for Trending and Recommended sections
                setUpRecyclerView(view);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), "Failed to load data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setUpRecyclerView(View view) {
        RecyclerView trendingRecyclerView = view.findViewById(R.id.recycler_trending);
        trendingRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        trendingRecyclerView.setAdapter(new Books_Adapter(trendingBooks));

        RecyclerView recommendedRecyclerView = view.findViewById(R.id.recycler_dexuat);
        recommendedRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recommendedRecyclerView.setAdapter(new Books_Adapter(recommendedBooks));
    }

}
