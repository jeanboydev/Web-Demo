package com.jeanboy.web.demo.domain.service;

import com.jeanboy.web.demo.domain.PermissionContract;
import com.jeanboy.web.demo.domain.entity.PermissionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionService implements PermissionContract.Service {

    private final PermissionContract.Repository repository;

    @Autowired
    public PermissionService(PermissionContract.Repository repository) {
        this.repository = repository;
    }

    @Override
    public PermissionEntity get(Integer id) {
        return repository.get(id);
    }

    @Override
    public List<PermissionEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public Integer save(PermissionEntity model) {
        return repository.save(model);
    }
}
