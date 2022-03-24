package com.VFeskin.collegecoursetracker;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.VFeskin.collegecoursetracker.Controller.TermList;


public class HomeScreen extends AppCompatActivity {

    // XML attribute
    private Button logInButton;
    private EditText userNameTxt;
    private EditText passwordTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);
        userNameTxt = findViewById(R.id.editTextLogInUserName);
        passwordTxt = findViewById(R.id.editTextLogInPassword);
        logInButton = findViewById(R.id.getStartedButton);

        logInButton.setOnClickListener(view -> {
            // get user input
            String userName = userNameTxt.getText().toString().trim();
            String password = passwordTxt.getText().toString().trim();

            // checking if the user entered text is empty or not.
            if (TextUtils.isEmpty(userName) && TextUtils.isEmpty(password)) {
                Toast.makeText(this, "Please enter user name and password", Toast.LENGTH_SHORT).show();
            } else {
                loginUser(userName, password);
            }
        });
    }

    private void loginUser(String userName, String password) {
        if(userName.equals("admin") && password.equals("admin")){
            openApplication();
        } else {
            Toast.makeText(this, "Incorrect conditionals", Toast.LENGTH_LONG).show();
        }


    }

    public void openApplication() {
        Intent intent = new Intent(this, TermList.class);
        startActivity(intent);
    }

}