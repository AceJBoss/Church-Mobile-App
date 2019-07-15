package jboss.kisconsult.com.championsassembly.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import jboss.kisconsult.com.championsassembly.R;

/**
 * Created by JBoss on 7/20/2016.
 */
public class Register extends AppCompatActivity {

  jboss.kisconsult.com.championsassembly.Helper.Register register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_add_member);
        register = new  jboss.kisconsult.com.championsassembly.Helper.Register(this, "member");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        register.onActivityResult(requestCode, resultCode, data);
    }
}
