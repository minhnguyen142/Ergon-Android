package com.example.project.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project.Class.Books;
import com.example.project.R;

import java.util.List;

public class Books_Adapter extends RecyclerView.Adapter<Books_Adapter.BookViewHolder> {
    private List<Books> booksList;

    public Books_Adapter(List<Books> booksList) {
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
        Books book = booksList.get(position);
        holder.title.setText(book.getTitle());
        holder.author.setText(book.getAuthor());
        Glide.with(holder.itemView.getContext())
                .load(book.getCoverUrl())
                .placeholder(R.drawable.ic_launcher_background) // Placeholder image
                .into(holder.imageView);
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
