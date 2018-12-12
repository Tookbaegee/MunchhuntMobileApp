package munchhunt.munchhuntproject.List;

import java.util.ArrayList;

public class Menus {
    private ArrayList<Menu> menus;
    public Menus(){}
    public Menus(ArrayList<Menu> menus)
    {
        this.menus = menus;
    }

    public ArrayList<Menu> getMenus() {
        return menus;
    }

    public void setMenus(ArrayList<Menu> menus) {
        this.menus = menus;
    }
}
