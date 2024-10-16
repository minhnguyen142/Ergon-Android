package com.example.project;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import androidx.appcompat.app.AppCompatActivity;

public class HomePage extends AppCompatActivity {
    DatabaseReference db = FirebaseDatabase.getInstance().getReference("Ergon");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        ImageView trendingBook1 = findViewById(R.id.iv_trending_book_1);
        ImageView recommendedBook1 = findViewById(R.id.iv_dexuat_book_1);


        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int count = 0;
                for (DataSnapshot bookSnapshot : dataSnapshot.getChildren()) {
                    String imageUrl = bookSnapshot.child("imageUrl").getValue(String.class);
                    String title = bookSnapshot.child("title").getValue(String.class);

                    if (count == 0) {

                        Glide.with(HomePage.this)
                                .load(imageUrl)
                                .into(trendingBook1);
                    } else if (count == 1) {

                        Glide.with(HomePage.this)
                                .load(imageUrl)
                                .into(recommendedBook1);
                    }
                    count++;
                    if (count >= 2) break;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(HomePage.this, "Failed to load data.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
