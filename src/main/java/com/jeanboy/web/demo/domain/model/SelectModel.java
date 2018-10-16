package com.jeanboy.web.demo.domain.model;

public class SelectModel {

    private long id;
    private String name;

    public SelectModel() {
    }

    public SelectModel(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
