package com.example.doan4;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.doan4.API.API;
import com.example.doan4.Database.CreateDatabase;
import com.example.doan4.Models.AppUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Signin extends AppCompatActivity {
    CreateDatabase database;
    public static int UserID = 0;
    EditText edttk,edtmk;
    Button btndn;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);
        btndn =  findViewById(R.id.signintc);
        edttk=(EditText) findViewById(R.id.taikhoan);
        edtmk=(EditText) findViewById(R.id.matkhau);
        btndn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String LogAcc = edttk.getText().toString().trim();
                String LogPass =edtmk.getText().toString().trim();
                if(LogAcc.isEmpty() || LogPass.isEmpty()){

                    Toast.makeText(getApplicationContext(),"Please enter your Account and Password",Toast.LENGTH_SHORT).show();
                }else {
                    API.API.CheckAcc(LogAcc,LogPass).enqueue(new Callback<List<AppUser>>() {
                        @Override
                        public void onResponse(Call<List<AppUser>> call, Response<List<AppUser>> response) {
                            if (response.isSuccessful()){

                                AppUser appUser = response.body().get(0);
                                Intent intent=new Intent(getApplicationContext(), Home.class);
                                UserID = appUser.getUserID();
                                intent.putExtra("UserID", appUser.getUserID());
                                startActivity(intent);
                            }else {

                                Toast.makeText(getApplicationContext(), "Wrong Account or Password", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<List<AppUser>> call, Throwable t) {
                            Log.e("Login error",""+t);
                        }
                    });

                }

            }
        });

    }
    public void btnDangnhap(){
        Intent intent = new Intent(Signin.this, Home.class);
        startActivity(intent);
    }

}

