package com.jeanboy.web.demo.domain.repository;

import com.jeanboy.web.demo.base.BaseRepository;
import com.jeanboy.web.demo.domain.AttendanceTypeContract;
import com.jeanboy.web.demo.domain.entity.AttendanceTypeEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class AttendanceTypeRepository extends BaseRepository<Integer, AttendanceTypeEntity> implements AttendanceTypeContract.Repository{

    @Override
    protected Class<AttendanceTypeEntity> getClazz() {
        return AttendanceTypeEntity.class;
    }
}
