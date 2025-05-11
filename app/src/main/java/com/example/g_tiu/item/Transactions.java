package com.example.g_tiu.item;

public class Transactions {
    private int id;
    private String date;
    private double amount;
    private int categoryId;
    private String note;

    private Category category;

    public Transactions(int id, String date, double amount, int categoryId, String note) {
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.categoryId = categoryId;
        this.note = note;
    }

    public Transactions(int id, String date, double amount, int categoryId, String note, Category category) {
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.categoryId = categoryId;
        this.note = note;
        this.category = category;
    }

    public Transactions() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
