package com.jeanboy.web.demo.domain.repository;

import com.jeanboy.web.demo.base.BaseRepository;
import com.jeanboy.web.demo.domain.RolePermissionContract;
import com.jeanboy.web.demo.domain.entity.RolePermissionEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public class RolePermissionRepository extends BaseRepository<Long, RolePermissionEntity> implements RolePermissionContract.Repository {

    @Override
    protected Class<RolePermissionEntity> getClazz() {
        return RolePermissionEntity.class;
    }

    @Override
    public List<RolePermissionEntity> findByRoleId(Integer roleId) {
        return findByWhere("roleId", roleId);
    }
}
