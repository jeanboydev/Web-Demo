package com.jeanboy.web.demo.controller;

import com.jeanboy.web.demo.base.BaseController;
import com.jeanboy.web.demo.config.PermissionConfig;
import com.jeanboy.web.demo.domain.entity.JobEntity;
import com.jeanboy.web.demo.domain.entity.UserEntity;
import com.jeanboy.web.demo.domain.service.JobService;
import com.jeanboy.web.demo.exceptions.ServerException;
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
        checkParam(token);
        checkParam(name);

        UserEntity onlineUser = getOnlineUser(token);
        checkPermission(onlineUser.getRoleId(), PermissionConfig.TABLE_JOB, PermissionConfig.IDENTITY_INSERT, true);
        JobEntity jobEntity = new JobEntity();
        jobEntity.setName(name);
        jobEntity.setCreateTime(System.currentTimeMillis());
        jobService.save(jobEntity);
        return getResponseInfo("");
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
                       @PathVariable("id") Integer id,
                       @RequestParam("name") String name) {
        checkParam(token);
        checkParam(id);
        checkParam(name);

        UserEntity onlineUser = getOnlineUser(token);
        checkPermission(onlineUser.getRoleId(), PermissionConfig.TABLE_JOB, PermissionConfig.IDENTITY_UPDATE, true);
        JobEntity jobEntity = jobService.get(id);
        jobEntity.setName(name);
        jobEntity.setCreateTime(System.currentTimeMillis());
        jobService.update(jobEntity);
        return getResponseInfo("");
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
                      @PathVariable(value = "id", required = false) Integer id) {
        checkParam(token);

        UserEntity onlineUser = getOnlineUser(token);
        if (id == null || id == 0) {
            checkPermission(onlineUser.getRoleId(), PermissionConfig.TABLE_JOB, PermissionConfig.IDENTITY_SELECT, true);
            List<JobEntity> roleList = jobService.getAll();
            return getResponseInfo(roleList);
        } else {
            checkPermission(onlineUser.getRoleId(), PermissionConfig.TABLE_JOB, PermissionConfig.IDENTITY_SELECT, false);
            JobEntity jobEntity = jobService.get(id);
            return getResponseInfo(jobEntity);
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
                         @PathVariable("id") Integer id) {
        checkParam(token);
        checkParam(id);

        UserEntity onlineUser = getOnlineUser(token);
        checkPermission(onlineUser.getRoleId(), PermissionConfig.TABLE_JOB, PermissionConfig.IDENTITY_DELETE, true);
        jobService.delete(id);
        return getResponseInfo("");
    }
}
