package org.example.models;

import org.example.Main;

import java.sql.Connection;
import java.sql.PreparedStatement;
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

    public static void printBookById(Scanner sc) {
        System.out.println("Enter the id of the book.");
        long id = Book.ValidateInput.longVal(sc);
        sc.nextLine();
        Book book = findBookById(id);
        if (book != null) {
            System.out.println("Book with id " + id + ": " + book.getTitle() + " " + book.getGenre() + " " + book.getAuthor_id());
        } else {
            System.out.println("No book found with id: " + id);
        }
    }

    public static Book findBookById(long id) {

        String query = "SELECT * FROM books where id = ?";
        Book book = null;
        try {
            Connection con = Main.connect();
            PreparedStatement pst = con.prepareStatement(query);
            pst.setLong(1, id);
            ResultSet rs = pst.executeQuery();
            while ((rs.next())) {
                book = new Book(rs.getLong("id"), rs.getString("title"), rs.getString("genre"), rs.getLong("author_id"));
            }
            con.close();
            pst.close();
            rs.close();
        } catch (Exception e) {
            System.out.println("Failed to find book.");
        }
        return book;
    }

    public static void removeBook(Scanner sc) {
        System.out.println("Enter the id of the Book you wish to remove.");
        long id = Book.ValidateInput.longVal(sc);
        sc.nextLine();
        Book book = findBookById(id);
//        reikia patikrin ar yra knygu priskirti
        if (book != null) {
            delete(id);
            System.out.println("The book with id " + id + " was removed.");
        } else {
            System.out.println("No book found with id: " + id);
        }
    }

    public static void delete(long id) {
        String query = "DELETE FROM `books` WHERE id = ?";
        try {
            Connection con = Main.connect();
            PreparedStatement pst = con.prepareStatement(query);
            pst.setLong(1, id);
            pst.executeUpdate();
            con.close();
            pst.close();
        } catch (Exception e) {
            System.out.println("Failed to delete book!");
        }
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

    public class ValidateInput {
        public static long longVal(Scanner sc) {
            while (true) {
                try {
                    return sc.nextLong();
                } catch (Exception e) {
                    System.out.println("Please enter a valid long value.");
                    sc.next();
                }
            }
        }
    }

    @Override
    public String toString() {
        return id + "." + " Title: " + title + " Genre: " + genre + " Author id: " + author_id + ";";
    }
}
