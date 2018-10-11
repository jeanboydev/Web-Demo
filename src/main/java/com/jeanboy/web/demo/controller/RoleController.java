package com.jeanboy.web.demo.controller;

import com.jeanboy.web.demo.base.BaseController;
import com.jeanboy.web.demo.config.PermissionConfig;
import com.jeanboy.web.demo.domain.entity.RoleEntity;
import com.jeanboy.web.demo.domain.entity.UserEntity;
import com.jeanboy.web.demo.domain.service.RoleService;
import com.jeanboy.web.demo.exceptions.ServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/role", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class RoleController extends BaseController {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @RequestMapping
    public String index() {
        throw new ServerException(HttpStatus.METHOD_NOT_ALLOWED);
    }


    /**
     * 添加角色信息
     * /role
     * PUT
     *
     * @param token
     * @param name
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public String put(@RequestHeader("token") String token,
                      @RequestParam("name") String name) {
        checkParam(token);
        checkParam(name);

        UserEntity onlineUser = getOnlineUser(token);
        checkPermission(onlineUser.getRoleId(), PermissionConfig.TABLE_ROLE, PermissionConfig.IDENTITY_INSERT, true);
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setName(name);
        roleEntity.setCreateTime(System.currentTimeMillis());
        roleService.save(roleEntity);
        return getResponseInfo("");
    }

    /**
     * 修改角色信息
     * /role
     * POST
     *
     * @param token
     * @param name
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    @ResponseBody
    public String post(@RequestHeader("token") String token,
                       @PathVariable("id") Integer roleId,
                       @RequestParam("name") String name) {
        checkParam(token);
        checkParam(roleId);
        checkParam(name);

        UserEntity onlineUser = getOnlineUser(token);
        checkPermission(onlineUser.getRoleId(), PermissionConfig.TABLE_ROLE, PermissionConfig.IDENTITY_UPDATE, true);
        RoleEntity roleEntity = roleService.get(roleId);
        roleEntity.setName(name);
        roleService.update(roleEntity);
        return getResponseInfo("");
    }

    /**
     * 获取角色信息
     * role/{id}
     * GET
     *
     * @param token
     * @param roleId
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String get(@RequestHeader("token") String token,
                      @PathVariable(value = "id", required = false) Integer roleId) {
        checkParam(token);

        UserEntity onlineUser = getOnlineUser(token);
        if (roleId == null || roleId == 0) {
            checkPermission(onlineUser.getRoleId(), PermissionConfig.TABLE_ROLE, PermissionConfig.IDENTITY_SELECT, true);
            List<RoleEntity> roleList = roleService.getAll();
            return getResponseInfo(roleList);
        } else {
            checkPermission(onlineUser.getRoleId(), PermissionConfig.TABLE_ROLE, PermissionConfig.IDENTITY_SELECT, false);
            RoleEntity roleEntity = roleService.get(roleId);
            return getResponseInfo(roleEntity);
        }
    }

    /**
     * 删除角色信息
     * role/{id}
     * DELETE
     *
     * @param token
     * @param roleId
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public String delete(@RequestHeader("token") String token,
                         @PathVariable("id") Integer roleId) {
        checkParam(token);
        checkParam(roleId);

        UserEntity onlineUser = getOnlineUser(token);
        checkPermission(onlineUser.getRoleId(), PermissionConfig.TABLE_ROLE, PermissionConfig.IDENTITY_DELETE, true);
        roleService.delete(roleId);
        return getResponseInfo("");
    }
}
