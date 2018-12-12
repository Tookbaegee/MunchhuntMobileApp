package munchhunt.munchhuntproject.RegisterLogin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import munchhunt.munchhuntproject.Map.MapsActivity;
import munchhunt.munchhuntproject.Party.PartyPage;
import munchhunt.munchhuntproject.R;

public class MainActivity extends AppCompatActivity {
    TextView username, password1;
    Button login, register;
    private ViewSwitcher mViewSwitcher;

    private FirebaseDatabase database;
    private FirebaseAuth mAuth;

    BottomNavigationView navBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registerlogin_opening);

        //firebase intlization
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();//creates instance of the database

        //widigets intlization
        username = (TextView)findViewById(R.id.username);
        password1 = (TextView)findViewById(R.id.password);
        login = (Button)findViewById(R.id.login);
        register = (Button)findViewById(R.id.register);

        mViewSwitcher = findViewById(R.id.switcher);
        mViewSwitcher.showNext();





//        checks if user is still logged in
//        if (currentUser != null)
//        {
//            Intent intent = new Intent(MainActivity.this, MapsActivity.class);
//            startActivity(intent);
//
//        }



        //when user clicks login
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString().trim();
                String pass = password1.getText().toString().trim();


            //    r1 = new restaurnat(cheap,highcalorie,healthy);
               // myRef.child("restaurant").setValue(r1);


                if (!user.isEmpty() || !pass.isEmpty()) {//checks if either box is empty
                    mAuth.signInWithEmailAndPassword(user, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Toast toast = Toast.makeText(MainActivity.this, "Logging in", Toast.LENGTH_SHORT);
                                toast.show();
                                FirebaseUser user = mAuth.getCurrentUser();
                                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                                finish();
                                startActivity(intent);

                            } else {
                                // If sign in fails, display a message to the user.

                                Toast.makeText(MainActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }
                else
                {
                    //if fails
                    Toast.makeText(MainActivity.this, "One or more empty field",
                            Toast.LENGTH_SHORT).show();
                }




            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegisterStep1.class);
                startActivity(intent);

            }
        });





    }


}
