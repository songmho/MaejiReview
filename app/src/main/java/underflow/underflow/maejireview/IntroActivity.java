package underflow.underflow.maejireview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Administrator on 2016-05-02.
 */


public class IntroActivity extends Activity {
    int delay_time = 2000;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiy_intro);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
              startActivity(new Intent(IntroActivity.this,MainActivity.class));
            finish();
            }
        },delay_time);
    }


}
