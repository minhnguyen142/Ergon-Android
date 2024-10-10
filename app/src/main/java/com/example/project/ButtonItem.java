package com.example.project;

public class ButtonItem {
    private final int iconResId;
    private final String title;
    private boolean isSelected;

    public ButtonItem(int iconResId, String title) {
        this.iconResId = iconResId;
        this.title = title;
        this.isSelected = false;
    }

    public int getIconResId() {
        return iconResId;
    }

    public String getTitle() {
        return title;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
