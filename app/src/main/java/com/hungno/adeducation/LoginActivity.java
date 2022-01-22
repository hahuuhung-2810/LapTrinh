package com.hungno.adeducation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    Button button;
    ImageView ImgFb, ImgGoogle;
    TextView txtResetpassword;
    FirebaseAuth mAuth;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ImgFb = findViewById(R.id.ImgFb2);
        ImgGoogle = findViewById(R.id.ImgGoogle);
        txtResetpassword = findViewById(R.id.txtResetPassword);
        addControls();
        addEvents();

    }

    private void addEvents() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               xuLyLogin();
//                Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
//                startActivity(intent);
            }

        });
        ImgFb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, FaceBookActivity.class);
                startActivity(intent);
            }
        });
        ImgGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, GoogleActivity.class);
                startActivity(intent);
            }
        });
        txtResetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });
    }

    private void xuLyLogin() {
        TextInputLayout Email = findViewById(R.id.edtEmail);
        TextInputLayout Password = findViewById(R.id.edtPass);
        String email = Email.getEditText().getText().toString().trim();
        String password = Password.getEditText().getText().toString().trim();

        if (TextUtils.isEmpty(Email.getEditText().getText().toString()) || TextUtils.isEmpty(Password.getEditText().getText().toString())) {
            Email.setError("Mời bạn nhập Email");
            Password.setError("Mời bạn nhập Password");
            return;
        }
        else {
            Email.setError(null);
            Password.setError(null);
        }

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            Toast.makeText(LoginActivity.this, "Successfully!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginActivity.this, "Fail!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void addControls() {
        button =findViewById(R.id.btnResetPass);
        mAuth = FirebaseAuth.getInstance();
    }

    public void openRegister(View view){
        Intent intent  = new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(intent);
    }
    
    
}