package com.example.netlab.todotest.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.util.Patterns;
import androidx.appcompat.app.AppCompatActivity;
import com.example.netlab.todotest.AbstractActivityProvider;
import com.example.netlab.todotest.Accessors.LoginAccessor;
import com.example.netlab.todotest.LoginItem;
import com.example.netlab.todotest.R;
import com.example.netlab.todotest.ToDoApplication;

import static android.view.View.VISIBLE;

public class LoginActivity extends AppCompatActivity {

    private boolean emailChecked = false;
    private boolean passwortChecked = false;
    private String password = "";
    private String email = "";

    private ToDoApplication application;
    private LoginAccessor loginAccessor;


    AsyncTask<Void, Void, Object> myTask(final TextView loginErrorView) {
        return new AsyncTask<Void, Void, Object>() {
            private ProgressDialog dialog = null;
            boolean authorized = false;

            @Override
            protected void onPreExecute() {
                dialog = ProgressDialog.show(LoginActivity.this, "Processing...", "the input.");
            }

            @Override
            protected Object doInBackground(Void... voids) {
                try {
                    authorized = loginAccessor.login(new LoginItem(email, password));
                    application.syncAccessors();
                } catch (Throwable t) {
                    Log.d("LoginActivity", "webservice not available");
                }
                return "test";
            }

            @Override
            protected void onPostExecute(Object o) {
                if (authorized) {
                    startActivity(new Intent(LoginActivity.this, TodoActivity.class));
                    dialog.cancel();
                    finish();
                } else {
                    loginErrorView.setVisibility(VISIBLE);
                    dialog.cancel();
                }
            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        application = (ToDoApplication) getApplication();
        ((AbstractActivityProvider) application.getSharedAccessor()).setActivity(this);

        loginAccessor = application.getLoginAccessor();

        final Button loginButton = findViewById(R.id.loginButton);
        final EditText emailEdit = findViewById(R.id.emailEdit);
        final EditText passwordEdit = findViewById(R.id.passwortEdit);
        final TextView feedbackTextView = findViewById(R.id.feedbackTextView);
        final TextView loginErrorView = findViewById(R.id.loginErrorView);

        emailEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    email = emailEdit.getText().toString();

                    if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        emailChecked = false;
                        feedbackTextView.setVisibility(VISIBLE);
                    } else {
                        emailChecked = true;
                        if (passwortChecked) {
                            loginButton.setEnabled(true);
                        }
                        feedbackTextView.setVisibility(View.GONE);
                    }
                }
            }
        });

        emailEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                feedbackTextView.setVisibility(View.GONE);
                loginErrorView.setVisibility(View.GONE);
            }
        });

        passwordEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                myTask(loginErrorView).execute();
                return false;
            }
        });

        passwordEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                password = s.toString();
                if (password.length() == 6) {
                    passwortChecked = true;
                    if(emailChecked) {
                        loginButton.setEnabled(true);
                    }
                } else {
                    passwortChecked = false;
                }
                loginErrorView.setVisibility(View.GONE);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myTask(loginErrorView).execute();
            }
        });
    }
}
