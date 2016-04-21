package com.example.kendall.hiddenhistory;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class AdventureDisplayActivity extends AppCompatActivity {

    private String email;
    private String token;
    private APICaller caller;
    private String description;
    private String locationName;
    private String findLocationInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = this.getIntent().getExtras();
        this.email = extras.getString("email");
        this.token = extras.getString("token");

        setContentView(R.layout.activity_adventure_display);

        caller = new APICaller();

        Log.d("Test for Extras", "email: " + email +", token: " + token);

        Adventure myAdventure = caller.getAdventure(token);

        description = myAdventure.getDescription();
        locationName = myAdventure.getLocationName();

        /*Adventure myAdventure = caller.startAdventure(token, 35.9, -79, 5, 10, email); */

        TextView adventureDisplay = (TextView) findViewById(R.id.adventure_display);

        Button findButton = (Button) findViewById(R.id.find_location_button);

        findButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findLocation();
            }
        });

        if(myAdventure != null) {
            Log.d("AdventureMainThread", myAdventure.toString());
            adventureDisplay.setText(myAdventure.toString());
        }
        else{
            adventureDisplay.setText("Adventure was null!");
        }
    }

    protected void findLocation()
    {
        findLocationInfo = caller.findLocation(token);
        Intent intent = new Intent(this, Narrative.class);
        intent.putExtra("email", email);
        intent.putExtra("token", token);
        intent.putExtra("name", locationName);
        intent.putExtra("description", description);
        intent.putExtra("find_info", findLocationInfo);
        startActivity(intent);
    }
}
