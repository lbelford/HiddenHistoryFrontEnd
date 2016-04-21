package com.example.kendall.hiddenhistory;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class API_TestActivity extends AppCompatActivity {

    TextView output;
    APICaller caller;
    String email;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api__test);

        Bundle extras = getIntent().getExtras();
        email = extras.getString("email");
        token = extras.getString("token");

        Button start_adventure = (Button) findViewById(R.id.start_adventure_button);
        Button get_adventure = (Button) findViewById(R.id.get_adventure_button);
        Button start_next = (Button) findViewById(R.id.start_next_button);
        Button find_location = (Button) findViewById(R.id.find_location_button);
        Button abort = (Button) findViewById(R.id.abort_button);

        output = (TextView) findViewById(R.id.api_output);
        caller = new APICaller();

        start_adventure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAdventure();
            }
        });

        get_adventure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAdventure();
            }
        });

        start_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNext();
            }
        });

        find_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findLocation();
            }
        });

        abort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abort();
            }
        });

    }

    protected void startAdventure()
    {
        if(caller.getAdventure(token) == null) {
            Adventure ad = caller.startAdventure(token, 35.910024, -79.053248, 2, 10, email);
            if(ad == null)
                output.setText("The adventure is null for some reason");
            else
                output.setText(ad.toString());
        }
        else
        {
            output.setText("The user already has an adventure in progress");
        }
    }

    protected void getAdventure()
    {
        Adventure ad = caller.getAdventure(token);
        if(ad == null)
            output.setText("There is no adventure in progress");
        else
            output.setText(ad.toString());
    }

    protected void startNext()
    {
        Adventure ad = caller.startNext(email, token, 1);
        output.setText(ad.toString());
    }

    protected void findLocation()
    {
        String nonsense = caller.findLocation(token);
        output.setText(nonsense);
    }

    protected void abort()
    {
        int score = caller.abort(token);
        output.setText("" + score);
    }
}
