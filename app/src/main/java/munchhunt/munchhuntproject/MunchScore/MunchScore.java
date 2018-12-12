package munchhunt.munchhuntproject.MunchScore;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import munchhunt.munchhuntproject.Callback.BooleanCallback;
import munchhunt.munchhuntproject.Callback.CurrentUserCallback;
import munchhunt.munchhuntproject.Callback.DietPatternListCallback;
import munchhunt.munchhuntproject.Objects.RestaurantItems;
import munchhunt.munchhuntproject.Party.SocialFirebase;
import munchhunt.munchhuntproject.Objects.DietPattern;
import munchhunt.munchhuntproject.Objects.User;

public class MunchScore {
    private static DatabaseReference mRef1;
    public MunchScore(){




    }


    public static void calculateMunchScore(final MunchScoreCallback myCallback, String id)
    {


        readRestaurantData(new RestaurantItemsCallback() {
            @Override
            public void onCallback(RestaurantItems restaurantList) {
                final RestaurantItems rItems = restaurantList;
                SocialFirebase.callCurrentUser(new CurrentUserCallback() {

                    @Override
                    public void callback(final User currentUser) {
                        SocialFirebase.checkInParty(currentUser.getId(), new BooleanCallback() {
                            @Override
                            public void callback(boolean data) {
                                if (data) {
                                    SocialFirebase.getPartyDietPattern(currentUser, new DietPatternListCallback() {
                                        @TargetApi(Build.VERSION_CODES.N)
                                        @Override
                                        public void dietPatternListCallback(List<DietPattern> dietPatterns) {
                                            Log.d("Diet Patterns", dietPatterns.toString());
                                            final List<DietPattern> partyMemberDPs = dietPatterns;
                                            partyMemberDPs.sort(new DietPatternComparator());

                                            double totalParty = 0.0;
                                            double weightVal = 1.0;

                                            for (int i = 0; i < partyMemberDPs.size(); i++) {
                                                double totalUser = 0.0;
                                                DietPattern d = partyMemberDPs.get(i);

                                                if (d.getBeef()) {
                                                    totalUser += rItems.getBeef();

                                                }
                                                if (d.getPork()) {
                                                    totalUser += rItems.getPork();
                                                }
                                                if (d.getWhite_meat()) {
                                                    totalUser += rItems.getWhiteMeat();
                                                }
                                                if (d.getNut()) {
                                                    totalUser += rItems.getNuts();

                                                }
                                                if (d.getGluten()) {
                                                    totalUser += rItems.getGlutenFree();

                                                }
                                                if (d.getDairy()) {
                                                    totalUser += rItems.getDairy();

                                                }
                                                if (d.getCrustacean()) {
                                                    totalUser += rItems.getCrustacean();

                                                }
                                                if (d.getShellfish()) {
                                                    totalUser += rItems.getShellfish();

                                                }
                                                if (d.getFish()) {
                                                    totalUser += rItems.getFish();

                                                }
                                                if (d.getEggs()) {
                                                    totalUser += rItems.getEggs();

                                                }

                                                totalParty = (double) totalParty + (double) (totalUser * weightVal);

                                                weightVal += .15;

                                            }
                                            double size = (double) (rItems.getBeef() + rItems.getPork() + rItems.getWhiteMeat() + rItems.getCrustacean() + rItems.getShellfish() + rItems.getFish() + rItems.getDairy() + rItems.getEggs() + rItems.getGlutenFree() + rItems.getNuts()) * (partyMemberDPs.size());
                                            double munchScore = (double) (totalParty / size) * 100;

                                            Log.d("MUNCHSCORE", munchScore + "");
                                            myCallback.onCallback(munchScore);


                                        }
                                    });
                                } else {


                                    double totalParty = 0.0;
                                    double weightVal = 1.0;
                                    double totalUser = 0.0;
                                    DietPattern d = currentUser.getDiet();

                                    if (d.getBeef()) {
                                        totalUser += rItems.getBeef();

                                    }
                                    if (d.getPork()) {
                                        totalUser += rItems.getPork();
                                    }
                                    if (d.getWhite_meat()) {
                                        totalUser += rItems.getWhiteMeat();
                                    }
                                    if (d.getNut()) {
                                        totalUser += rItems.getNuts();

                                    }
                                    if (d.getGluten()) {
                                        totalUser += rItems.getGlutenFree();

                                    }
                                    if (d.getDairy()) {
                                        totalUser += rItems.getDairy();

                                    }
                                    if (d.getCrustacean()) {
                                        totalUser += rItems.getCrustacean();

                                    }
                                    if (d.getShellfish()) {
                                        totalUser += rItems.getShellfish();

                                    }
                                    if (d.getFish()) {
                                        totalUser += rItems.getFish();

                                    }
                                    if (d.getEggs()) {
                                        totalUser += rItems.getEggs();

                                    }

                                    totalParty = (double) totalParty + (double) (totalUser * weightVal);


                                    double size = (double) (rItems.getBeef() + rItems.getPork() + rItems.getWhiteMeat() + rItems.getCrustacean() + rItems.getShellfish() + rItems.getFish() + rItems.getDairy() + rItems.getEggs() + rItems.getGlutenFree() + rItems.getNuts());
                                    double munchScore = (double) (totalParty / size) * 100;

                                    Log.d("MUNCHSCORE", munchScore + "");
                                    myCallback.onCallback(munchScore);
                                }
                            }




                        });

                    }

                });

            }
        }, id);

    }




    public static void readRestaurantData(final RestaurantItemsCallback myCallback, String id)
    {
        mRef1 = FirebaseDatabase.getInstance().getReference();

        mRef1.child("RestaurantData").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                RestaurantItems r = (RestaurantItems)dataSnapshot.child("restaurantItems").getValue(RestaurantItems.class);

                Log.d("Restrarant Items", r.toString());
                myCallback.onCallback(r);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



}
