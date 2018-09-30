package com.jeanboy.web.demo.controller;

import com.alibaba.fastjson.JSON;
import com.jeanboy.web.demo.base.BaseController;
import com.jeanboy.web.demo.config.PermissionConfig;
import com.jeanboy.web.demo.constants.ErrorCode;
import com.jeanboy.web.demo.domain.entity.JobEntity;
import com.jeanboy.web.demo.domain.entity.UserEntity;
import com.jeanboy.web.demo.domain.service.JobService;
import com.jeanboy.web.demo.exceptions.ServerException;
import com.jeanboy.web.demo.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/job", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class JobController extends BaseController {

    private final JobService jobService;

    @Autowired
    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @RequestMapping
    public String index() {
        throw new ServerException(HttpStatus.METHOD_NOT_ALLOWED);
    }


    /**
     * 增加职位信息
     * /job
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
        if (StringUtil.isEmpty(token)
                || StringUtil.isEmpty(name)) {
            throw new ServerException(ErrorCode.PARAMETER_ERROR);
        }

        UserEntity onlineUser = getOnlineUser(token);
        checkPermission(onlineUser.getRoleId(), PermissionConfig.TABLE_JOB, PermissionConfig.IDENTITY_INSERT, true);
        JobEntity jobEntity = new JobEntity();
        jobEntity.setName(name);
        jobEntity.setCreateTime(System.currentTimeMillis());
        jobService.save(jobEntity);
        return "";
    }

    /**
     * 更新职位信息
     * /job
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
                       @PathVariable("id") int id,
                       @RequestParam("name") String name) {
        if (StringUtil.isEmpty(token)
                || id == 0
                || StringUtil.isEmpty(name)) {
            throw new ServerException(ErrorCode.PARAMETER_ERROR);
        }

        UserEntity onlineUser = getOnlineUser(token);
        checkPermission(onlineUser.getRoleId(), PermissionConfig.TABLE_JOB, PermissionConfig.IDENTITY_UPDATE, true);
        JobEntity jobEntity = jobService.get(id);
        jobEntity.setName(name);
        jobEntity.setCreateTime(System.currentTimeMillis());
        jobService.update(jobEntity);
        return "";
    }

    /**
     * 获取职位信息
     * /job
     * GET
     *
     * @param token
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String get(@RequestHeader("token") String token,
                      @PathVariable(value = "id", required = false) int id) {
        if (StringUtil.isEmpty(token)) {
            throw new ServerException(ErrorCode.PARAMETER_ERROR);
        }

        UserEntity onlineUser = getOnlineUser(token);
        checkPermission(onlineUser.getRoleId(), PermissionConfig.TABLE_JOB, PermissionConfig.IDENTITY_SELECT, true);
        if (id == 0) {
            List<JobEntity> roleList = jobService.getAll();
            return JSON.toJSONString(roleList);
        } else {
            JobEntity jobEntity = jobService.get(id);
            return JSON.toJSONString(jobEntity);
        }
    }

    /**
     * 删除职位信息
     * /job
     * DELETE
     *
     * @param token
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public String delete(@RequestHeader("token") String token,
                         @PathVariable("id") int id) {
        if (StringUtil.isEmpty(token)
                || id == 0) {
            throw new ServerException(ErrorCode.PARAMETER_ERROR);
        }

        UserEntity onlineUser = getOnlineUser(token);
        checkPermission(onlineUser.getRoleId(), PermissionConfig.TABLE_JOB, PermissionConfig.IDENTITY_DELETE, true);
        jobService.delete(id);
        return "";
    }
}
