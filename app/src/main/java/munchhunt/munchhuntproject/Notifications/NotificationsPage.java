package munchhunt.munchhuntproject.Notifications;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import munchhunt.munchhuntproject.BottomNavigationViewHelper;
import munchhunt.munchhuntproject.Callback.BooleanCallback;
import munchhunt.munchhuntproject.Callback.CurrentUserCallback;
import munchhunt.munchhuntproject.Map.MapsActivity;
import munchhunt.munchhuntproject.Objects.Request;
import munchhunt.munchhuntproject.Party.CreateOrJoinPage;
import munchhunt.munchhuntproject.Party.SocialFirebase;
import munchhunt.munchhuntproject.Party.PartyPage;
import munchhunt.munchhuntproject.Profile.Profile;
import munchhunt.munchhuntproject.R;
import munchhunt.munchhuntproject.Objects.User;

/**
 * Created by kyl3g on 10/7/2018.
 */

public class NotificationsPage extends AppCompatActivity {

    private ListView mNotificationsList;
    private RequestAdapter mRequestAdapter;
    private BottomNavigationView navBar;

    private TextView mNotificationsText;



    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notifications_page);
        initializeNavigationBar();
        mNotificationsText = (TextView) findViewById(R.id.notificationsText);
        final List<Request> list = new ArrayList<Request>();
        mNotificationsList = findViewById(R.id.lvNotifications);
        mRequestAdapter = new RequestAdapter(this, R.layout.notifications_requestadapter, list);
        mNotificationsList.setAdapter(mRequestAdapter);
        SocialFirebase.autoUpdateCurrentUser(new CurrentUserCallback() {
            @Override
            public void callback(User currentUser) {
                Log.d("requests", currentUser.getRequests().toString());
                List<Request> requests = currentUser.getRequests();
                mRequestAdapter.clear();
                list.clear();
                for(int i = 1; i < requests.size(); i++){
                    list.add(requests.get(i));
                }
                if(list.size() == 0){
                    mNotificationsText.setText("No current notifications!");
                }



            }
        });

    }

    private void initializeNavigationBar(){
        navBar = (BottomNavigationView) findViewById(R.id.navigationBar);
        BottomNavigationViewHelper.disableShiftMode(navBar);
        android.view.Menu menu = navBar.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);
        navBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.navigation_map:
                        startActivity(new Intent(NotificationsPage.this, MapsActivity.class));
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
                                            startActivity(new Intent(NotificationsPage.this, PartyPage.class));
                                        }
                                        else{
                                            startActivity(new Intent(NotificationsPage.this, CreateOrJoinPage.class));
                                        }
                                    }
                                });
                            }
                        });
                        item.setIcon(R.drawable.ic_party);
                        break;
                    case R.id.navigation_notifications:
                        startActivity(new Intent(NotificationsPage.this, NotificationsPage.class));
                        item.setIcon(R.drawable.ic_notifications);
                        break;
                    case R.id.navigation_profile:
                        startActivity(new Intent(NotificationsPage.this, Profile.class));
                        item.setIcon(R.drawable.ic_profile);
                        break;

                }
                return false;
            }
        });
    }
}
