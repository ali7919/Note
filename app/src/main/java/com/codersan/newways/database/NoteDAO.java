package com.codersan.newways.database;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDAO {

    @Insert
    void insert(Note note);

    @Delete
    void delete(Note note);

    @Update
    void update(Note note);

    @Query("DELETE FROM note_tb")
    void delete_all();

    @Query("SELECT * FROM note_tb ORDER BY priority ASC")
    LiveData<List<Note>> get_all();
}
