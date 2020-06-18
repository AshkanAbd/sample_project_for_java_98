package ir.ashkanabd.Type;

import ir.ashkanabd.Context;

public class Book extends Type {
    private String name;
    private Integer categoryId;
    private Integer renterId;

    public Book(Context context) {
        super(context);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getRenterId() {
        return renterId;
    }

    public void setRenterId(Integer renterId) {
        this.renterId = renterId;
    }

    public Category getCategory(){
        return getContext().getCategories().get(getCategoryId());
    }

    public User getUser(){
        if (getRenterId() == null) {
            return null;
        }
        return getContext().getUsers().get(getRenterId());
    }

    @Override
    public String toString() {
        return "<" + getId() + ", " + getName() + ">";
    }

    @Override
    public int hashCode() {
        return getId().hashCode() + getName().hashCode() + getCategoryId().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Book)) {
            return false;
        }
        Book b = (Book) obj;
        return b.getId().equals(this.getId());
    }
}
