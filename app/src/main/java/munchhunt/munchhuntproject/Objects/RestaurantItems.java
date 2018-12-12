package munchhunt.munchhuntproject.Objects;

import java.io.Serializable;

public class RestaurantItems implements Serializable {
    int entrees = 0, appetizers = 0;
    int whiteMeat = 0,beef = 0,pork = 0, fish =0, crustacean = 0, shellfish= 0, nuts = 0, glutenFree = 0, dairy = 0, eggs = 0;
    public RestaurantItems()
    {

    }

    public RestaurantItems(int entrees, int appetizers, int whiteMeat, int beef, int pork, int fish, int crustacean, int shellfish, int nuts, int glutenFree, int dairy, int eggs)
    {
        this.entrees = entrees;
        this.appetizers = appetizers;
        this.whiteMeat = whiteMeat;
        this.beef = beef;
        this.pork = pork;
        this.fish = fish;
        this.crustacean = crustacean;
        this.shellfish = shellfish;
        this.nuts = nuts;
        this.glutenFree = glutenFree;
        this.dairy = dairy;
        this.eggs = eggs;
    }

    public int getEntrees() {
        return entrees;
    }

    public void setEntrees(int entrees) {
        this.entrees = entrees;
    }

    public int getAppetizers() {
        return appetizers;
    }

    public void setAppetizers(int appetizers) {
        this.appetizers = appetizers;
    }

    public int getWhiteMeat() {
        return whiteMeat;
    }

    public void setWhiteMeat(int whiteMeat) {
        this.whiteMeat = whiteMeat;
    }

    public int getBeef() {
        return beef;
    }

    public void setBeef(int beef) {
        this.beef = beef;
    }

    public int getPork() {
        return pork;
    }

    public void setPork(int pork) {
        this.pork = pork;
    }

    public int getFish() {
        return fish;
    }

    public void setFish(int fish) {
        this.fish = fish;
    }

    public int getCrustacean() {
        return crustacean;
    }

    public void setCrustacean(int crustacean) {
        this.crustacean = crustacean;
    }

    public int getShellfish() {
        return shellfish;
    }

    public void setShellfish(int shellfish) {
        this.shellfish = shellfish;
    }

    public int getNuts() {
        return nuts;
    }

    public void setNuts(int nuts) {
        this.nuts = nuts;
    }

    public int getGlutenFree() {
        return glutenFree;
    }

    public void setGlutenFree(int glutenFree) {
        this.glutenFree = glutenFree;
    }

    public int getDairy() {
        return dairy;
    }

    public void setDairy(int dairy) {
        this.dairy = dairy;
    }

    public int getEggs() {
        return eggs;
    }

    public void setEggs(int eggs) {
        this.eggs = eggs;
    }
}