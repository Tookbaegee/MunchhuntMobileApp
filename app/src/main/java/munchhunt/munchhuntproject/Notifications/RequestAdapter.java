package munchhunt.munchhuntproject.Notifications;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import munchhunt.munchhuntproject.Callback.UserCallback;
import munchhunt.munchhuntproject.Objects.Request;
import munchhunt.munchhuntproject.Party.SocialFirebase;
import munchhunt.munchhuntproject.R;
import munchhunt.munchhuntproject.Objects.User;

public class RequestAdapter extends ArrayAdapter<Request> {
    private Context mContext;
    private int mResource;
    private List<Request> requests;
    private int lastPosition = -1;


    static class ViewHolder {
        TextView name;
        TextView type;

    }

    public RequestAdapter(Context context, int resource, List<Request> requests) {
        super(context, resource, requests);
        this.requests = requests;
        mContext = context;
        mResource = resource;


    }




    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final View result;
        final munchhunt.munchhuntproject.Notifications.RequestAdapter.ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);

            holder = new munchhunt.munchhuntproject.Notifications.RequestAdapter.ViewHolder();

            holder.name = (TextView) convertView.findViewById(R.id.tvUsername);
            holder.type = (TextView) convertView.findViewById(R.id.tvRequestType);

            convertView.setTag(holder);

            result = convertView;

        } else {
            holder = (munchhunt.munchhuntproject.Notifications.RequestAdapter.ViewHolder) convertView.getTag();
            result = convertView;
        }

        Button acceptButton = (Button) convertView.findViewById(R.id.btnAccept);
        Button declineButton = (Button) convertView.findViewById(R.id.btnDecline);
        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getItem(position).getType() == 0) {
                    SocialFirebase.acceptFriendRequest(getItem(position).getSenderID());
                    requests.remove(position);
                    notifyDataSetChanged();
                    Toast.makeText(mContext, "Friend Request Accepted!", Toast.LENGTH_SHORT).show();

                }
                else if(getItem(position).getType() == 1) {
                    SocialFirebase.acceptPartyRequest(getItem(position).getSenderID());
                    requests.remove(position);
                    notifyDataSetChanged();
                    Toast.makeText(mContext, "Party Joined!", Toast.LENGTH_LONG).show();
                }
                else{}
            }
        });
        declineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getItem(position).getType() == 0) {
                    SocialFirebase.declineFriendRequest(getItem(position).getSenderID());
                    requests.remove(position);
                    notifyDataSetChanged();
                }
                else if(getItem(position).getType() == 1) {
                    SocialFirebase.declinePartyRequest(getItem(position).getSenderID());
                    requests.remove(position);
                    notifyDataSetChanged();

                }
                else{}
            }
        });

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.loading_down_anim : R.anim.loading_up_anim);
        result.startAnimation(animation);
        if(getItem(position).getType() == 0){
            holder.type.setText("Friend Request");
        }
        else if(getItem(position).getType() == 1){
            holder.type.setText("Party Request");
        }

        SocialFirebase.readUser(getItem(position).getSenderID(), new UserCallback<User>() {
                    @Override
                    public void callback(User user) {
                        holder.name.setText(user.getName());
                    }
                }
        );

        return convertView;
    }
}
