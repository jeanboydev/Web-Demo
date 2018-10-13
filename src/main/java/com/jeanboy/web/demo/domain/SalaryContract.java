package com.jeanboy.web.demo.domain;

import com.jeanboy.web.demo.base.BaseContract;
import com.jeanboy.web.demo.domain.entity.SalaryEntity;

import java.util.List;

public interface SalaryContract {

    interface Repository extends BaseContract<Integer, SalaryEntity> {

        List<SalaryEntity> findByJobId(Integer jobId);
    }

    interface Service {

        SalaryEntity get(Integer id);

        List<SalaryEntity> getAll();

        Integer save(SalaryEntity model);

        void update(SalaryEntity model);

        void delete(Integer id);

        List<SalaryEntity> findByJobId(Integer jobId);
    }
}
