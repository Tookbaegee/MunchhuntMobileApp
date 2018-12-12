package munchhunt.munchhuntproject.List;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by adria on 6/13/2018.
 */

public class Menu implements Serializable {
    private boolean red_meat, white_meat, gluten, nuts, dairy, seafood;
    private String name;
    private String description;
    private String price;
    private ArrayList users;

    public Menu(){}

    public Menu(String name, boolean red_meat, boolean white_meat, boolean gluten, boolean nuts, boolean dairy, boolean seafood, String description, String price)
    {
        this.name = name;

        this.red_meat = red_meat;
        this.white_meat = white_meat;
        this.gluten = gluten;
        this.nuts = nuts;
        this.dairy = dairy;
        this.seafood = seafood;
        this.description = description;
        this.price = price;



    }

    public String getName()
    {
        return name;
    }
    public boolean getRed_meat()
    {
        return red_meat;
    }
    public boolean getWhite_meat()
    {
        return  white_meat;
    }
    public boolean getGluten(){
        return  gluten;
    }

    public boolean getNuts(){
        return nuts;
    }
    public boolean getDairy()
    {
        return  dairy;
    }
    public boolean getSeafood(){
        return seafood;
    }

    public void setWhite_meat(boolean white_meat) {
        this.white_meat = white_meat;
    }



    public void setNuts(boolean nuts) {
        this.nuts = nuts;
    }

    public void setDairy(boolean dairy) {
        this.dairy = dairy;
    }

    public void setSeafood(boolean seafood) {
        this.seafood = seafood;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRed_meat(boolean red_meat) {
        this.red_meat = red_meat;
    }


    public void setGluten(boolean gluten) {
        this.gluten = gluten;
    }

    public void setUsers(ArrayList<String> users)
    {

        this.users = users;

    }
    public ArrayList getUsers()
    {
        return users;
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


}
