package com.example.bianyuprojectandroidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.bianyuprojectandroidapp.Authentication.LoginActivity;
import com.example.bianyuprojectandroidapp.UserEntity.User;
import com.example.bianyuprojectandroidapp.Utile.Utile;
// empty activity to check if user check the remember me button
public class MainEmptyActivity extends AppCompatActivity {
    ImageView imageView;
    User currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_empty);
        imageView=findViewById(R.id.imageView);

       //use Glide to add gif into layout
        Glide.with(this).load(R.drawable.loading).into(imageView);
        Intent activityIntent;
        //check if user check remember me
        String rem=Utile.checkRember(this);

        //if remember me checked. go to main activity
        if (rem.equals("yes")) {
           currentUser=Utile.getCurrentUser(this);
           Bundle bundle=new Bundle();
           bundle.putSerializable("currentUser",currentUser);
           activityIntent = new Intent(this, MainActivity.class);
           activityIntent.putExtra("currentUserIntent",bundle);

      } else {
            // if not, go to login window
           activityIntent = new Intent(this, LoginActivity.class);

      }

       startActivity(activityIntent);
       finish();

    }
}
