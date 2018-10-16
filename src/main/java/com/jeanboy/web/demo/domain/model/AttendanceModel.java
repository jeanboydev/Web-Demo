package com.jeanboy.web.demo.domain.model;

public class AttendanceModel {

    private long id;
    private long startTime;
    private long endTime;
    private long createDate;
    private int attendanceTypeId;
    private String attendanceTypeName;
    private long createTime;
    private long userId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public int getAttendanceTypeId() {
        return attendanceTypeId;
    }

    public void setAttendanceTypeId(int attendanceTypeId) {
        this.attendanceTypeId = attendanceTypeId;
    }

    public String getAttendanceTypeName() {
        return attendanceTypeName;
    }

    public void setAttendanceTypeName(String attendanceTypeName) {
        this.attendanceTypeName = attendanceTypeName;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
