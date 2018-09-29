package com.jeanboy.web.demo.domain.service;

import com.jeanboy.web.demo.domain.RolePermissionContract;
import com.jeanboy.web.demo.domain.entity.RolePermissionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolePermissionService implements RolePermissionContract.Service {

    private final RolePermissionContract.Repository repository;

    @Autowired
    public RolePermissionService(RolePermissionContract.Repository repository) {
        this.repository = repository;
    }

    @Override
    public RolePermissionEntity get(Long id) {
        return repository.get(id);
    }

    @Override
    public List<RolePermissionEntity> findByRoleId(Integer roleId) {
        return repository.findByRoleId(roleId);
    }

    @Override
    public List<RolePermissionEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public Long save(RolePermissionEntity model) {
        return repository.save(model);
    }

    @Override
    public void update(RolePermissionEntity model) {
        repository.update(model);
    }

    @Override
    public void delete(Long id) {
        repository.delete(id);
    }
}
