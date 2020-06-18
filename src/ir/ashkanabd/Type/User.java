package ir.ashkanabd.Type;

import ir.ashkanabd.Context;

import java.util.ArrayList;
import java.util.List;

public class User extends Type {
    private String username;
    private String password;
    private boolean isAdmin = false;

    public User(Context context) {
        super(context);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public List<Book> getBookList(){
        List<Book> bookList = new ArrayList<>();
        for (Book b : getContext().getBooks().values()) {
            if (b.getRenterId() != null && b.getRenterId().equals(getId())) {
                bookList.add(b);
            }
        }
        return bookList;
    }

    @Override
    public String toString() {
        return "<" + getId() + ", " + getUsername() + ">";
    }

    @Override
    public int hashCode() {
        return getId().hashCode() + getUsername().hashCode() + getPassword().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof User)) {
            return false;
        }
        User u = (User) obj;
        return u.getId().equals(this.getId());
    }
}
