package jboss.kisconsult.com.championsassembly.Helper;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import jboss.kisconsult.com.championsassembly.Activity.Member;
import jboss.kisconsult.com.championsassembly.App.EndPoints;
import jboss.kisconsult.com.championsassembly.App.MyApplication;
import jboss.kisconsult.com.championsassembly.Fragment.ViewMembers;
import jboss.kisconsult.com.championsassembly.Model.User;
import jboss.kisconsult.com.championsassembly.R;

/**
 * Created by JBoss on 7/20/2016.
 */
public class Register implements View.OnClickListener {
    private AppCompatActivity context;
    private String category;
    private AutoCompleteTextView fullname, email, username, password, age, height, mobile, state;
    private TextInputLayout fname, mail, uname, pass, lState, phone;
    private Spinner gender, level,day,month,year;
    private CircleImageView image;
    private ImageButton camera;
    private Button register;
    private String profile;
    final CharSequence[] items = {"Take Photo", "Choose from Gallery", "Cancel"};
    ProgressBar bar;


    public Register(AppCompatActivity context, String category) {
        this.context = context;
        this.category = category;
        initComp();
    }

    private void initComp() {
        fullname = (AutoCompleteTextView) context.findViewById(R.id.fullname);
        email = (AutoCompleteTextView) context.findViewById(R.id.email);
        username = (AutoCompleteTextView) context.findViewById(R.id.username);
        password = (AutoCompleteTextView) context.findViewById(R.id.password);
        state = (AutoCompleteTextView) context.findViewById(R.id.state);
        //height = (AutoCompleteTextView) context.findViewById(R.id.height);
        gender = (Spinner) context.findViewById(R.id.gender);
        level = (Spinner) context.findViewById(R.id.level);
        day = (Spinner) context.findViewById(R.id.day);
        month = (Spinner) context.findViewById(R.id.month);
        year = (Spinner) context.findViewById(R.id.year);
        image = (CircleImageView) context.findViewById(R.id.profile_pix);
        camera = (ImageButton) context.findViewById(R.id.camera);
        register = (Button) context.findViewById(R.id.register);
        mobile = (AutoCompleteTextView) context.findViewById(R.id.phone);
        phone = (TextInputLayout) context.findViewById(R.id.layout_phone);

        fname = (TextInputLayout) context.findViewById(R.id.layout_fullname);
        mail = (TextInputLayout) context.findViewById(R.id.layout_email);
        uname = (TextInputLayout) context.findViewById(R.id.layout_username);
        pass = (TextInputLayout) context.findViewById(R.id.layout_password);
        lState = (TextInputLayout) context.findViewById(R.id.layout_state);
       // lHeight = (TextInputLayout) context.findViewById(R.id.layout_height);

        fullname.addTextChangedListener(new MyTextWatcher(fullname));
        email.addTextChangedListener(new MyTextWatcher(email));
        username.addTextChangedListener(new MyTextWatcher(username));
        password.addTextChangedListener(new MyTextWatcher(password));
        state.addTextChangedListener(new MyTextWatcher(state));
        height.addTextChangedListener(new MyTextWatcher(height));
        mobile.addTextChangedListener(new MyTextWatcher(mobile));

        //get default image and decode it
        Bitmap bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.profile);
        profile = getImage(bm);

        camera.setOnClickListener(this);
        register.setOnClickListener(this);

        bar = (ProgressBar) context.findViewById(R.id.bar);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (!bar.isShown()) {
            switch (id) {
                case R.id.camera:
                    camera();
                    break;
                case R.id.register:
                    register();
                    break;
            }
        }
    }

    private void register() {
        if (!validateFullname()) {
            return;
        }
        if (!validateUsername()) {
            return;
        }
        if (!validateEmail()) {
            return;
        }
        if (!validatePassword()) {
            return;
        }
        if (!validateState()) {
            return;
        }
        if (!validateDay()) {
            return;
        }
        if (!validateMonth()) {
            return;
        }
        if (!validateYear()) {
            return;
        }
        if (!validateGender()) {
            return;
        }
        if (!validatePhone()) {
            return;
        }

        final String fname, uname, mail, pass, dy, mon, yer, sta, sex, blood,agee,lvl, phone,cat;

        fname = fullname.getText().toString().trim();
        uname = username.getText().toString().trim().toLowerCase();
        pass = password.getText().toString().trim().toLowerCase();
        mail = email.getText().toString().trim();
        sta = state.getText().toString().trim();
        sex = gender.getSelectedItem().toString();
        dy = day.getSelectedItem().toString();
        mon = month.getSelectedItem().toString();
        yer = year.getSelectedItem().toString();
        lvl = level.getSelectedItem().toString();
        agee = dy+"/"+mon+"/"+yer;
        phone = mobile.getText().toString().trim();
        cat = category.equalsIgnoreCase("member") ? "" : "Executive";

        bar.setVisibility(View.VISIBLE);
        StringRequest strReq = new StringRequest(Request.Method.POST, EndPoints.USER_REGISTER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                bar.setVisibility(View.GONE);
                Log.e("Login", response);
                try {
                    JSONObject obj = new JSONObject(response);

                    // check for error flag
                    if (obj.getBoolean("error") == false) {

                        User user = new User();
                        user.setFullname(fname);
                        user.setUsername(uname);
                        user.setPassword(pass);
                        user.setEmail(mail);
                        user.setMobile(phone);
                        user.setAge(agee);
                        user.setLevel(lvl);
                        user.setState(sta);
                        user.setGender(sex);
                        user.setImage("images/" + uname + ".JPEG");
                        user.setCategory(cat);

                        if (category.equalsIgnoreCase("member")) {
                            MyApplication.getInstance().getPref().saveUser(user);
                            Intent intent = new Intent(context, Member.class);
                             context.startActivity(intent);
                             context.finish();
                        } else {
                            Bundle bundle = new Bundle();
                            bundle.putString("category", "Executive");
                            ViewMembers frag = new ViewMembers();
                            frag.setArguments(bundle);
                            context.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, frag).commit();
                        }
                    } else {
                        // login error - simply toast the message
                        Toast.makeText(context.getApplicationContext(), "" + obj.getString("message").toString(), Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    Toast.makeText(context.getApplicationContext(), "Json parse error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                bar.setVisibility(View.GONE);
                Log.e("Login", "Error-> " + error.getMessage());
                NetworkResponse networkResponse = error.networkResponse;
                Toast.makeText(context.getApplicationContext(), "Volley error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("fullname", fname);
                params.put("username", uname);
                params.put("password", pass);
                params.put("email", mail);
                params.put("mobile", phone);
                params.put("dob", agee);
                params.put("level", lvl);
                params.put("state", sta);
                params.put("gender", sex);
                params.put("picture", profile);
                params.put("category", cat);

                return params;
            }
        };

        //Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(strReq);

    }

    private void camera() {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);

        alert.setTitle("Add Profile Photo!");
        alert.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    Intent intent = new Intent(
                            android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    context.startActivityForResult(intent, 105);

                } else if (items[item].equals("Choose from Gallery")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    context.startActivityForResult(
                            Intent.createChooser(intent, "Select Picture"), 100);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        alert.show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == context.RESULT_OK) {
            Bitmap bm = null;
            switch (requestCode) {
                case 100:
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = context.getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();

                    bm = BitmapFactory.decodeFile(picturePath);
                    break;
                case 105:
                    bm = (Bitmap) data.getExtras().get("data");
                    break;
            }
            image.setImageBitmap(bm);
            profile = getImage(bm);
        }
    }

    private String getImage(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    private boolean validateFullname() {
        if (fullname.getText().toString().trim().isEmpty()) {
            fname.setError(context.getString(R.string.err_msg_fullname));
            requestFocus(fullname);
            return false;
        } else {
            fname.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateUsername() {
        if (username.getText().toString().trim().isEmpty()) {
            uname.setError(context.getString(R.string.err_msg_username));
            requestFocus(username);
            return false;
        } else {
            uname.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePassword() {
        if (password.getText().toString().trim().isEmpty()) {
            pass.setError(context.getString(R.string.err_msg_password));
            requestFocus(password);
            return false;
        } else {
            pass.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateDay() {
        if (day.getSelectedItemPosition() <= 0) {
            Toast.makeText(context, "Select your day of birth", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
    private boolean validateMonth() {
        if (month.getSelectedItemPosition() <= 0) {
            Toast.makeText(context, "Select your month", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
    private boolean validateYear() {
        if (year.getSelectedItemPosition() <= 0) {
            Toast.makeText(context, "Select your year", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private boolean validateGender() {
        if (gender.getSelectedItemPosition() <= 0) {
            Toast.makeText(context, "Select your Gender", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }


    private boolean validateState() {
        if (state.getText().toString().trim().isEmpty()) {
            lState.setError(context.getString(R.string.err_msg_age));
            requestFocus(state);
            return false;
        } else {
            lState.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePhone() {
        if (mobile.getText().toString().trim().isEmpty()) {
            phone.setError(context.getString(R.string.err_msg_mobile));
            requestFocus(mobile);
            return false;
        } else {
            phone.setErrorEnabled(false);
        }

        return true;
    }


    private boolean validateEmail() {
        String email = this.email.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            mail.setError(context.getString(R.string.err_msg_email));
            requestFocus(this.email);
            return false;
        } else {
            mail.setErrorEnabled(false);
        }

        return true;
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            context.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
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
                    validateFullname();
                    break;
                case R.id.username:
                    validateUsername();
                    break;
                case R.id.email:
                    validateEmail();
                    break;
                case R.id.day:
                    validateDay();
                    break;
                case R.id.month:
                    validateMonth();
                    break;
                case R.id.year:
                    validateYear();
                    break;
                case R.id.password:
                    validatePassword();
                    break;
                case R.id.state:
                    validateState();
                    break;
                case R.id.phone:
                    validatePhone();
                    break;
            }
        }
    }
}
