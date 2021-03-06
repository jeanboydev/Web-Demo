package com.jeanboy.web.demo.utils;

import com.jeanboy.web.demo.config.PermissionConfig;
import com.jeanboy.web.demo.domain.cache.MemoryCache;
import com.jeanboy.web.demo.domain.entity.RolePermissionEntity;

import java.util.List;

public class PermissionUtil {

    public static boolean check(int roleId, int table, int identity, boolean isPrivileged) {
        List<RolePermissionEntity> rolePermissionList = MemoryCache.getRolePermissionEntity(roleId);
        if (rolePermissionList == null) return false;
        for(RolePermissionEntity rolePermissionEntity:rolePermissionList){
            int permissionIdentity = rolePermissionEntity.getPermissionIdentity();
            System.out.println("=======check====permissionIdentity===" + permissionIdentity);
            int authTable = permissionIdentity & table;
            System.out.println("=======check====authTable===" + authTable);
            if (authTable == table) {
                int authIdentity = permissionIdentity & identity;
                System.out.println("=======check====authIdentity===" + authIdentity);
                if (isPrivileged) {
                    int factor = identity << PermissionConfig.IDENTITY_FACTOR;
                    int authFactor = permissionIdentity & factor;
                    System.out.println("=======check====authFactor===" + authFactor);
                    return authIdentity == identity && authFactor == factor;
                }
                return authIdentity == identity;
            }
        }
        return false;
    }
}
