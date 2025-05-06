package com.example.g_tiu.domain.data;

public class PhanLoai {
    private Integer id;
    private String name;
    private String type;
    private long budget;

    public PhanLoai(int id, String name, String type, long budget) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.budget = budget;
    }

    public PhanLoai(String name, String type, long budget) {
        this.name = name;
        this.type = type;
        this.budget = budget;
    }

    public PhanLoai() {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getBudget() {
        return budget;
    }

    public void setBudget(long budget) {
        this.budget = budget;
    }
}
