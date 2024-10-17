package com.example.project.Fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.project.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomePage extends Fragment {

    private DatabaseReference db;
    private ImageView trendingBook1;
    private ImageView recommendedBook1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);

        // Initialize views
        trendingBook1 = view.findViewById(R.id.iv_trending_book_1);
        recommendedBook1 = view.findViewById(R.id.iv_dexuat_book_1);

        // Initialize Firebase Database reference
        db = FirebaseDatabase.getInstance().getReference("Ergon");

        // Load data from Firebase
        loadBooksFromFirebase();

        return view;
    }

    private void loadBooksFromFirebase() {
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int count = 0;
                for (DataSnapshot bookSnapshot : dataSnapshot.getChildren()) {
                    String imageUrl = bookSnapshot.child("imageUrl").getValue(String.class);
                    String title = bookSnapshot.child("title").getValue(String.class);

                    // Load images into ImageViews using Glide
                    if (count == 0 && imageUrl != null) {
                        Glide.with(getContext())
                                .load(imageUrl)
                                .into(trendingBook1);
                    } else if (count == 1 && imageUrl != null) {
                        Glide.with(getContext())
                                .load(imageUrl)
                                .into(recommendedBook1);
                    }
                    count++;
                    if (count >= 2) break; // Load only two books
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), "Failed to load data.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
