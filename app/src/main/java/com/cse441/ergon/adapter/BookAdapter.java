
package com.cse441.ergon.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cse441.ergon.ui.BookDetailActivity;
import com.cse441.ergon.R;
import com.cse441.ergon.model.Book;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
    private List<Book> booksList;
    private Context context;
//    private OnBookClickListener listener;

    // Khai báo interface cho listener
//    public interface OnBookClickListener {
//        void onBookClick(String bookId);
//    }

    // Cập nhật constructor để nhận listener
    public BookAdapter(Context context, List<Book> booksList) {
        this.context = context;
        this.booksList = booksList;
//        this.listener = listener;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = booksList.get(position);
        holder.title.setText(book.getTitle());
        holder.author.setText(book.getAuthor());
        Glide.with(holder.itemView.getContext())
                .load(book.getCoverUrl())
                .into(holder.imageView);

        holder.itemView.setOnClickListener(v -> {
            if (book.getId() != null) {
                SharedPreferences sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
                String userId = sharedPreferences.getString("user_id", null);
                Intent intent = new Intent(context, BookDetailActivity.class);
                intent.putExtra("book_image", book.getCoverUrl());
                intent.putExtra("book_id", book.getId());
                intent.putExtra("user_id", userId);
                context.startActivity(intent);
//                listener.onBookClick(book.getId());
            } else {
                Toast.makeText(context, "ID sách không hợp lệ", Toast.LENGTH_SHORT).show();
            }
        });

        holder.arrowDownButton.setOnClickListener(v -> {
            showMenuBottomSheet();
        });
    }

    private void showMenuBottomSheet() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this.context);
        bottomSheetDialog.setContentView(R.layout.activity_library_menu);

        ImageView closeMenuSheet = bottomSheetDialog.findViewById(R.id.closeMenuSheet);
        if (closeMenuSheet != null) {
            closeMenuSheet.setOnClickListener(v -> bottomSheetDialog.dismiss());
        }
        bottomSheetDialog.show();
    }

    @Override
    public int getItemCount() {
        return booksList.size();
    }

    static class BookViewHolder extends RecyclerView.ViewHolder {
        TextView title, author;
        ImageView imageView;
        ImageButton arrowDownButton;


        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            title = itemView.findViewById(R.id.tv2);
            author = itemView.findViewById(R.id.tv3);
            arrowDownButton = itemView.findViewById(R.id.arrowDownButton);
        }
    }
}
