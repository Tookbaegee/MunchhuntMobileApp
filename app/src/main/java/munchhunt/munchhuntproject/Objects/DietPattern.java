package munchhunt.munchhuntproject.Objects;

import java.io.Serializable;

public class DietPattern implements Serializable {

    private boolean pork, beef, fish, crustacean, shellfish, white_meat, gluten, nut, dairy, eggs;
    private int restriction = 0;
    private String name, id;

    public DietPattern()
    {}


    public DietPattern(String name, String id, boolean pork, boolean beef, boolean fish, boolean crustacean, boolean shellfish, boolean white_meat, boolean nuts, boolean gluten, boolean dairy, boolean eggs)
    {
        this.name = name;
        this.id = id;
        this.pork = pork;
        this.beef = beef;
        this.fish = fish;
        this.crustacean = crustacean;
        this.shellfish = shellfish;
        this.white_meat = white_meat;
        this.gluten = gluten;
        this.nut = nuts;
        this.dairy = dairy;
        this.eggs = eggs;

        if (!beef)
        {
            restriction++;

        }
        if (!pork)
        {
            restriction++;

        }
        if(!white_meat)
        {
            restriction++;
        }
        if(!shellfish)
        {
            restriction++;
        }
        if(!fish)
        {
            restriction++;
        }
        if(!shellfish)
        {
            restriction++;
        }
        if(!crustacean)
        {
            restriction++;
        }
        if(!dairy)
        {
            restriction++;
        }
        if (!gluten)
        {
            restriction++;
        }
        if(!nut) {
            restriction++;
        }
        if (!eggs)
        {
            restriction++;
        }





    }

    public String getName()
    {
        return name;
    }

    // pork
    public boolean getPork() { return pork; }

    public void setPork(boolean pork) { this.pork = pork; }

    // beef
    public boolean getBeef() { return beef; }

    public void setBeef(boolean beef) { this.beef = beef; }

    // fish
    public boolean getFish() { return fish; }

    public void setFish(boolean fish) { this.fish = fish; }

    // crustacean
    public boolean getCrustacean() { return crustacean; }

    public void setCrustacean(boolean crustacean) { this.crustacean = crustacean; }

    // shellfish
    public boolean getShellfish() { return shellfish; }

    public void setShellfish(boolean shellfish) { this.shellfish = shellfish; }

    // dairy
    public boolean getDairy() {
        return dairy;
    }

    public void setDairy(boolean dairy) {
        this.dairy = dairy;
    }

    // gluten
    public boolean getGluten() {
        return gluten;
    }

    public void setGluten(boolean gluten) {
        this.gluten = gluten;
    }

    // white meat
    public boolean getWhite_meat() {
        return white_meat;
    }

    public void setWhiteMeat(boolean whiteMeat) { this.white_meat = whiteMeat; }

    // nuts
    public boolean getNut() { return nut; }

    public void setNut(boolean nut) {
        this.nut = nut;
    }

    public int getRestriction() {
        return restriction;
    }

    public void setRestriction(int restriction) {
        this.restriction = restriction;
    }


    public boolean getEggs() {
        return eggs;
    }

    public void setEggs(boolean eggs) {
        this.eggs = eggs;
    }
}