package com.example.kendall.hiddenhistory;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ChooseCategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        setContentView(R.layout.category_choice);
    }

    public void goAdventure(View view) {
        new APICaller().startNext(getIntent().getExtras().getString("token"));
        Intent intent = new Intent(this, AdventureDisplayActivity.class);
        intent.putExtras(getIntent().getExtras());
        startActivity(intent);
    }

}
