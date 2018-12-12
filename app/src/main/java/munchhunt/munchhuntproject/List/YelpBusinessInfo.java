package munchhunt.munchhuntproject.List;

/**
 * Created by adria on 6/4/2018.
 */

public class YelpBusinessInfo {
    private String name, alias, directions, rating, latCoordinates, longCoordinates, price, yelpId;
    private Menus menus;





    public YelpBusinessInfo()
    {}

    public YelpBusinessInfo(String id, String name, String alias, String directions, String latCoordinates, String longCoordinates, String rating, String price, Menus menus )
    {
        this.menus = menus;

        this.yelpId = id;
        this.name = name;
        this.alias = alias;
        this.directions = directions;
        this.latCoordinates = latCoordinates;
        this.longCoordinates = longCoordinates;
        this.rating = rating;
        this.price= price;
        //  this.menu = menu;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getDirections() {
        return directions;
    }

    public void setDirections(String directions) {
        this.directions = directions;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getLatCoordinates() {
        return latCoordinates;
    }

    public void setLatCoordinates(String latCoordinates) {
        this.latCoordinates = latCoordinates;
    }

    public String getLongCoordinates() {
        return longCoordinates;
    }

    public void setLongCoordinates(String longCoordinates) {
        this.longCoordinates = longCoordinates;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getYelpId() {
        return yelpId;
    }

    public void setYelpId(String yelpId) {
        this.yelpId = yelpId;
    }

    public Menus getMenus() {
        return menus;
    }

    public void setMenus(Menus menus) {
        this.menus = menus;
    }
}
