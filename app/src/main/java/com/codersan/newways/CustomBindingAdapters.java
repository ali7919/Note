package com.codersan.newways;

import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

public class CustomBindingAdapters {


    @BindingAdapter("listsize0")
    public static void setsize(TextView tv,int size){
        tv.setText("size = "+size);
    }

    @BindingAdapter("custom_np")
    public static void init_np(NumberPicker np,int value){
        np.setMinValue(1);
        np.setMaxValue(10);
        np.setValue(value);
    }

}
