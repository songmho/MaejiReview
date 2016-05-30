package underflow.underflow.maejireview;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import org.json.JSONArray;

import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by songmho on 2016-05-30.
 */
public class MypageActivity extends AppCompatActivity {
    String profileUrl = null;

    Toolbar toolbar;
    AppBarLayout appBarLayout;

    ParseUser parseUser;
    ImageView profile_blur;
    ImageView profile;

    CollapsingToolbarLayout collapsing_toolbar;
    TextView title;
    TextView name;
    ImageView imageView;

    TextView write;
    TextView recommend;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);
        parseUser=ParseUser.getCurrentUser();
        toolbar = (Toolbar)findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        collapsing_toolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        title = (TextView) findViewById(R.id.info);
        imageView = (ImageView) findViewById(R.id.image);
        profile_blur = (ImageView) findViewById(R.id.profile_blur);
        profile = (ImageView) findViewById(R.id.profile);
        name = (TextView)findViewById(R.id.name);
        appBarLayout=(AppBarLayout)findViewById(R.id.app_bar);
        write = (TextView)findViewById(R.id.write);
        recommend = (TextView)findViewById(R.id.recommend);
        Glide.with(getApplicationContext()).load(profileUrl).diskCacheStrategy(DiskCacheStrategy.ALL).bitmapTransform(new CropCircleTransformation(getApplicationContext())).placeholder(R.drawable.profile).into(profile);

        initAppBarLayout();
        loadProfile();

        ParseRelation<ParseObject> r=ParseUser.getCurrentUser().getRelation("recommend");
        ParseQuery<ParseObject> q=r.getQuery();
        q.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                Toast.makeText(MypageActivity.this, ""+objects.size(), Toast.LENGTH_SHORT).show();
                recommend.setText(String.valueOf(objects.size()));
            }
        });
        ParseRelation<ParseObject> r1=ParseUser.getCurrentUser().getRelation("write");
        ParseQuery<ParseObject> q1=r1.getQuery();
        q1.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                Toast.makeText(MypageActivity.this, ""+objects.size(), Toast.LENGTH_SHORT).show();
                write.setText(String.valueOf(objects.size()));
            }
        });
    }

    private void initAppBarLayout() {
        collapsing_toolbar.setTitle("");
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                Log.e("d", "verticallOffset : " + verticalOffset + " , scrollRange + verticalOffset : " + (scrollRange + verticalOffset));
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsing_toolbar.setTitle("마이페이지");
                    profile.setVisibility(View.GONE);
                    isShow = true;
                } else if (isShow) {
                    collapsing_toolbar.setTitle("");
                    profile.setVisibility(View.VISIBLE);
                    isShow = false;
                }
            }
        });
    }

    private void loadProfile() {
        if (parseUser != null) {
            ParseFile parseFile = parseUser.getParseFile("profile");
            if (parseFile != null) {
                profileUrl = parseFile.getUrl();
                Log.e("dfdfdf", "parse file url : " + profileUrl);
                if (!isFinishing()) {
                    Glide.with(getApplicationContext()).load(profileUrl).diskCacheStrategy(DiskCacheStrategy.ALL).bitmapTransform(new CropCircleTransformation(getApplicationContext())).placeholder(R.drawable.profile).into(profile);
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadProfile();
        if (parseUser != null) {
            parseUser = ParseUser.getCurrentUser();
            name.setText(parseUser.getString("name"));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return super.onCreateOptionsMenu(menu);
    }  @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_edit) {
            Intent intent = new Intent(MypageActivity.this, MyPageEditActivity.class);
            intent.putExtra("name", name.getText().toString());
            intent.putExtra("profileUrl", profileUrl);

            startActivity(intent);
            return true;
        }
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
