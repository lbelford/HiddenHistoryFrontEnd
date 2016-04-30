package com.example.kendall.hiddenhistory;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

public class StartSettingsActivity extends AppCompatActivity {

    private String email;
    private String token;
    private int min;
    private int max;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        this.email = extras.getString("email");
        this.token = extras.getString("token");

        setContentView(R.layout.start_settings_screen);

    }

    public void chooseCategory(View view){
        SeekBar min_bar = (SeekBar) findViewById(R.id.min_bar);
        SeekBar max_bar = (SeekBar) findViewById(R.id.max_bar);
        this.min = min_bar.getProgress() + 1;
        this.max = max_bar.getProgress() + 15;
        new APICaller().startAdventure(this.token, 35.910024, -79.053248, this.min, this.max, this.email);
        Intent intent = new Intent(this, IntroductionActivity.class);
        intent.putExtra("email", this.email);
        intent.putExtra("token", this.token);
        startActivity(intent);


    }
}
