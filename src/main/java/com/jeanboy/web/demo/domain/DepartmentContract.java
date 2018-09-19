package com.jeanboy.web.demo.domain;

import com.jeanboy.web.demo.base.BaseContract;
import com.jeanboy.web.demo.domain.entity.AttendanceTypeEntity;
import com.jeanboy.web.demo.domain.entity.DepartmentEntity;

public interface DepartmentContract {

    interface Repository extends BaseContract<Integer, DepartmentEntity> {

    }

    interface Service {

        DepartmentEntity get(Integer id);

        Integer save(DepartmentEntity model);

        void update(DepartmentEntity model);
    }
}
