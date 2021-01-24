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

import com.codersan.newways.MainActivity;
import com.codersan.newways.R;
import com.codersan.newways.database.Note;
import com.codersan.newways.databinding.FragmentAddeditBinding;
import com.codersan.newways.ui.home.HomeViewModel;


public class AddEditFragment extends Fragment {

    private HomeViewModel parent_vm;
    private AddEditViewModel vm;
    private FragmentAddeditBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        //get the right owner for the VM
        NavBackStackEntry pre = NavHostFragment.findNavController(this).getBackStackEntry(R.id.navigation_home);
        parent_vm = new ViewModelProvider(pre).get(HomeViewModel.class);
        vm = new ViewModelProvider(this).get(AddEditViewModel.class);
        vm.setNote(parent_vm.getOn_edit_Note());
        decoration();


    }

    public void decoration() {
        ((MainActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_close_24);// set drawable icon
        if (vm.isEditing()) {
            ((MainActivity) getActivity()).getSupportActionBar().setTitle("edit note");
        } else {
            ((MainActivity) getActivity()).getSupportActionBar().setTitle("add note");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //init binding
        binding = FragmentAddeditBinding.inflate(inflater, container, false);
        binding.setVm(vm);

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
                //save
                Note note = vm.getNote();
                note.setPriority(binding.np.getValue());

                if (vm.isEditing())
                    parent_vm.update(note);
                else
                    parent_vm.insert(note);

                getActivity().onBackPressed();
                return true;
            default:
                getActivity().onBackPressed();
                return super.onOptionsItemSelected(item);


        }
    }

    @Override
    public void onStop() {
        super.onStop();
        parent_vm.setOn_edit_Note(null);

    }


}