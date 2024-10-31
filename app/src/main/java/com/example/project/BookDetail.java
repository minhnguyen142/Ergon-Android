package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.project.model.Book;
import com.example.project.ui.PdfViewerActivity;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class BookDetail extends AppCompatActivity {

    private ImageView bookCoverDetail, btnBack, iconShare;
    private TextView bookTitleDetail, bookAuthorDetail, bookGenreDetail, bookRatingDetail, bookPriceDetail;
    private Button readButton, addLibraryButton;
    private DatabaseReference databaseReference;
    private String userId;

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
        bookAuthorDetail = findViewById(R.id.bookAuthorDetail);
        bookGenreDetail = findViewById(R.id.bookGenreDetail);
        bookRatingDetail = findViewById(R.id.bookRatingDetail);
        bookPriceDetail = findViewById(R.id.bookPriceDetail);
        readButton = findViewById(R.id.readButton);
        addLibraryButton = findViewById(R.id.addLibrary);
        btnBack = findViewById(R.id.IconCLose);
        iconShare = findViewById(R.id.IconShare);
        iconShare.setOnClickListener(v -> showShareBottomSheet());
    }

    private void handleIntentData() {
        Intent intent = getIntent();
        if (intent.hasExtra("user_id")) {
            userId = intent.getStringExtra("user_id");
            Log.d("BookDetail", "User ID: " + userId);
        } else {
            Log.e("BookDetail", "User ID not found in Intent!");
            Toast.makeText(this, "Invalid User ID!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void loadBookImageAndFetchDetails() {
        String bookImage = getIntent().getStringExtra("book_image");
        if (bookImage != null) {
            Glide.with(this).load(bookImage).into(bookCoverDetail);
            fetchBookDetails(bookImage);
        } else {
            showToast("Book image not found!");
            finish();
        }
    }

    private void setupBackButton() {
        btnBack.setOnClickListener(v -> finish());
        addLibraryButton.setOnClickListener(v -> addBookToLibrary());
    }

    private void showShareBottomSheet() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.activity_share_book);

        ImageView closeShareSheet = bottomSheetDialog.findViewById(R.id.closeShareSheet);
        if (closeShareSheet != null) {
            closeShareSheet.setOnClickListener(v -> bottomSheetDialog.dismiss());
        }
        bottomSheetDialog.show();
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
                            showToast("Book details not found!");
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        showToast("Firebase connection error: " + error.getMessage());
                    }
                });
    }

    private void displayBookDetails(Book book) {
        bookTitleDetail.setText(book.getTitle());
        bookAuthorDetail.setText(book.getAuthor());
        bookGenreDetail.setText(book.getGenre());
        bookRatingDetail.setText("5.0 (10 reviews)");
        bookPriceDetail.setText("Free - You can add this book to your library and read it for free");
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
                showToast("Added book to reading history.");
            } else {
                showToast("Failed to add book to reading history.");
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void addBookToLibrary() {
        if (userId == null) {
            new AlertDialog.Builder(this)
                    .setTitle("Notification")
                    .setMessage("Please log in to add books to your library!")
                    .setNegativeButton("Log in", (v, d) -> {
                        Intent intent = new Intent(BookDetail.this, Login.class);
                        startActivity(intent);
                    })
                    .setPositiveButton("OK", null)
                    .show();
            return;
        }

        databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userId).child("library");

        String bookTitle = bookTitleDetail.getText().toString();
        String bookCoverUrl = getIntent().getStringExtra("book_image");
        String bookAuthor = bookAuthorDetail.getText().toString();

        Map<String, Object> bookData = new HashMap<>();
        bookData.put("title", bookTitle);
        bookData.put("coverUrl", bookCoverUrl);
        bookData.put("author", bookAuthor);

        String bookId = databaseReference.push().getKey();

        if (bookId != null) {
            databaseReference.child(bookId).setValue(bookData)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            showBookAddedDialog(new Book());
                        }
                    });
        }
    }

    private void showBookAddedDialog(Book book) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.activity_dialog_added_book, null);
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();

        Button btnReadNow = dialogView.findViewById(R.id.btnDocNgay);
        Button btnLater = dialogView.findViewById(R.id.btbDeSau);

        btnReadNow.setOnClickListener(v -> {
            Intent intent = new Intent(BookDetail.this, PdfViewerActivity.class);
            intent.putExtra("pdfUrl", book.getPdfUrl());
            startActivity(intent);
        });

        btnLater.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }
}
