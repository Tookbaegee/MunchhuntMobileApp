package munchhunt.munchhuntproject.Map;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.location.Location;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import munchhunt.munchhuntproject.BottomNavigationViewHelper;
import munchhunt.munchhuntproject.Callback.BooleanCallback;
import munchhunt.munchhuntproject.Callback.CurrentUserCallback;
import munchhunt.munchhuntproject.Callback.DietPatternCallback;
import munchhunt.munchhuntproject.Callback.PartyCallback;
import munchhunt.munchhuntproject.Notifications.NotificationsPage;
import munchhunt.munchhuntproject.Objects.Party;
import munchhunt.munchhuntproject.Objects.PlaceInfo;
import munchhunt.munchhuntproject.Objects.Restaurant;
import munchhunt.munchhuntproject.Objects.RestaurantItems;
import munchhunt.munchhuntproject.Party.CreateOrJoinPage;
import munchhunt.munchhuntproject.Party.SocialFirebase;
import munchhunt.munchhuntproject.Party.PartyPage;
import munchhunt.munchhuntproject.Profile.Profile;
import munchhunt.munchhuntproject.R;
import munchhunt.munchhuntproject.Objects.DietPattern;
import munchhunt.munchhuntproject.Objects.User;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener, GoogleMap.OnInfoWindowClickListener {

    private static final String TAG = "MapActivity";

    private PlaceAutocompleteAdapter mPlaceAutocompleteAdapter;
    private static final float DEFAULT_ZOOM = 13f;
    private static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(new LatLng(-40, -168), new LatLng(71, 136)); // Currently at ASU coordinates
    private static final LatLng ASUcoordinates = new LatLng(33.4628, -111.9229);

    // Maps implementation and permissions
    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Boolean mLocationPermissionsGranted = false;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;

    // Markers and GooglePlaces information object
    private PlaceInfo mPlace;
    private Marker mMarker;
    private Marker marker;

    // GUI elements
    private PlaceAutocompleteFragment placeAutocompleteFragment;
    private ImageView mHeart;
    private ListView mRestaurantList;
    BottomNavigationView navBar;
    private ArrayList<Restaurant> list;
    private ImageView mDragger;
    private ImageView mFilterButton;
    private Button mQuickEatsBtn, mSitDownBtn;

    private RelativeLayout mRelativeLayout;
    BottomSheetBehavior bottomSheetBehavior;

    // Database elements for recommended restaurants
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();

    private DatabaseReference mRef;
    private static DatabaseReference mRef1;

    private List<DietPattern> partyMemberDPs;
    private List<Marker> markerList;

    // Filters
    private Button mDistanceFilter, mTimeFilter, mPriceFilter;
    private boolean distance = false, time = false, price = false;
    private boolean quickeats = false, sitdown = false;


    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maplist_map);
        mRef = FirebaseDatabase.getInstance().getReference();
        mRef1 = FirebaseDatabase.getInstance().getReference();


        initializeElements();
        if (savedInstanceState == null) {
            Toast.makeText(MapsActivity.this, "Map is ready!", Toast.LENGTH_SHORT);
        } else {
            Toast.makeText(MapsActivity.this, "Welcome back!", Toast.LENGTH_SHORT);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initializeElements() {
//        // Autocomplete fragment, currently connected to Google Places
//        placeAutocompleteFragment = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
//        //  mHeart = (ImageView) findViewById(R.id.reccomend_button);
//        EditText fragmentText = (EditText) placeAutocompleteFragment.getView().findViewById(R.id.place_autocomplete_search_input);
//        fragmentText.setHint("Search for a restaurant");
//        fragmentText.setHintTextColor(getResources().getColor(R.color.white));
//        fragmentText.setTextColor(Color.WHITE);

        list = new ArrayList<Restaurant>();

        mRelativeLayout = (RelativeLayout) findViewById(R.id.slidingPanel);
        bottomSheetBehavior = BottomSheetBehavior.from(mRelativeLayout);

        mDragger = (ImageView) findViewById(R.id.dragger);

        // Checks if bottom sheet is expanded or closed. changes image based on it
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    mDragger.setImageResource(R.drawable.ic_drag_down);
                } else if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                    mDragger.setImageResource(R.drawable.ic_drag_up);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                mDragger.setImageResource(R.drawable.ic_loading);
            }
        });
        getLocationPermission();
        autoComplete();
        markRestaurants();

        mRestaurantList = (ListView) findViewById(R.id.lvRestaurants);
        final List<Restaurant> restaurants = new ArrayList<Restaurant>();
        final RestaurantListAdapter rla = new RestaurantListAdapter(MapsActivity.this, R.layout.maplist_adapter_restaurantview, restaurants);
        mRestaurantList.setAdapter(rla);
        readRestaurantData(new RestaurantDataCallback() {
            @Override
            public void onCallback(final ArrayList<Restaurant> restaurantList) {
                rla.clear();
                for (int i = 0; i < restaurantList.size(); i++) {
                    Restaurant temp = restaurantList.get(i);
                    boolean p = true;
                    boolean dist = true;
                    boolean t = true;
                    if (price) {
                        if (!(temp.getPrice().equals("$") || temp.getPrice().equals("$$"))) {
                            p = false;
                        }
                    }
                    if (distance) {
                        double lon1 = -111.9229;
                        double lon2 = Double.parseDouble(temp.getLongCoordinates());
                        double lat1 = 33.4628;
                        double lat2 = Double.parseDouble(temp.getLatCoordinates());
                        double dlon = Math.abs(lon2 - lon1);
                        double dlat = Math.abs(lat2 - lat1);
                        double a = 12430 * dlat / 180;

                        double b = 24901 * dlon / 360 * Math.cos((lat1 + lat2) / 2);

                        double d = Math.pow((a * a + b * b), .5);
                        if (!(d < 1.5)) {
                            dist = false;
                        }
                    }
                    if (time) {
                        if (!(temp.getTime() < 4)) {
                            t = false;
                        }
                    }
                    if (p && dist && t) {
                        restaurants.add(restaurantList.get(i));
                        rla.notifyDataSetChanged();
                    }
                }
            }
        });
        SocialFirebase.callCurrentUser(new CurrentUserCallback() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void callback(final User currentUser) {
                SocialFirebase.checkInParty(currentUser.getId(), new BooleanCallback() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void callback(boolean data) {

                        if (data) {
                            rla.clearPartyMemberDPs();
                            SocialFirebase.autoUpdateUserParty(currentUser, new PartyCallback<Party>() {
                                @Override
                                public void callback(Party party) {

                                    final List<String> partyMembers = party.getparty();
                                    for (int i = 0; i < partyMembers.size(); i++) {
                                        SocialFirebase.readUserDietbyID(partyMembers.get(i), new DietPatternCallback() {
                                            @Override

                                            public void dietPatternCallback(DietPattern dietPattern) {
                                                Log.d("Party Member Diet", dietPattern.toString());
                                                rla.addPartyMemberDPs(dietPattern);
                                                rla.assignMunchScore();
                                            }
                                        });
                                    }
                                }
                            });
                        } else {
                            rla.clearPartyMemberDPs();
                            rla.addPartyMemberDPs(currentUser.getDiet());
                            rla.assignMunchScore();

                        }
                    }
                });


            }
        });

        mRestaurantList.setOnTouchListener(new ListView.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow NestedScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;

                    case MotionEvent.ACTION_UP:
                        // Allow NestedScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }

                // Handle ListView touch events.
                v.onTouchEvent(event);
                return true;
            }
        });

        mRestaurantList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Restaurant r = (Restaurant) parent.getAdapter().getItem(position);
                Bundle b = new Bundle();
                Intent intent = new Intent(MapsActivity.this, MenuView.class);
                b.putSerializable("obj", r);
                intent.putExtras(b);
                Log.d("BEFORE", r.toString());
                startActivity(intent);
            }
        });

        // initial filter boolean values

        mDistanceFilter = (Button) findViewById(R.id.distanceFilter);
        mTimeFilter = (Button) findViewById(R.id.timeFilter);
        mPriceFilter = (Button) findViewById(R.id.priceFilter);

        // Distance Filter
        mDistanceFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (distance == false) { // If distance filter is currently not being applied and we want to turn it on...
                    distance = true;
                    mDistanceFilter.setBackgroundColor(getResources().getColor(R.color.holo_green_light));
                    Log.d("color", "now red");


                    // Should update list
                } else if (distance == true) {// If distance filter is currently being applied and we want to turn it off...
                    distance = false;
                    mDistanceFilter.setBackgroundColor(getResources().getColor(R.color.filter_grey));
                    Log.d("color", "now green");

                    // Should update list
                }
                readRestaurantData(new RestaurantDataCallback() {
                    @Override
                    public void onCallback(final ArrayList<Restaurant> restaurantList) {
                        rla.clear();
                        for (int i = 0; i < restaurantList.size(); i++) {
                            Restaurant temp = restaurantList.get(i);
                            boolean p = true;
                            boolean dist = true;
                            boolean t = true;
                            if (price) {
                                if (!(temp.getPrice().equals("$") || temp.getPrice().equals("$$"))) {
                                    p = false;
                                }
                            }
                            if (distance) {
                                double lon1 = -111.9229;
                                double lon2 = Double.parseDouble(temp.getLongCoordinates());
                                double lat1 = 33.4628;
                                double lat2 = Double.parseDouble(temp.getLatCoordinates());
                                double dlon = Math.abs(lon2 - lon1);
                                double dlat = Math.abs(lat2 - lat1);
                                double a = 12430 * dlat / 180;

                                double b = 24901 * dlon / 360 * Math.cos((lat1 + lat2) / 2);

                                double d = Math.pow((a * a + b * b), .5);
                                if (!(d < 1.5)) {
                                    dist = false;
                                }
                            }
                            if (time) {
                                if (!(temp.getTime() < 4)) {
                                    t = false;
                                }
                            }
                            if (p && dist && t) {
                                restaurants.add(restaurantList.get(i));
                                rla.notifyDataSetChanged();
                            }
                        }
                    }
                });
                SocialFirebase.callCurrentUser(new CurrentUserCallback() {
                    @Override
                    public void callback(final User currentUser) {
                        SocialFirebase.checkInParty(currentUser.getId(), new BooleanCallback() {
                            @RequiresApi(api = Build.VERSION_CODES.N)
                            @Override
                            public void callback(boolean data) {
                                if (data) {
                                    SocialFirebase.autoUpdateUserParty(currentUser, new PartyCallback<Party>() {
                                        @Override
                                        public void callback(Party party) {
                                            rla.clearPartyMemberDPs();
                                            final List<String> partyMembers = party.getparty();
                                            for (int i = 0; i < partyMembers.size(); i++) {
                                                SocialFirebase.readUserDietbyID(partyMembers.get(i), new DietPatternCallback() {
                                                    @Override

                                                    public void dietPatternCallback(DietPattern dietPattern) {
                                                        rla.addPartyMemberDPs(dietPattern);
                                                        rla.notifyDataSetChanged();
                                                    }
                                                });
                                            }
                                        }
                                    });
                                    rla.assignMunchScore();
                                    rla.sort(new RestaurantComparator());

                                } else {
                                    rla.clearPartyMemberDPs();
                                    rla.addPartyMemberDPs(currentUser.getDiet());
                                    rla.notifyDataSetChanged();
                                    rla.assignMunchScore();
                                    rla.sort(new RestaurantComparator());
                                }
                            }
                        });


                    }
                });
            }
        });

        // Time Filter
        mTimeFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (time == false) { // If distance filter is currently not being applied and we want to turn it on...
                    time = true;
                    mTimeFilter.setBackgroundColor(getResources().getColor(R.color.holo_green_light));
                    Log.d("color", "now red");

                    // Should update list
                } else if (time == true) {// If distance filter is currently being applied and we want to turn it off...
                    time = false;
                    mTimeFilter.setBackgroundColor(getResources().getColor(R.color.filter_grey));
                    Log.d("color", "now green");

                    // Should update list
                }
                readRestaurantData(new RestaurantDataCallback() {
                    @Override
                    public void onCallback(final ArrayList<Restaurant> restaurantList) {
                        rla.clear();
                        for (int i = 0; i < restaurantList.size(); i++) {
                            Restaurant temp = restaurantList.get(i);
                            boolean p = true;
                            boolean dist = true;
                            boolean t = true;
                            if (price) {
                                if (!(temp.getPrice().equals("$") || temp.getPrice().equals("$$"))) {
                                    p = false;
                                }
                            }
                            if (distance) {
                                double lon1 = -111.9229;
                                double lon2 = Double.parseDouble(temp.getLongCoordinates());
                                double lat1 = 33.4628;
                                double lat2 = Double.parseDouble(temp.getLatCoordinates());
                                double dlon = Math.abs(lon2 - lon1);
                                double dlat = Math.abs(lat2 - lat1);
                                double a = 12430 * dlat / 180;

                                double b = 24901 * dlon / 360 * Math.cos((lat1 + lat2) / 2);

                                double d = Math.pow((a * a + b * b), .5);
                                if (!(d < 1.5)) {
                                    dist = false;
                                }
                            }
                            if (time) {
                                if (!(temp.getTime() < 4)) {
                                    t = false;
                                }
                            }
                            if (p && dist && t) {
                                restaurants.add(restaurantList.get(i));
                                rla.notifyDataSetChanged();
                            }
                        }
                    }
                });
                SocialFirebase.callCurrentUser(new CurrentUserCallback() {
                    @Override
                    public void callback(final User currentUser) {
                        SocialFirebase.checkInParty(currentUser.getId(), new BooleanCallback() {
                            @RequiresApi(api = Build.VERSION_CODES.N)
                            @Override
                            public void callback(boolean data) {
                                if (data) {
                                    SocialFirebase.autoUpdateUserParty(currentUser, new PartyCallback<Party>() {
                                        @Override
                                        public void callback(Party party) {
                                            rla.clearPartyMemberDPs();
                                            final List<String> partyMembers = party.getparty();
                                            for (int i = 0; i < partyMembers.size(); i++) {
                                                SocialFirebase.readUserDietbyID(partyMembers.get(i), new DietPatternCallback() {
                                                    @Override

                                                    public void dietPatternCallback(DietPattern dietPattern) {
                                                        rla.addPartyMemberDPs(dietPattern);
                                                        rla.notifyDataSetChanged();
                                                    }
                                                });
                                            }
                                        }
                                    });
                                    rla.assignMunchScore();
                                    rla.sort(new RestaurantComparator());

                                } else {
                                    rla.clearPartyMemberDPs();
                                    rla.addPartyMemberDPs(currentUser.getDiet());
                                    rla.notifyDataSetChanged();
                                    rla.assignMunchScore();
                                    rla.sort(new RestaurantComparator());
                                }
                            }
                        });


                    }
                });
            }
        });

        // Price Filter
        mPriceFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (price == false) { // If distance filter is currently not being applied and we want to turn it on...
                    price = true;
                    mPriceFilter.setBackgroundColor(getResources().getColor(R.color.holo_green_light));
                    Log.d("color", "now red");

                    // Should update list
                } else if (price == true) {// If distance filter is currently being applied and we want to turn it off...
                    price = false;
                    mPriceFilter.setBackgroundColor(getResources().getColor(R.color.filter_grey));
                    Log.d("color", "now green");

                    // Should update list
                }
                readRestaurantData(new RestaurantDataCallback() {
                    @Override
                    public void onCallback(final ArrayList<Restaurant> restaurantList) {
                        rla.clear();
                        for (int i = 0; i < restaurantList.size(); i++) {
                            Restaurant temp = restaurantList.get(i);
                            boolean p = true;
                            boolean dist = true;
                            boolean t = true;
                            if (price) {
                                if (!(temp.getPrice().equals("$") || temp.getPrice().equals("$$"))) {
                                    p = false;
                                }
                            }
                            if (distance) {
                                double lon1 = -111.9229;
                                double lon2 = Double.parseDouble(temp.getLongCoordinates());
                                double lat1 = 33.4628;
                                double lat2 = Double.parseDouble(temp.getLatCoordinates());
                                double dlon = Math.abs(lon2 - lon1);
                                double dlat = Math.abs(lat2 - lat1);
                                double a = 12430 * dlat / 180;

                                double b = 24901 * dlon / 360 * Math.cos((lat1 + lat2) / 2);

                                double d = Math.pow((a * a + b * b), .5);
                                if (!(d < 1.5)) {
                                    dist = false;
                                }
                            }
                            if (time) {
                                if (!(temp.getTime() < 4)) {
                                    t = false;
                                }
                            }
                            if (p && dist && t) {
                                restaurants.add(restaurantList.get(i));
                                rla.notifyDataSetChanged();
                            }
                        }
                    }
                });
                SocialFirebase.callCurrentUser(new CurrentUserCallback() {
                    @Override
                    public void callback(final User currentUser) {
                        SocialFirebase.checkInParty(currentUser.getId(), new BooleanCallback() {
                            @RequiresApi(api = Build.VERSION_CODES.N)
                            @Override
                            public void callback(boolean data) {
                                if (data) {
                                    SocialFirebase.autoUpdateUserParty(currentUser, new PartyCallback<Party>() {
                                        @Override
                                        public void callback(Party party) {
                                            rla.clearPartyMemberDPs();
                                            final List<String> partyMembers = party.getparty();
                                            for (int i = 0; i < partyMembers.size(); i++) {
                                                SocialFirebase.readUserDietbyID(partyMembers.get(i), new DietPatternCallback() {
                                                    @Override

                                                    public void dietPatternCallback(DietPattern dietPattern) {
                                                        rla.addPartyMemberDPs(dietPattern);
                                                        rla.notifyDataSetChanged();
                                                    }
                                                });
                                            }
                                        }
                                    });
                                    rla.assignMunchScore();
                                    rla.sort(new RestaurantComparator());

                                } else {
                                    rla.clearPartyMemberDPs();
                                    rla.addPartyMemberDPs(currentUser.getDiet());
                                    rla.notifyDataSetChanged();
                                    rla.assignMunchScore();
                                    rla.sort(new RestaurantComparator());
                                }
                            }
                        });


                    }
                });
            }

        });

        mFilterButton = (ImageView) findViewById(R.id.filterButton);
        mFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(MapsActivity.this);
                dialog.setContentView(R.layout.maplist_filter_popup);
                dialog.setTitle("Title...");
                EditText mDistanceInput = (EditText) dialog.findViewById(R.id.distanceInput);
                EditText mPriceInput = (EditText) dialog.findViewById(R.id.priceInput);
                mQuickEatsBtn = (Button) dialog.findViewById(R.id.quickEatsButton);
                mSitDownBtn = (Button) dialog.findViewById(R.id.sitDownButton);

                mQuickEatsBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(quickeats == false){
                            quickeats = true;
                            mQuickEatsBtn.getBackground().setColorFilter(getResources().getColor(R.color.hot_pink), PorterDuff.Mode.SRC);
                        }else if(quickeats == true){
                            quickeats = false;
                            mQuickEatsBtn.getBackground().setColorFilter(getResources().getColor(R.color.filter_grey), PorterDuff.Mode.SRC);
                        }
                    }
                });
                mSitDownBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(sitdown == false){
                            sitdown = true;
                            mSitDownBtn.getBackground().setColorFilter(getResources().getColor(R.color.hot_pink), PorterDuff.Mode.SRC);
                        }else if(sitdown == true){
                            sitdown = false;
                            mSitDownBtn.getBackground().setColorFilter(getResources().getColor(R.color.filter_grey), PorterDuff.Mode.SRC);
                        }
                    }
                });
                dialog.show();
                Window window = dialog.getWindow();
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            }
        });

        initializeNavigationBar();
    }


    public static void readRestaurantData(final RestaurantDataCallback myCallback) {
        mRef1.child("RestaurantData").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Restaurant> rList = new ArrayList<>();

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    final Restaurant r1 = (Restaurant) dataSnapshot1.getValue(Restaurant.class);
                    String id = r1.getId();

                    Restaurant r = (Restaurant) dataSnapshot1.getValue(Restaurant.class);
                    //   r.setId(id);

                    rList.add(r);
                }

                myCallback.onCallback(rList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void init() {
        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();

//        mPlaceAutocompleteAdapter = new PlaceAutocompleteAdapter(this, mGoogleApiClient, LAT_LNG_BOUNDS, null);
//
//        placeAutocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
//            @Override
//            public void onPlaceSelected(Place place) {
//                final LatLng latLngLoc = place.getLatLng();
//
//                if (marker != null) {
//                    marker.remove();
//                }
//                moveCamera(new LatLng(place.getViewport().getCenter().latitude,
//                        place.getViewport().getCenter().longitude), DEFAULT_ZOOM);
//                markerInfo(place);
//                mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(MapsActivity.this));
//
//            }
//
//            @Override
//            public void onError(Status status) {
//
//            }
//        });
        hideSoftKeyboard();
        zoomCurrentLocation();
    }

    private void getDeviceLocation() {
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try {
            if (mLocationPermissionsGranted) {
                final Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Location currentLocation = (Location) task.getResult();

                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), DEFAULT_ZOOM);

                        } else {
                            Toast.makeText(MapsActivity.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage());
        }

    }

    private void moveCamera(LatLng latLng, float zoom) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        mMap.clear();
        hideSoftKeyboard();
    }

    private void initMap() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        if (mMap == null) {
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(MapsActivity.this);
        }

    }

    private void getLocationPermission() {
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionsGranted = true;
                initMap();
            } else {
                ActivityCompat.requestPermissions(this,
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: called.");
        mLocationPermissionsGranted = false;

        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionsGranted = false;
                            Log.d(TAG, "onRequestPermissionsResult: permission failed");
                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionsResult: permission granted");
                    mLocationPermissionsGranted = true;
                    //initialize our map
                    initMap();
                }
            }
        }
    }

    private void hideSoftKeyboard() {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

//    // Places API Autocomplete suggestions
//    private AdapterView.OnItemClickListener mAutocompleteClickListener = new AdapterView.OnItemClickListener() {
//        @Override
//        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//            hideSoftKeyboard();
//
//            final AutocompletePrediction item = mPlaceAutocompleteAdapter.getItem(i);
//            final String placeId = item.getPlaceId();
//
//            PendingResult placeResult = Places.GeoDataApi
//                    .getPlaceById(mGoogleApiClient, placeId);
//            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
//        }
//    };

//    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback = new ResultCallback<PlaceBuffer>() {
//        @Override
//        public void onResult(@NonNull PlaceBuffer places) {
//            if (!places.getStatus().isSuccess()) {
//                Log.d(TAG, "onResult: Place query did not complete successfully: " + places.getStatus().toString());
//                places.release();
//                return;
//            }
//            final Place place = places.get(0);
//
//            try {
//                mPlace = new PlaceInfo();
//                mPlace.setName(place.getName().toString());
//                Log.d(TAG, "onResult: name: " + place.getName());
//                mPlace.setAddress(place.getAddress().toString());
//                Log.d(TAG, "onResult: address: " + place.getAddress());
//                mPlace.setId(place.getId());
//                Log.d(TAG, "onResult: id:" + place.getId());
//                mPlace.setLatLng(place.getLatLng());
//                Log.d(TAG, "onResult: latlng: " + place.getLatLng());
//            } catch (NullPointerException e) {
//                Log.e(TAG, "onResult: NullPointerException: " + e.getMessage());
//            }
//            moveCamera(new LatLng(place.getViewport().getCenter().latitude, place.getViewport().getCenter().longitude), DEFAULT_ZOOM);
//
//            places.release();
//        }
//    };

    private void markerInfo(Place place) {
        String id = place.getId();

        mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(MapsActivity.this));
        if (place != null) {
            try {
                // Retrieves information from Google Places API
                String snippet = "Address: " + place.getAddress() + "\n" +
                        "Phone Number: " + place.getPhoneNumber() + "\n" +
                        "Website: " + place.getWebsiteUri() + "\n" +
                        "Price Rating: " + place.getPriceLevel() + "\n";

                MarkerOptions options = new MarkerOptions()
                        .position(place.getLatLng())
                        .title(place.getName().toString())
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                        .snippet(snippet);
                mMarker = mMap.addMarker(options);

            } catch (NullPointerException e) {
                Log.e(TAG, "moveCamera: NullPointerException: " + e.getMessage());
            }
        } else {
            mMap.addMarker(new MarkerOptions().position(place.getLatLng()));
        }
    }

    // Used to mark the reccomended restaurants
    public void markRestaurants() {
        readRestaurantData(new RestaurantDataCallback() {
            @Override
            public void onCallback(ArrayList<Restaurant> restaurantList) {
                markerList = new ArrayList<>();
                for (int i = 0; i < restaurantList.size(); i++) {
                    Restaurant b = restaurantList.get(i);

                    RestaurantItems rItems = new RestaurantItems();
                    rItems = b.getRestaurantItems();

                    String snippet = b.getAlias() + "_" +
                            b.getPrice() + "_";
                    LatLng restaurantCoor = new LatLng(Double.parseDouble(b.getLatCoordinates()), Double.parseDouble(b.getLongCoordinates()));
                    MarkerOptions options = new MarkerOptions()
                            .position(restaurantCoor)
                            .title(b.getName().toString())
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                            .snippet(snippet);

                    final CustomInfoWindowAdapter ciw = new CustomInfoWindowAdapter(MapsActivity.this);
                    SocialFirebase.callCurrentUser(new CurrentUserCallback() {

                        @Override
                        public void callback(final User currentUser) {
                            SocialFirebase.checkInParty(currentUser.getId(), new BooleanCallback() {
                                @Override
                                public void callback(boolean data) {
                                    if (data) {
                                        SocialFirebase.autoUpdateUserParty(currentUser, new PartyCallback<Party>() {
                                            @Override
                                            public void callback(Party party) {
                                                final List<String> partyMembers = party.getparty();
                                                for (int i = 0; i < partyMembers.size(); i++) {
                                                    SocialFirebase.readUserDietbyID(partyMembers.get(i), new DietPatternCallback() {
                                                        @Override

                                                        public void dietPatternCallback(DietPattern dietPattern) {

                                                            ciw.addPartyMemberDPs(dietPattern);
                                                            mMap.setInfoWindowAdapter(ciw);

                                                        }
                                                    });
                                                }
                                            }
                                        });

                                    } else {
                                        ciw.addPartyMemberDPs(currentUser.getDiet());
                                        mMap.setInfoWindowAdapter(ciw);

                                    }
                                }
                            });
                        }
                    });
                    mMarker = mMap.addMarker(options);
                    mMarker.setTag(b);
                    mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                        @Override
                        public void onInfoWindowClick(Marker marker) {
                            Restaurant r = (Restaurant) marker.getTag();
                            Intent intent = new Intent(MapsActivity.this, MenuView.class);
                            Bundle b = new Bundle();
                            b.putSerializable("obj", r);
                            intent.putExtras(b);
                            startActivity(intent);
                        }
                    });
                }
            }
        });
    }

    // Saves and loads map state when user opens map
    protected void onPause() {
        super.onPause();
        MapStateManager mgr = new MapStateManager(this);
        mgr.saveMapState(mMap);
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (mMap != null) {
            outState.putParcelable("AIzaSyCxDC75b0yA71-594xyVdH__3LBv-__RXk", mMap.getCameraPosition());
        }
    }

    // initializes map
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Changes theme to night mode
        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.style_json));

            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }
        ///////////////////////////////////////////////
        MapStateManager mgr = new MapStateManager(this);
        CameraPosition position = mgr.getSavedCameraPosition();

        if (position != null) {
            CameraUpdate update = CameraUpdateFactory.newCameraPosition(position);
            mMap.moveCamera(update);

            mMap.setMapType(mgr.getSavedMapType());
            init();
        }

        if (mLocationPermissionsGranted && position == null) {
            getDeviceLocation();
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);

            init();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    private void initializeNavigationBar() {
        // Bottom toolbar
        navBar = (BottomNavigationView) findViewById(R.id.navigationBar);
        BottomNavigationViewHelper.disableShiftMode(navBar);
        Menu menu = navBar.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);
        navBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_map:
                        startActivity(new Intent(MapsActivity.this, MapsActivity.class));
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
                                        if (data) {
                                            startActivity(new Intent(MapsActivity.this, PartyPage.class));
                                        } else {
                                            startActivity(new Intent(MapsActivity.this, CreateOrJoinPage.class));
                                        }
                                    }
                                });
                            }
                        });

                        item.setIcon(R.drawable.ic_party);
                        break;
                    case R.id.navigation_notifications:
                        startActivity(new Intent(MapsActivity.this, NotificationsPage.class));
                        item.setIcon(R.drawable.ic_notifications);
                        break;
                    case R.id.navigation_profile:
                        startActivity(new Intent(MapsActivity.this, Profile.class));
                        item.setIcon(R.drawable.ic_profile);
                        break;

                }
                return false;
            }
        });
    }


    public void zoomCurrentLocation() {
        CameraUpdate center =
                CameraUpdateFactory.newLatLng(ASUcoordinates);
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(16f);

        mMap.moveCamera(center);
        mMap.animateCamera(zoom);
        // LatLng coor = new LatLng(latitude,longitude);
        //  mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coor, DEFAULT_ZOOM));
        // mMap.clear();

    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Toast.makeText(this, marker.getTitle(), Toast.LENGTH_SHORT).show();
    }

    public void autoComplete(){
        String[] keywordTest = new String[]{
                "Chinese",
                "Sit-Down",
                "Quick-Eats",
                "Desserts",
                "Japanese",
                "Mexican",
                "Coffee",
                "Italian",
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, keywordTest);

        KeywordCompletionView completionView = (KeywordCompletionView) findViewById(R.id.foodSearchBar);
        completionView.setAdapter(adapter);

    }
}
