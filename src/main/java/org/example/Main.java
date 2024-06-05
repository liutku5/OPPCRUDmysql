package org.example;

import org.example.models.Author;
import org.example.models.Book;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static Scanner sc;

    public static void main(String[] args) {

        sc = new Scanner(System.in);
        while (true) {
            printMeniuInfo();
            switch (sc.nextInt()) {
                case 1:
                    authorMenu();
                    break;
                case 2:
                    bookMenu();
                    break;
                case 3:
                    System.exit(1);
                    break;
            }
        }
    }

    public static void authorMenu() {
        boolean authorM = true;
        while (authorM) {
            printAuthorMeniuInfo();
            switch (sc.nextInt()) {
                case 1:
                    Author.printAuthor();
                    break;
                case 2:
                    authorsSubMenu();
                    break;
                case 3:
                    Author.addAuthor(sc);
                    break;
                case 4:
                    Author.changeAuthorInfo(sc);
                    break;
                case 5:
                    Author.removeAuthor(sc);
                    break;
                case 6:
                    authorM = false;
                    break;
                case 7:
                    System.exit(1);
                    break;
            }
        }
    }

    public static void authorsSubMenu() {
        boolean bookSubM = true;
        while (bookSubM) {
            printAuthorSubMenu();
            switch (sc.nextInt()) {
                case 1:
                    Author.printAuthorById(sc);
                    break;
                case 2:
                    Author.printAuthorByNameSur(sc);
                    break;
                case 3:
                    bookSubM = false;
                    break;
                case 4:
                    System.exit(1);
                    break;
            }
        }
    }

    public static void bookMenu() {
        boolean bookM = true;
        while (bookM) {
            printBookMeniuInfo();
            switch (sc.nextInt()) {
                case 1:
                    Book.printBook();
                    break;
                case 2:
                    Book.printBookById(sc);
                    break;
                case 3:
                    Book.addBook(sc);
                    break;
                case 4:
                    Book.changeBookInfo(sc);
                    break;
                case 5:
                    Book.removeBook(sc);
                    break;
                case 6:
                    bookM = false;
                    break;
                case 7:
                    System.exit(1);
                    break;
            }
        }
    }

    public static void printMeniuInfo() {
        System.out.println();
        System.out.println("--------------------------");
        System.out.println("1. Authors");
        System.out.println("2. Books");
        System.out.println("3. Close program.");

        System.out.println("--------------------------");
    }

    public static void printAuthorMeniuInfo() {
        System.out.println();
        System.out.println("--------------------------");
        System.out.println("1. Show author list.");
        System.out.println("2. Search author.");
        System.out.println("3. Add author to the list.");
        System.out.println("4. Edit author information.");
        System.out.println("5. Remove author from the list.");
        System.out.println("6. Back to main menu.");
        System.out.println("7. Close program.");
        System.out.println("--------------------------");
        System.out.println();
    }

    public static void printAuthorSubMenu() {
        System.out.println();
        System.out.println("--------------------------");
        System.out.println("1. Search author by id.");
        System.out.println("2. Search author by name.");
        System.out.println("3. Back to author menu.");
        System.out.println("4. Close program.");
        System.out.println("--------------------------");
        System.out.println();
    }

    public static void printBookMeniuInfo() {
        System.out.println();
        System.out.println("--------------------------");
        System.out.println("1. Show book list.");
        System.out.println("2. Choose book by id.");
        System.out.println("3. Add book to the list.");
        System.out.println("4. Edit book information.");
        System.out.println("5. Remove book from the list.");
        System.out.println("6. Back to main menu.");
        System.out.println("7. Close program.");
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
