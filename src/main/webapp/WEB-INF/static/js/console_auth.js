var currentToken = "";
var currentShownMenu = 0;
$(function () {
    currentToken = getParam("token");
    currentShownMenu = $("#shownMenu").val();

    createMenu(currentShownMenu, menuAuth, currentToken);

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
                body += getFormInputGroup("角色名", "roleName", "");
            } else if (tab === 2) {
                body += getFormInputGroup("角色ID", "roleId", "");
                var tableList = $("#tableList").val();
                body += getFormSelectGroup("表", "table", tableList);
                body += getFormCheckGroup("查询", "select", 1, false);
                body += getFormCheckGroup("添加", "insert", 2, false);
                body += getFormCheckGroup("修改", "update", 4, false);
                body += getFormCheckGroup("删除", "delete", 8, false);
            }
        } else if (action === 4) {//编辑
            var id = button.data('id');
            title = "编辑";
            body += getFormIDGroup(id);

            if (tab === 1) {
                var name = button.data('name');
                body += getFormInputGroup("角色名", "roleName", name);
            } else if (tab === 2) {
                var roleId = button.data('roleid');
                var permissionIdentity = button.data('permissionidentity');
                body += getFormInputGroup("角色ID", "roleId", roleId);
                var tableList = $("#tableList").val();
                body += getFormSelectGroup("表", "table", tableList);
                body += getFormCheckGroup("查询", "select", 1, false);
                body += getFormCheckGroup("添加", "insert", 2, false);
                body += getFormCheckGroup("修改", "update", 4, false);
                body += getFormCheckGroup("删除", "delete", 8, false);
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
    window.location.href = host + "/console/auth?tab=" + tab + "&token=" + currentToken;
}

function onConfirmClick(tab, action) {
    if (tab === 1) {
        if (action === 2) {//新建
            toSubmitRoleCreate(currentToken, $("#roleName").val());
        } else if (action === 4) {//编辑
            toSubmitRoleUpdate(currentToken, $("#idMark").val(), $("#roleName").val());
        } else if (action === 8) {//删除
            toSubmitRoleDelete(currentToken, $("#idMark").val());
        }
    } else if (tab === 2) {
        if (action === 2) {//新建
            var selectValue = $("#select").is(":checked") ? $("#select").val() : 0;
            var insertValue = $("#insert").is(":checked") ? $("#insert").val() : 0;
            var updateValue = $("#update").is(":checked") ? $("#update").val() : 0;
            var deleteValue = $("#delete").is(":checked") ? $("#delete").val() : 0;
            var permissionIdentity = parseInt($("#table").val()) +
                parseInt(selectValue) +
                parseInt(insertValue) +
                parseInt(updateValue) +
                parseInt(deleteValue);
            toSubmitRolePermissionCreate(currentToken, $("#roleId").val(), permissionIdentity);
        } else if (action === 4) {//编辑
            var selectValue = $("#select").is(":checked") ? $("#select").val() : 0;
            var insertValue = $("#insert").is(":checked") ? $("#insert").val() : 0;
            var updateValue = $("#update").is(":checked") ? $("#update").val() : 0;
            var deleteValue = $("#delete").is(":checked") ? $("#delete").val() : 0;
            var permissionIdentity = parseInt($("#table").val()) +
                parseInt(selectValue) +
                parseInt(insertValue) +
                parseInt(updateValue) +
                parseInt(deleteValue);
            toSubmitRolePermissionUpdate(currentToken, $("#idMark").val(), $("#roleId").val(), permissionIdentity);
        } else if (action === 8) {//删除
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
    $.ajax(host + "/permission/relation/" + rolePermissionId, {
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