package com.jeanboy.web.demo.domain.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "salary")
public class SalaryEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    @Column(name = "monthly_value", nullable = false)
    private long monthlyValue;
    @Column(name = "create_time", length = 13, nullable = false)
    private long createTime;
    @Column(name = "job_id", nullable = false)
    private int jobId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public long getMonthlyValue() {
        return monthlyValue;
    }

    public void setMonthlyValue(long monthlyValue) {
        this.monthlyValue = monthlyValue;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}
