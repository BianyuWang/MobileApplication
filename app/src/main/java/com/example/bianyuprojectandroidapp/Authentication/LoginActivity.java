package com.example.bianyuprojectandroidapp.Authentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.bianyuprojectandroidapp.CustomToast.ToastBuilder;
import com.example.bianyuprojectandroidapp.MainActivity;
import com.example.bianyuprojectandroidapp.R;
import com.example.bianyuprojectandroidapp.UserEntity.User;
import com.example.bianyuprojectandroidapp.Utile.Utile;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView imageView;
    Button login_btn;
    RadioGroup gender_rg;
    CheckBox rememberMe_cb;
    EditText username_et;
    String username;
   User.Gender gender;
    User currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        imageView=findViewById(R.id.Login_iv);
        Glide.with(this).load(R.drawable.login).into(imageView);

        initialize();
    }

    private void initialize() {
        imageView=findViewById(R.id.Login_iv);
        Glide.with(this).load(R.drawable.login).into(imageView);
        login_btn=findViewById(R.id.Login_BTN);
        login_btn.setOnClickListener(this);
        gender_rg=findViewById(R.id.Gender_RG);
        rememberMe_cb=findViewById(R.id.RememberMe_CB);
        username_et=findViewById(R.id.Username_ET);
    }


    @Override
    public void onClick(View view) {
         username=username_et.getText().toString();
        if(username.length()==0)
        {

            ToastBuilder.info(this, "Please Enter an User Name" , ToastBuilder.LENGTH_SHORT).show();

        }
else {

            initializeUser();

            Bundle bundle = new Bundle();
            bundle.putSerializable("currentUser", currentUser);
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("currentUserIntent", bundle);
            startActivity(intent);
        } }

    private void initializeUser() {
        currentUser = new User(username);

        if (gender_rg.getCheckedRadioButtonId()==R.id.Gender_F) {
            currentUser.setGender(User.Gender.Girl);}
        else{
            currentUser.setGender(User.Gender.Boy);
        }

        if( rememberMe_cb.isChecked())
            Utile.saveLoginInfo(this, currentUser.getName(),currentUser.getGender(),"yes");
        else
         Utile.saveLoginInfo(this, currentUser.getName(),currentUser.getGender(),"no");
 }
}
