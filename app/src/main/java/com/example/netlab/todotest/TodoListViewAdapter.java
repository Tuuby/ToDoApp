package com.example.netlab.todotest;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.netlab.todotest.Accessors.ToDoListAccessor;

import java.util.List;

public class TodoListViewAdapter {
    public static ArrayAdapter<ToDoItem> createDataItemArrayAdapter(final Activity aContext, final List<ToDoItem> aItems, final ToDoListAccessor accessor) {
        return new ArrayAdapter<ToDoItem>(aContext, R.layout.list_item_layout, aItems) {
            @Override
            public View getView(int position, View listItemView, ViewGroup parent) {
                View layout = listItemView == null ? aContext.getLayoutInflater().inflate(
                        R.layout.list_item_layout, parent, false) : listItemView;

                final ToDoItem todo = aItems.get(position);

                TextView todoName = layout.findViewById(R.id.todo_name);
                CheckBox todoDone = layout.findViewById(R.id.todo_done);
                final ImageView todoFavourite = layout.findViewById(R.id.todo_favourite);

                todoName.setText(todo.getName());
                todoDone.setChecked(todo.isDone());
                if (todo.isHighPriority())
                    todoFavourite.setImageResource(R.drawable.star_yellow);
                else
                    todoFavourite.setImageResource(R.drawable.star_grey);

                todoFavourite.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        todo.setHighPriority(!todo.isHighPriority());
                        ((ToDoApplication) aContext.getApplication()).getSharedAccessor().updateItem(todo);
                    }
                });

                todoDone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        todo.setDone(isChecked);
                        ((ToDoApplication) aContext.getApplication()).getSharedAccessor().updateItem(todo);
                    }
                });

                return layout;
            }
        };
    }
}

