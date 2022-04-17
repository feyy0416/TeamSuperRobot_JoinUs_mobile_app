package edu.neu.madcourse.joinus.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import edu.neu.madcourse.joinus.R;
import edu.neu.madcourse.joinus.util.Utils;

public class SignupActivity extends AppCompatActivity {
    Button btn_signup;
    EditText email;
    EditText password;
    EditText repeatPassword;
    private FirebaseDatabase mDatabase;
    private FirebaseAuth mAuth;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        
        btn_signup = findViewById(R.id.btn_sign_up);
        email = findViewById(R.id.et_email_input);
        password = findViewById(R.id.et_password_input1);
        repeatPassword = findViewById(R.id.et_password_input2);
        mAuth = FirebaseAuth.getInstance();

        Utils.setInputReset(email);
        Utils.setInputReset(password);
        Utils.setInputReset(repeatPassword);
        
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (password.getText().toString().equals(repeatPassword.getText().toString())){
                    signup();
                }else {
                    Toast.makeText(SignupActivity.this, "The password entered twice does not match.", Toast.LENGTH_SHORT).show();
                }

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
        mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(SignupActivity.this, "Successfully Signed up!", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);

                        }else {
                            Toast.makeText(SignupActivity.this, "Sign up failed", Toast.LENGTH_SHORT).show();
                            updateUI();
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
    }
    private void updateUI() {
    }
}