package com.jeanboy.web.demo.controller;

import com.alibaba.fastjson.JSON;
import com.jeanboy.web.demo.base.BaseController;
import com.jeanboy.web.demo.constants.ErrorCode;
import com.jeanboy.web.demo.domain.cache.MemoryCache;
import com.jeanboy.web.demo.domain.entity.UserEntity;
import com.jeanboy.web.demo.domain.model.TokenModel;
import com.jeanboy.web.demo.domain.service.UserService;
import com.jeanboy.web.demo.exceptions.ServerException;
import com.jeanboy.web.demo.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value = "/token", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TokenController extends BaseController {


    private final UserService userService;

    @Autowired
    public TokenController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping
    public String index() {
        throw new ServerException(HttpStatus.METHOD_NOT_ALLOWED);
    }

    /**
     * 登录
     * /users
     * POST
     *
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public String signIn(@RequestParam("username") String username,
                         @RequestParam("password") String password) {
        checkParam(username);
        checkParam(password);

        List<UserEntity> userList = userService.findByUsername(username);
        if (userList.isEmpty()) {
            throw new ServerException(ErrorCode.ACCOUNT_NOT_FOUND);
        }
        UserEntity userEntity = userList.get(0);
        String cachePassword = userEntity.getPassword();
        if (!password.toUpperCase().equals(cachePassword)) {
            throw new ServerException(ErrorCode.PASSWORD_ERROR);
        }
        TokenModel tokenModel = TokenUtil.getToken(userEntity.getId());
        if (tokenModel == null) {
            throw new ServerException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        MemoryCache.putTokenModel(tokenModel);
        MemoryCache.putUserEntity(tokenModel.getToken(), userEntity);
        return JSON.toJSONString(tokenModel);
    }
}
