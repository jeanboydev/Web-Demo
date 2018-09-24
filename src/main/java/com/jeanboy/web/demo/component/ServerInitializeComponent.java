package com.jeanboy.web.demo.component;

import com.jeanboy.web.demo.config.AccountConfig;
import com.jeanboy.web.demo.config.PermissionConfig;
import com.jeanboy.web.demo.domain.entity.PermissionEntity;
import com.jeanboy.web.demo.domain.entity.RoleEntity;
import com.jeanboy.web.demo.domain.entity.UserEntity;
import com.jeanboy.web.demo.domain.service.PermissionService;
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
    private final UserService userService;

    @Autowired
    public ServerInitializeComponent(PermissionService permissionService, RoleService roleService, UserService userService) {
        this.permissionService = permissionService;
        this.roleService = roleService;
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

        RoleEntity roleManager = roleService.findByIdentity(PermissionConfig.MASTER);
        if (roleManager == null) {
            logger.info("======================初始化角色表信息======================");
            roleManager = new RoleEntity();
            roleManager.setName(AccountConfig.MANAGER_DEFAULT_ROLE_NAME);
            roleManager.setPermissionIdentity(PermissionConfig.MASTER);
            roleManager.setCreateTime(System.currentTimeMillis());
            int roleManagerId = roleService.save(roleManager);
            roleManager.setId(roleManagerId);
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
            userManager.setRoleId(roleManager.getId());
            userService.save(userManager);
        }

    }
}
