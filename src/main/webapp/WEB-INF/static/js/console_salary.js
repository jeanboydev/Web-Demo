var currentToken = "";
var currentShownMenu = 0;
$(function () {
    currentToken = getParam("token");
    currentShownMenu = $("#shownMenu").val();

    createMenu(currentShownMenu, menuSalary, currentToken);

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
                body += getFormInputGroup("薪水(元/月)", "monthlyValue", "");
                body += getFormInputGroup("职位ID", "jobId", "");
            }
        } else if (action === 4) {//编辑
            var id = button.data('id');
            title = "编辑";
            body += getFormIDGroup(id);

            if (tab === 1) {
                var monthlyValue = button.data('monthlyvalue');
                var jobId = button.data('jobid');
                body += getFormInputGroup("薪水(元/月)", "monthlyValue", monthlyValue);
                body += getFormInputGroup("职位ID", "jobId", jobId);
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
    window.location.href = host + "/console/salary?tab=" + tab + "&token=" + currentToken;
}

function onConfirmClick(tab, action) {
    if (tab === 1) {
        if (action === 2) {//新建
            toSubmitSalaryCreate(currentToken, $("#monthlyValue").val(), $("#jobId").val());
        } else if (action === 4) {//编辑
            toSubmitSalaryUpdate(currentToken, $("#idMark").val(), $("#monthlyValue").val(), $("#jobId").val());
        } else if (action === 8) {//删除
            toSubmitSalaryDelete(currentToken, $("#idMark").val());
        }
    }
}

function toSubmitSalaryCreate(token, monthlyValue, jobId) {
    if (monthlyValue === "") {
        showToast("薪水", "不能为空！");
        return;
    }
    if (jobId === "") {
        showToast("职位ID", "不能为空！");
        return;
    }
    $.ajax(host + "/salary", {
        headers: {
            token: decodeURIComponent(token)
        },
        method: "put",
        dataType: "json",
        data: {
            monthly_value: parseInt(monthlyValue),
            job_id: parseInt(jobId)
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

function toSubmitSalaryUpdate(token, salaryId, monthlyValue, jobId) {
    if (monthlyValue === "") {
        showToast("薪水", "不能为空！");
        return;
    }
    if (jobId === "") {
        showToast("职位ID", "不能为空！");
        return;
    }
    $.ajax(host + "/salary/" + salaryId, {
        headers: {
            token: decodeURIComponent(token)
        },
        method: "post",
        dataType: "json",
        data: {
            monthly_value: parseInt(monthlyValue),
            job_id: parseInt(jobId)
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

function toSubmitSalaryDelete(token, salaryId) {
    $.ajax(host + "/salary/" + salaryId, {
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