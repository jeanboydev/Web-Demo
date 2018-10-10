var currentToken = "";
$(function () {
    currentToken = getParam("token");
    $("#leftNav").html("<div class='nav flex-column nav-pills'>" +
        "<a class='nav-link active'>首页</a>" +
        "<a class='nav-link' href='/console/auth?token=" + currentToken + "'>权限管理</a>" +
        "<a class='nav-link' href='/console/profile?token=" + currentToken + "'>人事管理</a>" +
        "<a class='nav-link' href='/console/salary?token=" + currentToken + "'>工资管理</a>" +
        "<a class='nav-link' href='/console/record?token=" + currentToken + "'>考勤管理</a></div>");
});

function toGetUserInfo(userId) {
    $.ajax(host + "/token", {
        method: "post",
        dataType: "json",
        data: {
            username: username,
            password: md5(password)
        },
        success: function (data) {
            $(location).attr('href', host + "/console?tag=0&token=" + encodeURIComponent(data.token));
        },
        error: function (error) {
            console.error(error.responseText);
            if (error.responseJSON) {
                var info = error.responseJSON.errors[0];
                // alert(info.description);
            }
        }
    });
}
