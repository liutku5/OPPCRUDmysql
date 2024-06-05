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

    public Book(long id, String title, String genre, long author_id) {
        this.id = id;
        this.title = title;
        this.genre = genre;
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
                Book bo = new Book(rs.getLong("id"), rs.getString("title"), rs.getString("genre"), rs.getLong("author_id"));
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
            System.out.println("Book with id: " + id + " Title: " + book.getTitle() + " Genre: " + book.getGenre() + " Author id: " + book.getAuthor_id());
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

    public static void addBook(Scanner sc) {
        Book book = new Book();
        System.out.println("Enter book title.");
        sc.nextLine();
        String title = sc.nextLine();
        System.out.println("Enter book genre.");
        String genre = sc.nextLine();
        Long author_id = null;
        while (true) {
            Author.printAuthor();
            System.out.println();
            System.out.println("Select author id from the list.");
            author_id = sc.nextLong();
            boolean hasAuthor = false;
            for (Author author : Author.selectAll()) {
                if (author.getId() == author_id) {
                    hasAuthor = true;
                    break;
                }
            }
            if (!hasAuthor) {
                System.out.println("Author not found. Please try again.");
                System.out.println();
            } else {
                book.createBook(title, genre, author_id);
                System.out.println("Book was added to the list.");
                return;
            }
        }

    }

    public static void createBook(String title, String genre, Long author_id) {

        String query = "INSERT INTO `books`(`title`, `genre`, `author_id`) VALUES (?, ?, ?)";
        try {
            Connection con = Main.connect();
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, title);
            pst.setString(2, genre);
            pst.setLong(3, author_id);
            pst.executeUpdate();
            con.close();
            pst.close();
        } catch (Exception e) {
            System.out.println("Failed to ad book to the list!");
        }
    }

    public static void changeBookInfo(Scanner sc) {
        System.out.println("Enter the id of the book info you wish to change.");
        long id = Book.ValidateInput.longVal(sc);
        sc.nextLine();
        Book book = findBookById(id);
        if (book != null) {
            System.out.println("Enter new book title.");
            book.setTitle(sc.nextLine());
            System.out.println("Enter new book genre.");
            book.setGenre(sc.nextLine());
            Long author_id = null;
            while (true) {
                Author.printAuthor();
                System.out.println();
                System.out.println("Select author id from the list.");
                author_id = sc.nextLong();
                boolean hasAuthor = false;
                for (Author author : Author.selectAll()) {
                    if (author.getId() == author_id) {
                        hasAuthor = true;
                        break;
                    }
                }
                if (!hasAuthor) {
                    System.out.println("Author not found. Please try again.");
                    System.out.println();
                } else {
                    book.setAuthor_id(author_id);
                    book.update();
                    System.out.println("The book was changed successfully.");
                    return;
                }
            }
        } else {
            System.out.println("No book found with id: " + id);
        }
    }

    public void update() {
        String query = "UPDATE `books` SET `title`= ? ,`genre`= ? ,`author_id`= ? WHERE id = ?";
        try {
            Connection con = Main.connect();
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, this.title);
            pst.setString(2, this.genre);
            pst.setLong(3, this.author_id);
            pst.setLong(4, this.id);
            pst.executeUpdate();
            con.close();
            pst.close();
        } catch (Exception e) {
            System.out.println("Failed to update book!");
        }
    }

    public static void removeBook(Scanner sc) {
        System.out.println("Enter the id of the Book you wish to remove.");
        long id = Book.ValidateInput.longVal(sc);
        sc.nextLine();
        Book book = findBookById(id);
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

    public static void printBookByAuthorId(Scanner sc) {
        long id = Book.ValidateInput.longVal(sc);
        sc.nextLine();
        Book book = findBookById(id);
        if (book != null) {
            System.out.println("Title: " + book.getTitle() + " Genre: " + book.getGenre());
        } else {
            System.out.println("No book were find");
        }
    }

    public static ArrayList<Book> findBookByAuthorId(long id) {
        ArrayList<Book> books = new ArrayList<>();
        String query = "SELECT * FROM books WHERE author_id =?";
        try {
            Connection con = Main.connect();
            PreparedStatement pst = con.prepareStatement(query);
            pst.setLong(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Book book = new Book(rs.getLong("id"), rs.getString("title"), rs.getString("genre"), rs.getLong("author_id"));
                books.add(book);
            }
            con.close();
            pst.close();
            rs.close();
        } catch (Exception e) {
            System.out.println("Failed to retrieve books!");
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

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public long getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(long author_id) {
        this.author_id = author_id;
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
