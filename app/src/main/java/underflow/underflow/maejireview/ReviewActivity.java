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

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.viewPager) ViewPager viewPager;
    @BindView(R.id.review) TextView reView;
    @BindView(R.id.recommend) Button recommend;
    @BindView(R.id.un_recommend) Button un_recommend;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.user) TextView user;
    @BindView(R.id.container) LinearLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        ButterKnife.bind(this);

        Intent intent=getIntent();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(intent.getStringExtra("title"));

        user.setText(intent.getStringExtra("user"));

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
            return new Img_Fragment();
        }

        @Override
        public int getCount() {
            return 5;
        }
    }
}
