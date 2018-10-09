package com.jeanboy.web.demo.domain.service;

import com.jeanboy.web.demo.domain.UserInfoContract;
import com.jeanboy.web.demo.domain.entity.UserInfoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserInfoService implements UserInfoContract.Service {

    private final UserInfoContract.Repository repository;

    @Autowired
    public UserInfoService(UserInfoContract.Repository repository) {
        this.repository = repository;
    }

    @Override
    public UserInfoEntity get(Long id) {
        return repository.get(id);
    }

    @Override
    public List<UserInfoEntity> getAll() {
        return repository.getAll();
    }

    @Override
    public List<UserInfoEntity> findByUserId(long userId) {
        return repository.findByUserId(userId);
    }

    @Override
    public Long save(UserInfoEntity model) {
        return repository.save(model);
    }

    @Override
    public void update(UserInfoEntity model) {
        repository.update(model);
    }

    @Override
    public void delete(Long id) {
        repository.delete(id);
    }
}
