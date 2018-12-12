package munchhunt.munchhuntproject.Profile;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import munchhunt.munchhuntproject.BottomNavigationViewHelper;
import munchhunt.munchhuntproject.Callback.CurrentUserCallback;
import munchhunt.munchhuntproject.Map.MapsActivity;
import munchhunt.munchhuntproject.Notifications.NotificationsPage;
import munchhunt.munchhuntproject.Objects.User;
import munchhunt.munchhuntproject.Party.PartyPage;
import munchhunt.munchhuntproject.Party.Social;
import munchhunt.munchhuntproject.Party.SocialFirebase;
import munchhunt.munchhuntproject.R;
import munchhunt.munchhuntproject.RegisterLogin.MainActivity;

public class SettingsPage extends AppCompatActivity {

    BottomNavigationView navBar;
    private Button mSignOutBtn, mSaveChangesBtn;
    FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference mDatabase;
    private de.hdodenhof.circleimageview.CircleImageView mProfilePic;
    private EditText mEditName, mEditEmail, mEditPassword, mVerifyPassword;
    private String id, name, email, password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_settings);


        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mUser = FirebaseAuth.getInstance().getCurrentUser();

        initializeNavigationBar();
        initializeElements();


        mSignOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent(SettingsPage.this, MainActivity.class));
            }
        });

        mSaveChangesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = mUser.getUid();
                name = mEditName.getText().toString();
                email = mEditEmail.getText().toString();
                password = mEditPassword.getText().toString();

                saveChanges();
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
                        startActivity(new Intent(SettingsPage.this, MapsActivity.class));
                        item.setIcon(R.drawable.ic_map);
                        break;
                    case R.id.navigation_social:
                        startActivity(new Intent(SettingsPage.this, PartyPage.class));
                        item.setIcon(R.drawable.ic_party);
                        break;
                    case R.id.navigation_notifications:
                        startActivity(new Intent(SettingsPage.this, NotificationsPage.class));
                    case R.id.navigation_profile:
                        startActivity(new Intent(SettingsPage.this, Profile.class));
                        item.setIcon(R.drawable.ic_profile);
                        break;

                }
                return false;
            }
        });
    }

    private void initializeElements(){
        mEditName = (EditText) findViewById(R.id.editName);
        mEditPassword = (EditText) findViewById(R.id.editPassword1);
        mVerifyPassword = (EditText) findViewById(R.id.editPassword2);

        mSignOutBtn = (Button) findViewById(R.id.signOutBtn);
        mSaveChangesBtn = (Button) findViewById(R.id.saveChangesBtn);

        mProfilePic = (de.hdodenhof.circleimageview.CircleImageView) findViewById(R.id.profilePic);


    }

    private void saveChanges() {
////        if (mEditName.getText().toString().matches("") || mEditEmail.getText().toString().matches("")
////                || mEditPassword.getText().toString().matches("") || mVerifyPassword.getText().toString().matches("")) {
////            Toast.makeText(SettingsPage.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
////            return;
////        }
//        if(mEditPassword.getText().toString().matches("") || mVerifyPassword.getText().toString().matches("")){
//            Toast.makeText(SettingsPage.this, "Please complete the password requirements.", Toast.LENGTH_SHORT).show();
//        }
//        if (checkValidEmail(mEditEmail.getText().toString()) == false) {
//            Toast.makeText(SettingsPage.this, "Please enter a valid email address.", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if (!mEditPassword.getText().toString().equals(mVerifyPassword.getText().toString())) {
//            Toast.makeText(SettingsPage.this, "Passwords don't match.", Toast.LENGTH_SHORT).show();
//            return;
//        } else {
//            SocialFirebase.callCurrentUser(new CurrentUserCallback() {
//                @Override
//                public void callback(User currentUser) {
//                    if()
//                }
//            });
//        }

    }

    public static boolean checkValidEmail(String email){
        String symbols = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(symbols, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


}
