package com.jeanboy.web.demo.component;

import com.jeanboy.web.demo.config.AccountConfig;
import com.jeanboy.web.demo.config.PermissionConfig;
import com.jeanboy.web.demo.domain.entity.PermissionEntity;
import com.jeanboy.web.demo.domain.entity.RoleEntity;
import com.jeanboy.web.demo.domain.entity.RolePermissionEntity;
import com.jeanboy.web.demo.domain.entity.UserEntity;
import com.jeanboy.web.demo.domain.service.PermissionService;
import com.jeanboy.web.demo.domain.service.RolePermissionService;
import com.jeanboy.web.demo.domain.service.RoleService;
import com.jeanboy.web.demo.domain.service.UserService;
import com.jeanboy.web.demo.utils.StringUtil;
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

    @Autowired
    public ServerInitializeComponent(PermissionService permissionService,
                                     RoleService roleService,
                                     RolePermissionService rolePermissionService,
                                     UserService userService) {
        this.permissionService = permissionService;
        this.roleService = roleService;
        this.rolePermissionService = rolePermissionService;
        this.userService = userService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        logger.info("======================初始化配置信息======================");

        List<PermissionEntity> permissionEntityList = permissionService.findAll();
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

        List<RolePermissionEntity> permissionList = rolePermissionService.findByIdentity(PermissionConfig.MASTER);
        int roleManagerId = 0;
        if (permissionList.isEmpty()) {
            logger.info("======================初始化角色表信息======================");
            RoleEntity roleManager = new RoleEntity();
            roleManager.setName(AccountConfig.MANAGER_DEFAULT_ROLE_NAME);
            roleManager.setCreateTime(System.currentTimeMillis());
            roleManagerId = roleService.save(roleManager);
            roleManager.setId(roleManagerId);

            for (Integer key : PermissionConfig.tableMap.keySet()) {
                RolePermissionEntity rolePermissionEntity = new RolePermissionEntity();
                rolePermissionEntity.setRoleId(roleManagerId);
                rolePermissionEntity.setPermissionIdentity(PermissionConfig.MASTER);
                rolePermissionEntity.setCreateTime(System.currentTimeMillis());
                rolePermissionService.save(rolePermissionEntity);
            }
        } else {
            RolePermissionEntity rolePermissionEntity = permissionList.get(0);
            roleManagerId = rolePermissionEntity.getRoleId();
        }

        UserEntity userManager = userService.findByUsername(AccountConfig.MANAGER_DEFAULT_USERNAME);
        if (userManager == null) {
            logger.info("======================初始化用户表信息======================");
            userManager = new UserEntity();
            userManager.setUsername(AccountConfig.MANAGER_DEFAULT_USERNAME);
            userManager.setRealName(AccountConfig.MANAGER_DEFAULT_ROLE_NAME);
            String md5Password = StringUtil.getMD5(AccountConfig.MANAGER_DEFAULT_PASSWORD);
            userManager.setPassword(md5Password);
            userManager.setUpdateTime(System.currentTimeMillis());
            userManager.setCreateTime(System.currentTimeMillis());
            userManager.setRoleId(roleManagerId);
            userService.save(userManager);
        }

    }
}
