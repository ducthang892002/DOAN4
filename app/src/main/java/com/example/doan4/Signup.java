package com.example.doan4;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doan4.API.API;
import com.example.doan4.Database.CreateDatabase;
import com.example.doan4.Models.AppUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Signup extends AppCompatActivity {
    Button btndk1;
    Dialog dialog;
     private  EditText edthoten,edttkdk,edtmkdk,edtphone,edtloacation,onclickSignup;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        btndk1 = findViewById(R.id.signuptc);
        edttkdk = findViewById(R.id.taikhoan1);
        edtmkdk = findViewById(R.id.matkhau1);
        edthoten = findViewById(R.id.name1);
        edtphone = findViewById(R.id.sodienthoai);
        edtloacation = findViewById(R.id.diachi);
        btndk1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ResName = edthoten.getText().toString().trim();
                String ResAcc = edttkdk.getText().toString().trim();
                String ResPass = edtmkdk.getText().toString().trim();
                if(ResName.isEmpty() &&  ResAcc.isEmpty() && ResPass.isEmpty()){

                    Toast.makeText(getApplicationContext(),"Please enter all infomation", Toast.LENGTH_SHORT).show();
                }else {
                    API.API.CheckAcc(ResAcc,ResPass).enqueue(new Callback<List<AppUser>>() {
                        @Override
                        public void onResponse(Call<List<AppUser>> call, Response<List<AppUser>> response) {
                            if(!response.isSuccessful()){
                                AppUser appUser1 = new AppUser(0,ResName,ResAcc, ResPass,"Enter phone number","Enter address");
                                API.API.CreateAcc(appUser1).enqueue(new Callback<String>() {
                                    @Override
                                    public void onResponse(Call<String> call, Response<String> response) {
                                        if(response.isSuccessful()){
                                            Toast.makeText(getApplicationContext(), "Signup complete", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(Signup.this, LoginActivity.class);
                                            startActivity(intent);
                                        }else {

                                            Toast.makeText(getApplicationContext(), "Signup fail", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<String> call, Throwable t) {

                                    }
                                });

                            }else{

                                Toast.makeText(getApplicationContext(), "Account already exist", Toast.LENGTH_SHORT).show();
                            }

                        }
                        @Override
                        public void onFailure(Call<List<AppUser>> call, Throwable t) {
                            android.util.Log.e("Signup error", "" + t);
                        }
                    });
                }

            }
        });

    }


}

