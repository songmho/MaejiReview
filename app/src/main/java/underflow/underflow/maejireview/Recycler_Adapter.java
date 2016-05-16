package underflow.underflow.maejireview;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by 수진 on 2016-05-16.
 */
public class Recycler_Adapter extends RecyclerView.Adapter {

    Context context;
    List<Recycler_item> items;
    int item_layout;

    public Recycler_Adapter(Context context, List<Recycler_item> items, int item_layout){
        this.context=context;
        this.items=items;
        this.item_layout=item_layout;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()) .inflate(R.layout.item_cardview, null);
        return new Body(v);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Recycler_item item = items.get(position);
        Drawable drawable = context.getResources().getDrawable(item.getImage());
        ((Body) holder).title.setText(item.getTitle());
        ((Body)holder).cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, item.getTitle(),Toast.LENGTH_SHORT).show();
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
        CardView cardView;

        public RecyclerView.ViewHolder(View itemView){
            super(itemView);
            image=(ImageView)itemView.findViewById(R.id.image);
            title=(TextView)itemView.findViewById(R.id.title);
            cardView=(CardView)itemView.findViewById(R.id.cardview);

        }
    }

    public class Body extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView title;
        TextView user;
        ImageView background;
        public Body(View itemView) {
            super(itemView);
            background = (ImageView) itemView.findViewById(R.id.background);
            cardView = (CardView)itemView.findViewById(R.id.card);
            title = (TextView)itemView.findViewById(R.id.title);
            user = (TextView)itemView.findViewById(R.id.user);
        }
    }
}
