var currentToken = "";
$(function () {
    currentToken = getParam("token");

    $("#leftNav").html("<div class='nav flex-column nav-pills'>" +
        "<a class='nav-link' href='/console?tab=1&token=" + currentToken + "'>首页</a>" +
        "<a class='nav-link' href='/console/auth?tab=1&token=" + currentToken + "'>权限管理</a>" +
        "<a class='nav-link active' href='/console/tab=1&profile?token=" + currentToken + "'>人事管理</a>" +
        "<a class='nav-link' href='/console/salary?tab=1&token=" + currentToken + "'>工资管理</a>" +
        "<a class='nav-link' href='/console/record?tab=1&token=" + currentToken + "'>考勤管理</a></div>");

    $("#actionModal").on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget);
        var tab = button.data('tab');
        var action = button.data('action');

        var title = "";
        var body = "<div id='toast'></div>";
        var footer = getFormFooterButton(tab, action);
        if (action === 0) {//新建
            title = "新建";

            if (tab === 1) {
                body += getFormGroup("用户名", "username", "");
                body += getFormGroup("密码", "password", "");
                body += getFormGroup("角色ID", "roleId", "");
            } else if (tab === 2) {
                body += getFormGroup("用户ID", "userId", "");
                body += getFormGroup("真实姓名", "realName", "");
                body += getFormGroup("性别", "gender", "");
                body += getFormGroup("出生日期", "birthday", "");
                body += getFormGroup("教育程度", "educationLevel", "");
                body += getFormGroup("职位ID", "job", "");
                body += getFormGroup("部门ID", "department", "");
            } else if (tab === 3) {
                body += getFormGroup("部门名称", "name", "");
            } else if (tab === 4) {
                body += getFormGroup("职位名称", "name", "");
            }
        } else if (action === 1) {//编辑
            var id = button.data('id');
            title = "编辑";
            body += getFormIDGroup(id);

            if (tab === 1) {
                var username = button.data('username');
                var password = button.data('password');
                var roleId = button.data('roleid');
                body += getFormGroup("用户名", "username", username);
                body += getFormGroup("密码", "password", password);
                body += getFormGroup("角色ID", "roleId", roleId);
            } else if (tab === 2) {
                var userId = button.data('userid');
                var realName = button.data('realname');
                var gender = button.data('gender');
                var birthday = button.data('birthday');
                var educationLevel = button.data('educationlevel');
                var jobId = button.data('jobid');
                var departmentId = button.data('departmentid');
                body += getFormGroup("用户ID", "userId", userId);
                body += getFormGroup("真实姓名", "realName", realName);
                body += getFormGroup("性别", "gender", gender);
                body += getFormGroup("出生日期", "birthday", birthday);
                body += getFormGroup("教育程度", "educationLevel", educationLevel);
                body += getFormGroup("职位", "job", jobId);
                body += getFormGroup("部门", "department", departmentId);
            } else if (tab === 3) {
                var name = button.data('name');
                body += getFormGroup("部门名称", "name", name);
            } else if (tab === 4) {
                var name = button.data('name');
                body += getFormGroup("职位名称", "name", name);
            }
        } else if (action === 2) {//删除
            title = "提示";
            var id = button.data('id');
            body += getDeleteContent(id);
        }
        var modal = $(this);
        modal.find(".modal-title").text(title);
        modal.find(".modal-body").html(body);
        modal.find(".modal-footer").html(footer);
    });
});

function onTabClick(tab) {
    window.location.href = host + "/console/profile?tab=" + tab + "&token=" + currentToken;
}

function onConfirmClick(tab, action) {
    if (tab === 1) {
        if (action === 0) {//新建
            toSubmitUserCreate(currentToken,
                $("#username").val(),
                $("#password").val(),
                $("#roleId").val());
        } else if (action === 1) {//编辑
            toSubmitUserUpdate(currentToken,
                $("#idMark").val(),
                $("#username").val(),
                $("#password").val(),
                $("#roleId").val());
        } else if (action === 2) {//删除
            toSubmitUserDelete(currentToken, $("#idMark").val());
        }
    } else if (tab === 2) {
        if (action === 0) {//新建
            toSubmitUserInfoCreate(currentToken,
                $("#userId").val(),
                $("#realName").val(),
                $("#gender").val(),
                $("#birthday").val(),
                $("#educationLevel").val(),
                $("#job").val(),
                $("#department").val());
        } else if (action === 1) {//编辑
            toSubmitUserInfoUpdate(currentToken,
                $("#idMark").val(),
                $("#userId").val(),
                $("#realName").val(),
                $("#gender").val(),
                $("#birthday").val(),
                $("#educationLevel").val(),
                $("#job").val(),
                $("#department").val());
        } else if (action === 2) {//删除
            toSubmitUserInfoDelete(currentToken, $("#idMark").val());
        }
    } else if (tab === 3) {
        if (action === 0) {//新建
            toSubmitDepartmentCreate(currentToken, $("#name").val());
        } else if (action === 1) {//编辑
            toSubmitDepartmentUpdate(currentToken, $("#idMark").val(), $("#name").val());
        } else if (action === 2) {//删除
            toSubmitDepartmentDelete(currentToken, $("#idMark").val());
        }
    } else if (tab === 4) {
        if (action === 0) {//新建
            toSubmitJobCreate(currentToken, $("#name").val());
        } else if (action === 1) {//编辑
            toSubmitJobUpdate(currentToken, $("#idMark").val(), $("#name").val());
        } else if (action === 2) {//删除
            toSubmitJobDelete(currentToken, $("#idMark").val());
        }
    }
}


function toSubmitUserCreate(token, username, password, roleId) {
    if (username === "") {
        showToast("用户名", "不能为空！");
        return;
    }
    if (password === "") {
        showToast("密码", "不能为空！");
        return;
    }
    if (roleId === "") {
        showToast("角色ID", "不能为空！");
        return;
    }
    $.ajax(host + "/users", {
        headers: {
            token: decodeURIComponent(token)
        },
        method: "put",
        dataType: "json",
        data: {
            username: username,
            password: password,
            role_id: parseInt(roleId)
        },
        success: function (data) {
            window.location.reload();
        },
        error: function (error) {
            if (error.responseJSON) {
                var info = error.responseJSON.errors[0];
                showToast("错误：", info.description);
            }
        }
    });
}

function toSubmitUserUpdate(token, userId, username, password, roleId) {
    if (userId === "") {
        showToast("用户ID", "不能为空！");
        return;
    }
    if (username === "") {
        showToast("用户名", "不能为空！");
        return;
    }
    if (password === "") {
        showToast("密码", "不能为空！");
        return;
    }
    if (roleId === "") {
        showToast("角色ID", "不能为空！");
        return;
    }
    $.ajax(host + "/users/" + userId, {
        headers: {
            token: decodeURIComponent(token)
        },
        method: "post",
        dataType: "json",
        data: {
            username: username,
            password: password,
            role_id: parseInt(roleId)
        },
        success: function (data) {
            window.location.reload();
        },
        error: function (error) {
            if (error.responseJSON) {
                var info = error.responseJSON.errors[0];
                showToast("错误：", info.description);
            }
        }
    });
}

function toSubmitUserDelete(token, userInfoId) {
    $.ajax(host + "/users/" + userInfoId, {
        headers: {
            token: decodeURIComponent(token)
        },
        method: "delete",
        dataType: "json",
        data: {},
        success: function (data) {
            window.location.reload();
        },
        error: function (error) {
            if (error.responseJSON) {
                var info = error.responseJSON.errors[0];
                showToast("错误：", info.description);
            }
        }
    });
}

function toSubmitUserInfoCreate(token, userId, realName, gender, birthday, educationLevel, jobId, departmentId) {
    if (userId === "") {
        showToast("用户ID", "不能为空！");
        return;
    }
    if (realName === "") {
        showToast("真实姓名", "不能为空！");
        return;
    }
    if (gender === "") {
        showToast("性别", "不能为空！");
        return;
    }
    if (birthday === "") {
        showToast("出生日期", "不能为空！");
        return;
    }
    if (educationLevel === "") {
        showToast("教育程度", "不能为空！");
        return;
    }
    if (jobId === "") {
        showToast("职位ID", "不能为空！");
        return;
    }
    if (departmentId === "") {
        showToast("部门ID", "不能为空！");
        return;
    }
    $.ajax(host + "/users/info", {
        headers: {
            token: decodeURIComponent(token)
        },
        method: "put",
        dataType: "json",
        data: {
            id: parseInt(userId),
            real_name: realName,
            gender: gender,
            birthday: parseInt(birthday),
            education_level: parseInt(educationLevel),
            job_id: parseInt(jobId),
            department_id: parseInt(departmentId)
        },
        success: function (data) {
            window.location.reload();
        },
        error: function (error) {
            if (error.responseJSON) {
                var info = error.responseJSON.errors[0];
                showToast("错误：", info.description);
            }
        }
    });
}

function toSubmitUserInfoUpdate(token, userInfoId, userId, realName, gender, birthday, educationLevel, jobId, departmentId) {
    if (userId === "") {
        showToast("用户ID", "不能为空！");
        return;
    }
    if (realName === "") {
        showToast("真实姓名", "不能为空！");
        return;
    }
    if (gender === "") {
        showToast("性别", "不能为空！");
        return;
    }
    if (birthday === "") {
        showToast("出生日期", "不能为空！");
        return;
    }
    if (educationLevel === "") {
        showToast("教育程度", "不能为空！");
        return;
    }
    if (jobId === "") {
        showToast("职位ID", "不能为空！");
        return;
    }
    if (departmentId === "") {
        showToast("部门ID", "不能为空！");
        return;
    }
    $.ajax(host + "/users/info/" + userInfoId, {
        headers: {
            token: decodeURIComponent(token)
        },
        method: "post",
        dataType: "json",
        data: {
            id: parseInt(userId),
            real_name: realName,
            gender: gender,
            birthday: parseInt(birthday),
            education_level: parseInt(educationLevel),
            job_id: parseInt(jobId),
            department_id: parseInt(departmentId)
        },
        success: function (data) {
            window.location.reload();
        },
        error: function (error) {
            if (error.responseJSON) {
                var info = error.responseJSON.errors[0];
                showToast("错误：", info.description);
            }
        }
    });
}

function toSubmitUserInfoDelete(token, userInfoId) {
    $.ajax(host + "/users/info/" + userInfoId, {
        headers: {
            token: decodeURIComponent(token)
        },
        method: "delete",
        dataType: "json",
        data: {},
        success: function (data) {
            window.location.reload();
        },
        error: function (error) {
            if (error.responseJSON) {
                var info = error.responseJSON.errors[0];
                showToast("错误：", info.description);
            }
        }
    });
}

function toSubmitDepartmentCreate(token, name) {
    if (name === "") {
        showToast("部门名称", "不能为空！");
        return;
    }
    $.ajax(host + "/department", {
        headers: {
            token: decodeURIComponent(token)
        },
        method: "put",
        dataType: "json",
        data: {
            name: name
        },
        success: function (data) {
            window.location.reload();
        },
        error: function (error) {
            if (error.responseJSON) {
                var info = error.responseJSON.errors[0];
                showToast("错误：", info.description);
            }
        }
    });
}

function toSubmitDepartmentUpdate(token, departmentId, name) {
    if (name === "") {
        showToast("部门名称", "不能为空！");
        return;
    }
    $.ajax(host + "/department/" + departmentId, {
        headers: {
            token: decodeURIComponent(token)
        },
        method: "post",
        dataType: "json",
        data: {
            name: name
        },
        success: function (data) {
            window.location.reload();
        },
        error: function (error) {
            if (error.responseJSON) {
                var info = error.responseJSON.errors[0];
                showToast("错误：", info.description);
            }
        }
    });
}

function toSubmitDepartmentDelete(token, departmentId) {
    $.ajax(host + "/department/" + departmentId, {
        headers: {
            token: decodeURIComponent(token)
        },
        method: "delete",
        dataType: "json",
        data: {},
        success: function (data) {
            window.location.reload();
        },
        error: function (error) {
            if (error.responseJSON) {
                var info = error.responseJSON.errors[0];
                showToast("错误：", info.description);
            }
        }
    });
}

function toSubmitJobCreate(token, name) {
    if (name === "") {
        showToast("职位名称", "不能为空！");
        return;
    }
    $.ajax(host + "/job", {
        headers: {
            token: decodeURIComponent(token)
        },
        method: "put",
        dataType: "json",
        data: {
            name: name
        },
        success: function (data) {
            window.location.reload();
        },
        error: function (error) {
            if (error.responseJSON) {
                var info = error.responseJSON.errors[0];
                showToast("错误：", info.description);
            }
        }
    });
}

function toSubmitJobUpdate(token, jobId, name) {
    if (name === "") {
        showToast("职位名称", "不能为空！");
        return;
    }
    $.ajax(host + "/job/" + jobId, {
        headers: {
            token: decodeURIComponent(token)
        },
        method: "post",
        dataType: "json",
        data: {
            name: name
        },
        success: function (data) {
            window.location.reload();
        },
        error: function (error) {
            if (error.responseJSON) {
                var info = error.responseJSON.errors[0];
                showToast("错误：", info.description);
            }
        }
    });
}

function toSubmitJobDelete(token, jobId) {
    $.ajax(host + "/job/" + jobId, {
        headers: {
            token: decodeURIComponent(token)
        },
        method: "delete",
        dataType: "json",
        data: {},
        success: function (data) {
            window.location.reload();
        },
        error: function (error) {
            if (error.responseJSON) {
                var info = error.responseJSON.errors[0];
                showToast("错误：", info.description);
            }
        }
    });
}