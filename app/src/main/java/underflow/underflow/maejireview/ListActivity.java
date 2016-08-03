package underflow.underflow.maejireview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 수진 on 2016-05-14.
 */

public class ListActivity extends AppCompatActivity {

    Toolbar toolbar;
    String category;
    RecyclerView recyclerView;
    List<Review_item> items=new ArrayList<>();
    @Override
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Intent intent = getIntent();
        category = intent.getStringExtra("category");

        toolbar = (Toolbar)findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(category.equals("home"))
            getSupportActionBar().setTitle(R.string.drawer_home);
        else
            getSupportActionBar().setTitle(R.string.drawer_rice);

        recyclerView=(RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);


        //image, title, user 순서

        makeList();

    }

    private void makeList() {

        Review_item[] item=new Review_item[5];

        ParseQuery<ParseObject> q = ParseQuery.getQuery("Posting");
        q.whereContains("category",category);
        q.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(objects != null)
                for(ParseObject o : objects){
                    Review_item i = new Review_item(R.drawable.test1,o.getString("title"),o.getString("contents"));
                    items.add(i);
                }
                recyclerView.setAdapter(new Recycler_Adapter(getApplicationContext(), items, R.layout.activity_list));
            }
        });

    }


}