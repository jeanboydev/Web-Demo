package com.jeanboy.web.demo.domain.service;

import com.jeanboy.web.demo.domain.UserContract;
import com.jeanboy.web.demo.domain.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserContract.Service{

    private final UserContract.Repository repository;

    @Autowired
    public UserService(UserContract.Repository repository) {
        this.repository = repository;
    }

    @Override
    public UserModel get(Long id) {
        return repository.get(id);
    }

    @Override
    public Long save(UserModel model) {
        return repository.save(model);
    }

    @Override
    public void update(UserModel model) {
        repository.update(model);
    }
}
