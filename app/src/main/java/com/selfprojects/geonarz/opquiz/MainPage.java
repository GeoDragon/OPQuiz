package com.selfprojects.geonarz.opquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class MainPage extends AppCompatActivity {

    Button button1, button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        ImageView imageView = (ImageView)findViewById(R.id.backgroundMain);
        Picasso.with(getApplicationContext()).load(R.drawable.main_characters_background).into(imageView);

        button1 = (Button)findViewById(R.id.playButton);
        button2 = (Button)findViewById(R.id.exitButton);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainPage.this, PlayPage.class);
                startActivity(intent);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
