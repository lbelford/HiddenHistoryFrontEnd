package com.example.kendall.hiddenhistory;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Narrative extends AppCompatActivity {

    private String email;
    private String token;
    private String location_name;
    private String description;
    private String find_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_narrative);

        Bundle extras = getIntent().getExtras();
        email = extras.getString("email");
        token = extras.getString("token");
        location_name = extras.getString("name");
        description = extras.getString("description");
        find_info = extras.getString("find_info");


        TextView machine_view;
        TextView agent_view;

        machine_view = (TextView) findViewById(R.id.machine_terminal);
        agent_view = (TextView) findViewById(R.id.agent_texts);

        machine_view.setMovementMethod(new ScrollingMovementMethod());
        agent_view.setMovementMethod(new ScrollingMovementMethod());

        getSupportActionBar().setTitle(location_name);
        machine_view.setText(find_info);
        agent_view.setText(description);

        Button nextButton = (Button) findViewById(R.id.next_location_button);
        Button abortButton = (Button) findViewById((R.id.abort_button));

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextLocation();
            }
        });

        abortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abort();
            }
        });

    }

    protected void nextLocation()
    {
        Intent intent = new Intent(this, ChooseCategoryActivity.class);
        intent.putExtra("email", email);
        intent.putExtra("token", token);
        startActivity(intent);
    }

    protected void abort()
    {
        int score = new APICaller().abort(token);
        Intent intent = new Intent(this, EndAdventureActivity.class);
        intent.putExtra("email", email);
        intent.putExtra("token", token);
        intent.putExtra("score", score);
        startActivity(intent);
    }
}
