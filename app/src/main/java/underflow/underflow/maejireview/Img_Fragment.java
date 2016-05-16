package underflow.underflow.maejireview;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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
        ImageView img = (ImageView)getActivity().findViewById(R.id.img);
        img.setImageResource(bundle.getInt("img"));

        return view;
    }
}
