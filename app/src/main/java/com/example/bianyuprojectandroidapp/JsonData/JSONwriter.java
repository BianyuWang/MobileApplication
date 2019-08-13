package com.example.bianyuprojectandroidapp.JsonData;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;

import com.example.bianyuprojectandroidapp.UserEntity.History;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.io.ObjectOutputStream;

//future use
public class JSONwriter {

    private FileOutputStream fileOutputStream;


    public String processJSONData(Context context, History history)
    {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
       return gson.toJson(history);
    }

    public void save(String filename, String JSONhistory,
                            Context context) {
        try {
            fileOutputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);

            ObjectOutputStream oos = new ObjectOutputStream(fileOutputStream);
            String str =new String(JSONhistory.getBytes("UTF-8"),"UTF-8");
            oos.writeUTF(str);
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
