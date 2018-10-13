package com.jeanboy.web.demo.domain;

import com.jeanboy.web.demo.base.BaseContract;
import com.jeanboy.web.demo.domain.entity.AttendanceEntity;
import com.jeanboy.web.demo.domain.entity.AttendanceTypeEntity;

import java.util.List;

public interface AttendanceContract {

    interface Repository extends BaseContract<Long, AttendanceEntity> {

        List<AttendanceEntity> findByUserId(Long userId);
    }

    interface Service {

        AttendanceEntity get(Long id);

        List<AttendanceEntity> getAll();

        Long save(AttendanceEntity model);

        void update(AttendanceEntity model);

        void delete(Long id);

        List<AttendanceEntity> findByUserId(Long userId);
    }
}
