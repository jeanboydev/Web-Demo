package com.jeanboy.web.demo.domain.cache;

import com.jeanboy.web.demo.domain.entity.RoleEntity;
import com.jeanboy.web.demo.domain.entity.RolePermissionEntity;
import com.jeanboy.web.demo.domain.entity.UserEntity;
import com.jeanboy.web.demo.domain.model.TokenModel;

import java.util.HashMap;
import java.util.Map;

public class MemoryCache {

    private static Map<Integer, RolePermissionEntity> permissionMap = new HashMap<>();
    private static Map<Integer, RoleEntity> roleMap = new HashMap<>();
    private static Map<String, UserEntity> userMap = new HashMap<>();
    private static Map<String, TokenModel> tokenMap = new HashMap<>();

    public static RolePermissionEntity getRolePermissionEntity(Integer roleId) {
        return permissionMap.get(roleId);
    }

    public static void putRolePermissionEntity(Integer roleId, RolePermissionEntity rolePermissionEntity) {
        permissionMap.put(roleId, rolePermissionEntity);
    }

    public static RoleEntity getRoleEntity(Integer roleId) {
        return roleMap.get(roleId);
    }

    public static void putRoleEntity(Integer roleId, RoleEntity roleEntity) {
        roleMap.put(roleId, roleEntity);
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

}
