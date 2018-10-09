package com.jeanboy.web.demo.controller;

import com.alibaba.fastjson.JSON;
import com.jeanboy.web.demo.base.BaseController;
import com.jeanboy.web.demo.config.PermissionConfig;
import com.jeanboy.web.demo.domain.cache.MemoryCache;
import com.jeanboy.web.demo.domain.entity.*;
import com.jeanboy.web.demo.domain.model.*;
import com.jeanboy.web.demo.domain.service.RoleService;
import com.jeanboy.web.demo.domain.service.UserInfoService;
import com.jeanboy.web.demo.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

@Controller
@RequestMapping(value = {"", "/", "/index"}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ViewController extends BaseController {
    private final UserService userService;
    private final UserInfoService userInfoService;
    private final RoleService roleService;


    @Autowired
    public ViewController(UserService userService, UserInfoService userInfoService, RoleService roleService) {
        this.userService = userService;
        this.userInfoService = userInfoService;
        this.roleService = roleService;
    }

    @RequestMapping
    public String index() {
        return "sign_in";
    }

    @RequestMapping(value = "/console", method = RequestMethod.GET)
    public String console(@RequestParam("token") String token,
                          @RequestParam("tag") int tag,
                          Model model) {
        UserEntity onlineUser = getOnlineUser(token);
        String tokenEncode = "";
        try {
            tokenEncode = URLEncoder.encode(token, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        model.addAttribute("token", tokenEncode);
        checkPermission(onlineUser.getRoleId(), PermissionConfig.TABLE_USER, PermissionConfig.IDENTITY_SELECT, true);
        RoleEntity roleEntity = MemoryCache.getRoleEntity(onlineUser.getRoleId());
        RoleModel roleModel = Mapper.transform(roleEntity);
        UserModel userModel = Mapper.transform(onlineUser, roleModel);
        model.addAttribute("user", userModel);
        switch (tag) {
            case 1://权限管理
                checkPermission(onlineUser.getRoleId(), PermissionConfig.TABLE_ROLE, PermissionConfig.IDENTITY_SELECT, true);
                List<RoleEntity> roleList = roleService.getAll();
                model.addAttribute("dataList", roleList);
                return "console_" + tag;
            case 2://人事管理
                return "console_" + tag;
            case 3://工资管理
                return "console_" + tag;
            case 4://考勤管理
                return "console_" + tag;
            default://首页
                checkPermission(onlineUser.getRoleId(), PermissionConfig.TABLE_USER_INFO, PermissionConfig.IDENTITY_SELECT, true);
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
                return "console_0";
        }
    }
}
