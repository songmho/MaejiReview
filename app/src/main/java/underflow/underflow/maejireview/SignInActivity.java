package underflow.underflow.maejireview;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

/**
 * Created by Administrator on 2016-05-16.
 */



public class SignInActivity extends AppCompatActivity implements View.OnClickListener {
    EditText signin_email;
    EditText signin_password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        signin_email = (EditText)findViewById(R.id.signin_email);
        signin_password = (EditText)findViewById(R.id.signin_password);

        Button signin_in=(Button)findViewById(R.id.signin_in);
        signin_in.setOnClickListener(this);

        Button signin_signup=(Button)findViewById(R.id.signin_signup);
        signin_signup.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.signin_in:
                Log.d("dfdfdf",signin_email.getText().toString());
                ParseUser.logInInBackground(signin_email.getText().toString(), signin_password.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if(e!=null) {
                            Toast.makeText(SignInActivity.this, "정보를 확인해 주세요.   ", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            startActivity(new Intent(getApplication(),MainActivity.class));
                            SignInActivity.this.finish();
                        }
                    }
                });
                break;
            case R.id.signin_signup:
                startActivity(new Intent(getApplication(),SignUpActivity.class));
                break;


        }
    }
}
