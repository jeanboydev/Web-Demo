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