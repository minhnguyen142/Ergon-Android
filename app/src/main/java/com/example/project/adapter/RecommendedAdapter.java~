package com.example.project.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.project.R;

import java.util.List;

public class ImageOnlyAdapter extends RecyclerView.Adapter<ImageOnlyAdapter.ViewHolder> {
    private List<String> bookImages;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(String bookImage);
    }

    public ImageOnlyAdapter(List<String> bookImages, OnItemClickListener listener) {
        this.bookImages = bookImages;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_only, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String imageUrl = bookImages.get(position);
        Glide.with(holder.imageView.getContext())
                .load(imageUrl)
                .into(holder.imageView);
        holder.itemView.setOnClickListener(v -> listener.onItemClick(imageUrl));
    }

    @Override
    public int getItemCount() {
        return bookImages.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_button2);
        }
    }
}

