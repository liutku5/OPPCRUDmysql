package org.example.models;

import java.util.Scanner;

public class Book {
    private long id;
    private String title;
    private String genre;
    private long author_id;

    public Book() {

    }

    public Book(long id, String genre, String title, long author_id) {
        this.id = id;
        this.genre = genre;
        this.title = title;
        this.author_id = author_id;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(long author_id) {
        this.author_id = author_id;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", genre='" + genre + '\'' +
                ", author_id=" + author_id +
                '}';
    }
}
