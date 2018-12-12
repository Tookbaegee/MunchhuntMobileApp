package munchhunt.munchhuntproject.RegisterLogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import munchhunt.munchhuntproject.Objects.User;
import munchhunt.munchhuntproject.R;
import munchhunt.munchhuntproject.Objects.History;

public class RegisterStep4 extends AppCompatActivity {

    String email, name, handle, password;
    List<String> friendsList;
    DietPattern diet;

    private EditText mPasswordInput, mVerifyPassword;
    private Button mNextButton;

    Bundle b;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registerlogin_step4);

        mPasswordInput = (EditText) findViewById(R.id.passwordInput);
        mVerifyPassword = (EditText) findViewById(R.id.verifyPasswordInput);
        mNextButton = (Button) findViewById(R.id.nextButton);

        b = getIntent().getExtras();

        email = b.getString("email");
        name = b.getString("name");
        handle = b.getString("handle");

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mPasswordInput.getText().toString().matches("")){
                    Toast.makeText(RegisterStep4.this, "Please enter a password.", Toast.LENGTH_LONG);
                }
                if(mVerifyPassword.getText().toString().matches("")){
                    Toast.makeText(RegisterStep4.this, "Please verify your password.", Toast.LENGTH_SHORT).show();
                }
                if(mPasswordInput.getText().toString().equals(mVerifyPassword.getText().toString()) == false){
                    Toast.makeText(RegisterStep4.this, "The passwords do not match.", Toast.LENGTH_SHORT).show();
                }
                else{
                    password = mPasswordInput.getText().toString();
                    Intent intent = new Intent(RegisterStep4.this, RegisterStep5.class);
                    intent.putExtra("email", email);
                    intent.putExtra("name", name);
                    intent.putExtra("handle", handle);
                    intent.putExtra("password", password);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fadein, R.anim.fadeout);

                }
            }
        });



    }
}
