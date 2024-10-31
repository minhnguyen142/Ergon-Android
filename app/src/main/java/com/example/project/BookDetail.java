package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

    private ImageView bookCoverDetail, btnBack;
    private TextView bookTitleDetail, bookGenreDetail, bookRatingDetail, bookPriceDetail;
    private Button readButton, addLibraryButton;
    private DatabaseReference databaseReference;
    private String userId; // Giữ nguyên userId

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        initializeViews();
        handleIntentData();
        setupBackButton();
        loadBookImageAndFetchDetails();
    }

    private void initializeViews() {
        bookCoverDetail = findViewById(R.id.bookCoverDetail);
        bookTitleDetail = findViewById(R.id.bookTitleDetail);
        bookGenreDetail = findViewById(R.id.bookGenreDetail);
        bookRatingDetail = findViewById(R.id.bookRatingDetail);
        bookPriceDetail = findViewById(R.id.bookPriceDetail);
        readButton = findViewById(R.id.readButton);
        addLibraryButton = findViewById(R.id.addLibrary);
        btnBack = findViewById(R.id.IconCLose);
    }

    private void handleIntentData() {
        // Nhận userId từ Intent
        Intent intent = getIntent();
        if (intent.hasExtra("user_id")) {
            userId = intent.getStringExtra("user_id");
            Log.d("BookDetail", "User ID: " + userId);
        } else {
            Log.e("BookDetail", "User ID không có trong Intent!");
            Toast.makeText(this, "User ID không hợp lệ!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void loadBookImageAndFetchDetails() {
        String bookImage = getIntent().getStringExtra("book_image");
        if (bookImage != null) {
            Glide.with(this).load(bookImage).into(bookCoverDetail);
            fetchBookDetails(bookImage);
        } else {
            showToast("Không tìm thấy ảnh sách!");
            finish();
        }
    }

    private void setupBackButton() {
        btnBack.setOnClickListener(v -> finish());
    }

    private void fetchBookDetails(String bookImage) {
        databaseReference = FirebaseDatabase.getInstance().getReference("books");
        databaseReference.orderByChild("coverUrl").equalTo(bookImage)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                Book book = dataSnapshot.getValue(Book.class);
                                String bookId = dataSnapshot.getKey();
                                if (book != null && bookId != null) {
                                    displayBookDetails(book);
                                    setupReadButton(book, bookId);
                                }
                            }
                        } else {
                            showToast("Không tìm thấy thông tin sách!");
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        showToast("Lỗi kết nối Firebase: " + error.getMessage());
                    }
                });
    }

    private void displayBookDetails(Book book) {
        bookTitleDetail.setText(book.getTitle());
        bookGenreDetail.setText(book.getGenre());
        bookRatingDetail.setText("5.0 (10 Nhận xét)"); // Giả định
        bookPriceDetail.setText("Miễn phí - Bạn có thể thêm vào thư viện và đọc trọn vẹn cuốn sách miễn phí");
    }

    private void setupReadButton(Book book, String bookId) {
        Intent readIntent = new Intent(this, PdfViewerActivity.class);
        readIntent.putExtra("pdfUrl", book.getPdfUrl());
        readIntent.putExtra("content", book.getContent());

        readButton.setOnClickListener(v -> {
            addBookToHistory(bookId);
            startActivity(readIntent);
        });
    }

    private void addBookToHistory(String bookId) {
        DatabaseReference historyRef = FirebaseDatabase.getInstance().getReference("users")
                .child(userId)
                .child("readingHistory");

        historyRef.child(bookId).setValue(true).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                showToast("Đã thêm sách vào lịch sử đọc.");
            } else {
                showToast("Thêm sách vào lịch sử đọc thất bại.");
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
