package com.example.project.History_book;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
    public void onBindViewHolder(@NonNull Books_Adapter.BookViewHolder holder, int position) {
        Books books = booksList.get(position);
        holder.imageView.setImageResource(books.getImage());
        holder.title.setText(books.getTitle());
        holder.author.setText(books.getAuthor());
        holder.progressBar.setProgress(books.getProgress());

    }

    @Override
    public int getItemCount() {
        return 0;
    }
    static class BookViewHolder extends RecyclerView.ViewHolder {
        TextView title, author;
        ProgressBar progressBar;
        ImageView imageView;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            title = itemView.findViewById(R.id.tv2);
            author = itemView.findViewById(R.id.tv3);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }
}
