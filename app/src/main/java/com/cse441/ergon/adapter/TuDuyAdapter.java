package com.cse441.ergon.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.cse441.ergon.ui.BookDetailActivity;
import com.cse441.ergon.R;
import com.cse441.ergon.model.Book;

import java.util.List;

public class TuDuyAdapter extends RecyclerView.Adapter<TuDuyAdapter.VanhocViewHolder> {

    private List<Book> bookList;
    private Context context;

    public TuDuyAdapter(List<Book> bookList, Context context) {
        this.context = context;
        this.bookList = bookList;
    }

    @NonNull
    @Override
    public VanhocViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tuduy, parent, false);
        return new VanhocViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VanhocViewHolder holder, int position) {
        Book book = bookList.get(position);
        String imageUrl = book.getCoverUrl();
        Glide.with(holder.itemView.getContext())
                .load(imageUrl)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(16)))
                .into(holder.imageView);
        holder.bookTitle.setText(book.getTitle());
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, BookDetailActivity.class);
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

    public static class VanhocViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView bookTitle;

        public VanhocViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.bookImgTuDuy);
            bookTitle = itemView.findViewById(R.id.bookTitleTuDuy);
        }
    }
}
