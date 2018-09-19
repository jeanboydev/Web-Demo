package com.jeanboy.web.demo.domain.service;

import com.jeanboy.web.demo.domain.AttendanceContract;
import com.jeanboy.web.demo.domain.entity.AttendanceEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttendanceService implements AttendanceContract.Service {

    private final AttendanceContract.Repository repository;

    @Autowired
    public AttendanceService(AttendanceContract.Repository repository) {
        this.repository = repository;
    }

    @Override
    public AttendanceEntity get(Long id) {
        return repository.get(id);
    }

    @Override
    public Long save(AttendanceEntity model) {
        return repository.save(model);
    }

    @Override
    public void update(AttendanceEntity model) {
        repository.update(model);
    }
}
