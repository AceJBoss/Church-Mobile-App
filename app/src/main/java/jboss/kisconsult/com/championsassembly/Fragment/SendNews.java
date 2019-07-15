package jboss.kisconsult.com.championsassembly.Fragment;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.text.DateFormat;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import jboss.kisconsult.com.championsassembly.App.EndPoints;
import jboss.kisconsult.com.championsassembly.App.MyApplication;
import jboss.kisconsult.com.championsassembly.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import jboss.kisconsult.com.championsassembly.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SendNews extends Fragment implements View.OnClickListener {

    private AutoCompleteTextView topic, details;
    Button send;
    TextInputLayout top, dets;
    private ProgressBar bar;
    private CircleImageView image;
    private ImageButton camera;
    private String profile;
    final CharSequence[] items = {"Take Photo", "Choose from Gallery", "Cancel"};

    public SendNews() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_send_news, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initComp();
    }

    private void initComp() {
        topic = (AutoCompleteTextView) getActivity().findViewById(R.id.fullname);
        details = (AutoCompleteTextView) getActivity().findViewById(R.id.email);
        send = (Button) getActivity().findViewById(R.id.register);
        top = (TextInputLayout) getActivity().findViewById(R.id.layout_fullname);
        dets = (TextInputLayout) getActivity().findViewById(R.id.layout_detail);
        bar = (ProgressBar) getActivity().findViewById(R.id.bar);

        topic.addTextChangedListener(new MyTextWatcher(topic));
        details.addTextChangedListener(new MyTextWatcher(details));
       // drug.addTextChangedListener(new MyTextWatcher(drug));

        //get default image and decode it
        Bitmap bm = BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.profile);
        profile = getImage(bm);

        camera.setOnClickListener(this);

        send.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (!bar.isShown()) {
            switch (id) {
                case R.id.register:
                    send();
                    break;
            }
        }
    }

    private String getImage(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    private boolean validateComplain() {
        if (topic.getText().toString().trim().isEmpty()) {
            top.setError(getString(R.string.err_msg_complain));
            requestFocus(topic);
            return false;
        } else {
            top.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validateDetails() {
        if (details.getText().toString().trim().isEmpty()) {
            dets.setError(getString(R.string.err_msg_drug));
            requestFocus(details);
            return false;
        } else {
            dets.setErrorEnabled(false);
        }

        return true;
    }


    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private class MyTextWatcher implements android.text.TextWatcher {

        private View view;

        public MyTextWatcher(View view) {
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            switch (view.getId()) {
                case R.id.fullname:
                    validateComplain();
                    break;
                case R.id.email:
                    validateDetails();
                    break;
            }
        }
    }

    private void send() {
        if (!validateComplain()) {
            return;
        }
        if (!validateDetails()) {
            return;
        }

        final String dat,comp, drg, dept, uname;

        Date d = new Date();
        DateFormat dy = DateFormat.getDateTimeInstance();
        dat = dy.format(d);
        comp = topic.getText().toString().trim();
        drg = details.getText().toString().trim();
        uname = MyApplication.getInstance().getPref().getUser().getUsername();

        bar.setVisibility(View.VISIBLE);
        StringRequest strReq = new StringRequest(Request.Method.POST, EndPoints.ADD_COMPLAIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                bar.setVisibility(View.GONE);
                try {
                    JSONObject obj = new JSONObject(response);

                    // check for error flag
                    if (obj.getBoolean("error") == false) {

                        Toast.makeText(getActivity(), obj.getString("message"), Toast.LENGTH_LONG).show();
                       // getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PatientHome()).commit();
                    } else {
                        // login error - simply toast the message
                        Toast.makeText(getActivity(), obj.getString("message"), Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    Toast.makeText(getActivity(), "Json parse error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                bar.setVisibility(View.GONE);
                Log.e("Login", "Error-> " + error.getMessage());
                NetworkResponse networkResponse = error.networkResponse;
                Toast.makeText(getActivity(), "Volley error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("news_name", comp);
                params.put("news_details", drg);
                params.put("news_pix", profile);
                params.put("news_date", dat);
              //  params.put("department", dept);
                return params;
            }
        };

        //Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(strReq);

    }
}
