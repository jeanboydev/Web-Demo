package com.jeanboy.web.demo.constants;

public enum EducationLevel {

    UNKNOWN(0, "未知"),
    PRIMARY_SCHOOL(1, "小学"),
    MIDDLE_SCHOOL(2, "中学"),
    HIGH_SCHOOL(3, "高中"),
    JUNIOR_COLLEGE(4, "大专"),
    UNIVERSITY(5, "本科"),
    UNDERGRADUATE(6, "研究生"),
    DOCTOR(7, "博士或更高");

    private final int value;
    private final String name;

    EducationLevel(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}