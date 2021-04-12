package com.converter.firebaseloginandsignup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    EditText txtemail, txtpass, txtconfpass;
    Button bntsignup;
    TextView textlogin;
    FirebaseAuth mAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth=FirebaseAuth.getInstance();
        txtemail=findViewById(R.id.userlogin);
        txtpass=findViewById(R.id.password2);
        txtconfpass=findViewById(R.id.confirmpass);
        bntsignup=findViewById(R.id.signup);
        textlogin=findViewById(R.id.gotologin);


        bntsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = txtemail.getText().toString();
                String pass = txtpass.getText().toString();
                String conpass = txtconfpass.getText().toString();

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
                    mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "You are successfully Registered", Toast.LENGTH_SHORT).show();
                                txtemail.setText("");
                                txtpass.setText("");
                                txtconfpass.setText("");
                            } else {
                                Toast.makeText(MainActivity.this, "You are not Registered! Try again", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });


            }
        });

        textlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(MainActivity.this, Login.class));
                switch (view.getId()) {
                    case R.id.gotologin:
                        overridePendingTransition(android.R.anim.slide_out_right, android.R.anim.slide_in_left);
                        break;
                }
            }
        });


    }
}


