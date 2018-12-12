package munchhunt.munchhuntproject.RegisterLogin;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import munchhunt.munchhuntproject.Callback.BooleanCallback;
import munchhunt.munchhuntproject.Party.SocialFirebase;
import munchhunt.munchhuntproject.R;

public class RegisterStep1 extends AppCompatActivity {

    private String email;

    private EditText mEmailInput;
    private Button mNextButton;

    Bundle b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registerlogin_step1);

        mEmailInput = (EditText) findViewById(R.id.emailInput);
        mNextButton = (Button) findViewById(R.id.nextButton);

        b = new Bundle();

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkValidEmail(mEmailInput.getText().toString()) == false){
                    Toast.makeText(RegisterStep1.this, "Please enter a valid email address.", Toast.LENGTH_SHORT).show();
                }
                else if(mEmailInput.getText().toString().matches("")){
                    Toast.makeText(RegisterStep1.this, "Please enter an email address.", Toast.LENGTH_SHORT).show();
                }
                else{
                    SocialFirebase.isEmailDuplicate(mEmailInput.getText().toString(), new BooleanCallback() {
                        @Override
                        public void callback(boolean data) {
                            if(data){
                                Toast.makeText(RegisterStep1.this, "Email is already registered. Enter a different email.", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Intent intent = new Intent(RegisterStep1.this, RegisterStep2.class);
                                email = mEmailInput.getText().toString();
                                intent.putExtra("email", email);

                                startActivity(intent);
                                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                            }
                        }
                    });



//                    Handler h = new Handler();
//                    final Runnable r = new Runnable() {
//                        @Override
//                        public void run() {
//                            Intent registerIntent = new Intent(RegisterStep1.this, RegisterStep2.class);
//                            registerIntent.putExtra("id", "1");
//                            startActivity(registerIntent);
//                            RegisterStep1.this.finish();
//
//                        }
//                    };
                }
            }
        });


        // animation
//        mViewSwitcher = (ViewSwitcher) findViewById(R.id.switcher);
//        mViewSwitcher.showNext();



//        nextButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if(fullName.getText().toString().matches("") || email.getText().toString().matches(""))
//                {
//                    Toast.makeText(RegisterStep1.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                if(checkValidEmail(email.getText().toString()) == false)
//                {
//                    Toast.makeText(RegisterStep1.this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                if (!password.getText().toString().equals(password2.getText().toString()))
//                {
//                    Toast.makeText(RegisterStep1.this, "Passwords don't match", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                else {
//                    mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(RegisterStep1.this, new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            if (task.isSuccessful()) {
//                                friendsList = new ArrayList<String>();
//                                friendsList.add("");
//
//                                // Sign in success, update UI with the signed-in user's information
//                                Toast.makeText(RegisterStep1.this, "Account Created",
//                                        Toast.LENGTH_LONG).show();
//                                Intent intent = new Intent(RegisterStep1.this, RegisterStep2.class);
//                                intent.putExtra("username", email.getText().toString());
//                                intent.putExtra("fullname", fullName.getText().toString());
//                                intent.putExtra("password", password.getText().toString());
//                                mUser = FirebaseAuth.getInstance().getCurrentUser();
//                                id = mUser.getUid();//gets unique id
//                                // History history = new History()
//                                ArrayList<History> history = new ArrayList<History>();
//                                History historyobj = new History();
//                                history.add(historyobj);
//
//
//                                User user = new User(id, fullName.getText().toString(), password.getText().toString(), email.getText().toString(), friendsList, diet, history);
//
//                                mDatabase.child("Users").child(id).setValue(user);
//                                startActivity(intent);
//
//
//                            } else {
//                                // If sign in fails, display a message to the user.
//
//                                Toast.makeText(RegisterStep1.this, "Provided email has been registered already.",
//                                        Toast.LENGTH_LONG).show();
//
//                            }
//
//                            // ...
//                        }
//                    });
//
//                }
//            }
//        });


    }

    public static boolean checkValidEmail(String email){
        String symbols = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(symbols, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}