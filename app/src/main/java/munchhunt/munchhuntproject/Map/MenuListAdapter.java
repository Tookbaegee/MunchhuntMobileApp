package munchhunt.munchhuntproject.Map;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

import munchhunt.munchhuntproject.MunchScore.DietPatternComparator;
import munchhunt.munchhuntproject.Objects.MenuDetails;
import munchhunt.munchhuntproject.R;
import munchhunt.munchhuntproject.Objects.DietPattern;


/**
 * Created by adria on 6/12/2018.
 */

public class MenuListAdapter extends ArrayAdapter<MenuDetails> {
    private Context mContext;
    private int mResource;
    private int lastPosition = -1;
    private List<DietPattern> partyMemberDPs;
    int row_index;
    TextView users;
    private DatabaseReference mRef;



    static class ViewHolder {
        TextView menu;
        TextView direction;

    }


    public MenuListAdapter(Context context, int resource, List<MenuDetails> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
        partyMemberDPs = new ArrayList<DietPattern>();

    }

    public void addPartyMemberDPs(DietPattern userDiet){
        partyMemberDPs.add(userDiet);
    }




    @RequiresApi(api = Build.VERSION_CODES.N)
    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        String users = "";

        String name = getItem(position).getName() + "";

        ///ArrayList userList = getItem(position).getUsers();
/*
        for (int i = 0 ; i < userList.size(); i++)
        {
            users = users + userList.get(i) + " | " ;
        }
*/
        String description = getItem(position).getDescription();
        String price = getItem(position).getPrice();


        //   Menu menu = new Menu(name);

        final View result;
        final munchhunt.munchhuntproject.Map.RestaurantListAdapter.ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);

            holder = new munchhunt.munchhuntproject.Map.RestaurantListAdapter.ViewHolder();


            holder.name = (TextView) convertView.findViewById(R.id.foodName);
            holder.Direction = (TextView) convertView.findViewById(R.id.userMatch);
            holder.munchRating = (TextView) convertView.findViewById(R.id.menuDescrpition);
            holder.Title = (TextView) convertView.findViewById(R.id.menuPrice);

            convertView.setTag(holder);

            result = convertView;

        } else {
            holder = (RestaurantListAdapter.ViewHolder) convertView.getTag();
            result = convertView;
        }

/*
        TextView restName = (TextView)convertView.findViewById(R.id.restName);
        TextView restDirection = (TextView)convertView.findViewById(R.id.restLocation);
        TextView restMunchRating = (TextView)convertView.findViewById(R.id.munchRating);
        restName.setText(name);
        restDirection.setText(direction);
        restMunchRating.setText(munchRating);

*/


        if(partyMemberDPs.size() != 1) {
            partyMemberDPs.sort(new DietPatternComparator());
            //   Log.d("MUNCH SCORE")
            ArrayList<String> userList = new ArrayList<>();

            for (int i = 0; i < partyMemberDPs.size(); i++) {
                DietPattern d = partyMemberDPs.get(i);
                boolean match = true;
                MenuDetails f = getItem(position);

                if (!d.getBeef() && f.isBeef()) {
                    match = false;
                }
                if (!d.getPork() && f.isPork()) {
                    match = false;
                }
                if (!d.getWhite_meat() && f.isPork()) {
                    match = false;
                }
                if (d.getNut() && f.isNuts()) {
                    match = false;

                }
                if (!d.getGluten() && f.isGluten()) {
                    match = false;

                }
                if (!d.getDairy() && f.isDairy()) {

                    match = false;
                }
                if (!d.getCrustacean() && f.isCrustacean()) {
                    match = false;

                }
                if (!d.getShellfish() && f.isShellfish()) {
                    match = false;

                }
                if (!d.getFish() && f.isFish()) {
                    match = false;

                }
                if (!d.getEggs() && f.isEggs()) {
                    match = false;
                } else if (match) {
                    userList.add(partyMemberDPs.get(i).getName());

                }
            }

            String party = "";
            for (int x = 0 ; x < userList.size(); x++)
            {
                party = party + userList.get(x) + " | " ;
            }
            holder.Direction.setText(party);
        }
        else{
            ArrayList<String> userList = new ArrayList<>();
            DietPattern d = partyMemberDPs.get(0);
            boolean match = true;
            MenuDetails f = getItem(position);

            if (!d.getBeef() && f.isBeef()) {
                match = false;
            }
            if (!d.getPork() && f.isPork()) {
                match = false;
            }
            if (!d.getWhite_meat() && f.isPork()) {
                match = false;
            }
            if (d.getNut() && f.isNuts()) {
                match = false;

            }
            if (!d.getGluten() && f.isGluten()) {
                match = false;

            }
            if (!d.getDairy() && f.isDairy()) {

                match = false;
            }
            if (!d.getCrustacean() && f.isCrustacean()) {
                match = false;

            }
            if (!d.getShellfish() && f.isShellfish()) {
                match = false;

            }
            if (!d.getFish() && f.isFish()) {
                match = false;
            }
            if (!d.getEggs() && f.isEggs()) {
                match = false;
            } else if (match) {
                holder.Direction.setText(d.getName() + " | ");

            }
        }
//
//            SocialFirebase.callCurrentUser(new CurrentUserCallback() {
//                    @Override
//                    public void callback(final User currentUser) {
//                        SocialFirebase.checkInParty(currentUser.getId(), new BooleanCallback() {
//                            @Override
//                            public void callback(boolean data) {
//                                if (data) {
//
//                                    SocialFirebase.getPartyDietPattern(currentUser, new DietPatternListCallback() {
//                                        @TargetApi(Build.VERSION_CODES.N)
//                                        @Override
//                                        public void dietPatternListCallback(List<DietPattern> dietPatterns) {
//                                            final List<DietPattern> partyMemberDPs = dietPatterns;
//                                            partyMemberDPs.sort(new DietPatternComparator());
//                                            ArrayList<String> users = new ArrayList<>();
//
//                                            for (int i = 0; i < partyMemberDPs.size(); i++) {
//                                                DietPattern d = partyMemberDPs.get(i);
//                                                boolean match = true;
//                                                MenuDetails f = getItem(position);
//
//                                                if (!d.getBeef() && f.isBeef()) {
//                                                    match = false;
//                                                }
//                                                if (!d.getPork() && f.isPork()) {
//                                                    match = false;
//                                                }
//                                                if (!d.getWhite_meat() && f.isPork()) {
//                                                    match = false;
//                                                }
//                                                if (d.getNut() && f.isNuts()) {
//                                                    match = false;
//
//                                                }
//                                                if (!d.getGluten() && f.isGluten()) {
//                                                    match = false;
//
//                                                }
//                                                if (!d.getDairy() && f.isDairy()) {
//
//                                                    match = false;
//                                                }
//                                                if (!d.getCrustacean() && f.isCrustacean()) {
//                                                    match = false;
//
//                                                }
//                                                if (!d.getShellfish() && f.isShellfish()) {
//                                                    match = false;
//
//                                                }
//                                                if (!d.getFish() && f.isFish()) {
//                                                    match = false;
//
//                                                }
//                                                if (!d.getEggs() && f.isEggs()) {
//                                                    match = false;
//                                                } else if (match) {
//                                                    users.add(partyMemberDPs.get(i).getName());
//
//                                                }
//                                            }
//
//                                            String party = "";
//                                            for (int x = 0 ; x < users.size(); x++)
//                                            {
//                                                party = party + users.get(x) + " | " ;
//                                            }
//                                            holder.Direction.setText(party);
//
//
//
//
//
//                                        }
//                                    });
//                                }
//                                else
//                                {
//                                    ArrayList<String> users = new ArrayList<>();
//                                    DietPattern d = currentUser.getDiet();
//                                    boolean match = true;
//                                    MenuDetails f = getItem(position);
//
//                                    if (!d.getBeef() && f.isBeef()) {
//                                        match = false;
//                                    }
//                                    if (!d.getPork() && f.isPork()) {
//                                        match = false;
//                                    }
//                                    if (!d.getWhite_meat() && f.isPork()) {
//                                        match = false;
//                                    }
//                                    if (d.getNut() && f.isNuts()) {
//                                        match = false;
//
//                                    }
//                                    if (!d.getGluten() && f.isGluten()) {
//                                        match = false;
//
//                                    }
//                                    if (!d.getDairy() && f.isDairy()) {
//
//                                        match = false;
//                                    }
//                                    if (!d.getCrustacean() && f.isCrustacean()) {
//                                        match = false;
//
//                                    }
//                                    if (!d.getShellfish() && f.isShellfish()) {
//                                        match = false;
//
//                                    }
//                                    if (!d.getFish() && f.isFish()) {
//                                        match = false;
//                                    }
//                                    if (!d.getEggs() && f.isEggs()) {
//                                        match = false;
//                                    } else if (match) {
//                                        holder.Direction.setText(d.getName() + " | ");
//
//                                    }
//                                }
//                            }
//
//
//                        });
//
//                    }
//
//                });
//
//




    Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition)? R.anim.loading_down_anim: R.anim.loading_up_anim);
    result.startAnimation(animation);
    lastPosition = position;

    holder.name.setText(name);
    //holder.Direction.setText(users);
    holder.munchRating.setText(description);
    holder.Title.setText(price);



    return convertView;
    }






}
