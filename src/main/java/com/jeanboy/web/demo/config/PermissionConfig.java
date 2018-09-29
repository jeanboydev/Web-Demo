package com.jeanboy.web.demo.config;

import java.util.HashMap;
import java.util.Map;

public class PermissionConfig {

    public static final int ROLE_MASTER = 1048575;          //1111 1111 1111 1111 1111

    public static final int IDENTITY_FACTOR = 4;
    public static final int IDENTITY_SELECT = 1;            //0000 0000 0000 0000 0001
    public static final int IDENTITY_INSERT = 1 << 1;       //0000 0000 0000 0000 0010
    public static final int IDENTITY_UPDATE = 1 << 2;       //0000 0000 0000 0000 0100
    public static final int IDENTITY_DELETE = 1 << 3;       //0000 0000 0000 0000 1000

    public static final int TABLE_PERMISSION = 1 << 8;      //0000 0000 0001 0000 0000
    public static final int TABLE_ROLE = 1 << 9;            //0000 0000 0010 0000 0000
    public static final int TABLE_ROLE_PERMISSION = 1 << 10;//0000 0000 0100 0000 0000
    public static final int TABLE_DEPARTMENT = 1 << 11;     //0000 0000 1000 0000 0000
    public static final int TABLE_JOB = 1 << 12;            //0000 0001 0000 0000 0000
    public static final int TABLE_SALARY = 1 << 13;         //0000 0010 0000 0000 0000
    public static final int TABLE_USER = 1 << 14;           //0000 0100 0000 0000 0000
    public static final int TABLE_USER_INFO = 1 << 15;      //0000 1000 0000 0000 0000
    public static final int TABLE_ATTENDANCE_TYPE = 1 << 16;//0001 0000 0000 0000 0000
    public static final int TABLE_ATTENDANCE = 1 << 17;     //0010 0000 0000 0000 0000

    public static final Map<Integer, String> tableMap = new HashMap<>();

    static {
        tableMap.put(TABLE_PERMISSION, "权限表");
        tableMap.put(TABLE_ROLE, "角色表");
        tableMap.put(TABLE_ROLE_PERMISSION, "角色-权限表");
        tableMap.put(TABLE_DEPARTMENT, "部门表");
        tableMap.put(TABLE_JOB, "职位表");
        tableMap.put(TABLE_SALARY, "薪资表");
        tableMap.put(TABLE_USER, "用户表");
        tableMap.put(TABLE_USER_INFO, "用户信息表");
        tableMap.put(TABLE_ATTENDANCE_TYPE, "考勤-类型表");
        tableMap.put(TABLE_ATTENDANCE, "考勤表");
    }
}
