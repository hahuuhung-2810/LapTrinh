package com.hungno.adeducation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    Button btnSignup;
    ImageView IMGgg, ImgFb2;
    FirebaseAuth mAuth;
    EditText FullName, Email, Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        addControls();
        addEvents();

    }

    private void addEvents() {
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xylySignUp();
            }
        });
        IMGgg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this,GoogleActivity.class);
                startActivity(intent);
            }
        });
        ImgFb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this,FaceBookActivity.class);
                startActivity(intent);
            }
        });
    }

    private void xylySignUp() {
        TextInputLayout FullName = findViewById(R.id.edtName);
        TextInputLayout Email = findViewById(R.id.edtEmail);
        TextInputLayout Password = findViewById(R.id.edtPassword);

        String fullname = FullName.getEditText().getText().toString().trim();
        String email = Email.getEditText().getText().toString().trim();
        String password = Password.getEditText().getText().toString().trim();


        if (TextUtils.isEmpty(FullName.getEditText().getText().toString())) {
                FullName.setError("Mời bạn nhập Tên");
                return;
        }else {
        FullName.setError(null);}


        if (TextUtils.isEmpty(Email.getEditText().getText().toString())) {
            Email.setError("Mời bạn nhập Email");
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(Email.getEditText().getText().toString()).matches()) {
            Email.setError("Email bạn nhập chưa đúng định dạng");
            return;
        }else {
        Email.setError(null);}


        if (TextUtils.isEmpty(Password.getEditText().getText().toString())) {
            Password.setError("Mời bạn nhập Password");
            return;
        }
        if (Password.getEditText().getText().toString().trim().length() < 8) {
            Password.setError("Pass quá ngắn , tối thiểu 8 kí tự");
            return;
        }else {
        Password.setError(null);}


        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User user = new User(fullname,email,password);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(RegisterActivity.this, "Successfully!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(RegisterActivity.this, "Fail!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                });
    }
    private void addControls() {
        btnSignup = findViewById(R.id.btnResetPass);
        mAuth = FirebaseAuth.getInstance();
        IMGgg = findViewById(R.id.IMGgg);
        ImgFb2 = findViewById(R.id.ImgFb2);
    }

    public void openLogin(View view){
        Intent intent  = new Intent(RegisterActivity.this,LoginActivity.class);
        startActivity(intent);
    }

}