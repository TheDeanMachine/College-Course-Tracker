package com.VFeskin.collegecoursetracker;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.VFeskin.collegecoursetracker.Model.User;
import com.VFeskin.collegecoursetracker.Model.UserViewModel;
import com.google.android.material.snackbar.Snackbar;

/**
 * This class is used to create a new user.
 * There credentials will be stored in the DB.
 */
public class CreateNewUser extends AppCompatActivity {

    // XML attribute
    private EditText userNameTxt;
    private EditText passwordTxt;
    private Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_user);
        userNameTxt = findViewById(R.id.editTextSignUpUser);
        passwordTxt = findViewById(R.id.editTextSignUpPassword);
        signUpButton = findViewById(R.id.signUpButton);

        signUpButton.setOnClickListener(view -> {
            // get user input
            String userName = userNameTxt.getText().toString().trim();
            String password = passwordTxt.getText().toString().trim();

            // checking if the user entered text is empty or not.
            if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(password)) {
//                Toast.makeText(this, "Must enter a user name and a password", Toast.LENGTH_LONG).show();
                Snackbar.make(findViewById(R.id.scrollView2), "Must enter a user name and a password", Snackbar.LENGTH_SHORT).show();
            } else {
                UserViewModel.insert(new User(userName, password));
                Toast.makeText(this, "Welcome " + userName, Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }
}