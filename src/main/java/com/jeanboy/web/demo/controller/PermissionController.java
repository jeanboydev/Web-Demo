package com.jeanboy.web.demo.controller;

import com.alibaba.fastjson.JSON;
import com.jeanboy.web.demo.base.BaseController;
import com.jeanboy.web.demo.config.PermissionConfig;
import com.jeanboy.web.demo.constants.ErrorCode;
import com.jeanboy.web.demo.domain.entity.RolePermissionEntity;
import com.jeanboy.web.demo.domain.entity.UserEntity;
import com.jeanboy.web.demo.domain.service.PermissionService;
import com.jeanboy.web.demo.domain.service.RolePermissionService;
import com.jeanboy.web.demo.exceptions.ServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/permission", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class PermissionController extends BaseController {

    private final PermissionService permissionService;
    private final RolePermissionService rolePermissionService;

    @Autowired
    public PermissionController(PermissionService permissionService, RolePermissionService rolePermissionService) {
        this.permissionService = permissionService;
        this.rolePermissionService = rolePermissionService;
    }

    @RequestMapping
    public String index() {
        throw new ServerException(HttpStatus.METHOD_NOT_ALLOWED);
    }


    /**
     * 增加角色权限信息
     * /permission/relation
     * PUT
     *
     * @param token
     * @param roleId
     * @param permissionIdentity
     * @return
     */
    @RequestMapping(value = "/relation", method = RequestMethod.PUT)
    @ResponseBody
    public String put(@RequestHeader("token") String token,
                      @RequestParam("role_id") Integer roleId,
                      @RequestParam("permission_identity") Integer permissionIdentity) {
        checkParam(token);
        checkParam(roleId);
        checkParam(permissionIdentity);

        UserEntity onlineUser = getOnlineUser(token);
        checkPermission(onlineUser.getRoleId(), PermissionConfig.TABLE_ROLE_PERMISSION, PermissionConfig.IDENTITY_INSERT, true);
        RolePermissionEntity rolePermissionEntity = new RolePermissionEntity();
        rolePermissionEntity.setRoleId(roleId);
        rolePermissionEntity.setPermissionIdentity(permissionIdentity);
        rolePermissionEntity.setCreateTime(System.currentTimeMillis());
        rolePermissionService.save(rolePermissionEntity);
        return "";
    }

    /**
     * 更新角色权限信息
     * /permission/relation/{id}
     * POST
     *
     * @param token
     * @param id
     * @param permissionIdentity
     * @return
     */
    @RequestMapping(value = "/relation/{id}", method = RequestMethod.POST)
    @ResponseBody
    public String post(@RequestHeader("token") String token,
                       @PathVariable("id") Long id,
                       @RequestParam("permission_identity") Integer permissionIdentity) {
        checkParam(token);
        checkParam(id);
        checkParam(permissionIdentity);

        UserEntity onlineUser = getOnlineUser(token);
        checkPermission(onlineUser.getRoleId(), PermissionConfig.TABLE_ROLE_PERMISSION, PermissionConfig.IDENTITY_UPDATE, true);
        RolePermissionEntity rolePermissionEntity = rolePermissionService.get(id);
        if (rolePermissionEntity == null) {
            throw new ServerException(ErrorCode.DATA_NOT_FOUND);
        }
        rolePermissionEntity.setPermissionIdentity(permissionIdentity);
        rolePermissionEntity.setCreateTime(System.currentTimeMillis());
        rolePermissionService.update(rolePermissionEntity);
        return "";
    }

    /**
     * 获取角色权限信息
     * /permission/relation/{id}
     * GET
     *
     * @param token
     * @param id
     * @return
     */
    @RequestMapping(value = "/relation/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String get(@RequestHeader("token") String token,
                      @PathVariable(value = "id", required = false) Long id) {
        checkParam(token);

        UserEntity onlineUser = getOnlineUser(token);
        checkPermission(onlineUser.getRoleId(), PermissionConfig.TABLE_ROLE_PERMISSION, PermissionConfig.IDENTITY_SELECT, true);
        if (id == null || id == 0) {
            List<RolePermissionEntity> rolePermissionList = rolePermissionService.getAll();
            return JSON.toJSONString(rolePermissionList);
        } else {
            RolePermissionEntity rolePermissionEntity = rolePermissionService.get(id);
            if (rolePermissionEntity == null) {
                throw new ServerException(ErrorCode.DATA_NOT_FOUND);
            }
            return JSON.toJSONString(rolePermissionEntity);
        }
    }

    /**
     * 删除角色权限信息
     * /permission/relation/{id}
     * DELETE
     *
     * @param token
     * @param id
     * @return
     */
    @RequestMapping(value = "/relation/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public String delete(@RequestHeader("token") String token,
                         @PathVariable("id") Long id) {
        checkParam(token);

        UserEntity onlineUser = getOnlineUser(token);
        checkPermission(onlineUser.getRoleId(), PermissionConfig.TABLE_ROLE_PERMISSION, PermissionConfig.IDENTITY_DELETE, true);
        rolePermissionService.delete(id);
        return "";
    }
}
