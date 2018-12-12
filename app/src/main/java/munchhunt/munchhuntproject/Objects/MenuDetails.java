package munchhunt.munchhuntproject.Objects;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by adria on 6/13/2018.
 */

public class MenuDetails implements Serializable {
    private boolean beef, pork , white_meat, shellfish, crustacean, fish, nuts, gluten, dairy, eggs;
    private String menuType;
    private String name;
    private String description;
    private String price;
    private ArrayList users;
    private String id;

    public MenuDetails(){}

    public MenuDetails(String name, boolean beef, boolean pork, boolean white_meat, boolean gluten, boolean nuts, boolean dairy, boolean shellfish, boolean crustacean, boolean fish, boolean eggs,
                       String description, String price, String menuType)
    {
        this.name = name;
        this.beef = beef;
        this.pork = pork;
        this.white_meat = white_meat;
        this.gluten = gluten;
        this.nuts = nuts;
        this.dairy = dairy;
        this.shellfish = shellfish;
        this.crustacean = crustacean;
        this.fish = fish;
        this.description = description;
        this.price = price;
        this.menuType = menuType;
        this.eggs = eggs;


    }


    public boolean isBeef() {
        return beef;
    }

    public void setBeef(boolean beef) {
        this.beef = beef;
    }

    public boolean isPork() {
        return pork;
    }

    public void setPork(boolean pork) {
        this.pork = pork;
    }

    public boolean isWhite_meat() {
        return white_meat;
    }

    public void setWhite_meat(boolean white_meat) {
        this.white_meat = white_meat;
    }

    public boolean isShellfish() {
        return shellfish;
    }

    public void setShellfish(boolean shellfish) {
        this.shellfish = shellfish;
    }

    public boolean isCrustacean() {
        return crustacean;
    }

    public void setCrustacean(boolean crustacean) {
        this.crustacean = crustacean;
    }

    public boolean isFish() {
        return fish;
    }

    public void setFish(boolean fish) {
        this.fish = fish;
    }

    public boolean isNuts() {
        return nuts;
    }

    public void setNuts(boolean nuts) {
        this.nuts = nuts;
    }

    public boolean isGluten() {
        return gluten;
    }

    public void setGluten(boolean gluten) {
        this.gluten = gluten;
    }

    public boolean isDairy() {
        return dairy;
    }

    public void setDairy(boolean dairy) {
        this.dairy = dairy;
    }

    public String getMenuType() {
        return menuType;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public ArrayList getUsers() {
        return users;
    }

    public void setUsers(ArrayList users) {
        this.users = users;
    }

    public boolean isEggs()
    {
        return eggs;
    }

    public void setEggs(boolean eggs)
    {
        this.eggs = eggs;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
