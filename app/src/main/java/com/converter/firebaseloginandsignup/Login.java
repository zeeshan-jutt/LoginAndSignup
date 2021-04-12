package com.converter.firebaseloginandsignup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    EditText txtemail, txtpass;
    Button bntsignin;
    TextView textView;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth=FirebaseAuth.getInstance();

        txtemail=findViewById(R.id.userlogin);
        txtpass=findViewById(R.id.loginpass);
        bntsignin=findViewById(R.id.signin);
        textView=findViewById(R.id.gotosignup);

        bntsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = txtemail.getText().toString();
                String pass = txtpass.getText().toString();

                if (email.isEmpty()) {
                    txtemail.setError("Email is empty");
                    txtemail.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    txtemail.setError("Enter the valid email address");
                    txtemail.requestFocus();
                    return;
                }
                if (pass.isEmpty()) {
                    txtpass.setError("Enter the password");
                    txtpass.requestFocus();
                    return;
                }
                if (pass.length() < 6) {
                    txtpass.setError("Length of the password should be more than 6");
                    txtpass.requestFocus();
                    return;
                }

                mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Intent i = new Intent(Login.this, Dashboard.class);
                                    startActivity(i);
                                    switch (view.getId()){
                                        case R.id.signin:
                                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                                            break;
                                    }
                                } else {
                                    Toast.makeText(Login.this, "Login Error! Try again", Toast.LENGTH_SHORT).show();
                                }
                    }
                });

            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Login.this, MainActivity.class);
                startActivity(intent);
                switch (view.getId()) {
                    case R.id.gotosignup:
                        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                        break;
                }
            }
        });
    }
}