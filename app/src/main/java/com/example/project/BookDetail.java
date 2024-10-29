package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.project.model.Book;
import com.example.project.ui.PdfViewerActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class BookDetail extends AppCompatActivity {

    private ImageView bookCoverDetail, btnback;
    private TextView bookTitleDetail, bookGenreDetail, bookRatingDetail, bookPriceDetail;
    private Button readButton, addLibraryButton;
    private DatabaseReference databaseReference;
    private String userId; // Khai báo userId

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        bookCoverDetail = findViewById(R.id.bookCoverDetail);
        bookTitleDetail = findViewById(R.id.bookTitleDetail);
        bookGenreDetail = findViewById(R.id.bookGenreDetail);
        bookRatingDetail = findViewById(R.id.bookRatingDetail);
        bookPriceDetail = findViewById(R.id.bookPriceDetail);
        readButton = findViewById(R.id.readButton);
        addLibraryButton = findViewById(R.id.addLibrary);
        btnback = findViewById(R.id.IconCLose);

        // Giả sử bạn đã lưu userId khi đăng nhập
        userId = getIntent().getStringExtra("user_id");

        Intent intent = getIntent();
        String bookImage = intent.getStringExtra("book_image");

        if (bookImage != null) {
            Glide.with(this)
                    .load(bookImage)
                    .into(bookCoverDetail);

            fetchBookDetailsFromFirebase(bookImage);
        }

        btnback.setOnClickListener(v -> {
            finish();
        });
    }

    private void fetchBookDetailsFromFirebase(String bookImage) {
        databaseReference = FirebaseDatabase.getInstance().getReference("books");
        databaseReference.orderByChild("coverUrl").equalTo(bookImage).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Book book = dataSnapshot.getValue(Book.class);
                        if (book != null) {
                            bookTitleDetail.setText(book.getTitle());
                            bookGenreDetail.setText(book.getGenre());
                            bookRatingDetail.setText("5.0 (10 Nhận xét)");
                            bookPriceDetail.setText("Miễn phí - Bạn có thể thêm vào thư viện và đọc trọn vẹn cuốn sách miễn phí");

                            Intent readIntent = new Intent(BookDetail.this, PdfViewerActivity.class);
                            readIntent.putExtra("pdfUrl", book.getPdfUrl());
                            readIntent.putExtra("content", book.getContent());

                            readButton.setOnClickListener(v -> {
                                startActivity(readIntent);
//                                addToReadingHistory(book);
                            });
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

//    private void addToReadingHistory(Book book) {
//        DatabaseReference historyRef = FirebaseDatabase.getInstance().getReference("users").child(userId).child("readingHistory");
//        String historyId = historyRef.push().getKey();
//
//
//        ReadingHistory readingHistory = new ReadingHistory(
//                historyId,
//                book.getTitle(),
//                book.getAuthor(),
//                System.currentTimeMillis()
//        );
//
//        // Lưu vào Firebase
//        if (historyId != null) {
//            historyRef.child(historyId).setValue(readingHistory).addOnCompleteListener(task -> {
//                if (task.isSuccessful()) {} else {}
//            });
//        }
//    }
}
