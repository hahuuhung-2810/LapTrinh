package com.hungno.adeducation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {
    FirebaseAuth auth;
    Button btnResetPass;
    TextInputLayout email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        addControls();
        addEvent();
    }

    private void addEvent() {
        btnResetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xulyForgotPass();
            }
        });
    }

    private void addControls() {
        auth = FirebaseAuth.getInstance();
        btnResetPass = findViewById(R.id.btnResetPass);
        email = findViewById(R.id.edtEmail);
    }

    private void xulyForgotPass() {
        TextInputLayout email = findViewById(R.id.edtEmail);
        String gmail = email.getEditText().getText().toString().trim();

        if (TextUtils.isEmpty(email.getEditText().getText().toString())) {
            email.setError("Mời bạn nhập Email");
            return;
        }else{
            email.setError(null);
        }
        auth.sendPasswordResetEmail(gmail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ForgotPasswordActivity.this, "Check your Email to reset your Password", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(ForgotPasswordActivity.this, "Try again! Something wrong happend!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}