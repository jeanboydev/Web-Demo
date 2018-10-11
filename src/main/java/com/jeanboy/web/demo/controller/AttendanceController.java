package com.jeanboy.web.demo.controller;

import com.alibaba.fastjson.JSON;
import com.jeanboy.web.demo.base.BaseController;
import com.jeanboy.web.demo.config.PermissionConfig;
import com.jeanboy.web.demo.domain.entity.AttendanceEntity;
import com.jeanboy.web.demo.domain.entity.AttendanceTypeEntity;
import com.jeanboy.web.demo.domain.entity.UserEntity;
import com.jeanboy.web.demo.domain.service.AttendanceService;
import com.jeanboy.web.demo.domain.service.AttendanceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
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
        checkParam(token);
        checkParam(name);

        UserEntity onlineUser = getOnlineUser(token);
        checkPermission(onlineUser.getRoleId(), PermissionConfig.TABLE_ATTENDANCE_TYPE, PermissionConfig.IDENTITY_INSERT, true);
        AttendanceTypeEntity attendanceTypeEntity = new AttendanceTypeEntity();
        attendanceTypeEntity.setName(name);
        attendanceTypeEntity.setCreateTime(System.currentTimeMillis());
        attendanceTypeService.save(attendanceTypeEntity);
        return getResponseInfo("");
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
                           @PathVariable("id") Integer id,
                           @RequestParam("name") String name) {
        checkParam(token);
        checkParam(id);
        checkParam(name);

        UserEntity onlineUser = getOnlineUser(token);
        checkPermission(onlineUser.getRoleId(), PermissionConfig.TABLE_ATTENDANCE_TYPE, PermissionConfig.IDENTITY_UPDATE, true);
        AttendanceTypeEntity attendanceTypeEntity = attendanceTypeService.get(id);
        attendanceTypeEntity.setName(name);
        attendanceTypeService.update(attendanceTypeEntity);
        return getResponseInfo("");
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
                          @PathVariable(value = "id", required = false) Integer id) {
        checkParam(token);

        UserEntity onlineUser = getOnlineUser(token);
        checkPermission(onlineUser.getRoleId(), PermissionConfig.TABLE_ATTENDANCE_TYPE, PermissionConfig.IDENTITY_SELECT, false);
        if (id == null || id == 0) {
            List<AttendanceTypeEntity> attendanceTypeList = attendanceTypeService.getAll();
            return getResponseInfo(JSON.toJSONString(attendanceTypeList));
        } else {
            AttendanceTypeEntity attendanceTypeEntity = attendanceTypeService.get(id);
            return getResponseInfo(JSON.toJSONString(attendanceTypeEntity));
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
    @RequestMapping(value = "/type/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteType(@RequestHeader("token") String token,
                             @PathVariable("id") Integer id) {
        checkParam(token);
        checkParam(id);

        UserEntity onlineUser = getOnlineUser(token);
        checkPermission(onlineUser.getRoleId(), PermissionConfig.TABLE_ATTENDANCE_TYPE, PermissionConfig.IDENTITY_DELETE, true);
        attendanceTypeService.delete(id);
        return getResponseInfo("");
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
                      @RequestParam("user_id") Long userId,
                      @RequestParam("start_time") Long startTime,
                      @RequestParam("end_time") Long endTime,
                      @RequestParam("create_date") Long createDate,
                      @RequestParam("attendance_type_id") Integer attendanceTypeId) {
        checkParam(token);
        checkParam(userId);
        checkParam(startTime);
        checkParam(endTime);
        checkParam(createDate);
        checkParam(attendanceTypeId);

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
        return getResponseInfo("");
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
                       @PathVariable("id") Long id,
                       @RequestParam(value = "start_time", required = false) Long startTime,
                       @RequestParam(value = "end_time", required = false) Long endTime,
                       @RequestParam(value = "create_date", required = false) Long createDate) {
        checkParam(token);
        checkParam(id);

        UserEntity onlineUser = getOnlineUser(token);
        checkPermission(onlineUser.getRoleId(), PermissionConfig.TABLE_ATTENDANCE, PermissionConfig.IDENTITY_UPDATE, true);
        AttendanceEntity attendanceEntity = attendanceService.get(id);
        if (startTime != null && startTime != 0) {
            attendanceEntity.setStartTime(startTime);
        }
        if (endTime != null && endTime != 0) {
            attendanceEntity.setEndTime(endTime);
        }
        if (createDate != null && createDate != 0) {
            attendanceEntity.setCreateDate(createDate);
        }
        attendanceEntity.setCreateTime(System.currentTimeMillis());
        attendanceService.save(attendanceEntity);
        return getResponseInfo("");
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
                      @PathVariable(value = "id", required = false) Long id) {
        checkParam(token);

        UserEntity onlineUser = getOnlineUser(token);
        checkPermission(onlineUser.getRoleId(), PermissionConfig.TABLE_ATTENDANCE, PermissionConfig.IDENTITY_SELECT, true);
        if (id == null || id == 0) {
            List<AttendanceEntity> attendanceList = attendanceService.getAll();
            return getResponseInfo(JSON.toJSONString(attendanceList));
        } else {
            AttendanceEntity attendanceEntity = attendanceService.get(id);
            return getResponseInfo(JSON.toJSONString(attendanceEntity));
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
                         @PathVariable("id") Long id) {
        checkParam(token);
        checkParam(id);

        UserEntity onlineUser = getOnlineUser(token);
        checkPermission(onlineUser.getRoleId(), PermissionConfig.TABLE_ATTENDANCE, PermissionConfig.IDENTITY_DELETE, true);
        attendanceService.delete(id);
        return getResponseInfo("");
    }
}
