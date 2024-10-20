package com.example.project.entity;

public class Books {
    private String title;
    private String author;
    private String contentDescription;
    private String coverUrl;
    private String genre;
    private String pdfUrl;

    public Books() {
    }

    public Books(String title, String author, String contentDescription, String coverUrl, String genre, String pdfUrl) {
        this.title = title;
        this.author = author;
        this.contentDescription = contentDescription;
        this.coverUrl = coverUrl;
        this.genre = genre;
        this.pdfUrl = pdfUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getContentDescription() {
        return contentDescription;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public String getGenre() {
        return genre;
    }

    public String getPdfUrl() {
        return pdfUrl;
    }
}
