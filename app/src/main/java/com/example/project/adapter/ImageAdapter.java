package com.example.project.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project.R;
import com.example.project.model.Book;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private List<Book> bookList;

    public ImageAdapter(List<Book> bookList) {
        this.bookList = bookList;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Book book = bookList.get(position);
        Glide.with(holder.itemView.getContext())
                .load(book.getCoverUrl())
                .into(holder.imageView);
        holder.imageView.setBackgroundResource(R.drawable.border_drawable);

        // Set title and author text
//        holder.titleTextView.setText(book.getTitle());
//        holder.authorTextView.setText(book.getAuthor());
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView titleTextView;
        TextView authorTextView;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgTrend);
//            titleTextView = itemView.findViewById(R.id.bookTitle);
//            authorTextView = itemView.findViewById(R.id.bookAuthor);
        }
    }
}
