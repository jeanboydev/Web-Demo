var currentToken = "";
$(function () {
    currentToken = getParam("token");

    $("#leftNav").html("<div class='nav flex-column nav-pills'>" +
        "<a class='nav-link' href='/console?tab=1&token=" + currentToken + "'>首页</a>" +
        "<a class='nav-link' href='/console/auth?tab=1&token=" + currentToken + "'>权限管理</a>" +
        "<a class='nav-link' href='/console/profile?tab=1&token=" + currentToken + "'>人事管理</a>" +
        "<a class='nav-link' href='/console/salary?tab=1&token=" + currentToken + "'>工资管理</a>" +
        "<a class='nav-link active' href='/console/record?tab=1&token=" + currentToken + "'>考勤管理</a></div>");

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
                body += getFormGroup("考勤类型名称", "name", "");
            } else if (tab === 2) {
                body += getFormGroup("用户ID", "userId", "");
                body += getFormGroup("考勤类型ID", "attendanceTypeId", "");
                body += getFormGroup("打卡日期", "createDate", "");
                body += getFormGroup("上班日期", "startTime", "");
                body += getFormGroup("下班日期", "endTime", "");
            }
        } else if (action === 1) {//编辑
            var id = button.data('id');
            title = "编辑";
            body += getFormIDGroup(id);

            if (tab === 1) {
                var name = button.data('name')
                body += getFormGroup("考勤类型名称", "name", name);
            } else if (tab === 2) {
                var userId = button.data('userid');
                var attendanceTypeId = button.data('attendancetypeid');
                var createDate = button.data('createdate');
                var startTime = button.data('starttime');
                var endTime = button.data('endtime');
                body += getFormGroup("用户ID", "userId", userId);
                body += getFormGroup("考勤类型ID", "attendanceTypeId", attendanceTypeId);
                body += getFormGroup("打卡日期", "createDate", createDate);
                body += getFormGroup("上班日期", "startTime", startTime);
                body += getFormGroup("下班日期", "endTime", endTime);
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
    window.location.href = host + "/console/record?tab=" + tab + "&token=" + currentToken;
}

function onConfirmClick(tab, action) {
    if (tab === 1) {
        if (action === 0) {//新建
            toSubmitAttendanceTypeCreate(currentToken, $("#name").val());
        } else if (action === 1) {//编辑
            toSubmitAttendanceTypeUpdate(currentToken,
                $("#idMark").val(),
                $("#name").val());
        } else if (action === 2) {//删除
            toSubmitAttendanceTypeDelete(currentToken, $("#idMark").val());
        }
    } else if (tab === 2) {
        if (action === 0) {//新建
            toSubmitAttendanceCreate(currentToken,
                $("#userId").val(),
                $("#attendanceTypeId").val(),
                $("#createDate").val(),
                $("#startTime").val(),
                $("#endTime").val());
        } else if (action === 1) {//编辑
            toSubmitAttendanceUpdate(currentToken,
                $("#idMark").val(),
                $("#userId").val(),
                $("#attendanceTypeId").val(),
                $("#createDate").val(),
                $("#startTime").val(),
                $("#endTime").val());
        } else if (action === 2) {//删除
            toSubmitAttendanceDelete(currentToken, $("#idMark").val());
        }
    }
}

function toSubmitAttendanceTypeCreate(token, name) {
    if (name === "") {
        showToast("考勤类型名称", "不能为空！");
        return;
    }
    $.ajax(host + "/attendance/type", {
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

function toSubmitAttendanceTypeUpdate(token, attendanceTypeId, name) {
    if (attendanceTypeId === "") {
        showToast("考勤类型ID", "不能为空！");
        return;
    }
    if (name === "") {
        showToast("考勤类型名称", "不能为空！");
        return;
    }
    $.ajax(host + "/attendance/type/" + attendanceTypeId, {
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

function toSubmitAttendanceTypeDelete(token, attendanceTypeId) {
    $.ajax(host + "/attendance/type/" + attendanceTypeId, {
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

function toSubmitAttendanceCreate(token, userId, attendanceTypeId, createDate, startTime, endTime) {
    if (userId === "") {
        showToast("用户ID", "不能为空！");
        return;
    }
    if (attendanceTypeId === "") {
        showToast("考勤类型ID", "不能为空！");
        return;
    }
    if (createDate === "") {
        showToast("打卡时间", "不能为空！");
        return;
    }
    if (startTime === "") {
        showToast("上班时间", "不能为空！");
        return;
    }
    if (endTime === "") {
        showToast("下班时间", "不能为空！");
        return;
    }
    $.ajax(host + "/attendance", {
        headers: {
            token: decodeURIComponent(token)
        },
        method: "put",
        dataType: "json",
        data: {
            user_id: parseInt(userId),
            attendance_type_id: parseInt(attendanceTypeId),
            create_date: parseInt(createDate),
            start_time: parseInt(startTime),
            end_time: parseInt(endTime)
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

function toSubmitAttendanceUpdate(token, attendanceId, userId, attendanceTypeId, createDate, startTime, endTime) {
    if (userId === "") {
        showToast("用户ID", "不能为空！");
        return;
    }
    if (attendanceTypeId === "") {
        showToast("考勤类型ID", "不能为空！");
        return;
    }
    if (createDate === "") {
        showToast("打卡时间", "不能为空！");
        return;
    }
    if (startTime === "") {
        showToast("上班时间", "不能为空！");
        return;
    }
    if (endTime === "") {
        showToast("下班时间", "不能为空！");
        return;
    }
    $.ajax(host + "/attendance/" + attendanceId, {
        headers: {
            token: decodeURIComponent(token)
        },
        method: "post",
        dataType: "json",
        data: {
            user_id: parseInt(userId),
            attendance_type_id: parseInt(attendanceTypeId),
            create_date: parseInt(createDate),
            start_time: parseInt(startTime),
            end_time: parseInt(endTime)
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

function toSubmitAttendanceDelete(token, attendanceId) {
    $.ajax(host + "/attendance/" + attendanceId, {
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