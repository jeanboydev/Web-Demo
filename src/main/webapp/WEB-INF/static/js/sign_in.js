function toSignIn() {
    var username = $("input[name='username']").val();
    var password = $("input[name='password']").val();
    if (username === "") {
        showToast("用户名", "不能为空！");
        return;
    }
    if (password === "") {
        showToast("密码", "不能为空！");
        return;
    }
    $.ajax(host + "/token", {
        method: "post",
        dataType: "json",
        data: {
            username: username,
            password: password
        },
        success: function (response) {
            window.location.href = host + "/console?tab=1&token=" + encodeURIComponent(response.data.token);
        },
        error: function (error) {
            console.error(error.responseText);
            if (error.responseJSON) {
                var info = error.responseJSON.errors[0];
                showToast("错误", info.description);
            }
        }
    });
}