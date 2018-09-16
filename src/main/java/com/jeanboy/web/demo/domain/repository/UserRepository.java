package com.jeanboy.web.demo.domain.repository;

import com.jeanboy.web.demo.base.BaseRepository;
import com.jeanboy.web.demo.domain.UserContract;
import com.jeanboy.web.demo.domain.model.UserModel;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class UserRepository extends BaseRepository<Long, UserModel> implements UserContract.Repository{

    @Override
    protected Class<UserModel> getClazz() {
        return UserModel.class;
    }
}
