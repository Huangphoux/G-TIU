package com.example.g_tiu.item;

public class Category {
    private int id;
    private String name;
    private boolean isHeader;

    public Category(int id, String name, boolean isHeader) {
        this.id = id;
        this.name = name;
        this.isHeader = isHeader;
    }

    public Category() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isHeader() {
        return isHeader;
    }

    public void setHeader(boolean header) {
        isHeader = header;
    }
}
