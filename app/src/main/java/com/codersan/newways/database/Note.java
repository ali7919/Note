package com.codersan.newways.database;


import androidx.databinding.Bindable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Observable;

@Entity(tableName = "note_tb")
public class Note  {

    @PrimaryKey(autoGenerate = true)
    private int Id;

    private String name;

    private String des;

    private int priority;


    public Note(String name, String des, int priority) {
        this.name = name;
        this.des = des;
        this.priority = priority;
    }


    public void setId(int id) {
        Id = id;
    }

    public int getId() {
        return Id;
    }

    public String getName() {
        return name;
    }

    public String getDes() {
        return des;
    }

    public int getPriority() {
        return priority;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
