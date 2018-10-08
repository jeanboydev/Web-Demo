package com.jeanboy.web.demo.domain.model;

public class EnumModel {

    private int id;
    private String name;

    public EnumModel() {
    }

    public EnumModel(int id, String name) {
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
}
