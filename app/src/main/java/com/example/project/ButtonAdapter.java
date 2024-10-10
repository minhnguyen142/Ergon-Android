package com.example.project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ButtonAdapter extends RecyclerView.Adapter<ButtonAdapter.ButtonViewHolder> {

    private final List<ButtonItem> buttonItemList;
    private final OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public ButtonAdapter(List<ButtonItem> buttonItemList, OnItemClickListener onItemClickListener) {
        this.buttonItemList = buttonItemList;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ButtonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_button, parent, false);
        return new ButtonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ButtonViewHolder holder, int position) {
        ButtonItem buttonItem = buttonItemList.get(position);
        holder.bind(buttonItem, onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return buttonItemList.size();
    }

    public static class ButtonViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageViewIcon;
        private final TextView textViewTitle;
        private final View itemView;

        public ButtonViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            imageViewIcon = itemView.findViewById(R.id.imageViewIcon);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
        }

        public void bind(ButtonItem buttonItem, OnItemClickListener listener) {
            imageViewIcon.setImageResource(buttonItem.getIconResId());
            textViewTitle.setText(buttonItem.getTitle());

            // Thay đổi màu nền dựa trên trạng thái chọn
            if (buttonItem.isSelected()) {
                itemView.setBackgroundResource(R.drawable.button_selected);
            } else {
                itemView.setBackgroundResource(R.drawable.button_unselected);
            }

            itemView.setOnClickListener(v -> listener.onItemClick(getAdapterPosition()));
        }
    }
}