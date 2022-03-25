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
import com.google.android.material.snackbar.Snackbar;

/**
 * This class is used for authenticating the user.
 * If the user has credentials they may enter them, this will search the DB for matching user.
 * Else they make create a new account by clicking sign up.
 */
public class HomeScreen extends AppCompatActivity {

    // XML attribute
    private EditText userNameTxt;
    private EditText passwordTxt;
    private Button logInButton;
    private Button signUpButton;

    // view model
    private UserViewModel userViewModel;

    // user
    private LiveData<User> user;

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
                Toast.makeText(this, "Must enter a user name and a password", Toast.LENGTH_SHORT).show();
            } else {
                loginUser(userName, password);
            }
        });

        signUpButton.setOnClickListener(this::createNewUser);
    }

    private void createNewUser(View view) {
        Intent intent = new Intent(this, CreateNewUser.class);
        startActivity(intent);
    }

    private void loginUser(String userName, String password) {
        // must use view model to check for user
        user = userViewModel.getUser(userName, password);
        user.observe(this, user -> {
            // check for any results
            if (user != null) {
                String name = user.getUserName();
                String pass = user.getPassword();
                // verify credentials
                if (userName.equals(name) && password.equals(pass)) {
                    openApplication();
                }
            } else {
                Toast.makeText(this, "Incorrect conditionals", Toast.LENGTH_LONG).show();
            }

        });
    }

    public void openApplication() {
        Intent intent = new Intent(this, TermList.class);
        startActivity(intent);
    }

}