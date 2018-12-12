package munchhunt.munchhuntproject.DeprecatedClasses;

import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import munchhunt.munchhuntproject.BottomNavigationViewHelper;
import munchhunt.munchhuntproject.Callback.CurrentUserCallback;
import munchhunt.munchhuntproject.Callback.FriendListCallback;
import munchhunt.munchhuntproject.Callback.PartyCallback;
import munchhunt.munchhuntproject.Callback.UserCallback;
import munchhunt.munchhuntproject.Map.MapsActivity;
import munchhunt.munchhuntproject.Notifications.NotificationsPage;
import munchhunt.munchhuntproject.Objects.Party;
import munchhunt.munchhuntproject.Party.PartyAdderAdapter;
import munchhunt.munchhuntproject.Party.PartyAdderDialog;
import munchhunt.munchhuntproject.Party.SocialFirebase;
import munchhunt.munchhuntproject.Profile.FriendView;
import munchhunt.munchhuntproject.Profile.GridViewAdapter;
import munchhunt.munchhuntproject.Profile.Profile;
import munchhunt.munchhuntproject.R;
import munchhunt.munchhuntproject.Objects.User;

/**
 * Created by kyl3g on 9/21/2018.
 */

public class SocialPage extends FragmentActivity implements PartyAdderDialog.NoticeDialogListener {

    private ViewStub stubGrid;
    private GridView gridView;
    private GridViewAdapter gridViewAdapter;

    private DatabaseReference mDatabase;

    // GUI elements
    private BottomNavigationView navBar;
    private RelativeLayout mRelativeLayout;
    BottomSheetBehavior mBottomSheetBehavior;

    private List<FriendView> currentParty;

    private ListView mFriendsList;
    private PartyAdderAdapter faa;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.social_page);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mRelativeLayout = (RelativeLayout) findViewById(R.id.bottomSlide);
        mBottomSheetBehavior = BottomSheetBehavior.from(mRelativeLayout);

        stubGrid = (ViewStub) findViewById(R.id.stub_grid);
        stubGrid.inflate();
        gridView = (GridView) findViewById(R.id.mygridview);
        set();
        gridView.setOnItemClickListener(onItemClick);

        final ArrayList<String> list = new ArrayList<String>();
        mFriendsList = (ListView) findViewById(R.id.lvFriendsList);
        faa = new PartyAdderAdapter(this, R.layout.social_partyadderview, list);
        mFriendsList.setAdapter(faa);
        SocialFirebase.callCurrentUser(new CurrentUserCallback() {
            @Override
            public void callback(User currentUser) {
                SocialFirebase.autoUpdateFriend(currentUser.getId(), new FriendListCallback() {
                    @Override
                    public void callback(List<String> friendsList) {
                        faa.clear();
                        list.clear();
                        for(int i = 1; i < friendsList.size(); i++){
                            list.add(friendsList.get(i));
                            faa.notifyDataSetChanged();
                        }
                    }
                });
            }
        });
        mFriendsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                set();
            }
        });


        initializeNavigationBar();

    }


    private void set() {
        currentParty = new ArrayList<FriendView>();
        SocialFirebase.callCurrentUser(new CurrentUserCallback() {
            @Override
            public void callback(final User currentUser) {
                SocialFirebase.autoUpdateUserParty(currentUser, new PartyCallback<Party>() {
                    @Override
                    public void callback(Party party) {
                        //currentParty.add(new FriendView(R.drawable.adrianehot, party.getId()));
                        if(currentParty.size() > 0){
                            currentParty.clear();
                        }
                        Log.d("callback accessed", party.getId());

                        for (int i = 0; i < party.getparty().size(); i++){
                            Log.d("accessed", party.getparty().get(i).toString());
                            SocialFirebase.readUser(party.getparty().get(i), new UserCallback<User>() {
                                @Override
                                public void callback(User user) {
                                    currentParty.add(new FriendView(user));
                                    gridViewAdapter = new GridViewAdapter(SocialPage.this, R.layout.grid_item, currentParty);
                                    gridView.setAdapter(gridViewAdapter);
                                }
                            });

                        }

                    }
                });

            }
        });

    }

//    public void getCurrentParty() {
//        currentParty = new ArrayList<FriendView>();
//        SocialFirebase.callCurrentUser(new CurrentUserCallback() {
//            @Override
//            public void callback(final User currentUser) {
//
//                SocialFirebase.autoUpdateUserParty(currentUser, new PartyCallback<Party>() {
//                @Override
//                public void callback(Party party) {
//                    for (int i = 0; i < party.getparty().size(); i++){
//                        SocialFirebase.readUser(party.getparty().get(i), new UserCallback<User>() {
//                            @Override
//                            public void callback(User user) {
//                                currentParty.add(new FriendView(R.drawable.adrianehot, user.getName()));
//                                setAdapter();
//                            }
//                        });
//
//                        }
//                    }
//                });
//
//            }
//        });
//
//    }

    AdapterView.OnItemClickListener onItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //Toast.makeText(getApplicationContext(), currentParty.get(position).getName(), Toast.LENGTH_SHORT).show();

        }
    };

    private void initializeNavigationBar(){
        // Bottom toolbar
        navBar = (BottomNavigationView) findViewById(R.id.navigationBar);
        BottomNavigationViewHelper.disableShiftMode(navBar);
        Menu menu = navBar.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);
        navBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_map:
                        startActivity(new Intent(SocialPage.this, MapsActivity.class));
                        item.setIcon(R.drawable.ic_map);
                        break;
                    case R.id.navigation_social:
                        startActivity(new Intent(SocialPage.this, SocialPage.class));
                        item.setIcon(R.drawable.ic_party);
                        break;
                    case R.id.navigation_notifications:
                        startActivity(new Intent(SocialPage.this, NotificationsPage.class));
                        item.setIcon(R.drawable.ic_notifications);
                        break;
                    case R.id.navigation_profile:
                        startActivity(new Intent(SocialPage.this, Profile.class));
                        item.setIcon(R.drawable.ic_profile);
                        break;

                }
                return false;
            }
        });
    }

//    public void showNoticeDialog(){
//        DialogFragment dialog = new FriendAdderDialog();
//        dialog.show(getSupportFragmentManager(), "FriendAdderDialog");
//    }

    public void onDialogPositiveClick(DialogFragment dialog){

    }

    public void onDialogNegativeClick(DialogFragment dialog){

    }

    private void dietCheck(Drawable whiteIcon, Drawable colorIcon, boolean dietCheck){

    }

}