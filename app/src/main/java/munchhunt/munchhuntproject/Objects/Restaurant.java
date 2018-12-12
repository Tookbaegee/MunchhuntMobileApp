package munchhunt.munchhuntproject.Objects;

import com.google.firebase.storage.StorageReference;

import java.io.Serializable;

/**
 * Created by adria on 6/4/2018.
 */

public class Restaurant implements Serializable {
    private String id, name, alias, directions, latCoordinates,longCoordinates, price, keyword;
    private int time;
    private Menus menus;
    private String website, hours;
    private RestaurantItems restaurantItems;
    private double munchScore;


    public Restaurant()
    {}

    public Restaurant(String id, String name, Menus menus, String alias, String directions, String latCoordinates, String longCoordinates, String price, String keyword, RestaurantItems restaurantItems)
    {
        this.menus = menus;
        this.id = id;

        this.name = name;
        this.alias = alias;
        this.directions = directions;
        this.latCoordinates = latCoordinates;
        this.longCoordinates = longCoordinates;
        this.price = price;
        this.keyword = keyword;
        this.restaurantItems = restaurantItems;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Menus getMenus() {
        return menus;
    }

    public void setMenus(Menus menu) {
        this.menus = menus;
    }

    public String getLatCoordinates() {
        return latCoordinates;
    }

    public void setLatCoordinates(String latCoordinates) {
        this.latCoordinates = latCoordinates;
    }

    public String getDirections() {
        return directions;
    }

    public void setDirections(String directions) {
        this.directions = directions;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKeyword()
    {
        return keyword;
    }

    public void setKeyword(String keyword)
    {
        this.keyword = keyword;
    }

    public RestaurantItems getRestaurantItems() {
        return restaurantItems;
    }

    public void setRestaurantItems(RestaurantItems restaurantItems) {
        this.restaurantItems = restaurantItems;
    }

    public double getMunchScore() { return munchScore; }

    public void setMunchScore(double munchScore) { this.munchScore = munchScore; }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
