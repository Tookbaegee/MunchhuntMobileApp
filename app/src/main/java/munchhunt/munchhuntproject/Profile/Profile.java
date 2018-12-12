package munchhunt.munchhuntproject.Profile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

import munchhunt.munchhuntproject.BottomNavigationViewHelper;
import munchhunt.munchhuntproject.Callback.BooleanCallback;
import munchhunt.munchhuntproject.Callback.CurrentUserCallback;
import munchhunt.munchhuntproject.Map.MapsActivity;
import munchhunt.munchhuntproject.Notifications.NotificationsPage;
import munchhunt.munchhuntproject.Party.CreateOrJoinPage;
import munchhunt.munchhuntproject.Party.PartyPage;
import munchhunt.munchhuntproject.Party.SocialFirebase;
import munchhunt.munchhuntproject.R;
import munchhunt.munchhuntproject.Objects.User;

public class Profile extends AppCompatActivity {
    private FirebaseDatabase mFirebaseDatabase;
    FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser mUser;

    private DatabaseReference myRef;
    private String userID;

    private TextView mFullName, mNumFriends;
    private ImageView ivPork, ivBeef, ivFish, ivCrustacean, ivShellfish, ivWhiteMeat, ivNuts, ivGluten, ivDairy, ivEggs;
    private View mPorkBar, mBeefBar, mFishBar, mCrustaceanBar, mShellfishBar, mWhiteMeatBar, mNutsBar, mGlutenBar, mDairyBar, mEggsBar;
    private ImageView mSettingsButton;
    int color;
    private RelativeLayout mFriendsLayout;
    private de.hdodenhof.circleimageview.CircleImageView mProfilePic;
    private ImageView mAddFriendsBtn;

    BottomNavigationView navBar;

    private static final String TAG = "Profile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_profilepage);
        initializeNavigationBar();
        initializeElements();

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null)
                {
                    Log.d(TAG, "onAuthStateChanged:signed_in" + user.getUid());
                    Toast.makeText(Profile.this, "Successfully signed in with: " + user.getEmail(), Toast.LENGTH_SHORT);
                } else
                {
                    Log.d(TAG, "onAuthStateCHanged:signed_out");
                    Toast.makeText(Profile.this, "Successfully signed out", Toast.LENGTH_SHORT);
                }
            }
        };




//        SocialFirebase.callCurrentUser(new CurrentUserCallback() {
//            @Override
//            public void callback(User currentUser) {
//                if(!currentUser.getId().equals("1XewZvz0XEcTQ1A4iTinSGycVQj1"))
//                {
//                    mProfilePic.setImageResource(android.R.color.transparent);
//                }
//                else
//                {
//                    mProfilePic.setImageResource(R.drawable.adrianehot);
//                }
//            }
//       });

        SocialFirebase.callCurrentUser(new CurrentUserCallback() {
            @Override
            public void callback(User currentUser) {
                int numFriends = 0;
                for(int i = 0; i < currentUser.getFriendsList().size(); i++){
                    numFriends++;
                }
                mFullName.setText(currentUser.getName());
                mNumFriends.setText(--numFriends +"");
                //mNumFriends.setText(u.getFriendsList().size());

                if(currentUser.getDiet().getPork() == true){
                    ivPork.setImageResource(R.drawable.ic_pork_col);
                    mPorkBar.setBackgroundColor(getResources().getColor(R.color.hot_pink));
                }
                if(currentUser.getDiet().getBeef() == true){
                    ivBeef.setImageResource(R.drawable.ic_beef_col);
                    mBeefBar.setBackgroundColor(getResources().getColor(R.color.hot_pink));
                }
                if(currentUser.getDiet().getFish() == true){
                    ivFish.setImageResource(R.drawable.ic_seafood_col);
                    mFishBar.setBackgroundColor(getResources().getColor(R.color.hot_pink));
                }
                if(currentUser.getDiet().getCrustacean() == true){
                    ivCrustacean.setImageResource(R.drawable.ic_crustacean_col);
                    mCrustaceanBar.setBackgroundColor(getResources().getColor(R.color.hot_pink));
                }
                if(currentUser.getDiet().getShellfish() == true){
                    ivShellfish.setImageResource(R.drawable.ic_shellfish_col);
                    mShellfishBar.setBackgroundColor(getResources().getColor(R.color.hot_pink));
                }
                if(currentUser.getDiet().getWhite_meat() == true){
                    ivWhiteMeat.setImageResource(R.drawable.ic_whitemeat_col);
                    mWhiteMeatBar.setBackgroundColor(getResources().getColor(R.color.hot_pink));
                }
                if(currentUser.getDiet().getGluten() == true){
                    ivGluten.setImageResource(R.drawable.ic_gluten_col);
                    mGlutenBar.setBackgroundColor(getResources().getColor(R.color.hot_pink));
                }
                if(currentUser.getDiet().getNut() == true){
                    ivNuts.setImageResource(R.drawable.ic_peanut_col);
                    mNutsBar.setBackgroundColor(getResources().getColor(R.color.hot_pink));
                }
                if(currentUser.getDiet().getDairy() == true){
                    ivDairy.setImageResource(R.drawable.ic_dairy_col);
                    mDairyBar.setBackgroundColor(getResources().getColor(R.color.hot_pink));
                }
                if(currentUser.getDiet().getEggs() == true){
                    ivEggs.setImageResource(R.drawable.ic_egg_col);
                    mEggsBar.setBackgroundColor(getResources().getColor(R.color.hot_pink));

                }

                StorageReference userRef = FirebaseStorage.getInstance().getReference().child("User");
                StorageReference imageRef = userRef.child(currentUser.getId() + ".jpg");
                try {
                    final File localFile = File.createTempFile("images", "jpg");
                    imageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                            mProfilePic.setImageBitmap(bitmap);

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                        }
                    });
                } catch (IOException e ) {}
            }
        });


        mSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this, SettingsPage.class));
            }
        });
    }



    private void initializeElements(){
        mAddFriendsBtn = findViewById(R.id.btnAddFriend);
        mAddFriendsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this, SearchFriendsPage.class));
            }
        });

        mUser = FirebaseAuth.getInstance().getCurrentUser();


        mFullName = (TextView) findViewById(R.id.fullName);
        mNumFriends = (TextView) findViewById(R.id.numFriends);
        mFriendsLayout = (RelativeLayout) findViewById(R.id.relLayoutNumFriends);

        navBar = (BottomNavigationView) findViewById(R.id.navigationBar);
        mSettingsButton = (ImageView) findViewById(R.id.settingsBtn);
        mProfilePic = (de.hdodenhof.circleimageview.CircleImageView) findViewById(R.id.profilePic);

        // User diet statistics
        ivPork = (ImageView) findViewById(R.id.porkCheck);
        ivBeef = (ImageView) findViewById(R.id.beefCheck);
        ivFish = (ImageView) findViewById(R.id.fishCheck);
        ivCrustacean = (ImageView) findViewById(R.id.crustaceanCheck);
        ivShellfish = (ImageView) findViewById(R.id.shellfishCheck);
        ivWhiteMeat = (ImageView) findViewById(R.id.whiteMeatcheck);
        ivNuts = (ImageView) findViewById(R.id.nutsCheck);
        ivGluten = (ImageView) findViewById(R.id.glutenCheck);
        ivDairy = (ImageView) findViewById(R.id.dairyCheck);
        ivEggs = (ImageView) findViewById(R.id.eggsCheck);

        // Color bars
        mPorkBar = (View) findViewById(R.id.porkBar);
        mBeefBar = (View) findViewById(R.id.beefBar);
        mFishBar = (View) findViewById(R.id.fishBar);
        mCrustaceanBar = (View) findViewById(R.id.crustaceanBar);
        mShellfishBar = (View) findViewById(R.id.shellfishBar);
        mWhiteMeatBar = (View) findViewById(R.id.whiteMeatBar);
        mGlutenBar = (View) findViewById(R.id.glutenBar);
        mNutsBar = (View) findViewById(R.id.nutsBar);
        mDairyBar = (View) findViewById(R.id.dairyBar);
        mEggsBar = (View) findViewById(R.id.eggsBar);

        mFriendsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this, ViewFriendsListPage.class));
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
                        startActivity(new Intent(Profile.this, MapsActivity.class));
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
                                            startActivity(new Intent(Profile.this, PartyPage.class));
                                        }
                                        else{
                                            startActivity(new Intent(Profile.this, CreateOrJoinPage.class));
                                        }
                                    }
                                });
                            }
                        });
                        item.setIcon(R.drawable.ic_party);
                        break;
                    case R.id.navigation_notifications:
                        startActivity(new Intent(Profile.this, NotificationsPage.class));
                        item.setIcon(R.drawable.ic_notifications);
                        break;
                    case R.id.navigation_profile:
                        startActivity(new Intent(Profile.this, Profile.class));
                        item.setIcon(R.drawable.ic_profile);
                        break;

                }
                return false;
            }
        });
    }


}
