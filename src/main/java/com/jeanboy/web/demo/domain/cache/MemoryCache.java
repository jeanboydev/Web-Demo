package com.jeanboy.web.demo.domain.cache;

import com.jeanboy.web.demo.domain.entity.RoleEntity;
import com.jeanboy.web.demo.domain.entity.RolePermissionEntity;
import com.jeanboy.web.demo.domain.entity.UserEntity;

import java.util.HashMap;
import java.util.Map;

public class MemoryCache {

    private static Map<Long, RolePermissionEntity> permissionMap = new HashMap<>();
    private static Map<Integer, RoleEntity> roleMap = new HashMap<>();
    private static Map<String, UserEntity> tokenMap = new HashMap<>();

    public static Map<Long, RolePermissionEntity> getPermissionMap() {
        return permissionMap;
    }

    public static Map<Integer, RoleEntity> getRoleMap() {
        return roleMap;
    }

    public static Map<String, UserEntity> getTokenMap() {
        return tokenMap;
    }

}
