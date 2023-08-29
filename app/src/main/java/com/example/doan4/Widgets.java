package com.example.doan4;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.doan4.API.API;
import com.example.doan4.Activity.ID_Product_Activity;
import com.example.doan4.Adapter.CategoryAdapter;
import com.example.doan4.Models.Category;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Widgets extends AppCompatActivity {

    ListView lvsp;
    CategoryAdapter category_adapter;
    RecyclerView rcv_displayhome_LoaiSP;
    ArrayList<Category>Category_arr=new ArrayList<>();
    BottomNavigationView navigationView;
    private ImageSlider imageSlider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.widgetslayout);
        getData();
        Anhxa();
        imageSlider = findViewById(R.id.imagesilde);
        ArrayList<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel("https://i.pinimg.com/564x/e4/da/d2/e4dad2240616600830b74493a1700f74.jpg", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://i.pinimg.com/564x/af/e2/ee/afe2ee20ba00f601094a50ab95bd329a.jpg", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://i.pinimg.com/564x/37/6c/5a/376c5aca0d515be196c686773c7e5334.jpg", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://i.pinimg.com/564x/19/0e/cd/190ecdaea8717f12e3701241fbdb75b1.jpg", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://i.pinimg.com/564x/3b/28/ef/3b28ef40051c5c219afa19cf15568bb2.jpg", ScaleTypes.FIT));

        imageSlider.setImageList(slideModels,ScaleTypes.FIT);
        navigationView = findViewById(R.id.bottom_navigation);
        navigationView.setSelectedItemId(R.id.nav_widgets);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.nav_widgets:
                        return true;
                    case R.id.nav_home:
                        startActivity(new Intent(getApplicationContext(),Home.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_person:
                        startActivity(new Intent(getApplicationContext(),My.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

    }

    private void Anhxa() {
        rcv_displayhome_LoaiSP=findViewById(R.id.rcv_displayhome_LoaiSP);
    }

    private void getData() {
        API.API.Get_Category().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                Category_arr= (ArrayList<Category>) response.body();
                rcv_displayhome_LoaiSP.setLayoutManager(new GridLayoutManager(Widgets.this,2));
                category_adapter=new CategoryAdapter(Category_arr,Widgets.this);
                rcv_displayhome_LoaiSP.setAdapter(category_adapter);
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {

            }
        });
    }


}
