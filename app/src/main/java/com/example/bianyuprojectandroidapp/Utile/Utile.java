package com.example.bianyuprojectandroidapp.Utile;

import android.content.Context;
import android.content.SharedPreferences;


import com.example.bianyuprojectandroidapp.UserEntity.User;

import static android.content.Context.MODE_PRIVATE;
// helper class to restore information using SharedPreferences
public class Utile {

    static SharedPreferences sharedPre;
   private static  String rememberme,HS,LT,gender,username;

    public static void checkUser(Context context) {
       sharedPre=context.getSharedPreferences("config", MODE_PRIVATE);
         rememberme=sharedPre.getString("rememberme","");
         username=sharedPre.getString("username", "");
         HS=sharedPre.getString("highestScore","");
         LT=sharedPre.getString("longestTime","");
         gender=sharedPre.getString("gender", "");

  }

    public static String checkRember(Context context){
        checkUser( context);

        return rememberme;

    }



public static   User getCurrentUser(Context context)

{     checkUser(context);

    User currentUser=new User(username);



    if (gender=="Girl") {
        currentUser.setGender(User.Gender.Girl);}
    else{
        currentUser.setGender(User.Gender.Boy);
    }
return currentUser;
}
    public static void saveLoginInfo(Context context, String username,  User.Gender gender,String rem){

         sharedPre=context.getSharedPreferences("config",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPre.edit();
        editor.putString("username", username);
      editor.putString("gender",String.valueOf(gender));
        editor.putString("rememberme", rem);

        editor.commit();
    }
    public static void saveLoginInfo(Context context, int HS){

        sharedPre=context.getSharedPreferences("config",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPre.edit();
        editor.putString("highestScore", String.valueOf(HS));
        editor.commit();
    }

    }


