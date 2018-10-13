var currentToken = "";
var currentShownMenu = 0;
$(function () {
    currentToken = getParam("token");
    currentShownMenu = $("#shownMenu").val();

    createMenu(currentShownMenu, menuHome, currentToken);
});
