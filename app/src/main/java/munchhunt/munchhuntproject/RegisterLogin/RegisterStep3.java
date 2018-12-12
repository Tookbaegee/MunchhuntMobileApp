package munchhunt.munchhuntproject.RegisterLogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import munchhunt.munchhuntproject.Callback.BooleanCallback;
import munchhunt.munchhuntproject.Party.SocialFirebase;
import munchhunt.munchhuntproject.R;

public class RegisterStep3 extends AppCompatActivity {

    String email, name, handle;
    private EditText mHandleInput;
    private Button mNextButton;

    Bundle b;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registerlogin_step3);

        mHandleInput = (EditText) findViewById(R.id.handleInput);
        mNextButton = (Button) findViewById(R.id.nextButton);

        b = getIntent().getExtras();

        email = b.getString("email");
        name = b.getString("name");

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mHandleInput.getText().toString().matches("")){
                    Toast.makeText(RegisterStep3.this, "Please enter a handle.", Toast.LENGTH_LONG).show();
                }
                else{
                    SocialFirebase.isHandleDuplicate(mHandleInput.getText().toString(), new BooleanCallback() {
                        @Override
                        public void callback(boolean data) {
                            if(data){
                                Toast.makeText(RegisterStep3.this, "Handle is taken. Please enter a different handle.", Toast.LENGTH_LONG).show();
                            }
                            else{
                                Intent intent = new Intent(RegisterStep3.this, RegisterStep4.class);
                                handle = mHandleInput.getText().toString();
                                intent.putExtra("email", email);
                                intent.putExtra("name", name);
                                intent.putExtra("handle", handle);
                                startActivity(intent);
                                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                            }
                        }
                    });
                }
            }
        });



    }
}
