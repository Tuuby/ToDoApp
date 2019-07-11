package com.example.netlab.todotest.Accessors.remote;

import android.os.AsyncTask;
import android.util.Log;
import com.example.netlab.todotest.AbstractActivityProvider;
import com.example.netlab.todotest.Accessors.ToDoListAccessor;
import com.example.netlab.todotest.Adapters.FavouriteSortableArrayAdapter;
import com.example.netlab.todotest.ToDoItem;
import java.util.List;

public class ResteasyToDoItemAccessorImpl extends AbstractActivityProvider implements ToDoListAccessor {
    private ResteasyToDoItemCRUDAccessor crudAccessor;

    public ResteasyToDoItemAccessorImpl(String baseUrl) {
        crudAccessor = new ResteasyToDoItemCRUDAccessor(baseUrl);
    }

    @Override
    public void addItem(final ToDoItem item) {
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    crudAccessor.createItem(item);
                } catch (Exception e) {
                    Log.d("ResteasyAccessor", "createItem network failed");
                }
                return null;
            }
        }.execute();
    }

    @Override
    public List<ToDoItem> readAllItems() {
        return crudAccessor.readAllItems();
    }

    @Override
    public FavouriteSortableArrayAdapter getAdapter() {
        return null;
    }

    @Override
    public void updateItem(final ToDoItem item) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    crudAccessor.updateItem(item);
                } catch (Exception e) {
                    Log.d("ResteasyAccessor", "updateItem network failed");
                }
                return null;
            }
        }.execute();
    }

    @Override
    public void deleteItem(final ToDoItem item) {
        new AsyncTask<Void, Void, Void>()  {

            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    crudAccessor.deleteItem(item.getId());
                } catch (Exception e) {
                    Log.d("ResteasyAccessor", "deleteItem network failed");
                }
                return null;
            }
        }.execute();
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
