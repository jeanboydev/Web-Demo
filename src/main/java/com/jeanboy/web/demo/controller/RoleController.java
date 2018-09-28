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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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


    @RequestMapping(value = "/getRoles", method = RequestMethod.POST)
    @ResponseBody
    public String getRoles(@RequestParam("token") String token) {
        if (StringUtil.isEmpty(token)) {
            throw new ServerException(ErrorCode.PARAMETER_ERROR);
        }
        UserEntity userEntity = MemoryCache.getTokenMap().get(token);
        if (userEntity == null) {
            throw new ServerException(ErrorCode.TOKEN_INVALID);
        }
        int roleId = userEntity.getRoleId();
        boolean hadPermission = PermissionUtil.check(roleId, PermissionConfig.TABLE_ROLE,
                PermissionConfig.IDENTITY_SELECT);
        if (!hadPermission) {
            throw new ServerException(ErrorCode.PERMISSION_DENIED);
        }
        List<RoleEntity> roleList = roleService.findAll();
        return JSON.toJSONString(roleList);
    }

    @RequestMapping(value = "/deleteRole", method = RequestMethod.POST)
    @ResponseBody
    public String deleteRole(@RequestParam("token") String token,
                             @RequestParam("role_id") int roleId) {
        if (StringUtil.isEmpty(token) || roleId == 0) {
            throw new ServerException(ErrorCode.PARAMETER_ERROR);
        }
        UserEntity userEntity = MemoryCache.getTokenMap().get(token);
        if (userEntity == null) {
            throw new ServerException(ErrorCode.TOKEN_INVALID);
        }
        int onlineRoleId = userEntity.getRoleId();
        boolean hadPermission = PermissionUtil.check(onlineRoleId, PermissionConfig.TABLE_ROLE,
                PermissionConfig.IDENTITY_DELETE);
        if (!hadPermission) {
            throw new ServerException(ErrorCode.PERMISSION_DENIED);
        }
        roleService.delete(roleId);
        return "";
    }
}
