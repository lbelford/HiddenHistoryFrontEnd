package com.example.kendall.hiddenhistory;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class StartSettingsActivity extends AppCompatActivity {

    private String email;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        this.email = extras.getString("email");
        this.token = extras.getString("token");

        setContentView(R.layout.start_settings_screen);

        new APICaller().startAdventure(this.token, 35.910024, -79.053248, 2, 10, this.email);
    }

    public void chooseCategory(View view){
        Intent intent = new Intent(this, ChooseCategoryActivity.class);
        intent.putExtra("email", this.email);
        intent.putExtra("token", this.token);
        startActivity(intent);


    }
}
