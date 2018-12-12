package munchhunt.munchhuntproject.Objects;

import java.util.ArrayList;

public class Menus {
    private ArrayList<MenuDetails> menus;
    public Menus(){}
    public Menus(ArrayList<MenuDetails> menus)
    {
        this.menus = menus;
    }

    public ArrayList<MenuDetails> getMenus() {
        return menus;
    }

    public void setMenus(ArrayList<MenuDetails> menus) {
        this.menus = menus;
    }
}
