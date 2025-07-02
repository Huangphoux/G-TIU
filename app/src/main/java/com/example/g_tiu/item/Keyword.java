package com.example.g_tiu.item;

import androidx.annotation.Nullable;

public class Keyword {

    private int id;
    private String name;

    public Keyword(int id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public boolean equals(@Nullable Object obj) {
        return obj instanceof Keyword && ((Keyword) obj).getId() == id;
    }
}

