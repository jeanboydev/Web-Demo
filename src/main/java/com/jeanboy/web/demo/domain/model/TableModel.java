package com.jeanboy.web.demo.domain.model;

public class TableModel {

    private int identity;
    private String name;

    public TableModel() {
    }

    public TableModel(int identity, String name) {
        this.identity = identity;
        this.name = name;
    }

    public int getIdentity() {
        return identity;
    }

    public void setIdentity(int identity) {
        this.identity = identity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
