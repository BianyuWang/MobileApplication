package com.example.bianyuprojectandroidapp.JsonData;

import android.content.Context;

import com.example.bianyuprojectandroidapp.UserEntity.History;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
//future use
public class JSONreader {

    BufferedReader reader = null;

    public void  readData(String fileName,Context context)
    {


        try {
            FileInputStream inputStream = context.openFileInput(fileName);
            ByteArrayOutputStream outputStream = new  ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = -1;
            while ((len = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }
            inputStream.close();
            outputStream.close();
            String s = outputStream.toString();



        //    reader = new BufferedReader(new FileReader(fileName));
            Gson gson = new Gson();
            ArrayList<History> historyList= (ArrayList<History>) gson.fromJson(s,
                    new TypeToken<ArrayList<String>>() {
                    }.getType());

            System.out.println(historyList.get(0).getListOfQuestion());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
