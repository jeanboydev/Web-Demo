package com.jeanboy.web.demo.domain;

import com.jeanboy.web.demo.base.BaseContract;
import com.jeanboy.web.demo.domain.entity.UserEntity;
import com.jeanboy.web.demo.domain.entity.UserInfoEntity;

import java.util.List;

public interface UserInfoContract {

    interface Repository extends BaseContract<Long, UserInfoEntity> {

        List<UserInfoEntity> findByUserId(long userId);
    }

    interface Service {

        UserInfoEntity get(Long id);

        List<UserInfoEntity> findByUserId(long userId);

        Long save(UserInfoEntity model);

        void update(UserInfoEntity model);

        void delete(Long id);
    }
}
