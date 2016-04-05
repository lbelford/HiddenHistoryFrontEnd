package com.example.kendall.hiddenhistory;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class Narrative extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_narrative);

        TextView machine_view;
        TextView agent_view;

        machine_view = (TextView) findViewById(R.id.machine_terminal);
        agent_view = (TextView) findViewById(R.id.agent_texts);

        machine_view.setMovementMethod(new ScrollingMovementMethod());
        agent_view.setMovementMethod(new ScrollingMovementMethod());

    }
}
