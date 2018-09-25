package com.jeanboy.web.demo.domain;

import com.jeanboy.web.demo.base.BaseContract;
import com.jeanboy.web.demo.domain.entity.UserEntity;

import java.util.List;

public interface UserContract {

    interface Repository extends BaseContract<Long, UserEntity> {

        List<UserEntity> findByUsername(String username);
    }

    interface Service {

        UserEntity get(Long id);

        List<UserEntity> findByUsername(String username);

        Long save(UserEntity model);

        void update(UserEntity model);
    }
}
