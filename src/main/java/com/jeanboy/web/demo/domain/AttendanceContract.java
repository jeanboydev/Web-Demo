package com.jeanboy.web.demo.domain;

import com.jeanboy.web.demo.base.BaseContract;
import com.jeanboy.web.demo.domain.entity.AttendanceEntity;

public interface AttendanceContract {

    interface Repository extends BaseContract<Long, AttendanceEntity> {

    }

    interface Service {

        AttendanceEntity get(Long id);

        Long save(AttendanceEntity model);

        void update(AttendanceEntity model);
    }
}
