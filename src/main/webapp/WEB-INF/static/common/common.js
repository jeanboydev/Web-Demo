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