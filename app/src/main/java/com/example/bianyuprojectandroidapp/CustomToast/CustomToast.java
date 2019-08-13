package com.example.bianyuprojectandroidapp.CustomToast;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.TextView;
import android.widget.Toast;

import com.example.bianyuprojectandroidapp.R;


public class CustomToast  {

    Toast toast;

    private Context context;
    public View view;


    public CustomToast(Context context) {
        this.context = context;
        this.toast = new Toast(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.view = inflater.inflate(R.layout.colored_toast_layout, null);
        toast.setView(view);

    }

    public void setText(String text){
        if(view==null)
            return;
        ((TextView) view.findViewById(R.id.toast_msg)).setText(text);
    }

    public Toast getToast(){
        return toast;
    }

    public View getView(){
        return view;
    }
}
