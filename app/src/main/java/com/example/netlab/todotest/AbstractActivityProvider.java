package com.example.netlab.todotest;

import androidx.appcompat.app.AppCompatActivity;

public abstract class AbstractActivityProvider {

    private AppCompatActivity activity;

    protected AppCompatActivity getActivity() {
        return activity;
    }

    public void setActivity(AppCompatActivity activity) {
        this.activity = activity;
    }
}
