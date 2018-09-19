package com.jeanboy.web.demo.domain.repository;

import com.jeanboy.web.demo.base.BaseRepository;
import com.jeanboy.web.demo.domain.DepartmentContract;
import com.jeanboy.web.demo.domain.entity.DepartmentEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class DepartmentRepository extends BaseRepository<Integer, DepartmentEntity> implements DepartmentContract.Repository{

    @Override
    protected Class<DepartmentEntity> getClazz() {
        return DepartmentEntity.class;
    }
}
