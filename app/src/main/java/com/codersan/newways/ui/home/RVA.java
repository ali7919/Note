package com.codersan.newways.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.codersan.newways.R;
import com.codersan.newways.database.Note;
import com.codersan.newways.databinding.EachNoteBinding;

import java.util.List;

public class RVA extends ListAdapter<Note,RVA.NotesHolder> {
    private OnEditListenner OnEditListenner;
    private HomeFragment.ListUpdateListenner listUpdateListenner;

    public RVA(HomeFragment.ListUpdateListenner listUpdateListenner) {
        super(new DiffUtil.ItemCallback<Note>() {
            @Override
            public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
                return oldItem.getId()==newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
                return oldItem.getName().equals(newItem.getName()) && oldItem.getDes().equals(newItem.getDes()) && oldItem.getPriority()==newItem.getPriority();
            }
        });
        this.listUpdateListenner=listUpdateListenner;
    }


    @Override
    public void onCurrentListChanged(@NonNull List<Note> previousList, @NonNull List<Note> currentList) {
        super.onCurrentListChanged(previousList, currentList);
        listUpdateListenner.ListUpdated();
    }

    public Note note_at(int i){
        return getItem(i);
    }

    @NonNull
    @Override
    public NotesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        EachNoteBinding binding=EachNoteBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new NotesHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesHolder holder, int position) {

        holder.bind(position);
    }


    class NotesHolder extends RecyclerView.ViewHolder{

        private EachNoteBinding binding;


        public NotesHolder(EachNoteBinding binding) {
            super(binding.getRoot());

            this.binding=binding;
            itemView.setOnClickListener(view -> {
                if (OnEditListenner !=null && getAdapterPosition()!=-1){
                    OnEditListenner.clicked(getItem(getAdapterPosition()));
                }
            });
        }
        public void bind(int position){
            Note note=getItem(position);

            binding.setNote(note);


        }
    }

    public interface OnEditListenner {
        void clicked(Note note);
    }

    public void setOnEditListenner(OnEditListenner onEditListenner) {
        this.OnEditListenner = onEditListenner;
    }
}
