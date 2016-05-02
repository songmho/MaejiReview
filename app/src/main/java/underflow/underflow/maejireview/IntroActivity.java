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
        Handler hd = new Handler();
        hd.postDelayed(new splashhandler(), delay_time);
    }

    private class splashhandler implements Runnable {
        public void run() {
            startActivity(new Intent(getApplication(),MainActivity.class));
            IntroActivity.this.finish();
        }
    }

}
