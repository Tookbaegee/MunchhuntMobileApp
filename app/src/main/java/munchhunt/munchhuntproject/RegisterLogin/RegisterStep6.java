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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import munchhunt.munchhuntproject.Map.MapsActivity;
import munchhunt.munchhuntproject.Objects.DietPattern;
import munchhunt.munchhuntproject.Objects.History;
import munchhunt.munchhuntproject.Objects.User;
import munchhunt.munchhuntproject.R;

public class RegisterStep6 extends AppCompatActivity {

    // Retrieved from bundle
    private String email, name, handle, password;
    private DietPattern diet;

    // Preference boolean values
    private boolean american, chinese, italian, mexican, middleEastern, sandwiches, seafood;
    private CheckBox mAmericanBox, mChineseBox, mItalianBox, mMexicanBox, mMiddleEasternBox, mSandwichBox, mSeafoodBox;

    private Button mFinishButton;

    Bundle b;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registerlogin_step6);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        b = getIntent().getExtras();

        email = b.getString("email");
        name = b.getString("name");
        handle = b.getString("handle");
        password = b.getString("password");
        diet = (DietPattern) b.getSerializable("diet");
        Log.d("pork", "" + diet.getPork());

        initializeElements();

        mFinishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegisterStep6.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent intent = new Intent(RegisterStep6.this, MapsActivity.class);
                            List<String> friendsList = new ArrayList<>();
                            friendsList.add("");
                            ArrayList<History> history = new ArrayList<History>();
                            History historyobj = new History();
                            history.add(historyobj);
                            User user = new User(handle, FirebaseAuth.getInstance().getCurrentUser().getUid(), name, password, email, friendsList, diet, history);
                            mDatabase.child("Users").child(handle).setValue(user);
                            Toast.makeText(RegisterStep6.this, "Account Created!",
                                    Toast.LENGTH_LONG).show();

                            startActivity(intent);
                        }
                        else{
                            FirebaseAuthException e = (FirebaseAuthException) task.getException();
                            Toast.makeText(RegisterStep6.this, "Failed "+ e.getMessage(), Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                });
            }
        });

    }

    private void initializeElements(){
        mAmericanBox = (CheckBox) findViewById(R.id.cbAmerican);
        mChineseBox = (CheckBox) findViewById(R.id.cbChinese);
        mItalianBox = (CheckBox) findViewById(R.id.cbItalian);
        mMexicanBox = (CheckBox) findViewById(R.id.cbMexican);
        mMiddleEasternBox = (CheckBox) findViewById(R.id.cbMiddleEastern);
        mSandwichBox = (CheckBox) findViewById(R.id.cbSandwiches);
        mSeafoodBox = (CheckBox) findViewById(R.id.cbSeafood);

        mFinishButton = (Button) findViewById(R.id.finishButton);
    }

    public void onCheckboxClicked(View view){
        boolean checked = ((CheckBox) view).isChecked();
        switch (view.getId()) {
            case R.id.cbAmerican:
                if(checked) {american = true;}
                else{ american = false; }
                break;
            case R.id.cbChinese:
                if(checked) {chinese = true;}
                else{ chinese = false; }
                break;
            case R.id.cbMexican:
                if(checked) {mexican = true;}
                else{ mexican = false; }
                break;
            case R.id.cbMiddleEastern:
                if(checked) {middleEastern = true;}
                else{ middleEastern = false; }
                break;
            case R.id.cbSandwiches:
                if(checked) { sandwiches = true;}
                else{ sandwiches = false; }
                break;
            case R.id.cbSeafood:
                if(checked) {seafood = true;}
                else{ seafood = false; }
                break;
            case R.id.cbItalian:
                if(checked) {italian = true;}
                else{ italian = false; }
                break;
        }
    }
}
