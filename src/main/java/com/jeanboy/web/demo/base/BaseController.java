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


    protected UserEntity getOnlineUser(String token) {
        UserEntity userEntity = MemoryCache.getTokenMap().get(token);
        if (userEntity == null) {
            throw new ServerException(ErrorCode.TOKEN_INVALID);
        }
        return userEntity;
    }

    protected void checkParam(Object param) {
        if (param == null) {
            throw new ServerException(ErrorCode.PARAMETER_ERROR);
        } else if (param instanceof String) {
            if (((String) param).isEmpty()) {
                throw new ServerException(ErrorCode.PARAMETER_ERROR);
            }
        } else if (param instanceof Number) {
            if (((Number) param).intValue() == 0) {
                throw new ServerException(ErrorCode.PARAMETER_ERROR);
            } else if (((Number) param).longValue() == 0) {
                throw new ServerException(ErrorCode.PARAMETER_ERROR);
            } else if (((Number) param).doubleValue() == 0) {
                throw new ServerException(ErrorCode.PARAMETER_ERROR);
            } else if (((Number) param).floatValue() == 0) {
                throw new ServerException(ErrorCode.PARAMETER_ERROR);
            }
        }
    }

    protected void checkPermission(int roleId, int table, int identity, boolean isPrivileged) {
        boolean hadPermission = PermissionUtil.check(roleId, table, identity, isPrivileged);
        if (!hadPermission) {
            throw new ServerException(ErrorCode.PERMISSION_DENIED);
        }
    }
}
