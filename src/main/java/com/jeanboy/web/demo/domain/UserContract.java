package com.jeanboy.web.demo.domain;

import com.jeanboy.web.demo.base.BaseContract;
import com.jeanboy.web.demo.domain.entity.UserEntity;

public interface UserContract {

    interface Repository extends BaseContract<Long, UserEntity> {

        UserEntity findByUsername(String username);
    }

    interface Service {

        UserEntity get(Long id);

        UserEntity findByUsername(String username);

        Long save(UserEntity model);

        void update(UserEntity model);
    }
}
