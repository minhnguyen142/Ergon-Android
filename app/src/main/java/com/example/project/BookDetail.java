package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.project.model.Book;
import com.example.project.ui.PdfViewerActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class BookDetail extends AppCompatActivity {

    private ImageView bookCoverDetail, btnback;
    private TextView bookTitleDetail, bookGenreDetail, bookRatingDetail, bookPriceDetail, bookAuthorDetail;
    private Button readButton, addLibraryButton;
    private DatabaseReference databaseReference;
    private String userId; // Khai báo userId

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        bookCoverDetail = findViewById(R.id.bookCoverDetail);
        bookTitleDetail = findViewById(R.id.bookTitleDetail);
        bookAuthorDetail = findViewById(R.id.bookAuthorDetail);
        bookGenreDetail = findViewById(R.id.bookGenreDetail);
        bookRatingDetail = findViewById(R.id.bookRatingDetail);
        bookPriceDetail = findViewById(R.id.bookPriceDetail);
        readButton = findViewById(R.id.readButton);
        addLibraryButton = findViewById(R.id.addLibrary);
        btnback = findViewById(R.id.IconCLose);

        // Giả sử bạn đã lưu userId khi đăng nhập
        userId = getIntent().getStringExtra("userid");

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
        addLibraryButton.setOnClickListener(v -> addBookToLibrary());
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
                            bookAuthorDetail.setText(book.getAuthor());
                            bookGenreDetail.setText(book.getGenre());
                            bookRatingDetail.setText("5.0 (10 Nhận xét)");
                            bookPriceDetail.setText("Miễn phí - Bạn có thể thêm vào thư viện và đọc trọn vẹn cuốn sách miễn phí");

                            Intent readIntent = new Intent(BookDetail.this, PdfViewerActivity.class);
                            readIntent.putExtra("pdfUrl", book.getPdfUrl());
                            readIntent.putExtra("content", book.getContent());
                            readIntent.putExtra("contentDescription", book.getContentDescription());

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


    private void addBookToLibrary() {

        // Kiem tra xem nguoi dung da dang nhap chua
        if (userId == null) {
            // Neu chua dang nhap, hien thi thong bao
            new AlertDialog.Builder(this)
                    .setTitle("Thông báo")
                    .setMessage("Người dùng vui lòng đăng nhập để thêm sách vào thư viện!")
                    .setNegativeButton("Đăng nhập", (v, d) -> {
                        Intent intent = new Intent(BookDetail.this, Login.class);
                        startActivity(intent);
                    })
                    .setPositiveButton("OK", null)
                    .show();
            return;
        }
        // Truy cập vào Firebase để thêm sách vào thư viện của người dùng
        databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userId).child("library");

        // Lấy thông tin sách hiện tại
        String bookTitle = bookTitleDetail.getText().toString();
        String bookCoverUrl = getIntent().getStringExtra("book_image");
        String bookAuthor = bookAuthorDetail.getText().toString();

        // Tạo một map để lưu thông tin sách
        Map<String, Object> bookData = new HashMap<>();
        bookData.put("title", bookTitle);
        bookData.put("coverUrl", bookCoverUrl);
        bookData.put("author", bookAuthor);

        // Tạo node với id là "bookXX" và thêm sách vào thư viện
        String bookId = databaseReference.push().getKey(); // Tạo key ngẫu nhiên cho mỗi sách

        if (bookId != null) {
            databaseReference.child(bookId).setValue(bookData)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            showBookAddedDialog(new Book()); // Hiển thị thông báo khi thêm sách thành công
                        }
                    });
        }
    }

    private void showBookAddedDialog(Book book) {
        // Tạo một AlertDialog để hiển thị thông báo sách đã thêm vào thư viện
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.activity_dialog_added_book, null);
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();

        // Ánh xạ các nút "Đọc ngay" và "Để sau" trong dialog
        Button btnDocNgay = dialogView.findViewById(R.id.btnDocNgay);
        Button btbDeSau = dialogView.findViewById(R.id.btbDeSau);

        // Xử lý sự kiện khi nhấn nút "Đọc ngay"
        btnDocNgay.setOnClickListener(v -> {
            Intent readIntent = new Intent(BookDetail.this, PdfViewerActivity.class);
            readIntent.putExtra("pdfUrl", book.getPdfUrl());
            readIntent.putExtra("content", book.getContent());
            readIntent.putExtra("contentDescription", book.getContentDescription());
            startActivity(readIntent);
            dialog.dismiss();
        });

        // Xử lý sự kiện khi nhấn nút "Để sau"
        btbDeSau.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
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
