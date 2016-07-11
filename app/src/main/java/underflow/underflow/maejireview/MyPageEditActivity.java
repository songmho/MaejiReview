package underflow.underflow.maejireview;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.parse.ParseFile;
import com.parse.ParseUser;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by songmho on 2016-05-30.
 */
public class MyPageEditActivity extends AppCompatActivity {

    Toolbar toolbar;

    ParseFile profileParse;
    ParseUser user = ParseUser.getCurrentUser();
    String profileUrl = null;
    int CAMERA_REQUEST = 1000;
    int SELECT_FILE = 2000;
    CharSequence[] item = {"카메라", "갤러리에서 사진 가져오기", "삭제"};
    private List<String> arrTags;
    Context mContext;

    EditText editName;
    ImageView profile;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypageedit);


        toolbar = (Toolbar)findViewById(R.id.toolbar);
        profile = (ImageView)findViewById(R.id.my_image);
        editName = (EditText)findViewById(R.id.name);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("내정보 수정");

        Intent getIntent = getIntent();
        editName.setText(getIntent.getStringExtra("name"));
        Glide.with(getApplicationContext()).load(getIntent.getStringExtra("profileUrl")).diskCacheStrategy(DiskCacheStrategy.ALL).bitmapTransform(new CropCircleTransformation(getApplicationContext())).placeholder(R.drawable.profile).into(profile);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MakingAlertDialog();
            }
        });
        setProfileImage();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_confirm, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_check) {
            if (editName.getText().toString().trim().equals("")) {
                Toast.makeText(MyPageEditActivity.this, "이름을 입력하세요.", Toast.LENGTH_SHORT).show();
            }  else {    //모두 입력 되었을 때 서버로 전송한다.
                ParseUser user = ParseUser.getCurrentUser();
                user.put("name", String.valueOf(editName.getText()));
                user.saveInBackground();
                finish();
                Toast.makeText(MyPageEditActivity.this, "저장 되었습니다.", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        if (id == android.R.id.home && getIntent().getIntExtra("signup", 0) == 1) {
            startActivity(new Intent(MyPageEditActivity.this, MainActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    public void setProfileImage() {
        if (profileUrl != null) {
            if (!isFinishing())
              Glide.with(getApplicationContext()).load(profileUrl).diskCacheStrategy(DiskCacheStrategy.ALL).bitmapTransform(new CropCircleTransformation(getApplicationContext())).placeholder(R.drawable.profile).into(profile);
        }
        else
            Glide.with(getApplicationContext()).load(R.drawable.profile).diskCacheStrategy(DiskCacheStrategy.ALL).bitmapTransform(new CropCircleTransformation(getApplicationContext())).placeholder(R.drawable.profile).into(profile);

    }

    private void MakingAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MyPageEditActivity.this, R.style.dialog);
        builder.setTitle("프로필 사진 추가하기");
        builder.setItems(item, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position) {
                if (item[position].equals("카메라")) {
                    Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (camera.resolveActivity(getPackageManager()) != null)
                        startActivityForResult(camera, CAMERA_REQUEST);
                } else if (item[position].equals("갤러리에서 사진 가져오기")) {
                    Intent gallery = new Intent(Intent.ACTION_PICK);
                    gallery.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
                    gallery.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                    gallery.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(gallery, SELECT_FILE);
                } else if (item[position].equals("삭제")) {
                    ParseUser.getCurrentUser().remove("profile");
                    Toast.makeText(mContext, "삭제하였습니다.", Toast.LENGTH_SHORT).show();
                    Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.profile);
                    Glide.with(getApplicationContext()).load(b).diskCacheStrategy(DiskCacheStrategy.ALL).bitmapTransform(new CropCircleTransformation(getApplicationContext())).placeholder(R.drawable.profile).into(profile);

                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap thum = null;
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == CAMERA_REQUEST) {
                thum = (Bitmap) data.getExtras().get("data");
                Glide.with(getApplicationContext()).load(bitmapToByteArray(thum)).diskCacheStrategy(DiskCacheStrategy.ALL).bitmapTransform(new CropCircleTransformation(getApplicationContext())).placeholder(R.drawable.profile).into(profile);
                imgSendParse(thum);
            } else if (requestCode == SELECT_FILE) {
                Uri uri = data.getData();
                try {
                    thum = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    Glide.with(getApplicationContext()).load(uri).diskCacheStrategy(DiskCacheStrategy.ALL).bitmapTransform(new CropCircleTransformation(getApplicationContext())).placeholder(R.drawable.profile).into(profile);
                    imgSendParse(thum);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            FileOutputStream fos = null;
            try {
                fos = openFileOutput("profile.jpg", 0);
                if (thum != null) {
                    thum.compress(Bitmap.CompressFormat.JPEG, 50, fos);
                    fos.flush();
                }
                fos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }

    private void imgSendParse(Bitmap thum) {
        profileParse = new ParseFile("profile.jpg", bitmapToByteArray(thum));
        if (user.get("profile") != null)
            user.remove("profile");
        user.put("profile", profileParse);
        user.saveInBackground();
    }

    private byte[] bitmapToByteArray(Bitmap bm) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 50, stream);
        return stream.toByteArray();
    }


}
