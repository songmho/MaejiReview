package underflow.underflow.maejireview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Administrator on 2016-05-16.
 */



public class SignInActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        Button signin_in=(Button)findViewById(R.id.signin_in);
        signin_in.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Toast.makeText(SignInActivity.this, "ㅎㅇ", Toast.LENGTH_SHORT).show();
    }
}
