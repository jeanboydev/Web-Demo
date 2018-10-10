var currentToken = "";
$(function () {
    currentToken = getParam("token");
    $("#actionModal").on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget);
        var tab = button.data('tab');
        var action = button.data('action');

        var title = "";
        var body = "<div id='toast'></div>";
        var footer = getFooterButton(tab, action);
        if (action === 0) {//新建
            title = "新建";
            body += getFormGroup("角色名", "roleName", "");
        } else if (action === 1) {//编辑
            var id = button.data('id');
            var name = button.data('name');
            title = "编辑";
            body += getFormIDGroup(id);
            body += getFormGroup("角色名", "roleName", name, action);
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

function getDeleteContent(id) {
    return "<input type='text' readonly hidden id='idMark' value='" + id + "'><p>确定要删除吗？</p>";
}

function getFormIDGroup(value) {
    return "<div class='form-group row'>\n" +
        "<label for='id_value' class='col-sm-2 col-form-label'>ID</label>\n" +
        "<div class='col-sm-10'>\n" +
        "<input type='text' readonly class='form-control-plaintext' id='idMark' value='" + value + "'>\n" +
        "</div></div>";
}

function getFormGroup(label, idName, value) {
    return "<div class='form-group row'>\n" +
        "<label for='role_name' class='col-sm-2 col-form-label'>" + label + "</label>\n" +
        "<div class='col-sm-10'>\n" +
        "<input type='text' class='form-control' placeholder='请输入" + label + "' id='" + idName + "' value='" + value + "'>\n" +
        "</div></div>";
}

function getFooterButton(tab, action) {
    return "<button type='button' class='btn btn-secondary' data-dismiss='modal'>取消</button>\n"
        + "<button type='button' class='btn btn-primary' onclick='onConfirmClick(" + tab + "," + action + ");'>确定</button>";
}

function onConfirmClick(tab, action) {
    if (action === 0) {//新建
        toSubmitRoleCreate(currentToken, $("#roleName").val());
    } else if (action === 1) {//编辑
        toSubmitRoleUpdate(currentToken, $("#idMark").val(), $("#roleName").val());
    } else if (action === 2) {//删除
        toSubmitRoleDelete(currentToken, $("#idMark").val());
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
        dataType: "text",
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
        dataType: "text",
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
        dataType: "text",
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
