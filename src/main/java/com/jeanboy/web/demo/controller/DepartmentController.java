package com.jeanboy.web.demo.controller;

import com.alibaba.fastjson.JSON;
import com.jeanboy.web.demo.base.BaseController;
import com.jeanboy.web.demo.config.PermissionConfig;
import com.jeanboy.web.demo.domain.entity.DepartmentEntity;
import com.jeanboy.web.demo.domain.entity.UserEntity;
import com.jeanboy.web.demo.domain.service.DepartmentService;
import com.jeanboy.web.demo.exceptions.ServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/department", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class DepartmentController extends BaseController {

    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @RequestMapping
    public String index() {
        throw new ServerException(HttpStatus.METHOD_NOT_ALLOWED);
    }


    /**
     * 增加部门信息
     * /department
     * PUT
     *
     * @param token
     * @param name
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public String put(@RequestHeader("token") String token,
                      @RequestParam("name") String name) {
        checkParam(token);
        checkParam(name);

        UserEntity onlineUser = getOnlineUser(token);
        checkPermission(onlineUser.getRoleId(), PermissionConfig.TABLE_DEPARTMENT, PermissionConfig.IDENTITY_INSERT, true);
        DepartmentEntity departmentEntity = new DepartmentEntity();
        departmentEntity.setName(name);
        departmentEntity.setCreateTime(System.currentTimeMillis());
        departmentService.save(departmentEntity);
        return getResponseInfo("");
    }

    /**
     * 更新部门信息
     * /department
     * POST
     *
     * @param token
     * @param id
     * @param name
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    @ResponseBody
    public String post(@RequestHeader("token") String token,
                       @PathVariable("id") Integer id,
                       @RequestParam("name") String name) {
        checkParam(token);
        checkParam(id);
        checkParam(name);

        UserEntity onlineUser = getOnlineUser(token);
        checkPermission(onlineUser.getRoleId(), PermissionConfig.TABLE_DEPARTMENT, PermissionConfig.IDENTITY_UPDATE, true);
        DepartmentEntity departmentEntity = departmentService.get(id);
        departmentEntity.setName(name);
        departmentEntity.setCreateTime(System.currentTimeMillis());
        departmentService.update(departmentEntity);
        return getResponseInfo("");
    }

    /**
     * 获取部门信息
     * /department
     * GET
     *
     * @param token
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String get(@RequestHeader("token") String token,
                      @PathVariable(value = "id", required = false) Integer id) {
        checkParam(token);

        UserEntity onlineUser = getOnlineUser(token);
        if (id == null || id == 0) {
            checkPermission(onlineUser.getRoleId(), PermissionConfig.TABLE_DEPARTMENT, PermissionConfig.IDENTITY_SELECT, true);
            List<DepartmentEntity> departmentList = departmentService.getAll();
            return getResponseInfo(JSON.toJSONString(departmentList));
        } else {
            checkPermission(onlineUser.getRoleId(), PermissionConfig.TABLE_DEPARTMENT, PermissionConfig.IDENTITY_SELECT, false);
            DepartmentEntity departmentEntity = departmentService.get(id);
            return getResponseInfo(JSON.toJSONString(departmentEntity));
        }
    }

    /**
     * 删除部门信息
     * /department
     * DELETE
     *
     * @param token
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public String delete(@RequestHeader("token") String token,
                         @PathVariable("id") Integer id) {
        checkParam(token);
        checkParam(id);

        UserEntity onlineUser = getOnlineUser(token);
        checkPermission(onlineUser.getRoleId(), PermissionConfig.TABLE_DEPARTMENT, PermissionConfig.IDENTITY_SELECT, true);
        departmentService.delete(id);
        return getResponseInfo("");
    }
}
