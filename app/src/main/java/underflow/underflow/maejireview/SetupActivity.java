package underflow.underflow.maejireview;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseUser;

import java.io.File;

/**
 * Created by songmho on 2016-05-28.
 */
public class SetupActivity extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        Button btnLogOut =(Button)findViewById(R.id.logout);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("설정");

        TextView txtLastVer = (TextView) findViewById(R.id.last_ver);
        TextView txtCurVer = (TextView) findViewById(R.id.cur_ver);

        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), 0);
            txtCurVer.setText(info.versionName);
            txtLastVer.setText(info.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        //로그인 되지 않은 상태에서는 로그아웃 버튼을 숨겨준다.
        if (ParseUser.getCurrentUser() == null) {
            btnLogOut.setVisibility(View.GONE);
        }

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser currentUser = ParseUser.getCurrentUser();
                File file_up_path = new File("data/data/com.team1.valueupapp/files/");
                if (currentUser != null) {
                    File[] files = file_up_path.listFiles();
                    if (files != null) {
                        for (File file : files) {
                            String fname = file.getName();
                            if (fname.equals("profile.jpg"))
                                file.delete();
                            currentUser.remove("profile");
                        }
                    }
                    Toast.makeText(getApplicationContext(), "로그아웃되었습니다", Toast.LENGTH_SHORT).show();
                    ParseUser.logOut();
                    finish();
                    //메인 페이지를 재시작한다.
                    Intent restartIntent = new Intent(SetupActivity.this, MainActivity.class);
                    restartIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(restartIntent);

                } else {
                    Toast.makeText(getApplicationContext().getApplicationContext(), "로그인정보를 확인하세요", Toast.LENGTH_SHORT).show();
                }//end else
            }
        });//OnClickListener
    }
}
