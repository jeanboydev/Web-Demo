package com.jeanboy.web.demo.domain.model;

public class PageModel {

    private int shownMenu;
    private int shownTab;
    private int shownAction;

    public PageModel() {
    }

    public PageModel(int shownMenu, int shownTab, int shownAction) {
        this.shownMenu = shownMenu;
        this.shownTab = shownTab;
        this.shownAction = shownAction;
    }

    public int getShownMenu() {
        return shownMenu;
    }

    public void setShownMenu(int shownMenu) {
        this.shownMenu = shownMenu;
    }

    public int getShownTab() {
        return shownTab;
    }

    public void setShownTab(int shownTab) {
        this.shownTab = shownTab;
    }

    public int getShownAction() {
        return shownAction;
    }

    public void setShownAction(int shownAction) {
        this.shownAction = shownAction;
    }
}
