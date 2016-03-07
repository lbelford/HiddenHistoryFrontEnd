package com.example.kendall.hiddenhistory;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class StartSettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_settings_screen);
    }

    public void chooseCategory(View view){
        Intent intent = new Intent(this, ChooseCategoryActivity.class);
        startActivity(intent);
    }
}
