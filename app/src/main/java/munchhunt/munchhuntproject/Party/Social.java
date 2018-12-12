package munchhunt.munchhuntproject.Party;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import munchhunt.munchhuntproject.Objects.User;

public class Social extends AppCompatActivity {
    Button done;
    private String currentUserID;
    private User currentUser;
    private DatabaseReference myRef;
    private FirebaseDatabase database;
    private FirebaseUser mUser;
    private Button add;
    private EditText searchFriend;
    private TextView partyView, friendlistView;



    private String userToBeCalledID;
    private User userToBeCalled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        callCurrentUser();

//        done = (Button)findViewById(R.id.return_social);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    public void callCurrentUser(){


        final FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull final FirebaseAuth firebaseAuth) {
                final FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    currentUserID = user.getUid();

                    //4  Toast.makeText(Profile.this, "USER ID\n"+currentUser,Toast.LENGTH_SHORT).show();

                } else {
                    //  Toast.makeText(Profile.this, "USER ID\n"+ currentUser,Toast.LENGTH_SHORT).show();
                }
            }
        });

        myRef = database.getInstance().getReference().child("Users");

        ValueEventListener valueEventListener = myRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {


                    User user = ds.getValue(User.class);

                    if(currentUserID.equals(user.getId())){
                        currentUser = user;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


//    public void button_AddtoParty(View view) {
//
//        SocialFirebase.getUserParty(currentUser.getId(), new PartyCallback<Party>() {
//            @Override
//            public void callback(Party party) {
//                //do whatever with that party. (either show it on the GUI or nest it with another callback
//                //to do something like the next picture
//            }
//        });
//
//
//    }
}
