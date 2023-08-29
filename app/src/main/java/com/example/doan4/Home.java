package com.example.doan4;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.doan4.API.API;
import com.example.doan4.Activity.SearchActivity;
import com.example.doan4.Adapter.ProductAdapter;
import com.example.doan4.Models.Product;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends AppCompatActivity {
    BottomNavigationView navigationView;
    TextView TextView_Search;
    ViewFlipper viewFlipper;
    RecyclerView rcv_displayhome_LoaiSP,rcv_displayhome_SP;
    ArrayList<Product> productsArrayList = new ArrayList<>();
    ProductAdapter product_adapter;
    private ImageSlider imageSlider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homelayout);
        Anhxa();
        getDta();
        ActionViewFlipper();
        imageSlider = findViewById(R.id.imagesilde);
        ArrayList<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel("https://i.pinimg.com/564x/e4/da/d2/e4dad2240616600830b74493a1700f74.jpg", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://i.pinimg.com/564x/af/e2/ee/afe2ee20ba00f601094a50ab95bd329a.jpg", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://i.pinimg.com/564x/37/6c/5a/376c5aca0d515be196c686773c7e5334.jpg", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://i.pinimg.com/564x/19/0e/cd/190ecdaea8717f12e3701241fbdb75b1.jpg", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://i.pinimg.com/564x/3b/28/ef/3b28ef40051c5c219afa19cf15568bb2.jpg", ScaleTypes.FIT));
        imageSlider.setImageList(slideModels,ScaleTypes.FIT);
        TextView_Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Home.this, SearchActivity.class));
            }
        });
        navigationView = findViewById(R.id.bottom_navigation);
        navigationView.setSelectedItemId(R.id.nav_home);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.nav_widgets:
                        startActivity(new Intent(getApplicationContext(),Widgets.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_home:
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

    private void ActionViewFlipper() {
        List<String> quangcao = new ArrayList<>();
        quangcao.add("https://i.pinimg.com/564x/af/e2/ee/afe2ee20ba00f601094a50ab95bd329a.jpg");
        quangcao.add("https://i.pinimg.com/564x/37/6c/5a/376c5aca0d515be196c686773c7e5334.jpg");
        quangcao.add("https://i.pinimg.com/564x/e4/da/d2/e4dad2240616600830b74493a1700f74.jpg");
        quangcao.add("https://i.pinimg.com/564x/3b/28/ef/3b28ef40051c5c219afa19cf15568bb2.jpg");
        for (int i = 0; i < quangcao.size(); i++) {
            ImageView imageView = new ImageView(getApplicationContext());
            Glide.with(getApplicationContext()).load(quangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(2000);
        viewFlipper.setAutoStart(true);
    }

    private void getDta() {
        API.API.Get_Product().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if(response.isSuccessful()){
                    productsArrayList = (ArrayList<Product>) response.body();
                    product_adapter =new ProductAdapter(productsArrayList,Home.this);
                    rcv_displayhome_SP.setLayoutManager(new LinearLayoutManager(Home.this, RecyclerView.VERTICAL,false));
                    rcv_displayhome_SP.setAdapter(product_adapter);
                }
            }
            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });
    }

    private void Anhxa() {
        TextView_Search = findViewById(R.id.TextView_Search);
        viewFlipper = findViewById(R.id.viewfliper);
        rcv_displayhome_SP=findViewById(R.id.rcv_displayhome_SP);

    }

}
