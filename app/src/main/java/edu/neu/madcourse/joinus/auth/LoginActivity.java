package edu.neu.madcourse.joinus.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import edu.neu.madcourse.joinus.AddEventActivity;
import edu.neu.madcourse.joinus.EventActivity;
import edu.neu.madcourse.joinus.MainActivity;
import edu.neu.madcourse.joinus.MapsActivity;
import edu.neu.madcourse.joinus.MyLocationDemoActivity;
import edu.neu.madcourse.joinus.R;
import edu.neu.madcourse.joinus.util.Utils;

public class LoginActivity extends AppCompatActivity {
    Button btn_sign_in;
    EditText etLoginEmail;
    EditText etLoginPassword;
    TextView forgotPassword;
    TextView signup;
    FirebaseAuth mAuth;
    Button btn_test;
    Button btn_testEvent;
    Button btn_test_loc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_sign_in = findViewById(R.id.btn_sign_up);
        etLoginEmail = findViewById(R.id.et_email_input);
        etLoginPassword = findViewById(R.id.et_password_input1);
        forgotPassword = findViewById(R.id.link_forgot_password);
        signup = findViewById(R.id.link_sign_up);

        mAuth = FirebaseAuth.getInstance();

        Utils.setInputReset(etLoginEmail);
        Utils.setInputReset(etLoginPassword);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        btn_test = findViewById(R.id.btn_test);
        btn_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });
        btn_testEvent = findViewById(R.id.btn_testEvent);
        btn_testEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, EventActivity.class);
                startActivity(intent);
            }
        });
        Button btn_testAdd = findViewById(R.id.btn_testAddEvent);
        btn_testAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, AddEventActivity.class);
                startActivity(intent);
            }
        });

        btn_test_loc = findViewById(R.id.btn_test_loc);
        btn_test_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, MyLocationDemoActivity.class);
                startActivity(intent);
            }
        });


    }

    private void signIn() {
        String email = etLoginEmail.getText().toString();
        String password = etLoginPassword.getText().toString();

        if (TextUtils.isEmpty(email)) {
            etLoginEmail.setError("Email cannot be empty");
            etLoginEmail.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            etLoginPassword.setError("Passwords cannot be empty");
            etLoginPassword.requestFocus();
        } else {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "Logged in Successfully!",
                                Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    } else {
                        Toast.makeText(LoginActivity.this,
                                "Log in failed: " + task.getException().getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

}
