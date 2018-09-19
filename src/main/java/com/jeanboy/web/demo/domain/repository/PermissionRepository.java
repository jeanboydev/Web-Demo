package com.jeanboy.web.demo.domain.repository;

import com.jeanboy.web.demo.base.BaseRepository;
import com.jeanboy.web.demo.domain.PermissionContract;
import com.jeanboy.web.demo.domain.entity.PermissionEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class PermissionRepository extends BaseRepository<Integer, PermissionEntity> implements PermissionContract.Repository{

    @Override
    protected Class<PermissionEntity> getClazz() {
        return PermissionEntity.class;
    }
}
