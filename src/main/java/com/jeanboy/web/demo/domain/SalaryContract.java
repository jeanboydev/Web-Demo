package com.jeanboy.web.demo.domain;

import com.jeanboy.web.demo.base.BaseContract;
import com.jeanboy.web.demo.domain.entity.SalaryEntity;

import java.util.List;

public interface SalaryContract {

    interface Repository extends BaseContract<Integer, SalaryEntity> {

    }

    interface Service {

        SalaryEntity get(Integer id);

        List<SalaryEntity> findAll();

        Integer save(SalaryEntity model);

        void update(SalaryEntity model);

        void delete(Integer id);
    }
}
