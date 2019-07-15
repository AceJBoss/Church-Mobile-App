package jboss.kisconsult.com.championsassembly.Fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import jboss.kisconsult.com.championsassembly.Helper.Image;
import jboss.kisconsult.com.championsassembly.Model.Newsetter;
import jboss.kisconsult.com.championsassembly.Model.User;
import jboss.kisconsult.com.championsassembly.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewDetail extends Fragment {

    private Newsetter news;
    private TextView fname,detail;
    private CircleImageView image;

    public ViewDetail() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_detail, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        news = (Newsetter) bundle.getSerializable("data");
        initComp();
    }

    private void initComp() {
        fname = (TextView) getActivity().findViewById(R.id.fullname);
        detail = (TextView) getActivity().findViewById(R.id.username);

        image = (CircleImageView) getActivity().findViewById(R.id.profile_pix);

        fname.setText(news.getNamr());
        detail.setText(news.getDetails());

        new Image(image, news.getImage());
    }
}
