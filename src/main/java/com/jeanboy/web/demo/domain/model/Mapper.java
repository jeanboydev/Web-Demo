package com.jeanboy.web.demo.domain.model;

import com.jeanboy.web.demo.constants.EducationLevel;
import com.jeanboy.web.demo.constants.Gender;
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

    public static UserModel transform(UserEntity userEntity, RoleModel roleModel) {
        if (userEntity == null) return null;
        UserModel userModel = new UserModel();
        userModel.setId(userEntity.getId());
        userModel.setUsername(userEntity.getUsername());
        userModel.setCreateTime(userEntity.getCreateTime());
        userModel.setRole(roleModel);
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

    public static UserInfoModel transform(UserInfoEntity userInfoEntity, JobModel jobModel, DepartmentModel departmentModel) {
        if (userInfoEntity == null) return null;
        UserInfoModel userInfoModel = new UserInfoModel();
        userInfoModel.setId(userInfoEntity.getId());
        userInfoModel.setRealName(userInfoEntity.getRealName());
        Gender gender = Gender.UNKNOWN;
        if (userInfoEntity.getGender() == Gender.FEMALE.getValue()) {
            gender = Gender.FEMALE;
        } else if (userInfoEntity.getGender() == Gender.MALE.getValue()) {
            gender = Gender.MALE;
        }
        userInfoModel.setGender(new EnumModel(gender.getValue(), gender.getName()));
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
        userInfoModel.setEducationLevel(new EnumModel(educationLevel.getValue(), educationLevel.getName()));
        userInfoModel.setImportTime(userInfoEntity.getImportTime());
        userInfoModel.setUpdateTime(userInfoEntity.getUpdateTime());
        userInfoModel.setCreateTime(userInfoEntity.getCreateTime());
        userInfoModel.setJob(jobModel);
        userInfoModel.setDepartment(departmentModel);
        return userInfoModel;
    }
}
