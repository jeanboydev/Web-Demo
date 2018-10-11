var currentToken = "";
$(function () {
    currentToken = getParam("token");

    $("#leftNav").html("<div class='nav flex-column nav-pills'>" +
        "<a class='nav-link' href='/console?tab=1&token=" + currentToken + "'>首页</a>" +
        "<a class='nav-link active' href='/console/auth?tab=1&token=" + currentToken + "'>权限管理</a>" +
        "<a class='nav-link' href='/console/profile?tab=1&token=" + currentToken + "'>人事管理</a>" +
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
                body += getFormGroup("角色名", "roleName", "");
            } else if (tab === 2) {
                body += getFormGroup("角色ID", "roleId", "");
                body += getFormGroup("权限标识", "permissionIdentity", "");
            }
        } else if (action === 1) {//编辑
            var id = button.data('id');
            title = "编辑";
            body += getFormIDGroup(id);

            if (tab === 1) {
                var name = button.data('name');
                body += getFormGroup("角色名", "roleName", name);
            } else if (tab === 2) {
                var roleId = button.data('roleid');
                var permissionIdentity = button.data('permissionidentity');
                body += getFormGroup("角色ID", "roleId", roleId);
                body += getFormGroup("权限标识", "permissionIdentity", permissionIdentity);
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
    window.location.href = host + "/console/auth?tab=" + tab + "&token=" + currentToken;
}

function onConfirmClick(tab, action) {
    if (tab === 1) {
        if (action === 0) {//新建
            toSubmitRoleCreate(currentToken, $("#roleName").val());
        } else if (action === 1) {//编辑
            toSubmitRoleUpdate(currentToken, $("#idMark").val(), $("#roleName").val());
        } else if (action === 2) {//删除
            toSubmitRoleDelete(currentToken, $("#idMark").val());
        }
    } else if (tab === 2) {
        if (action === 0) {//新建
            toSubmitRolePermissionCreate(currentToken, $("#roleId").val(), $("#permissionIdentity").val());
        } else if (action === 1) {//编辑
            toSubmitRolePermissionUpdate(currentToken, $("#idMark").val(), $("#roleId").val(), $("#permissionIdentity").val());
        } else if (action === 2) {//删除
            toSubmitRolePermissionDelete(currentToken, $("#idMark").val());
        }
    }
}

function toSubmitRoleCreate(token, roleName) {
    if (roleName === "") {
        showToast("角色名", "不能为空！");
        return;
    }
    $.ajax(host + "/role", {
        headers: {
            token: decodeURIComponent(token)
        },
        method: "put",
        dataType: "json",
        data: {
            name: roleName
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

function toSubmitRoleUpdate(token, roleId, roleName) {
    if (roleName === "") {
        showToast("角色名", "不能为空！");
        return;
    }
    $.ajax(host + "/role/" + roleId, {
        headers: {
            token: decodeURIComponent(token)
        },
        method: "post",
        dataType: "json",
        data: {
            name: roleName
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

function toSubmitRoleDelete(token, roleId) {
    $.ajax(host + "/role/" + roleId, {
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

function toSubmitRolePermissionCreate(token, roleId, permissionIdentity) {
    if (roleId === "") {
        showToast("角色ID", "不能为空！");
        return;
    }
    if (permissionIdentity === "") {
        showToast("权限标识", "不能为空！");
        return;
    }
    $.ajax(host + "/permission/relation", {
        headers: {
            token: decodeURIComponent(token)
        },
        method: "put",
        dataType: "json",
        data: {
            role_id: parseInt(roleId),
            permission_identity: parseInt(permissionIdentity)
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

function toSubmitRolePermissionUpdate(token, rolePermissionId, roleId, permissionIdentity) {
    if (roleId === "") {
        showToast("角色ID", "不能为空！");
        return;
    }
    if (permissionIdentity === "") {
        showToast("权限标识", "不能为空！");
        return;
    }
    $.ajax(host + "/permission/relation/" + roleId, {
        headers: {
            token: decodeURIComponent(token)
        },
        method: "post",
        dataType: "json",
        data: {
            role_id: parseInt(roleId),
            permission_identity: parseInt(permissionIdentity)
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

function toSubmitRolePermissionDelete(token, rolePermissionId) {
    $.ajax(host + "/permission/relation/" + rolePermissionId, {
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