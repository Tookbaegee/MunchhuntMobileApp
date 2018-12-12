package munchhunt.munchhuntproject.Map;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import munchhunt.munchhuntproject.MunchScore.DietPatternComparator;
import munchhunt.munchhuntproject.Objects.DietPattern;
import munchhunt.munchhuntproject.Objects.Restaurant;
import munchhunt.munchhuntproject.Objects.RestaurantItems;
import munchhunt.munchhuntproject.R;

/**
 * Created by adria on 5/22/2018.
 */

/**
 * Created by adria on 5/22/2018.
 */

//Adapter class to put values into listView
public class RestaurantListAdapter extends ArrayAdapter<Restaurant> implements Runnable{
    private Context mContext;
    private int mResource;
    private int lastPosition = -1;
    private ViewHolder holder;
    private List<Restaurant> restaurants;
    private List<DietPattern> partyMemberDPs;

    @Override
    public void run() {
        synchronized (this){

        }
    }


    static class ViewHolder {
        TextView name;
        TextView Title;
        TextView Direction;
        TextView munchRating;

    }


    public RestaurantListAdapter(Context context, int resource, List<Restaurant> restaurants)
    {
        super (context, resource, restaurants);
        mContext = context;
        mResource = resource;
        this.restaurants = restaurants;
        this.partyMemberDPs = new ArrayList<DietPattern>();

    }

    public void clearPartyMemberDPs(){
        partyMemberDPs = new ArrayList<DietPattern>();
    }

    public void addPartyMemberDPs(DietPattern userDP){
        partyMemberDPs.add(userDP);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void assignMunchScore(){

        Log.d("assigning munchscore", "");
        for(int j = 0; j < restaurants.size(); j++) {
            RestaurantItems rItems = new RestaurantItems();
            if (getItem(j) != null) {
                rItems = getItem(j).getRestaurantItems();
            }
            if(partyMemberDPs.size() == 0){
                Log.d("party Member", "is empty");
            }
            else if (partyMemberDPs.size() > 1) {
                Log.d("party Member", partyMemberDPs.toString());
                partyMemberDPs.sort(new DietPatternComparator());
                //   Log.d("MUNCH SCORE")

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


                    weightVal += .02;
                    totalParty = (double) totalParty + (double) (totalUser * weightVal);

                }


                double size = (double) (rItems.getBeef() + rItems.getPork() + rItems.getWhiteMeat() + rItems.getCrustacean() + rItems.getShellfish() + rItems.getFish() + rItems.getDairy() + rItems.getEggs() + rItems.getGlutenFree() + rItems.getNuts()) * (partyMemberDPs.size());
                double munchScore = (double) (totalParty / size) * 100;
                NumberFormat formatter = new DecimalFormat("###");
                Restaurant restaurant = getItem(j);
                restaurant.setMunchScore(munchScore);
                restaurants.set(j, restaurant);
            } else {
                double totalParty = 0.0;
                double weightVal = 1.0;
                double totalUser = 0.0;
                DietPattern d = partyMemberDPs.get(0);
                Log.d("solo dietpattern", d.toString());

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
                NumberFormat formatter = new DecimalFormat("###");
                Restaurant restaurant = getItem(j);
                restaurant.setMunchScore(munchScore);
                restaurants.set(j, restaurant);
            }
        }

        sort(new RestaurantComparator());

    }




    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        String name = getItem(position).getName();
        String title = getItem(position).getAlias();
        String direction = getItem(position).getDirections();
        //String munchRating = getItem(position).getMunchScore();
        //String munchRating = "Default";


        //  restaurant restaurant = new restaurant(name, direction, munchRating);

        final View result;
        if (convertView == null)
        {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);

            holder = new ViewHolder();


            holder.name = (TextView) convertView.findViewById(R.id.restName);
            holder.Title = (TextView) convertView.findViewById(R.id.munchTitle);
            holder.Direction = (TextView) convertView.findViewById(R.id.munchLocation);
            holder.munchRating = (TextView)  convertView.findViewById(R.id.munchRating);

            convertView.setTag(holder);

            result = convertView;

        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }


        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition)? R.anim.loading_down_anim: R.anim.loading_up_anim);
        result.startAnimation(animation);
        lastPosition = position;

        holder.name.setText(name);
        holder.Title.setText(title);
        holder.Direction.setText(direction);
        NumberFormat formatter = new DecimalFormat("###");
        holder.munchRating.setText("Munch Score: "+ formatter.format(getItem(position).getMunchScore()) + "%");
//        RestaurantItems rItems = new RestaurantItems();
//        if(getItem(position) != null){
//            rItems = getItem(position).getRestaurantItems();
//        }
//
//        if(partyMemberDPs.size() != 1){
//            partyMemberDPs.sort(new DietPatternComparator());
//            //   Log.d("MUNCH SCORE")
//
//            double totalParty = 0.0;
//            double weightVal = 1.0;
//
//            for (int i = 0; i < partyMemberDPs.size(); i++) {
//                double totalUser = 0.0;
//                DietPattern d = partyMemberDPs.get(i);
//
//                if (d.getBeef()) {
//                    totalUser += rItems.getBeef();
//
//                }
//                if (d.getPork()) {
//                    totalUser += rItems.getPork();
//                }
//                if (d.getWhite_meat()) {
//                    totalUser += rItems.getWhiteMeat();
//                }
//                if (d.getNut()) {
//                    totalUser += rItems.getNuts();
//
//                }
//                if (d.getGluten()) {
//                    totalUser += rItems.getGlutenFree();
//
//                }
//                if (d.getDairy()) {
//                    totalUser += rItems.getDairy();
//
//                }
//                if (d.getCrustacean()) {
//                    totalUser += rItems.getCrustacean();
//
//                }
//                if (d.getShellfish()) {
//                    totalUser += rItems.getShellfish();
//
//                }
//                if (d.getFish()) {
//                    totalUser += rItems.getFish();
//
//                }
//                if (d.getEggs()) {
//                    totalUser += rItems.getEggs();
//
//                }
//
//
//                weightVal += .02;
//                totalParty = (double) totalParty + (double) (totalUser * weightVal);
//
//            }
//
//
//            double size = (double) (rItems.getBeef() + rItems.getPork() + rItems.getWhiteMeat() + rItems.getCrustacean() + rItems.getShellfish() + rItems.getFish() + rItems.getDairy() + rItems.getEggs() + rItems.getGlutenFree() + rItems.getNuts()) * (partyMemberDPs.size());
//            double munchScore = (double) (totalParty / size) * 100;
//            NumberFormat formatter = new DecimalFormat("###");
//            Restaurant restaurant = getItem(position);
//            restaurant.setMunchScore(munchScore);
//            restaurants.set(position, restaurant);
//            notifyDatasetChanged();
//            holder.munchRating.setText("Munch Score: "+ formatter.format(munchScore) + "%");
//        }
//        else{
//            double totalParty = 0.0;
//            double weightVal = 1.0;
//            double totalUser = 0.0;
//            DietPattern d = partyMemberDPs.get(0);
//
//            if (d.getBeef()) {
//                totalUser += rItems.getBeef();
//
//            }
//            if (d.getPork()) {
//                totalUser += rItems.getPork();
//            }
//            if (d.getWhite_meat()) {
//                totalUser += rItems.getWhiteMeat();
//            }
//            if (d.getNut()) {
//                totalUser += rItems.getNuts();
//
//            }
//            if (d.getGluten()) {
//                totalUser += rItems.getGlutenFree();
//
//            }
//            if (d.getDairy()) {
//                totalUser += rItems.getDairy();
//
//            }
//            if (d.getCrustacean()) {
//                totalUser += rItems.getCrustacean();
//
//            }
//            if (d.getShellfish()) {
//                totalUser += rItems.getShellfish();
//
//            }
//            if (d.getFish()) {
//                totalUser += rItems.getFish();
//
//            }
//            if (d.getEggs()) {
//                totalUser += rItems.getEggs();
//
//            }
//
//            totalParty = (double) totalParty + (double) (totalUser * weightVal);
//
//
//            double size = (double) (rItems.getBeef() + rItems.getPork() + rItems.getWhiteMeat() + rItems.getCrustacean() + rItems.getShellfish() + rItems.getFish() + rItems.getDairy() + rItems.getEggs() + rItems.getGlutenFree() + rItems.getNuts());
//            double munchScore = (double) (totalParty / size) * 100;
//
//            NumberFormat formatter = new DecimalFormat("###");
//            Restaurant restaurant = getItem(position);
//            restaurant.setMunchScore(munchScore);
//            restaurants.set(position, restaurant);
//            notifyDatasetChanged();
//            holder.munchRating.setText("Munch Score: "+ formatter.format(munchScore) + "%");
//        }





//        SocialFirebase.callCurrentUser(new CurrentUserCallback() {
//
//                    @Override
//                    public void callback(final User currentUser) {
//                        SocialFirebase.checkInParty(currentUser.getId(), new BooleanCallback() {
//                    @Override
//                    public void callback(boolean data) {
//                        if (true) {
//                            Log.d("in party?", "true");
//                            SocialFirebase.autoUpdateUserParty(currentUser, new PartyCallback<Party>() {
//                                @RequiresApi(api = Build.VERSION_CODES.N)
//                                @Override
//                                public void callback(Party party) {
//                                    //currentParty.add(new FriendView(R.drawable.adrianehot, party.getId()));
//                                    Log.d("callback accessed", party.getId());
//                                    final List<DietPattern> partyMemberDPs = new ArrayList<DietPattern>();
//
//                                    for (int i = 0; i < party.getparty().size(); i++){
//                                        Log.d("accessed", party.getparty().get(i).toString());
//                                        SocialFirebase.readUser(party.getparty().get(i), new UserCallback<User>() {
//                                            @Override
//                                            public void callback(User user) {
//                                                partyMemberDPs.add(user.getDiet());
//                                            }
//                                        });
//
//                                    }
//                                    partyMemberDPs.sort(new DietPatternComparator());
//                                    //   Log.d("MUNCH SCORE")
//
//                                    double totalParty = 0.0;
//                                    double weightVal = 1.0;
//
//                                    for (int i = 0; i < partyMemberDPs.size(); i++) {
//                                        double totalUser = 0.0;
//                                        DietPattern d = partyMemberDPs.get(i);
//
//                                        if (d.getBeef()) {
//                                            totalUser += rItems.getBeef();
//
//                                        }
//                                        if (d.getPork()) {
//                                            totalUser += rItems.getPork();
//                                        }
//                                        if (d.getWhite_meat()) {
//                                            totalUser += rItems.getWhiteMeat();
//                                        }
//                                        if (d.getNut()) {
//                                            totalUser += rItems.getNuts();
//
//                                        }
//                                        if (d.getGluten()) {
//                                            totalUser += rItems.getGlutenFree();
//
//                                        }
//                                        if (d.getDairy()) {
//                                            totalUser += rItems.getDairy();
//
//                                        }
//                                        if (d.getCrustacean()) {
//                                            totalUser += rItems.getCrustacean();
//
//                                        }
//                                        if (d.getShellfish()) {
//                                            totalUser += rItems.getShellfish();
//
//                                        }
//                                        if (d.getFish()) {
//                                            totalUser += rItems.getFish();
//
//                                        }
//                                        if (d.getEggs()) {
//                                            totalUser += rItems.getEggs();
//
//                                        }
//
//
//                                        weightVal += .15;
//                                        totalParty = (double) totalParty + (double) (totalUser * weightVal);
//
//                                    }
//
//
//                                    double size = (double) (rItems.getBeef() + rItems.getPork() + rItems.getWhiteMeat() + rItems.getCrustacean() + rItems.getShellfish() + rItems.getFish() + rItems.getDairy() + rItems.getEggs() + rItems.getGlutenFree() + rItems.getNuts()) * (partyMemberDPs.size());
//                                    double munchScore = (double) (totalParty / size) * 100;
//                                    Log.d("SIZE", size+ "");
//                                    Log.d("TOTALPARTY", totalParty + "");
//                                    Log.d("MUNCHSCORE", munchScore + "");
//                                    NumberFormat formatter = new DecimalFormat("###");
//
//                                    holder.munchRating.setText("Munch Rating: "+ formatter.format(munchScore) + "%");
//
//
//
//                                }
//                            });
////                            SocialFirebase.getPartyDietPattern(currentUser, new DietPatternListCallback() {
////                                @TargetApi(Build.VERSION_CODES.N)
////                                @Override
////                                public void dietPatternListCallback(List<DietPattern> dietPatterns) {
////                                    Log.d("diet pattern", dietPatterns.toString());
////                                    final List<DietPattern> partyMemberDPs = dietPatterns;
////                                    partyMemberDPs.sort(new DietPatternComparator());
////                                    //   Log.d("MUNCH SCORE")
////
////                                    double totalParty = 0.0;
////                                    double weightVal = 1.0;
////
////                                    for (int i = 0; i < partyMemberDPs.size(); i++) {
////                                        double totalUser = 0.0;
////                                        DietPattern d = partyMemberDPs.get(i);
////
////                                        if (d.getBeef()) {
////                                            totalUser += rItems.getBeef();
////
////                                        }
////                                        if (d.getPork()) {
////                                            totalUser += rItems.getPork();
////                                        }
////                                        if (d.getWhite_meat()) {
////                                            totalUser += rItems.getWhiteMeat();
////                                        }
////                                        if (d.getNut()) {
////                                            totalUser += rItems.getNuts();
////
////                                        }
////                                        if (d.getGluten()) {
////                                            totalUser += rItems.getGlutenFree();
////
////                                        }
////                                        if (d.getDairy()) {
////                                            totalUser += rItems.getDairy();
////
////                                        }
////                                        if (d.getCrustacean()) {
////                                            totalUser += rItems.getCrustacean();
////
////                                        }
////                                        if (d.getShellfish()) {
////                                            totalUser += rItems.getShellfish();
////
////                                        }
////                                        if (d.getFish()) {
////                                            totalUser += rItems.getFish();
////
////                                        }
////                                        if (d.getEggs()) {
////                                            totalUser += rItems.getEggs();
////
////                                        }
////
////
////                                        weightVal += .15;
////                                        totalParty = (double) totalParty + (double) (totalUser * weightVal);
////
////                                    }
////
////
////                                    double size = (double) (rItems.getBeef() + rItems.getPork() + rItems.getWhiteMeat() + rItems.getCrustacean() + rItems.getShellfish() + rItems.getFish() + rItems.getDairy() + rItems.getEggs() + rItems.getGlutenFree() + rItems.getNuts()) * (partyMemberDPs.size());
////                                    double munchScore = (double) (totalParty / size) * 100;
////                                    Log.d("SIZE", size+ "");
////                                    Log.d("TOTALPARTY", totalParty + "");
////                                    Log.d("MUNCHSCORE", munchScore + "");
////                                    NumberFormat formatter = new DecimalFormat("###");
////
////                                    holder.munchRating.setText("Munch Rating: "+ formatter.format(munchScore) + "%");
////
////
////                                }
////                            });
//                        } else {
//
//
//                            double totalParty = 0.0;
//                            double weightVal = 1.0;
//                            double totalUser = 0.0;
//                            DietPattern d = currentUser.getDiet();
//
//                            if (d.getBeef()) {
//                                totalUser += rItems.getBeef();
//
//                            }
//                            if (d.getPork()) {
//                                totalUser += rItems.getPork();
//                            }
//                            if (d.getWhite_meat()) {
//                                totalUser += rItems.getWhiteMeat();
//                            }
//                            if (d.getNut()) {
//                                totalUser += rItems.getNuts();
//
//                            }
//                            if (d.getGluten()) {
//                                totalUser += rItems.getGlutenFree();
//
//                            }
//                            if (d.getDairy()) {
//                                totalUser += rItems.getDairy();
//
//                            }
//                            if (d.getCrustacean()) {
//                                totalUser += rItems.getCrustacean();
//
//                            }
//                            if (d.getShellfish()) {
//                                totalUser += rItems.getShellfish();
//
//                            }
//                            if (d.getFish()) {
//                                totalUser += rItems.getFish();
//
//                            }
//                            if (d.getEggs()) {
//                                totalUser += rItems.getEggs();
//
//                            }
//
//                            totalParty = (double) totalParty + (double) (totalUser * weightVal);
//
//
//                            double size = (double) (rItems.getBeef() + rItems.getPork() + rItems.getWhiteMeat() + rItems.getCrustacean() + rItems.getShellfish() + rItems.getFish() + rItems.getDairy() + rItems.getEggs() + rItems.getGlutenFree() + rItems.getNuts());
//                            double munchScore = (double) (totalParty / size) * 100;
//
//                            Log.d("MUNCHSCORE", munchScore + "");
//                            NumberFormat formatter = new DecimalFormat("###");
//
//                            holder.munchRating.setText("Munch Rating: "+ formatter.format(munchScore) + "%");
//                        }
//                    }
//
//
//
//
//                });
//
//            }
//
//        });

//    }
//        MunchScore.calculateMunchScore(new MunchScoreCallback() {
//            @Override
//            public void onCallback(double munchScore) {
//                NumberFormat formatter = new DecimalFormat("###");
//
//                holder.munchRating.setText("Munch Rating: "+ formatter.format(munchScore) + "%");
//            }
//        }, getItem(position).getId());


        return convertView;
    }

}
