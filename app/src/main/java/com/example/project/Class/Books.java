package com.example.project.Class;

public class Books {
    private String title;
    private String author;

    private int image;
//    private int progress;

    public Books(String author, int image, String title) {
        this.author = author;
        this.image = image;
//        this.progress = progress;
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

//    public int getProgress() {
//        return progress;
//    }
//
//    public void setProgress(int progress) {
//        this.progress = progress;
//    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
