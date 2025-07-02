package com.example.g_tiu.item;

import java.io.Serializable;

public class Category implements Serializable {
    private int id;
    private String name;
    private boolean isHeader;
    private String type;
    private long budget;
    private String hex;
    private int icon;
    private long actual;

    private long lastTime;

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

    public Category(int id, String name, boolean isHeader, String type, long budget, long actual) {
        this.id = id;
        this.name = name;
        this.isHeader = isHeader;
        this.type = type;
        this.budget = budget;
        this.actual = actual;
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

    public Category(int id, String name, boolean isHeader, String type, long budget, String hex, int icon) {
        this.id = id;
        this.name = name;
        this.isHeader = isHeader;
        this.type = type;
        this.budget = budget;
        this.hex = hex;
        this.icon = icon;
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

    public long getActual() {
        return actual;
    }

    public void setActual(long actual) {
        this.actual = actual;
    }

    public String getHex() {
        return hex;
    }

    public void setHex(String hex) {
        this.hex = hex;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public long getLastTime() {
        return lastTime;
    }

    public void setLastTime(long lastTime) {
        this.lastTime = lastTime;
    }
}

