package com.example.kendall.hiddenhistory;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class TitleActivity extends AppCompatActivity {

    private String email;
    private String token;
    private APICaller caller;
    private Adventure myAdventure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.title_screen);
        Bundle extras = getIntent().getExtras();
        this.email = extras.getString("email");
        this.token = extras.getString("token");
        Log.d("TitleExtras", "email: " + this.email);
        Log.d("TitleExtras", "token: " + this.token);

        Button startButton = (Button) findViewById(R.id.start_button);

        Button testButton = (Button) findViewById(R.id.settings_button);

        //testButton.setText("Test");
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goApiTest(v);
            }
        });

        caller = new APICaller();

        this.myAdventure = caller.getAdventure(token);

        if(this.myAdventure == null)
        {
            startButton.setText("Start");
            startButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goSettings(v);
                }
            });
        }
        else
        {
            startButton.setText("Continue");
            if(myAdventure.getActive()) {
                startButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        goAdventureDisplay(v);
                    }
                });
            }
            else
            {
                startButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        goNarrative(v);
                    }
                });
            }
        }
    }

    public void goSettings(View view) {
        Intent intent = new Intent(this, StartSettingsActivity.class);
        intent.putExtra("email", this.email);
        intent.putExtra("token", this.token);
        startActivity(intent);
    }

    public void goAdventureDisplay(View view)
    {
        Intent intent = new Intent(this, AdventureDisplayActivity.class);
        intent.putExtra("email", this.email);
        intent.putExtra("token", this.token);
        startActivity(intent);
    }

    public void goNarrative(View view)
    {
        Intent intent = new Intent(this, Narrative.class);
        intent.putExtra("email", this.email);
        intent.putExtra("token", this.token);
        intent.putExtra("description", this.myAdventure.getDescription());
        intent.putExtra("location", this.myAdventure.getDescription());
        intent.putExtra("find_info", new APICaller().findLocation(this.token));
        startActivity(intent);
    }

    public void goApiTest(View view)
    {
        Intent intent = new Intent(this, API_TestActivity.class);
        intent.putExtra("email", this.email);
        intent.putExtra("token", this.token);
        startActivity(intent);
    }
}
