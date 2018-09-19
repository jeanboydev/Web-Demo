package com.jeanboy.web.demo.domain.repository;

import com.jeanboy.web.demo.base.BaseRepository;
import com.jeanboy.web.demo.domain.JobContract;
import com.jeanboy.web.demo.domain.entity.JobEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class JobRepository extends BaseRepository<Integer, JobEntity> implements JobContract.Repository{

    @Override
    protected Class<JobEntity> getClazz() {
        return JobEntity.class;
    }
}
