package com.jeanboy.web.demo.controller;

import com.alibaba.fastjson.JSON;
import com.jeanboy.web.demo.base.BaseController;
import com.jeanboy.web.demo.config.PermissionConfig;
import com.jeanboy.web.demo.constants.ErrorCode;
import com.jeanboy.web.demo.domain.entity.AttendanceEntity;
import com.jeanboy.web.demo.domain.entity.AttendanceTypeEntity;
import com.jeanboy.web.demo.domain.entity.UserEntity;
import com.jeanboy.web.demo.domain.service.AttendanceService;
import com.jeanboy.web.demo.domain.service.AttendanceTypeService;
import com.jeanboy.web.demo.exceptions.ServerException;
import com.jeanboy.web.demo.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/attendance", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class AttendanceController extends BaseController {

    private final AttendanceService attendanceService;
    private final AttendanceTypeService attendanceTypeService;

    @Autowired
    public AttendanceController(AttendanceService attendanceService, AttendanceTypeService attendanceTypeService) {
        this.attendanceService = attendanceService;
        this.attendanceTypeService = attendanceTypeService;
    }

    /**
     * 增加考勤类型信息
     * /attendance/type
     * PUT
     *
     * @param token
     * @param name
     * @return
     */
    @RequestMapping(value = "/type", method = RequestMethod.PUT)
    @ResponseBody
    public String putType(@RequestHeader("token") String token,
                          @RequestParam("name") String name) {
        if (StringUtil.isEmpty(token)
                || StringUtil.isEmpty(name)) {
            throw new ServerException(ErrorCode.PARAMETER_ERROR);
        }

        UserEntity onlineUser = getOnlineUser(token);
        checkPermission(onlineUser.getRoleId(), PermissionConfig.TABLE_ATTENDANCE_TYPE, PermissionConfig.IDENTITY_INSERT, true);
        AttendanceTypeEntity attendanceTypeEntity = new AttendanceTypeEntity();
        attendanceTypeEntity.setName(name);
        attendanceTypeEntity.setCreateTime(System.currentTimeMillis());
        attendanceTypeService.save(attendanceTypeEntity);
        return "";
    }

    /**
     * 更新考勤类型信息
     * /attendance/type
     * POST
     *
     * @param token
     * @param id
     * @param name
     * @return
     */
    @RequestMapping(value = "/type/{id}", method = RequestMethod.POST)
    @ResponseBody
    public String postType(@RequestHeader("token") String token,
                           @PathVariable("id") int id,
                           @RequestParam("name") String name) {
        if (StringUtil.isEmpty(token)
                || id == 0
                || StringUtil.isEmpty(name)) {
            throw new ServerException(ErrorCode.PARAMETER_ERROR);
        }

        UserEntity onlineUser = getOnlineUser(token);
        checkPermission(onlineUser.getRoleId(), PermissionConfig.TABLE_ATTENDANCE_TYPE, PermissionConfig.IDENTITY_UPDATE, true);
        AttendanceTypeEntity attendanceTypeEntity = attendanceTypeService.get(id);
        attendanceTypeEntity.setName(name);
        attendanceTypeService.update(attendanceTypeEntity);
        return "";
    }

    /**
     * 获取考勤类型信息
     * /attendance/type
     * GET
     *
     * @param token
     * @param id
     * @return
     */
    @RequestMapping(value = "/type/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String getType(@RequestHeader("token") String token,
                          @PathVariable(value = "id", required = false) int id) {
        if (StringUtil.isEmpty(token)) {
            throw new ServerException(ErrorCode.PARAMETER_ERROR);
        }
        UserEntity onlineUser = getOnlineUser(token);
        checkPermission(onlineUser.getRoleId(), PermissionConfig.TABLE_ATTENDANCE_TYPE, PermissionConfig.IDENTITY_SELECT, true);
        if (id == 0) {
            List<AttendanceTypeEntity> attendanceTypeList = attendanceTypeService.getAll();
            return JSON.toJSONString(attendanceTypeList);
        } else {
            AttendanceTypeEntity attendanceTypeEntity = attendanceTypeService.get(id);
            return JSON.toJSONString(attendanceTypeEntity);
        }
    }

    /**
     * 删除考勤类型信息
     * /attendance/type
     * DELETE
     *
     * @param token
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteType(@RequestHeader("token") String token,
                             @PathVariable("id") int id) {
        if (StringUtil.isEmpty(token)
                || id == 0) {
            throw new ServerException(ErrorCode.PARAMETER_ERROR);
        }

        UserEntity onlineUser = getOnlineUser(token);
        checkPermission(onlineUser.getRoleId(), PermissionConfig.TABLE_ATTENDANCE_TYPE, PermissionConfig.IDENTITY_DELETE, true);
        attendanceTypeService.delete(id);
        return "";
    }

    /**
     * 增加考勤信息
     * /attendance
     * PUT
     *
     * @param token
     * @param userId
     * @param startTime
     * @param endTime
     * @param createDate
     * @param attendanceTypeId
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public String put(@RequestHeader("token") String token,
                      @RequestParam("user_id") long userId,
                      @RequestParam("start_time") long startTime,
                      @RequestParam("end_time") long endTime,
                      @RequestParam("create_date") long createDate,
                      @RequestParam("attendance_type_id") int attendanceTypeId) {
        if (StringUtil.isEmpty(token)
                || userId == 0
                || startTime == 0
                || endTime == 0
                || createDate == 0
                || attendanceTypeId == 0) {
            throw new ServerException(ErrorCode.PARAMETER_ERROR);
        }

        UserEntity onlineUser = getOnlineUser(token);
        checkPermission(onlineUser.getRoleId(), PermissionConfig.TABLE_ATTENDANCE, PermissionConfig.IDENTITY_INSERT, true);
        AttendanceEntity attendanceEntity = new AttendanceEntity();
        attendanceEntity.setUserId(userId);
        attendanceEntity.setStartTime(startTime);
        attendanceEntity.setEndTime(endTime);
        attendanceEntity.setCreateDate(createDate);
        attendanceEntity.setAttendanceType(attendanceTypeId);
        attendanceEntity.setCreateTime(System.currentTimeMillis());
        attendanceService.save(attendanceEntity);
        return "";
    }

    /**
     * 更新考勤信息
     * /attendance
     * POST
     *
     * @param token
     * @param id
     * @param startTime
     * @param endTime
     * @param createDate
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    @ResponseBody
    public String post(@RequestHeader("token") String token,
                       @PathVariable("id") long id,
                       @RequestParam("start_time") long startTime,
                       @RequestParam("end_time") long endTime,
                       @RequestParam("create_date") long createDate) {
        if (StringUtil.isEmpty(token)
                || id == 0) {
            throw new ServerException(ErrorCode.PARAMETER_ERROR);
        }

        UserEntity onlineUser = getOnlineUser(token);
        checkPermission(onlineUser.getRoleId(), PermissionConfig.TABLE_ATTENDANCE, PermissionConfig.IDENTITY_UPDATE, true);
        AttendanceEntity attendanceEntity = attendanceService.get(id);
        if (startTime != 0) {
            attendanceEntity.setStartTime(startTime);
        }
        if (endTime != 0) {
            attendanceEntity.setEndTime(endTime);
        }
        if (createDate != 0) {
            attendanceEntity.setCreateDate(createDate);
        }
        attendanceEntity.setCreateTime(System.currentTimeMillis());
        attendanceService.save(attendanceEntity);
        return "";
    }

    /**
     * 获取考勤信息
     * /attendance
     * GET
     *
     * @param token
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String get(@RequestHeader("token") String token,
                      @PathVariable(value = "id", required = false) long id) {
        if (StringUtil.isEmpty(token)) {
            throw new ServerException(ErrorCode.PARAMETER_ERROR);
        }
        UserEntity onlineUser = getOnlineUser(token);
        checkPermission(onlineUser.getRoleId(), PermissionConfig.TABLE_ATTENDANCE, PermissionConfig.IDENTITY_SELECT, true);
        if (id == 0) {
            List<AttendanceEntity> attendanceList = attendanceService.getAll();
            return JSON.toJSONString(attendanceList);
        } else {
            AttendanceEntity attendanceEntity = attendanceService.get(id);
            return JSON.toJSONString(attendanceEntity);
        }
    }

    /**
     * 删除考勤类型信息
     * /attendance
     * DELETE
     *
     * @param token
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public String delete(@RequestHeader("token") String token,
                         @PathVariable("id") long id) {
        if (StringUtil.isEmpty(token)
                || id == 0) {
            throw new ServerException(ErrorCode.PARAMETER_ERROR);
        }

        UserEntity onlineUser = getOnlineUser(token);
        checkPermission(onlineUser.getRoleId(), PermissionConfig.TABLE_ATTENDANCE, PermissionConfig.IDENTITY_DELETE, true);
        attendanceService.delete(id);
        return "";
    }
}
