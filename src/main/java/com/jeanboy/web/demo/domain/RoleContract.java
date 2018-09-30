package com.jeanboy.web.demo.domain;

import com.jeanboy.web.demo.base.BaseContract;
import com.jeanboy.web.demo.domain.entity.RoleEntity;

import java.util.List;

public interface RoleContract {

    interface Repository extends BaseContract<Integer, RoleEntity> {

    }

    interface Service {

        RoleEntity get(Integer id);

        List<RoleEntity> getAll();

        Integer save(RoleEntity model);

        void update(RoleEntity model);

        void delete(Integer id);
    }
}
