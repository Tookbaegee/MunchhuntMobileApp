package munchhunt.munchhuntproject.Party;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.ArrayList;
import java.util.List;


import munchhunt.munchhuntproject.BottomNavigationViewHelper;
import munchhunt.munchhuntproject.Callback.BooleanCallback;
import munchhunt.munchhuntproject.Callback.CurrentUserCallback;
import munchhunt.munchhuntproject.Callback.FriendListCallback;
import munchhunt.munchhuntproject.Callback.PartyCallback;
import munchhunt.munchhuntproject.Callback.UserCallback;
import munchhunt.munchhuntproject.DeprecatedClasses.SocialPage;
import munchhunt.munchhuntproject.Map.MapsActivity;
import munchhunt.munchhuntproject.Notifications.NotificationsPage;
import munchhunt.munchhuntproject.Objects.Party;
import munchhunt.munchhuntproject.Profile.Profile;
import munchhunt.munchhuntproject.R;
import munchhunt.munchhuntproject.Objects.History;
import munchhunt.munchhuntproject.Objects.DietPattern;
import munchhunt.munchhuntproject.Objects.User;

public class  PartyPage extends AppCompatActivity implements munchhunt.munchhuntproject.CursorWheelLayout.OnMenuItemClickListener{

    munchhunt.munchhuntproject.CursorWheelLayout userWheel;
    List<User> currentParty;
    BottomNavigationView navBar;

    private ImageView mAddByHandleBtn;
    private Button mLeavePartyButton;

    private PartyWheelAdapter twa;

    private DatabaseReference mDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.party_partypage);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        initializeElements();
        getParty();


    }

    private void initializeElements(){
        userWheel = findViewById(R.id.userWheel);
        mAddByHandleBtn = (ImageView) findViewById(R.id.cameraBtn);
        mLeavePartyButton = (Button) findViewById(R.id.leavePartyBtn);

        SocialFirebase.callCurrentUser(new CurrentUserCallback() {
            @Override
            public void callback(User currentUser) {
                SocialFirebase.autoUpdateUserParty(currentUser, new PartyCallback<Party>() {
                    @Override
                    public void callback(Party party) {
                        try {

                            ImageView iv_PartyQRCode = (ImageView) findViewById(R.id.partyQRCode);
//                                Resources r = getResources();
//                                float dpHeight = iv_PartyQRCode.getHeight();
//                                float dpWidth = iv_PartyQRCode.getWidth();
//                                float pxHeight = TypedValue.applyDimension(
//                                        TypedValue.COMPLEX_UNIT_DIP,
//                                        dpHeight,
//                                        r.getDisplayMetrics()
//                                );
//                                float pxWidth = TypedValue.applyDimension(
//                                        TypedValue.COMPLEX_UNIT_DIP,
//                                        dpWidth,
//                                        r.getDisplayMetrics()
//                                );

                            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                            Bitmap bitmap = barcodeEncoder.encodeBitmap(party.getId(), BarcodeFormat.QR_CODE, 370, 370);
                            iv_PartyQRCode.setImageBitmap(bitmap);

                        } catch(Exception e) {
                        }
                    }
                });
            }
        });

        initializeNavigationBar();

        userWheel.setOnMenuItemClickListener(new munchhunt.munchhuntproject.CursorWheelLayout.OnMenuItemClickListener() {
            @Override
            public void onItemClick(View view, int pos) {
                Toast.makeText(PartyPage.this, currentParty.get(pos).getName(), Toast.LENGTH_SHORT).show();
            }
        });

        mAddByHandleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(PartyPage.this);
                dialog.setContentView(R.layout.party_searchbyhandle_popup);
                dialog.setTitle("Title...");
                final ArrayList<String> list = new ArrayList<String>();
                ListView lvFriendSearch= (ListView) dialog.findViewById(R.id.lv_FriendSearchList);
                final PartyAdderAdapter paa = new PartyAdderAdapter(PartyPage.this, R.layout.social_partyadderview, list);
                lvFriendSearch.setAdapter(paa);
                SocialFirebase.callCurrentUser(new CurrentUserCallback() {
                    @Override
                    public void callback(User currentUser) {
                        SocialFirebase.autoUpdateFriend(currentUser.getId(), new FriendListCallback() {
                            @Override
                            public void callback(final List<String> friendsList) {
                                paa.clear();
                                list.clear();
                                for(int i = 1; i < friendsList.size(); i++){
                                    final int finalI = i;
                                    SocialFirebase.checkInParty(friendsList.get(i), new BooleanCallback() {
                                        @Override
                                        public void callback(boolean data) {
                                            if(!data){
                                                list.add(friendsList.get(finalI));
                                                paa.notifyDataSetChanged();
                                            }
                                        }
                                    });

                                }
                            }
                        });
                    }
                });
                dialog.show();
            }
        });

        mLeavePartyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SocialFirebase.leaveFromParty();
                startActivity(new Intent(PartyPage.this, CreateOrJoinPage.class));
            }
        });

    }

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
                        startActivity(new Intent(PartyPage.this, MapsActivity.class));
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
                                            startActivity(new Intent(PartyPage.this, PartyPage.class));
                                        }
                                        else{
                                            startActivity(new Intent(PartyPage.this, CreateOrJoinPage.class));
                                        }
                                    }
                                });
                            }
                        });
                        item.setIcon(R.drawable.ic_party);
                        break;
                    case R.id.navigation_notifications:
                        startActivity(new Intent(PartyPage.this, NotificationsPage.class));
                        item.setIcon(R.drawable.ic_notifications);
                        break;
                    case R.id.navigation_profile:
                        startActivity(new Intent(PartyPage.this, Profile.class));
                        item.setIcon(R.drawable.ic_profile);
                        break;

                }
                return false;
            }
        });
    }

    private void getParty(){
        currentParty = new ArrayList<User>();
        List<String> fillStrings = new ArrayList<>();
        DietPattern diet = new DietPattern();
        List<History> history = new ArrayList<>();
        currentParty.add(new User("id", "", "", "password", "email",fillStrings,diet,history));
//        currentParty.add(new User("id", "jason", "password", "email",fillStrings,diet,history));
//        currentParty.add(new User("id", "kyle", "password", "email",fillStrings,diet,history));
        twa = new PartyWheelAdapter(PartyPage.this, currentParty);
        userWheel.setAdapter(twa);
        Log.d("method accessed", "get party");
        SocialFirebase.callCurrentUser(new CurrentUserCallback() {
            @Override
            public void callback(final User currentUser) {
                Log.d("user accessed", "get password");
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
                                    currentParty.add(user);
                                    twa = new PartyWheelAdapter(PartyPage.this, currentParty);
                                    userWheel.setAdapter(twa);
                                }
                            });

                        }

                    }
                });

            }
        });
    }

    @Override
    public void onItemClick(View view, int pos) {



    }


}
