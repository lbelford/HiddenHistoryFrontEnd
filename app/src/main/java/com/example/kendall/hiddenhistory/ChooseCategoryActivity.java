package com.example.kendall.hiddenhistory;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ChooseCategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_choice);
    }

    public void goAdventure(View view) {
        Intent intent = new Intent(this, Narrative.class);
        startActivity(intent);
    }

    //Change for git
}
