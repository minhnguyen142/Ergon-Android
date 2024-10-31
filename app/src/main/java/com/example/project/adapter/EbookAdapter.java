package com.example.project.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project.BookDetail;
import com.example.project.R;
import com.example.project.model.Book;

import java.util.List;

public class EbookAdapter extends RecyclerView.Adapter<EbookAdapter.EbookViewHolder> {
    private List<Book> bookList;
    private Context context;

    public EbookAdapter(List<Book> bookList, Context context) {
        this.bookList = bookList;
        this.context = context;
    }
    @NonNull
    @Override
    public EbookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ebook, parent, false);
        return new EbookViewHolder(view);
    }
    public void onBindViewHolder(@NonNull EbookViewHolder holder, int position) {
        Book book = bookList.get(position);
        Glide.with(holder.itemView.getContext())
                .load(book.getCoverUrl())
                .into(holder.bookImg);
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, BookDetail.class);
            intent.putExtra("book_image", book.getCoverUrl());
            SharedPreferences sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
            String userId = sharedPreferences.getString("user_id", null);
            intent.putExtra("user_id", userId);
            context.startActivity(intent);
        });
    }
    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public class EbookViewHolder extends RecyclerView.ViewHolder {
        ImageView bookImg;
        public EbookViewHolder(@NonNull View itemView) {
            super(itemView);
            bookImg = itemView.findViewById(R.id.imageEbook);
        }
    }
}
