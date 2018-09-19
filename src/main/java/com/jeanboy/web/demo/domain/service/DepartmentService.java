package com.jeanboy.web.demo.domain.service;

import com.jeanboy.web.demo.domain.DepartmentContract;
import com.jeanboy.web.demo.domain.entity.DepartmentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService implements DepartmentContract.Service {

    private final DepartmentContract.Repository repository;

    @Autowired
    public DepartmentService(DepartmentContract.Repository repository) {
        this.repository = repository;
    }

    @Override
    public DepartmentEntity get(Integer id) {
        return repository.get(id);
    }

    @Override
    public Integer save(DepartmentEntity model) {
        return repository.save(model);
    }

    @Override
    public void update(DepartmentEntity model) {
        repository.update(model);
    }
}
