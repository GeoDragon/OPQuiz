package com.selfprojects.geonarz.opquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ScorePage extends AppCompatActivity {

    Button playAgain, mainMenu;
    TextView bountyFinal,message,remark;
    int beri;
    ImageView luffy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_page);

        playAgain = (Button)findViewById(R.id.playAgain);
        mainMenu = (Button)findViewById(R.id.mainMenu);

        bountyFinal = (TextView)findViewById(R.id.bountyFinal);
        bountyFinal.setText(Integer.toString(PlayPage.beri)+",000,000");
        message = (TextView)findViewById(R.id.message);
        remark = (TextView)findViewById(R.id.remark);

        beri = PlayPage.beri;

        luffy = (ImageView)findViewById(R.id.luffy);
        if(beri<500){
            Picasso.with(getApplicationContext()).load(R.drawable.luffydisapproves).into(luffy);
            message.setText("Luffy is Sad");
            remark.setText("Try Again!");
        }else{
            Picasso.with(getApplicationContext()).load(R.drawable.luffyapproves).into(luffy);
            message.setText("Luffy is Proud");
            remark.setText("Good Job!");
        }

        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ScorePage.this, PlayPage.class);
                startActivity(i);
                finish();
            }
        });

        mainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
