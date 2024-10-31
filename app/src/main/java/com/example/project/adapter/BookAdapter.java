package com.example.project.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project.BookDetail;
import com.example.project.R;
import com.example.project.model.Book;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
    private List<Book> booksList;
    private Context context;
    private OnBookClickListener listener;

    // Khai báo interface cho listener
    public interface OnBookClickListener {
        void onBookClick(String bookId);
    }

    // Cập nhật constructor để nhận listener
    public BookAdapter(Context context, List<Book> booksList, OnBookClickListener listener) {
        this.context = context;
        this.booksList = booksList;
        this.listener = listener;
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
                listener.onBookClick(book.getId()); // Gọi phương thức trong listener
            } else {
                Toast.makeText(context, "ID sách không hợp lệ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return booksList.size();
    }

    static class BookViewHolder extends RecyclerView.ViewHolder {
        TextView title, author;
        ImageView imageView;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            title = itemView.findViewById(R.id.tv2);
            author = itemView.findViewById(R.id.tv3);
        }
    }
}
