package com.jeanboy.web.demo.domain;

import com.jeanboy.web.demo.base.BaseContract;
import com.jeanboy.web.demo.domain.entity.AttendanceTypeEntity;

public interface AttendanceTypeContract {

    interface Repository extends BaseContract<Integer, AttendanceTypeEntity> {

    }

    interface Service {

        AttendanceTypeEntity get(Integer id);

        Integer save(AttendanceTypeEntity model);

        void update(AttendanceTypeEntity model);
    }
}
