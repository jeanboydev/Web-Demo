package com.jeanboy.web.demo.domain.service;

import com.jeanboy.web.demo.domain.UserContract;
import com.jeanboy.web.demo.domain.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserContract.Service {

    private final UserContract.Repository repository;

    @Autowired
    public UserService(UserContract.Repository repository) {
        this.repository = repository;
    }

    @Override
    public UserEntity get(Long id) {
        return repository.get(id);
    }

    @Override
    public List<UserEntity> findByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public Long save(UserEntity model) {
        return repository.save(model);
    }

    @Override
    public void update(UserEntity model) {
        repository.update(model);
    }
}
