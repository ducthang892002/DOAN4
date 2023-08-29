package com.example.doan4;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.doan4.Activity.CheckOrderActivity;
import com.example.doan4.Activity.SpCartActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class My extends AppCompatActivity {
    BottomNavigationView navigationView;
    LinearLayout ttdonhang;
    ImageButton giohang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mylayout);
        navigationView = findViewById(R.id.bottom_navigation);
        anhxa();
        giohang = findViewById(R.id.giohangtoi);
        giohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(My.this, SpCartActivity.class);
                startActivity(intent);
            }
        });
        ttdonhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(My.this, CheckOrderActivity.class);
                startActivity(intent);
            }
        });
        navigationView.setSelectedItemId(R.id.nav_person);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.nav_widgets:
                        startActivity(new Intent(getApplicationContext(),Widgets.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_home:
                        startActivity(new Intent(getApplicationContext(),Home.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_person:
                        return true;
                }
                return false;
            }
        });
    }

    private void anhxa() {
        ttdonhang = findViewById(R.id.trangthaidonhang);
    }
}
