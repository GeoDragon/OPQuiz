package com.selfprojects.geonarz.opquiz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;

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
    private int highScore;
    static int numWrong = 0;
    SharedPreferences prefs;

    PopupWindow popUpWindow;
    LayoutInflater layoutInflater;
    ScrollView scrollView;
    String charDescription;

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

        scrollView = (ScrollView)findViewById(R.id.scrollView);
        charDescription = "To be populated";

        updateScreen();

        prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);

        SharedPreferences prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        highScore = prefs.getInt("key", 0);
    }

    public void updateScreen(){
        List<String> randomOptions = Arrays.asList(options);
        Collections.shuffle(randomOptions);

        if(++questionNum<=5){

            Picasso.with(getApplicationContext())
                    .load(getImageId(getApplicationContext(),answer[questionNum-1]
                            .toLowerCase().replace(" ","")))
                    .into(character);

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
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("key", highScore);
            editor.apply();
            Intent intent = new Intent(PlayPage.this, ScorePage.class);
            startActivity(intent);
            finish();
        }
    }

    public void checkAnswer(Button button){

        layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewGroup container = (ViewGroup) layoutInflater.inflate(R.layout.pop, null);

        if(button.getText().equals(answer[questionNum-1])){
            button.setBackgroundResource(R.color.colorPrimary);
            increaseBeri();
            numWrong = 0;

            ImageView cross = (ImageView)container.findViewById(R.id.cross);
            Picasso.with(getApplicationContext()).load(R.drawable.cross).into(cross);

            ImageView character = (ImageView)container.findViewById(R.id.character);
            Picasso.with(getApplicationContext())
                    .load(getImageId(getApplicationContext(),answer[questionNum-1]
                            .toLowerCase().replace(" ","")))
                    .into(character);

            TextView charDesc = (TextView)container.findViewById(R.id.charDesc);
            charDesc.setText(getCharDesc(answer[questionNum-1]
                    .toLowerCase().replace(" ","")));

            TextView characterName = (TextView)container.findViewById(R.id.characterName);
            characterName.setText(answer[questionNum-1]);

            popUpWindow = new PopupWindow(container, scrollView.getMeasuredWidth() -300, scrollView.getMeasuredHeight(), true);
            popUpWindow.showAtLocation(scrollView, Gravity.CENTER, 0, 0);

            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popUpWindow.dismiss();
                    updateScreen();
                }
            });


        }else{
            if(++numWrong == 4){
                ImageView cross = (ImageView)container.findViewById(R.id.cross);
                Picasso.with(getApplicationContext()).load(R.drawable.cross).into(cross);

                ImageView character = (ImageView)container.findViewById(R.id.character);
                Picasso.with(getApplicationContext())
                        .load(R.drawable.guess)
                        .into(character);

                TextView charDesc = (TextView)container.findViewById(R.id.charDesc);
                charDesc.setText("You are just guessing!");

                popUpWindow = new PopupWindow(container, scrollView.getMeasuredWidth() -300, scrollView.getMeasuredHeight(), true);
                popUpWindow.showAtLocation(scrollView, Gravity.CENTER, 0, 0);

                container.findViewById(R.id.cross).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popUpWindow.dismiss();
                    }
                });
                numWrong=0;
            }
            else{
                button.setBackgroundResource(R.color.red);
            }
            decreaseBeri();
            bounty.setText(Integer.toString(beri)+",000,000");
        }
    }

    private String getCharDesc(String characterName) {
        if(characterName.equals("arlong")){
            return "Arlong the Saw is a sawshark Fishman. He was the pirate captain of the all Fishman crew, " +
                    "the Arlong Pirates, a former member of the Sun Pirates, and the older half-brother of Madam Shyarly. " +
                    "He was the main antagonist of the Arlong Park Arc, and had the highest bounty in the East Blue before " +
                    "being defeated by Monkey D. Luffy.";
        }
        else if (characterName.equals("crocodile")){
            return "Sir Crocodile is the former president of the mysterious crime syndicate Baroque Works" +
                    " and the main antagonist of the Alabasta Saga. He is one of the longest running and most " +
                    "noteworthy primary adversaries of the series, as he was the first enemy to hand Luffy a complete and utter defeat, " +
                    "as well as one of only two who has defeated Luffy more than once the other being magellan. He was originally introduced " +
                    "as a Shichibukai but was later stripped of his title after attempting to take control of the desert kingdom Alabasta.";
        }
        else if(characterName.equals("roblucci")){
            return "Rob Lucci was the strongest member of the CP9 unit operating undercover to obtain the Pluton blueprints from Iceburg. " +
                    "Introduced as one of the five foremen of Galley-La Company's Dock One, he was a sawyer, treenail, and bolt specialist. " +
                    "After his defeat by Luffy, he was dismissed from CP9 by Spandam who declared him and the other assassins to be responsible for the Straw Hats' havoc on Enies Lobby. " +
                    "He was the main antagonist of the Water 7 Saga, specifically the Water 7 and Enies Lobby Arc. He is currently part of CP-0.";
        }
        else if(characterName.equals("portgasdace")){
            return "Portgas D. Ace, born as Gol D. Ace and nicknamed \"Fire Fist Ace\", was the adopted older brother of Luffy and Sabo, " +
                    "and son of the late Pirate King, Gol D. Roger and his lover, Portgas D. Rouge. " +
                    "He was adopted by Monkey D. Garp as wished by Roger to him before his birth. " +
                    "Ace was the 2nd division commander of the Whitebeard Pirates and one-time captain of the Spade Pirates.";
        }
        else if(characterName.equals("donquixotedoflamingo")){
            return "Donquixote Doflamingo, nicknamed \"Heavenly Yaksha\", was the captain of the Donquixote Pirates, one of the Shichibukai with a frozen bounty of Beli 340,000,000," +
                    " the former most influential underworld broker under the codename \"Joker\", and is a former World Noble of the Donquixote Family descent. " +
                    "He is the son of Donquixote Homing and the older brother of Rosinante, both of whom he killed out of feeling betrayed by them. " +
                    "Doflamingo became the king of Dressrosa after taking the throne from Riku Dold III eight years before the start of the series; " +
                    "he ruled the country tyrannically until he was stripped of his positions as a Shichibukai and king after he was defeated by " +
                    "Monkey D. Luffy near the end of the Dressrosa Arc, being arrested alongside most of his crew members by Admiral Fujitora.";
        }
        return "Fetching Error";
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
