package com.example.g_tiu.item;

public class Transactions {
    private long id;
    private String date;
    private long amount;
    private int categoryId;
    private String note;
    private long createTime;
    private String keys;
    private Category category;

    public Transactions(int id, String date, long amount, int categoryId, String note) {
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.categoryId = categoryId;
        this.note = note;
    }

    public Transactions(long id, String date, long amount, int categoryId, String note, long createTime, Category category) {
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.categoryId = categoryId;
        this.note = note;
        this.createTime = createTime;
        this.category = category;
    }

    public Transactions(long id, String date, long amount, int categoryId, String note, String keys, long createTime, Category category) {
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.categoryId = categoryId;
        this.note = note;
        this.keys = keys;
        this.createTime = createTime;
        this.category = category;
    }

    public Transactions(String date, long amount, int categoryId, String note) {
        this.date = date;
        this.amount = amount;
        this.categoryId = categoryId;
        this.note = note;
    }

    public Transactions(int id, String date, long amount, int categoryId, String note, Category category) {
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.categoryId = categoryId;
        this.note = note;
        this.category = category;
    }

    public Transactions(long id, String date, long amount, int categoryId, String note, long createTime, String keys, Category category) {
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.categoryId = categoryId;
        this.note = note;
        this.createTime = createTime;
        this.keys = keys;
        this.category = category;
    }

    public Transactions() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
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

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getKeys() {
        return keys;
    }

    public void setKeys(String keys) {
        this.keys = keys;
    }
}
