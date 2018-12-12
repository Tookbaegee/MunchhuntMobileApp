package munchhunt.munchhuntproject.Map;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import munchhunt.munchhuntproject.MunchScore.DietPatternComparator;
import munchhunt.munchhuntproject.Objects.Restaurant;
import munchhunt.munchhuntproject.Objects.RestaurantItems;
import munchhunt.munchhuntproject.R;
import munchhunt.munchhuntproject.Objects.DietPattern;


public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private final View mWindow;
    private Context mContext;
    private static final String TAG = "CustomInfoWindowAdapter";
    private List<DietPattern> partyMemberDPs = new ArrayList<DietPattern>();

    public CustomInfoWindowAdapter(Context context) {
        mContext = context;
        mWindow = LayoutInflater.from(context).inflate(R.layout.custom_info_window, null);
    }

    public void addPartyMemberDPs(DietPattern userDP){
        partyMemberDPs.add(userDP);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void rendowWindowText(Marker marker, View view){

        String id = marker.getId();
//        getPhotos(id);
        Log.d(TAG, "String " + marker.getId());

        Restaurant restaurant = (Restaurant) marker.getTag();

        String title = marker.getTitle();
        TextView tvTitle = (TextView) view.findViewById(R.id.title);
        TextView tvFoodType = (TextView) view.findViewById(R.id.foodTypeTag);
        TextView tvRestaurantType = (TextView) view.findViewById(R.id.restaurantTypeTag);
        TextView tvPricing = (TextView) view.findViewById(R.id.pricingTag);
        TextView tvMunchRating = (TextView) view.findViewById(R.id.windowMunchRating);


        if(!title.equals("")){
            tvTitle.setText(title);
        }

        String snippet = marker.getSnippet();
        final String[] snippetParser = snippet.split("_");

        tvFoodType.setText(restaurant.getAlias());
        tvPricing.setText(restaurant.getPrice());

        RestaurantItems rItems = restaurant.getRestaurantItems();

        if(partyMemberDPs.size() != 1) {
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

            tvMunchRating.setText(formatter.format(munchScore) + "%");


        }

    }

    @Override
    @RequiresApi(api = Build.VERSION_CODES.N)
    public View getInfoWindow(Marker marker) {
        rendowWindowText(marker, mWindow);
        return mWindow;
    }

    @Override
    @RequiresApi(api = Build.VERSION_CODES.N)
    public View getInfoContents(Marker marker) {
        rendowWindowText(marker, mWindow);
        return mWindow;
    }

    // Request photos and metadata for the specified place.
//    private void getPhotos(String placeId) {
//        final Task<PlacePhotoMetadataResponse> photoMetadataResponse = mGeoDataClient.getPlacePhotos(placeId);
//        photoMetadataResponse.addOnCompleteListener(new OnCompleteListener<PlacePhotoMetadataResponse>() {
//            @Override
//            public void onComplete(@NonNull Task<PlacePhotoMetadataResponse> task) {
//                // Get the list of photos.
//                PlacePhotoMetadataResponse photos = task.getResult();
//                // Get the PlacePhotoMetadataBuffer (metadata for all of the photos).
//                PlacePhotoMetadataBuffer photoMetadataBuffer = photos.getPhotoMetadata();
//                // Get the first photo in the list.
//                PlacePhotoMetadata photoMetadata = photoMetadataBuffer.get(0);
//                // Get the attribution text.
//                CharSequence attribution = photoMetadata.getAttributions();
//                // Get a full-size bitmap for the photo.
//                Task<PlacePhotoResponse> photoResponse = mGeoDataClient.getPhoto(photoMetadata);
//                photoResponse.addOnCompleteListener(new OnCompleteListener<PlacePhotoResponse>() {
//                    @Override
//                    public void onComplete(@NonNull Task<PlacePhotoResponse> task) {
//                        PlacePhotoResponse photo = task.getResult();
//                        bitmap = photo.getBitmap();
//                    }
//                });
//            }
//        });
//    }

}