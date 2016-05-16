package underflow.underflow.maejireview;

/**
 * Created by 수진 on 2016-05-02.
 */
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.ButterKnife;

public class ReviewActivity extends AppCompatActivity implements View.OnClickListener {

    ViewPager viewPager;
    TextView reView;
    Button recommend;
    Button un_recommend;
    Toolbar toolbar;
    TextView user;
    LinearLayout container;

    int[] img_arr= new int[5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        ButterKnife.bind(this);

        Intent intent=getIntent();

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        reView = (TextView)findViewById(R.id.review);
        recommend = (Button) findViewById(R.id.recommend);
        un_recommend = (Button)findViewById(R.id.un_recommend);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        user = (TextView)findViewById(R.id.user);
        container = (LinearLayout)findViewById(R.id.container);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(intent.getStringExtra("title"));

        user.setText(intent.getStringExtra("user"));

        img_arr[0]=R.drawable.test1;
        img_arr[1]=R.drawable.test2;
        img_arr[2]=R.drawable.test3;
        img_arr[3]=R.drawable.test4;
        img_arr[4]=R.drawable.test5;

        ViewpagerAdapter adapter = new ViewpagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        recommend.setOnClickListener(this);
        un_recommend.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.recommend:
                Snackbar.make(container, getString(R.string.string_recommend), Snackbar.LENGTH_LONG)
                    .setAction(getString(R.string.string_undo), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    })
                    .show(); // Don’t forget to show!
                break;

            case R.id.un_recommend:
                Snackbar.make(container, getString(R.string.string_unrecommend), Snackbar.LENGTH_LONG)
                        .setAction(getString(R.string.string_undo), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        })
                        .show(); // Don’t forget to show!
                break;
        }
    }


    private class ViewpagerAdapter extends FragmentPagerAdapter {
        public ViewpagerAdapter(FragmentManager supportFragmentManager) {
            super(supportFragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            Img_Fragment imgFragment = new Img_Fragment();
            Bundle bundle = new Bundle();
            bundle.putString("activity","review");
            bundle.putInt("img",img_arr[position]);
            imgFragment.setArguments(bundle);
            return imgFragment;
        }

        @Override
        public int getCount() {
            return 5;
        }
    }
}
