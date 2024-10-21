package com.example.project.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.project.adapter.ImageAdapter;
import com.example.project.model.Book;
import com.example.project.R;
import com.example.project.model.Book;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends Fragment {

    private DatabaseReference db;
    private List<Book> trendingBooks, recommendedBooks;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home_page, container, false);


        db = FirebaseDatabase.getInstance().getReference("Ergon");

        trendingBooks = new ArrayList<>();
        recommendedBooks = new ArrayList<>();


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
                    Book book = bookSnapshot.getValue(Book.class);

                    if (book != null && book.getCoverUrl() != null) {
                        if (count < 5) { // First 5 books for Trending
                            trendingBooks.add(book);
                        } else if (count < 10) { // Next 5 books for Recommended
                            recommendedBooks.add(book);
                        }
                        count++;
                    }

                    if (count >= 10) break;
                }


                setUpRecyclerView(view);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), "Failed to load data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setUpRecyclerView(View view) {

        List<String> trendingImageUrls = new ArrayList<>();
        for (Book book : trendingBooks) {
            trendingImageUrls.add(book.getCoverUrl());
        }


        List<String> recommendedImageUrls = new ArrayList<>();
        for (Book book : recommendedBooks) {
            recommendedImageUrls.add(book.getCoverUrl());
        }


        RecyclerView trendingRecyclerView = view.findViewById(R.id.recycler_trending);
        trendingRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        trendingRecyclerView.setAdapter(new ImageAdapter(trendingImageUrls));

        
        RecyclerView recommendedRecyclerView = view.findViewById(R.id.recycler_dexuat);
        recommendedRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recommendedRecyclerView.setAdapter(new ImageAdapter(recommendedImageUrls));
    }

}
