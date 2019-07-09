package com.example.netlab.todotest.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.netlab.todotest.Accessors.LoginAccessor;
import com.example.netlab.todotest.LoginItem;

import com.example.netlab.todotest.R;
import com.example.netlab.todotest.ToDoApplication;

public class MainActivity extends AppCompatActivity {

    private LoginAccessor login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = ((ToDoApplication)getApplication()).getLoginAccessor();

        final ImageView iconView = findViewById(R.id.imageView);
    }

    @Override
    protected void onResume() {
        super.onResume();

        CheckOnline check = new CheckOnline();
        check.execute((Void) null);
    }

    public class CheckOnline extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                login.login(new LoginItem("check", "online"));
                return true;
            } catch (Exception e) {
                Log.e("CheckOnline", "doInBackground", e);
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean status) {
            ((ToDoApplication)getApplication()).setOffline(!status);

            Intent start;

            if (status)
                start = new Intent(MainActivity.this, LoginActivity.class);
            else
                start = new Intent(MainActivity.this, TodoActivity.class);

            startActivity(start);

            finish();
        }
    }

}