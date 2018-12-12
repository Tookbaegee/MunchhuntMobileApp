package munchhunt.munchhuntproject.Map;

import java.util.Comparator;

import munchhunt.munchhuntproject.Objects.Restaurant;

public class RestaurantComparator implements Comparator<Restaurant> {
    @Override
    public int compare(Restaurant r1, Restaurant r2) {
        int result = 0;
        double compare = (r2.getMunchScore() - r1.getMunchScore());

        if (compare < 0)
        {
            result = -1;
        }
        else if (compare > 0)
        {
            result = 1;
        }
        else if (compare == 0)
        {
            result = 0;
        }
        return result;
    }
}
