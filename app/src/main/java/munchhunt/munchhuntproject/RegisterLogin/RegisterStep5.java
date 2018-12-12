package munchhunt.munchhuntproject.RegisterLogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import munchhunt.munchhuntproject.Map.MapsActivity;
import munchhunt.munchhuntproject.Objects.DietPattern;
import munchhunt.munchhuntproject.Objects.History;
import munchhunt.munchhuntproject.Objects.Party;
import munchhunt.munchhuntproject.Objects.Restaurant;
import munchhunt.munchhuntproject.Objects.User;
import munchhunt.munchhuntproject.R;

public class RegisterStep5 extends AppCompatActivity implements Serializable {

    private String email, name, handle, password;
    private CheckBox mPorkBox, mBeefBox, mFishBox, mCrustaceanBox,
            mShellfishBox, mWhiteMeatBox, mNutsBox, mGlutenBox, mDairyBox, mEggsBox, mSoyBox;
    private Button mNextButton;
    private boolean pork, beef, fish, crustacean, shellfish, whiteMeat, nuts, gluten, dairy, eggs, soy;


    Bundle b;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registerlogin_step5);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        b = getIntent().getExtras();

        email = b.getString("email");
        name = b.getString("name");
        handle = b.getString("handle");
        password = b.getString("password");

        initializeElements();

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("email", email);
                Log.d("password", password);
                DietPattern diet = new DietPattern(name, handle, pork, beef, fish, crustacean, shellfish, whiteMeat, nuts, gluten, dairy, eggs);

                Intent intent = new Intent(RegisterStep5.this, RegisterStep6.class);
                intent.putExtra("email", email);
                intent.putExtra("name", name);
                intent.putExtra("handle", handle);
                intent.putExtra("password", password);
                intent.putExtra("diet", diet);


                startActivity(intent);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);

//                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegisterStep5.this,
//                        new OnCompleteListener<AuthResult>() {
//                            @Override
//                            public void onComplete(@NonNull Task<AuthResult> task) {
//                                if(task.isSuccessful()){
//                                    Intent intent = new Intent(RegisterStep5.this, MapsActivity.class);
//                                    List<String> friendsList = new ArrayList<>();
//                                    friendsList.add("");
//                                    ArrayList<History> history = new ArrayList<History>();
//                                    History historyobj = new History();
//                                    history.add(historyobj);

//                                    User user = new User(handle, FirebaseAuth.getInstance().getCurrentUser().getUid(), name, password, email, friendsList, diet, history);
//                                    mDatabase.child("Users").child(handle).setValue(user);
//                                    Toast.makeText(RegisterStep5.this, "Account Created!",
//                                            Toast.LENGTH_LONG).show();
//
//                                    startActivity(intent);
//
//                                }
//                                else{
//                                    FirebaseAuthException e = (FirebaseAuthException) task.getException();
//                                    Toast.makeText(RegisterStep5.this, "Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//                                    return;
//
//                                }
//                            }
//                        });
            }

        });


    }

    private void initializeElements(){
        mPorkBox = (CheckBox) findViewById(R.id.cbPork);
        mBeefBox = (CheckBox) findViewById(R.id.cbBeef);
        mFishBox = (CheckBox) findViewById(R.id.cbFish);
        mCrustaceanBox = (CheckBox) findViewById(R.id.cbCrustacean);
        mShellfishBox = (CheckBox) findViewById(R.id.cbShellfish);
        mWhiteMeatBox = (CheckBox) findViewById(R.id.cbWhiteMeat);
        mNutsBox = (CheckBox) findViewById(R.id.cbNuts);
        mGlutenBox = (CheckBox) findViewById(R.id.cbGluten);
        mDairyBox = (CheckBox) findViewById(R.id.cbDairy);
        mEggsBox = (CheckBox) findViewById(R.id.cbEggs);
        mSoyBox = (CheckBox) findViewById(R.id.cbSoy);

        mNextButton = (Button) findViewById(R.id.nextButton);


    }


    public void onCheckboxClicked(View view) {

        boolean checked = ((CheckBox) view).isChecked();
        switch (view.getId()) {
            case R.id.cbPork:
                if(checked) {pork = true;}
                else{ pork = false; }
                break;
            case R.id.cbBeef:
                if(checked) {beef = true;}
                else{ beef = false; }
                break;
            case R.id.cbFish:
                if(checked) {fish = true;}
                else{ fish = false; }
                break;
            case R.id.cbCrustacean:
                if(checked) {crustacean = true;}
                else{ crustacean = false; }
                break;
            case R.id.cbShellfish:
                if(checked) {shellfish = true;}
                else{ shellfish = false; }
                break;
            case R.id.cbWhiteMeat:
                if(checked) {whiteMeat = true;}
                else{ whiteMeat = false; }
                break;
            case R.id.cbNuts:
                if(checked) {nuts = true;}
                else{ nuts = false; }
                break;
            case R.id.cbGluten:
                if(checked) {gluten = true;}
                else{ gluten = false; }
                break;
            case R.id.cbDairy:
                if(checked) {dairy = true;}
                else{ dairy = false; }
                break;
            case R.id.cbEggs:
                if(checked) {eggs = true;}
                else{ eggs = false; }
                break;
            case R.id.cbSoy:
                if(checked) {soy = true;}
                else{ soy = false; }
                break;
        }
    }


}
