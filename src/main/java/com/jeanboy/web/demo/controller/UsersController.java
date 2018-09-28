package com.jeanboy.web.demo.controller;

import com.alibaba.fastjson.JSON;
import com.jeanboy.web.demo.base.BaseController;
import com.jeanboy.web.demo.config.PermissionConfig;
import com.jeanboy.web.demo.constants.ErrorCode;
import com.jeanboy.web.demo.domain.cache.MemoryCache;
import com.jeanboy.web.demo.domain.entity.UserEntity;
import com.jeanboy.web.demo.domain.model.TokenModel;
import com.jeanboy.web.demo.domain.service.UserService;
import com.jeanboy.web.demo.exceptions.ServerException;
import com.jeanboy.web.demo.utils.PermissionUtil;
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

import java.util.List;

@Controller
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UsersController extends BaseController {

    private final UserService userService;


    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
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
        MemoryCache.getTokenMap().put(token, userEntity);
        TokenModel tokenModel = new TokenModel(token);
        return JSON.toJSONString(tokenModel);
    }

    /**
     * 注册
     * /users/signUp
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
    @RequestMapping(value = "/getInfo", method = RequestMethod.POST)
    @ResponseBody
    public String getUserInfo(@RequestParam("token") String token,
                              @RequestParam("id") long userId) {
        if (StringUtil.isEmpty(token) || userId == 0) {
            throw new ServerException(ErrorCode.PARAMETER_ERROR);
        }
        UserEntity userEntity = MemoryCache.getTokenMap().get(token);
        if (userEntity == null) {
            throw new ServerException(ErrorCode.TOKEN_INVALID);
        }
        if (userEntity.getId() == userId) {
            userEntity.setPassword("");
            return JSON.toJSONString(userEntity);
        } else {
            int roleId = userEntity.getRoleId();
            boolean hadPermission = PermissionUtil.check(roleId, PermissionConfig.TABLE_ROLE,
                    PermissionConfig.IDENTITY_SELECT);
            if (!hadPermission) {
                throw new ServerException(ErrorCode.PERMISSION_DENIED);
            }
            UserEntity findUser = userService.get(userId);
            if (findUser == null) {
                throw new ServerException(ErrorCode.ACCOUNT_NOT_FOUND);
            }
            findUser.setPassword("");
            return JSON.toJSONString(findUser);
        }
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public String updateInfo(@RequestParam("token") String token,
                             @RequestParam("id") long userId,
                             @RequestParam(value = "password", required = false) String password,
                             @RequestParam(value = "real_name", required = false) String realName,
                             @RequestParam(value = "gender", required = false) int gender,
                             @RequestParam(value = "birthday", required = false) long birthday,
                             @RequestParam(value = "education_level", required = false) int educationLevel,
                             @RequestParam(value = "import_time", required = false) long importTime,
                             @RequestParam(value = "job_id", required = false) int jobId,
                             @RequestParam(value = "department_id", required = false) int departmentId,
                             @RequestParam(value = "role_id", required = false) int roleId) {

        if (StringUtil.isEmpty(token) || userId == 0) {
            throw new ServerException(ErrorCode.PARAMETER_ERROR);
        }
        UserEntity updateUser = null;
        UserEntity userEntity = MemoryCache.getTokenMap().get(token);
        if (userEntity == null) {
            throw new ServerException(ErrorCode.TOKEN_INVALID);
        }
        if (userEntity.getId() == userId) {
            updateUser = userEntity;
        } else {
            int onlineRoleId = userEntity.getRoleId();
            boolean hadPermission = PermissionUtil.check(onlineRoleId, PermissionConfig.TABLE_ROLE,
                    PermissionConfig.IDENTITY_SELECT);
            if (!hadPermission) {
                throw new ServerException(ErrorCode.PERMISSION_DENIED);
            }
            UserEntity findUser = userService.get(userId);
            if (findUser == null) {
                throw new ServerException(ErrorCode.ACCOUNT_NOT_FOUND);
            }
            updateUser = findUser;
        }

        if (!StringUtil.isEmpty(password)) {
            updateUser.setPassword(password);
        }
        if (!StringUtil.isEmpty(realName)) {
            updateUser.setRealName(realName);
        }
        if (gender != 0) {
            updateUser.setGender(gender);
        }
        if (birthday != 0) {
            updateUser.setBirthday(birthday);
        }
        if (educationLevel != 0) {
            updateUser.setEducationLevel(educationLevel);
        }
        if (importTime != 0) {
            updateUser.setImportTime(importTime);
        }
        if (jobId != 0) {
            updateUser.setJobId(jobId);
        }
        if (departmentId != 0) {
            updateUser.setDepartmentId(departmentId);
        }
        if (roleId != 0) {
            updateUser.setRoleId(roleId);
        }
        updateUser.setUpdateTime(System.currentTimeMillis());
        userService.update(updateUser);
        return "";
    }
}
