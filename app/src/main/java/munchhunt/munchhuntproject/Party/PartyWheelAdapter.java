package munchhunt.munchhuntproject.Party;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.List;

import munchhunt.munchhuntproject.R;
import munchhunt.munchhuntproject.Objects.User;

public class PartyWheelAdapter extends munchhunt.munchhuntproject.CursorWheelLayout.CycleWheelAdapter {

    private Context mContext;
    private List<User> userList;
    private LayoutInflater inflater;
    private int gravity;

    public PartyWheelAdapter(Context mContext, List<User> userList){
        this.mContext = mContext;
        this.userList = userList;
        gravity = Gravity.CENTER;
        inflater = LayoutInflater.from(mContext);
    }

    public PartyWheelAdapter(Context mContext, List<User> userList, int gravity){
        this.mContext = mContext;
        this.userList = userList;
        this.gravity = gravity;
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public View getView(View parent, int position) {
        User mUser = getItem(position);
        View root = inflater.inflate(R.layout.party_userbubble_adapter, null, false);
        TextView tvName = root.findViewById(R.id.usersname);

        final de.hdodenhof.circleimageview.CircleImageView profilePic = root.findViewById(R.id.userPic);

        StorageReference userRef = FirebaseStorage.getInstance().getReference().child("User");
        StorageReference imageRef = userRef.child(mUser.getId() + ".jpg");
        try {
            final File localFile = File.createTempFile("images", "jpg");
            imageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    profilePic.setImageBitmap(bitmap);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                }
            });
        } catch (IOException e ) {}

        tvName.setText(mUser.getName());
        if(tvName.getLayoutParams() instanceof FrameLayout.LayoutParams)
            ((FrameLayout.LayoutParams)tvName.getLayoutParams()).gravity = gravity;

        return root;
    }

    @Override
    public User getItem(int position) {
        return userList.get(position);
    }
}
