package com.jeanboy.web.demo.component;

import com.jeanboy.web.demo.config.PermissionConfig;
import com.jeanboy.web.demo.domain.entity.PermissionEntity;
import com.jeanboy.web.demo.domain.service.PermissionService;
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

    @Autowired
    public ServerInitializeComponent(PermissionService permissionService) {
        this.permissionService = permissionService;
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
    }
}
