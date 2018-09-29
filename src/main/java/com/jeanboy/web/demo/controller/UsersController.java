package com.jeanboy.web.demo.controller;

import com.alibaba.fastjson.JSON;
import com.jeanboy.web.demo.base.BaseController;
import com.jeanboy.web.demo.config.PermissionConfig;
import com.jeanboy.web.demo.constants.ErrorCode;
import com.jeanboy.web.demo.domain.cache.MemoryCache;
import com.jeanboy.web.demo.domain.entity.UserEntity;
import com.jeanboy.web.demo.domain.entity.UserInfoEntity;
import com.jeanboy.web.demo.domain.model.TokenModel;
import com.jeanboy.web.demo.domain.service.UserInfoService;
import com.jeanboy.web.demo.domain.service.UserService;
import com.jeanboy.web.demo.exceptions.ServerException;
import com.jeanboy.web.demo.utils.StringUtil;
import com.jeanboy.web.demo.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UsersController extends BaseController {

    private final UserService userService;
    private final UserInfoService userInfoService;


    @Autowired
    public UsersController(UserService userService, UserInfoService userInfoService) {
        this.userService = userService;
        this.userInfoService = userInfoService;
    }

    @RequestMapping
    public String index() {
        throw new ServerException(HttpStatus.METHOD_NOT_ALLOWED);
    }

    /**
     * 注册
     * /users
     * PUT
     *
     * @param token
     * @param username
     * @param password
     * @param roleId
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public String signUp(@RequestHeader("token") String token,
                         @RequestParam("username") String username,
                         @RequestParam("password") String password,
                         @RequestParam("role_id") int roleId) {
        if (StringUtil.isEmpty(token)
                || StringUtil.isEmpty(username)
                || StringUtil.isEmpty(password)
                || roleId == 0) {
            throw new ServerException(ErrorCode.PARAMETER_ERROR);
        }

        UserEntity onlineUser = getOnlineUser(token);
        checkPermission(onlineUser.getRoleId(), PermissionConfig.TABLE_USER, PermissionConfig.IDENTITY_INSERT, true);
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);
        userEntity.setPassword(password);
        userEntity.setCreateTime(System.currentTimeMillis());
        userEntity.setRoleId(roleId);
        userService.save(userEntity);
        return "";
    }

    /**
     * 登录
     * /users
     * POST
     *
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
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
     * 更新用户注册信息
     * /users
     * POST
     *
     * @param token
     * @param userId
     * @param password
     * @param roleId
     * @return
     */

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    @ResponseBody
    public String updateUser(@RequestHeader("token") String token,
                             @PathVariable("id") long userId,
                             @RequestParam(value = "password", required = false) String password,
                             @RequestParam(value = "role_id", required = false) int roleId) {
        if (StringUtil.isEmpty(token)
                || (StringUtil.isEmpty(password) && roleId == 0)) {
            throw new ServerException(ErrorCode.PARAMETER_ERROR);
        }

        UserEntity onlineUser = getOnlineUser(token);
        UserEntity resultUser;
        if (onlineUser.getId() == userId) {
            resultUser = onlineUser;
        } else {
            checkPermission(onlineUser.getRoleId(), PermissionConfig.TABLE_USER, PermissionConfig.IDENTITY_UPDATE, true);
            resultUser = userService.get(userId);
        }
        if (resultUser == null) {
            throw new ServerException(ErrorCode.ACCOUNT_NOT_FOUND);
        }
        if (!StringUtil.isEmpty(password)) {
            resultUser.setPassword(password);
        }
        if (roleId != 0) {
            resultUser.setRoleId(roleId);
        }
        userService.update(resultUser);
        return "";
    }

    /**
     * 获取用户注册信息
     * /users
     * POST
     *
     * @param token
     * @param userId
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String getUser(@RequestHeader("token") String token,
                          @PathVariable("id") long userId) {
        if (StringUtil.isEmpty(token) || userId == 0) {
            throw new ServerException(ErrorCode.PARAMETER_ERROR);
        }

        UserEntity onlineUser = getOnlineUser(token);
        UserEntity resultUser;
        if (onlineUser.getId() == userId) {
            resultUser = onlineUser;
        } else {
            checkPermission(onlineUser.getRoleId(), PermissionConfig.TABLE_USER, PermissionConfig.IDENTITY_SELECT, true);
            resultUser = userService.get(userId);
        }
        return JSON.toJSONString(resultUser);
    }


    /**
     * 删除用户注册信息
     * /users
     * POST
     *
     * @param token
     * @param userId
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteUser(@RequestHeader("token") String token,
                             @PathVariable("id") long userId) {
        if (StringUtil.isEmpty(token) || userId == 0) {
            throw new ServerException(ErrorCode.PARAMETER_ERROR);
        }
        UserEntity onlineUser = getOnlineUser(token);
        checkPermission(onlineUser.getRoleId(), PermissionConfig.TABLE_USER, PermissionConfig.IDENTITY_DELETE, true);
        List<UserInfoEntity> userInfoList = userInfoService.findByUserId(userId);
        if (userInfoList.size() > 0) {
            UserInfoEntity userInfoEntity = userInfoList.get(0);
            userInfoService.delete(userInfoEntity.getId());
        }
        userService.delete(userId);
        return "";
    }

    /**
     * 添加用户详情信息
     * /users/info
     * PUT
     *
     * @param token
     * @param userId
     * @param realName
     * @param gender
     * @param birthday
     * @param educationLevel
     * @param jobId
     * @param departmentId
     * @return
     */
    @RequestMapping(value = "/info", method = RequestMethod.PUT)
    @ResponseBody
    public String addUserInfo(@RequestHeader("token") String token,
                              @RequestParam("id") long userId,
                              @RequestParam("real_name") String realName,
                              @RequestParam("gender") int gender,
                              @RequestParam("birthday") long birthday,
                              @RequestParam("education_level") int educationLevel,
                              @RequestParam("job_id") int jobId,
                              @RequestParam("department_id") int departmentId) {
        if (StringUtil.isEmpty(token)
                || userId == 0
                || StringUtil.isEmpty(realName)
                || gender == 0
                || birthday == 0
                || educationLevel == 0
                || jobId == 0
                || departmentId == 0) {
            throw new ServerException(ErrorCode.PARAMETER_ERROR);
        }
        UserEntity onlineUser = getOnlineUser(token);
        checkPermission(onlineUser.getRoleId(), PermissionConfig.TABLE_USER_INFO, PermissionConfig.IDENTITY_INSERT, true);
        UserInfoEntity userInfoEntity = new UserInfoEntity();
        userInfoEntity.setUserId(userId);
        userInfoEntity.setRealName(realName);
        userInfoEntity.setGender(gender);
        userInfoEntity.setBirthday(birthday);
        userInfoEntity.setEducationLevel(educationLevel);
        userInfoEntity.setJobId(jobId);
        userInfoEntity.setDepartmentId(departmentId);
        userInfoEntity.setImportTime(System.currentTimeMillis());
        userInfoEntity.setUpdateTime(System.currentTimeMillis());
        userInfoService.save(userInfoEntity);
        return "";
    }

    /**
     * 更新用户详情信息
     * /users/info
     * POST
     *
     * @param token
     * @param userInfoId
     * @param realName
     * @param gender
     * @param birthday
     * @param educationLevel
     * @param jobId
     * @param departmentId
     * @return
     */
    @RequestMapping(value = "/info/{id}", method = RequestMethod.POST)
    @ResponseBody
    public String updateUserInfo(@RequestHeader("token") String token,
                                 @PathVariable("id") long userInfoId,
                                 @RequestParam(value = "real_name", required = false) String realName,
                                 @RequestParam(value = "gender", required = false) int gender,
                                 @RequestParam(value = "birthday", required = false) long birthday,
                                 @RequestParam(value = "education_level", required = false) int educationLevel,
                                 @RequestParam(value = "job_id", required = false) int jobId,
                                 @RequestParam(value = "department_id", required = false) int departmentId) {

        if (StringUtil.isEmpty(token)
                || userInfoId == 0) {
            throw new ServerException(ErrorCode.PARAMETER_ERROR);
        }

        UserEntity onlineUser = getOnlineUser(token);
        checkPermission(onlineUser.getRoleId(), PermissionConfig.TABLE_USER_INFO, PermissionConfig.IDENTITY_UPDATE, true);
        List<UserInfoEntity> userInfoList = userInfoService.findByUserId(userInfoId);
        if (userInfoList.size() > 0) {
            UserInfoEntity userInfoEntity = userInfoList.get(0);
            if (!StringUtil.isEmpty(realName)) {
                userInfoEntity.setRealName(realName);
            }
            if (gender != 0) {
                userInfoEntity.setGender(gender);
            }
            if (birthday != 0) {
                userInfoEntity.setBirthday(birthday);
            }
            if (educationLevel != 0) {
                userInfoEntity.setEducationLevel(educationLevel);
            }
            if (jobId != 0) {
                userInfoEntity.setJobId(jobId);
            }
            if (departmentId != 0) {
                userInfoEntity.setDepartmentId(departmentId);
            }
            userInfoEntity.setUpdateTime(System.currentTimeMillis());
            userInfoService.update(userInfoEntity);
        }
        return "";
    }


    /**
     * 获取用户详情信息
     * /users/info/{id}
     * GET
     *
     * @param token
     * @param userInfoId
     * @return
     */
    @RequestMapping(value = "/info/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String getUserInfo(@RequestHeader("token") String token,
                              @PathVariable("id") long userInfoId) {
        if (StringUtil.isEmpty(token)
                || userInfoId == 0) {
            throw new ServerException(ErrorCode.PARAMETER_ERROR);
        }

        UserEntity onlineUser = getOnlineUser(token);
        List<UserInfoEntity> userInfoList = userInfoService.findByUserId(userInfoId);
        if (userInfoList.isEmpty()) {
            throw new ServerException(ErrorCode.ACCOUNT_NOT_FOUND);
        }
        UserInfoEntity userInfoEntity = userInfoList.get(0);
        if (userInfoEntity.getUserId() == onlineUser.getId()) {
            return JSON.toJSONString(userInfoEntity);
        } else {
            checkPermission(onlineUser.getRoleId(), PermissionConfig.TABLE_USER_INFO, PermissionConfig.IDENTITY_SELECT, true);
            return JSON.toJSONString(userInfoEntity);
        }
    }


    /**
     * 删除用户详情信息
     * /users/info
     * DELETE
     *
     * @param token
     * @param userInfoId
     * @return
     */
    @RequestMapping(value = "/info/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public String updateUserInfo(@RequestHeader("token") String token,
                                 @PathVariable("id") long userInfoId) {
        if (StringUtil.isEmpty(token)
                || userInfoId == 0) {
            throw new ServerException(ErrorCode.PARAMETER_ERROR);
        }

        UserEntity onlineUser = getOnlineUser(token);
        checkPermission(onlineUser.getRoleId(), PermissionConfig.TABLE_USER_INFO, PermissionConfig.IDENTITY_DELETE, true);
        userInfoService.delete(userInfoId);
        return "";
    }
}
