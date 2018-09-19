package com.jeanboy.web.demo.domain.repository;

import com.jeanboy.web.demo.base.BaseRepository;
import com.jeanboy.web.demo.domain.RoleContract;
import com.jeanboy.web.demo.domain.entity.RoleEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class RoleRepository extends BaseRepository<Integer, RoleEntity> implements RoleContract.Repository{

    @Override
    protected Class<RoleEntity> getClazz() {
        return RoleEntity.class;
    }
}
