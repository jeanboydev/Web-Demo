package com.jeanboy.web.demo.controller;

import com.alibaba.fastjson.JSON;
import com.jeanboy.web.demo.base.BaseController;
import com.jeanboy.web.demo.config.PermissionConfig;
import com.jeanboy.web.demo.domain.entity.SalaryEntity;
import com.jeanboy.web.demo.domain.entity.UserEntity;
import com.jeanboy.web.demo.domain.service.SalaryService;
import com.jeanboy.web.demo.exceptions.ServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/salary", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class SalaryController extends BaseController {

    private final SalaryService salaryService;

    @Autowired
    public SalaryController(SalaryService salaryService) {
        this.salaryService = salaryService;
    }

    @RequestMapping
    public String index() {
        throw new ServerException(HttpStatus.METHOD_NOT_ALLOWED);
    }


    /**
     * 增加薪资信息
     * /salary
     * PUT
     *
     * @param token
     * @param monthlyValue
     * @param jobId
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public String put(@RequestHeader("token") String token,
                      @RequestParam("job_id") Integer jobId,
                      @RequestParam("monthly_value") Long monthlyValue) {
        checkParam(token);
        checkParam(jobId);
        checkParam(monthlyValue);

        UserEntity onlineUser = getOnlineUser(token);
        checkPermission(onlineUser.getRoleId(), PermissionConfig.TABLE_SALARY, PermissionConfig.IDENTITY_INSERT, true);
        SalaryEntity salaryEntity = new SalaryEntity();
        salaryEntity.setJobId(jobId);
        salaryEntity.setMonthlyValue(monthlyValue);
        salaryEntity.setCreateTime(System.currentTimeMillis());
        salaryService.save(salaryEntity);
        return "";
    }

    /**
     * 更新薪资信息
     * /salary
     * POST
     *
     * @param token
     * @param id
     * @param monthlyValue
     * @param jobId
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    @ResponseBody
    public String post(@RequestHeader("token") String token,
                       @PathVariable("id") Integer id,
                       @RequestParam("job_id") Integer jobId,
                       @RequestParam("monthly_value") Long monthlyValue) {
        checkParam(token);
        checkParam(jobId);
        checkParam(monthlyValue);

        UserEntity onlineUser = getOnlineUser(token);
        checkPermission(onlineUser.getRoleId(), PermissionConfig.TABLE_SALARY, PermissionConfig.IDENTITY_UPDATE, true);
        SalaryEntity salaryEntity = salaryService.get(id);
        salaryEntity.setJobId(jobId);
        salaryEntity.setMonthlyValue(monthlyValue);
        salaryEntity.setCreateTime(System.currentTimeMillis());
        salaryService.update(salaryEntity);
        return "";
    }

    /**
     * 获取薪资信息
     * /salary
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
            checkPermission(onlineUser.getRoleId(), PermissionConfig.TABLE_SALARY, PermissionConfig.IDENTITY_SELECT, true);
            List<SalaryEntity> salaryList = salaryService.getAll();
            return JSON.toJSONString(salaryList);
        } else {
            checkPermission(onlineUser.getRoleId(), PermissionConfig.TABLE_SALARY, PermissionConfig.IDENTITY_SELECT, false);
            SalaryEntity salaryEntity = salaryService.get(id);
            return JSON.toJSONString(salaryEntity);
        }
    }

    /**
     * 删除薪资信息
     * /salary
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
        checkPermission(onlineUser.getRoleId(), PermissionConfig.TABLE_SALARY, PermissionConfig.IDENTITY_DELETE, true);
        salaryService.delete(id);
        return "";
    }
}
