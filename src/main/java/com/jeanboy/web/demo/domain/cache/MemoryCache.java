package com.jeanboy.web.demo.domain.cache;

import com.jeanboy.web.demo.domain.entity.*;
import com.jeanboy.web.demo.domain.model.DepartmentModel;
import com.jeanboy.web.demo.domain.model.TokenModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemoryCache {

    private static Map<Integer, List<RolePermissionEntity>> permissionMap = new HashMap<>();
    private static Map<Integer, RoleEntity> roleMap = new HashMap<>();
    private static Map<Integer, JobEntity> jobMap = new HashMap<>();
    private static Map<Integer, DepartmentEntity> departmentMap = new HashMap<>();
    private static Map<String, UserEntity> userMap = new HashMap<>();
    private static Map<String, TokenModel> tokenMap = new HashMap<>();
    private static Map<Integer, AttendanceTypeEntity> attendanceTypeMap = new HashMap<>();

    public static List<RolePermissionEntity> getRolePermissionEntity(Integer roleId) {
        return permissionMap.get(roleId);
    }

    public static void putRolePermissionEntity(RolePermissionEntity rolePermissionEntity) {
        List<RolePermissionEntity> rolePermissionList = getRolePermissionEntity(rolePermissionEntity.getRoleId());
        if (rolePermissionList == null) {
            rolePermissionList = new ArrayList<>();
        }
        rolePermissionList.add(rolePermissionEntity);
        permissionMap.put(rolePermissionEntity.getRoleId(), rolePermissionList);
    }

    public static RoleEntity getRoleEntity(Integer roleId) {
        return roleMap.get(roleId);
    }

    public static void putRoleEntity(RoleEntity roleEntity) {
        roleMap.put(roleEntity.getId(), roleEntity);
    }

    public static JobEntity getJobEntity(Integer jobId) {
        return jobMap.get(jobId);
    }

    public static void putJobEntity(JobEntity jobEntity) {
        jobMap.put(jobEntity.getId(), jobEntity);
    }

    public static void removeJobEntity(Integer jobId) {
        jobMap.remove(jobId);
    }

    public static DepartmentEntity getDepartmentEntity(Integer departmentId) {
        return departmentMap.get(departmentId);
    }

    public static void putDepartmentEntity(DepartmentEntity departmentEntity) {
        departmentMap.put(departmentEntity.getId(), departmentEntity);
    }

    public static void removeDepartmentEntity(Integer departmentId) {
        departmentMap.remove(departmentId);
    }

    public static UserEntity getUserEntity(String token) {
        return userMap.get(token);
    }

    public static void putUserEntity(String token, UserEntity userEntity) {
        userMap.put(token, userEntity);
    }

    public static void putTokenModel(TokenModel tokenModel) {
        tokenMap.put(tokenModel.getToken(), tokenModel);
    }

    public static TokenModel getTokenModel(String token) {
        return tokenMap.get(token);
    }

    public static void putAttendanceTypeEntity(AttendanceTypeEntity attendanceTypeEntity) {
        attendanceTypeMap.put(attendanceTypeEntity.getId(), attendanceTypeEntity);
    }

    public static AttendanceTypeEntity getAttendanceTypeEntity(Integer attendanceTypeId) {
        return attendanceTypeMap.get(attendanceTypeId);
    }

    public static void removeAttendanceTypeEntity(Integer attendanceTypeId) {
        attendanceTypeMap.remove(attendanceTypeId);
    }
}
