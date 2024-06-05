package org.example.models;

import org.example.Main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class Author {
    private long id;
    private String name;
    private String surname;
    public Author() {
    }

    public Author(long id, String name, String surname) {
        this.id = id;
        this.name = name;
        this.surname = surname;
    }

    public static void printAuthor() {
        for (Author author : selectAll()) {
            System.out.print(author);
            System.out.println();
        }
    }

    public static ArrayList<Author> selectAll() {
        ArrayList<Author> authors = new ArrayList<>();
        String query = "SELECT * FROM authors ";
        try {
            Connection con = Main.connect();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while ((rs.next())) {
                Author aut = new Author(rs.getLong("id"), rs.getString("name"), rs.getString("surname"));
                authors.add(aut);
            }
            con.close();
            stmt.close();
            rs.close();
        } catch (Exception e) {
            System.out.println("Failed to retrieve author list!");
        }
        return authors;
    }

    public static void printAuthorById(Scanner sc) {
        System.out.println("Enter the id of the author.");
        long id = ValidateInput.longVal(sc);
        sc.nextLine();
        Author author = findById(id);
        if (author != null) {
            System.out.println("Author with id " + id + ": " + author.getName() + " " + author.getSurname());
            System.out.println("Do you want to se the books written by this author? (yes/no)");
            String response = sc.nextLine();
            if (response.equalsIgnoreCase("yes")) {
                ArrayList<Book> books = Book.findBookByAuthorId(id);
                if (!books.isEmpty()) {
                    System.out.println("Books written by " + author.getName() + " " + author.getSurname() + ":");
                    System.out.println();
                    for (Book book : books) {
                        System.out.println(book.getTitle() + " genre: " + book.getGenre());
                    }
                } else {
                    System.out.println("No books were fond.");
                }
            }
        } else {
            System.out.println("No author found with id: " + id);
        }
    }

    public static Author findById(long id) {
        String query = "SELECT * FROM authors where id = ?";
        Author aut = null;
        try {
            Connection con = Main.connect();
            PreparedStatement pst = con.prepareStatement(query);
            pst.setLong(1, id);
            ResultSet rs = pst.executeQuery();
            while ((rs.next())) {
                aut = new Author(rs.getLong("id"), rs.getString("name"), rs.getString("surname"));
            }
            con.close();
            pst.close();
            rs.close();
        } catch (Exception e) {
            System.out.println("Failed to find author");
        }
        return aut;
    }

    public static void printAuthorByNameSur(Scanner sc) {
        System.out.println("Enter the name and surname of the author.");
        String searchAut = sc.nextLine();
        ArrayList<Author> authors = findByNameSurname(searchAut);
        if (!authors.isEmpty()) {
            for (Author author : authors) {
                System.out.print("Id: " + author.getId() + " " + author.getName() + " " +  author.getSurname());
                System.out.println();
            }
        } else {
            System.out.println("No author found.");
        }
    }

    public static ArrayList<Author> findByNameSurname(String searchAut) {
        ArrayList<Author> authors = new ArrayList<>();
            String query = "SELECT id, name, surname FROM authors WHERE concat(name,' ', surname) like ? OR concat(surname, ' ', name) like ? ";
        try {
            Connection con = Main.connect();
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, "%" + searchAut + "%");
            pst.setString(2, "%" + searchAut + "%");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Author aut = new Author(rs.getLong("id"), rs.getString("name"), rs.getString("surname"));
                authors.add(aut);
            }
            con.close();
            pst.close();
            rs.close();
        } catch (Exception e) {
            System.out.println("Failed to find author");
        }
        return authors;
    }

    public static void addAuthor(Scanner sc) {
        Author author = new Author();
        System.out.println("Enter author name.");
        sc.nextLine();
        String name = sc.nextLine();
        System.out.println("Enter author surname.");
        String surname = sc.nextLine();
        author.create(name, surname);
        System.out.println("Author was added to the list.");
    }

    public static void create(String name, String surname) {

        String query = "INSERT INTO `authors`(`name`, `surname`) VALUES (?, ?)";
        try {
            Connection con = Main.connect();
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, name);
            pst.setString(2, surname);
            pst.executeUpdate();
            con.close();
            pst.close();
        } catch (Exception e) {
            System.out.println("Failed to ad author to the list!");
        }
    }

    public static void removeAuthor(Scanner sc) {
        System.out.println("Enter the id of the author you wish to remove.");
        long id = ValidateInput.longVal(sc);
        sc.nextLine();
        Author author = findById(id);
        if (author != null) {
            ArrayList<Book> books = Book.findBookByAuthorId(id);
            if (!books.isEmpty()) {
                System.out.println("The author has written the following books:");
                for (Book book : books) {
                    System.out.println("Title: " + book.getTitle());
                }
                System.out.println("Please remove books before removing the author.");
            } else {
                delete(id);
                System.out.println("The author with id " + id + " was removed.");
            }
        } else {
            System.out.println("No author found with id: " + id);
        }
    }


    public static void delete(long id) {
        String query = "DELETE FROM `authors` WHERE id = ?";
        try {
            Connection con = Main.connect();
            PreparedStatement pst = con.prepareStatement(query);
            pst.setLong(1, id);
            pst.executeUpdate();
            con.close();
            pst.close();
        } catch (Exception e) {
            System.out.println("Failed to delete author!");
        }
    }

    public static void changeAuthorInfo(Scanner sc) {
        System.out.println("Enter the id of the author info you wish to change.");
        long id = ValidateInput.longVal(sc);
        sc.nextLine();
        Author author = findById(id);
        if (author != null) {
            System.out.println("Enter new author name.");
            author.setName(sc.nextLine());
            System.out.println("Enter new author surname.");
            author.setSurname(sc.nextLine());
            author.update();
            System.out.println("The author was changed successfully.");
        } else {
            System.out.println("No author found with id: " + id);
        }
    }

    public void update() {
        String query = "UPDATE `authors` SET `name`= ? ,`surname`= ? WHERE id = ?";
        try {
            Connection con = Main.connect();
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, this.name);
            pst.setString(2, this.surname);
            pst.setLong(3, this.id);
            pst.executeUpdate();
            con.close();
            pst.close();
        } catch (Exception e) {
            System.out.println("Failed to update authors!");
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
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
        return id + "." + " Name: " + name + " Surname: " + surname + ";";

    }
}
