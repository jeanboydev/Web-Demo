package com.jeanboy.web.demo.controller;

import com.alibaba.fastjson.JSON;
import com.jeanboy.web.demo.base.BaseController;
import com.jeanboy.web.demo.domain.model.UserModel;
import com.jeanboy.web.demo.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UsersController extends BaseController {

    private final UserService userService;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }


    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public String test() {
        UserModel userModel = new UserModel();
        userModel.setUsername("test");
        userModel.setPassword("123");
        userModel.setNickname("测试昵称");
        userModel.setUpdateTime(System.currentTimeMillis());
        userService.save(userModel);
        return "hello world";
    }

    @RequestMapping(value = "get", method = RequestMethod.GET)
    @ResponseBody
    public String test2() {
        UserModel userModel = userService.get(1L);
        return JSON.toJSONString(userModel);
    }
}
