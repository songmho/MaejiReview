package underflow.underflow.maejireview;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by songmho on 2016-05-03.
 */
public class List_Adapter extends RecyclerView.Adapter {
    Context context;
    ArrayList<ListItem> items;
    public List_Adapter(Context context, ArrayList<ListItem> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,parent,false);

        return new Body(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ListItem item = items.get(position);
        ((Body)holder).title.setText(item.getTitle());
        ((Body)holder).user.setText(item.getUser());
        ((Body)holder).cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context,ReviewActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("title",item.getTitle());
                i.putExtra("user",item.getUser());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class Body extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView title;
        TextView user;
        public Body(View itemView) {
            super(itemView);
            cardView = (CardView)itemView.findViewById(R.id.card);
            title = (TextView)itemView.findViewById(R.id.title);
            user = (TextView)itemView.findViewById(R.id.user);
        }
    }

}
