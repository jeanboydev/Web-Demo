package com.jeanboy.web.demo.utils;

import com.jeanboy.web.demo.domain.cache.MemoryCache;
import com.jeanboy.web.demo.domain.entity.RolePermissionEntity;

public class PermissionUtil {

    public static boolean check(int roleId, int table, int identity) {
        RolePermissionEntity rolePermissionEntity = MemoryCache.getPermissionMap().get(roleId);
        if (rolePermissionEntity == null) return false;
        int permissionIdentity = rolePermissionEntity.getPermissionIdentity();
        System.out.println("=======check====permissionIdentity===" + permissionIdentity);
        int authTable = permissionIdentity & table;
        System.out.println("=======check====authTable===" + authTable);
        if (authTable == table) {
            int authIdentity = permissionIdentity & identity;
            System.out.println("=======check====authIdentity===" + authIdentity);
            return authIdentity == identity;
        }
        return false;
    }
}
