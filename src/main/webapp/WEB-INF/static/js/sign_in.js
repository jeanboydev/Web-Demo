function toSignIn() {
    var username = $("input[name='username']").val();
    var password = $("input[name='password']").val();
    if (username === "" || password === "") {
        return;
    }
    $.ajax(host + "/token", {
        method: "post",
        dataType: "json",
        data: {
            username: username,
            password: md5(password)
        },
        success: function (data) {
            console.warn(data);
        },
        error: function (error) {
            console.error(error.responseText);
            if (error.responseJSON) {
                var info = error.responseJSON.errors[0];
                alert(info.description);
            }
        }
    });
}