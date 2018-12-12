package munchhunt.munchhuntproject.Profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
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
import java.util.List;

import munchhunt.munchhuntproject.BottomNavigationViewHelper;
import munchhunt.munchhuntproject.Callback.CurrentUserIDCallback;
import munchhunt.munchhuntproject.Callback.FriendListCallback;
import munchhunt.munchhuntproject.Callback.UserCallback;
import munchhunt.munchhuntproject.Map.MapsActivity;
import munchhunt.munchhuntproject.Notifications.NotificationsPage;
import munchhunt.munchhuntproject.Party.SocialFirebase;
import munchhunt.munchhuntproject.DeprecatedClasses.SocialPage;
import munchhunt.munchhuntproject.R;
import munchhunt.munchhuntproject.Objects.User;

public class ViewFriendsListPage extends AppCompatActivity {

    private String currentUserID;
    private User currentUser;
    private FirebaseDatabase database;
    private FirebaseUser mUser;
    private String userToBeCalledID;
    private User userToBeCalled;


    private BottomNavigationView navBar;
    private ListView mViewFriendsList;
    DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_viewfriendslistpage);
        callCurrentUser();
        initializeElements();
        initializeNavigationBar();

        mViewFriendsList = (ListView) findViewById(R.id.lvViewFriendsList);
        final ArrayList<String> friendsList = new ArrayList<String>();
        final ArrayAdapter<String> friendsAdapter = new ArrayAdapter<>(this, R.layout.social_adapter_friendview);
        mViewFriendsList.setAdapter(friendsAdapter);
        final FirebaseAuth auth = FirebaseAuth.getInstance();
        SocialFirebase.readCurrentUserID(new CurrentUserIDCallback() {
            @Override
            public void callback(String currentUserID) {

                SocialFirebase.autoUpdateFriend(currentUserID, new FriendListCallback() {

                    @Override
                    public void callback(final List<String> friendsList) {
                        friendsList.clear();
                        friendsAdapter.clear();

                        for (int i = 0; i < friendsList.size(); i++) {


                            friendsList.add(friendsList.get(i));
                            SocialFirebase.readUser(friendsList.get(i), new UserCallback<User>() {
                                @Override
                                public void callback(User user) {
                                    friendsList.add(user.getName());
                                    friendsAdapter.notifyDataSetChanged();

                                }
                            });

                        }

                    }


                });

            }
        });


    }

    private void initializeElements(){

    }

    private void initializeNavigationBar(){
        navBar = (BottomNavigationView) findViewById(R.id.navigationBar);
        BottomNavigationViewHelper.disableShiftMode(navBar);
        android.view.Menu menu = navBar.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);
        navBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.navigation_map:
                        startActivity(new Intent(ViewFriendsListPage.this, MapsActivity.class));
                        item.setIcon(R.drawable.ic_map);
                        break;
                    case R.id.navigation_social:
                        startActivity(new Intent(ViewFriendsListPage.this, SocialPage.class));
                        item.setIcon(R.drawable.ic_party);
                        break;
                    case R.id.navigation_notifications:
                        startActivity(new Intent(ViewFriendsListPage.this, NotificationsPage.class));
                        item.setIcon(R.drawable.ic_notifications);
                        break;
                    case R.id.navigation_profile:
                        startActivity(new Intent(ViewFriendsListPage.this, Profile.class));
                        item.setIcon(R.drawable.ic_profile);
                        break;

                }
                return false;
            }
        });
    }

    public void callCurrentUser(){


        final FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull final FirebaseAuth firebaseAuth) {
                final FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    currentUserID = user.getUid();

                    //4  Toast.makeText(Profile.this, "USER ID\n"+currentUser,Toast.LENGTH_SHORT).show();

                } else {
                    //  Toast.makeText(Profile.this, "USER ID\n"+ currentUser,Toast.LENGTH_SHORT).show();
                }
            }
        });

        myRef = database.getInstance().getReference().child("Users");

        ValueEventListener valueEventListener = myRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {


                    User user = ds.getValue(User.class);

                    if(currentUserID.equals(user.getId())){
                        currentUser = user;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



}
