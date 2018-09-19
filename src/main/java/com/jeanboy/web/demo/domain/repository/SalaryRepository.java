package com.jeanboy.web.demo.domain.repository;

import com.jeanboy.web.demo.base.BaseRepository;
import com.jeanboy.web.demo.domain.SalaryContract;
import com.jeanboy.web.demo.domain.entity.SalaryEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class SalaryRepository extends BaseRepository<Integer, SalaryEntity> implements SalaryContract.Repository{

    @Override
    protected Class<SalaryEntity> getClazz() {
        return SalaryEntity.class;
    }
}
