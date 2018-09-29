package com.jeanboy.web.demo.domain.service;

import com.jeanboy.web.demo.domain.DepartmentContract;
import com.jeanboy.web.demo.domain.JobContract;
import com.jeanboy.web.demo.domain.entity.DepartmentEntity;
import com.jeanboy.web.demo.domain.entity.JobEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobService implements JobContract.Service {

    private final JobContract.Repository repository;

    @Autowired
    public JobService(JobContract.Repository repository) {
        this.repository = repository;
    }

    @Override
    public JobEntity get(Integer id) {
        return repository.get(id);
    }

    @Override
    public List<JobEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public Integer save(JobEntity model) {
        return repository.save(model);
    }

    @Override
    public void update(JobEntity model) {
        repository.update(model);
    }

    @Override
    public void delete(Integer id) {
        repository.delete(id);
    }
}
