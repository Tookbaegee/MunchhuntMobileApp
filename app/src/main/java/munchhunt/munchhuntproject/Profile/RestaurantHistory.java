package munchhunt.munchhuntproject.Profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import munchhunt.munchhuntproject.BottomNavigationViewHelper;
import munchhunt.munchhuntproject.Map.MapsActivity;
import munchhunt.munchhuntproject.DeprecatedClasses.SocialPage;
import munchhunt.munchhuntproject.R;
import munchhunt.munchhuntproject.Objects.History;
import munchhunt.munchhuntproject.Objects.User;

/**
 * Created by kyl3g on 7/31/2018.
 */

public class RestaurantHistory extends AppCompatActivity {

    private ListView mRestaurantHistory;
    BottomNavigationView navBar;
    private FirebaseDatabase mFirebaseDatabase;
    FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    ArrayList<String> listItems = new ArrayList<String>();

    private DatabaseReference myRef;
    private String userID;
    private String restaurantName;
    private FirebaseUser mUser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_restauranthistory);
        mRestaurantHistory = findViewById(R.id.restaurantHistory);
        final ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);
        mRestaurantHistory.setAdapter(itemsAdapter);

        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();

        myRef.child("Users").child(mUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User u = (User)dataSnapshot.getValue(User.class);
                itemsAdapter.clear();

                ArrayList<History> history = (ArrayList)u.getHistory();
                for (int i = 0; i < 1; i++)
                {
                    restaurantName = history.get(0).getName();
                    itemsAdapter.add(restaurantName);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        navBar = (BottomNavigationView) findViewById(R.id.navigationBar);
        BottomNavigationViewHelper.disableShiftMode(navBar);
        Menu menu = navBar.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);
        navBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.navigation_map:
                        startActivity(new Intent(RestaurantHistory.this, MapsActivity.class));
                        item.setIcon(R.drawable.ic_map);
                        break;
                    case R.id.navigation_social:
                        startActivity(new Intent(RestaurantHistory.this, SocialPage.class));
                        item.setIcon(R.drawable.ic_party);
                        break;
                    case R.id.navigation_profile:
                        startActivity(new Intent(RestaurantHistory.this, Profile.class));
                        item.setIcon(R.drawable.ic_profile);
                        break;

                }
                return false;
            }
        });


    }
}
