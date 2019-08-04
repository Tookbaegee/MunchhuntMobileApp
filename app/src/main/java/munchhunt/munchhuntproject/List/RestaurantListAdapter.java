package munchhunt.munchhuntproject.List;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import munchhunt.munchhuntproject.R;

/**
 * Created by adria on 5/22/2018.
 */

//Adapter class to put values into listView
public class    RestaurantListAdapter extends ArrayAdapter<restaurant> {
    private Context mContext;
    private int mResource;
    private int lastPosition = -1;


    static class ViewHolder {
        TextView name;
        TextView Title;
        TextView Direction;
        TextView munchRating;
        TextView yelpRating;

    }

    public RestaurantListAdapter(Context context, int resource, List<restaurant> objects)
    {
        super (context, resource, objects);
        mContext = context;
        mResource = resource;

    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        String name = getItem(position).getName();
        String title = getItem(position).getTitle();
        String direction = getItem(position).getDirection();
        String munchRating = getItem(position).getMunchRating();
        String yelpRating = getItem(position).getYelpRating();

        //  restaurant restaurant = new restaurant(name, direction, munchRating);

        final View result;
        ViewHolder holder;
        if (convertView == null)
        {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);

            holder = new ViewHolder();


            holder.name = (TextView) convertView.findViewById(R.id.restName);
            holder.Title = (TextView) convertView.findViewById(R.id.munchTitle);
            holder.Direction = (TextView) convertView.findViewById(R.id.munchLocation);
            holder.munchRating = (TextView)  convertView.findViewById(R.id.munchRating);

            convertView.setTag(holder);

            result = convertView;

        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition)? R.anim.loading_down_anim: R.anim.loading_up_anim);
        result.startAnimation(animation);
        lastPosition = position;

        holder.name.setText(name);
        holder.Title.setText(title);
        holder.Direction.setText(direction);
        holder.yelpRating.setText("Yelp Rating: " + yelpRating);
        holder.munchRating.setText("Munch Rating: "+ munchRating);


        return convertView;
    }

}
