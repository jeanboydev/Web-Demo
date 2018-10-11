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
    $("#toast").append("<div class=\"alert alert-warning alert-dismissible fade show\" role=\"alert\">\n" +
        "        <strong>" + title + "</strong> " + content + "\n" +
        "        <button type=\"button\" class=\"close\" onclick=\"hideToast();\">\n" +
        "            <span aria-hidden=\"true\">&times;</span>\n" +
        "        </button>\n" +
        "    </div>");
}

function hideToast() {
    $("#toast").empty();
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

function getFormGroup(label, idName, value) {
    return "<div class='form-group row'>\n" +
        "<label for='role_name' class='col-sm-2 col-form-label'>" + label + "</label>\n" +
        "<div class='col-sm-10'>\n" +
        "<input type='text' class='form-control' placeholder='请输入" + label + "' id='" + idName + "' value='" + value + "'>\n" +
        "</div></div>";
}

function getFormFooterButton(tab, action) {
    return "<button type='button' class='btn btn-secondary' data-dismiss='modal'>取消</button>\n"
        + "<button type='button' class='btn btn-primary' onclick='onConfirmClick(" + tab + "," + action + ");'>确定</button>";
}