function onCreate(tab) {
    $('#dialogCreate').modal();
}

function onCreateSave(tab, token) {
    var roleName = $('#role_name').val();
    console.error("===tab==" + tab);
    console.error("===roleName==" + roleName);
    if (roleName === ""){
        showToast("角色名", "不能为空！");
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
            console.error(error.responseText);
            if (error.responseJSON) {
                var info = error.responseJSON.errors[0];
                alert(info.description);
            }
        }
    });
}