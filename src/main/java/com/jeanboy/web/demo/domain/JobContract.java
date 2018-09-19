package com.jeanboy.web.demo.domain;

import com.jeanboy.web.demo.base.BaseContract;
import com.jeanboy.web.demo.domain.entity.JobEntity;

public interface JobContract {

    interface Repository extends BaseContract<Integer, JobEntity> {

    }

    interface Service {

        JobEntity get(Integer id);

        Integer save(JobEntity model);

        void update(JobEntity model);
    }
}
