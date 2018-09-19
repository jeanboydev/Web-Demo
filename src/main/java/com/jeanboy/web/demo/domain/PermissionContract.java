package com.jeanboy.web.demo.domain;

import com.jeanboy.web.demo.base.BaseContract;
import com.jeanboy.web.demo.domain.entity.PermissionEntity;

import java.util.List;

public interface PermissionContract {

    interface Repository extends BaseContract<Integer, PermissionEntity> {

    }

    interface Service {

        PermissionEntity get(Integer id);

        List<PermissionEntity> findAll();

        Integer save(PermissionEntity model);
    }
}
