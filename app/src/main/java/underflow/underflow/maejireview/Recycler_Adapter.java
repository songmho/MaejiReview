package underflow.underflow.maejireview;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by 수진 on 2016-05-16.
 */
public class Recycler_Adapter extends RecyclerView.Adapter {

    Context context;
    List<Review_item> items;
    int item_layout;

    public Recycler_Adapter(Context context, List<Review_item> items, int item_layout){
        this.context=context;
        this.items=items;
        this.item_layout=item_layout;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()) .inflate(R.layout.item_review, null);
        return new Body(v);
    }


    @Override
    //item 생성시 body와 adpapter를 이어줌
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Review_item item = items.get(position);
        Glide.with(context).load(item.getImage())
                .bitmapTransform(new CropCircleTransformation(context))
                .into(((Body) holder).image);
        Drawable drawable = context.getResources().getDrawable(item.getImage());
        ((Body) holder).title.setText(item.getTitle());
        ((Body) holder).user.setText(item.getUser());
        ((Body) holder).review_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ReviewActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("user", item.getUser());
                intent.putExtra("title", item.getTitle());
                context.startActivity(intent);
            }
        });
    }




    @Override
    public int getItemCount() {
        return this.items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView title;
        TextView user;

        public ViewHolder(View itemView){
            super(itemView);
            image=(ImageView)itemView.findViewById(R.id.image);
            title=(TextView)itemView.findViewById(R.id.title);
            user=(TextView)itemView.findViewById(R.id.user);
        }
    }

    public class Body extends RecyclerView.ViewHolder {
        TextView title;
        TextView user;
        ImageView image;
        LinearLayout review_item;
        public Body(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            title = (TextView)itemView.findViewById(R.id.title);
            user = (TextView)itemView.findViewById(R.id.user);
            review_item =(LinearLayout)itemView.findViewById(R.id.review_item);
        }
    }
}
