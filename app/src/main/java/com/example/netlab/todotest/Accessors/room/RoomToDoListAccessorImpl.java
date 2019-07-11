package com.example.netlab.todotest.Accessors.room;

import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.netlab.todotest.AbstractActivityProvider;
import com.example.netlab.todotest.Accessors.ToDoListAccessor;
import com.example.netlab.todotest.Adapters.FavouriteSortableArrayAdapter;
import com.example.netlab.todotest.ToDoItem;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class RoomToDoListAccessorImpl extends AbstractActivityProvider implements ToDoListAccessor {

    private ToDoItemDao mDAO;
    private AppDatabase mAppDB;
    private FavouriteSortableArrayAdapter adapter;

    @Override
    public void setActivity(AppCompatActivity activity) {
        super.setActivity(activity);
        init();
    }

    private void init() {
        mAppDB = AppDatabase.getInstance(getActivity());
        mDAO = mAppDB.toDoItemDao();
    }

    @Override
    public void addItem(final ToDoItem item) {
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                item.setId(mDAO.getAll().size());
                mDAO.insert(item);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                if (adapter != null) {
                    adapter.add(item);
                    adapter.notifyDataSetChanged();
                }
            }
        }.execute();
    }

    @Override
    public List<ToDoItem> readAllItems() {
        return mDAO.getAll();
    }

    @Override
    public FavouriteSortableArrayAdapter getAdapter() {
        final List<ToDoItem> todos = new ArrayList<>();

        adapter = FavouriteSortableArrayAdapter.create(getActivity(), todos, this);

        new AsyncTask<Void, Void, List<ToDoItem>>() {
            @Override
            protected List<ToDoItem> doInBackground(Void... voids) {
                return mDAO.getAll();
            }

            @Override
            protected void onPostExecute(List<ToDoItem> todos) {
                adapter.addAll(todos);
            }
        }.execute();

        return adapter;
    }

    @Override
    public void updateItem(final ToDoItem item) {
        new AsyncTask<Void, Void, ToDoItem>() {
            @Override
            protected ToDoItem doInBackground(Void... voids) {
                mDAO.update(item);
                return item;
            }

            @Override
            protected void onPostExecute(ToDoItem newItem) {
                adapter.getItem(adapter.getPosition(newItem)).updateFrom(newItem);
                adapter.notifyDataSetChanged();
            }
        }.execute();
    }

    @Override
    public void deleteItem(final ToDoItem item) {
        new AsyncTask<Void, Void, Void>()  {

            @Override
            protected Void doInBackground(Void... voids) {
                mDAO.deleteItem(item);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                if (adapter != null) {
                    adapter.remove(item);
                    adapter.notifyDataSetChanged();
                }
            }
        }.execute();

    }

    @Override
    public ToDoItem getSelectedItem(int itemPosition) {
        throw new UnsupportedOperationException("needs to be implemented!!!");
    }

    @Override
    public ToDoItem getSelectedItem(final long itemId) {
        try {
            return new AsyncTask<Void, Void, ToDoItem>() {
                @Override
                protected ToDoItem doInBackground(Void... voids) {
                    return mDAO.getItemById(itemId);
                }
            }.execute().get();
        } catch (ExecutionException | InterruptedException e) {
            Toast toast = new Toast(getActivity().getApplicationContext());
            toast.setText(e.getMessage());
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.show();
            return null;
        }
    }

    @Override
    public void close() {
        mAppDB.close();
    }
}
