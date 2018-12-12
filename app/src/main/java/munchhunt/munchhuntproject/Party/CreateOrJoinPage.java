package munchhunt.munchhuntproject.Party;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import munchhunt.munchhuntproject.BottomNavigationViewHelper;
import munchhunt.munchhuntproject.Callback.BooleanCallback;
import munchhunt.munchhuntproject.Callback.CurrentUserCallback;
import munchhunt.munchhuntproject.DeprecatedClasses.SocialPage;
import munchhunt.munchhuntproject.Map.MapsActivity;
import munchhunt.munchhuntproject.Notifications.NotificationsPage;
import munchhunt.munchhuntproject.Objects.User;
import munchhunt.munchhuntproject.Profile.Profile;
import munchhunt.munchhuntproject.R;

public class CreateOrJoinPage extends AppCompatActivity {

    BottomNavigationView navBar;
    Button mCreatePartyBtn, mJoinPartyBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.party_createorjoin_page);

        initializeElements();
        initializeNavigationBar();

        mCreatePartyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SocialFirebase.createNewParty();
                startActivity(new Intent(CreateOrJoinPage.this, PartyPage.class));
            }
        });

        mJoinPartyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(CreateOrJoinPage.this);

                integrator.setPrompt("Scan Party QRcode");

                integrator.setOrientationLocked(false);

                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);

                integrator.initiateScan();
                //        Use this for more customization

//        IntentIntegrator integrator = new IntentIntegrator(this);

//        integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);

//        integrator.setPrompt("Scan a barcode");

//        integrator.setCameraId(0);  // Use a specific camera of the device

//        integrator.setBeepEnabled(false);

//        integrator.setBarcodeImageEnabled(true);

//        integrator.initiateScan();

            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (result != null) {

            if (result.getContents() == null) {

                Toast.makeText(this, "Scan Cancelled", Toast.LENGTH_LONG).show();

            } else {
                Log.d("Party ID", result.getContents());
                SocialFirebase.joinPartyByID(result.getContents());
                startActivity(new Intent(CreateOrJoinPage.this, PartyPage.class));

            }

        } else {

            super.onActivityResult(requestCode, resultCode, data);

        }

    }

    private void initializeElements(){
        mCreatePartyBtn = (Button) findViewById(R.id.createBtn);
        mJoinPartyBtn = (Button) findViewById(R.id.joinBtn);


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
                        startActivity(new Intent(CreateOrJoinPage.this, MapsActivity.class));
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
                                            startActivity(new Intent(CreateOrJoinPage.this, PartyPage.class));
                                        }
                                        else{
                                            startActivity(new Intent(CreateOrJoinPage.this, CreateOrJoinPage.class));
                                        }
                                    }
                                });
                            }
                        });
                        item.setIcon(R.drawable.ic_party);
                        break;
                    case R.id.navigation_notifications:
                        startActivity(new Intent(CreateOrJoinPage.this, NotificationsPage.class));
                        item.setIcon(R.drawable.ic_notifications);
                        break;
                    case R.id.navigation_profile:
                        startActivity(new Intent(CreateOrJoinPage.this, Profile.class));
                        item.setIcon(R.drawable.ic_profile);
                        break;

                }
                return false;
            }
        });
    }
}
