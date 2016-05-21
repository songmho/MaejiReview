package underflow.underflow.maejireview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 수진 on 2016-05-14.
 */

public class ListActivity extends AppCompatActivity {

    @Override
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView=(RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        List<Review_item> items=new ArrayList<>();
        Review_item[] item=new Review_item[5];
        Intent intent = getIntent();
        String category = intent.getStringExtra("category");

        //image, title, user 순서

        if(category.equals("rice")) {
            item[0] = new Review_item(R.drawable.test5, "rice1", "user1");
            item[1] = new Review_item(R.drawable.test1, "rice2", "user2");
            item[2] = new Review_item(R.drawable.test2, "rice3", "user3");
            item[3] = new Review_item(R.drawable.test3, "rice4", "user4");
            item[4] = new Review_item(R.drawable.test4, "rice5", "user5");
        }

        else if(category.equals("home")) {
            item[0] = new Review_item(R.drawable.test5, "home1", "user1");
            item[1] = new Review_item(R.drawable.test1, "home2", "user2");
            item[2] = new Review_item(R.drawable.test2, "home3", "user3");
            item[3] = new Review_item(R.drawable.test3, "home4", "user4");
            item[4] = new Review_item(R.drawable.test4, "home5", "user5");

        }
        else{}

        for (int i = 0; i < 5; i++) items.add(item[i]);
        recyclerView.setAdapter(new Recycler_Adapter(getApplicationContext(), items, R.layout.item_review));
    }


}