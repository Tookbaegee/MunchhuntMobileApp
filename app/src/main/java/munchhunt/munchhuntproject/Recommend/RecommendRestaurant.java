package munchhunt.munchhuntproject.Recommend;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import munchhunt.munchhuntproject.List.YelpBusinessInfo;
import munchhunt.munchhuntproject.Objects.Categories;
import munchhunt.munchhuntproject.Objects.History;

public class RecommendRestaurant extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private FirebaseUser mUser;
    Categories c;

    Button doneTest;

    String id;
    History h;


    ArrayList<String>historyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        historyList = new ArrayList<String>();


        readHistory(new MyCallbackHistory() {
            @Override
            public void onCallback(ArrayList<String> history) {

                for (int i = 0 ; i < history.size(); i++) {

                    readRestaurant(new MyCallbackRestaurant() {
                        @Override
                        public void onCallback(ArrayList<YelpBusinessInfo> match) {
                            for (int x = 0 ; x  < match.size(); x++)
                            {
                                Log.d("MATCH", match.get(x).getName());
                            }

                        }
                    }, history.get(i));
                }


            //    Log.d("HISTORY", history.toString());
            }
        });
        /*
        readHistory(new MyCallbackHistory() {
            @Override
            public void onCallback(final ArrayList<String> history) {
                historyList.addAll(history);
                readRestaurant(new MyCallbackRestaurant() {
                    @Override
                    public void onCallback(ArrayList<Categories> match) {
                       // Categories c1 = (Categories) match.get(history.size()-1);


                    }
                }, historyList);

                Log.d("ANSWER", c.getMediterranean() +"");
                Log.d("ANSWER",c.getBurgers()+ "");

            }
        });

*/



        doneTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public void readHistory(final MyCallbackHistory myCallback)
    {

        mDatabase.child("Users").child(mUser.getUid()).child("history").addListenerForSingleValueEvent(new ValueEventListener() {
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
                    YelpBusinessInfo b = (YelpBusinessInfo) ds.getValue(YelpBusinessInfo.class);

                    if (Category.equalsIgnoreCase(b.getAlias()))
                    {
                        list.add(b);
                    }
                }

                myCallback.onCallback(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }
    /*
    public void readRestaurants(final MyCallbackRestaurants myCallback, String id)
    {
        mDatabase.c

        mDatabase.child("Restaurant").child(id).child("Menu").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Menu> menu1 = new ArrayList<Menu>();
                for (DataSnapshot ds: dataSnapshot.getChildren())
                {

                    Menu m = (Menu) ds.getValue(Menu.class);
                    String name = m.getName();
                    final boolean restDairy = m.getDairy();
                    final boolean restGluten = m.getGluten();
                    final boolean restPeanut = m.getPeanut();
                    final boolean restRed_Meat = m.getRed_meat();
                    final boolean restWhite_Meat = m.getWhite_meat();
                    final boolean restSeafood = m.getSeafood();
                    final boolean restVeg = m.getVegetables();
                    Menu getRestTest = new Menu(name, restRed_Meat, restWhite_Meat, restGluten, restVeg, restPeanut,restDairy, restSeafood );
                    menu1.add(getRestTest);


                }
                myCallback.onCallback(menu1);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
*/



}
