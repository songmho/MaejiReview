package underflow.underflow.maejireview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by songmho on 2016-07-06.
 */
public class WriteAdapter extends RecyclerView.Adapter {
    Context mContext;
    List<Uri> imgArr = new ArrayList<>();
    public WriteAdapter(Context applicationContext, List<Uri>  imgArr) {
        this.mContext = applicationContext;
        this.imgArr = imgArr;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_img,parent,false);
        return new Body(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(imgArr.get(position)!=null) {
            Log.e("writeAdapter","1");
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(),imgArr.get(position));
                ((Body) holder).imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else
            ((Body)holder).imageView.setBackgroundColor(0xffff0033);
    }

    @Override
    public int getItemCount() {
        return imgArr.size();
    }

    public class Body extends RecyclerView.ViewHolder{
        ImageView imageView;
        public Body(View itemView) {
            super(itemView);
            imageView=(ImageView)itemView.findViewById(R.id.img);
        }
    }
}
