package com.jeanboy.web.demo.domain;

import com.jeanboy.web.demo.base.BaseContract;
import com.jeanboy.web.demo.domain.entity.JobEntity;

import java.util.List;

public interface JobContract {

    interface Repository extends BaseContract<Integer, JobEntity> {

    }

    interface Service {

        JobEntity get(Integer id);

        List<JobEntity> findAll();

        Integer save(JobEntity model);

        void update(JobEntity model);

        void delete(Integer id);
    }
}
