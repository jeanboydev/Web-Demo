package com.jeanboy.web.demo.domain;

import com.jeanboy.web.demo.base.BaseContract;
import com.jeanboy.web.demo.domain.entity.AttendanceTypeEntity;

import java.util.List;

public interface AttendanceTypeContract {

    interface Repository extends BaseContract<Integer, AttendanceTypeEntity> {

    }

    interface Service {

        AttendanceTypeEntity get(Integer id);

        List<AttendanceTypeEntity> getAll();

        Integer save(AttendanceTypeEntity model);

        void update(AttendanceTypeEntity model);

        void delete(Integer id);
    }
}
