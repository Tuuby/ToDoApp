package com.example.netlab.todotest.Accessors;

import com.example.netlab.todotest.Adapters.FavouriteSortableArrayAdapter;
import com.example.netlab.todotest.ToDoItem;

import java.util.List;

public interface ToDoListAccessor {

    void addItem(ToDoItem item);

    List<ToDoItem> readAllItems();

    FavouriteSortableArrayAdapter getAdapter();

    void updateItem(ToDoItem item);

    void deleteItem(ToDoItem item);

    ToDoItem getSelectedItem(int itemPosition);

    ToDoItem getSelectedItem(long itemId);

    void close();
}
