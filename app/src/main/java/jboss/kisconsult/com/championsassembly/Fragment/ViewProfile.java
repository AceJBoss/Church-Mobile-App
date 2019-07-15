package jboss.kisconsult.com.championsassembly.Fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import jboss.kisconsult.com.championsassembly.Helper.Image;
import jboss.kisconsult.com.championsassembly.Model.User;
import jboss.kisconsult.com.championsassembly.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewProfile extends Fragment {

    private User user;
    private TextView fname, uname, mobile, email, age, level, gender, state, department;
    private CircleImageView image;

    public ViewProfile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_profile, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        user = (User) bundle.getSerializable("data");
        initComp();
    }

    private void initComp() {
        fname = (TextView) getActivity().findViewById(R.id.fullname);
        uname = (TextView) getActivity().findViewById(R.id.username);
        mobile = (TextView) getActivity().findViewById(R.id.mobile);
        email = (TextView) getActivity().findViewById(R.id.email);
        age = (TextView) getActivity().findViewById(R.id.age);
        level = (TextView) getActivity().findViewById(R.id.level);
        gender = (TextView) getActivity().findViewById(R.id.gender);
        state = (TextView) getActivity().findViewById(R.id.state);
        department = (TextView) getActivity().findViewById(R.id.department);
        image = (CircleImageView) getActivity().findViewById(R.id.profile_pix);

        fname.setText(user.getFullname());
        uname.setText(user.getUsername());
        mobile.setText(user.getMobile());
        email.setText(user.getEmail());
        age.setText(user.getAge()+"");
        level.setText(user.getLevel() + "");
        gender.setText(user.getGender());
        state.setText(user.getBgroup());
        department.setText(user.getCategory());
        new Image(image, user.getImage());
    }
}
