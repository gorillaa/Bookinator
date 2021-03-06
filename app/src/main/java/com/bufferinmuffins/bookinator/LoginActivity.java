package com.bufferinmuffins.bookinator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.MenuItem;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;


public class LoginActivity extends ActionBarActivity {



    public static BookinatorSession bsession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bsession = new BookinatorSession(getString(R.string.mongolab_apikey), this);
        SharedPreferences settings = getSharedPreferences("session", 0);
        bsession.checkSession(settings.getString("sessid", "notagoodsession"));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    public void onLoginClick(final View view) {
        String email = ((EditText)findViewById(R.id.login_email_editText)).getText().toString();
        String pwd = ((EditText)findViewById(R.id.login_password_editText)).getText().toString();
        bsession.login(email, pwd);


    }
    public void onRegisterClick(final View view) {
        Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(i);
    }

    public void onLoginResponse(boolean pass) {
        if (!pass) {
            ((TextView)findViewById(R.id.login_errtext)).setText(bsession.getErrMsg());
            return;
        }
        SharedPreferences settings = getSharedPreferences("session", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("sessid", bsession.getSessID());
        editor.commit();
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();
    }


    public void onSessionResponse(boolean pass) {
        if (!pass) {
            setContentView(R.layout.activity_login);
            this.getSupportActionBar().hide();
            return;
        }
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();
    }
}
