package com.example.kendall.hiddenhistory;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class IntroductionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);

        TextView intro = (TextView) findViewById(R.id.intro_terminal);

        Button begin = (Button) findViewById(R.id.begin_button);

        begin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goAdventureDisplay(v);
            }
        });

        intro.setMovementMethod(new ScrollingMovementMethod());

        intro.setTextColor(Color.GREEN);

        intro.setText(new APICaller().getAdventure(getIntent().getExtras().getString("token")).getStory());
    }

    protected void goAdventureDisplay(View view){
        Intent intent = new Intent(this, ChooseCategoryActivity.class);
        intent.putExtras(getIntent().getExtras());
        startActivity(intent);
    }
}
