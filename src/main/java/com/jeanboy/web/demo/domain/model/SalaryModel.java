package com.jeanboy.web.demo.domain.model;

public class SalaryModel {

    private int id;
    private long monthlyValue;
    private long createTime;
    private int jobId;
    private String jobName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }
}
