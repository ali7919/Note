package com.codersan.newways.database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.codersan.newways.database.Note;
import com.codersan.newways.database.NoteDAO;
import com.codersan.newways.database.NoteDatabase;

import java.util.List;

public class Repository {

    private NoteDAO dao;
    private LiveData<List<Note>> all_notes;

    private static Repository instance;

    public static Repository getInstance(Application application){
        if (instance==null){
            instance=new Repository(application);
        }
        return instance;

    }

    private Repository(Application application) {


        dao = NoteDatabase.getInstance(application).noteDAO();
        all_notes = dao.get_all();


    }


    public void insert(Note note) {
        new insertAsynctask(dao).execute(note);

    }

    public void update(Note note) {
        new updateAsynctask(dao).execute(note);

    }

    public void delete(Note note) {
        new deleteAsynctask(dao).execute(note);

    }

    public void delete_all() {
        new delete_allAsynctask(dao).execute();

    }

    public LiveData<List<Note>> getAll_notes() {
        return all_notes;
    }

    private static class insertAsynctask extends AsyncTask<Note, Void, Void> {
        private NoteDAO dao;

        public insertAsynctask(NoteDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            dao.insert(notes[0]);
            return null;
        }
    }

    private static class updateAsynctask extends AsyncTask<Note, Void, Void> {
        private NoteDAO dao;

        public updateAsynctask(NoteDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            dao.update(notes[0]);
            return null;
        }
    }

    private static class deleteAsynctask extends AsyncTask<Note, Void, Void> {
        private NoteDAO dao;

        public deleteAsynctask(NoteDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            dao.delete(notes[0]);
            return null;
        }
    }

    private static class delete_allAsynctask extends AsyncTask<Void, Void, Void> {
        private NoteDAO dao;

        public delete_allAsynctask(NoteDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.delete_all();
            return null;
        }
    }


}
