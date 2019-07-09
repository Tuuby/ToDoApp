package com.example.netlab.todotest.Accessors;

import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import com.example.netlab.todotest.AbstractActivityProvider;
import com.example.netlab.todotest.R;
import com.example.netlab.todotest.ToDoItem;
import com.example.netlab.todotest.TodoListViewAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ResourceToDoListAccessorImpl extends AbstractActivityProvider implements ToDoListAccessor {

    private List<ToDoItem> todos;
    private ArrayAdapter<ToDoItem> adapter;

    @Override
    public void addItem(ToDoItem item) {
        todos.add(item);
        sortList();
        adapter.notifyDataSetChanged();
    }

    @Override
    public List<ToDoItem> readAllItems() {
        String[] fuggos = getActivity().getResources().getStringArray(R.array.item_list);

        todos = new ArrayList<>();

        for (String fuggo : fuggos) {
            todos.add(new ToDoItem(fuggo, "", 1));
        }
        return todos;
    }

    @Override
    public ListAdapter getAdapter() {
        todos = readAllItems();

        sortList();

        adapter = TodoListViewAdapter.createDataItemArrayAdapter(getActivity(), todos, this);

        return adapter;
    }

    @Override
    public void updateItem(ToDoItem item) {
        lookupItem(item.getId()).updateFrom(item);
        sortList();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void deleteItem(ToDoItem item) {
        todos.remove(lookupItem(item.getId()));
        sortList();
        adapter.notifyDataSetChanged();
    }

    private ToDoItem lookupItem(long itemId) {
        for (ToDoItem current : this.todos) {
            if (current.getId() == itemId) {
                return current;
            }
        }
        return null;
    }

    private void sortList() {
        Collections.sort(todos);
    }

    @Override
    public ToDoItem getSelectedItem(int itemPosition) {
        return todos.get(itemPosition);
    }

    @Override
    public ToDoItem getSelectedItem(long itemId) {
        return lookupItem(itemId);
    }

    @Override
    public void close() {

    }
}
