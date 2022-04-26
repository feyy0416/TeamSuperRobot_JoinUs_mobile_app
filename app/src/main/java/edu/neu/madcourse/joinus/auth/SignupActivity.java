package edu.neu.madcourse.joinus.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import edu.neu.madcourse.joinus.R;
import edu.neu.madcourse.joinus.util.Utils;

public class SignupActivity extends AppCompatActivity {
    Button btn_signup;
    EditText etEmail;
    EditText etPassword;
    EditText etRepeatPassword;
    EditText etUsername;
    private FirebaseAuth mAuth;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        
        btn_signup = findViewById(R.id.btn_sign_up);
        etUsername = findViewById(R.id.et_username_input);
        etEmail = findViewById(R.id.et_email_input);
        etPassword = findViewById(R.id.et_password_input1);
        etRepeatPassword = findViewById(R.id.et_password_input2);
        mAuth = FirebaseAuth.getInstance();

        Utils.setInputReset(etUsername);
        Utils.setInputReset(etEmail);
        Utils.setInputReset(etPassword);
        Utils.setInputReset(etRepeatPassword);
        
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signup();
                /*if (etPassword.getText().toString().equals(repeatPassword.getText().toString())){
                    signup();
                }else {
                    Toast.makeText(SignupActivity.this, "Passwords do not match.",
                            Toast.LENGTH_SHORT).show();
                }*/
            }
        });
        
    }
    //check if logged in
//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if(currentUser != null){
//            reload();
//        }
//    }

    private void signup() {
        String username = etUsername.getText().toString();
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        String repeatPassword = etRepeatPassword.getText().toString();

        if (TextUtils.isEmpty(username)) {
            etUsername.setError("Username cannot be empty");
            etUsername.requestFocus();
        } else if (TextUtils.isEmpty(email)) {
            etEmail.setError("Email cannot be empty");
            etEmail.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            etPassword.setError("Passwords cannot be empty");
            etPassword.requestFocus();
        } else if (!repeatPassword.equals(password)) {
            etRepeatPassword.setError("Passwords do not match");
            etRepeatPassword.requestFocus();
        } else {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(SignupActivity.this, "Signed up Successfully!",
                                        Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);

                            } else {
                                Toast.makeText(SignupActivity.this,
                                        "Sign up failed: " + task.getException().getMessage(),
                                        Toast.LENGTH_SHORT).show();
                                updateUI();
                            }
                        }
                    });
        }
    }

    private void updateUI(FirebaseUser user) {
    }
    private void updateUI() {
    }
}