package ir.ashkanabd;

import ir.ashkanabd.Type.Book;
import ir.ashkanabd.Type.Category;
import ir.ashkanabd.Type.User;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;

public class Context {
    private String basePath;
    private HashMap<Integer, Category> categories;
    private HashMap<Integer, Book> books;
    private HashMap<Integer, User> users;

    Context(String basePath) {
        this.basePath = basePath;
        categories = new HashMap<>();
        books = new HashMap<>();
        users = new HashMap<>();
        load();
    }

    public void load() {
        try {
            loadCategories();
            loadBooks();
            loadUsers();
        } catch (IOException e) {
            //ignore
        }
    }

    private void loadCategories() throws IOException {
        categories.clear();
        File file = new File(basePath, "category.txt");
        Scanner scan = new Scanner(file);
        String line;
        while (scan.hasNextLine()) {
            line = scan.nextLine();
            if (line.startsWith("#")) {
                continue;
            }
            String[] data = line.split("[|]");
            Category c = new Category(this);
            c.setId(Integer.parseInt(data[0]));
            c.setName(data[1]);
            categories.put(c.getId(), c);
        }
        scan.close();
    }

    private void loadBooks() throws IOException {
        books.clear();
        File file = new File(basePath, "book.txt");
        Scanner scan = new Scanner(file);
        String line;
        while (scan.hasNextLine()) {
            line = scan.nextLine();
            if (line.startsWith("#")) {
                continue;
            }
            String[] data = line.split("[|]");
            Book b = new Book(this);
            b.setId(Integer.parseInt(data[0]));
            b.setName(data[1]);
            b.setCategoryId(Integer.parseInt(data[2]));
            if (Integer.parseInt(data[3]) != -1) {
                b.setRenterId(Integer.parseInt(data[3]));
            }
            books.put(b.getId(), b);
        }
        scan.close();
    }

    private void loadUsers() throws IOException {
        users.clear();
        File file = new File(basePath, "user.txt");
        Scanner scan = new Scanner(file);
        String line;
        while (scan.hasNextLine()) {
            line = scan.nextLine();
            if (line.startsWith("#")) {
                continue;
            }
            String[] data = line.split("[|]");
            User u = new User(this);
            u.setId(Integer.parseInt(data[0]));
            u.setUsername(data[1]);
            u.setPassword(data[2]);
            if (Integer.parseInt(data[3]) == 1) {
                u.setAdmin(true);
            }
            users.put(u.getId(), u);
        }
        scan.close();
    }

    public void save() {
        try {
            saveCategories();
            saveBooks();
            saveUsers();
        } catch (IOException e) {
            //ignore
        }
    }

    private void saveCategories() throws IOException {
        File file = new File(basePath, "category.txt");
        PrintWriter writer = new PrintWriter(file);
        writer.println("# id | name");
        for (Category c : categories.values()) {
            writer.print(c.getId());
            writer.print("|");
            writer.print(c.getName());
            writer.println();
        }
        writer.close();
    }

    private void saveBooks() throws IOException {
        File file = new File(basePath, "book.txt");
        PrintWriter writer = new PrintWriter(file);
        writer.println("# id | name | category_id | renter_id");
        for (Book b : books.values()) {
            writer.print(b.getId());
            writer.print("|");
            writer.print(b.getName());
            writer.print("|");
            writer.print(b.getCategoryId());
            writer.print("|");
            if (b.getRenterId() == null) {
                writer.print("-1");
            } else {
                writer.print(b.getRenterId());
            }
            writer.println();
        }
        writer.close();
    }

    private void saveUsers() throws IOException {
        File file = new File(basePath, "user.txt");
        PrintWriter writer = new PrintWriter(file);
        writer.println("# id | username | password | is_admin");
        for (User u : users.values()) {
            writer.print(u.getId());
            writer.print("|");
            writer.print(u.getUsername());
            writer.print("|");
            writer.print(u.getPassword());
            writer.print("|");
            if (u.isAdmin()) {
                writer.print("1");
            } else {
                writer.print("0");
            }
            writer.println();
        }
        writer.close();
    }

    public HashMap<Integer, Category> getCategories() {
        return categories;
    }

    public HashMap<Integer, Book> getBooks() {
        return books;
    }

    public HashMap<Integer, User> getUsers() {
        return users;
    }
}
