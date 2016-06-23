package underflow.underflow.maejireview;

/**
 * Created by 수진 on 2016-05-02.
 */
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class ReviewActivity extends AppCompatActivity implements View.OnClickListener {

    ViewPager viewPager;
    TextView reView;
    Button recommend;
    Button un_recommend;
    Toolbar toolbar;
    TextView user;
    LinearLayout container;

    int[] img_arr = new int[5];

    Intent intent;
    ParseQuery<ParseObject> q;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        ButterKnife.bind(this);

        intent = getIntent();

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        reView = (TextView) findViewById(R.id.review);
        recommend = (Button) findViewById(R.id.recommend);
        un_recommend = (Button) findViewById(R.id.un_recommend);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        user = (TextView) findViewById(R.id.user);
        container = (LinearLayout) findViewById(R.id.container);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(intent.getStringExtra("title"));

        user.setText(intent.getStringExtra("user"));

        img_arr[0] = R.drawable.test1;
        img_arr[1] = R.drawable.test2;
        img_arr[2] = R.drawable.test3;
        img_arr[3] = R.drawable.test4;
        img_arr[4] = R.drawable.test5;

        ViewpagerAdapter adapter = new ViewpagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        recommend.setOnClickListener(this);
        un_recommend.setOnClickListener(this);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.recommend:    //추천 눌렀을 경우

                q = ParseQuery.getQuery("Posting");
                q.whereEqualTo("title", getIntent().getStringExtra("title"));
                q.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        putReco_un(objects, true);
                    }
                });


                break;

            case R.id.un_recommend:     //비추천 눌렀을 경우

                q = ParseQuery.getQuery("Posting");
                q.whereEqualTo("title", getIntent().getStringExtra("title"));
                q.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        putReco_un(objects, false);
                    }
                });

                break;
        }
    }

    private void putReco_un(List<ParseObject> objects, boolean isReco) {

        final String state, opp_state;
        final String[] cur_str = new String[2];

        final ParseObject o = objects.get(0);

        if (isReco) {
            state = "reco_user";
            opp_state = "unreco_user";
        } else {
            state = "unreco_user";
            opp_state = "reco_user";
        }

        ParseQuery<ParseObject> q1 = o.getRelation(state).getQuery();
        q1.whereContains("objectId", ParseUser.getCurrentUser().getObjectId());
        q1.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (objects.isEmpty()) {              //추천을 하는 경우
                    o.getRelation(state).add(ParseUser.getCurrentUser());       //추천
                    o.saveInBackground();

                    ParseQuery<ParseObject> q2 = o.getRelation(opp_state).getQuery();             //비추천이 되어 있으면 취소 시킴
                    q2.whereContains("objectId", ParseUser.getCurrentUser().getObjectId());
                    q2.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> objects, ParseException e) {
                            if (!objects.isEmpty()) {
                                o.getRelation(opp_state).remove(ParseUser.getCurrentUser());        //비추천에 있는 이름 삭제
                                o.saveInBackground();
                            }
                        }
                    });

                    if (state.equals("reco_user"))
                        cur_str[0] = getString(R.string.string_recommend);
                    else
                        cur_str[0] = getString(R.string.string_unrecommend);

                    Snackbar.make(container, cur_str[0], Snackbar.LENGTH_LONG)     //스낵바 생성
                    .setAction(getString(R.string.string_undo), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {       // 취소를 눌렀을 때
                            q = ParseQuery.getQuery("Posting");
                            q.whereEqualTo("title", getIntent().getStringExtra("title"));
                            q.findInBackground(new FindCallback<ParseObject>() {
                                @Override
                                public void done(List<ParseObject> objects, ParseException e) {
                                    ParseObject o = objects.get(0);
                                    o.getRelation(state).remove(ParseUser.getCurrentUser());
                                    o.saveInBackground();
                                }
                            });

                            ParseQuery<ParseObject> q2 = o.getRelation(opp_state).getQuery();             //비추천이 되어 있었는데 취소 되었을 경우 다시 비추천 시킴
                            q2.whereContains("objectId", ParseUser.getCurrentUser().getObjectId());
                            q2.findInBackground(new FindCallback<ParseObject>() {
                                @Override
                                public void done(List<ParseObject> objects, ParseException e) {
                                    if (!objects.isEmpty()) {
                                        o.getRelation(opp_state).add(ParseUser.getCurrentUser());        //비추천에 이름 추가
                                        o.saveInBackground();
                                    }
                                }
                            });     //end findBackground
                        }   //end onClick
                    })  //end setAction
                            .show(); // Don’t forget to show!
                } else {               //추천, 비추천을 했던 경우
                    o.getRelation(state).remove(ParseUser.getCurrentUser());        //있던 것 삭제
                    o.saveInBackground();

                    if (state.equals("reco_user"))
                        cur_str[1] = getString(R.string.string_recommend_undo);
                    else
                        cur_str[1] = getString(R.string.string_unrecommend_undo);

                    Snackbar.make(container, cur_str[1], Snackbar.LENGTH_LONG)        //스낵바 생성
                            .setAction(getString(R.string.string_undo), new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {       // 취소를 눌렀을 때
                                    q = ParseQuery.getQuery("Posting");
                                    q.whereEqualTo("title", getIntent().getStringExtra("title"));
                                    q.findInBackground(new FindCallback<ParseObject>() {
                                        @Override
                                        public void done(List<ParseObject> objects, ParseException e) {
                                            ParseObject o = objects.get(0);
                                            o.getRelation(state).add(ParseUser.getCurrentUser());
                                            o.saveInBackground();
                                        }
                                    });
                                }
                            })
                            .show(); // Don’t forget to show!
                }
            }
        });
    }

    private class ViewpagerAdapter extends FragmentPagerAdapter {
        public ViewpagerAdapter(FragmentManager supportFragmentManager) {
            super(supportFragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            Img_Fragment imgFragment = new Img_Fragment();
            Bundle bundle = new Bundle();
            bundle.putString("activity", "review");
            bundle.putInt("img", img_arr[position]);
            imgFragment.setArguments(bundle);
            return imgFragment;
        }

        @Override
        public int getCount() {
            return 5;
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
