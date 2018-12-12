package munchhunt.munchhuntproject.Map;

import java.util.ArrayList;

import munchhunt.munchhuntproject.Objects.MenuDetails;

public interface RestaurantMenuCallback {
    void onCallback(ArrayList<MenuDetails> menu);
}
