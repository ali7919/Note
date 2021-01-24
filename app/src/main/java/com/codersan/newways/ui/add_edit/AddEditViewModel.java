package com.codersan.newways.ui.add_edit;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.codersan.newways.database.Note;

public class AddEditViewModel extends AndroidViewModel {
    private boolean editing = true;
    private int priority=1;
    private Note note;




    public AddEditViewModel(@NonNull Application application) {
        super(application);
    }

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        if (note==null){
            editing=false;
        }
        this.note = (note==null)?new Note("","",1):note;
        priority=(this.note.getPriority());
    }

    public int getPriority() {
        return priority;
    }


    public boolean isEditing() {
        return editing;
    }


}
