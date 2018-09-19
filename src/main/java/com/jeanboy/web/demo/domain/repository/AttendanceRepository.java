package com.jeanboy.web.demo.domain.repository;

import com.jeanboy.web.demo.base.BaseRepository;
import com.jeanboy.web.demo.domain.AttendanceContract;
import com.jeanboy.web.demo.domain.entity.AttendanceEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class AttendanceRepository extends BaseRepository<Long, AttendanceEntity> implements AttendanceContract.Repository{

    @Override
    protected Class<AttendanceEntity> getClazz() {
        return AttendanceEntity.class;
    }
}
