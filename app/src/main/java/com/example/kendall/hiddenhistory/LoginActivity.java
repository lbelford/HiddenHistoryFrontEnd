package com.example.kendall.hiddenhistory;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

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
        TextView loginProgress = (TextView) findViewById(R.id.login_progress);

        String email = String.valueOf(emailEditText.getText());
        String pass = String.valueOf(passwordEditText.getText());

        if( email != null && !email.isEmpty()) {

            String token = new APICaller().authorize(email, pass);

            Log.d("HiddenHistory", "Token, " + token);
            if(token != null) {
                Log.d("LoginSuccess", token);
                loginProgress.setTextColor(Color.GREEN);
                loginProgress.setText("Login Successful!");
                goTitle(email, token);
                //goTest(email, token);
            }
            else
            {
                Log.d("LoginFailure", "Login failed");
                loginProgress.setTextColor(Color.RED);
                loginProgress.setText("Login credentials unrecognized");
            }
        }
        else
        {
            Log.d("HiddenHistory", email);
            loginProgress.setTextColor(Color.RED);
            loginProgress.setText("Please enter an email address");
        }
    }

    public void goTitle(String email, String token) {
        Intent intent = new Intent(this, TitleActivity.class);
        intent.putExtra("email", email);
        intent.putExtra("token", token);
        startActivity(intent);
    }

    public void goTest(String email, String token)
    {
        Intent intent = new Intent(this, AdventureDisplayActivity.class);
        intent.putExtra("email", email);
        intent.putExtra("token", token);
        startActivity(intent);
    }

}
