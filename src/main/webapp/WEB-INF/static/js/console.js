var currentToken = "";
$(function () {
    currentToken = getParam("token");
    $("#leftNav").html("<div class='nav flex-column nav-pills'>" +
        "<a class='nav-link active' href='/console?tab=1&token=" + currentToken + "'>首页</a>" +
        "<a class='nav-link' href='/console/auth?tab=1&token=" + currentToken + "'>权限管理</a>" +
        "<a class='nav-link' href='/console/profile?tab=1&token=" + currentToken + "'>人事管理</a>" +
        "<a class='nav-link' href='/console/salary?tab=1&token=" + currentToken + "'>工资管理</a>" +
        "<a class='nav-link' href='/console/record?tab=1&token=" + currentToken + "'>考勤管理</a></div>");
});
