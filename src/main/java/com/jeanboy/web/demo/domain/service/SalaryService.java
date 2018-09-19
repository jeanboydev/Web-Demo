package com.jeanboy.web.demo.domain.service;

import com.jeanboy.web.demo.domain.SalaryContract;
import com.jeanboy.web.demo.domain.entity.SalaryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SalaryService implements SalaryContract.Service {

    private final SalaryContract.Repository repository;

    @Autowired
    public SalaryService(SalaryContract.Repository repository) {
        this.repository = repository;
    }

    @Override
    public SalaryEntity get(Integer id) {
        return repository.get(id);
    }

    @Override
    public Integer save(SalaryEntity model) {
        return repository.save(model);
    }

    @Override
    public void update(SalaryEntity model) {
        repository.update(model);
    }
}
