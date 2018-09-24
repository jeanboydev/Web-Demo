package com.jeanboy.web.demo.domain.cache;

import com.jeanboy.web.demo.domain.entity.PermissionEntity;
import com.jeanboy.web.demo.domain.entity.RoleEntity;

import java.util.HashMap;
import java.util.Map;

public class MemoryCache {

    private static Map<Integer, PermissionEntity> permissionMap = new HashMap<>();
    private static Map<Integer, RoleEntity> roleMap = new HashMap<>();
}
