package com.jeanboy.web.demo.controller;

import com.jeanboy.web.demo.base.BaseController;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = {"", "/", "/index"}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ViewController extends BaseController {

    @RequestMapping
    public String index() {
        System.out.println("=========ViewController===========");
        return "sign_in";
    }
}
