package munchhunt.munchhuntproject.Party;

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
import munchhunt.munchhuntproject.R;
import munchhunt.munchhuntproject.Objects.User;

public class PartyAdderAdapter extends ArrayAdapter<String> {
    private Context mContext;
    private int mResource;
    private int lastPosition = -1;
    private List<String> friends;


    static class ViewHolder {
        TextView name;

    }

    public PartyAdderAdapter(Context context, int resource, List<String> friends) {
        super(context, resource, friends);
        mContext = context;
        mResource = resource;
        this.friends = friends;

    }
    public void addFriends(String friendId){
        friends.add(friendId);
    }
    public List<String> getFriends(){
        return friends;
    }
    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        final View result;
        final PartyAdderAdapter.ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);

            holder = new PartyAdderAdapter.ViewHolder();

            holder.name = (TextView) convertView.findViewById(R.id.tvFriendName);

            convertView.setTag(holder);

            result = convertView;

        } else {
            holder = (PartyAdderAdapter.ViewHolder) convertView.getTag();
            result = convertView;
        }

        ImageView addButton = (ImageView) convertView.findViewById(R.id.addFriendBtn);

        //ADD FRIEND TO PARTY
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add to party when clicked
                SocialFirebase.sendPartyRequest(getItem(position));
                Toast.makeText(mContext, "Party Invite Request Sent!", Toast.LENGTH_LONG).show();

            }
        });
        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.loading_down_anim : R.anim.loading_up_anim);
        result.startAnimation(animation);
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
