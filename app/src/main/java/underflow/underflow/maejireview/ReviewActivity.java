package underflow.underflow.maejireview;

/**
 * Created by 수진 on 2016-05-02.
 */
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView;
import android.app.Activity;

public class ReviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        ImageView image = new ImageView(this);
        image.setImageResource(R.drawable.logo_p);
        TextView text = new TextView(this);
    }



}
