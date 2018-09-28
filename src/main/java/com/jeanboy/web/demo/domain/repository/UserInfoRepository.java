package com.jeanboy.web.demo.domain.repository;

import com.jeanboy.web.demo.base.BaseRepository;
import com.jeanboy.web.demo.domain.UserInfoContract;
import com.jeanboy.web.demo.domain.entity.UserInfoEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public class UserInfoRepository extends BaseRepository<Long, UserInfoEntity> implements UserInfoContract.Repository {

    @Override
    protected Class<UserInfoEntity> getClazz() {
        return UserInfoEntity.class;
    }

    @Override
    public List<UserInfoEntity> findByUserId(long userId) {
        return findByWhere("userId",userId);
    }
}
