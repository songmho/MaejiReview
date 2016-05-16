package underflow.underflow.maejireview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Review_item item = items.get(position);
        Drawable drawable = context.getResources().getDrawable(item.getImage());
        ((Body) holder).title.setText(item.getTitle());
        ((Body) holder).user.setText(item.getUser());
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
