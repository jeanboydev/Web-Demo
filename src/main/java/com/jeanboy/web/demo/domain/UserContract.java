package com.jeanboy.web.demo.domain;

import com.jeanboy.web.demo.base.BaseContract;
import com.jeanboy.web.demo.domain.entity.UserEntity;

public interface UserContract {

    interface Repository extends BaseContract<Long, UserEntity> {

    }

    interface Service {

        UserEntity get(Long id);

        Long save(UserEntity model);

        void update(UserEntity model);
    }
}
