package underflow.underflow.maejireview;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {          //MainActivity

    @Bind(R.id.fab) FloatingActionButton fab;
    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.drawerLayout) DrawerLayout drawerlayout;
    @Bind(R.id.navigationView) NavigationView navigationView;
    @Bind(R.id.recyclerView) RecyclerView recyclerView;

    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);                 //ButterKnife 사용

        setSupportActionBar(toolbar);            //toolbar를 actionbar로 설정
        if (getSupportActionBar() != null)      //Actionbar가 있을 경우
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(R.drawable.drawericon);             //toolbar에 icon 등록
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {                 //toolbar Icon 클릭 시
                drawerlayout.openDrawer(GravityCompat.START);         //drawer 열림
            }
        });

        ArrayList<ListItem> items=new ArrayList<>();                //List Item 생성
        for(int i=0;i<5;i++){
            ListItem item = new ListItem("# "+i);
            items.add(item);
        }

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new List_Adapter(getApplicationContext(),items));           //List adapter설정

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {        //drawer의 item 선택
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                return changeDrawerMenu(item);
            }
        });

        fab.setOnClickListener(this);        //fab clickListener
    }       //end Oncreate

    private boolean changeDrawerMenu(MenuItem item) {       //drawer의 항목을 눌렀을 경우
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        switch (item.getItemId()){
            case R.id.setup:
                Toast.makeText(MainActivity.this, "준비중!", Toast.LENGTH_SHORT).show();
                drawerlayout.closeDrawer(GravityCompat.START);
                return true;
        }   //end switch
        return true;
    }       //end changeDrawerMenu


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab:      //Floating Action Button을 눌렀을 경우
                Toast.makeText(MainActivity.this, "준비중!", Toast.LENGTH_SHORT).show();
               // startActivity(new Intent(MainActivity.this, ReviewActivity.class));
                break;
        }   //end switch
    }       //end Onclick method

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:     //back키를 입력했을 때
                if (drawerlayout.isDrawerOpen(navigationView))      //drawer가 열려 있는 상황
                    drawerlayout.closeDrawers();        //drawer를 닫음
                else {      //drawer가 닫혀있는 상황
                    moveTaskToBack(true);   //Activity를 백그라운드로 실행
                    finish();       //Activity 닫음
                }       //end else
                break;
        }   //end switch
        return true;
    }       //end onKeyDown method

}   //end class
