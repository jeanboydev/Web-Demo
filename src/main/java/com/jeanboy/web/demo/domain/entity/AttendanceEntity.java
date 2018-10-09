package com.jeanboy.web.demo.domain.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "attendance")
public class AttendanceEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @Column(name = "start_time", length = 13, nullable = false)
    private long startTime;
    @Column(name = "end_time", length = 13, nullable = false)
    private long endTime;
    @Column(name = "create_date", length = 13, nullable = false)
    private long createDate;
    @Column(name = "attendance_type_id", nullable = false)
    private int attendanceType;
    @Column(name = "create_time", length = 13, nullable = false)
    private long createTime;
    @Column(name = "user_id", nullable = false)
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

    public int getAttendanceType() {
        return attendanceType;
    }

    public void setAttendanceType(int attendanceType) {
        this.attendanceType = attendanceType;
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
