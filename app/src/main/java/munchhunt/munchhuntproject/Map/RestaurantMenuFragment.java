package munchhunt.munchhuntproject.Map;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import munchhunt.munchhuntproject.Callback.BooleanCallback;
import munchhunt.munchhuntproject.Callback.CurrentUserCallback;
import munchhunt.munchhuntproject.Callback.DietPatternCallback;
import munchhunt.munchhuntproject.Callback.PartyCallback;
import munchhunt.munchhuntproject.Objects.MenuDetails;
import munchhunt.munchhuntproject.Objects.Party;
import munchhunt.munchhuntproject.Objects.Restaurant;
import munchhunt.munchhuntproject.Party.SocialFirebase;
import munchhunt.munchhuntproject.R;
import munchhunt.munchhuntproject.Objects.DietPattern;
import munchhunt.munchhuntproject.Objects.User;

public class RestaurantMenuFragment extends Fragment {

    Restaurant r;
    ListView menuListView;
    private DatabaseReference mRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.maplist_restaurantmenu_fragment, container, false);

        MenuView activity = (MenuView) getActivity();
        Bundle results = activity.getMyData();

        r = (Restaurant) results.getSerializable("restaurant");

        final List<MenuDetails> menuDetailsList = new ArrayList<MenuDetails>();
        final MenuListAdapter mla = new MenuListAdapter(getActivity(), R.layout.maplist_adapter_menuview, menuDetailsList);
        menuListView = (ListView) v.findViewById(R.id.menuListView);

        readMenu(new RestaurantMenuCallback() {
            @Override
            public void onCallback(ArrayList<MenuDetails> menu) {
                for (int i = 0; i < menu.size(); i++)
                {
                    menuDetailsList.add(menu.get(i));
                    mla.notifyDataSetChanged();
                    menu.get(i).setId(r.getId());
                }
            }
        },r.getId());

        SocialFirebase.callCurrentUser(new CurrentUserCallback() {

            @Override
            public void callback(final User currentUser) {
                SocialFirebase.checkInParty(currentUser.getId(), new BooleanCallback() {
                    @Override
                    public void callback(boolean data) {
                        if (data) {

                            SocialFirebase.autoUpdateUserParty(currentUser, new PartyCallback<Party>() {
                                @Override
                                public void callback(Party party) {
                                    final List<String> partyMembers = party.getparty();
                                    for(int i = 0; i < partyMembers.size(); i++){
                                        SocialFirebase.readUserDietbyID(partyMembers.get(i), new DietPatternCallback() {
                                            @Override

                                            public void dietPatternCallback(DietPattern dietPattern) {
                                                mla.addPartyMemberDPs(dietPattern);
                                                mla.notifyDataSetChanged();
                                                menuListView.setAdapter(mla);

                                            }
                                        });
                                    }
                                }
                            });

                        } else {
                            mla.addPartyMemberDPs(currentUser.getDiet());
                            mla.notifyDataSetChanged();
                            menuListView.setAdapter(mla);
                        }
                    }
                });


            }
        });

        menuListView.setAdapter(mla);
        return v;
    }

    public static RestaurantMenuFragment newInstance(String text) {

        RestaurantMenuFragment f = new RestaurantMenuFragment();
        return f;
    }

    public void putArguments(Bundle args){
        r = (Restaurant) args.getSerializable("restaurant");
    }

    public void readMenu(final RestaurantMenuCallback myCallback, String id)
    {
        mRef = FirebaseDatabase.getInstance().getReference();
        mRef.child("RestaurantData").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("callback", "database accessed");
                //    Restaurant r = (Restaurant)dataSnapshot.getValue(Restaurant.class);

                GenericTypeIndicator<ArrayList<MenuDetails>> t = new GenericTypeIndicator<ArrayList<MenuDetails>>() {};

                ArrayList<MenuDetails> m = (ArrayList<MenuDetails>) dataSnapshot.child("menus").child("menus").getValue(t);
                myCallback.onCallback(m);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
