package com.example.bianyuprojectandroidapp.CustomToast;

import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.bianyuprojectandroidapp.R;


public class ToastBuilder {

    private static final int RED = 0xfff44336;
    private static final int BLUE = 0xff2195f3;
    private static final int ORANGE = 0xffffc107;
    private static final int GREEN = 0xff4caf50;
   public static final int LENGTH_LONG  = 1;
    public static final int LENGTH_SHORT = 0;

    public static Toast alert(Context context, String text, int duration){

        CustomToast toastCustom = new CustomToast(context);
        toastCustom.setText(text);
        toastCustom.getToast().setDuration(duration);
        toastCustom.getToast().setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 360, -500);

        toastCustom.getView().setBackgroundColor(RED);
        return toastCustom.getToast();
    }


    public static Toast info(Context context, String text, int duration){

        CustomToast toastCustom = new CustomToast(context);
        toastCustom.setText(text);
        toastCustom.getToast().setDuration(duration);
        toastCustom.getToast().setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 50);

        toastCustom.getView().setBackgroundColor(BLUE);
        return toastCustom.getToast();
    }


    public static Toast warning(Context context, String text, int duration){

        CustomToast toastCustom = new CustomToast(context);
        toastCustom.setText(text);
        toastCustom.getToast().setDuration(duration);
        toastCustom.getToast().setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 100, 0);

        toastCustom.getView().setBackgroundColor(ORANGE);
        return toastCustom.getToast();
    }


    public static Toast success(Context context, String text, int duration){

        CustomToast toastCustom = new CustomToast(context);

        toastCustom.setText(text);
        toastCustom.getToast().setDuration(duration);
        toastCustom.getToast().setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 360, -500);

        toastCustom.getView().setBackgroundColor(GREEN);
        return toastCustom.getToast();
    }


    public static Toast imageToast(Context context, int imageRes, int duration){

        Toast toast = new Toast(context);

        LinearLayout layout = new LinearLayout(context);
  layout.setLayoutParams(new LinearLayout.LayoutParams(800, 800));


        ImageView iv = new ImageView(context);

        iv.setLayoutParams(layout.getLayoutParams());
        iv.setScaleType(ImageView.ScaleType.FIT_CENTER);

        iv.requestLayout();


        iv.setImageResource(imageRes);


        layout.addView(iv);


        toast.setView(layout);
        toast.setDuration(duration);

        toast.setGravity(Gravity.TOP| Gravity.CENTER, 0, 500);
        return toast;
    }
}
