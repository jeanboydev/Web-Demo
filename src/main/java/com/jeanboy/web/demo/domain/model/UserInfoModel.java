package com.jeanboy.web.demo.domain.model;

public class UserInfoModel {

    private long id;
    private String realName;
    private EnumModel gender;
    private long birthday;
    private EnumModel educationLevel;
    private long importTime;
    private long updateTime;
    private long createTime;
    private JobModel job;
    private DepartmentModel department;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public EnumModel getGender() {
        return gender;
    }

    public void setGender(EnumModel gender) {
        this.gender = gender;
    }

    public long getBirthday() {
        return birthday;
    }

    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }

    public EnumModel getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(EnumModel educationLevel) {
        this.educationLevel = educationLevel;
    }

    public long getImportTime() {
        return importTime;
    }

    public void setImportTime(long importTime) {
        this.importTime = importTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public JobModel getJob() {
        return job;
    }

    public void setJob(JobModel job) {
        this.job = job;
    }

    public DepartmentModel getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentModel department) {
        this.department = department;
    }
}
