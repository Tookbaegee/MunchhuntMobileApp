//package munchhunt.munchhuntproject.DeprecatedClasses;
//
//
//import android.app.Dialog;
//import android.os.Bundle;
//import android.os.StrictMode;
//import android.support.design.widget.BottomNavigationView;
//import android.support.v7.app.AppCompatActivity;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageButton;
//import android.widget.ListView;
//import android.widget.Toast;
//
//import com.google.android.gms.common.ConnectionResult;
//import com.google.android.gms.common.GoogleApiAvailability;
//import com.google.android.gms.location.FusedLocationProviderClient;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.yelp.fusion.client.connection.YelpFusionApi;
//import com.yelp.fusion.client.connection.YelpFusionApiFactory;
//import com.yelp.fusion.client.models.Business;
//import com.yelp.fusion.client.models.SearchResponse;
//
//import java.io.IOException;
//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Random;
//
//import munchhunt.munchhuntproject.List.Menu;
//import munchhunt.munchhuntproject.List.MenuListAdapter;
//import munchhunt.munchhuntproject.List.Menus;
//import munchhunt.munchhuntproject.List.MyCallbackRestaurants;
//import munchhunt.munchhuntproject.List.RestaurantListAdapter;
//import munchhunt.munchhuntproject.List.YelpBusinessInfo;
//import munchhunt.munchhuntproject.List.restaurant;
//import munchhunt.munchhuntproject.R;
//import munchhunt.munchhuntproject.Objects.History;
//import retrofit2.Call;
//
//public class ListSearch extends AppCompatActivity implements Serializable {
//    //Variables
//    private ListView mListView, menuListView;
//    private ImageButton mSearchButton;
//    private Button mFriendsButton, mPartyButton;
//    private EditText searchBar;
//    private restaurant r, getRest;
//    private ArrayList<restaurant> list;
//    private ArrayList<Business> businesses;
//    private String input = "";
//    private RestaurantListAdapter restaurantListAdapter;
//    private MenuListAdapter menuList;
//    // private double lat = 33.4641042;
//    // private double long1 = -111.92309190000003;
//    private double lat = 33.4242399;
//    private double long1 = -111.92805269999997;
//
//
//    String yelpId;
//    ArrayList<Menu> m1;
//
//    ArrayList<String> userMatch;
//    List<Menu> menu1;
//    String id;
//
//    BottomNavigationView navBar;
//
//    Bundle b;
//
//    // private ArrayList<Menu> user, menu;
//
//
//    //Variables for yelp
//    private YelpFusionApi yelpFusionApi;
//    private SearchResponse searchResponse;
//    private Call<SearchResponse> call;
//    private Map<String, String> params;
//    private final String api_key = "ktA0B6fgYPsBAWy23zkxFKyiqzt6RZ1N0o2YWZ7EtGk_z4-e0xawEBRItDgKdBkygpi336XPldKVO-8bX5ss0FAcRnR7nO3X06K8tuyl3sgsprUwhLtuuB3v0LgHW3Yx";
//    private static final int ERROR_DIALOG_REQUEST = 9001;
//
//    private FusedLocationProviderClient mFusedLocationClient;
//
//    private DatabaseReference mDatabase;
//    private FirebaseUser mUser;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.maplist_list);
//
//        //intilization of widgets
//        mListView = (ListView) findViewById(R.id.listView1);
//        menuListView = (ListView) findViewById(R.id.menuListView);
//        mSearchButton = (ImageButton) findViewById(R.id.searchButton);
//        searchBar = (EditText) findViewById(R.id.searchBar);
//        mFriendsButton = (Button) findViewById(R.id.addFriendsBtn);
//        mPartyButton = (Button) findViewById(R.id.createPartyBtn);
//
//        //forces the code to be implmented without threading (NEEDS FIX)
//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(policy);
//
//        mDatabase = FirebaseDatabase.getInstance().getReference();
//        mUser = FirebaseAuth.getInstance().getCurrentUser();
//
//        getYelpValues();
//        setRestaurantValues();
//
////        SocialFirebase.callCurrentUser(new CurrentUserCallback() {
////
////            @Override
////            public void callback(final User currentUser) {
////
////                SocialFirebase.checkInParty(currentUser.getId(), new BooleanCallback() {
////                    @Override
////                    public void callback(boolean data) {
////                        if(data){
////                            SocialFirebase.getPartyDietPattern(currentUser, new DietPatternListCallback() {
////                                @Override
////                                public void dietPatternListCallback(List<DietPattern> dietPatterns) {
////                                    final List<DietPattern> user = dietPatterns;
////                                    for (int z = 0 ; z < list.size(); z++) {
////                                        final restaurant rest = list.get(z);
////                                        String restID = rest.getId();
////                                        readRestaurants(new MyCallbackRestaurants() {
////                                            @Override
////                                            public void onCallback(ArrayList<Menu> value) {
////                                                int totalRestMenu = 0;
////                                                int totalMatchingMenu = 0;
////                                                int amountUsers = user.size();
////                                                String munchRating = "match";
////                                                int index = 0;
////
////
////                                                for (int x = 0; x < value.size(); x++) {
////                                                    Menu menu = (Menu)value.get(x);
////                                                    boolean restDairy = menu.getDairy();
////                                                    boolean restGluten = menu.getGluten();
////                                                    boolean restPeanut = menu.getNuts();
////                                                    boolean restRed_Meat = menu.getRed_meat();
////                                                    boolean restWhite_Meat = menu.getWhite_meat();
////                                                    boolean restSeafood = menu.getSeafood();
////                                                    //  boolean restVeg = menu.getVegetables();
////
////
////                                                    for (int y = 0; y < user.size(); y++) {
////
////                                                        boolean userDairy = user.get(y).getDairy();
////                                                        boolean userGluten = user.get(y).getGluten();
////                                                        boolean userPeanut = user.get(y).getNut();
////                                                        boolean userRed_Meat = user.get(y).getRed_meat();
////                                                        boolean userWhite_Meat = user.get(y).getWhite_meat();
////                                                        boolean userSeafood = user.get(y).getSeafood();
////                                                        //boolean userVeg = user.get(y).getVegetables();
////                                                        boolean match = true;
////
////                                                        if (restDairy == true && userDairy == false) {
////                                                            match = false;
////                                                        }
////                                                        else if (restGluten == true && userGluten == false) {
////                                                            match = false;
////                                                        }
////                                                        else if (restPeanut == true && userPeanut == false) {
////                                                            match = false;
////                                                        }
////                                                        else if (restRed_Meat == true && userRed_Meat== false) {
////                                                            match = false;
////                                                        }
////                                                        else if (restWhite_Meat==true && userWhite_Meat==false) {
////                                                            match = false;
////                                                        }
////                                                        else if (restSeafood==true && userSeafood==false) {
////                                                            match = false;
////                                                        }
////                                                        else if (match == true) {
////                                                            totalMatchingMenu++;
////                                                        }
////
////
////                                                    }
////
////                                                }
////                                                totalRestMenu = value.size();
////                                                Log.d("MATCHING", totalMatchingMenu + "");
////                                                Log.d("TOTAL", totalRestMenu + "");
////                                                if (value.size() != 0) {
////                                                    try {
////                                                        double ans = (((double)totalMatchingMenu /(double) (totalRestMenu * user.size() ))* 100);
////                                                        Log.d("ANS", String.valueOf(ans));
////                                                        NumberFormat formatter = new DecimalFormat("#0.00");
////
////                                                        munchRating = String.valueOf(formatter.format(ans)) + "%";
////                                                    } catch (ArithmeticException ae) {
////                                                        Log.d("ERROR", "divide by zero");
////                                                    }
////                                                    rest.setMunchRating(munchRating);
////                                                }
////                                                else
////                                                {
////                                                    rest.setMunchRating("No Menu");
////                                                }
////
////
////
////
////
////                                            }
////                                        }, restID);
////                                    }
////
////                                }
////                            });
////                        }
////                        else{
////                            final List<DietPattern> user = new ArrayList<DietPattern>();
////
////                            user.add(currentUser.getDiet());
////
////                            for (int z = 0 ; z < list.size(); z++) {
////                                final restaurant rest = list.get(z);
////                                String restID = rest.getId();
////                                readRestaurants(new MyCallbackRestaurants() {
////                                    @Override
////                                    public void onCallback(ArrayList<Menu> value) {
////                                        int totalRestMenu = 0;
////                                        int totalMatchingMenu = 0;
////                                        int amountUsers = user.size();
////                                        String munchRating = "match";
////                                        int index = 0;
////
////
////                                        for (int x = 0; x < value.size(); x++) {
////                                            Menu menu = (Menu)value.get(x);
////                                            boolean restDairy = menu.getDairy();
////                                            boolean restGluten = menu.getGluten();
////                                            boolean restPeanut = menu.getNuts();
////                                            boolean restRed_Meat = menu.getRed_meat();
////                                            boolean restWhite_Meat = menu.getWhite_meat();
////                                            boolean restSeafood = menu.getSeafood();
////                                            //  boolean restVeg = menu.getVegetables();
////
////
////                                            for (int y = 0; y < user.size(); y++) {
////
////                                                boolean userDairy = user.get(y).getDairy();
////                                                boolean userGluten = user.get(y).getGluten();
////                                                boolean userPeanut = user.get(y).getNut();
////                                                boolean userRed_Meat = user.get(y).getRed_meat();
////                                                boolean userWhite_Meat = user.get(y).getWhite_meat();
////                                                boolean userSeafood = user.get(y).getSeafood();
////                                                //boolean userVeg = user.get(y).getVegetables();
////                                                boolean match = true;
////
////                                                if (restDairy == true && userDairy == false) {
////                                                    match = false;
////                                                }
////                                                else if (restGluten == true && userGluten == false) {
////                                                    match = false;
////                                                }
////                                                else if (restPeanut == true && userPeanut == false) {
////                                                    match = false;
////                                                }
////                                                else if (restRed_Meat == true && userRed_Meat== false) {
////                                                    match = false;
////                                                }
////                                                else if (restWhite_Meat==true && userWhite_Meat==false) {
////                                                    match = false;
////                                                }
////                                                else if (restSeafood==true && userSeafood==false) {
////                                                    match = false;
////                                                }
////                                                else if (match == true) {
////                                                    totalMatchingMenu++;
////                                                }
////
////
////                                            }
////
////                                        }
////                                        totalRestMenu = value.size();
////                                        Log.d("MATCHING", totalMatchingMenu + "");
////                                        Log.d("TOTAL", totalRestMenu + "");
////                                        if (value.size() != 0) {
////                                            try {
////                                                double ans = (((double)totalMatchingMenu /(double) (totalRestMenu * user.size() ))* 100);
////                                                Log.d("ANS", String.valueOf(ans));
////                                                NumberFormat formatter = new DecimalFormat("#0.00");
////
////                                                munchRating = String.valueOf(formatter.format(ans)) + "%";
////                                            } catch (ArithmeticException ae) {
////                                                Log.d("ERROR", "divide by zero");
////                                            }
////                                            rest.setMunchRating(munchRating);
////                                        }
////                                        else
////                                        {
////                                            rest.setMunchRating("No Menu");
////                                        }
////
////
////
////
////
////                                    }
////                                }, restID);
////                            }
////
////                        }
////                    }
////                });
////
////            }
////        });
////
////
////
////        // r = new restaurant(name, alias, directions, "MATCH", rating, yelpId);
////        // list.add(r);
////
////        //  restaurantListAdapter.notifyDataSetChanged();
////
////        //the code above is the default and what is seen when first entered
////        //the code below is when the search button is pressed
////        //it is the same procedure as the top
////
////        mSearchButton.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                input = searchBar.getText().toString();
////                restaurantListAdapter.clear();//reset list
////                String name;
////                String alias;
////                String directions;
////                String rating;
////
////
////                //params = new HashMap<>();
////                params.put("term", input);
////                params.put("latitude", lat + "");
////                params.put("longitude", long1 + "");
////                params.put("limit", "50");
////                params.put("radius", "10000");
////
////
////                call = yelpFusionApi.getBusinessSearch(params);
////                try {
////                    searchResponse = call.execute().body();
////                } catch (IOException e) {
////                    e.printStackTrace();
////                }
////                businesses = searchResponse.getBusinesses();
////
////
////                for (int i = 0; i < businesses.size(); i++) {
////                    name = businesses.get(i).getName() + "";
////                    alias = businesses.get(i).getCategories().get(0).getTitle() + "";
////                    directions = businesses.get(i).getLocation().getAddress1() + " " + businesses.get(i).getLocation().getZipCode();
////                    rating = businesses.get(i).getRating() + "";
////                    yelpId = businesses.get(i).getId() + "";
////                    String munchRating;
////                    r = new restaurant(name, alias, directions, "N/A", rating, yelpId);
////                    list.add(r);
////
////
////                }
////
////
////                restaurantListAdapter.notifyDataSetChanged();//notifys change in the listview
////                searchBar.setText("");//resets search bar
////            }
////
////        });
////
////
////
////        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
////            @Override
////            public void onItemClick(final AdapterView<?> adapterView, View view, final int i, long l) {
////
////                SocialFirebase.callCurrentUser(new CurrentUserCallback() {
////                    @Override
////                    public void callback(final User currentUser) {
////                        SocialFirebase.checkInParty(currentUser.getId(), new BooleanCallback() {
////                            @Override
////                            public void callback(boolean data) {
////                                if(data) {
////                                    SocialFirebase.getPartyDietPattern(currentUser, new DietPatternListCallback() {
////                                        @Override
////                                        public void dietPatternListCallback(final List<DietPattern> dietPatterns) {
////
////                                            for (int i = 0; i < dietPatterns.size(); i++)
////                                                Log.d("Diet Name", dietPatterns.get(i).getName());
////                                            getRest = (restaurant) adapterView.getAdapter().getItem(i);
////                                            Log.d("TAG2", list.toString());
////
////                                            readRestaurants(new MyCallbackRestaurants() {
////                                                @Override
////                                                public void onCallback(ArrayList<Menu> value) {
////                                                    m1 = new ArrayList<>();
////                                                    b = new Bundle();
////
////
////                                                    for (int x = 0; x < value.size(); x++) {
////                                                        Menu menu = value.get(x);
////                                                        String name = menu.getName();
////                                                        boolean restDairy = menu.getDairy();
////                                                        boolean restGluten = menu.getGluten();
////                                                        boolean restPeanut = menu.getNuts();
////                                                        boolean restRed_Meat = menu.getRed_meat();
////                                                        boolean restWhite_Meat = menu.getWhite_meat();
////                                                        boolean restSeafood = menu.getSeafood();
////                                                        // boolean restVeg = menu.getVegetables();
////                                                        String description = menu.getDescription();
////                                                        String price = menu.getPrice();
////                                                        userMatch = new ArrayList<String>();
////
////                                                        Menu m = new Menu(name, restRed_Meat, restWhite_Meat, restGluten, restPeanut, restDairy, restSeafood, description, price);
////
////
////                                                        for (int y = 0; y < dietPatterns.size(); y++) {
////                                                            String username = dietPatterns.get(y).getName();
////                                                            boolean userDairy = dietPatterns.get(y).getDairy();
////                                                            boolean userGluten = dietPatterns.get(y).getGluten();
////                                                            boolean userPeanut = dietPatterns.get(y).getNut();
////                                                            boolean userRed_Meat = dietPatterns.get(y).getRed_meat();
////                                                            boolean userWhite_Meat = dietPatterns.get(y).getWhite_meat();
////                                                            boolean userSeafood = dietPatterns.get(y).getSeafood();
////                                                            //boolean userVeg = user.get(y).getVegetables();
////                                                            boolean match = true;
////
////                                                            if (restDairy == true && userDairy == false) {
////                                                                match = false;
////                                                            } else if (restGluten == true && userGluten == false) {
////                                                                match = false;
////                                                            } else if (restPeanut == true && userPeanut == false) {
////                                                                match = false;
////                                                            } else if (restRed_Meat == true && userRed_Meat == false) {
////                                                                match = false;
////                                                            } else if (restWhite_Meat == true && userWhite_Meat == false) {
////                                                                match = false;
////                                                            } else if (restSeafood == true && userSeafood == false) {
////                                                                match = false;
////                                                            } else if (match == true) {
////
////                                                                userMatch.add(username);
////
////                                                            }
////                                                        }
////
////                                                        m.setUsers(userMatch);
////                                                        m1.add(m);
////
////
////                                                    }
////
////                                                    Intent intent2 = new Intent(ListSearch.this, MenuView.class);
////                                                    b.putSerializable("obj", m1);
////                                                    b.putString("name", getRest.getName());
////
////
////                                                    //b.putSerializable("name", userMatch);
////                                                    intent2.putExtras(b);
////
////
////                                                    startActivity(intent2);
////
////
////                                                }
////                                            }, getRest.getId());
////                                        }
////                                    });
////                                }
////                                else{
////                                    final List<DietPattern> dietPatterns = new ArrayList<DietPattern>();
////                                    dietPatterns.add(currentUser.getDiet());
////                                    Log.d("By your Self", dietPatterns.get(0).getName());
////                                    for (int i = 0; i < dietPatterns.size(); i++)
////                                        Log.d("Diet Name", dietPatterns.get(i).getName());
////                                    getRest = (restaurant) adapterView.getAdapter().getItem(i);
////                                    Log.d("TAG2", list.toString());
////
////                                    readRestaurants(new MyCallbackRestaurants() {
////                                        @Override
////                                        public void onCallback(ArrayList<Menu> value) {
////                                            m1 = new ArrayList<>();
////                                            b = new Bundle();
////
////
////                                            for (int x = 0; x < value.size(); x++) {
////                                                Menu menu = value.get(x);
////                                                String name = menu.getName();
////                                                boolean restDairy = menu.getDairy();
////                                                boolean restGluten = menu.getGluten();
////                                                boolean restPeanut = menu.getNuts();
////                                                boolean restRed_Meat = menu.getRed_meat();
////                                                boolean restWhite_Meat = menu.getWhite_meat();
////                                                boolean restSeafood = menu.getSeafood();
////                                                // boolean restVeg = menu.getVegetables();
////                                                String description = menu.getDescription();
////                                                String price = menu.getPrice();
////                                                userMatch = new ArrayList<String>();
////
////                                                Menu m = new Menu(name, restRed_Meat, restWhite_Meat, restGluten, restPeanut, restDairy, restSeafood, description, price);
////
////
////                                                for (int y = 0; y < dietPatterns.size(); y++) {
////                                                    String username = dietPatterns.get(y).getName();
////                                                    boolean userDairy = dietPatterns.get(y).getDairy();
////                                                    boolean userGluten = dietPatterns.get(y).getGluten();
////                                                    boolean userPeanut = dietPatterns.get(y).getNut();
////                                                    boolean userRed_Meat = dietPatterns.get(y).getRed_meat();
////                                                    boolean userWhite_Meat = dietPatterns.get(y).getWhite_meat();
////                                                    boolean userSeafood = dietPatterns.get(y).getSeafood();
////                                                    //boolean userVeg = user.get(y).getVegetables();
////                                                    boolean match = true;
////
////                                                    if (restDairy == true && userDairy == false) {
////                                                        match = false;
////                                                    } else if (restGluten == true && userGluten == false) {
////                                                        match = false;
////                                                    } else if (restPeanut == true && userPeanut == false) {
////                                                        match = false;
////                                                    } else if (restRed_Meat == true && userRed_Meat == false) {
////                                                        match = false;
////                                                    } else if (restWhite_Meat == true && userWhite_Meat == false) {
////                                                        match = false;
////                                                    } else if (restSeafood == true && userSeafood == false) {
////                                                        match = false;
////                                                    } else if (match == true) {
////
////                                                        userMatch.add(username);
////
////                                                    }
////                                                }
////
////                                                m.setUsers(userMatch);
////                                                m1.add(m);
////
////
////                                            }
////
////                                            Intent intent2 = new Intent(ListSearch.this, MenuView.class);
////                                            b.putSerializable("obj", m1);
////                                            b.putString("name", getRest.getName());
////
////
////                                            //b.putSerializable("name", userMatch);
////                                            intent2.putExtras(b);
////
////
////                                            startActivity(intent2);
////
////
////                                        }
////                                    }, getRest.getId());
////
////                                }
////                            }
////                        });
////
////                    }
////                });
////
////
////
////            }
////        });
////
////
////        //adapter for the listview
////        restaurantListAdapter = new RestaurantListAdapter(this, R.layout.maplist_adapter_restaurantview, list);
////        mListView.setAdapter(restaurantListAdapter);
////
////
////
////        mFriendsButton.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                Intent intent = new Intent(ListSearch.this, PartyAdder.class);
////                startActivity(intent);
////            }
////        });
////
////        mPartyButton.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                Intent intent = new Intent(ListSearch.this, PartyCreator.class);
////                startActivity(intent);
////            }
////        });
//
////        navBar = (BottomNavigationView) findViewById(R.id.navigationBar);
////        BottomNavigationViewHelper.disableShiftMode(navBar);
////        android.view.Menu menu = navBar.getMenu();
////        MenuItem menuItem = menu.getItem(1);
////        menuItem.setChecked(true);
////        navBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
////            @Override
////            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
////                switch (item.getItemId())
////                {
////                    case R.id.navigation_map:
////                        startActivity(new Intent(ListSearch.this, MapsActivity.class));
////                        item.setIcon(R.drawable.ic_map);
////                        break;
////                    case R.id.navigation_list:
////                        startActivity(new Intent(ListSearch.this, ListSearch.class));
////                        item.setIcon(R.drawable.ic_feed);
////                        break;
////                    case R.id.navigation_feed:
////                        startActivity(new Intent(ListSearch.this, FeedPage.class));
////                        item.setIcon(R.drawable.ic_feed);
////                        break;
////                    case R.id.navigation_profile:
////                        startActivity(new Intent(ListSearch.this, Profile.class));
////                        item.setIcon(R.drawable.ic_profile);
////                        break;
////
////                }
////                return false;
////            }
////        });
//
//
//    }
//
//
//    public void readRestaurants(final MyCallbackRestaurants myCallback, String id)
//    {
//        //   DatabaseReference mDatabase1 = FirebaseDatabase.getInstance().getReference();
//
//
////        mDatabase.child("Restaurant").child(id).child("menus").addListenerForSingleValueEvent(new ValueEventListener() {
////            @Override
////            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
////                ArrayList menus1 = new ArrayList<>();
////                Map<String, Object> td = (HashMap<String, Object>) dataSnapshot.getValue(Menus.class);
////                //YelpBusinessInfo y = (YelpBusinessInfo) dataSnapshot.getValue(YelpBusinessInfo.class);
////                //ArrayList m1= new ArrayList<>(td.values());
////                //  Log.d("DATABSE", td.get("menus").toString());
//////
//////                ArrayList menuSize = (ArrayList) td.get("menus");
//////                Log.d("MARKER", menuSize + "");
////
////                for (int i = 0; i <  menuSize.size() ; i++)
////                {
////                    Map<String, Object> m = (HashMap<String, Object>) dataSnapshot.child("menus").child(i + "").getValue();
////                    String name = m.get("name").toString();
////
////                    String description = m.get("description").toString();
////                    String price = m.get("price").toString();
////
////
////                    // String description = m.getDescription();
////                    //String price = m.getPrice();
////                    final boolean restDairy = Boolean.parseBoolean(m.get("dairy").toString());
////                    final boolean restGluten = Boolean.parseBoolean(m.get("gluten").toString());
////                    final boolean restRed_Meat = Boolean.parseBoolean(m.get("red_meat").toString());
////                    final boolean restWhite_Meat =Boolean.parseBoolean(m.get("white_meat").toString());
////                    final boolean restSeafood = Boolean.parseBoolean(m.get("seafood").toString());
////                    final boolean restNuts = Boolean.parseBoolean(m.get("nuts").toString());
////                    //final boolean restVeg = m.getVegetables();
////
////                    Menu getRestTest = new Menu(name,restRed_Meat, restWhite_Meat, restGluten, restNuts, restDairy, restSeafood, description, price);
////                    Log.d("TEST_NAME", getRestTest.getName());
////                    Log.d("TEST_WHITEMEAT",getRestTest.getRed_meat() + "");
////                    Log.d("TEST_GLUTEN",getRestTest.getGluten() + "");
////                    Log.d("TEST_PEANUT",getRestTest.getNuts() + "");
////                    Log.d("TEST_DAIRY",getRestTest.getDairy() + "");
////                    Log.d("TEST_SEAFOOD",getRestTest.getSeafood() + "");
////                    menus1.add(getRestTest);
////                }
////
////
////
////                myCallback.onCallback(menus1);
////
////            }
////
////            @Override
////            public void onCancelled(@NonNull DatabaseError databaseError) {
////
////            }
////        });
//
//    }
//    public boolean isServicesOK(){
//
//        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(ListSearch.this);
//
//        if(available == ConnectionResult.SUCCESS){
//            //everything is fine and the user can make map requests
//            return true;
//        }
//        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
//            //an error occured but we can resolve it
//            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(ListSearch.this, available, ERROR_DIALOG_REQUEST);
//            dialog.show();
//        }else{
//            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
//        }
//        return false;
//    }
//
//    public void getYelpValues()
//    {
//        //allows the use of Yelp but inserting api key
//        YelpFusionApiFactory apiFactory = new YelpFusionApiFactory();
//        try {
//            yelpFusionApi = apiFactory.createAPI(api_key);
//        } catch (IOException e1) {
//            e1.printStackTrace();
//        }
//        //hash map is a way to communicate with JSON
//        params = new HashMap<>();
//        params.put("term", "");
//        params.put("latitude", lat + "");
//        params.put("longitude", long1 + "");
//        params.put("limit", "20");
//        params.put("radius", "15000");
//        //params.put("categories", "");
//        //  params.put("attributes", "");
//
//
//        //calls yelp database and searchs using params
//        call = yelpFusionApi.getBusinessSearch(params);
//        try {
//
//            searchResponse = call.execute().body();//excutes call
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        //grabs the responses and puts it into an arraylist
//        businesses = searchResponse.getBusinesses();
//
//    }
//
//    public void setRestaurantValues()
//    {
//        list = new ArrayList<restaurant>();
//        ArrayList<History> historyList = new ArrayList<History>();
//
//
//
//
//
//        //we grab values from the business araylist and put it into our own
//        for (int i = 0; i < businesses.size(); i++) {
//
//            final String name = businesses.get(i).getName() + "";
//            final String alias = businesses.get(i).getCategories().get(0).getTitle() + "";
//            final String directions = businesses.get(i).getLocation().getAddress1() + " " + businesses.get(i).getLocation().getZipCode();
//            final String rating = businesses.get(i).getRating() + "";
//            String latCoordinates = businesses.get(i).getCoordinates().getLatitude() + "";
//            String longCoordinates = businesses.get(i).getCoordinates().getLongitude() + "";
//            String price = businesses.get(i).getPrice() + "";
//            yelpId = businesses.get(i).getId() + "";
//
//            Random random = new Random();
//            int i1 = random.nextInt(15 - 1 + 1) + 1;
//
//            if( i1== 5)
//            {
//                History history = new History(name, yelpId, alias);
//                historyList.add(history);
//
//            }
//
//            ArrayList<Menu> menus23 = new ArrayList<Menu>();
///*
//            Menu menuDefault = new Menu("Default","","", true, true, true, true, true, true);
//           //mDatabase.child("Restaurant").child(yelpId).child("menu").child("0").setValue(menuDefault);
//            Menu menuDefault1 = new Menu("Default1", true, true, true, true, true, true);
//          //  mDatabase.child("Restaurant").child(yelpId).child("menu").child("1").setValue(menuDefault1);
//
//            menus23.add(menuDefault);
//            menus23.add(menuDefault1);
//*/
//            Menus menus = new Menus(menus23);
//            YelpBusinessInfo y1 = new YelpBusinessInfo(yelpId, name, alias, directions, latCoordinates, longCoordinates, rating, price, menus );
//            // String key = mDatabase.child("Restaurant").child(yelpId).child("Menu").push().getKey();
//            //    mDatabase.child("Restaurant1").child(yelpId).setValue(y1);
//            //  mDatabase.child("Test2").child(yelpId).setValue(y1);
//
//
///*
//            Menu m = new Menu("default", true, true, true, true, true, true, true, yelpId);
//            mDatabase.child("Restaurant").child(yelpId).child("Menu").child("Default").setValue(m);
//
//            Menu m1 = new Menu("default1", true, true, true, true, true, true, true);
//            mDatabase.child("Restaurant").child(yelpId).child("Menu").child("Default1").setValue(m1);
//
//*/
//
//            r = new restaurant(name, alias, directions, "", rating, yelpId);
//            list.add(r);
//
//
//        }
//    }
//
//}
