package com.jeanboy.web.demo.domain.service;

import com.jeanboy.web.demo.domain.AttendanceTypeContract;
import com.jeanboy.web.demo.domain.entity.AttendanceTypeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttendanceTypeService implements AttendanceTypeContract.Service {

    private final AttendanceTypeContract.Repository repository;

    @Autowired
    public AttendanceTypeService(AttendanceTypeContract.Repository repository) {
        this.repository = repository;
    }

    @Override
    public AttendanceTypeEntity get(Integer id) {
        return repository.get(id);
    }

    @Override
    public List<AttendanceTypeEntity> getAll() {
        return repository.getAll();
    }

    @Override
    public Integer save(AttendanceTypeEntity model) {
        return repository.save(model);
    }

    @Override
    public void update(AttendanceTypeEntity model) {
        repository.update(model);
    }

    @Override
    public void delete(Integer id) {
        repository.delete(id);
    }
}
