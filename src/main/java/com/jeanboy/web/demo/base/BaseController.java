package com.jeanboy.web.demo.base;

import com.jeanboy.web.demo.constants.ErrorCode;
import com.jeanboy.web.demo.domain.cache.MemoryCache;
import com.jeanboy.web.demo.domain.entity.UserEntity;
import com.jeanboy.web.demo.exceptions.ServerException;
import com.jeanboy.web.demo.utils.PermissionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseController {

    protected Logger logger = LoggerFactory.getLogger(BaseController.class);

    protected UserEntity checkPermission(String token, int table, int identity) {
        UserEntity userEntity = MemoryCache.getTokenMap().get(token);
        if (userEntity == null) {
            throw new ServerException(ErrorCode.TOKEN_INVALID);
        }
        boolean hadPermission = PermissionUtil.check(userEntity.getRoleId(), table, identity);
        if (!hadPermission) {
            throw new ServerException(ErrorCode.PERMISSION_DENIED);
        }
        return userEntity;
    }
}
