package com.example.bianyuprojectandroidapp;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.example.bianyuprojectandroidapp.JsonData.JSONwriter;
import com.example.bianyuprojectandroidapp.UserEntity.History;
import com.example.bianyuprojectandroidapp.UserEntity.Question;
import com.example.bianyuprojectandroidapp.UserEntity.User;

import java.util.ArrayList;

public class CheckHistory extends AppCompatActivity {
TextView userName_tv;
User currentUser;
    History todayHistory;
    LinearLayout parentInfoLinearLayout;
    ListView advanced_listView;
    CellController_BaseAdapter cellController_base_adapter;
    RadioGroup option_rg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_check_history);
          initialize();
         getMyIntent();
          setData();
       // test();
    }

    private void setData() {
        userName_tv.setText(currentUser.getName()+"'s records "+todayHistory.myPercentage());

       getAllQuestion();

  }

  // get today's all question
    private void getAllQuestion(){
//if no question list , remove listview add text view and show message
        if(todayHistory.getListOfQuestion().size()==0){
            TextView info= new TextView(this);
            info.setText("Life is Short, Play More!");
            info.setTextSize(60);

            parentInfoLinearLayout.removeView(advanced_listView);
            parentInfoLinearLayout.addView(info);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) info.getLayoutParams();
            params.gravity= Gravity.CENTER;

            info.setLayoutParams(params);
        }

   //  set adapter to listview and show all the question information
       else {

            cellController_base_adapter = new CellController_BaseAdapter(this, todayHistory.getListOfQuestion());
            advanced_listView.setAdapter(cellController_base_adapter);
        }
    }

    private void getMyIntent() {
      Intent intentHistory=getIntent();
      Bundle bundleHistory=  intentHistory.getBundleExtra("historyIntent");
      currentUser=(User)bundleHistory.getSerializable("userBundle");
      todayHistory=(History)bundleHistory.getSerializable("historyBundle");
    }

    private void initialize() {
        userName_tv=findViewById(R.id.UserName_tv);
        advanced_listView=findViewById(R.id.Advanced_listView);
        option_rg=findViewById(R.id.Option_rg);
       parentInfoLinearLayout=findViewById(R.id.MessageBox_ll);

    }
// for future use
    private void test(){
     String file = "Leo_history_json.json";
    JSONwriter jw=new JSONwriter();
    jw.processJSONData(this,todayHistory);
    jw.save(file,jw.processJSONData(this,todayHistory),this);


    }
    // for future use
    public void copyDbFile(View view) {
      test();
    //    JSONreader jReader=new JSONreader();
      //  jReader.readData("Leo_history_json.json",this);
    }


//reload list view display selon the radio button chosen
    public void reloadListView(View view) {
        int optionListview= option_rg.getCheckedRadioButtonId();
        switch (optionListview){
            case R.id.Right_rb:

                CellController_BaseAdapter  rightAnswerAdapter = new CellController_BaseAdapter(this,  todayHistory.getRightAnswerQuestion());
                   advanced_listView.setAdapter(rightAnswerAdapter);
break;
            case R.id.Wrong_rb:
                CellController_BaseAdapter wrongAnswerAdapter = new CellController_BaseAdapter(this,  todayHistory.getWrongAnswerQuestion());
                advanced_listView.setAdapter(wrongAnswerAdapter);
                break;
                case R.id.All_rb:
                    CellController_BaseAdapter  cellController_base_adapter = new CellController_BaseAdapter(this, todayHistory.getListOfQuestion());
                    advanced_listView.setAdapter(cellController_base_adapter);
break;
            case  R.id.SortA_rb:
                todayHistory.getRightAnswerFirstQuestion();

                CellController_BaseAdapter rightAnswerFirstAdapter = new CellController_BaseAdapter(this,    todayHistory.getListOfQuestion());
                advanced_listView.setAdapter(rightAnswerFirstAdapter);
break;

            case R.id.SortD_rb:

                todayHistory.getWrongAnswerFirstQuestion();

                CellController_BaseAdapter WrongAnswerFirstAdapter = new CellController_BaseAdapter(this,    todayHistory.getListOfQuestion());
                advanced_listView.setAdapter(WrongAnswerFirstAdapter);
break;
        }

    }

    //go back button

    public void goBack(View view) {

        finish();
    }
}
