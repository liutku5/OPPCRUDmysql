package org.example.models;

import org.example.Main;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
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
    public static void printBook() {
        for (Book book : selectAllBook()) {
            System.out.print(book);
            System.out.println();
        }
    }
    public static ArrayList<Book> selectAllBook() {
        ArrayList<Book> books = new ArrayList<>();
        String query = "SELECT * FROM books ";
        try {
            Connection con = Main.connect();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while ((rs.next())) {
                Book bo = new Book(rs.getLong("id"), rs.getString("title"), rs.getString("genre"),rs.getLong("author_id"));
                books.add(bo);
            }
            con.close();
            stmt.close();
            rs.close();
        } catch (Exception e) {
            System.out.println("Failed to retrieve book list!");
        }
        return books;
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
    public static int intInput(Scanner sc) {
        while (true) {
            try {
                return sc.nextInt();
            } catch (Exception e) {
                System.out.println("Plese enter a digit");
                sc.nextLine();
            }
        }
    }

    @Override
    public String toString() {
        return id + "." + " Title: " + title + " Genre: " + genre + " Author id: " + author_id + ";";
    }
}
