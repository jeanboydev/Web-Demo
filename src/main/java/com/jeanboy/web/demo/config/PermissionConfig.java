package com.jeanboy.web.demo.config;

import java.util.HashMap;
import java.util.Map;

public class PermissionConfig {

    public static final int IDENTITY_SELECT = 1;    //0001
    public static final int IDENTITY_INSERT = 2;    //0010
    public static final int IDENTITY_UPDATE = 4;    //0100
    public static final int IDENTITY_DELETE = 8;    //1000


    public static final int TABLE_PERMISSION = 16;          //0000 0000 0001 0000
    public static final int TABLE_ROLE = 32;                //0000 0000 0010 0000
    public static final int TABLE_ROLE_PERMISSION = 64;     //0000 0000 0100 0000
    public static final int TABLE_DEPARTMENT = 128;         //0000 0000 1000 0000
    public static final int TABLE_JOB = 256;                //0000 0001 0000 0000
    public static final int TABLE_SALARY = 512;             //0000 0010 0000 0000
    public static final int TABLE_USER = 1024;              //0000 0100 0000 0000
    public static final int TABLE_ATTENDANCE_TYPE = 2048;   //0000 1000 0000 0000
    public static final int TABLE_ATTENDANCE = 4096;        //0001 0000 0000 0000

    public static final Map<Integer, String> tableMap = new HashMap<>();

    static {
        tableMap.put(TABLE_PERMISSION, "权限表");
        tableMap.put(TABLE_ROLE, "角色表");
        tableMap.put(TABLE_ROLE_PERMISSION, "角色-权限表");
        tableMap.put(TABLE_DEPARTMENT, "部门表");
        tableMap.put(TABLE_JOB, "职位表");
        tableMap.put(TABLE_SALARY, "薪资表");
        tableMap.put(TABLE_USER, "用户表");
        tableMap.put(TABLE_ATTENDANCE_TYPE, "考勤-类型表");
        tableMap.put(TABLE_ATTENDANCE, "考勤表");
    }
}
