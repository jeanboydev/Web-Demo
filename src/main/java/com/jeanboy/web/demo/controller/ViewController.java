package com.jeanboy.web.demo.controller;

import com.jeanboy.web.demo.base.BaseController;
import com.jeanboy.web.demo.config.PermissionConfig;
import com.jeanboy.web.demo.domain.cache.MemoryCache;
import com.jeanboy.web.demo.domain.entity.*;
import com.jeanboy.web.demo.domain.model.*;
import com.jeanboy.web.demo.domain.service.*;
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
    private final RolePermissionService rolePermissionService;
    private final SalaryService salaryService;
    private final AttendanceTypeService attendanceTypeService;
    private final AttendanceService attendanceService;


    @Autowired
    public ViewController(UserService userService,
                          UserInfoService userInfoService,
                          RoleService roleService,
                          RolePermissionService rolePermissionService, SalaryService salaryService,
                          AttendanceTypeService attendanceTypeService,
                          AttendanceService attendanceService) {
        this.userService = userService;
        this.userInfoService = userInfoService;
        this.roleService = roleService;
        this.rolePermissionService = rolePermissionService;
        this.salaryService = salaryService;
        this.attendanceTypeService = attendanceTypeService;
        this.attendanceService = attendanceService;
    }

    @RequestMapping
    public String index() {
        return "sign_in";
    }


    /**
     * 控制台-首页
     *
     * @param token
     * @param model
     * @return
     */
    @RequestMapping(value = "/console", method = RequestMethod.GET)
    public String console(@RequestParam("token") String token, Model model) {
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
        checkPermission(onlineUser.getRoleId(), PermissionConfig.TABLE_USER_INFO, PermissionConfig.IDENTITY_SELECT, true);
        List<UserInfoEntity> userInfoList = userInfoService.findByUserId(onlineUser.getId());
        if (userInfoList.size() > 0) {
            UserInfoEntity userInfoEntity = userInfoList.get(0);
            JobEntity jobEntity = MemoryCache.getJobEntity(userInfoEntity.getJobId());
            JobModel jobModel = Mapper.transform(jobEntity);
            DepartmentEntity departmentEntity = MemoryCache.getDepartmentEntity(userInfoEntity.getDepartmentId());
            DepartmentModel departmentModel = Mapper.transform(departmentEntity);
            UserInfoModel userInfoModel = Mapper.transform(userInfoEntity, jobModel, departmentModel);
            model.addAttribute("userInfo", userInfoModel);
        }
        return "console_home";
    }

    /**
     * 权限管理-角色表
     *
     * @param token
     * @param model
     * @return
     */
    @RequestMapping(value = "/console/auth", method = RequestMethod.GET)
    public String consoleAuth(@RequestParam("token") String token,
                              @RequestParam(value = "tab", required = false) Integer tab,
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

        if (tab != null && tab == 1) {
            checkPermission(onlineUser.getRoleId(), PermissionConfig.TABLE_ROLE_PERMISSION, PermissionConfig.IDENTITY_SELECT, true);
            List<RolePermissionEntity> dataList = rolePermissionService.getAll();
            model.addAttribute("tab", 1);
            model.addAttribute("dataList", dataList);
        } else {
            checkPermission(onlineUser.getRoleId(), PermissionConfig.TABLE_ROLE, PermissionConfig.IDENTITY_SELECT, true);
            List<RoleEntity> dataList = roleService.getAll();
            model.addAttribute("tab", 0);
            model.addAttribute("dataList", dataList);
        }
        return "console_auth";
    }

    /**
     * 人事管理-用户信息表
     *
     * @param token
     * @param model
     * @return
     */
    @RequestMapping(value = "/console/profile", method = RequestMethod.GET)
    public String consoleProfile(@RequestParam("token") String token, Model model) {
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
        checkPermission(onlineUser.getRoleId(), PermissionConfig.TABLE_USER_INFO, PermissionConfig.IDENTITY_SELECT, true);
        List<UserInfoEntity> dataList = userInfoService.getAll();
        model.addAttribute("dataList", dataList);
        return "console_profile";
    }

    /**
     * 工资管理-工资表
     *
     * @param token
     * @param model
     * @return
     */
    @RequestMapping(value = "/console/salary", method = RequestMethod.GET)
    public String consoleSalary(@RequestParam("token") String token, Model model) {
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
        checkPermission(onlineUser.getRoleId(), PermissionConfig.TABLE_SALARY, PermissionConfig.IDENTITY_SELECT, true);
        List<SalaryEntity> dataList = salaryService.getAll();
        model.addAttribute("dataList", dataList);
        return "console_salary";
    }

    /**
     * 考勤管理-考勤表
     *
     * @param token
     * @param model
     * @return
     */
    @RequestMapping(value = "/console/record", method = RequestMethod.GET)
    public String consoleRecord(@RequestParam("token") String token, Model model) {
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
        checkPermission(onlineUser.getRoleId(), PermissionConfig.TABLE_SALARY, PermissionConfig.IDENTITY_SELECT, true);
        List<AttendanceEntity> dataList = attendanceService.getAll();
        model.addAttribute("dataList", dataList);
        return "console_record";
    }
}
