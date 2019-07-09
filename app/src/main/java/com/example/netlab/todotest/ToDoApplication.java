package com.example.netlab.todotest;

import android.app.Application;
import android.util.Log;
import com.example.netlab.todotest.Accessors.LoginAccessor;
import com.example.netlab.todotest.Accessors.ToDoListAccessor;
import com.example.netlab.todotest.Accessors.remote.ResteasyLoginItemAccessorImpl;
import com.example.netlab.todotest.Accessors.remote.ResteasyToDoItemAccessorImpl;
import com.example.netlab.todotest.Accessors.room.RoomToDoListAccessorImpl;
import com.example.netlab.todotest.Accessors.shared.SharedToDoItemAccessor;

import java.util.ArrayList;
import java.util.List;

public class ToDoApplication extends Application {
    private ToDoListAccessor localAccessor;
    private ToDoListAccessor remoteAccessor;
    private ToDoListAccessor sharedAccessor;

    private LoginAccessor loginAccessor;

    private boolean offline;

    public void onCreate() {
        super.onCreate();

        localAccessor = new RoomToDoListAccessorImpl();
        remoteAccessor = new ResteasyToDoItemAccessorImpl("http://10.0.2.2:8080/backend_war_exploded/rest");

        sharedAccessor = new SharedToDoItemAccessor(localAccessor, remoteAccessor);

        loginAccessor = new ResteasyLoginItemAccessorImpl("http://10.0.2.2:8080/backend_war_exploded/rest");
    }

    public ToDoListAccessor getSharedAccessor() {
        return sharedAccessor;
    }

    private void purgeRemoteItems(List<ToDoItem> items) {
        for (ToDoItem item : items) {
            this.remoteAccessor.deleteItem(item);
        }
    }

    public void syncAccessors() {
        List<ToDoItem> local = new ArrayList<>(this.localAccessor.readAllItems());
        List<ToDoItem> remote = this.remoteAccessor.readAllItems();

        if (local.size() > 0) {
            purgeRemoteItems(remote);

            for (ToDoItem item : local) {
                this.remoteAccessor.addItem(item);
            }
        } else {
            for (ToDoItem item : remote) {
                this.localAccessor.addItem(item);
            }
        }
    }

    public boolean isOffline() {
        return offline;
    }

    public void setOffline(boolean offline) {
        this.offline = offline;
    }

    public LoginAccessor getLoginAccessor() {
        return loginAccessor;
    }
}
