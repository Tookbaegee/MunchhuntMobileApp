package munchhunt.munchhuntproject.List;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by adria on 5/22/2018.
 */
//storage class for Rrestaurant (VARIABLES MUST BE PUBLIC OR HAVE GETTER/SETTER)
public class restaurant implements Serializable{
    String name;
    String direction;
    String munchRating;
    String id;
    String title;
    String yelpRating;
 //   Menu menu;

    public restaurant(String name, String title, String direction, String munchRating, String yelpRating,  String id)
    {
        this.name = name;
        this.title = title;
        this.direction = direction;
        this.munchRating = munchRating;
        this.yelpRating = yelpRating;
        this.id = id;

    }
/*
    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }
*/
    public String getName()
    {
        return name;
    }
    public String getDirection()
    {
        return direction;
    }
    public String getMunchRating()
    {
        return munchRating;
    }
    public String getTitle ()
    {
        return title;
    }
    public String getYelpRating()
    {
        return yelpRating;
    }

    public String getId()
    {
        return id;
    }

    public void setMunchRating(String munchRating)
    {
        this.munchRating = munchRating;
    }
}
