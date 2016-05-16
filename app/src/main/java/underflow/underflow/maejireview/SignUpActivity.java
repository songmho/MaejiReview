package underflow.underflow.maejireview;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Administrator on 2016-05-15.
 */
public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Button id_check=(Button)findViewById(R.id.id_check);
        id_check.setOnClickListener(this);

        Button password_check=(Button)findViewById(R.id.password_check);
        password_check.setOnClickListener(this);

        Button signin_confirm=(Button)findViewById(R.id.signin_confirm);
        signin_confirm.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.id_check:
                Toast.makeText(SignUpActivity.this, "이메일 중복확인", Toast.LENGTH_SHORT).show();
                break;

            case R.id.password_check:
                Toast.makeText(SignUpActivity.this, "닉네임 중복확인", Toast.LENGTH_SHORT).show();
                break;
            case R.id.signin_confirm:
                Toast.makeText(SignUpActivity.this, "회원가입 완료", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplication(),MainActivity.class));
                SignUpActivity.this.finish();
                break;
        }
    }
}
