//package munchhunt.munchhuntproject.Feed;
//
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.design.widget.BottomNavigationView;
//import android.support.v7.app.AppCompatActivity;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ListView;
//import android.widget.Toast;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import munchhunt.munchhuntproject.R;
//
//public class FeedPage extends AppCompatActivity {
//
//    BottomNavigationView navBar;
//    private ListView mFeed;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_feedpage);
//
//        navBar = (BottomNavigationView) findViewById(R.id.navigationBar);
//
//        List<Status> statuses = getListData();
//        final ListView mFeed = (ListView) findViewById(R.id.feedView);
//        mFeed.setAdapter(new FeedAdapter(this, statuses));
//        mFeed.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Object o = mFeed.getItemAtPosition(position);
//                Status status = (Status) o;
//                Toast.makeText(FeedPage.this, "Selected :" + " " + status, Toast.LENGTH_LONG).show();
//            }
//        });
//
////        BottomNavigationViewHelper.disableShiftMode(navBar);
////        android.view.Menu menu = navBar.getMenu();
////        MenuItem menuItem = menu.getItem(2);
////        menuItem.setChecked(true);
////        navBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
////            @Override
////            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
////                switch (item.getItemId())
////                {
////                    case R.id.navigation_map:
////                        startActivity(new Intent(FeedPage.this, MapsActivity.class));
////                        break;
////                    case R.id.navigation_list:
////                        startActivity(new Intent(FeedPage.this, ListSearch.class));
////                        break;
////                    case  R.id.navigation_feed:
////                        startActivity(new Intent(FeedPage.this, FeedPage.class));
////                        break;
////                    case R.id.navigation_profile:
////                        startActivity(new Intent(FeedPage.this, Profile.class));
////                        break;
////
////                }
////                return false;
////            }
////        });
//
//    }
//
//    private List<Status>getListData(){
//        List<Status> list = new ArrayList<>();
//        Status status1 = new Status("1 hour ago", "Kyle uploaded a photo.", "kylepic", "Kyle Gonzalez");
//        Status status2 = new Status("2 hours ago", "Kyle checked in to La Bocca Urban Pizzeria + Wine Bar.", "kylepic", "Kyle Gonzalez");
//        Status status3 = new Status("14 hours ago", "Jason reached level 2!", "jasonbae",  "Jason Kwon");
//        Status statua4 = new Status("14 hours ago", "Kyle and Jason checked in to Spinato's Pizza.", "jasonbae",  "Jason Kwon");
//
//        list.add(status1);
//        list.add(status2);
//        list.add(status3);
//        list.add(statua4);
//
//        return list;
//
//    }
//}
