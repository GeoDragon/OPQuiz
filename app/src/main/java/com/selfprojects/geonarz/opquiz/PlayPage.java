package com.selfprojects.geonarz.opquiz;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class PlayPage extends AppCompatActivity {

    Button button1,button2,button3,button4,button5;
    TextView question,bounty;
    ImageView character;
    static int questionNum;
    static int beri;
    static int numWrong = 0;

    //----_IDHAR DEKH------------FOR HIGHSCORE--------------------------------------------
    //For getting high score create another static variable and when one game finishes set
    //beri's value to that variable.

    String answer[] = {
            "Arlong",
            "Crocodile",
            "Rob Lucci",
            "Portgas D Ace",
            "Donquixote Doflamingo"
    };

    String options[] = {
            "Arlong",
            "Crocodile",
            "Rob Lucci",
            "Portgas D Ace",
            "Donquixote Doflamingo"
    };

    public static int randInt(int min, int max){
        Random rand = null;
        int randomNum = rand.nextInt((max - min)+ 1 )+ min;
        return randomNum;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_page);

        question = (TextView)findViewById(R.id.questionNumber);
        bounty = (TextView)findViewById(R.id.bounty);

        button1 = (Button)findViewById(R.id.button1);
        button2 = (Button)findViewById(R.id.button2);
        button3 = (Button)findViewById(R.id.button3);
        button4 = (Button)findViewById(R.id.button4);
        button5 = (Button)findViewById(R.id.button5);

        character = (ImageView)findViewById(R.id.character);

        beri = 0;
        questionNum=0;

        updateScreen();
    }

    public void updateScreen(){
        List<String> randomOptions = Arrays.asList(options);
        Collections.shuffle(randomOptions);

        if(++questionNum<=5){

            question.setText("Question "+questionNum);

            button1.setBackgroundResource(android.R.drawable.btn_default);
            button2.setBackgroundResource(android.R.drawable.btn_default);
            button3.setBackgroundResource(android.R.drawable.btn_default);
            button4.setBackgroundResource(android.R.drawable.btn_default);
            button5.setBackgroundResource(android.R.drawable.btn_default);

            button1.setText(randomOptions.get(0));
            button2.setText(randomOptions.get(1));
            button3.setText(randomOptions.get(2));
            button4.setText(randomOptions.get(3));
            button5.setText(randomOptions.get(4));

            bounty.setText(Integer.toString(beri)+",000,000");

            Picasso.with(getApplicationContext())
                    .load(getImageId(getApplicationContext(),answer[questionNum-1]
                            .toLowerCase().replace(" ","")))
                    .into(character);

            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkAnswer(button1);
                }
            });

            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkAnswer(button2);
                }
            });

            button3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkAnswer(button3);
                }
            });

            button4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkAnswer(button4);
                }
            });
            button5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkAnswer(button5);
                }
            });
        }
        else{
            questionNum = 0;
            Intent intent = new Intent(PlayPage.this, ScorePage.class);
            startActivity(intent);
            finish();
        }
    }

    public void checkAnswer(Button button){
        if(button.getText().equals(answer[questionNum-1])){
            button.setBackgroundResource(R.color.colorPrimary);
            increaseBeri();
            numWrong = 0;
            updateScreen();
        }else{
            button.setBackgroundResource(R.color.red);
            Toast.makeText(PlayPage.this, "Thats Not Right", Toast.LENGTH_SHORT).show();
            numWrong++;
            decreaseBeri();
            bounty.setText(Integer.toString(beri)+",000,000");
        }
    }

    public void increaseBeri(){
        if(beri <= 0){
            beri = 30;
        }else if(beri<=30){
            beri = 100;
        }else if(beri<=100){
            beri = 300;
        }else if(beri<=300){
            beri = 400;
        }else if(beri<=400) {
            beri = 500;
        }
    }

    public void decreaseBeri(){
        if(beri == 0){

        }else if(beri <= 30){
            beri = beri - 10;
        }else if(beri <= 100){
            beri = beri - 20;
        }else if(beri <= 300){
            beri = beri - 50;
        }else if(beri <= 400){
            beri = beri - 100;
        }
    }

    public static int getImageId(Context context, String imageName) {
        return context.getResources().getIdentifier("drawable/" + imageName, null, context.getPackageName());
    }
}
