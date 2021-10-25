package com.hungno.adeducation;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CaNhan extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canhan);
        BottomNavigationView bottomNavigationView=findViewById(R.id.button_navigation);
        bottomNavigationView.setSelectedItemId(R.id.canhan);
    }
}
