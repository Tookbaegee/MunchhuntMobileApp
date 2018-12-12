//package munchhunt.munchhuntproject.DeprecatedClasses;
//
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.design.widget.BottomNavigationView;
//import android.support.v7.app.AppCompatActivity;
//import android.util.Log;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.AutoCompleteTextView;
//import android.widget.Button;
//import android.widget.ListView;
//import android.widget.TextView;
//
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import munchhunt.munchhuntproject.Callback.CurrentUserCallback;
//import munchhunt.munchhuntproject.Callback.FriendListCallback;
//import munchhunt.munchhuntproject.Callback.PartyCallback;
//import munchhunt.munchhuntproject.Callback.StringCallback;
//import munchhunt.munchhuntproject.Callback.UserCallback;
//import munchhunt.munchhuntproject.Objects.Party;
//import munchhunt.munchhuntproject.Party.SocialFirebase;
//import munchhunt.munchhuntproject.R;
//import munchhunt.munchhuntproject.Objects.User;
//
//public class PartyCreator extends AppCompatActivity{
//
//    private String currentUserID;
//    private User currentUser;
//    private DatabaseReference myRef;
//    private FirebaseDatabase database;
//    private FirebaseUser mUser;
//    private String userToBeCalledID;
//    private User userToBeCalled;
//
//    // GUI Elements
//    BottomNavigationView navBar;
//    private AutoCompleteTextView actv;
//    private ListView mPartyList;
//    private TextView partySearch;
//    private Button partyAdd, partyDone;
//
//
//
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.social_partycreator);
//        callCurrentUser();
//        // Navigation Bar
//
//
//        // Autocomplete, searches for users in the database
//        mPartyList = (ListView) findViewById(R.id.partyList);
//        partySearch = (TextView)findViewById(R.id.partySearch);
//        partyAdd = (Button)findViewById(R.id.addPartyCreator);
//        partyDone = (Button)findViewById(R.id.donePartyCreator);
//
//        final ArrayList<String> partyMembersID = new ArrayList<String>();
//        final ArrayAdapter<String> partyMembersName = new ArrayAdapter<>(this, R.layout.social_adapter_partymemberview);
//        mPartyList.setAdapter(partyMembersName);
//        SocialFirebase.callCurrentUser(new CurrentUserCallback() {
//            @Override
//            public void callback(User currentUser) {
//                SocialFirebase.autoUpdateUserParty(currentUser, new PartyCallback<Party>() {
//                    @Override
//                    public void callback(Party party) {
//                        partyMembersID.clear();
//                        partyMembersName.clear();
//                        List<String> friends = party.getparty();
//                        for(int i = 0; i < friends.size(); i++) {
//                            partyMembersID.add(friends.get(i));
//                            SocialFirebase.readUser(friends.get(i), new UserCallback<User>() {
//                                @Override
//                                public void callback(User user) {
//                                    partyMembersName.add(user.getName());
//                                    partyMembersName.notifyDataSetChanged();
//                                }
//                            });
//
//                        }
//                    }
//                });
//            }
//        });
//        mPartyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
//                Log.d("Current Party Members", partyMembersID.toString());
//                String memberToRemove = partyMembersName.getItem(i);
//                SocialFirebase.readUserIDbyName(memberToRemove, new StringCallback() {
//                    @Override
//                    public void callback(String stringToCallback) {
//                        SocialFirebase.leaveFromParty(stringToCallback);
//                    }
//                });
//                partyMembersName.remove(memberToRemove);
//
//            }
//        });
//
//
//
//
//
//
//        // NEED TO CONNECT TO USER'S FRIENDLIST
//        myRef = FirebaseDatabase.getInstance().getReference();
//        final ArrayAdapter<String> autoCompleteName = new ArrayAdapter<>(this, R.layout.social_adapter_friend);
//        final ArrayList<String> autoCompleteID  = new ArrayList<String>();
//        SocialFirebase.callCurrentUser(new CurrentUserCallback() {
//            @Override
//            public void callback(User currentUser) {
//                SocialFirebase.autoUpdateFriend(currentUser.getId(), new FriendListCallback() {
//                    @Override
//                    public void callback(List<String> friendsList) {
//                        autoCompleteName.clear();
//                        autoCompleteID.clear();
//                       for(int i = 0; i < friendsList.size(); i++) {
//                           if(!autoCompleteID.contains(friendsList.get(i))) {
//                               autoCompleteID.add(friendsList.get(i));
//                           }
//                           SocialFirebase.readUser(autoCompleteID.get(i), new UserCallback<User>() {
//                               @Override
//                               public void callback(User user) {
//                                   autoCompleteName.add(user.getName());
//                                   autoCompleteName.notifyDataSetChanged();
//                               }
//                           });
//                       }
//                    }
//                });
//            }
//        });
//        actv = (AutoCompleteTextView) findViewById(R.id.partySearch);
//        actv.setAdapter(autoCompleteName);
//        actv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
//
//            }
//        });
//
//        partyAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String userToBeAddedName = actv.getText().toString();
//                SocialFirebase.readUserIDbyName(userToBeAddedName, new StringCallback() {
//                    @Override
//                    public void callback(String stringToCallback) {
//                        SocialFirebase.addToParty(currentUser,stringToCallback);
//                    }
//
//                });
//
//            }
//        });
//
//        partyDone.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//
//    }
//
//
//
//    public void callCurrentUser(){
//
//
//        final FirebaseAuth auth = FirebaseAuth.getInstance();
//        auth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull final FirebaseAuth firebaseAuth) {
//                final FirebaseUser user = firebaseAuth.getCurrentUser();
//
//                if (user != null) {
//                    currentUserID = user.getUid();
//
//                    //4  Toast.makeText(Profile.this, "USER ID\n"+currentUser,Toast.LENGTH_SHORT).show();
//
//                } else {
//                    //  Toast.makeText(Profile.this, "USER ID\n"+ currentUser,Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//        myRef = database.getInstance().getReference().child("Users");
//
//        ValueEventListener valueEventListener = myRef.addValueEventListener(new ValueEventListener() {
//
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                for (DataSnapshot ds : dataSnapshot.getChildren()) {
//
//
//                    User user = ds.getValue(User.class);
//
//                    if(currentUserID.equals(user.getId())){
//                        currentUser = user;
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//    }
//
////   /
//
//
////    public void button_AddtoParty(View view) {
////
////        SocialFirebase.getUserParty(currentUser.getId(), new PartyCallback<Party>() {
////            @Override
////            public void callback(Party party) {
////                //do whatever with that party. (either show it on the GUI or nest it with another callback
////                //to do something like the next picture
////            }
////        });
////
////
////    }
//
//}