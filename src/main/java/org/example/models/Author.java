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

    public static Author findById(long id) {

        String query = "SELECT * FROM authors where id = ?" ;
        Author aut = null;
        try {
            Connection con = Main.connect();
            PreparedStatement pst = con.prepareStatement(query);
            pst.setLong(1,id);
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
    public static void create(Scanner sc) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter author name: ");
        String name = scanner.nextLine();
        System.out.print("Enter author surname: ");
        String surname = scanner.nextLine();

        String query = "INSERT INTO `authors`(`name`, `surname`) VALUES (?, ?)" ;
        try {
            Connection con = Main.connect();
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1,name);
            pst.setString(2,surname);
            pst.executeUpdate();
            con.close();
            pst.close();
        } catch (Exception e) {
            System.out.println("Failed to ad author to the list!");
        }
    }

    public static void delete(long id) {
        String query = "DELETE FROM `authors` WHERE id = ?" ;
        try {
            Connection con = Main.connect();
            PreparedStatement pst = con.prepareStatement(query);
            pst.setLong(1,id);
            pst.executeUpdate();
            con.close();
            pst.close();
        } catch (Exception e) {
            System.out.println("Failed to retrieve author!");
        }
    }
    public  void update() {
        String query = "UPDATE `authors` SET `name`= ? ,`surname`= ? WHERE id = ?" ;
        try {
            Connection con = Main.connect();
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1,this.name);
            pst.setString(2,this.surname);
            pst.setLong(3,this.id);
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
    @Override
    public String toString() {
        return "Author: " +
                "id: " + id +
                ", name: " + name  +
                ", surname: " + surname;
    }
}
