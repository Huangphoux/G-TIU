package com.example.g_tiu.item;

public class Color {
    private int id;
    private String hex;

    public Color(int id, String hex) {
        this.id = id;
        this.hex = hex;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHex() {
        return hex;
    }

    public void setHex(String hex) {
        this.hex = hex;
    }
}

