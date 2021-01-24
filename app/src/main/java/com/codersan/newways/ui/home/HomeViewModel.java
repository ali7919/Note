package com.codersan.newways.ui.home;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.fragment.NavHostFragment;

import com.codersan.newways.R;
import com.codersan.newways.database.Note;
import com.codersan.newways.database.Repository;

import java.util.List;

public class HomeViewModel extends AndroidViewModel {

    //viewmodel shared between home fragment and its child(add edit fragment)

    private Repository repository;
    private LiveData<List<Note>> all_notes;

    private Note on_edit_Note;
    private MutableLiveData<Integer> size=new MutableLiveData<>();

    private RVA rva;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        repository = Repository.getInstance(application);
        all_notes = repository.getAll_notes();
        setRva();

    }


    public void insert(Note note) {
        repository.insert(note);
    }

    public void update(Note note) {
        repository.update(note);
    }

    public void delete(Note note) {
        repository.delete(note);
    }

    public void deleteall() {
        repository.delete_all();
    }

    public LiveData<List<Note>> getAll_notes() {
        return all_notes;
    }

    public void setOn_edit_Note(Note on_edit_Note) {
        this.on_edit_Note=(on_edit_Note);
    }


    public Note getOn_edit_Note() {
        return on_edit_Note;
    }

    public MutableLiveData<Integer> getSize() {
        return size;
    }

    public void setSize() {
        if (all_notes==null || all_notes.getValue()==null)return;
        this.size.setValue(all_notes.getValue().size());
        Log.d("ddd",size.getValue()+"");

    }

    public RVA getRva() {
        return rva;
    }

    public void setRva() {
        rva = new RVA(() -> {setSize();});



    }
}
