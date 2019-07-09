package com.example.netlab.todotest.Accessors.room;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.netlab.todotest.ToDoItem;

@Database(entities = {ToDoItem.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase sInstance;

    public static AppDatabase getInstance(final Context aContext) {
        if (sInstance == null) {
            sInstance = Room.databaseBuilder(
                    aContext.getApplicationContext(),
                    AppDatabase.class,
                    "todoitem-database"
            ).build();
        }
        return sInstance;
    }

    public abstract ToDoItemDao toDoItemDao();
}