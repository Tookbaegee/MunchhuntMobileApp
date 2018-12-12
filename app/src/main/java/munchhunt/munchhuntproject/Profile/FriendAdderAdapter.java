package munchhunt.munchhuntproject.Profile;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import munchhunt.munchhuntproject.Callback.UserCallback;
import munchhunt.munchhuntproject.Party.SocialFirebase;
import munchhunt.munchhuntproject.R;
import munchhunt.munchhuntproject.Objects.User;

public class FriendAdderAdapter extends ArrayAdapter<String> {
    private Context mContext;
    private int mResource;
    private int lastPosition = -1;
    private List<String> users;


    static class ViewHolder {
        TextView name;

    }

    public FriendAdderAdapter(Context context, int resource, List<String> users) {
        super(context, resource, users);
        mContext = context;
        mResource = resource;
        this.users = users;
    }
    public void addusers(String friendId){
        users.add(friendId);
    }
    public List<String> getusers(){
        return users;
    }
    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        final View result;
        final FriendAdderAdapter.ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);

            holder = new FriendAdderAdapter.ViewHolder();

            holder.name = (TextView) convertView.findViewById(R.id.tv_faUserName);

            convertView.setTag(holder);

            result = convertView;

        } else {
            holder = (FriendAdderAdapter.ViewHolder) convertView.getTag();
            result = convertView;
        }

        ImageView addButton = (ImageView) convertView.findViewById(R.id.bt_faaddFriend);

        //send friend request to a user
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SocialFirebase.sendFriendRequest(getItem(position));

            }
        });
        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.loading_down_anim : R.anim.loading_up_anim);
        result.startAnimation(animation);
//        lastPosition = position;
        SocialFirebase.readUser(getItem(position), new UserCallback<User>() {
                    @Override
                    public void callback(User user) {
                        holder.name.setText(user.getName());
                    }
                }
        );

        return convertView;
    }
}
