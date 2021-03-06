package com.hungno.adeducation.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hungno.adeducation.InforActivity;
import com.hungno.adeducation.LoginActivity;
import com.hungno.adeducation.R;


public class CanhanFragment extends Fragment {
    ImageView avtGG,avtLogout;
    TextView tenMail,ten;
    Button btnDangXuat,btnThongtin;
    FirebaseAuth firebaseAuth;
    GoogleSignInClient googleSignInClient;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
         View view=inflater.inflate(R.layout.fragment_canhan, container, false);
         avtGG=view.findViewById(R.id.avtGG);
         avtLogout=view.findViewById(R.id.avtLogout);
         tenMail=view.findViewById(R.id.tenMail);
         ten=view.findViewById(R.id.ten);
         btnDangXuat=view.findViewById(R.id.btnDangxuat);
        btnThongtin=view.findViewById(R.id.btnThongtin);
         firebaseAuth=FirebaseAuth.getInstance();


        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        GoogleSignInAccount signInAccount= GoogleSignIn.getLastSignedInAccount(view.getContext());
        if (firebaseUser != null){
            Glide.with(CanhanFragment.this)
                    .load(firebaseUser.getPhotoUrl())
                    .into(avtGG);
            tenMail.setText(firebaseUser.getEmail());
            ten.setText(firebaseUser.getDisplayName());
        }
        btnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(view.getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
        avtLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(view.getContext(),LoginActivity.class));
            }
        });
        avtGG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            startActivity(new Intent(view.getContext(), InforActivity.class))   ;
            }
        });
        btnThongtin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(view.getContext(), InforActivity.class));
            }
        });
         return view;

    }

}