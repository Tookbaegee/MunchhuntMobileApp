package munchhunt.munchhuntproject.Map;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

import munchhunt.munchhuntproject.Objects.Restaurant;
import munchhunt.munchhuntproject.R;

public class RestaurantInfoFragment extends Fragment {


    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    String name;
    Restaurant r;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.maplist_restaurantinfo_fragment, container, false);


        final ImageView ivProfilePicture = (ImageView) v.findViewById(R.id.restaurantPictures);
        TextView tvFoodType = (TextView) v.findViewById(R.id.foodTypeTag);
        TextView tvPricing = (TextView) v.findViewById(R.id.pricingTag);
        TextView tvAddress = (TextView) v.findViewById(R.id.tvAddress);




//        StorageReference imageRef = storageReference.child("Restaurant/test.jpg");
        MenuView activity = (MenuView) getActivity();
        Bundle results = activity.getMyData();
        StorageReference restaurantRef = storageReference.child("Restaurant");

        r = (Restaurant) results.getSerializable("restaurant");
        String restaurantName = r.getName();
        StorageReference imageRef = restaurantRef.child(restaurantName + ".jpg");

        // Website
        TextView mWebsite = (TextView) v.findViewById(R.id.tvWebsite);
        mWebsite.setText(r.getWebsite());

        // Hours
        TextView mHours = (TextView) v.findViewById(R.id.tvHours);
        mHours.setText(r.getHours().trim());

        tvFoodType.setText(r.getAlias());
        tvPricing.setText(r.getPrice());
        tvAddress.setText(r.getDirections());

        try {
            final File localFile = File.createTempFile("images", "jpg");
            imageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    ivProfilePicture.setImageBitmap(bitmap);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                }
            });
        } catch (IOException e ) {}


        return v;
    }

    public static RestaurantInfoFragment newInstance(String text) {

        RestaurantInfoFragment f = new RestaurantInfoFragment();
//        b.putString("msg", text);
//
//        f.setArguments(b);

        return f;
    }

    public void putArguments(Bundle args){
        r = (Restaurant) args.getSerializable("restaurant");
    }

}
