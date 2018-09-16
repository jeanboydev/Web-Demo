package com.jeanboy.web.demo.domain;

import com.jeanboy.web.demo.base.BaseContract;
import com.jeanboy.web.demo.domain.model.UserModel;

public interface UserContract {

    interface Repository extends BaseContract<Long, UserModel> {

    }

    interface Service {

        UserModel get(Long id);

        Long save(UserModel model);

        void update(UserModel model);
    }
}
