package underflow.underflow.maejireview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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
        item[0]=new Review_item(R.drawable.test,"#1","1");
        item[1]=new Review_item(R.drawable.test1,"#2","2");
        item[2]=new Review_item(R.drawable.test2,"#3","3");
        item[3]=new Review_item(R.drawable.test3,"#4","4");
        item[4]=new Review_item(R.drawable.test4,"#5","5");

        for(int i=0;i<5;i++) items.add(item[i]);

        recyclerView.setAdapter(new Recycler_Adapter(getApplicationContext(), items, R.layout.activity_main));
    }


}