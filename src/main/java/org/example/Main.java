package org.example;

import org.example.models.Author;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        while (true) {
            printInfoMessege();
            int author = Author.intInput(sc);
            sc.nextLine();
            while (true) {
                switch (author) {
                    case 1:
                        System.out.println(Author.selectAll());
                        break;
                    case 2:
                        Author.printAuthorById(sc);
                        break;
                    case 3:
                        Author.addAuthor(sc);
                        break;
                    case 4:
                        Author.changeAuthorInfo(sc);
                        break;
                    case 5:
                        Author.removeAuthor(sc);
                    case 6:
                        System.exit(1);
                }
                break;
            }
        }
    }
    public static void printInfoMessege () {
            System.out.println();
            System.out.println("--------------------------");
            System.out.println("1. Show author list.");
            System.out.println("2. Choose author from the list by id.");
            System.out.println("3. Add author to the list.");
            System.out.println("4. Edit author information");
            System.out.println("5. Remove author from the list");
            System.out.println("6. Close program");
            System.out.println("--------------------------");
            System.out.println();
    }

    public static Connection connect() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "");
        } catch (Exception e) {
            System.out.println("Failed to connect to database");
        }
        return connection;
    }
}