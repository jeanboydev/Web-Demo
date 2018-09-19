package com.jeanboy.web.demo.domain;

import com.jeanboy.web.demo.base.BaseContract;
import com.jeanboy.web.demo.domain.entity.RolePermissionEntity;

public interface RolePermissionContract {

    interface Repository extends BaseContract<Long, RolePermissionEntity> {

    }

    interface Service {

        RolePermissionEntity get(Long id);

        Long save(RolePermissionEntity model);

        void update(RolePermissionEntity model);
    }
}
