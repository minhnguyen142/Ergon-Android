package com.example.project.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.R;
import com.example.project.entity.Ebook;

import java.util.List;

public class EbookAdapter extends RecyclerView.Adapter<EbookAdapter.EbookViewHolder> {

    private List<Ebook> ebookList;

    public EbookAdapter(List<Ebook> ebookList) {
        this.ebookList = ebookList;
    }

    @NonNull
    @Override
    public EbookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ebook, parent, false);
        return new EbookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EbookViewHolder holder, int position) {
        Ebook ebook = ebookList.get(position);
        holder.bookTitle.setText(ebook.getTitle());
        holder.bookAuthor.setText(ebook.getAuthor());
        holder.bookProgress.setText(ebook.getProgress() + "% đã đọc");
        holder.bookCover.setImageResource(ebook.getImageLink());
        holder.progressBar.setProgress(ebook.getProgress());

        holder.moreIcon.setOnClickListener(v -> {
            // Create PopupMenu
            PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
            popupMenu.inflate(R.menu.ebook_menu);

            // Set menu item click listener
            popupMenu.setOnMenuItemClickListener(menuItem -> {
                int id = menuItem.getItemId();

                if (id == R.id.action_read_now) {
                    Toast.makeText(v.getContext(), "Đọc ngay: " + ebook.getTitle(), Toast.LENGTH_SHORT).show();
                    return true;
                } else if (id == R.id.action_review) {
                    Toast.makeText(v.getContext(), "Viết đánh giá cho: " + ebook.getTitle(), Toast.LENGTH_SHORT).show();
                    return true;
                } else if (id == R.id.action_delete) {
                    Toast.makeText(v.getContext(), "Xóa tệp: " + ebook.getTitle(), Toast.LENGTH_SHORT).show();
                    return true;
                } else if (id == R.id.action_share) {
                    Toast.makeText(v.getContext(), "Chia sẻ: " + ebook.getTitle(), Toast.LENGTH_SHORT).show();
                    return true;
                }

                return false;
            });

            // Show the PopupMenu
            popupMenu.show();
        });
    }


    @Override
    public int getItemCount() {
        return ebookList.size();
    }

    static class EbookViewHolder extends RecyclerView.ViewHolder {
        ImageView bookCover, moreIcon, favoriteIcon;
        TextView bookTitle, bookAuthor, bookProgress;
        ProgressBar progressBar;

        public EbookViewHolder(@NonNull View itemView) {
            super(itemView);
            bookCover = itemView.findViewById(R.id.bookCover);
            bookTitle = itemView.findViewById(R.id.bookTitle);
            bookAuthor = itemView.findViewById(R.id.bookAuthor);
            bookProgress = itemView.findViewById(R.id.bookProgress);
            progressBar = itemView.findViewById(R.id.progressBar);
            moreIcon = itemView.findViewById(R.id.moreIcon);
            favoriteIcon = itemView.findViewById(R.id.favoriteIcon);
        }
    }
}
