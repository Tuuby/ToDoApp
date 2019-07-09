package com.example.netlab.todotest.Activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.*;
import com.example.netlab.todotest.R;
import com.example.netlab.todotest.ToDoItem;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class DetailViewActivity extends Activity {

    public static final int RESULT_DELETE = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);

        final ToDoItem todo = (ToDoItem) getIntent().getSerializableExtra("selectedTodo");

        final Calendar todoCalendar = new GregorianCalendar();
        todoCalendar.setTimeInMillis(todo.getDeadline());

        final TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                todoCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                todoCalendar.set(Calendar.MINUTE, minute);

                todo.setDeadline(todoCalendar.getTimeInMillis());
            }
        }, todoCalendar.get(Calendar.HOUR_OF_DAY), todoCalendar.get(Calendar.MINUTE), true);

        final DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                todoCalendar.set(year, month, dayOfMonth);
                timePickerDialog.show();
            }
        }, todoCalendar.get(Calendar.YEAR), todoCalendar.get(Calendar.MONTH), todoCalendar.get(Calendar.DAY_OF_MONTH));

        final Button dateTimeButton = findViewById(R.id.dateTimeButton);
        final Button confirmButton = findViewById(R.id.confirmButton);
        final Button deleteButton = findViewById(R.id.deleteButton);

        final CheckBox isDone = findViewById(R.id.isDone);
        final Switch isFavourite = findViewById(R.id.isFavourite);
        final EditText todo_name = findViewById(R.id.todo_name);
        final EditText todo_description = findViewById(R.id.todo_description);

        isDone.setChecked(todo.isDone());
        isFavourite.setChecked(todo.isHighPriority());
        todo_name.setText(todo.getName());
        todo_description.setText(todo.getDescription());

        dateTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                todo.setDone(isDone.isChecked());
                todo.setHighPriority(isFavourite.isChecked());
                todo.setName(todo_name.getText().toString());
                todo.setDescription(todo_description.getText().toString());

                Intent i = new Intent();
                i.putExtra("selectedTodo", todo);
                setResult(RESULT_OK, i);
                finish();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.putExtra("selectedTodo", todo);
                setResult(RESULT_DELETE, i);
                finish();
            }
        });
    }

}
