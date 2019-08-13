package com.example.bianyuprojectandroidapp;
import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.bumptech.glide.util.Util;
import com.example.bianyuprojectandroidapp.Authentication.LoginActivity;
import com.example.bianyuprojectandroidapp.CustomToast.ToastBuilder;
import com.example.bianyuprojectandroidapp.UserEntity.History;
import com.example.bianyuprojectandroidapp.UserEntity.Question;
import com.example.bianyuprojectandroidapp.UserEntity.User;
import com.example.bianyuprojectandroidapp.Utile.Utile;
import java.text.SimpleDateFormat;
import java.util.Date;



public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    ImageView avatarUser_iv;
    TextView  userName_tv, high_score_tv, longestTime_tv, question_tv, timeRemainder_tv, answer_et, currentScore_tv;
    LinearLayout mBtnListLayout;
    User currentUser;
    Button startBtn,clearBtn;
    int playMode=-1; //no mode choose , 1, time out ,2 endless
    int timerState=-1; // no timer set , 1, timer start
    int  checkAnswerState=0; //no question set, 1 has question need to check answer
   String myAnswer = "";
    Chronometer timer;
    int totalScore;
    CountDownTimer countDownTimer;
    int level=1;
    Question question;
    History todayHistory;
   //Array of character which will show on the number button, in order to make easy understanding , change / with ÷, and * with ×
    String btnText[][] = {{"1", "2", "3"}, {"4", "5", "6"}, {"7", "8", "9"}, {",", "0", "-",}, {"+", "×", "÷"}};
   // array of buttons which will add programmatically to layout
    private Button[][] button = new Button[5][3];



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inititalize();
        getMyIntent();
        getData();
        generateBtnList();
    }


    // generate button
 private void generateBtnList() {

        for (int i = 0; i < 5; i++) {
            LinearLayout line = new LinearLayout(this);
            for (int j = 0; j < 3; j++) {
                button[i][j] = new Button(this);
                //set text of button base on the array pre define
                button[i][j].setText(btnText[i][j]);

                // add a tag to button in order to get which button has been clicked
                button[i][j].setTag(i * 3 + j + 1);


                button[i][j].setId(i);

                button[i][j].setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                //set button background based on gender of current user
if(currentUser.getGender()==User.Gender.Girl)
    button[i][j].setBackgroundResource(R.drawable.button1_girl);
else
                button[i][j].setBackgroundResource(R.drawable.button1_boy);
                button[i][j].setTextColor(Color.BLACK);

             //set on click listener
                button[i][j].setOnClickListener(this);
                //add button to Line view
                line.addView(button[i][j]);
                //set button size
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) button[i][j].getLayoutParams();
                params.height = 130;
                params.width = 200;
                button[i][j].setLayoutParams(params);
            }
            //add button Line parent view to button group view
            mBtnListLayout.addView(line);
        }

    }


    private void getData() {
        userName_tv.setText("Welcome: "+currentUser.getName());
    }

    // get current user information
    private void getMyIntent() {
        Intent myIntent = getIntent();
        Bundle bundle = myIntent.getBundleExtra("currentUserIntent");
        currentUser = (User) bundle.getSerializable("currentUser");
// set UI based on the current user's gender
        if(currentUser.getGender()==User.Gender.Girl)
        {   startBtn.setBackgroundResource(R.drawable.button_girl);
            clearBtn .setBackgroundResource(R.drawable.button_girl);
            avatarUser_iv.setImageResource(R.drawable.avatar_girl);
            avatarUser_iv.setBackgroundResource(R.drawable.background_style_girl);
        }
        Date date=new Date();
        SimpleDateFormat dateFormat=new SimpleDateFormat("YYYY-MM-dd");
        dateFormat.format(date);
      // create an instance of todayHistory with today's date
        todayHistory=new History(dateFormat.format(date));
    }

    private void inititalize() {
        avatarUser_iv = findViewById(R.id.AvatarUser_iv);
        userName_tv = findViewById(R.id.UserName_tv);
        high_score_tv = findViewById(R.id.HighScore_tv);
        longestTime_tv = findViewById(R.id.LongestTime_tv);
        avatarUser_iv.setImageResource(R.drawable.avatar_boy);
        mBtnListLayout = findViewById(R.id.listButtons_ll);
        question_tv = findViewById(R.id.Question_tv);
        timeRemainder_tv = findViewById(R.id.TimeRemainder_tv);
        answer_et = findViewById(R.id.Answer_et);
        currentScore_tv = findViewById(R.id.CurrentScore_tv);
        timer = findViewById(R.id.timer);
        startBtn=findViewById(R.id.StartBtn);
        clearBtn=findViewById(R.id.ClearBTN);

    }
// create option meue
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

   // change user menu item
        menu.add("Change Player").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                initialGame();
                Utile.saveLoginInfo(MainActivity.this, "", null, "no");
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                return false;
            }
        });
// go to history viw menu item
        menu.add("History").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                initialGame();
             Bundle historyBundle= new Bundle();

             Intent intent = new Intent(MainActivity.this, CheckHistory.class);
              historyBundle.putSerializable("historyBundle",todayHistory);
              historyBundle.putSerializable("userBundle",currentUser);
              intent.putExtra("historyIntent",historyBundle);

                startActivity(intent);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }


    @Override

    //add click lisener only on number/operator button
    public void onClick(View view) {

        if( playMode!=-1&&timerState!=-1&&checkAnswerState!=0) {
            int btnValue = (int) view.getTag();
            // if not 1-9 button
            if (myAnswer.length() != 0 || (int) view.getTag() <= 9 || (int) view.getTag() == 11 || (int) view.getTag() == 12) {
                String btnValueStr = "";
                if (btnValue > 9) {
                    switch (btnValue) {
                        case 10:
                            if (!myAnswer.contains("."))
                                btnValueStr = ".";
                            break;
                            //only allow "00" at the beginning
                        case 11:
                            if(myAnswer.length()==1&&myAnswer.equals("0"))
                                btnValueStr="";
                            else
                            btnValueStr = "0";
                            break;
                        case 12:
                            if (!checkOperator())
                                btnValueStr = "-";
                            break;
                        case 13:
                            if (!checkOperator())
                                btnValueStr = "+";
                            break;
                        case 14:
                            if (!checkOperator())
                                btnValueStr = "×";
                            checkOperator();
                            break;
                        case 15:
                            if (!checkOperator())
                                btnValueStr = "÷";
                            break;
                    }
                } else {
                    //if 0-9 clicked
                    btnValueStr = String.valueOf(btnValue);
                }
                //get answer and display it
                myAnswer = myAnswer + btnValueStr;
                answer_et.setText(myAnswer);
            }
        }
    }

    //check operator make sure two operator not appear one by one
    private boolean checkOperator() {
        if (myAnswer.length() != 0)
            return myAnswer.substring(myAnswer.length() - 1).equals("/") ||
                    myAnswer.substring(myAnswer.length() - 1).equals("*") ||
                    myAnswer.substring(myAnswer.length() - 1).equals("+") ||
                    myAnswer.substring(myAnswer.length() - 1).equals("-");
        else
            return false;
    }


    //if check answer clieked
    public void checkAnswer(View view) {
//if game is running
        if(timerState!=-1) {

            question.setYourAnswer(myAnswer);
            //if blank answer or not right answer
            if (myAnswer.length() == 0 || (question.getRightAnswer() == false)) {
                // no point add
                ToastBuilder.alert(this, "+0", ToastBuilder.LENGTH_SHORT).show();
              // if in endless mode, game over
                if (playMode == 2 || question_tv.getText().toString().equals("Game over")) {
                    timerState = -1;
                    question_tv.setText("Game over");
                    playMode = -1;
                    checkAnswerState = 0;
                    timer.stop();
                    return;
                }
            } else {
                //if is right answer, add 5 points
                totalScore = totalScore + 5;
                currentScore_tv.setText("Score: " + totalScore);
                ToastBuilder.success(this, "+5" , ToastBuilder.LENGTH_SHORT).show();
                if (playMode == 2) {
                    // if in endless mode level us when total score == 25 ||  total score == 50
                    if (totalScore == 25)
                        levelUp();
                    if (totalScore == 50)
                        levelUp();
                }
            }
            // change state to generate question
            checkAnswerState = 0;
            // add finished question to today's history
            todayHistory.getListOfQuestion().add(question);
         //generate question or start new game
            startGame();
            // clear answer display
            ClearAnswer(view);
        }

    }

    //set all the parameter to dafault mode
    private void initialGame()
    {
        level=1;
        timerState=-1;
        currentScore_tv.setText("");
        timeRemainder_tv.setText("");
        answer_et.setText("");
        question_tv.setText("");
        if( countDownTimer!=null)
            countDownTimer.cancel();
        totalScore=0;
        timer.stop();
        timer.setText("");
    }

    //user chose play mode
    public void chooseMode(View view) {
        initialGame();

         AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.gameMode)
                .setCancelable(false)
                .setSingleChoiceItems(new String[]{"Time out", "Endless"}, // Single Choice
                        -1,                                   // -1 for no default selection
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (i == 1) {
                                  playMode=2;
                                } else {
                                    playMode=1;
                                }
                            }
                        })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                        startGame();
                    }
                });
        builder.show();
  }

  // level up the current game
private void levelUp()
{
    level=level+1;
    ToastBuilder.warning(MainActivity.this, "Level up", ToastBuilder.LENGTH_SHORT).show();

}

    private void startGame() {
        checkAnswerState=0;
        timeRemainder_tv.setTextColor(Color.WHITE);

   //if play mode is time out, start count down timer
      if(playMode==1)
        {
      timer.setText("");
         if(timerState==-1) {
            timerState=1;
            countDownTimer= new CountDownTimer(60000, 1000) {
           public void onTick(long millisUntilFinished) {

               //set level up point
               if(millisUntilFinished / 1000==49)
                   levelUp();

               if(millisUntilFinished / 1000==20)
                   levelUp();
// set count down timer in red
              if(millisUntilFinished / 1000<=5)
              timeRemainder_tv.setTextColor(Color.RED);
               timeRemainder_tv.setText("Time remaining: " + millisUntilFinished / 1000);
           }

// if count down timer finished
           public void onFinish() {
               timerState =-1;
               //set text to game over
               question_tv.setText("Game over");

                playMode=-1;
                checkAnswerState=0;
                //set today's highest score base on current game result
                if(currentUser.getHighestScore()<totalScore)
                {  high_score_tv.setText("HS: "+totalScore);

                currentUser.setHighestScore(totalScore);
                    Utile.saveLoginInfo(MainActivity.this,totalScore);
                }

           }
       }.start();
   }
        }
 //if play mode is endless
      else{
     timeRemainder_tv.setText("");
             if(timerState==-1) {
                 timerState=1;
                 // initial new timer
                 timer.setBase(SystemClock.elapsedRealtime());
                 timer.start();
         }
 }
if (timerState==1) {
    //if on play mode, generate the question
    generateQuestion();
}
   }

   // create a new question , generate question based on level
    public void generateQuestion() {
        Question newQuestion=new Question();
          question = newQuestion;
        question.setQuestion(level);
        checkAnswerState=1;
        question_tv.setText(question.getQuestion());

    }

    //clear answer

    public void ClearAnswer(View view) {
        answer_et.setText("");
        myAnswer = "";
    }

}