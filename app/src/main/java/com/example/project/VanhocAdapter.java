package com.example.project;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.project.model.Book;

import java.util.List;

public class VanhocAdapter extends RecyclerView.Adapter<VanhocAdapter.VanhocViewHolder> {

    private List<Book> bookList;

    public VanhocAdapter(List<Book> bookList) {
        this.bookList = bookList;
    }

    @NonNull
    @Override
    public VanhocViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vanhoc, parent, false);
        return new VanhocViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VanhocViewHolder holder, int position) {
        Book book = bookList.get(position);
        String imageUrl = book.getCoverUrl();


        Log.d("VanhocAdapter", "Image URL: " + imageUrl);


        Glide.with(holder.itemView.getContext())
                .load(imageUrl)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(16)))
                .into(holder.imageView);
        holder.bookTitle.setText(book.getTitle());
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public static class VanhocViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView bookTitle;

        public VanhocViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.bookImg);
            bookTitle = itemView.findViewById(R.id.bookTitle);
        }
    }
}
