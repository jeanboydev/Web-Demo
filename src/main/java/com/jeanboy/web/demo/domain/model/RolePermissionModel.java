package com.jeanboy.web.demo.domain.model;

public class RolePermissionModel {

    private long id;
    private int roleId;
    private String roleName;
    private int permissionIdentity;
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

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
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
