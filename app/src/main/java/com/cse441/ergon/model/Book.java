package com.cse441.ergon.model;

import java.io.Serializable;

public class Book implements Serializable {
    private String id; // ID của sách
    private String title; // Tiêu đề sách
    private String author; // Tác giả sách
    private String contentDescription; // Mô tả nội dung sách
    private String coverUrl; // URL hình ảnh bìa sách
    private String genre; // Thể loại sách
    private String pdfUrl; // URL đến file PDF của sách
    private Boolean trend; // Trạng thái xu hướng của sách
    private String content; // Nội dung sách

    // Constructor mặc định cần thiết cho Firebase
    public Book() {
    }

    // Constructor với tất cả các thuộc tính
    public Book(String id, String title, String author, String contentDescription,
                String coverUrl, String genre, String pdfUrl, Boolean trend, String content) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.contentDescription = contentDescription;
        this.coverUrl = coverUrl;
        this.genre = genre;
        this.pdfUrl = pdfUrl;
        this.trend = trend;
        this.content = content;
    }

    // Getter cho các thuộc tính
    public String getId() {
        return id;
    }
    public String setId(String id) {
        return this.id = id;
    }
    public String getContent() {
        return content;
    }

    public Boolean getTrend() {
        return trend;
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
