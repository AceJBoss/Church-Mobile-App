package jboss.kisconsult.com.championsassembly.Fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import jboss.kisconsult.com.championsassembly.Adapter.MemberAdapter;
import jboss.kisconsult.com.championsassembly.App.EndPoints;
import jboss.kisconsult.com.championsassembly.App.MyApplication;
import jboss.kisconsult.com.championsassembly.Model.User;
import jboss.kisconsult.com.championsassembly.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewNews extends Fragment implements SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener {

    ArrayList<User> arrayList;
    MemberAdapter adapter;
    private ListView listView;
    private SwipeRefreshLayout refresh;
    private String category;

    public ViewNews() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_news, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initComp();
    }

    private void initComp() {
        Bundle bundle = getArguments();
        category = bundle.getString("category");

        arrayList = new ArrayList<>();
        adapter = new MemberAdapter(getActivity(), arrayList);

        listView = (ListView) getActivity().findViewById(R.id.list);
        refresh = (SwipeRefreshLayout) getActivity().findViewById(R.id.refresh);

        listView.setAdapter(adapter);
        refresh.setOnRefreshListener(this);
        refresh.post(new Runnable() {
            @Override
            public void run() {
                refresh.setRefreshing(true);
                getUser();
            }
        });
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onRefresh() {
        getUser();
    }

    private void getUser() {
        String url = EndPoints.GET_USERS.replace("_ID_", category);


        refresh.setRefreshing(true);
        StringRequest strReq = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                refresh.setRefreshing(false);
                try {
                    JSONObject obj = new JSONObject(response);

                    // check for error flag
                    if (obj.getBoolean("error") == false) {

                        JSONArray array = obj.getJSONArray("user");
                        arrayList.clear();
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject users = (JSONObject) array.get(i);
                            User user = new User();
                            user.setFullname(users.getString("fullname"));
                            user.setMobile(users.getString("phone"));
                            user.setImage(users.getString("picture"));
                            user.setUsername(users.getString("username"));
                            user.setAge(users.getString("dob"));
                            user.setLevel(users.getString("level"));
                            user.setEmail(users.getString("email"));
                            user.setCategory(users.getString("category"));
                            user.setState(users.getString("state"));
                            user.setPassword(users.getString("password"));
                            user.setGender(users.getString("gender"));
                            user.setCategory(users.getString("category"));

                            arrayList.add(user);
                            adapter.notifyDataSetChanged();
                        }
                    } else {
                        // login error - simply toast the message
                        Toast.makeText(getActivity(), "" + obj.getString("message").toString(), Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    Toast.makeText(getActivity(), "Json parse error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                refresh.setRefreshing(false);
                Log.e("Login", "Error-> " + error.getMessage());
                NetworkResponse networkResponse = error.networkResponse;
                Toast.makeText(getActivity(), "Volley error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        //Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(strReq);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", arrayList.get(position));
        // ViewDetail frag = new ViewDetail();
        //frag.setArguments(bundle);
        //getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(frag.getTag()).replace(R.id.fragment_container, frag).commit();
    }
}
