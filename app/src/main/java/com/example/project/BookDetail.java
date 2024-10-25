package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.example.project.model.Book;

public class BookDetail extends AppCompatActivity {

    private ImageView bookCoverDetail, iconClose, iconShare;
    private TextView bookTitleDetail, bookAuthorDetail, bookGenreDetail, bookRatingDetail, bookPriceDetail;
    private Button readButton, addLibraryButton;

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


        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("book")) {
            Book book = (Book) intent.getSerializableExtra("book");


            bookTitleDetail.setText(book.getTitle());
            bookGenreDetail.setText(book.getGenre());
            bookRatingDetail.setText("5.0 (10 Nhận xét)");
            bookPriceDetail.setText("Miễn phí - Bạn có thể thêm vào thư viện và đọc trọn vẹn cuốn sách miễn phí");


            Glide.with(this)
                    .load(book.getCoverUrl())
                    .into(bookCoverDetail);
        }
    }
}
