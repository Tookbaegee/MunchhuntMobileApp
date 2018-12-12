package munchhunt.munchhuntproject.Recommend;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import munchhunt.munchhuntproject.List.Menus;
import munchhunt.munchhuntproject.List.YelpBusinessInfo;
import munchhunt.munchhuntproject.Objects.Categories;
import munchhunt.munchhuntproject.Objects.History;

public class Reccommend {
    private DatabaseReference mDatabase;
    private FirebaseUser mUser;
    Categories c;

    Button doneTest;

    String id;
    History h;


    ArrayList<String>historyList;


    public Reccommend()
    {

    }

    public ArrayList<YelpBusinessInfo> getRecommendation()
    {
        final ArrayList<YelpBusinessInfo> match1 = new ArrayList<>();

        readHistory(new MyCallbackHistory() {
            @Override
            public void onCallback(ArrayList<String> history) {


                for (int i = 0 ; i < history.size(); i++) {

                    readRestaurant(new MyCallbackRestaurant() {
                        @Override
                        public void onCallback(ArrayList<YelpBusinessInfo> match) {
                            for (int x = 0 ; x  < match.size(); x++)
                            {
                                YelpBusinessInfo y = match.get(x);
                                String id = y.getYelpId();
                                String name = y.getName();
                                String alias = y.getAlias();
                                String directions = y.getDirections();
                                String latCoordinates = y.getLatCoordinates();
                                String longCoordinates = y.getLongCoordinates();
                                String rating = y.getRating();
                                String price = y.getPrice();
                                Menus menu = new Menus();
                               // ublic YelpBusinessInfo(String id, String name, String alias, String directions, String latCoordinates, String longCoordinates, String rating, String price, Menus menus )
                                YelpBusinessInfo y1 = new YelpBusinessInfo(id,name, alias, directions, latCoordinates,longCoordinates,rating, price, menu );
                                Log.d("MATCH", match.get(x).getName());
                                match1.add(y1);

                              //  Intent intent = new Intent(Reccommen.this, MapsActivity.class);
                            }
                            mDatabase.child("Rec").child(mUser.getUid()).child("Recommend").setValue(match1);

                        }
                    }, history.get(i));
                }


                //    Log.d("HISTORY", history.toString());
            }
        });
        Log.d("RECCOMEND_RESTAURANT", match1.toString());
        return match1;
    }
    public void readHistory(final MyCallbackHistory myCallback)
    {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mUser = FirebaseAuth.getInstance().getCurrentUser();

        mDatabase.child("Rec").child(mUser.getUid()).child("history").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> list = new ArrayList<>();
                Categories c = new Categories(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);

                for (DataSnapshot ds: dataSnapshot.getChildren())
                {
                    History history = (History)ds.getValue(History.class);
                    c.match(history.getCategory());
                    c.updateArrayist();

                }
                Log.d("AMERICANNEW", c.getAmericanNew() + "");
                Log.d("BURGERS", c.getBurgers() + "");
                Log.d("SUSHI", c.getSushiBars() + "");
                Log.d("MEXICAN", c.getMexican() + "");
                list = c.getResult();
                //  h = new History(history.getRestaurantHistory());
                //    list = h.getRestaurantHistory();
                myCallback.onCallback(list);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void readRestaurant(final MyCallbackRestaurant myCallback, final String Category)
    {
        mDatabase.child("Restaurant").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<YelpBusinessInfo> list = new ArrayList<>();
                for (DataSnapshot ds: dataSnapshot.getChildren())
                {
                    Map<String, Object> b = (HashMap<String, Object>) ds.getValue();

                    if (Category.equalsIgnoreCase(b.get("alias").toString()))
                    {
                        /*
                        Map<String, Object> b1 = (HashMap<String, Object>) dataSnapshot.getValue();
                        YelpBusinessInfo y = (YelpBusinessInfo)b1.get(b.get("yelpId").toString());
                        Log.d("YELPBUSINESSINFO",y.toString());
                        */


                        String id = b.get("yelpId").toString();
                        String name =b.get("name").toString();
                        String alias= b.get("alias").toString();
                        String directions=b.get("directions").toString();
                        String latCoordinates = b.get("latCoordinates").toString();
                        String longCoordinates = b.get("longCoordinates").toString();
                        String rating = b.get("rating").toString();
                        String price = b.get("price").toString();
                        Menus menus = new Menus();
                        //ublic YelpBusinessInfo(String id, String name, String alias, String directions, String latCoordinates, String longCoordinates, String rating, String price, Menus menus )
                        YelpBusinessInfo y = new YelpBusinessInfo(id, name, alias, directions, latCoordinates, longCoordinates,rating, price,menus);

                        list.add(y);
                    }
                }

                myCallback.onCallback(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }
}
