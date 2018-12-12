package munchhunt.munchhuntproject.Profile;

import android.content.Context;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import munchhunt.munchhuntproject.R;


public class GridViewAdapter extends ArrayAdapter<FriendView> {
    public GridViewAdapter(Context context, int resource, List<FriendView> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if(null == v) {
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.grid_item, null);
        }
        ImageView ivPork = (ImageView) v.findViewById(R.id.porkCheck);
        ImageView ivBeef = (ImageView) v.findViewById(R.id.beefCheck);
        ImageView ivFish = (ImageView) v.findViewById(R.id.fishCheck);
        ImageView ivCrustacean = (ImageView) v.findViewById(R.id.crustaceanCheck);
        ImageView ivShellfish = (ImageView) v.findViewById(R.id.shellfishCheck);
        ImageView ivWhiteMeat = (ImageView) v.findViewById(R.id.whiteMeatcheck);
        ImageView ivNuts = (ImageView) v.findViewById(R.id.nutsCheck);
        ImageView ivGluten = (ImageView) v.findViewById(R.id.glutenCheck);
        ImageView ivDairy = (ImageView) v.findViewById(R.id.dairyCheck);
        ImageView ivEggs = (ImageView) v.findViewById(R.id.eggsCheck);

        // Color bars
        View mPorkBar = (View) v.findViewById(R.id.porkBar);
        View mBeefBar = (View) v.findViewById(R.id.beefBar);
        View mFishBar = (View) v.findViewById(R.id.fishBar);
        View mCrustaceanBar = (View) v.findViewById(R.id.crustaceanBar);
        View mShellfishBar = (View) v.findViewById(R.id.shellfishBar);
        View mWhiteMeatBar = (View) v.findViewById(R.id.whiteMeatBar);
        View mGlutenBar = (View) v.findViewById(R.id.glutenBar);
        View mNutsBar = (View) v.findViewById(R.id.nutsBar);
        View mDairyBar = (View) v.findViewById(R.id.dairyBar);
        View mEggsBar = (View) v.findViewById(R.id.eggsBar);

        FriendView friend = getItem(position);
        de.hdodenhof.circleimageview.CircleImageView img = v.findViewById(R.id.imageView);
        TextView txtTitle = (TextView) v.findViewById(R.id.txtTitle);

        if(friend.getUser().getDiet().getPork() == true){ dietCheck(ivPork, mPorkBar, R.drawable.ic_pork_col, R.color.hot_pink); }
        if(friend.getUser().getDiet().getBeef() == true){ dietCheck(ivBeef, mBeefBar, R.drawable.ic_beef_col, R.color.hot_pink); }
        if(friend.getUser().getDiet().getFish() == true){ dietCheck(ivFish, mFishBar, R.drawable.ic_seafood_col, R.color.hot_pink); }
        if(friend.getUser().getDiet().getCrustacean() == true){ dietCheck(ivCrustacean, mCrustaceanBar, R.drawable.ic_crustacean_col, R.color.hot_pink); }
        if(friend.getUser().getDiet().getShellfish() == true){ dietCheck(ivShellfish, mShellfishBar, R.drawable.ic_shellfish_col, R.color.hot_pink); }
        if(friend.getUser().getDiet().getWhite_meat() == true){ dietCheck(ivWhiteMeat, mWhiteMeatBar, R.drawable.ic_whitemeat_col, R.color.hot_pink); }
        if(friend.getUser().getDiet().getGluten() == true){ dietCheck(ivGluten, mGlutenBar, R.drawable.ic_gluten_col, R.color.hot_pink); }
        if(friend.getUser().getDiet().getNut() == true){ dietCheck(ivNuts, mNutsBar, R.drawable.ic_peanut_col, R.color.hot_pink); }
        if(friend.getUser().getDiet().getDairy() == true){ dietCheck(ivDairy, mDairyBar, R.drawable.ic_dairy_col, R.color.hot_pink); }
        if(friend.getUser().getDiet().getEggs() == true){ dietCheck(ivEggs, mEggsBar, R.drawable.ic_egg_col, R.color.hot_pink); }


        txtTitle.setText(friend.getUser().getName());


        return v;
    }

    private void dietCheck(ImageView dietIcon, View dietBar, int coloredIcon, int barColor){
        dietIcon.setImageResource(coloredIcon);
        dietBar.setBackgroundResource(R.color.hot_pink);

    }
}
