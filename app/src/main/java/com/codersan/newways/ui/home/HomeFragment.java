package com.codersan.newways.ui.home;

import android.os.Bundle;
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

public class HomeFragment extends Fragment {


    private FragmentHomeBinding binding;
    private HomeViewModel vm;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        //get the right owner for the VM
        NavBackStackEntry thisone = NavHostFragment.findNavController(this).getBackStackEntry(R.id.navigation_home);
        vm = new ViewModelProvider(thisone).get(HomeViewModel.class);
        setHasOptionsMenu(true);
        binding=FragmentHomeBinding.inflate(inflater,container,false);
        binding.setLifecycleOwner(thisone);
        binding.setVm(vm);

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        vm.setSize();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //init rv
        vm.getRva().setOnEditListenner(note -> {
            vm.setOn_edit_Note(note);
            NavHostFragment.findNavController(this).navigate(R.id.action_navigation_home_to_blankFragment);
        });

        RecyclerView rv = binding.rv;
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(vm.getRva());


        //swipe to delete
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.DOWN | ItemTouchHelper.UP,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                vm.delete(vm.getRva().note_at(viewHolder.getAdapterPosition()));
                Toast.makeText(getActivity(), "deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(rv);


        //observe notes
        vm.getAll_notes().observe(getActivity(), notes -> {
            vm.getRva().submitList(notes);
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
                vm.deleteall();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public interface ListUpdateListenner{
        void ListUpdated();
    }
}