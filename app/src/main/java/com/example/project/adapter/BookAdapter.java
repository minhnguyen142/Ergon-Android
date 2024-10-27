package com.example.project.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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

    public BookAdapter(Context context, List<Book> booksList) {
        this.context = context;
        this.booksList = booksList;
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
            Intent intent = new Intent(context, BookDetail.class);
            intent.putExtra("book_image", book.getCoverUrl());
            context.startActivity(intent);
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