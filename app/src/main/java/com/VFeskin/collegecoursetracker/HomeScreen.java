package com.VFeskin.collegecoursetracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.VFeskin.collegecoursetracker.Controller.TermList;
import com.VFeskin.collegecoursetracker.Model.User;
import com.VFeskin.collegecoursetracker.Model.UserViewModel;

import java.util.Objects;


public class HomeScreen extends AppCompatActivity {

    // XML attribute
    private EditText userNameTxt;
    private EditText passwordTxt;
    private Button logInButton;
    private Button signUpButton;
    UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);
        userNameTxt = findViewById(R.id.editTextLogInUserName);
        passwordTxt = findViewById(R.id.editTextLogInPassword);
        logInButton = findViewById(R.id.getStartedButton);
        signUpButton = findViewById(R.id.add_new_user);

        userViewModel = new ViewModelProvider.AndroidViewModelFactory(HomeScreen.this
                .getApplication())
                .create(UserViewModel.class);

        logInButton.setOnClickListener(view -> {
            // get user input
            String userName = userNameTxt.getText().toString().trim();
            String password = passwordTxt.getText().toString().trim();

            // checking if the user entered text is empty or not.
            if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(password)) {
                Toast.makeText(this, "Please enter user name and password", Toast.LENGTH_SHORT).show();
            } else {
                loginUser(userName, password);
            }
        });

        openApplication();
        signUpButton.setOnClickListener(this::createNewUser);
    }

    private void createNewUser(View view) {
        Intent intent = new Intent(this, CreateNewUser.class);
        startActivity(intent);
    }

    private void loginUser(String userName, String password) {

        // use observer
//
//        // Create a background thread
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                LiveData<User> user = userViewModel.getUser(userName, password);
//                if (user != null) {
//                    String name = user.getValue().getUserName();
//                    String pass = user.getValue().getPassword();
//
//                    if (userName.equals(name) && password.equals(pass)) {
//                        openApplication();
//                    }
//                }
//
//                // UI should only be updated by main thread
//                HomeScreen.this.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(HomeScreen.this, "Incorrect conditionals", Toast.LENGTH_LONG).show();
//                    }
//                });
//            }
//        });
//        thread.start();



//        LiveData<User> user = userViewModel.getUser(userName, password);
//
//        if (user != null) {
//            String name = user.getValue().getUserName();
//            String pass = user.getValue().getPassword();
//
//            if (userName.equals(name) && password.equals(pass)) {
//                openApplication();
//            }
//
//        } else {
//            Toast.makeText(this, "Incorrect conditionals", Toast.LENGTH_LONG).show();
//        }
    }

    public void openApplication() {
        Intent intent = new Intent(this, TermList.class);
        startActivity(intent);
    }

}