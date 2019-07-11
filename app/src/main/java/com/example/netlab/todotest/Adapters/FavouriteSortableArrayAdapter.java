package com.example.netlab.todotest.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.core.content.ContextCompat;
import com.example.netlab.todotest.Accessors.ToDoListAccessor;
import com.example.netlab.todotest.R;
import com.example.netlab.todotest.ToDoApplication;
import com.example.netlab.todotest.ToDoItem;

import java.util.Comparator;
import java.util.List;


public class FavouriteSortableArrayAdapter extends ArrayAdapter<ToDoItem> {
    private boolean favouriteFirst;

    private Comparator<ToDoItem> comparator = new Comparator<ToDoItem>() {
        @Override
        public int compare(ToDoItem toDoItem1, ToDoItem toDoItem2) {
            int result = (toDoItem1.isDone() ? 1 : 0) - (toDoItem2.isDone() ? 1 : 0);

            if (result == 0)
                if (favouriteFirst) {
                    result = (toDoItem2.isHighPriority() ? 1 : 0) - (toDoItem1.isHighPriority() ? 1 : 0);
                    if (result == 0)
                        result = Long.compare(toDoItem1.getDeadline(), toDoItem2.getDeadline());
                } else {
                    result = Long.compare(toDoItem1.getDeadline(), toDoItem2.getDeadline());
                    if (result == 0)
                        result = (toDoItem2.isHighPriority() ? 1 : 0) - (toDoItem1.isHighPriority() ? 1 : 0);
                }
            return result;
        }
    };

    FavouriteSortableArrayAdapter(Context context, int resource, List<ToDoItem> objects) {
        super(context, resource, objects);
    }

    public boolean isFavouriteFirst() {
        return favouriteFirst;
    }

    public void setFavouriteFirst(boolean favouriteFirst) {
        this.favouriteFirst = favouriteFirst;
    }

    @Override
    public void notifyDataSetChanged() {
        this.setNotifyOnChange(false);
        this.sort(comparator);
        this.setNotifyOnChange(true);
        super.notifyDataSetChanged();
    }

    public static FavouriteSortableArrayAdapter create(final Activity aContext, final List<ToDoItem> aItems, final ToDoListAccessor accessor) {
        return new FavouriteSortableArrayAdapter(aContext, R.layout.list_item_layout, aItems) {
            @Override
            public View getView(int position, View listItemView, ViewGroup parent) {
                View layout = listItemView == null ? aContext.getLayoutInflater().inflate(
                        R.layout.list_item_layout, parent, false) : listItemView;

                final ToDoItem todo = aItems.get(position);

                final TextView todoName = layout.findViewById(R.id.todo_name);
                final CheckBox todoDone = layout.findViewById(R.id.todo_done);
                final ImageView todoFavourite = layout.findViewById(R.id.todo_favourite);
                final TextView todoDeadline = layout.findViewById(R.id.todo_deadline);

                todoName.setText(todo.getName());
                todoDeadline.setText(todo.deadlineToString());
                if (!todo.isDone()) {
                    if (todo.getDeadline() < System.currentTimeMillis()) {
                        todoDeadline.setTextColor(Color.RED);
                    } else {
                        todoDeadline.setTextColor(ContextCompat.getColor(aContext, R.color.defaultDateColor));
                    }

                }
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

                todoDone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        todo.setDone(todoDone.isChecked());
                        ((ToDoApplication) aContext.getApplication()).getSharedAccessor().updateItem(todo);
                    }
                });

                return layout;
            }
        };
    }
}
