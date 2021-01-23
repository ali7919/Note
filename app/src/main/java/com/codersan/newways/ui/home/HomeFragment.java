package com.codersan.newways.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codersan.newways.R;
import com.codersan.newways.databinding.FragmentHomeBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomeFragment extends Fragment {


    private FragmentHomeBinding binding;
    private NoteViewModel noteVM;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        //get the right owner for the VM
        NavBackStackEntry thisone = NavHostFragment.findNavController(this).getBackStackEntry(R.id.navigation_home);
        noteVM = new ViewModelProvider(thisone).get(NoteViewModel.class);

        setHasOptionsMenu(true);
        binding=FragmentHomeBinding.inflate(inflater,container,false);

        binding.setLifecycleOwner(thisone);

        binding.setVm(noteVM);

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        noteVM.setSize();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //init rv
        RVA rva = new RVA(() -> {noteVM.setSize();});
        rva.setOnEditListenner(note -> {
            noteVM.setOn_edit_Note(note);
            NavHostFragment.findNavController(this).navigate(R.id.action_navigation_home_to_blankFragment);
        });

        RecyclerView rv = binding.rv;
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(rva);


        //swipe to delete
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.DOWN | ItemTouchHelper.UP,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                noteVM.delete(rva.note_at(viewHolder.getAdapterPosition()));
                Toast.makeText(getActivity(), "deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(rv);


        //observe notes
        noteVM.getAll_notes().observe(getActivity(), notes -> {
            rva.submitList(notes);
            Log.d("aaaa","ch");
        });


        //init fb
        binding.fab.setOnClickListener(view2 -> {

            NavHostFragment.findNavController(this).navigate(R.id.action_navigation_home_to_blankFragment);



        });

    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.homemenu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.deleteall:
                noteVM.deleteall();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public interface ListUpdateListenner{
        void ListUpdated();
    }
}