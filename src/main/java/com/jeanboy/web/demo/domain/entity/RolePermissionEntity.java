package com.jeanboy.web.demo.domain.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "role_permission")
public class RolePermissionEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @Column(name = "role_id", nullable = false)
    private int roleId;
    @Column(name = "permission_identity", nullable = false)
    private int permissionIdentity;
    @Column(name = "create_time", length = 13, nullable = false)
    private long createTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getPermissionIdentity() {
        return permissionIdentity;
    }

    public void setPermissionIdentity(int permissionIdentity) {
        this.permissionIdentity = permissionIdentity;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}
