package com.jeanboy.web.demo.controller;

import com.alibaba.fastjson.JSON;
import com.jeanboy.web.demo.base.BaseController;
import com.jeanboy.web.demo.config.PermissionConfig;
import com.jeanboy.web.demo.domain.cache.MemoryCache;
import com.jeanboy.web.demo.domain.entity.*;
import com.jeanboy.web.demo.domain.model.*;
import com.jeanboy.web.demo.domain.service.UserInfoService;
import com.jeanboy.web.demo.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping(value = {"", "/", "/index"}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ViewController extends BaseController {
    private final UserService userService;
    private final UserInfoService userInfoService;


    @Autowired
    public ViewController(UserService userService, UserInfoService userInfoService) {
        this.userService = userService;
        this.userInfoService = userInfoService;
    }

    @RequestMapping
    public String index() {
        return "sign_in";
    }

    @RequestMapping(value = "/console", method = RequestMethod.GET)
    public String console(@RequestParam("token") String token, Model model) {
        UserEntity onlineUser = getOnlineUser(token);
        checkPermission(onlineUser.getRoleId(), PermissionConfig.TABLE_USER_INFO, PermissionConfig.IDENTITY_SELECT, true);
        RoleEntity roleEntity = MemoryCache.getRoleEntity(onlineUser.getRoleId());
        RoleModel roleModel = Mapper.transform(roleEntity);
        UserModel userModel = Mapper.transform(onlineUser, roleModel);
        model.addAttribute("user", userModel);
        List<UserInfoEntity> userInfoList = userInfoService.findByUserId(onlineUser.getId());
        if (userInfoList.size() > 0) {
            UserInfoEntity userInfoEntity = userInfoList.get(0);
            JobEntity jobEntity = MemoryCache.getJobEntity(userInfoEntity.getJobId());
            JobModel jobModel = Mapper.transform(jobEntity);
            DepartmentEntity departmentEntity = MemoryCache.getDepartmentEntity(userInfoEntity.getDepartmentId());
            DepartmentModel departmentModel = Mapper.transform(departmentEntity);
            UserInfoModel userInfoModel = Mapper.transform(userInfoEntity, jobModel, departmentModel);
            System.out.println("+==============");
            System.out.println(JSON.toJSONString(userInfoModel));
            model.addAttribute("userInfo", userInfoModel);
        }
        return "console";
    }
}
