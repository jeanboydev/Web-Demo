package com.jeanboy.web.demo.controller;

import com.jeanboy.web.demo.base.BaseController;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UsersController extends BaseController {

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public String test(){
        return "hello world";
    }
}
