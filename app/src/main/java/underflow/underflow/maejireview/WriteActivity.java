package underflow.underflow.maejireview;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.parse.ParseObject;
import com.parse.ParseUser;

import butterknife.ButterKnife;

/**
 * Created by songmho on 2016-05-09.
 */
public class WriteActivity extends AppCompatActivity {

    Toolbar toolbar;
    ViewPager viewPager;
    EditText edit_title;
    EditText edit_content;
    RadioGroup radiogroup;
    RadioButton radio_rice, radio_home;

    int[] img_arr= new int[5];

    String category;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        ButterKnife.bind(this);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        viewPager = (ViewPager)findViewById(R.id.viewPager);
        edit_title = (EditText)findViewById(R.id.edit_title);
        edit_content = (EditText)findViewById(R.id.edit_content);
        radiogroup = (RadioGroup)findViewById(R.id.radiogroup);
        radio_home = (RadioButton)findViewById(R.id.radio_home);
        radio_rice = (RadioButton)findViewById(R.id.radio_rice);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("리뷰작성");


        img_arr[0]=R.drawable.test1;
        img_arr[1]=R.drawable.test2;
        img_arr[2]=R.drawable.test3;
        img_arr[3]=R.drawable.test4;
        img_arr[4]=R.drawable.test5;



        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog();
            }
        });

        ViewpagerAdapter adapter = new ViewpagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_rice:
                        category="rice";
                        break;
                    case R.id.radio_home:
                        category="home";
                        break;
                }
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_write,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.item_completed){
            edit_title.getText().toString();
            edit_content.getText().toString();
            ParseObject o = new ParseObject("Posting");
            o.put("category",category);
            o.put("title",edit_title.getText().toString());
            o.put("contents",edit_content.getText().toString());
            o.put("posting_user", ParseUser.getCurrentUser());
            o.saveInBackground();
            Toast.makeText(WriteActivity.this, "글쓰기 완료되었다!", Toast.LENGTH_SHORT).show();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_BACK:
                getDialog();
                break;
        }

        return super.onKeyDown(keyCode, event);
    }

    void getDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.alert_head));
        builder.setMessage(getString(R.string.alert_body));
        builder.setPositiveButton(getString(R.string.alert_positive), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton(getString(R.string.alert_negative), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    private class ViewpagerAdapter extends FragmentPagerAdapter {
        public ViewpagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
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
