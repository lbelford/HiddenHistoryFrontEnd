package com.example.kendall.hiddenhistory;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EndAdventureActivity extends AppCompatActivity {

    private String email;
    private String token;
    private int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_adventure);
        Bundle extras = getIntent().getExtras();
        email = extras.getString("email");
        token = extras.getString("token");
        score = extras.getInt("score");

        TextView scoreDisplay = (TextView) findViewById(R.id.score_display);

        scoreDisplay.setText("Congratulations on finishing your adventure! Your score is: " + score);

        Button finishButton = (Button) findViewById(R.id.finish_button);

        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnToTitle();
            }
        });
    }

    protected void returnToTitle()
    {
        Intent intent = new Intent(this, TitleActivity.class);
        intent.putExtra("email", email);
        intent.putExtra("token", token);
        startActivity(intent);
    }
}
