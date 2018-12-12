//package munchhunt.munchhuntproject.List;
//
//import android.content.Context;
//import android.support.annotation.NonNull;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.animation.Animation;
//import android.view.animation.AnimationUtils;
//import android.widget.ArrayAdapter;
//import android.widget.TextView;
//
//import org.w3c.dom.Text;
//
//import java.util.ArrayList;
//import java.util.List;
//import munchhunt.munchhuntproject.R;
//
//
//
///**
// * Created by adria on 6/12/2018.
// */
//
//public class MenuListAdapter extends ArrayAdapter<Menu>{
//    private Context mContext;
//    private int mResource;
//    private int lastPosition = -1;
//    int row_index;
//    TextView users;
//
//
//    static class ViewHolder {
//        TextView menu;
//        TextView direction;
//
//    }
//
//
//    public MenuListAdapter(Context context, int resource, List<Menu> objects)
//    {
//        super (context, resource, objects);
//        mContext = context;
//        mResource = resource;
//
//    }
//
//
//    @NonNull
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent)
//    {
//        String users = "";
//
//        String name = getItem(position).getName() + "";
//
//        ArrayList userList = getItem(position).getUsers();
//
//        for (int i = 0 ; i < userList.size(); i++)
//        {
//            users = users + userList.get(i) + " | " ;
//        }
//
//       String description = getItem(position).getDescription();
//        String price = getItem(position).getPrice();
//
//
//     //   Menu menu = new Menu(name);
//
//        final View result;
//        RestaurantListAdapter.ViewHolder holder;
//        if (convertView == null)
//        {
//            LayoutInflater inflater = LayoutInflater.from(mContext);
//            convertView = inflater.inflate(mResource, parent, false);
//
//            holder = new RestaurantListAdapter.ViewHolder();
//
//
//            holder.name = (TextView) convertView.findViewById(R.id.foodName);
//            holder.Direction = (TextView) convertView.findViewById(R.id.userMatch);
//            holder.munchRating = (TextView) convertView.findViewById(R.id.menuDescrpition);
//            holder.Title = (TextView) convertView.findViewById(R.id.menuPrice);
//         ;
//            convertView.setTag(holder);
//
//            result = convertView;
//
//        }
//        else
//        {
//            holder = (RestaurantListAdapter.ViewHolder) convertView.getTag();
//            result = convertView;
//        }
//
///*
//        TextView restName = (TextView)convertView.findViewById(R.id.restName);
//        TextView restDirection = (TextView)convertView.findViewById(R.id.restLocation);
//        TextView restMunchRating = (TextView)convertView.findViewById(R.id.munchRating);
//        restName.setText(name);
//        restDirection.setText(direction);
//        restMunchRating.setText(munchRating);
//
//*/
//        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition)? R.asdf.loading_down_anim: R.asdf.loading_up_anim);
//        result.startAnimation(animation);
//        lastPosition = position;
//
//        holder.name.setText(name);
//        holder.Direction.setText(users);
//        holder.munchRating.setText(description);
//         holder.Title.setText(price);
//
//
//
//        return convertView;
//    }
//
//
//
//
//}
