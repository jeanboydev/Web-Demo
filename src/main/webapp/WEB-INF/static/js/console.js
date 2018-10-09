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