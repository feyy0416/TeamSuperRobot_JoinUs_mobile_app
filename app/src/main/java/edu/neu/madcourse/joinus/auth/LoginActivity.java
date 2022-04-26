package edu.neu.madcourse.joinus.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import edu.neu.madcourse.joinus.MainActivity;
import edu.neu.madcourse.joinus.R;
import edu.neu.madcourse.joinus.util.Utils;

public class LoginActivity extends AppCompatActivity {
    Button btn_sign_in;
    EditText email;
    EditText password;
    TextView forgotPassword;
    TextView signup;
    FirebaseDatabase mDatabase;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_sign_in = findViewById(R.id.btn_sign_up);
        email = findViewById(R.id.et_email_input);
        password = findViewById(R.id.et_password_input1);
        forgotPassword = findViewById(R.id.link_forgot_password);
        signup = findViewById(R.id.link_sign_up);

        mAuth = FirebaseAuth.getInstance();

        Utils.setInputReset(email);
        Utils.setInputReset(password);

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
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    private void signin() {
    }

}
