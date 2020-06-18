package ir.ashkanabd.Type;

import ir.ashkanabd.Context;

import java.util.ArrayList;
import java.util.List;

public class Category extends Type {
    private String name;

    public Category(Context context) {
        super(context);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Book> getBookList() {
        List<Book> bookList = new ArrayList<>();
        for (Book b : getContext().getBooks().values()) {
            if (b.getCategoryId().equals(getId())) {
                bookList.add(b);
            }
        }
        return bookList;
    }

    @Override
    public String toString() {
        return "<" + getId() + ", " + getName() + ">";
    }

    @Override
    public int hashCode() {
        return getId().hashCode() + getName().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Category)) {
            return false;
        }
        Category c = (Category) obj;
        return c.getId().equals(this.getId());
    }
}
