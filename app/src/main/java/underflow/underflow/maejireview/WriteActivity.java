package underflow.underflow.maejireview;

import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by songmho on 2016-05-09.
 */
public class WriteActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    EditText edit_title;
    EditText edit_content;
    RadioGroup radiogroup;
    RadioButton radio_rice, radio_home;
    ImageButton bt_add;

    List<Uri> img_arr = new ArrayList<>();
    String profileUrl = null;
    int CAMERA_REQUEST = 1000;
    int SELECT_FILE = 2000;
    CharSequence[] item = {"카메라", "갤러리에서 사진 가져오기", "삭제"};

    Context mContext;
    String category;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        ButterKnife.bind(this);

        mContext = this;

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        edit_title = (EditText)findViewById(R.id.edit_title);
        edit_content = (EditText)findViewById(R.id.edit_content);
        radiogroup = (RadioGroup)findViewById(R.id.radiogroup);
        radio_home = (RadioButton)findViewById(R.id.radio_home);
        radio_rice = (RadioButton)findViewById(R.id.radio_rice);
        bt_add = (ImageButton)findViewById(R.id.bt_add);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("리뷰작성");

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(new WriteAdapter(getApplicationContext(),img_arr));

        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MakingAlertDialog();
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog();
            }
        });

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

    private void MakingAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(WriteActivity.this, R.style.dialog);
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
                //    Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.profile);
                 //   Glide.with(getApplicationContext()).load(b).diskCacheStrategy(DiskCacheStrategy.ALL).bitmapTransform(new CropCircleTransformation(getApplicationContext())).placeholder(R.drawable.profile).into(profile);

                }
            }
        });
        builder.show();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap thum = null;
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == CAMERA_REQUEST) {
                OutputStream outputStream = null;
                try {
                    outputStream = new FileOutputStream(Environment.getExternalStorageDirectory().getAbsolutePath()+"MaejiReview\\temp.png");
                    thum = (Bitmap) data.getExtras().get("data");
                    thum.compress(Bitmap.CompressFormat.PNG,100,outputStream);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }finally{
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            } else if (requestCode == SELECT_FILE) {
               // Uri uri = data.getData();
                ClipData clipData = data.getClipData();
                for (int i = 0; i < clipData.getItemCount(); i++) {
                    Uri uri = clipData.getItemAt(i).getUri();
                    Log.e("dfdf",""+uri);
                    img_arr.add(uri);
                }
                recyclerView.getAdapter().notifyDataSetChanged();
                 //     thum = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                //       Glide.with(getApplicationContext()).load(uri).diskCacheStrategy(DiskCacheStrategy.ALL).bitmapTransform(new CropCircleTransformation(getApplicationContext())).placeholder(R.drawable.profile).into(profile);

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


    private void imgSendParse(ParseObject o) {
        for(int i = 0;i<img_arr.size();i++) {
            //이부분 에러남 file 이 null로 뜨는 부분에서
            ParseFile file = null;
            try {
                file = new ParseFile("img"+i+".jpg",bitmapToByteArray(img_arr.get(i)));
            } catch (IOException e) {
                e.printStackTrace();
            }
            ParseQuery<ParseObject> query = o.getRelation("posting_photo").getQuery();
            final ParseFile finalFile = file;
            query.getFirstInBackground(new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject object, ParseException e) {
                    object.add("photo", finalFile);
                }
            });
        }
    }

    private byte[] bitmapToByteArray(Uri uri) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        FileInputStream fis = new FileInputStream(new File(String.valueOf(uri)));

        byte[] buf = new byte[1024];
        int n;
        while (-1 != (n = fis.read(buf)))
            baos.write(buf, 0, n);

        return baos.toByteArray();
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
            imgSendParse(o);
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
}
