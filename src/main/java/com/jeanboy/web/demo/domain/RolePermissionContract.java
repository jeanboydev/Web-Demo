package com.jeanboy.web.demo.domain;

import com.jeanboy.web.demo.base.BaseContract;
import com.jeanboy.web.demo.domain.entity.RolePermissionEntity;

import java.util.List;

public interface RolePermissionContract {

    interface Repository extends BaseContract<Long, RolePermissionEntity> {

        List<RolePermissionEntity> findByIdentity(Integer identity);
    }

    interface Service {

        RolePermissionEntity get(Long id);

        List<RolePermissionEntity> findByIdentity(Integer identity);

        Long save(RolePermissionEntity model);

        void update(RolePermissionEntity model);
    }
}
