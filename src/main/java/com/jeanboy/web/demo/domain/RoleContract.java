package com.jeanboy.web.demo.domain;

import com.jeanboy.web.demo.base.BaseContract;
import com.jeanboy.web.demo.domain.entity.RoleEntity;

public interface RoleContract {

    interface Repository extends BaseContract<Integer, RoleEntity> {

        RoleEntity findByIdentity(Integer identity);
    }

    interface Service {

        RoleEntity get(Integer id);

        RoleEntity findByIdentity(Integer identity);

        Integer save(RoleEntity model);

        void update(RoleEntity model);
    }
}
