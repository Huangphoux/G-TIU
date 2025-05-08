package com.example.g_tiu.item;

public class Category {
    private int id;
    private String name;
    private boolean isHeader;
    private String type;
    private long budget;

    public Category(int id, String name, boolean isHeader) {
        this.id = id;
        this.name = name;
        this.isHeader = isHeader;
    }

    public Category(String name, String type, long budget) {
        this.name = name;
        this.type = type;
        this.isHeader = false;
        this.budget = budget;
    }

    public Category(int id, String name, boolean isHeader, long budget) {
        this.id = id;
        this.name = name;
        this.isHeader = isHeader;
        this.budget = budget;
    }

    public Category(int id, String name, String type, long budget) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.budget = budget;
    }

    public long getBudget() {
        return budget;
    }

    public void setBudget(long budget) {
        this.budget = budget;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
