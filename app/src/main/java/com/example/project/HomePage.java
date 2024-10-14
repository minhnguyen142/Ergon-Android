package com.example.project;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import androidx.appcompat.app.AppCompatActivity;

public class HomePage extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        ImageView trendingBook1 = findViewById(R.id.iv_trending_book_1);
        ImageView recommendedBook1 = findViewById(R.id.iv_dexuat_book_1);

        // Fetch data from Firestore
        db.collection("Ergon")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        int count = 0; // Đếm số tài liệu đã xử lý
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String imageUrl = document.getString("imageUrl");
                            String title = document.getString("title");

                            if (count == 0) {
                                // Load image for the first trending book
                                Glide.with(HomePage.this)
                                        .load(imageUrl)
                                        .into(trendingBook1);
                            } else if (count == 1) {
                                // Load image for the first recommended book
                                Glide.with(HomePage.this)
                                        .load(imageUrl)
                                        .into(recommendedBook1);
                            }
                            count++;
                        }
                    } else {
                        Toast.makeText(HomePage.this, "Failed to load data.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
