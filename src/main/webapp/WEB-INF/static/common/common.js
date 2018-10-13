function getParam(key) {
    var url = location.search;
    var context = "";
    var reg = new RegExp("(^|&)" + key + "=([^&]*)(&|$)", "i");
    var strs = url.split("?");
    var param = "";
    if (strs.length > 1) {
        param = strs[1];
    }
    var r = param.match(reg);
    if (r !== null) {
        context = r[2];
    }
    reg = null;
    r = null;
    return (context === null || context === "" || context === "undefined") ? "" : context;
}

function showToast(title, content) {
    $("#toast").empty();
    $("#toast").append("<div class='alert alert-warning alert-dismissible fade show' role='alert'>\n" +
        "        <strong>" + title + "</strong> " + content + "\n" +
        "        <button type='button' class='close' onclick='hideToast();'>\n" +
        "            <span aria-hidden='true'>&times;</span>\n" +
        "        </button>\n" +
        "    </div>");
}

function hideToast() {
    $("#toast").empty();
}

var menuHome = 0;
var menuAuth = 1;
var menuProfile = 2;
var menuSalary = 4;
var menuRecord = 8;

function createMenu(shownMenu, currentMenu, token) {
    $("#leftNav").html("<div class='nav flex-column nav-pills'>" +
        ((shownMenu & menuHome) === menuHome ? "<a class='nav-link" + (currentMenu === menuHome ? " active" : "") + "' href='/console?tab=1&token=" + token + "'>首页</a>" : "") +
        ((shownMenu & menuAuth) === menuAuth ? "<a class='nav-link" + (currentMenu === menuAuth ? " active" : "") + "' href='/console/auth?tab=1&token=" + token + "'>权限管理</a>" : "") +
        ((shownMenu & menuProfile) === menuProfile ? "<a class='nav-link" + (currentMenu === menuProfile ? " active" : "") + "' href='/console/profile?tab=1&token=" + token + "'>人事管理</a>" : "") +
        ((shownMenu & menuSalary) === menuSalary ? "<a class='nav-link" + (currentMenu === menuSalary ? " active" : "") + "' href='/console/salary?tab=1&token=" + token + "'>工资管理</a>" : "") +
        ((shownMenu & menuRecord) === menuRecord ? "<a class='nav-link" + (currentMenu === menuRecord ? " active" : "") + "' href='/console/record?tab=1&token=" + token + "'>考勤管理</a></div>" : ""));
}


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

function getFormSelectGroup(label, idName, value) {
    var valueList = JSON.parse(value);
    var content = "";
    for (var i = 0; i < valueList.length; i++) {
        var item = valueList[i];
        content += "<option value='" + item.id + "'>" + item.name + "</option>";
    }
    return "<div class='form-group row'>\n" +
        "<label for='" + idName + "' class='col-sm-2 col-form-label'>" + label + "</label>\n" +
        "<div class='col-sm-10'>\n" +
        "<select class='form-control' id='" + idName + "'>" +
        content +
        "</select>\n" +
        "</div></div>";
}

function getFormCheckGroup(label, idName, value, privilegedId, privilegedValue) {
    return "<div class='form-group row'>\n" +
        "<label for='" + idName + "' class='col-sm-2 col-form-label'>" + label + "</label>\n" +
        "<div class='col-sm-10'>\n<div class='form-control-plaintext'>" +
        "<div class='form-check form-check-inline'>\n" +
        "<input class='form-check-input' type='checkbox' value='" + value + "' id='" + idName + "'>" +
        "<label for='" + idName + "' class='form-check-label'>仅自己</label></div>"
        + "<div class='form-check form-check-inline'>\n" +
        "<input class='form-check-input' type='checkbox' value='" + privilegedValue + "' id='" + privilegedId + "'>" +
        "<label for='" + privilegedId + "' class='form-check-label'>全部</label></div>" +
        "</div></div></div>";
}

function getFormInputGroup(label, idName, value) {
    return "<div class='form-group row'>\n" +
        "<label for='" + idName + "' class='col-sm-2 col-form-label'>" + label + "</label>\n" +
        "<div class='col-sm-10'>\n" +
        "<input type='text' class='form-control' placeholder='请输入" + label + "' id='" + idName + "' value='" + value + "'>\n" +
        "</div></div>";
}

function getFormFooterButton(tab, action) {
    return "<button type='button' class='btn btn-secondary' data-dismiss='modal'>取消</button>\n"
        + "<button type='button' class='btn btn-primary' onclick='onConfirmClick(" + tab + "," + action + ");'>确定</button>";
}