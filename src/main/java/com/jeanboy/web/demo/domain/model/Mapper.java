package com.jeanboy.web.demo.domain.model;

import com.jeanboy.web.demo.constants.EducationLevel;
import com.jeanboy.web.demo.constants.Gender;
import com.jeanboy.web.demo.domain.cache.MemoryCache;
import com.jeanboy.web.demo.domain.entity.*;
import com.jeanboy.web.demo.domain.service.UserInfoService;

public class Mapper {

    public static RoleModel transform(RoleEntity roleEntity) {
        if (roleEntity == null) return null;
        RoleModel roleModel = new RoleModel();
        roleModel.setId(roleEntity.getId());
        roleModel.setName(roleEntity.getName());
        return roleModel;
    }

    public static UserModel transform(UserEntity userEntity) {
        if (userEntity == null) return null;
        UserModel userModel = new UserModel();
        userModel.setId(userEntity.getId());
        userModel.setRoleId(userEntity.getRoleId());
        userModel.setRoleName(MemoryCache.getRoleEntity(userEntity.getRoleId()).getName());
        userModel.setUsername(userEntity.getUsername());
        userModel.setPassword(userEntity.getPassword());
        userModel.setCreateTime(userEntity.getCreateTime());
        return userModel;
    }

    public static JobModel transform(JobEntity jobEntity) {
        if (jobEntity == null) return null;
        JobModel jobModel = new JobModel();
        jobModel.setId(jobEntity.getId());
        jobModel.setName(jobEntity.getName());
        return jobModel;
    }

    public static DepartmentModel transform(DepartmentEntity departmentEntity) {
        if (departmentEntity == null) return null;
        DepartmentModel departmentModel = new DepartmentModel();
        departmentModel.setId(departmentEntity.getId());
        departmentModel.setName(departmentEntity.getName());
        return departmentModel;
    }

    public static UserInfoModel transform(UserInfoEntity userInfoEntity) {
        if (userInfoEntity == null) return null;

        JobModel jobModel = transform(MemoryCache.getJobEntity(userInfoEntity.getJobId()));
        DepartmentModel departmentModel = transform(MemoryCache.getDepartmentEntity(userInfoEntity.getDepartmentId()));

        UserInfoModel userInfoModel = new UserInfoModel();
        userInfoModel.setId(userInfoEntity.getId());
        userInfoModel.setUserId(userInfoEntity.getUserId());
        userInfoModel.setRealName(userInfoEntity.getRealName());
        Gender gender = Gender.UNKNOWN;
        if (userInfoEntity.getGender() == Gender.FEMALE.getValue()) {
            gender = Gender.FEMALE;
        } else if (userInfoEntity.getGender() == Gender.MALE.getValue()) {
            gender = Gender.MALE;
        }
        userInfoModel.setGender(gender.getValue());
        userInfoModel.setGenderName(gender.getName());
        userInfoModel.setBirthday(userInfoEntity.getBirthday());
        EducationLevel educationLevel = EducationLevel.UNKNOWN;
        if (userInfoEntity.getEducationLevel() == EducationLevel.PRIMARY_SCHOOL.getValue()) {
            educationLevel = EducationLevel.PRIMARY_SCHOOL;
        } else if (userInfoEntity.getEducationLevel() == EducationLevel.MIDDLE_SCHOOL.getValue()) {
            educationLevel = EducationLevel.MIDDLE_SCHOOL;
        } else if (userInfoEntity.getEducationLevel() == EducationLevel.HIGH_SCHOOL.getValue()) {
            educationLevel = EducationLevel.HIGH_SCHOOL;
        } else if (userInfoEntity.getEducationLevel() == EducationLevel.JUNIOR_COLLEGE.getValue()) {
            educationLevel = EducationLevel.JUNIOR_COLLEGE;
        } else if (userInfoEntity.getEducationLevel() == EducationLevel.UNIVERSITY.getValue()) {
            educationLevel = EducationLevel.UNIVERSITY;
        } else if (userInfoEntity.getEducationLevel() == EducationLevel.UNDERGRADUATE.getValue()) {
            educationLevel = EducationLevel.UNDERGRADUATE;
        } else if (userInfoEntity.getEducationLevel() == EducationLevel.DOCTOR.getValue()) {
            educationLevel = EducationLevel.DOCTOR;
        }
        userInfoModel.setEducationLevel(educationLevel.getValue());
        userInfoModel.setEducationLevelName(educationLevel.getName());
        userInfoModel.setImportTime(userInfoEntity.getImportTime());
        userInfoModel.setUpdateTime(userInfoEntity.getUpdateTime());
        userInfoModel.setCreateTime(userInfoEntity.getCreateTime());
        userInfoModel.setJobId(userInfoEntity.getJobId());
        userInfoModel.setJobName(jobModel != null ? jobModel.getName() : "未知");
        userInfoModel.setDepartmentId(userInfoEntity.getDepartmentId());
        userInfoModel.setDepartmentName(departmentModel != null ? departmentModel.getName() : "未知");
        return userInfoModel;
    }

    public static RolePermissionModel transform(RolePermissionEntity rolePermissionEntity) {
        RolePermissionModel rolePermissionModel = new RolePermissionModel();
        rolePermissionModel.setId(rolePermissionEntity.getId());
        rolePermissionModel.setRoleId(rolePermissionEntity.getRoleId());
        rolePermissionModel.setRoleName(MemoryCache.getRoleEntity(rolePermissionEntity.getRoleId()).getName());
        rolePermissionModel.setPermissionIdentity(rolePermissionEntity.getPermissionIdentity());
        rolePermissionModel.setCreateTime(rolePermissionModel.getCreateTime());
        return rolePermissionModel;
    }

    public static SalaryModel transform(SalaryEntity salaryEntity) {
        SalaryModel salaryModel = new SalaryModel();
        salaryModel.setId(salaryEntity.getId());
        salaryModel.setJobId(salaryEntity.getJobId());
        salaryModel.setJobName(MemoryCache.getJobEntity(salaryEntity.getJobId()).getName());
        salaryModel.setMonthlyValue(salaryEntity.getMonthlyValue());
        salaryEntity.setCreateTime(salaryEntity.getCreateTime());
        return salaryModel;
    }
}
