package jboss.kisconsult.com.championsassembly.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jboss.kisconsult.com.championsassembly.Helper.Register;
import jboss.kisconsult.com.championsassembly.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddMember extends Fragment {


    Register register;
    public AddMember() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_member, container, false);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        register = new Register((AppCompatActivity) getActivity(), "Executive");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        register.onActivityResult(requestCode, resultCode, data);
    }

}
