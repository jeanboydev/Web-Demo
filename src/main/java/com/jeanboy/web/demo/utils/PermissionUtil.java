package com.jeanboy.web.demo.utils;

import com.jeanboy.web.demo.domain.entity.RolePermissionEntity;

import java.util.List;

public class PermissionUtil {

    public static boolean check(List<RolePermissionEntity> rolePermissionList, int table, int identity) {
        if (rolePermissionList.isEmpty()) return false;
        for (RolePermissionEntity entity : rolePermissionList) {
            int permissionIdentity = entity.getPermissionIdentity();
            System.out.println("=======check====permissionIdentity===" + permissionIdentity);
            int authTable = permissionIdentity & table;
            System.out.println("=======check====authTable===" + authTable);
            if (authTable == table) {
                int authIdentity = permissionIdentity & identity;
                System.out.println("=======check====authIdentity===" + authIdentity);
                if (authIdentity == identity) {
                    return true;
                }
            }
        }
        return false;
    }
}
