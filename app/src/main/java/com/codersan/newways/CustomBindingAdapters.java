package com.codersan.newways;

import android.widget.TextView;

import androidx.databinding.BindingAdapter;

public class CustomBindingAdapters {


    @BindingAdapter("listsize0")
    public static void setsize(TextView tv,int size){
        tv.setText("size = "+size);
    }

}
