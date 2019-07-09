package com.example.netlab.todotest.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.netlab.todotest.AbstractActivityProvider;
import com.example.netlab.todotest.Accessors.ToDoListAccessor;
import com.example.netlab.todotest.Accessors.remote.ResteasyToDoItemAccessorImpl;
import com.example.netlab.todotest.Accessors.room.RoomToDoListAccessorImpl;
import com.example.netlab.todotest.Accessors.shared.SharedToDoItemAccessor;
import com.example.netlab.todotest.R;
import com.example.netlab.todotest.ToDoApplication;
import com.example.netlab.todotest.ToDoItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class TodoActivity extends AppCompatActivity {

    public static final int CREATE_TODO = 1;
    public static final int EDIT_TODO = 2;

    private ToDoApplication application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);
        final FloatingActionButton addButton = findViewById(R.id.addButton);
        final Switch prioritySwitch = findViewById(R.id.prioritySwitch);
        final ListView todoList = findViewById(R.id.todoList);

        application = (ToDoApplication) getApplication();

        if (application.isOffline()) {
            Toast.makeText(this, "Keine Internetverbindung", Toast.LENGTH_LONG).show();
        }

        ((AbstractActivityProvider) application.getSharedAccessor()).setActivity(this);


        todoList.setAdapter(application.getSharedAccessor().getAdapter());

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToDoItem item = new ToDoItem();
                goToDetail(item, CREATE_TODO);
            }
        });

        todoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ToDoItem todo = (ToDoItem) todoList.getItemAtPosition(position);
                goToDetail(todo, EDIT_TODO);
            }
        });

    }

    private void goToDetail(ToDoItem todo, int request) {
        Intent i = new Intent(TodoActivity.this, DetailViewActivity.class);
        i.putExtra("selectedTodo", todo);
        startActivityForResult(i, request);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EDIT_TODO && resultCode == RESULT_OK && data != null) {
            ToDoItem item = (ToDoItem) data.getSerializableExtra("selectedTodo");
            application.getSharedAccessor().updateItem(item);
        }

        if (requestCode == CREATE_TODO && resultCode == RESULT_OK && data != null) {
            ToDoItem item = (ToDoItem) data.getSerializableExtra("selectedTodo");
            application.getSharedAccessor().addItem(item);
        }

        if (requestCode == EDIT_TODO && resultCode == DetailViewActivity.RESULT_DELETE && data != null) {
            ToDoItem item = (ToDoItem) data.getSerializableExtra("selectedTodo");
            application.getSharedAccessor().deleteItem(item);
        }
    }
}
