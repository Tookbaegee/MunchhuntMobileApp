package munchhunt.munchhuntproject.RegisterLogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import munchhunt.munchhuntproject.Map.MapsActivity;
import munchhunt.munchhuntproject.Objects.DietPattern;
import munchhunt.munchhuntproject.Objects.History;
import munchhunt.munchhuntproject.R;

public class RegisterStep2 extends AppCompatActivity{

    String email, name;
//    private CheckBox mPorkBox, mBeefBox, mFishBox, mCrustaceanBox, mShellfishBox, mWhiteMeatBox, mNutsBox, mGlutenBox, mDairyBox, mEggsBox;
    private EditText mNameInput;
    private Button mNextButton;

    Bundle b;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registerlogin_step2);

        mNameInput = (EditText) findViewById(R.id.nameInput);
        mNextButton = (Button) findViewById(R.id.nextButton);

        b = getIntent().getExtras();
        email = b.getString("email");

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mNameInput.getText().toString().matches("")){
                    Toast.makeText(RegisterStep2.this, "Please enter a name.", Toast.LENGTH_LONG);
                }
                else {
                    Intent intent = new Intent(RegisterStep2.this, RegisterStep3.class);
                    name = mNameInput.getText().toString();
                    intent.putExtra("email", email);
                    intent.putExtra("name", name);

                    startActivity(intent);
                    overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                }
            }
        });

    }

//    private void initializeElements(){
//        mPorkBox = (CheckBox) findViewById(R.id.cbPork);
//        mBeefBox = (CheckBox) findViewById(R.id.cbBeef);
//        mFishBox = (CheckBox) findViewById(R.id.cbFish);
//        mCrustaceanBox = (CheckBox) findViewById(R.id.cbCrustacean);
//        mShellfishBox = (CheckBox) findViewById(R.id.cbShellfish);
//        mWhiteMeatBox = (CheckBox) findViewById(R.id.cbWhiteMeat);
//        mNutsBox = (CheckBox) findViewById(R.id.cbNuts);
//        mGlutenBox = (CheckBox) findViewById(R.id.cbGluten);
//        mDairyBox = (CheckBox) findViewById(R.id.cbDairy);
//        mEggsBox = (CheckBox) findViewById(R.id.cbEggs);
//
//
//        nextButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                friendsList = new ArrayList<>();
//                friendsList.add("");
//                historyList = new ArrayList<>();
//                historyList.add(new History("","",""));
//                if(b != null){
//                    username = (String) b.getString("username");
//                    fullname = (String) b.getString("fullname");
//                    password = (String) b.getString("password");
//                }
//
//                id = mUser.getUid();//gets unique id
//                DietPattern diet = new DietPattern(fullname, id, pork, beef, fish, crustacean, shellfish, whiteMeat, nuts, gluten, dairy, eggs);
//                User user = new User(id, fullname, password, username, friendsList, diet, historyList);
//                //  mDatabase.child("Users").child(id).child("diet").setValue(diet);
//                //mDatabase.child("Users").child(id).child("friendsList").setValue(list);
//
//                mDatabase.child("Users").child(id).setValue(user);
//                Toast.makeText(RegisterStep2.this, "Account Created!",
//                        Toast.LENGTH_LONG).show();
//
//                Intent intent = new Intent(RegisterStep2.this, MapsActivity.class);
//                finish();
//                startActivity(intent);
//            }
//
//        });
//
//    }
//
//    public void onCheckboxClicked(View view) {
//
//        boolean checked = ((CheckBox) view).isChecked();
//        switch (view.getId()) {
//            case R.id.cbPork:
//                if(checked) {pork = true;}
//                else{ pork = false; }
//                break;
//            case R.id.cbBeef:
//                if(checked) {beef = true;}
//                else{ beef = false; }
//                break;
//            case R.id.cbFish:
//                if(checked) {fish = true;}
//                else{ fish = false; }
//                break;
//            case R.id.cbCrustacean:
//                if(checked) {crustacean = true;}
//                else{ crustacean = false; }
//                break;
//            case R.id.cbShellfish:
//                if(checked) {shellfish = true;}
//                else{ shellfish = false; }
//                break;
//            case R.id.cbWhiteMeat:
//                if(checked) {whiteMeat = true;}
//                else{ whiteMeat = false; }
//                break;
//            case R.id.cbNuts:
//                if(checked) {nuts = true;}
//                else{ nuts = false; }
//                break;
//            case R.id.cbGluten:
//                if(checked) {gluten = true;}
//                else{ gluten = false; }
//                break;
//            case R.id.cbDairy:
//                if(checked) {dairy = true;}
//                else{ dairy = false; }
//                break;
//            case R.id.cbEggs:
//                if(checked) {eggs = true;}
//                else{ eggs = false; }
//                break;
//        }
//    }

}
