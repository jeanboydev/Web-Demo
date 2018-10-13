package com.jeanboy.web.demo.controller;

import com.alibaba.fastjson.JSON;
import com.jeanboy.web.demo.base.BaseController;
import com.jeanboy.web.demo.config.MenuConfig;
import com.jeanboy.web.demo.config.PermissionConfig;
import com.jeanboy.web.demo.constants.EducationLevel;
import com.jeanboy.web.demo.constants.Gender;
import com.jeanboy.web.demo.domain.cache.MemoryCache;
import com.jeanboy.web.demo.domain.entity.*;
import com.jeanboy.web.demo.domain.model.*;
import com.jeanboy.web.demo.domain.service.*;
import com.jeanboy.web.demo.utils.PermissionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
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
    private final DepartmentService departmentService;
    private final JobService jobService;


    @Autowired
    public ViewController(UserService userService,
                          UserInfoService userInfoService,
                          RoleService roleService,
                          RolePermissionService rolePermissionService,
                          SalaryService salaryService,
                          AttendanceTypeService attendanceTypeService,
                          AttendanceService attendanceService,
                          DepartmentService departmentService,
                          JobService jobService) {
        this.userService = userService;
        this.userInfoService = userInfoService;
        this.roleService = roleService;
        this.rolePermissionService = rolePermissionService;
        this.salaryService = salaryService;
        this.attendanceTypeService = attendanceTypeService;
        this.attendanceService = attendanceService;
        this.departmentService = departmentService;
        this.jobService = jobService;
    }

    @RequestMapping
    public String index() {
        return "sign_in";
    }

    private int getShownMenu(int roleId) {
        boolean hadRole = PermissionUtil.check(roleId, PermissionConfig.TABLE_ROLE, PermissionConfig.IDENTITY_SELECT, true);
        boolean hadRolePermission = PermissionUtil.check(roleId, PermissionConfig.TABLE_ROLE_PERMISSION, PermissionConfig.IDENTITY_SELECT, true);
        return MenuConfig.ITEM_HOME + (hadRole && hadRolePermission ? MenuConfig.ITEM_AUTH : 0) + MenuConfig.ITEM_PROFILE + MenuConfig.ITEM_SALARY + MenuConfig.ITEM_RECORD;
    }

    private int getShownTab(int roleId, int table, int identity, int value) {
        boolean had = PermissionUtil.check(roleId, table, identity, false);
        return had ? value : 0;
    }

    private int getShownAction(int roleId, int table) {
        boolean select = PermissionUtil.check(roleId, table, PermissionConfig.IDENTITY_SELECT, false);
        boolean insert = PermissionUtil.check(roleId, table, PermissionConfig.IDENTITY_INSERT, false);
        boolean update = PermissionUtil.check(roleId, table, PermissionConfig.IDENTITY_UPDATE, false);
        boolean delete = PermissionUtil.check(roleId, table, PermissionConfig.IDENTITY_DELETE, false);
        return (select ? PermissionConfig.IDENTITY_SELECT : 0) +
                (insert ? PermissionConfig.IDENTITY_INSERT : 0) +
                (update ? PermissionConfig.IDENTITY_UPDATE : 0) +
                (delete ? PermissionConfig.IDENTITY_DELETE : 0);
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

        UserEntity onlineUser;
        try {
            checkParam(token);
            onlineUser = getOnlineUser(token);
        } catch (Exception e) {
            return "sign_in";
        }

        checkPermission(onlineUser.getRoleId(), PermissionConfig.TABLE_USER, PermissionConfig.IDENTITY_SELECT, false);
        UserModel userModel = Mapper.transform(onlineUser);
        model.addAttribute("user", userModel);
        checkPermission(onlineUser.getRoleId(), PermissionConfig.TABLE_USER_INFO, PermissionConfig.IDENTITY_SELECT, false);
        List<UserInfoEntity> userInfoList = userInfoService.findByUserId(onlineUser.getId());
        if (userInfoList.size() > 0) {
            UserInfoEntity userInfoEntity = userInfoList.get(0);
            UserInfoModel userInfoModel = Mapper.transform(userInfoEntity);
            model.addAttribute("userInfo", userInfoModel);
        }

        int shownMenu = getShownMenu(onlineUser.getRoleId());
        PageModel pageModel = new PageModel(shownMenu, 0, 0);
        model.addAttribute("page", pageModel);
        return "console_home";
    }

    /**
     * 控制台-权限管理
     *
     * @param token
     * @param model
     * @return
     */
    @RequestMapping(value = "/console/auth", method = RequestMethod.GET)
    public String consoleAuth(@RequestParam("token") String token,
                              @RequestParam(value = "tab") Integer tab,
                              Model model) {
        UserEntity onlineUser;
        try {
            checkParam(token);
            onlineUser = getOnlineUser(token);
        } catch (Exception e) {
            return "sign_in";
        }

        checkParam(tab);

        checkPermission(onlineUser.getRoleId(), PermissionConfig.TABLE_USER, PermissionConfig.IDENTITY_SELECT, true);
        UserModel userModel = Mapper.transform(onlineUser);
        model.addAttribute("user", userModel);

        if (tab == 2) {//角色权限表
            checkPermission(onlineUser.getRoleId(), PermissionConfig.TABLE_ROLE_PERMISSION, PermissionConfig.IDENTITY_SELECT, true);
            List<RolePermissionEntity> entityList = rolePermissionService.getAll();

            List<RolePermissionModel> dataList = new ArrayList<>();
            for (RolePermissionEntity entity : entityList) {
                RolePermissionModel rolePermissionModel = Mapper.transform(entity);
                dataList.add(rolePermissionModel);
            }

            model.addAttribute("dataList", dataList);
            model.addAttribute("tab", 2);

            //角色列表
            List<RoleEntity> roleList = roleService.getAll();
            model.addAttribute("roleList", JSON.toJSON(roleList));
            //权限列表
            List<SelectModel> tableList = new ArrayList<>();
            for (int key : PermissionConfig.tableMap.keySet()) {
                tableList.add(new SelectModel(key, PermissionConfig.tableMap.get(key)));
            }
            model.addAttribute("tableList", JSON.toJSON(tableList));

            int shownMenu = getShownMenu(onlineUser.getRoleId());
            int tab1 = getShownTab(onlineUser.getRoleId(), PermissionConfig.TABLE_ROLE, PermissionConfig.IDENTITY_SELECT, 1);
            int tab2 = getShownTab(onlineUser.getRoleId(), PermissionConfig.TABLE_ROLE_PERMISSION, PermissionConfig.IDENTITY_SELECT, 2);
            int shownTab = tab1 + tab2;
            int showAction = getShownAction(onlineUser.getRoleId(), PermissionConfig.TABLE_ROLE_PERMISSION);
            PageModel pageModel = new PageModel(shownMenu, shownTab, showAction);
            model.addAttribute("page", pageModel);
        } else {//角色表
            checkPermission(onlineUser.getRoleId(), PermissionConfig.TABLE_ROLE, PermissionConfig.IDENTITY_SELECT, true);
            List<RoleEntity> dataList = roleService.getAll();
            model.addAttribute("dataList", dataList);
            model.addAttribute("tab", 1);

            int shownMenu = getShownMenu(onlineUser.getRoleId());
            int tab1 = getShownTab(onlineUser.getRoleId(), PermissionConfig.TABLE_ROLE, PermissionConfig.IDENTITY_SELECT, 1);
            int tab2 = getShownTab(onlineUser.getRoleId(), PermissionConfig.TABLE_ROLE_PERMISSION, PermissionConfig.IDENTITY_SELECT, 2);
            int shownTab = tab1 + tab2;
            int showAction = getShownAction(onlineUser.getRoleId(), PermissionConfig.TABLE_ROLE);
            PageModel pageModel = new PageModel(shownMenu, shownTab, showAction);
            model.addAttribute("page", pageModel);
        }
        return "console_auth";
    }

    /**
     * 控制台-人事管理
     *
     * @param token
     * @param model
     * @return
     */
    @RequestMapping(value = "/console/profile", method = RequestMethod.GET)
    public String consoleProfile(@RequestParam("token") String token,
                                 @RequestParam(value = "tab") Integer tab,
                                 Model model) {
        UserEntity onlineUser;
        try {
            checkParam(token);
            onlineUser = getOnlineUser(token);
        } catch (Exception e) {
            return "sign_in";
        }

        checkParam(tab);

        checkPermission(onlineUser.getRoleId(), PermissionConfig.TABLE_USER, PermissionConfig.IDENTITY_SELECT, false);
        UserModel userModel = Mapper.transform(onlineUser);
        model.addAttribute("user", userModel);

        if (tab == 2) {//用户信息表
            List<UserInfoEntity> entityList;
            try {
                checkPermission(onlineUser.getRoleId(), PermissionConfig.TABLE_USER_INFO, PermissionConfig.IDENTITY_SELECT, true);
                entityList = userInfoService.getAll();
            } catch (Exception e) {
                checkPermission(onlineUser.getRoleId(), PermissionConfig.TABLE_USER_INFO, PermissionConfig.IDENTITY_SELECT, false);
                entityList = userInfoService.findByUserId(onlineUser.getId());
            }

            List<UserInfoModel> dataList = new ArrayList<>();
            for (UserInfoEntity entity : entityList) {
                UserInfoModel transform = Mapper.transform(entity);
                dataList.add(transform);
            }

            model.addAttribute("dataList", dataList);
            model.addAttribute("tab", 2);

            List<SelectModel> genderList = new ArrayList<>();
            genderList.add(new SelectModel(Gender.UNKNOWN.getValue(), Gender.UNKNOWN.getName()));
            genderList.add(new SelectModel(Gender.MALE.getValue(), Gender.MALE.getName()));
            genderList.add(new SelectModel(Gender.FEMALE.getValue(), Gender.FEMALE.getName()));
            model.addAttribute("genderList", JSON.toJSON(genderList));

            List<SelectModel> educationLevelList = new ArrayList<>();
            educationLevelList.add(new SelectModel(EducationLevel.UNKNOWN.getValue(), EducationLevel.UNKNOWN.getName()));
            educationLevelList.add(new SelectModel(EducationLevel.PRIMARY_SCHOOL.getValue(), EducationLevel.PRIMARY_SCHOOL.getName()));
            educationLevelList.add(new SelectModel(EducationLevel.MIDDLE_SCHOOL.getValue(), EducationLevel.MIDDLE_SCHOOL.getName()));
            educationLevelList.add(new SelectModel(EducationLevel.HIGH_SCHOOL.getValue(), EducationLevel.HIGH_SCHOOL.getName()));
            educationLevelList.add(new SelectModel(EducationLevel.JUNIOR_COLLEGE.getValue(), EducationLevel.JUNIOR_COLLEGE.getName()));
            educationLevelList.add(new SelectModel(EducationLevel.UNIVERSITY.getValue(), EducationLevel.UNIVERSITY.getName()));
            educationLevelList.add(new SelectModel(EducationLevel.UNDERGRADUATE.getValue(), EducationLevel.UNDERGRADUATE.getName()));
            educationLevelList.add(new SelectModel(EducationLevel.DOCTOR.getValue(), EducationLevel.DOCTOR.getName()));
            model.addAttribute("educationLevelList", JSON.toJSON(educationLevelList));

            List<SelectModel> jobList = new ArrayList<>();
            List<JobEntity> jobEntityList = jobService.getAll();
            jobList.add(new SelectModel(0, "未知"));
            for (JobEntity entity : jobEntityList) {
                jobList.add(new SelectModel(entity.getId(), entity.getName()));
            }
            model.addAttribute("jobList", JSON.toJSON(jobList));

            List<SelectModel> departmentList = new ArrayList<>();
            List<DepartmentEntity> departmentEntityList = departmentService.getAll();
            departmentList.add(new SelectModel(0, "未知"));
            for (DepartmentEntity entity : departmentEntityList) {
                departmentList.add(new SelectModel(entity.getId(), entity.getName()));
            }
            model.addAttribute("departmentList", JSON.toJSON(departmentList));

            int shownMenu = getShownMenu(onlineUser.getRoleId());
            int tab1 = getShownTab(onlineUser.getRoleId(), PermissionConfig.TABLE_USER, PermissionConfig.IDENTITY_SELECT, 1);
            int tab2 = getShownTab(onlineUser.getRoleId(), PermissionConfig.TABLE_USER_INFO, PermissionConfig.IDENTITY_SELECT, 2);
            int tab3 = getShownTab(onlineUser.getRoleId(), PermissionConfig.TABLE_DEPARTMENT, PermissionConfig.IDENTITY_SELECT, 4);
            int tab4 = getShownTab(onlineUser.getRoleId(), PermissionConfig.TABLE_JOB, PermissionConfig.IDENTITY_SELECT, 8);
            int shownTab = tab1 + tab2 + tab3 + tab4;
            int showAction = getShownAction(onlineUser.getRoleId(), PermissionConfig.TABLE_USER_INFO);
            PageModel pageModel = new PageModel(shownMenu, shownTab, showAction);
            model.addAttribute("page", pageModel);
        } else if (tab == 4) {//部门信息表
            checkPermission(onlineUser.getRoleId(), PermissionConfig.TABLE_DEPARTMENT, PermissionConfig.IDENTITY_SELECT, true);
            List<DepartmentEntity> dataList = departmentService.getAll();
            model.addAttribute("dataList", dataList);
            model.addAttribute("tab", 4);

            int shownMenu = getShownMenu(onlineUser.getRoleId());
            int tab1 = getShownTab(onlineUser.getRoleId(), PermissionConfig.TABLE_USER, PermissionConfig.IDENTITY_SELECT, 1);
            int tab2 = getShownTab(onlineUser.getRoleId(), PermissionConfig.TABLE_USER_INFO, PermissionConfig.IDENTITY_SELECT, 2);
            int tab3 = getShownTab(onlineUser.getRoleId(), PermissionConfig.TABLE_DEPARTMENT, PermissionConfig.IDENTITY_SELECT, 4);
            int tab4 = getShownTab(onlineUser.getRoleId(), PermissionConfig.TABLE_JOB, PermissionConfig.IDENTITY_SELECT, 8);
            int shownTab = tab1 + tab2 + tab3 + tab4;
            int showAction = getShownAction(onlineUser.getRoleId(), PermissionConfig.TABLE_DEPARTMENT);
            PageModel pageModel = new PageModel(shownMenu, shownTab, showAction);
            model.addAttribute("page", pageModel);
        } else if (tab == 8) {//职位信息表
            checkPermission(onlineUser.getRoleId(), PermissionConfig.TABLE_JOB, PermissionConfig.IDENTITY_SELECT, true);
            List<JobEntity> dataList = jobService.getAll();
            model.addAttribute("dataList", dataList);
            model.addAttribute("tab", 8);

            int shownMenu = getShownMenu(onlineUser.getRoleId());
            int tab1 = getShownTab(onlineUser.getRoleId(), PermissionConfig.TABLE_USER, PermissionConfig.IDENTITY_SELECT, 1);
            int tab2 = getShownTab(onlineUser.getRoleId(), PermissionConfig.TABLE_USER_INFO, PermissionConfig.IDENTITY_SELECT, 2);
            int tab3 = getShownTab(onlineUser.getRoleId(), PermissionConfig.TABLE_DEPARTMENT, PermissionConfig.IDENTITY_SELECT, 4);
            int tab4 = getShownTab(onlineUser.getRoleId(), PermissionConfig.TABLE_JOB, PermissionConfig.IDENTITY_SELECT, 8);
            int shownTab = tab1 + tab2 + tab3 + tab4;
            int showAction = getShownAction(onlineUser.getRoleId(), PermissionConfig.TABLE_JOB);
            PageModel pageModel = new PageModel(shownMenu, shownTab, showAction);
            model.addAttribute("page", pageModel);
        } else {//用户账号表
            List<UserEntity> entityList;
            try {
                checkPermission(onlineUser.getRoleId(), PermissionConfig.TABLE_USER, PermissionConfig.IDENTITY_SELECT, true);
                entityList = userService.getAll();
            } catch (Exception e) {
                checkPermission(onlineUser.getRoleId(), PermissionConfig.TABLE_USER, PermissionConfig.IDENTITY_SELECT, false);
                entityList = userService.findByUsername(onlineUser.getUsername());
            }

            List<UserModel> dataList = new ArrayList<>();
            for (UserEntity entity : entityList) {
                UserModel transform = Mapper.transform(entity);
                dataList.add(transform);
            }
            model.addAttribute("dataList", dataList);
            model.addAttribute("tab", 1);

            //角色列表
            List<RoleEntity> roleList = roleService.getAll();
            model.addAttribute("roleList", JSON.toJSON(roleList));

            int shownMenu = getShownMenu(onlineUser.getRoleId());
            int tab1 = getShownTab(onlineUser.getRoleId(), PermissionConfig.TABLE_USER, PermissionConfig.IDENTITY_SELECT, 1);
            int tab2 = getShownTab(onlineUser.getRoleId(), PermissionConfig.TABLE_USER_INFO, PermissionConfig.IDENTITY_SELECT, 2);
            int tab3 = getShownTab(onlineUser.getRoleId(), PermissionConfig.TABLE_DEPARTMENT, PermissionConfig.IDENTITY_SELECT, 4);
            int tab4 = getShownTab(onlineUser.getRoleId(), PermissionConfig.TABLE_JOB, PermissionConfig.IDENTITY_SELECT, 8);
            int shownTab = tab1 + tab2 + tab3 + tab4;
            int showAction = getShownAction(onlineUser.getRoleId(), PermissionConfig.TABLE_USER);
            PageModel pageModel = new PageModel(shownMenu, shownTab, showAction);
            model.addAttribute("page", pageModel);
        }
        return "console_profile";
    }

    /**
     * 控制台-工资管理
     *
     * @param token
     * @param model
     * @return
     */
    @RequestMapping(value = "/console/salary", method = RequestMethod.GET)
    public String consoleSalary(@RequestParam("token") String token,
                                @RequestParam(value = "tab") Integer tab,
                                Model model) {
        UserEntity onlineUser;
        try {
            checkParam(token);
            onlineUser = getOnlineUser(token);
        } catch (Exception e) {
            return "sign_in";
        }

        checkPermission(onlineUser.getRoleId(), PermissionConfig.TABLE_USER, PermissionConfig.IDENTITY_SELECT, false);
        UserModel userModel = Mapper.transform(onlineUser);
        model.addAttribute("user", userModel);

        List<SalaryEntity> salaryList = new ArrayList<>();
        try {
            checkPermission(onlineUser.getRoleId(), PermissionConfig.TABLE_SALARY, PermissionConfig.IDENTITY_SELECT, true);
            salaryList = salaryService.getAll();
        } catch (Exception e) {
            checkPermission(onlineUser.getRoleId(), PermissionConfig.TABLE_SALARY, PermissionConfig.IDENTITY_SELECT, false);
            List<UserInfoEntity> userInfoList = userInfoService.findByUserId(onlineUser.getId());
            if (userInfoList.size() > 0) {
                UserInfoEntity userInfoEntity = userInfoList.get(0);
                salaryList = salaryService.findByJobId(userInfoEntity.getJobId());
            }
        }
        List<SalaryModel> dataList = new ArrayList<>();
        for (SalaryEntity entity : salaryList) {
            SalaryModel salaryModel = Mapper.transform(entity);
            dataList.add(salaryModel);
        }

        model.addAttribute("dataList", dataList);
        model.addAttribute("tab", 1);

        List<SelectModel> jobList = new ArrayList<>();
        List<JobEntity> jobEntityList = jobService.getAll();
        jobList.add(new SelectModel(0, "未知"));
        for (JobEntity entity : jobEntityList) {
            jobList.add(new SelectModel(entity.getId(), entity.getName()));
        }
        model.addAttribute("jobList", JSON.toJSON(jobList));

        int shownMenu = getShownMenu(onlineUser.getRoleId());
        int tab1 = getShownTab(onlineUser.getRoleId(), PermissionConfig.TABLE_SALARY, PermissionConfig.IDENTITY_SELECT, 1);
        int shownTab = tab1;
        int showAction = getShownAction(onlineUser.getRoleId(), PermissionConfig.TABLE_SALARY);
        PageModel pageModel = new PageModel(shownMenu, shownTab, showAction);
        model.addAttribute("page", pageModel);
        return "console_salary";
    }

    /**
     * 控制台-考勤管理
     *
     * @param token
     * @param model
     * @return
     */
    @RequestMapping(value = "/console/record", method = RequestMethod.GET)
    public String consoleRecord(@RequestParam("token") String token,
                                @RequestParam(value = "tab") Integer tab,
                                Model model) {
        UserEntity onlineUser;
        try {
            checkParam(token);
            onlineUser = getOnlineUser(token);
        } catch (Exception e) {
            return "sign_in";
        }

        checkPermission(onlineUser.getRoleId(), PermissionConfig.TABLE_USER, PermissionConfig.IDENTITY_SELECT, false);
        UserModel userModel = Mapper.transform(onlineUser);
        model.addAttribute("user", userModel);
        if (tab == 2) {//考勤表
            List<AttendanceEntity> dataList;
            try {
                checkPermission(onlineUser.getRoleId(), PermissionConfig.TABLE_ATTENDANCE, PermissionConfig.IDENTITY_SELECT, true);
                dataList = attendanceService.getAll();
            } catch (Exception e) {
                checkPermission(onlineUser.getRoleId(), PermissionConfig.TABLE_ATTENDANCE, PermissionConfig.IDENTITY_SELECT, false);
                dataList = attendanceService.findByUserId(onlineUser.getId());
            }
            model.addAttribute("dataList", dataList);
            model.addAttribute("tab", 2);

            List<SelectModel> attendanceTypeList = new ArrayList<>();
            List<AttendanceTypeEntity> jobEntityList = attendanceTypeService.getAll();
            attendanceTypeList.add(new SelectModel(0, "未知"));
            for (AttendanceTypeEntity entity : jobEntityList) {
                attendanceTypeList.add(new SelectModel(entity.getId(), entity.getName()));
            }
            model.addAttribute("attendanceTypeList", JSON.toJSON(attendanceTypeList));

            int shownMenu = getShownMenu(onlineUser.getRoleId());
            int tab1 = getShownTab(onlineUser.getRoleId(), PermissionConfig.TABLE_ATTENDANCE_TYPE, PermissionConfig.IDENTITY_SELECT, 1);
            int tab2 = getShownTab(onlineUser.getRoleId(), PermissionConfig.TABLE_ATTENDANCE, PermissionConfig.IDENTITY_SELECT, 2);
            int shownTab = tab1 + tab2;
            int showAction = getShownAction(onlineUser.getRoleId(), PermissionConfig.TABLE_ATTENDANCE);
            PageModel pageModel = new PageModel(shownMenu, shownTab, showAction);
            model.addAttribute("page", pageModel);
        } else {//考勤类型表
            checkPermission(onlineUser.getRoleId(), PermissionConfig.TABLE_ATTENDANCE_TYPE, PermissionConfig.IDENTITY_SELECT, false);
            List<AttendanceTypeEntity> dataList = attendanceTypeService.getAll();
            model.addAttribute("dataList", dataList);
            model.addAttribute("tab", 1);

            int shownMenu = getShownMenu(onlineUser.getRoleId());
            int tab1 = getShownTab(onlineUser.getRoleId(), PermissionConfig.TABLE_ATTENDANCE_TYPE, PermissionConfig.IDENTITY_SELECT, 1);
            int tab2 = getShownTab(onlineUser.getRoleId(), PermissionConfig.TABLE_ATTENDANCE, PermissionConfig.IDENTITY_SELECT, 2);
            int shownTab = tab1 + tab2;
            int showAction = getShownAction(onlineUser.getRoleId(), PermissionConfig.TABLE_ATTENDANCE_TYPE);
            PageModel pageModel = new PageModel(shownMenu, shownTab, showAction);
            model.addAttribute("page", pageModel);
        }
        return "console_record";
    }
}
