package com.codersan.newways.ui.add_edit;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.NumberPicker;

import com.codersan.newways.MainActivity;
import com.codersan.newways.R;
import com.codersan.newways.database.Note;
import com.codersan.newways.databinding.FragmentAddeditBinding;
import com.codersan.newways.ui.home.NoteViewModel;


public class AddEditFragment extends Fragment {


    private NoteViewModel noteVM;
    private FragmentAddeditBinding binding;
    private boolean editing = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);


        //get the right owner for the VM
        NavBackStackEntry pre = NavHostFragment.findNavController(this).getBackStackEntry(R.id.navigation_home);
        noteVM = new ViewModelProvider(pre).get(NoteViewModel.class);


        decoration();


    }

    public void decoration() {
        ((MainActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_close_24);// set drawable icon

        if (noteVM.getOn_edit_Note().getValue() == null) {
            ((MainActivity) getActivity()).getSupportActionBar().setTitle("add note");
        } else {
            ((MainActivity) getActivity()).getSupportActionBar().setTitle("edit note");
            editing = true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //init binding
        binding = FragmentAddeditBinding.inflate(inflater, container, false);

        //init number picker
        binding.np.setMinValue(1);
        binding.np.setMaxValue(10);
        if (noteVM.getOn_edit_Note().getValue() != null)
            binding.np.setValue(noteVM.getOn_edit_Note().getValue().getPriority());
        else noteVM.setOn_edit_Note(new Note("", "", 1));

        //set binding value (two way binding)
        binding.setNote(noteVM.getOn_edit_Note().getValue());

        return binding.getRoot();
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.savemenu, menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save:
                save();
                return true;
            default:
                getActivity().onBackPressed();
                return super.onOptionsItemSelected(item);


        }
    }

    @Override
    public void onStop() {
        super.onStop();
        noteVM.setOn_edit_Note(null);

    }

    private void save() {

        //done
        Note note = noteVM.getOn_edit_Note().getValue();
        note.setPriority(binding.np.getValue());

        if (editing)
            noteVM.update(note);
        else
            noteVM.insert(note);

        getActivity().onBackPressed();


    }
}