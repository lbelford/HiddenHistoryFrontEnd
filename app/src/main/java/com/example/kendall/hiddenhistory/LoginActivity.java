package com.example.kendall.hiddenhistory;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    public final static String baseURL = "http://ec2-52-87-160-106.compute-1.amazonaws.com:9000/";
    public final static String apiURL = baseURL + "api/hiddenhistory/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void login(View view){
        EditText emailEditText = (EditText) findViewById(R.id.email_address);
        EditText passwordEditText = (EditText) findViewById(R.id.password);

        String email = emailEditText.toString();
        String pass = passwordEditText.toString();

        if( email != null && !email.isEmpty()) {

            String token = new APICaller().authorize(email, pass);
            System.out.println(token);
        }
    }
}
