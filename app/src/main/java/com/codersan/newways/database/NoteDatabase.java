package com.codersan.newways.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;


@Database(entities = {Note.class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase instance;

    public abstract NoteDAO noteDAO();


    //singleton getinstace
    public static synchronized NoteDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), NoteDatabase.class, "note_db").
                    fallbackToDestructiveMigration().
                    addCallback(new FirstCallBack()).
                    build();
        }
        return instance;
    }


    //callback for first time
    static class FirstCallBack extends Callback {

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new InsertFirstTimeAsycTask(instance.noteDAO()).execute();

        }
    }


    //special first time insert procedure
    static class InsertFirstTimeAsycTask extends AsyncTask<Note, Void, Void> {

        private NoteDAO dao;

        public InsertFirstTimeAsycTask(NoteDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            dao.insert(new Note("name1", "des1", 1));
            dao.insert(new Note("name2", "des2", 2));
            return null;
        }
    }


}
