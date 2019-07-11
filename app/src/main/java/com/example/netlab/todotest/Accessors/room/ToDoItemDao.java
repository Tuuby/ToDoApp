package com.example.netlab.todotest.Accessors.room;

import androidx.room.*;
import com.example.netlab.todotest.ToDoItem;
import java.util.List;

@Dao
public interface ToDoItemDao {
    @Insert
    void insert(ToDoItem item);

    @Query("SELECT * FROM todoitem")
    List<ToDoItem> getAll();

    @Query("DELETE FROM todoitem")
    void deleteAllDataItems();

    @Update
    void update(ToDoItem item);

    @Delete
    void deleteItem(ToDoItem item);

    @Query("SELECT * FROM todoitem WHERE id = :id")
    ToDoItem getItemById(Long id);
}
