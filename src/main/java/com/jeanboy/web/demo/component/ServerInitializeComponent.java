package com.jeanboy.web.demo.component;

import com.jeanboy.web.demo.config.AccountConfig;
import com.jeanboy.web.demo.config.PermissionConfig;
import com.jeanboy.web.demo.domain.cache.MemoryCache;
import com.jeanboy.web.demo.domain.entity.*;
import com.jeanboy.web.demo.domain.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ServerInitializeComponent implements ApplicationListener<ContextRefreshedEvent> {

    private Logger logger = LoggerFactory.getLogger(ServerInitializeComponent.class);

    private final PermissionService permissionService;
    private final RoleService roleService;
    private final RolePermissionService rolePermissionService;
    private final UserService userService;
    private final UserInfoService userInfoService;
    private final JobService jobService;
    private final DepartmentService departmentService;
    private final AttendanceTypeService attendanceTypeService;

    @Autowired
    public ServerInitializeComponent(PermissionService permissionService,
                                     RoleService roleService,
                                     RolePermissionService rolePermissionService,
                                     UserService userService,
                                     UserInfoService userInfoService,
                                     JobService jobService,
                                     DepartmentService departmentService,
                                     AttendanceTypeService attendanceTypeService) {
        this.permissionService = permissionService;
        this.roleService = roleService;
        this.rolePermissionService = rolePermissionService;
        this.userService = userService;
        this.userInfoService = userInfoService;
        this.jobService = jobService;
        this.departmentService = departmentService;
        this.attendanceTypeService = attendanceTypeService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        logger.info("======================初始化配置信息======================");

        List<PermissionEntity> permissionEntityList = permissionService.getAll();
        if (permissionEntityList.isEmpty()) {
            logger.info("======================初始化权限表信息======================");
            for (Integer key : PermissionConfig.tableMap.keySet()) {
                PermissionEntity entity = new PermissionEntity();
                entity.setIdentity(key);
                entity.setDescription(PermissionConfig.tableMap.get(key));
                entity.setCreateTime(System.currentTimeMillis());
                permissionService.save(entity);
            }
        }

        List<RolePermissionEntity> permissionList = rolePermissionService.getAll();
        int roleManagerId = 0;
        if (!permissionList.isEmpty()) {
            for (RolePermissionEntity entity : permissionList) {
                if (entity.getPermissionIdentity() == PermissionConfig.ROLE_MASTER) {
                    roleManagerId = entity.getRoleId();
                    break;
                }
            }
        }
        if (roleManagerId == 0) {
            logger.info("======================初始化角色表信息======================");
            RoleEntity roleManager = new RoleEntity();
            roleManager.setName(AccountConfig.MANAGER_DEFAULT_ROLE_NAME);
            roleManager.setCreateTime(System.currentTimeMillis());
            roleManagerId = roleService.save(roleManager);
            roleManager.setId(roleManagerId);

            RolePermissionEntity rolePermissionEntity = new RolePermissionEntity();
            rolePermissionEntity.setRoleId(roleManagerId);
            rolePermissionEntity.setPermissionIdentity(PermissionConfig.ROLE_MASTER);
            rolePermissionEntity.setCreateTime(System.currentTimeMillis());
            rolePermissionService.save(rolePermissionEntity);
        }

        List<UserEntity> userList = userService.findByUsername(AccountConfig.MANAGER_DEFAULT_USERNAME);
        if (userList.isEmpty()) {
            logger.info("======================初始化用户表信息======================");
            UserEntity userManager = new UserEntity();
            userManager.setUsername(AccountConfig.MANAGER_DEFAULT_USERNAME);
//            String md5Password = StringUtil.getMD5(AccountConfig.MANAGER_DEFAULT_PASSWORD);
            userManager.setPassword(AccountConfig.MANAGER_DEFAULT_PASSWORD);
            userManager.setCreateTime(System.currentTimeMillis());
            userManager.setRoleId(roleManagerId);
            long userManagerId = userService.save(userManager);

            UserInfoEntity userManagerInfo = new UserInfoEntity();
            userManagerInfo.setRealName(AccountConfig.MANAGER_DEFAULT_ROLE_NAME);
            userManagerInfo.setUserId(userManagerId);
            userManagerInfo.setImportTime(System.currentTimeMillis());
            userManagerInfo.setUpdateTime(System.currentTimeMillis());
            userManagerInfo.setCreateTime(System.currentTimeMillis());
            userInfoService.save(userManagerInfo);
        }


        List<RoleEntity> roleList = roleService.getAll();
        for (RoleEntity entity : roleList) {
            MemoryCache.putRoleEntity(entity);
        }

        List<RolePermissionEntity> rolePermissionList = rolePermissionService.getAll();
        for (RolePermissionEntity entity : rolePermissionList) {
            MemoryCache.putRolePermissionEntity(entity);
        }

        List<JobEntity> jobEntityList = jobService.getAll();
        for (JobEntity entity : jobEntityList) {
            MemoryCache.putJobEntity(entity);
        }

        List<DepartmentEntity> departmentEntityList = departmentService.getAll();
        for (DepartmentEntity entity : departmentEntityList) {
            MemoryCache.putDepartmentEntity(entity);
        }
        List<AttendanceTypeEntity> attendanceTypeEntityList = attendanceTypeService.getAll();
        for (AttendanceTypeEntity entity : attendanceTypeEntityList) {
            MemoryCache.putAttendanceTypeEntity(entity);
        }
    }
}
