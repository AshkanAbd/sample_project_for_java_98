package ir.ashkanabd.View;

import ir.ashkanabd.Context;
import ir.ashkanabd.Type.Book;
import ir.ashkanabd.Type.Category;
import ir.ashkanabd.Type.User;

import java.util.List;
import java.util.Scanner;

public class View {
    private Context context;
    private Scanner scanner;
    private User user;

    public View(Context context) {
        this.context = context;
        scanner = new Scanner(System.in);
        welcome();
    }

    private int getIntInput() {
        Integer result = null;
        while (result == null) {
            try {
                result = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Please enter a valid integer");
            }
        }
        return result;
    }

    private void welcome() {
        System.out.println("1) Register");
        System.out.println("2) Login");
        switch (getIntInput()) {
            case 1:
                register();
                break;
            case 2:
                login();
                break;
            default:
                welcome();
                break;
        }
    }

    private void register() {
        System.out.println("Enter username:");
        String username = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();
        for (User user : context.getUsers().values()) {
            if (user.getUsername().equals(username)) {
                System.out.println("Username exists!!!");
                welcome();
                return;
            }
        }
        User user = new User(context);
        User lastUser = (User) context.getUsers().values().toArray()[context.getUsers().size() - 1];
        user.setId(lastUser.getId() + 1);
        user.setUsername(username);
        user.setPassword(password);
        user.setAdmin(false);
        context.getUsers().put(user.getId(), user);
        context.save();
        this.user = user;
        userMenu();
    }

    private void login() {
        System.out.println("Enter username:");
        String username = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();
        for (User user : context.getUsers().values()) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                this.user = user;
                if (user.isAdmin()) {
                    adminMenu();
                } else {
                    userMenu();
                }
                return;
            }
        }
        System.out.println("Username or password is incorrect!!!");
        welcome();
    }

    private void adminMenu() {
        System.out.println("Admin menu:");
        System.out.println("1) Manage categories");
        System.out.println("2) Manage books");
        switch (getIntInput()) {
            case 1:
                manageCategories();
                break;
            case 2:
                manageBooks();
                break;
        }
        adminMenu();
    }

    private void userMenu() {
        System.out.println("User menu:");
        System.out.println("1) Category list");
        System.out.println("2) Book list");
        System.out.println("3) Search book");
        System.out.println("4) Rented books");
        System.out.println("5) Rent a book");
        System.out.println("6) Giveback book");
        switch (getIntInput()) {
            case 1:
                categoryList();
                break;
            case 2:
                bookList();
                break;
            case 3:
                searchByName();
                break;
            case 4:
                getRentedList();
                break;
            case 5:
                rentBook();
                break;
            case 6:
                giveBack();
                break;
        }
        userMenu();
    }

    private void manageCategories() {
        System.out.println("1) Category list");
        System.out.println("2) Create category");
        System.out.println("3) Edit category");
        System.out.println("4) delete category");
        switch (getIntInput()) {
            case 1:
                categoryList();
                break;
            case 2:
                createCategory();
                break;
            case 3:
                editCategory();
                break;
            case 4:
                deleteCategory();
                break;
            default:
                adminMenu();
                break;
        }
        manageCategories();
    }

    private void categoryList() {
        for (Category c : context.getCategories().values()) {
            System.out.print(c);
            System.out.print(", Book: ");
            System.out.print(c.getBookList());
            System.out.println();
        }
    }

    private void createCategory() {
        System.out.println("Enter category name:");
        String name = scanner.nextLine();
        for (Category c : context.getCategories().values()) {
            if (c.getName().equals(name)) {
                System.out.println("Category exists!!!");
                return;
            }
        }
        Category category = new Category(context);
        if (context.getCategories().size() != 0) {
            Category lsatCategory = (Category) context.getCategories().values().toArray()[context.getCategories().size() - 1];
            category.setId(lsatCategory.getId() + 1);
        } else {
            category.setId(1);
        }
        category.setName(name);
        context.getCategories().put(category.getId(), category);
        context.save();
    }

    private void editCategory() {
        System.out.println("Enter category id:");
        Integer id = getIntInput();
        System.out.println("Enter category name:");
        String name = scanner.nextLine();
        if (!context.getCategories().containsKey(id)) {
            System.out.println("Category notfound");
            return;
        }
        for (Category c : context.getCategories().values()) {
            if (!c.getId().equals(id) && c.getName().equals(name)) {
                System.out.println("Category exists!!!");
                return;
            }
        }
        Category category = context.getCategories().get(id);
        category.setName(name);
        context.save();
    }

    private void deleteCategory() {
        System.out.println("Enter category id:");
        Integer id = getIntInput();
        List<Book> bookList = context.getCategories().get(id).getBookList();
        for (Book book : bookList) {
            context.getBooks().remove(book.getId());
        }
        context.getCategories().remove(id);
        context.save();
    }

    private void manageBooks() {
        System.out.println("Manage books");
        System.out.println("1) Book list");
        System.out.println("2) create book");
        System.out.println("3) edit book");
        System.out.println("4) delete book");
        switch (getIntInput()) {
            case 1:
                bookList();
                break;
            case 2:
                createBook();
                break;
            case 3:
                editBook();
                break;
            case 4:
                deleteBook();
                break;
            default:
                adminMenu();
                break;
        }
        manageBooks();
    }

    private void bookList() {
        for (Book b : context.getBooks().values()) {
            System.out.print(b);
            System.out.print(", Category: ");
            System.out.print(b.getCategory());
            if (b.getRenterId() == null) {
                System.out.println(", available");
            } else {
                System.out.println(", rented by " + b.getUser().getUsername());
            }
        }
    }

    private void createBook() {
        System.out.println("Enter category id");
        int categoryId = getIntInput();
        System.out.println("Enter book name");
        String name = scanner.nextLine();
        for (Book b : context.getBooks().values()) {
            if (b.getName().equals(name) && b.getCategoryId().equals(categoryId)) {
                System.out.println("Book exists");
                return;
            }
        }
        if (!context.getCategories().containsKey(categoryId)) {
            System.out.println("Category notfound");
            return;
        }
        Book b = new Book(context);
        if (context.getBooks().size() != 0) {
            Book lastBook = (Book) context.getBooks().values().toArray()[context.getBooks().size() - 1];
            b.setId(lastBook.getId() + 1);
        } else {
            b.setId(1);
        }
        b.setName(name);
        b.setCategoryId(categoryId);
        b.setRenterId(null);
        context.getBooks().put(b.getId(), b);
        context.save();
    }

    private void editBook() {
        System.out.println("Enter book id:");
        int bookId = getIntInput();
        System.out.println("Enter category id");
        int categoryId = getIntInput();
        System.out.println("Enter book name");
        String name = scanner.nextLine();

        if (!context.getBooks().containsKey(bookId)) {
            System.out.println("Book notfound");
            return;
        }
        if (!context.getCategories().containsKey(categoryId) && categoryId != -1) {
            System.out.println("Category notfound");
            return;
        }
        if (categoryId == -1 && name.isEmpty()) {
            return;
        }

        Book currentBook = context.getBooks().get(bookId);
        for (Book b : context.getBooks().values()) {
            if (b.getId().equals(bookId)) {
                continue;
            }

            if (categoryId != -1 && !name.isEmpty()) {
                if (b.getName().equals(name) && b.getCategoryId().equals(categoryId)) {
                    System.out.println("Book exists");
                    return;
                }
            } else if (!name.isEmpty() && categoryId == -1) {
                if (b.getName().equals(name) && b.getCategoryId().equals(currentBook.getCategoryId())) {
                    System.out.println("Book exists");
                    return;
                }
            } else if (name.isEmpty() && categoryId != -1) {
                if (b.getCategoryId().equals(categoryId) && b.getName().equals(currentBook.getName())) {
                    System.out.println("Book exists");
                    return;
                }
            }
        }
        if (!name.isEmpty()) {
            currentBook.setName(name);
        }

        if (categoryId != -1) {
            currentBook.setCategoryId(categoryId);
        }

        context.save();
    }

    private void deleteBook() {
        System.out.println("Enter book id:");
        int bookId = getIntInput();
        context.getBooks().remove(bookId);
        context.save();
    }

    private void searchByName() {
        System.out.println("Enter keyword:");
        String keyword = scanner.nextLine();
        for (Book b : context.getBooks().values()) {
            if (b.getName().toLowerCase().contains(keyword.toLowerCase())) {
                System.out.print(b);
                System.out.print(", Category");
                System.out.print(b.getCategory());
                if (b.getRenterId() == null) {
                    System.out.println(", available");
                } else {
                    System.out.println(", rented by " + b.getUser());
                }
            }
        }
    }

    private void getRentedList() {
        for (Book b : user.getBookList()) {
            System.out.print(b);
            System.out.print(", Category");
            System.out.print(b.getCategory());
            System.out.println();
        }
    }

    private void rentBook() {
        System.out.println("Enter book id:");
        int bookId = getIntInput();
        if (!context.getBooks().containsKey(bookId)) {
            System.out.println("Book notfound");
            return;
        }
        Book book = context.getBooks().get(bookId);
        if (book.getRenterId() != null && book.getRenterId().equals(user.getId())) {
            System.out.println("Book rented by yourself");
            return;
        }
        if (book.getRenterId() != null) {
            System.out.println("Book rented by an other user");
            return;
        }
        book.setRenterId(user.getId());
        context.save();
    }

    private void giveBack() {
        System.out.println("Enter book id:");
        int bookId = getIntInput();
        if (!context.getBooks().containsKey(bookId)) {
            System.out.println("Book notfound");
            return;
        }
        Book book = context.getBooks().get(bookId);
        if (book.getRenterId() != null && !book.getRenterId().equals(user.getId())) {
            System.out.println("You can't giveback this book.");
            return;
        }
        book.setRenterId(null);
        context.save();
    }

}
