package munchhunt.munchhuntproject.Map;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import munchhunt.munchhuntproject.BottomNavigationViewHelper;
import munchhunt.munchhuntproject.Callback.BooleanCallback;
import munchhunt.munchhuntproject.Callback.CurrentUserCallback;
import munchhunt.munchhuntproject.Notifications.NotificationsPage;
import munchhunt.munchhuntproject.Objects.MenuDetails;
import munchhunt.munchhuntproject.Objects.Restaurant;
import munchhunt.munchhuntproject.Objects.User;
import munchhunt.munchhuntproject.Party.CreateOrJoinPage;
import munchhunt.munchhuntproject.Party.PartyPage;
import munchhunt.munchhuntproject.Party.SocialFirebase;
import munchhunt.munchhuntproject.Profile.Profile;
import munchhunt.munchhuntproject.R;


public class MenuView extends AppCompatActivity {
    private DatabaseReference mRef;

    RestaurantInfoFragment restaurantInfoFragment;
    RestaurantMenuFragment restaurantMenuFragment;

    BottomNavigationView navBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maplist_menu);
        initializeNavigationBar();

        restaurantInfoFragment = new RestaurantInfoFragment();
        restaurantMenuFragment = new RestaurantMenuFragment();

        mRef = FirebaseDatabase.getInstance().getReference();

        ViewPager pager = (ViewPager) findViewById(R.id.viewPager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabDots);
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(pager);

    }

    public void readMenu(final RestaurantMenuCallback myCallback, String id)
    {
        mRef = FirebaseDatabase.getInstance().getReference();
        mRef.child("RestaurantData").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
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

    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            switch(pos) {

                case 0: return RestaurantInfoFragment.newInstance("Restaurant Info");
                case 1: return RestaurantMenuFragment.newInstance("Restaurant Menu");
                default: return RestaurantInfoFragment.newInstance("Restaurant Info");
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

    public Bundle getMyData(){
        Bundle b = this.getIntent().getExtras();
        final Restaurant r = (Restaurant) b.getSerializable("obj");

        // Restaurant Name
        TextView mRestaurantName = (TextView) findViewById(R.id.tvRestaurantName);
        mRestaurantName.setText(r.getName());

        Bundle hm = new Bundle();
        hm.putSerializable("restaurant", r);

        return hm;

    }

    private void initializeNavigationBar(){
        // Bottom toolbar
        navBar = (BottomNavigationView) findViewById(R.id.navigationBar);
        BottomNavigationViewHelper.disableShiftMode(navBar);
        Menu menu = navBar.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);
        navBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_map:
                        startActivity(new Intent(MenuView.this, MapsActivity.class));
                        item.setIcon(R.drawable.ic_map);
                        break;
                    case R.id.navigation_social:
                        SocialFirebase.callCurrentUser(new CurrentUserCallback() {
                            @Override
                            public void callback(User currentUser) {
                                SocialFirebase.checkInParty(currentUser.getId(), new BooleanCallback() {
                                    @Override
                                    public void callback(boolean data) {
                                        if(data){
                                            startActivity(new Intent(MenuView.this, PartyPage.class));
                                        }
                                        else{
                                            startActivity(new Intent(MenuView.this, CreateOrJoinPage.class));
                                        }
                                    }
                                });
                            }
                        });

                        item.setIcon(R.drawable.ic_party);
                        break;
                    case R.id.navigation_notifications:
                        startActivity(new Intent(MenuView.this, NotificationsPage.class));
                        item.setIcon(R.drawable.ic_notifications);
                        break;
                    case R.id.navigation_profile:
                        startActivity(new Intent(MenuView.this, Profile.class));
                        item.setIcon(R.drawable.ic_profile);
                        break;

                }
                return false;
            }
        });
    }


}



