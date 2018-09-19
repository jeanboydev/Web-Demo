package com.jeanboy.web.demo.domain.service;

import com.jeanboy.web.demo.domain.RoleContract;
import com.jeanboy.web.demo.domain.entity.RoleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService implements RoleContract.Service {

    private final RoleContract.Repository repository;

    @Autowired
    public RoleService(RoleContract.Repository repository) {
        this.repository = repository;
    }

    @Override
    public RoleEntity get(Integer id) {
        return repository.get(id);
    }

    @Override
    public Integer save(RoleEntity model) {
        return repository.save(model);
    }

    @Override
    public void update(RoleEntity model) {
        repository.update(model);
    }
}
