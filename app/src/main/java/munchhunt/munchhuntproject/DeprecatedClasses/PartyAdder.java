package munchhunt.munchhuntproject.DeprecatedClasses;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import munchhunt.munchhuntproject.Callback.CurrentUserIDCallback;
import munchhunt.munchhuntproject.Callback.FriendListCallback;
import munchhunt.munchhuntproject.Callback.StringCallback;
import munchhunt.munchhuntproject.Callback.UserCallback;
import munchhunt.munchhuntproject.Party.SocialFirebase;
import munchhunt.munchhuntproject.R;
import munchhunt.munchhuntproject.Objects.User;

public class PartyAdder extends AppCompatActivity{

    private String currentUserID;
    private User currentUser;
    private DatabaseReference myRef;
    private FirebaseDatabase database;
    private FirebaseUser mUser;
    private String userToBeCalledID;
    private User userToBeCalled;
    private ListView mFriendList;
    private Button addFriend, doneFriend;
    private TextView friendSearch;



    // GUI Elements
    BottomNavigationView navBar;
    private AutoCompleteTextView actv;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.social_friendadder);
        callCurrentUser();
        // Navigation Bar
        navBar = (BottomNavigationView) findViewById(R.id.navigationBar);


        // Autocomplete, searches for users in the database
        mFriendList =  (ListView) findViewById(R.id.friendList);
        addFriend = (Button)findViewById(R.id.addFriendAdder);
        doneFriend = (Button)findViewById(R.id.doneFriendAdder);
        friendSearch = (TextView)findViewById(R.id.friendSearch);

        final ArrayList<String>  friends = new ArrayList<String>();
        final ArrayAdapter<String> friendsName = new ArrayAdapter<>(this, R.layout.social_adapter_friendview);
        mFriendList.setAdapter(friendsName);
        final FirebaseAuth auth = FirebaseAuth.getInstance();
        SocialFirebase.readCurrentUserID(new CurrentUserIDCallback() {
            @Override
            public void callback(String currentUserID) {

                SocialFirebase.autoUpdateFriend(currentUserID, new FriendListCallback() {

                    @Override
                    public void callback(final List<String> friendsList) {
                        friends.clear();
                        friendsName.clear();

                        for (int i = 0; i < friendsList.size(); i++) {

                            friends.add(friendsList.get(i));
                            SocialFirebase.readUser(friendsList.get(i), new UserCallback<User>() {
                                @Override
                                public void callback(User user) {
                                        friendsName.add(user.getName());
                                        friendsName.notifyDataSetChanged();

                                }
                            });

                        }

                        Log.d("Friends ID", friends.toString());
                        Log.d("Friends Name", friendsName.toString());
                    }


                });

            }
        });

        mFriendList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                String friendToRemove = friendsName.getItem(i);
                SocialFirebase.readUserIDbyName(friendToRemove, new StringCallback() {
                    @Override
                    public void callback(String stringToCallback) {
                        SocialFirebase.removeFriend(stringToCallback);
                    }
                });


            }
        });



        myRef = FirebaseDatabase.getInstance().getReference();
        final ArrayAdapter<String> autoComplete = new ArrayAdapter<>(this, R.layout.social_adapter_friend);
        final ArrayList<String> userIDs = new ArrayList<String>();
        SocialFirebase.readCurrentUserID(new CurrentUserIDCallback() {
            @Override
            public void callback(final String currentUserID) {
                myRef.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot suggestionSnapshot : dataSnapshot.getChildren()) {
                            String suggestion = suggestionSnapshot.child("name").getValue(String.class);
                            String id = suggestionSnapshot.child("id").getValue(String.class);
                            if(!id.equals(currentUserID)) {
                                autoComplete.add(suggestion);
                                userIDs.add(id);
                                autoComplete.notifyDataSetChanged();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        actv = (AutoCompleteTextView) findViewById(R.id.friendSearch);
        actv.setAdapter(autoComplete);
        actv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userToBeAddedName = actv.getText().toString();
                SocialFirebase.readUserIDbyName(userToBeAddedName, new StringCallback() {
                    @Override
                    public void callback(String stringToCallback) {
                        Log.d("USER TO BE ADDED", stringToCallback);

                        SocialFirebase.addFriendBothWays(currentUser, stringToCallback);

                    }
                });
            }
        });



        doneFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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



//    public void button_AddtoParty(View view) {
//
//        SocialFirebase.getUserParty(currentUser.getId(), new PartyCallback<Party>() {
//            @Override
//            public void callback(Party party) {
//                //do whatever with that party. (either show it on the GUI or nest it with another callback
//                //to do something like the next picture
//            }
//        });
//
//
//    }

}