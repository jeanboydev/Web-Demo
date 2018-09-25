package com.jeanboy.web.demo.controller;

import com.alibaba.fastjson.JSON;
import com.jeanboy.web.demo.base.BaseController;
import com.jeanboy.web.demo.config.PermissionConfig;
import com.jeanboy.web.demo.constants.ErrorCode;
import com.jeanboy.web.demo.domain.PermissionContract;
import com.jeanboy.web.demo.domain.entity.RoleEntity;
import com.jeanboy.web.demo.domain.entity.UserEntity;
import com.jeanboy.web.demo.domain.model.TokenModel;
import com.jeanboy.web.demo.domain.service.RoleService;
import com.jeanboy.web.demo.domain.service.UserService;
import com.jeanboy.web.demo.exceptions.ServerException;
import com.jeanboy.web.demo.utils.StringUtil;
import com.jeanboy.web.demo.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UsersController extends BaseController {

    private final UserService userService;
    private final RoleService roleService;

    private static Map<String, UserEntity> tokenMap = new HashMap<>();

    @Autowired
    public UsersController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @RequestMapping
    public String index() {
        throw new ServerException(HttpStatus.METHOD_NOT_ALLOWED);
    }

    /**
     * 登录
     * /users/signIn
     * POST
     *
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(value = "/signIn", method = RequestMethod.POST)
    @ResponseBody
    public String signIn(@RequestParam("username") String username,
                         @RequestParam("password") String password) {
        if (StringUtil.isEmpty(username) || StringUtil.isEmpty(password)) {
            throw new ServerException(ErrorCode.PARAMETER_ERROR);
        }

        List<UserEntity> userList = userService.findByUsername(username);
        if (userList.isEmpty()) {
            throw new ServerException(ErrorCode.ACCOUNT_NOT_FOUND);
        }
        UserEntity userEntity = userList.get(0);
        String cachePassword = userEntity.getPassword();
        if (!password.toUpperCase().equals(cachePassword)) {
            throw new ServerException(ErrorCode.PASSWORD_ERROR);
        }
        String token = TokenUtil.getToken(userEntity.getId());
        tokenMap.put(token, userEntity);
        TokenModel tokenModel = new TokenModel(token);
        return JSON.toJSONString(tokenModel);
    }

    /**
     * 注册
     * /users/signIn
     * POST
     *
     * @param username
     * @param realName
     * @param password
     * @param roleId
     * @return
     */
    @RequestMapping(value = "/signUp", method = RequestMethod.POST)
    @ResponseBody
    public String signUp(@RequestParam("username") String username,
                         @RequestParam("real_name") String realName,
                         @RequestParam("password") String password,
                         @RequestParam("role_id") int roleId) {
        if (StringUtil.isEmpty(username)
                || StringUtil.isEmpty(realName)
                || StringUtil.isEmpty(password)
                || roleId == 0) {
            throw new ServerException(ErrorCode.PARAMETER_ERROR);
        }

        List<UserEntity> userList = userService.findByUsername(username);
        if (!userList.isEmpty()) {
            throw new ServerException(ErrorCode.ACCOUNT_ALREADY_EXISTS);
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);
        userEntity.setRealName(realName);
        userEntity.setPassword(password);
        userEntity.setUpdateTime(System.currentTimeMillis());
        userEntity.setCreateTime(System.currentTimeMillis());
        userEntity.setRoleId(roleId);
        userService.save(userEntity);
        return "";
    }

    /**
     * 获取用户信息
     * /users
     * POST
     *
     * @param token
     * @param userId
     * @return
     */
    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseBody
    public String getUserInfo(@RequestParam("token") String token,
                              @RequestParam("id") long userId) {
        if (StringUtil.isEmpty(token) || userId == 0) {
            throw new ServerException(ErrorCode.PARAMETER_ERROR);
        }
        UserEntity userEntity = tokenMap.get(token);
        if (userEntity == null) {
            throw new ServerException(ErrorCode.PARAMETER_ERROR);
        }
        if (userEntity.getId() == userId) {
            return JSON.toJSONString(userEntity);
        } else {
            int roleId = userEntity.getRoleId();
            RoleEntity roleEntity = roleService.get(roleId);
            if (roleEntity == null) {
                throw new ServerException(HttpStatus.INTERNAL_SERVER_ERROR);
            }
//            int identity = roleEntity.getPermissionIdentity();
//            int tableIdentity = identity & PermissionConfig.TABLE_USER;
//            if (tableIdentity == PermissionConfig.TABLE_USER) ;
        }
        return "";
    }

//    @RequestMapping(method = RequestMethod.GET)
//    @ResponseBody
//    public String test() {
//        UserEntity userModel = new UserEntity();
//        userModel.setUsername("test");
//        userModel.setPassword("123");
////        userModel.setNickname("测试昵称");
//        userModel.setUpdateTime(System.currentTimeMillis());
//        userService.save(userModel);
//        return "hello world";
//    }
//
//    @RequestMapping(value = "get", method = RequestMethod.GET)
//    @ResponseBody
//    public String test2() {
//        UserEntity userModel = userService.get(1L);
//        return JSON.toJSONString(userModel);
//    }
}
