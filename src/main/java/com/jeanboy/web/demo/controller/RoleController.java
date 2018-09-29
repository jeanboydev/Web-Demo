package com.jeanboy.web.demo.controller;

import com.alibaba.fastjson.JSON;
import com.jeanboy.web.demo.base.BaseController;
import com.jeanboy.web.demo.config.PermissionConfig;
import com.jeanboy.web.demo.constants.ErrorCode;
import com.jeanboy.web.demo.domain.cache.MemoryCache;
import com.jeanboy.web.demo.domain.entity.RoleEntity;
import com.jeanboy.web.demo.domain.entity.UserEntity;
import com.jeanboy.web.demo.domain.service.RoleService;
import com.jeanboy.web.demo.exceptions.ServerException;
import com.jeanboy.web.demo.utils.PermissionUtil;
import com.jeanboy.web.demo.utils.StringUtil;
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
    public String addRole(@RequestHeader("token") String token,
                          @RequestParam("name") String name) {
        if (StringUtil.isEmpty(token)
                || StringUtil.isEmpty(name)) {
            throw new ServerException(ErrorCode.PARAMETER_ERROR);
        }

        UserEntity onlineUser = getOnlineUser(token);
        checkPermission(onlineUser.getRoleId(), PermissionConfig.TABLE_ROLE, PermissionConfig.IDENTITY_INSERT, true);
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setName(name);
        roleEntity.setCreateTime(System.currentTimeMillis());
        roleService.save(roleEntity);
        return "";
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
    public String deleteRole(@RequestHeader("token") String token,
                             @PathVariable(value = "id", required = false) int roleId) {
        if (StringUtil.isEmpty(token)) {
            throw new ServerException(ErrorCode.PARAMETER_ERROR);
        }

        UserEntity onlineUser = getOnlineUser(token);
        checkPermission(onlineUser.getRoleId(), PermissionConfig.TABLE_ROLE, PermissionConfig.IDENTITY_SELECT, true);
        if (roleId == 0) {
            List<RoleEntity> roleList = roleService.findAll();
            return JSON.toJSONString(roleList);
        } else {
            RoleEntity roleEntity = roleService.get(roleId);
            return JSON.toJSONString(roleEntity);
        }
    }
}
