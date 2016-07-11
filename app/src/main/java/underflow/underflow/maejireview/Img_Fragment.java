package underflow.underflow.maejireview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;

import butterknife.ButterKnife;

/**
 * Created by songmho on 2016-05-09.
 */
public class Img_Fragment extends Fragment {

    Context mContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_img,container,false);
        ButterKnife.bind(this,view);

        Bundle bundle=getArguments();
        ImageView img = (ImageView)view.findViewById(R.id.img);
        if(bundle.getString("activity").equals("write")) {
            Log.e("dfdfdf","hello");
            if(bundle.getString("img")!=null && !bundle.getString("img").isEmpty()) {
                Log.e("dfdfdf12",bundle.getString("img"));
                File imgFile = new File(bundle.getString("img"));
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

                Glide.with(mContext).load(myBitmap).into(img);
            }
        }else
            Glide.with(mContext).load(bundle.getInt("img")).into(img);

        return view;
    }
}
