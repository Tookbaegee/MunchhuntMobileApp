package munchhunt.munchhuntproject.Recommend;

import java.util.ArrayList;

import munchhunt.munchhuntproject.List.YelpBusinessInfo;


public interface MyCallbackRestaurant {

    void onCallback(ArrayList<YelpBusinessInfo> match);
}
