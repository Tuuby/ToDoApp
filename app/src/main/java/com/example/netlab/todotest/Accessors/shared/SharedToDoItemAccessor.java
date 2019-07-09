package com.example.netlab.todotest.Accessors.shared;

import android.widget.ListAdapter;
import androidx.appcompat.app.AppCompatActivity;
import com.example.netlab.todotest.AbstractActivityProvider;
import com.example.netlab.todotest.Accessors.ToDoListAccessor;
import com.example.netlab.todotest.ToDoApplication;
import com.example.netlab.todotest.ToDoItem;

import java.util.List;

public class SharedToDoItemAccessor extends AbstractActivityProvider implements ToDoListAccessor {
    private ToDoListAccessor localAccessor;
    private ToDoListAccessor remoteAccessor;

    private ToDoApplication application;

    public SharedToDoItemAccessor(ToDoListAccessor local, ToDoListAccessor remote) {
        localAccessor = local;
        remoteAccessor = remote;
    }

    @Override
    public void setActivity(AppCompatActivity activity) {
        application = (ToDoApplication) activity.getApplication();
        ((AbstractActivityProvider) localAccessor).setActivity(activity);
        ((AbstractActivityProvider) remoteAccessor).setActivity(activity);
    }

    @Override
    public void addItem(ToDoItem item) {
        localAccessor.addItem(item);
        if (!application.isOffline()) {
            remoteAccessor.addItem(item);
        }
    }

    @Override
    public List<ToDoItem> readAllItems() {
        return localAccessor.readAllItems();
    }

    @Override
    public ListAdapter getAdapter() {
        return localAccessor.getAdapter();
    }

    @Override
    public void updateItem(ToDoItem item) {
        localAccessor.updateItem(item);
        if (!application.isOffline()) {
            remoteAccessor.updateItem(item);
        }
    }

    @Override
    public void deleteItem(ToDoItem item) {
        localAccessor.deleteItem(item);
        if (!application.isOffline()) {
            remoteAccessor.deleteItem(item);
        }
    }

    @Override
    public ToDoItem getSelectedItem(int itemPosition) {
        return null;
    }

    @Override
    public ToDoItem getSelectedItem(long itemId) {
        return null;
    }

    @Override
    public void close() {

    }
}
