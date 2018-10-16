var currentToken = "";
var currentShownMenu = 0;
$(function () {
    currentToken = getParam("token");
    currentShownMenu = $("#shownMenu").val();

    createMenu(currentShownMenu, menuRecord, currentToken);

    $("#actionModal").on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget);
        var tab = button.data('tab');
        var action = button.data('action');

        var title = "";
        var body = "<div id='toast'></div>";
        var footer = getFormFooterButton(tab, action);
        if (action === 2) {//新建
            title = "新建";
            if (tab === 1) {
                body += getFormInputGroup("考勤类型名称", "name", "");
            } else if (tab === 2) {
                var userList = $("#userList").val();
                body += getFormSelectGroup("用户", "userId", userList, 0);
                var attendanceTypeList = $("#attendanceTypeList").val();
                body += getFormSelectGroup("考勤类型", "attendanceTypeId", attendanceTypeList);
                body += getFormInputGroup("打卡日期", "createDate", "");
                body += getFormInputGroup("上班日期", "startTime", "");
                body += getFormInputGroup("下班日期", "endTime", "");
            }
        } else if (action === 4) {//编辑
            var id = button.data('id');
            title = "编辑";
            body += getFormIDGroup(id);

            if (tab === 1) {
                var name = button.data('name')
                body += getFormInputGroup("考勤类型名称", "name", name);
            } else if (tab === 2) {
                var userId = button.data('userid');
                var attendanceTypeId = button.data('attendancetypeid');
                var createDate = button.data('createdate');
                var startTime = button.data('starttime');
                var endTime = button.data('endtime');
                var userList = $("#userList").val();
                body += getFormSelectGroup("用户", "userId", userList, userId);
                var attendanceTypeList = $("#attendanceTypeList").val();
                body += getFormSelectGroup("考勤类型", "attendanceTypeId", attendanceTypeList, attendanceTypeId);
                body += getFormInputGroup("打卡日期", "createDate", createDate);
                body += getFormInputGroup("上班日期", "startTime", startTime);
                body += getFormInputGroup("下班日期", "endTime", endTime);
            }
        } else if (action === 8) {//删除
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
        if (action === 2) {//新建
            toSubmitAttendanceTypeCreate(currentToken, $("#name").val());
        } else if (action === 4) {//编辑
            toSubmitAttendanceTypeUpdate(currentToken,
                $("#idMark").val(),
                $("#name").val());
        } else if (action === 8) {//删除
            toSubmitAttendanceTypeDelete(currentToken, $("#idMark").val());
        }
    } else if (tab === 2) {
        if (action === 2) {//新建
            toSubmitAttendanceCreate(currentToken,
                $("#userId").val(),
                $("#attendanceTypeId").val(),
                $("#createDate").val(),
                $("#startTime").val(),
                $("#endTime").val());
        } else if (action === 4) {//编辑
            toSubmitAttendanceUpdate(currentToken,
                $("#idMark").val(),
                $("#userId").val(),
                $("#attendanceTypeId").val(),
                $("#createDate").val(),
                $("#startTime").val(),
                $("#endTime").val());
        } else if (action === 8) {//删除
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