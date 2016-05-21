package underflow.underflow.maejireview;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

/**
 * Created by Administrator on 2016-05-15.
 */
public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    EditText editID;
    EditText editPassword;
    EditText editPasswordCheck;
    EditText editName;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        editID = (EditText)findViewById(R.id.editID);
        editPassword =(EditText)findViewById(R.id.editPassword);
        editPasswordCheck =(EditText)findViewById(R.id.editPasswordCheck);
        editName =(EditText)findViewById(R.id.editName);


        Button signin_confirm=(Button)findViewById(R.id.signin_confirm);
        signin_confirm.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.signin_confirm:
                ParseUser user=new ParseUser();
                user.setUsername(editID.getText().toString());
                user.setEmail(editID.getText().toString());
                user.put("name",editName.getText().toString());
                if(editPassword.getText().toString().equals(editPasswordCheck.getText().toString()))
                    user.setPassword(editPassword.getText().toString());
                else
                    Toast.makeText(SignUpActivity.this, "비밀번호를 확인하세요.", Toast.LENGTH_SHORT).show();
                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e==null){
                            Toast.makeText(SignUpActivity.this, "회원가입 완료", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplication(),MainActivity.class));
                            SignUpActivity.this.finish();
                        }
                        else{
                            Toast.makeText(SignUpActivity.this, "정보를 확인하세요.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                break;
        }
    }
}
