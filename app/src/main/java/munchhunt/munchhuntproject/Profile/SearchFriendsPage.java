package munchhunt.munchhuntproject.Profile;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import munchhunt.munchhuntproject.BottomNavigationViewHelper;
import munchhunt.munchhuntproject.Callback.BooleanCallback;
import munchhunt.munchhuntproject.Callback.CurrentUserCallback;
import munchhunt.munchhuntproject.Map.MapsActivity;
import munchhunt.munchhuntproject.Notifications.NotificationsPage;
import munchhunt.munchhuntproject.Party.CreateOrJoinPage;
import munchhunt.munchhuntproject.Party.PartyPage;
import munchhunt.munchhuntproject.Party.SocialFirebase;
import munchhunt.munchhuntproject.DeprecatedClasses.SocialPage;
import munchhunt.munchhuntproject.R;
import munchhunt.munchhuntproject.Objects.User;

public class SearchFriendsPage extends AppCompatActivity {

    private String currentUserID;
    private User currentUser;
    private DatabaseReference myRef;
    private FirebaseDatabase database;
    private FirebaseUser mUser;
    private String userToBeCalledID;
    private User userToBeCalled;
    private ListView lv_autoCompUser;
    private EditText userSearch;

    BottomNavigationView navBar;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_searchfriendspage);

        initializeNavigationBar();
        lv_autoCompUser= (ListView) findViewById(R.id.lv_UserSearchList);
        userSearch = (EditText) findViewById(R.id.et_FriendSearch);
        final List<String> matchinguserIDs = new ArrayList<String>();
        final FriendAdderAdapter faa = new FriendAdderAdapter(this, R.layout.profile_friendadderview, matchinguserIDs);
        lv_autoCompUser.setAdapter(faa);
        userSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (userSearch.getText().toString().isEmpty() || userSearch.getText().toString().length() < 3){
                    matchinguserIDs.clear();
                    faa.clear();
                }
                else{
                    SocialFirebase.callCurrentUser(new CurrentUserCallback() {
                        @Override
                        public void callback(final User currentUser) {

                            myRef = FirebaseDatabase.getInstance().getReference();
                            myRef.child("Users").addValueEventListener(new ValueEventListener() {
                                @TargetApi(Build.VERSION_CODES.N)
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    matchinguserIDs.clear();
                                    faa.clear();
                                    for (DataSnapshot suggestionSnapshot : dataSnapshot.getChildren()) {
                                        String name = suggestionSnapshot.child("name").getValue(String.class);
                                        final String id = suggestionSnapshot.child("id").getValue(String.class);
                                        if(!name.equals(currentUser.getName()) && name.toLowerCase().replaceAll("\\s+","").contains(userSearch.getText().toString().toLowerCase().replaceAll("\\s+",""))) {
                                            SocialFirebase.checkMutual(currentUser, id, new BooleanCallback() {
                                                @Override
                                                public void callback(boolean data) {
                                                    if(!data){
                                                        matchinguserIDs.add(id);
                                                        faa.notifyDataSetChanged();
                                                    }
                                                }
                                            });
                                        }
                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                    });



                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

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
                        startActivity(new Intent(SearchFriendsPage.this, MapsActivity.class));
                        item.setIcon(R.drawable.ic_map);
                        break;
                    case R.id.navigation_social:
                        SocialFirebase.callCurrentUser(new CurrentUserCallback() {
                            @Override
                            public void callback(User currentUser) {
                                Log.d("current user", currentUser.getId());
                                SocialFirebase.checkInParty(currentUser.getId(), new BooleanCallback() {
                                    @Override
                                    public void callback(boolean data) {
                                        if(data){
                                            startActivity(new Intent(SearchFriendsPage.this, PartyPage.class));
                                        }
                                        else{
                                            startActivity(new Intent(SearchFriendsPage.this, CreateOrJoinPage.class));
                                        }
                                    }
                                });
                            }
                        });
                        item.setIcon(R.drawable.ic_party);
                        break;
                    case R.id.navigation_notifications:
                        startActivity(new Intent(SearchFriendsPage.this, NotificationsPage.class));
                        item.setIcon(R.drawable.ic_notifications);
                        break;
                    case R.id.navigation_profile:
                        startActivity(new Intent(SearchFriendsPage.this, Profile.class));
                        item.setIcon(R.drawable.ic_profile);
                        break;

                }
                return false;
            }
        });
    }


}
